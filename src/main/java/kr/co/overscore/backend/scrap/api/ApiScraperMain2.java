package kr.co.overscore.backend.scrap.api;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import kr.co.overscore.backend.model.AllModel;
import kr.co.overscore.backend.model.GameTypeModel;
import kr.co.overscore.backend.model.HeroesModel;
import kr.co.overscore.backend.model.HeroesStatModel;
import kr.co.overscore.backend.model.UserAchvModel;
import kr.co.overscore.backend.model.UserHeroesModel;
import kr.co.overscore.backend.model.UserModel;
import kr.co.overscore.backend.model.UserStatModel;
import kr.co.overscore.backend.scrap.AbstractScraper;
import kr.co.overscore.backend.scrap.Scraper;
import kr.co.overscore.backend.service.CacheService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ApiScraperMain2 extends AbstractScraper implements Scraper {
	String baseUrl = "https://playoverwatch.com/en-us/career/";

	boolean heroesCacheChanged = false;
	boolean statCacheChanged = false;
	
	CacheService cacheService = new CacheService();

	int v_comprank;
	int v_level;
	int v_prestige;

	public List<AllModel> extract(String battleTag) throws Exception {

		List<GameTypeModel> gameTypeCache = cacheService.getGametype();
		List<AllModel> allList = new ArrayList<AllModel>();

		/* pc
		[redips#31459]  battleTag : redips-31459, userName : redips, userTag : 31459, server : pc
		[redips#psn]       battleTag : redips,            userName : redips, userTag : ,           server : psn
		[redips#xbl]        battleTag : redips,            userName : redips, userTag : ,           server : xbl
		*/
		battleTag = battleTag.replace("#", "-");
		String[] battleTags = battleTag.split("-");
		String userName = battleTags[0];
		String userTag = battleTags[1];
		String server = (battleTags[1].equals("psn") || battleTags[1].equals("xbl")) ? battleTags[1] : "pc";
		battleTag = server.equals("pc") ? battleTag : userName;

		AllModel allModel = new AllModel();

		Document doc = scrap(baseUrl + server + "/" + battleTag);
		Elements elHeader = doc.select(".masthead-player-progression.show-for-lg");
		
		if(elHeader.isEmpty()){
			return allList;
		}

		UserModel userModel = new UserModel();
		userModel.setUserName(userName);
		userModel.setUserTag(userTag);
		userModel.setServer(server);
		userModel.setAvatar(doc.select(".masthead-player>img").attr("src"));

		String rank = "0";
		if (elHeader.select(".competitive-rank").size() > 0)
			rank = elHeader.select(".competitive-rank>div").first().html();

		userModel.setRankImage(elHeader.select(".competitive-rank>img").attr("src"));
		userModel.setTier(APIUtils.getTIER(elHeader.select(".competitive-rank>img").attr("src")
				.replaceAll("https://d1u1mce87gyfbn.cloudfront.net/game/rank-icons/season-2/", "")));
		v_comprank = Integer.parseInt(rank.equals("0") ? "-1" : rank);
		userModel.setRank(v_comprank);

		String prestige = APIUtils.getPRESTIGE(elHeader.select(".player-level").attr("style")
				.replace("background-image:url(https://d1u1mce87gyfbn.cloudfront.net/game/playerlevelrewards/", "")
				.split("_")[0]);
		if (prestige == null)
			prestige = "0";

		String level = elHeader.select(".player-level>div").first().html();
		v_level = Integer.parseInt(level);
		v_prestige = Integer.parseInt(prestige);

		// level = prestige + level;
		v_level = (v_prestige * 100) + v_level;
		userModel.setLevel(v_level);

		List<UserAchvModel> userAchvList = new ArrayList<UserAchvModel>();
		List<UserStatModel> userStatList = new ArrayList<UserStatModel>();
		List<UserHeroesModel> userHeroesList = new ArrayList<UserHeroesModel>();

		String[] gameTypes = { "quickplay", "competitive" };
		for (String gameType : gameTypes) {
			Elements elGameType = doc.select("#" + gameType);
			if (elGameType.select("select[data-group-id=stats]").size() > 0)
			{
				for (GameTypeModel data : gameTypeCache) {
					if (data.getTypeEng().equals(gameType)) {
						// cache reload
/*						if (statCacheChanged) {
							cacheService.removeStat();
							statCacheChanged = false;
						}*/
						if (heroesCacheChanged) {
							cacheService.removeHeroes();
							heroesCacheChanged = false;
						}

						Elements elCareerStat = elGameType.select("section.career-stats-section");
						Elements datas = elCareerStat.select("div.row.js-stats").attr("data-group-id", "stats");
						// 3. StatModel Building
						userStatList.add(getStat(datas.get(0), data.getTypeId()));

						// 4. HeroesStatModel Building
						datas.remove(0);
						// userHeroesList.addAll(getHeroesStat(datas,
						// data.getTypeId()));

						// 4-1. HeroesStatModel HeroesMostOrder Building
						Elements elComparison = elGameType.select("section.hero-comparison-section");
						Elements mosts = elComparison
								.select("[data-category-id=" + APIUtils.getDATA_CATEGORY("Time Played") + "]")
								.select("[data-overwatch-progress-percent]");
						userHeroesList.addAll(getHeroesMost(mosts,  getHeroesStat(datas, data.getTypeId())));

						// 5. ArchModel Building -- deprecated.
						List<UserAchvModel> userAchv = new ArrayList<UserAchvModel>();
						userAchvList.addAll(userAchv);
					}
				}
			}

		}
		userModel.setUserAchv(userAchvList);
		userModel.setUserStat(userStatList);
		userModel.setUserHeroes(userHeroesList);

		allModel.setUserModel(userModel);
		allList.add(allModel);
	

		return allList;
	}

	// return scrapUrl
	private List<String> getScrapUrl(String battleTag) throws IOException {
		List<String> scrapUrlList = new ArrayList<String>();

		// battleTag = battleTag.replaceAll(" ", "%20");

		String baseUrl = "https://playoverwatch.com/en-us";
		String searchUrl = "/search/account-by-name/";
		String jsonString = scrapJson(baseUrl.concat(searchUrl).concat(URLEncoder.encode(battleTag, "UTF-8").replaceAll("\\+", "%20")));

		JSONArray urlArray = JSONArray.fromObject(JSONSerializer.toJSON(jsonString));

		for (int i = 0; i < urlArray.size(); i++) {
			JSONObject urlObject = urlArray.getJSONObject(i);
			scrapUrlList.add(baseUrl.concat(urlObject.getString("careerLink")));
		}
		return scrapUrlList;
	}

	private UserStatModel getStat(Element data, String typeId) {
		Elements keys = data.select("td:eq(0)");
		Elements values = data.select("td:eq(1)");

		float gamesWon = 0;
		float gamesPlayed = 0;
		float damageDone = 0;
		float damageDoneAverage = 0;
		float eliminations = 0;
		float deaths = 1;
		float deathsAverage = 1;
		float gamesTied = 0;

		UserStatModel userStatModel = new UserStatModel();
		userStatModel.setTypeId(Integer.parseInt(typeId));

		for (int j = 0; j < keys.size(); j++) {
			String key = keys.get(j).text();
			String value = values.get(j).text();

			// refine ext value
			String sanKey = APIUtils.sanitize(key);
			float sanValue = Float.parseFloat(APIUtils.tryExtract(value));

			if (sanKey.equals("card") || sanKey.equals("cards")) {
				userStatModel.setCards(sanValue);
			} else if (sanKey.equals("comprank")) {
				userStatModel.setComprank(sanValue);
			} else if (sanKey.equals("damage_done") || sanKey.equals("all_damage_done")) {
				damageDone = sanValue;
				userStatModel.setDamageDone(sanValue);
			} else if (sanKey.equals("damage_done_average") || sanKey.equals("damage_done_avg")) {
				damageDoneAverage = sanValue;
				userStatModel.setDamageDoneAverage(sanValue);
			} else if (sanKey.equals("damage_done_most_in_game")) {
				userStatModel.setDamageDoneMostInGame(sanValue);
			} else if (sanKey.equals("deaths") || sanKey.equals("death")) {
				deaths = sanValue;
				userStatModel.setDeaths(sanValue);
			} else if (sanKey.equals("deaths_average") || sanKey.equals("deaths_avg")) {
				deathsAverage = sanValue;
				userStatModel.setDeathsAverage(sanValue);
			} else if (sanKey.equals("defensive_assists") || sanKey.equals("defensive_assists")) {
				userStatModel.setDefensiveAssists(sanValue);
			} else if (sanKey.equals("defensive_assists_average")) {
				userStatModel.setDefensiveAssistsAverage(sanValue);
			} else if (sanKey.equals("defensive_assists_most_in_game")
					|| sanKey.equals("defensive_assist_most_in_game")) {
				userStatModel.setDefensiveAssistsMostInGame(sanValue);
			} else if (sanKey.equals("eliminations") || sanKey.equals("elimination")) {
				eliminations = sanValue;
				userStatModel.setEliminations(sanValue);
			} else if (sanKey.equals("eliminations_average") || sanKey.equals("eliminations_avg")) {
				userStatModel.setEliminationsAverage(sanValue);
			} else if (sanKey.equals("eliminations_most_in_game") || sanKey.equals("elimination_most_in_game")) {
				userStatModel.setEliminationsMostInGame(sanValue);
			} else if (sanKey.equals("environmental_deaths") || sanKey.equals("environmental_death")) {
				userStatModel.setEnvironmentalDeaths(sanValue);
			} else if (sanKey.equals("environmental_kills") || sanKey.equals("environmental_kill")) {
				userStatModel.setEnvironmentalKills(sanValue);
			} else if (sanKey.equals("final_blows") || sanKey.equals("final_blow")) {
				userStatModel.setFinalBlows(sanValue);
			} else if (sanKey.equals("final_blows_average") || sanKey.equals("final_blows_avg")) {
				userStatModel.setFinalBlowsAverage(sanValue);
			} else if (sanKey.equals("final_blows_most_in_game") || sanKey.equals("final_blow_most_in_game")) {
				userStatModel.setFinalBlowsMostInGame(sanValue);
			} else if (sanKey.equals("games")) {
				userStatModel.setGames(sanValue);
			} else if (sanKey.equals("games_lost")) {
				userStatModel.setGamesLost(sanValue);
			} else if (sanKey.equals("games_played")) {
				gamesPlayed = sanValue;
				userStatModel.setGamesPlayed(sanValue);
			} else if (sanKey.equals("games_tied")) {
				gamesTied = sanValue;
				userStatModel.setGamesTied(sanValue);
			} else if (sanKey.equals("games_won")) {
				gamesWon = sanValue;
				userStatModel.setGamesWon(sanValue);
			} else if (sanKey.equals("healing_done")) {
				userStatModel.setHealingDone(sanValue);
			} else if (sanKey.equals("healing_done_average") || sanKey.equals("healing_done_avg")) {
				userStatModel.setHealingDoneAverage(sanValue);
			} else if (sanKey.equals("healing_done_most_in_game")) {
				userStatModel.setHealingDoneMostInGame(sanValue);
			} else if (sanKey.equals("kpd")) {
				userStatModel.setKpd(sanValue);
			} else if (sanKey.equals("level")) {
				userStatModel.setLevel(sanValue);
			} else if (sanKey.equals("losses")) {
				userStatModel.setLosses(sanValue);
			} else if (sanKey.equals("medals")) {
				userStatModel.setMedals(sanValue);
			} else if (sanKey.equals("medals_bronze")) {
				userStatModel.setMedalsBronze(sanValue);
			} else if (sanKey.equals("medals_gold")) {
				userStatModel.setMedalsGold(sanValue);
			} else if (sanKey.equals("medals_silver")) {
				userStatModel.setMedalsSilver(sanValue);
			} else if (sanKey.equals("melee_final_blows") || sanKey.equals("melee_final_blow")) {
				userStatModel.setMeleeFinalBlows(sanValue);
			} else if (sanKey.equals("melee_final_blows_average") || sanKey.equals("melee_final_blows_avg")) {
				userStatModel.setMeleeFinalBlowsAverage(sanValue);
			} else if (sanKey.equals("melee_final_blows_most_in_game")
					|| sanKey.equals("melee_final_blow_most_in_game")) {
				userStatModel.setMeleeFinalBlowsMostInGame(sanValue);
			} else if (sanKey.equals("multikills") || sanKey.equals("multikill")) {
				userStatModel.setMultikills(sanValue);
			} else if (sanKey.equals("multikill_best")) {
				userStatModel.setMultikillBest(sanValue);
			} else if (sanKey.equals("objective_kills") || sanKey.equals("objective_kill")) {
				userStatModel.setObjectiveKills(sanValue);
			} else if (sanKey.equals("objective_kills_average")) {
				userStatModel.setObjectiveKillsAverage(sanValue);
			} else if (sanKey.equals("objective_kills_most_in_game") || sanKey.equals("objective_kill_most_in_game")) {
				userStatModel.setObjectiveKillsMostInGame(sanValue);
			} else if (sanKey.equals("objective_time")) {
				userStatModel.setObjectiveTime(sanValue);
			} else if (sanKey.equals("objective_time_average") || sanKey.equals("objective_time_avg")) {
				userStatModel.setObjectiveTimeAverage(sanValue);
			} else if (sanKey.equals("objective_time_most_in_game")) {
				userStatModel.setObjectiveTimeMostInGame(sanValue);
			} else if (sanKey.equals("offensive_assists") || sanKey.equals("offensive_assist")) {
				userStatModel.setOffensiveAssists(sanValue);
			} else if (sanKey.equals("offensive_assists_average")) {
				userStatModel.setOffensiveAssistsAverage(sanValue);
			} else if (sanKey.equals("offensive_assists_most_in_game")
					|| sanKey.equals("offensive_assist_most_in_game")) {
				userStatModel.setOffensiveAssistsMostInGame(sanValue);
			} else if (sanKey.equals("prestige")) {
				userStatModel.setPrestige(sanValue);
			} else if (sanKey.equals("recon_assists") || sanKey.equals("recon_assist")) {
				userStatModel.setReconAssists(sanValue);
			} else if (sanKey.equals("recon_assists_average")) {
				userStatModel.setReconAssistsAverage(sanValue);
			} else if (sanKey.equals("recon_assists_most_in_game") || sanKey.equals("recon_assist_most_in_game")) {
				userStatModel.setReconAssistsMostInGame(sanValue);
			} else if (sanKey.equals("solo_kills") || sanKey.equals("solo_kill")) {
				userStatModel.setSoloKills(sanValue);
			} else if (sanKey.equals("solo_kills_average") || sanKey.equals("solo_kills_avg")) {
				userStatModel.setSoloKillsAverage(sanValue);
			} else if (sanKey.equals("solo_kills_most_in_game") || sanKey.equals("solo_kill_most_in_game")) {
				userStatModel.setSoloKillsMostInGame(sanValue);
			} else if (sanKey.equals("teleporter_pads_destroyed") || sanKey.equals("teleporter_pad_destroyed")) {
				userStatModel.setTeleporterPadsDestroyed(sanValue);
			} else if (sanKey.equals("ties")) {
				userStatModel.setTies(sanValue);
			} else if (sanKey.equals("time_played")) {
				userStatModel.setTimePlayed(sanValue);
			} else if (sanKey.equals("time_spent_on_fire")) {
				userStatModel.setTimeSpentOnFire(sanValue);
			} else if (sanKey.equals("time_spent_on_fire_average") || sanKey.equals("time_spent_on_fire_avg")) {
				userStatModel.setTimeSpentOnFireAverage(sanValue);
			} else if (sanKey.equals("time_spent_on_fire_most_in_game")) {
				userStatModel.setTimeSpentOnFireMostInGame(sanValue);
			} else if (sanKey.equals("wins")) {
				userStatModel.setWins(sanValue);
			} else if (sanKey.equals("win_rate")) {
				userStatModel.setWinRate(sanValue);
			}
		}

		float gamesTotal = (gamesPlayed == 0) ? (deaths / deathsAverage) : gamesPlayed;
		gamesTotal = Float.isInfinite(gamesTotal) ? 0 : gamesTotal;
		
		userStatModel.setWins(gamesWon);
		userStatModel.setLosses(gamesTotal - gamesWon);
		userStatModel.setTies(gamesTied);
		userStatModel.setGames(gamesTotal);
		userStatModel.setKpd(Float.parseFloat(String.format("%.2f", eliminations / deaths)));
		userStatModel.setComprank(v_comprank);
		userStatModel.setLevel(v_level);
		userStatModel.setPrestige(v_prestige);
		userStatModel.setWinRate(gamesWon < 0.01 ? 0 : gamesWon / (gamesTotal - gamesTied));

		return userStatModel;
	}

	// ext data
	/*
	private UserStatModel getUserStatModelExt(String typeId, String groupId, String key, float value) {
		UserStatModel userStatModel = new UserStatModel();
		userStatModel.setTypeId(typeId);
		userStatModel.setGroupId(groupId);
		userStatModel.setStatId(getStatId(key));
		userStatModel.setValue(value);

		return userStatModel;
	}
	 */
	private List<UserHeroesModel> getHeroesStat(Elements datas, String typeId) {
		List<HeroesStatModel> heroesStatList = cacheService.getHeroesStat();

		List<UserHeroesModel> userHeroesList = new ArrayList<UserHeroesModel>();

		for (int i = 0; i < datas.size(); i++) {
			String heroesName = APIUtils.getHEROES(datas.get(i).attr("data-category-id"));
			String heroesId = getHeroesId(heroesName);
			Elements keys = datas.get(i).select("td:eq(0)");
			Elements values = datas.get(i).select("td:eq(1)");
			
			HeroesStatModel heroesStat = new HeroesStatModel();
			for (HeroesStatModel data : heroesStatList) {
				if (data.getHeroesId() == Integer.parseInt(heroesId))
					heroesStat = data;
			}

			UserHeroesModel userHeroesModel = new UserHeroesModel();
			userHeroesModel.setTypeId(Integer.parseInt(typeId));
			userHeroesModel.setHeroesId(Integer.parseInt(heroesId));

			for (int j = 0; j < keys.size(); j++) {
				String key = keys.get(j).text();
				String value = values.get(j).text();

				// Refine version 2.0
				String sanKey = APIUtils.sanitize(key);
				float sanValue = Float.parseFloat(APIUtils.tryExtract(value));

				// GAME
				if (sanKey.equals("time_played"))
					userHeroesModel.setTimePlayed(sanValue);
				else if (sanKey.equals("games_won"))
					userHeroesModel.setGamesWon(sanValue);
				else if (sanKey.equals("objective_time"))
					userHeroesModel.setObjectiveKills(sanValue);
				else if (sanKey.equals("time_spent_on_fire"))
					userHeroesModel.setTimeSpentOnFire(sanValue);
				else if (sanKey.equals("games_played"))
					userHeroesModel.setGamesPlayed(sanValue);
				else if (sanKey.equals("win_percentage"))
					userHeroesModel.setWinPercentage(sanValue);
				else if (sanKey.equals("games_tied"))
					userHeroesModel.setGamesTied(sanValue);
				else if (sanKey.equals("games_lost"))
					userHeroesModel.setGamesLost(sanValue);

				// COMBAT
				else if (sanKey.equals("eliminations") || sanKey.equals("elimination"))
					userHeroesModel.setEliminations(sanValue);
				else if (sanKey.equals("final_blows") || sanKey.equals("final_blow"))
					userHeroesModel.setFinalBlows(sanValue);
				else if (sanKey.equals("solo_kills") || sanKey.equals("solo_kill"))
					userHeroesModel.setSoloKills(sanValue);
				else if (sanKey.equals("shots_fired") || sanKey.equals("shot_fired"))
					userHeroesModel.setShotsFired(sanValue);
				else if (sanKey.equals("shots_hit") || sanKey.equals("shot_hit"))
					userHeroesModel.setShotsHit(sanValue);
				else if (sanKey.equals("critical_hits") || sanKey.equals("critical_hit"))
					userHeroesModel.setCriticalHits(sanValue);
				else if (sanKey.equals("damage_done") || sanKey.equals("all_damage_done"))
					userHeroesModel.setDamageDone(sanValue);
				else if (sanKey.equals("damage_blocked") && userHeroesModel.getDamageBlocked() == 0.0)
					userHeroesModel.setDamageBlocked(sanValue);
				else if (sanKey.equals("objective_kills"))
					userHeroesModel.setObjectiveKills(sanValue);
				else if (sanKey.equals("multikills") || sanKey.equals("multikill"))
					userHeroesModel.setMultikills(sanValue);
				else if (sanKey.equals("melee_final_blows") || sanKey.equals("melee_final_blow"))
					userHeroesModel.setMeleeFinalBlows(sanValue);
				else if (sanKey.equals("critical_hit_per_minute"))
					userHeroesModel.setCriticalHitPerMinute(sanValue);
				else if (sanKey.equals("critical_hit_accuracy"))
					userHeroesModel.setCriticalHitAccuracy(sanValue);
				else if (sanKey.equals("eliminations_per_life") || sanKey.equals("elimination_per_life"))
					userHeroesModel.setEliminationsPerLife(sanValue);
				else if (sanKey.equals("weapon_accuracy"))
					userHeroesModel.setWeaponAccuracy(sanValue);

				// DEATHS
				else if (sanKey.equals("deaths") || sanKey.equals("death"))
					userHeroesModel.setDeaths(sanValue);
				else if (sanKey.equals("environmental_deaths") || sanKey.equals("environmental_death"))
					userHeroesModel.setEnvironmentalDeaths(sanValue);

				// ASSISTS
				else if (sanKey.equals("healing_done"))
					userHeroesModel.setHealingDone(sanValue);
				else if (sanKey.equals("teleporter_pads_destroyed") || sanKey.equals("teleporter_pad_destroyed"))
					userHeroesModel.setTeleporterPadsDestroyed(sanValue);
				else if (sanKey.equals("turrets_destroyed") || sanKey.equals("turret_destroyed"))
					userHeroesModel.setTurretsDestroyed(sanValue);
				else if (sanKey.equals("offensive_assists") || sanKey.equals("offensive_assist"))
					userHeroesModel.setOffensiveAssists(sanValue);
				else if (sanKey.equals("self_healing"))
					userHeroesModel.setSelfHealing(sanValue);

				// MATCH AWARDS
				else if (sanKey.equals("cards") || sanKey.equals("card"))
					userHeroesModel.setCards(sanValue);
				else if (sanKey.equals("medals") || sanKey.equals("medal"))
					userHeroesModel.setMedals(sanValue);
				else if (sanKey.equals("medals_gold") || sanKey.equals("medal_gold"))
					userHeroesModel.setMedalsGold(sanValue);
				else if (sanKey.equals("medals_silver") || sanKey.equals("medal_silver"))
					userHeroesModel.setMedalsSilver(sanValue);
				else if (sanKey.equals("medals_bronze") || sanKey.equals("medal_bronze"))
					userHeroesModel.setMedalsBronze(sanValue);

				// AVERAGE
				else if (sanKey.equals("melee_final_blows_average") || sanKey.equals("melee_final_blow_average"))
					userHeroesModel.setMeleeFinalBlowsAverage(sanValue);
				else if (sanKey.equals("self_healing_average"))
					userHeroesModel.setSelfHealingAverage(sanValue);
				else if (sanKey.equals("offensive_assists_average") || sanKey.equals("offensive_assist_average"))
					userHeroesModel.setOffensiveAssistsAverage(sanValue);
				else if (sanKey.equals("deaths_average") || sanKey.equals("death_average"))
					userHeroesModel.setDeathsAverage(sanValue);
				else if (sanKey.equals("solo_kills_average") || sanKey.equals("solo_kill_average"))
					userHeroesModel.setSoloKillsAverage(sanValue);
				else if (sanKey.equals("objective_time_average"))
					userHeroesModel.setObjectiveTimeAverage(sanValue);
				else if (sanKey.equals("objective_kills_average") || sanKey.equals("objective_kill_average"))
					userHeroesModel.setObjectiveKillsAverage(sanValue);
				else if (sanKey.equals("healing_done_average"))
					userHeroesModel.setHealingDoneAverage(sanValue);
				else if (sanKey.equals("final_blows_average") || sanKey.equals("final_blows_average"))
					userHeroesModel.setFinalBlowsAverage(sanValue);

				// BEST
				else if (sanKey.equals("eliminations_most_in_life") || sanKey.equals("elimination_most_in_life"))
					userHeroesModel.setEliminationsMostInLife(sanValue);
				else if (sanKey.equals("damage_done_most_in_life"))
					userHeroesModel.setDamageDoneMostInLife(sanValue);
				else if (sanKey.equals("healing_done_most_in_life"))
					userHeroesModel.setHealingDoneMostInLife(sanValue);
				else if (sanKey.equals("weapon_accuracy_best_in_game"))
					userHeroesModel.setWeaponAccuracyBestInGame(sanValue);
				else if (sanKey.equals("kill_streak_best"))
					userHeroesModel.setKillStreakBest(sanValue);
				else if (sanKey.equals("damage_done_most_in_game"))
					userHeroesModel.setDamageDoneMostInGame(sanValue);
				else if (sanKey.equals("eliminations_most_in_game") || sanKey.equals("elimination_most_in_game"))
					userHeroesModel.setEliminationsMostInGame(sanValue);
				else if (sanKey.equals("final_blows_most_in_game") || sanKey.equals("final_blow_most_in_game"))
					userHeroesModel.setFinalBlowsMostInGame(sanValue);
				else if (sanKey.equals("objective_kills_most_in_game") || sanKey.equals("objective_kill_most_in_game"))
					userHeroesModel.setObjectiveKillsMostInGame(sanValue);
				else if (sanKey.equals("objective_time_most_in_game"))
					userHeroesModel.setObjectiveTimeMostInGame(sanValue);
				else if (sanKey.equals("solo_kills_most_in_game") || sanKey.equals("solo_kill_most_in_game"))
					userHeroesModel.setSoloKillsMostInGame(sanValue);
				else if (sanKey.equals("offensive_assists_most_in_game") || sanKey.equals("offensive_assist_most_in_game"))
					userHeroesModel.setOffensiveAssistsMostInGame(sanValue);
				else if (sanKey.equals("critical_hits_most_in_game") || sanKey.equals("critical_hit_most_in_game"))
					userHeroesModel.setCriticalHitsMostInGame(sanValue);
				else if (sanKey.equals("critical_hits_most_in_life") || sanKey.equals("critical_hit_most_in_life"))
					userHeroesModel.setCriticalHitsMostInLife(sanValue);
				else if (sanKey.equals("self_healing_most_in_game"))
					userHeroesModel.setSelfHealingMostInGame(sanValue);
				else if (sanKey.equals("melee_final_blows_most_in_game") || sanKey.equals("melee_final_blow_most_in_game"))
					userHeroesModel.setMeleeFinalBlowsMostInGame(sanValue);
				else if (sanKey.equals("damage_blocked_most_in_game"))
					userHeroesModel.setDamageBlockedMostInGame(sanValue);

				// HERO SPECIFIC [no else]
				if (heroesStat.getStat1() != null  && sanKey.replaceAll("s", "").equals(heroesStat.getStat1().replaceAll("s", "")))
					userHeroesModel.setStat1(sanValue);
				if(sanKey.replaceAll("s", "").equals(("sound_{count,_plural,_one_{barrier}_other_{barriers}}_provided").replaceAll("s", "")))
					userHeroesModel.setStat1(sanValue);
				if (heroesStat.getStat2() != null  && sanKey.replaceAll("s", "").equals(heroesStat.getStat2().replaceAll("s", "")))
					userHeroesModel.setStat2(sanValue);
				if (heroesStat.getStat3() != null  && sanKey.replaceAll("s", "").equals(heroesStat.getStat3().replaceAll("s", "")))
					userHeroesModel.setStat3(sanValue);
				if (heroesStat.getStat4() != null  && sanKey.replaceAll("s", "").equals(heroesStat.getStat4().replaceAll("s", "")))
					userHeroesModel.setStat4(sanValue);
				if (heroesStat.getStat5() != null  && sanKey.replaceAll("s", "").equals(heroesStat.getStat5().replaceAll("s", "")))
					userHeroesModel.setStat5(sanValue);
				if (heroesStat.getStat6() != null  && sanKey.replaceAll("s", "").equals(heroesStat.getStat6().replaceAll("s", "")))
					userHeroesModel.setStat6(sanValue);
				if (heroesStat.getStat7() != null  && sanKey.replaceAll("s", "").equals(heroesStat.getStat7().replaceAll("s", "")))
					userHeroesModel.setStat7(sanValue);
				if (heroesStat.getStat8() != null  && sanKey.replaceAll("s", "").equals(heroesStat.getStat8().replaceAll("s", "")))
					userHeroesModel.setStat8(sanValue);
				if (heroesStat.getStat9() != null  && sanKey.replaceAll("s", "").equals(heroesStat.getStat9().replaceAll("s", "")))
					userHeroesModel.setStat9(sanValue);
				if (heroesStat.getStat10() != null  && sanKey.replaceAll("s", "").equals(heroesStat.getStat10().replaceAll("s", "")))
					userHeroesModel.setStat10(sanValue);
				if (heroesStat.getStat11() != null  && sanKey.replaceAll("s", "").equals(heroesStat.getStat11().replaceAll("s", "")))
					userHeroesModel.setStat11(sanValue);
				if (heroesStat.getStat12() != null  && sanKey.replaceAll("s", "").equals(heroesStat.getStat12().replaceAll("s", "")))
					userHeroesModel.setStat12(sanValue);
				if (heroesStat.getStat13() != null  && sanKey.replaceAll("s", "").equals(heroesStat.getStat13().replaceAll("s", "")))
					userHeroesModel.setStat13(sanValue);
				if (heroesStat.getStat14() != null  && sanKey.replaceAll("s", "").equals(heroesStat.getStat14().replaceAll("s", "")))
					userHeroesModel.setStat14(sanValue);
				if (heroesStat.getStat15() != null  && sanKey.replaceAll("s", "").equals(heroesStat.getStat15().replaceAll("s", "")))
					userHeroesModel.setStat15(sanValue);
				if (heroesStat.getStat16() != null  && sanKey.replaceAll("s", "").equals(heroesStat.getStat16().replaceAll("s", "")))
					userHeroesModel.setStat16(sanValue);
				if (heroesStat.getStat17() != null  && sanKey.replaceAll("s", "").equals(heroesStat.getStat17().replaceAll("s", "")))
					userHeroesModel.setStat17(sanValue);
				if (heroesStat.getStat18() != null  && sanKey.replaceAll("s", "").equals(heroesStat.getStat18().replaceAll("s", "")))
					userHeroesModel.setStat18(sanValue);
				if (heroesStat.getStat19() != null  && sanKey.replaceAll("s", "").equals(heroesStat.getStat19().replaceAll("s", "")))
					userHeroesModel.setStat19(sanValue);
				if (heroesStat.getStat20() != null  && sanKey.replaceAll("s", "").equals(heroesStat.getStat20().replaceAll("s", "")))
					userHeroesModel.setStat20(sanValue);
			}
			userHeroesList.add(userHeroesModel);
		}

		return userHeroesList;
	}

	private List<UserHeroesModel> getHeroesMost(Elements datas, List<UserHeroesModel> userHeroesList) {
		if (datas == null || (datas != null && datas.size() < 1))
			return userHeroesList;

		for (int i = 0; i < datas.size(); i++) {
			String heroesName = APIUtils.sanitize(datas.get(i).select(".title").html());
			String heroesId = getHeroesId(heroesName);

			for (UserHeroesModel data : userHeroesList) {
				if (heroesId.equals(String.valueOf(data.getHeroesId()))) {
					data.setMostOrder(i + 1);
					break;
				}
			}
		}

		return userHeroesList;
	}

	// ext data
	/*
	 * private UserHeroesModel getUserHeroesModelExt(String typeId, String
	 * groupId, String heroesId, String key, float value){ UserHeroesModel
	 * userHeroesModel = new UserHeroesModel();
	 * userHeroesModel.setTypeId(typeId); userHeroesModel.setGroupId(groupId);
	 * userHeroesModel.setHeroesId(heroesId);
	 * userHeroesModel.setStatId(getStatId(key));
	 * userHeroesModel.setValue(value);
	 * 
	 * return userHeroesModel; }
	 */

	private String getHeroesId(String heroesName) {
		List<HeroesModel> heroesCache = cacheService.getHeroes();

		for (HeroesModel data : heroesCache) {
			if (data.getHeroesName().equals(heroesName))
				return data.getHeroesId();
		}

		HeroesModel heroesModel = cacheService.putHeroes(heroesName);
		heroesCacheChanged = true;
		return heroesModel.getHeroesId();
	}
/*
	private String getStatId(String statName) {
		List<StatModel> statCache = cacheService.getStat();
		for (StatModel data : statCache) {
			if (data.getStatEng().equals(statName))
				return data.getStatId();
		}

		StatModel statModel = cacheService.putStat(statName);
		statCacheChanged = true;
		return statModel.getStatId();
	}
*/
}
