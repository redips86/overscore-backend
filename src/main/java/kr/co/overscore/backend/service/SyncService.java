package kr.co.overscore.backend.service;

import java.util.HashMap;
import java.util.List;

import kr.co.overscore.backend.model.AllModel;
import kr.co.overscore.backend.model.UserHeroesModel;
import kr.co.overscore.backend.model.UserModel;
import kr.co.overscore.backend.model.UserStatModel;
import kr.co.overscore.backend.persistence.SyncMapper;

public class SyncService {
	SyncMapper mapper = new SyncMapper();
	
	public void upsertAllModel(AllModel allModel){
		
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		UserModel userModel = allModel.getUserModel();
		if(userModel == null)
			return;
		
		map.put("userName", allModel.getUserModel().getUserName());
		map.put("userTag", allModel.getUserModel().getUserTag());
		map.put("server", allModel.getUserModel().getServer());
		map.put("rankImage", allModel.getUserModel().getRankImage());
		map.put("avatar", allModel.getUserModel().getAvatar());
		map.put("tier", allModel.getUserModel().getTier());
		map.put("level", String.valueOf(allModel.getUserModel().getLevel()));
		map.put("rank", String.valueOf(allModel.getUserModel().getRank()));
		map.put("oprt", "admin");
		
		UserModel userIdModel = mapper.upsertUser(map);
		int userId = userIdModel.getUserId();
		
		List<UserStatModel> userStatList = allModel.getUserModel().getUserStat();
		mapper.deleteUserStat(userId);
		
		for(UserStatModel data : userStatList){
			map = new HashMap<String,Object>();
			map.put("userId", userId);
			map.put("typeId", data.getTypeId());
			map.put("cards",  data.getCards());
			map.put("comprank", data.getComprank());
			map.put("damageDone", data.getDamageDone());
			map.put("damageDoneAverage", data.getDamageDoneAverage());
			map.put("damageDoneMostInGame", data.getDamageDoneMostInGame());
			map.put("deaths", data.getDeaths());
			map.put("deathsAverage", data.getDeathsAverage());
			map.put("defensiveAssists", data.getDefensiveAssists());
			map.put("defensiveAssistsAverage", data.getDefensiveAssistsAverage());
			map.put("defensiveAssistsMostInGame", data.getDefensiveAssistsMostInGame());
			map.put("eliminations", data.getEliminations());
			map.put("eliminationsAverage", data.getEliminationsAverage());
			map.put("eliminationsMostInGame", data.getEliminationsMostInGame());
			map.put("environmentalDeaths", data.getEnvironmentalDeaths());
			map.put("environmentalKills", data.getEnvironmentalKills());
			map.put("finalBlows", data.getFinalBlows());
			map.put("finalBlowsAverage", data.getFinalBlowsAverage());
			map.put("finalBlowsMostInGame", data.getFinalBlowsMostInGame());
			map.put("games", data.getGames());
			map.put("gamesLost", data.getGamesLost());
			map.put("gamesPlayed", data.getGamesPlayed());
			map.put("gamesTied", data.getGamesTied());
			map.put("gamesWon", data.getGamesWon());
			map.put("healingDone", data.getHealingDone());
			map.put("healingDoneAverage", data.getHealingDoneAverage());
			map.put("healingDoneMostInGame", data.getHealingDoneMostInGame());
			map.put("kpd", data.getKpd());
			map.put("level", data.getLevel());
			map.put("losses", data.getLosses());
			map.put("medals", data.getMedals());
			map.put("medalsBronze", data.getMedalsBronze());
			map.put("medalsGold", data.getMedalsGold());
			map.put("medalsSilver", data.getMedalsSilver());
			map.put("meleeFinalBlows", data.getMeleeFinalBlows());
			map.put("meleeFinalBlowsAverage", data.getMeleeFinalBlowsAverage());
			map.put("meleeFinalBlowsMostInGame", data.getMeleeFinalBlowsMostInGame());
			map.put("multikills", data.getMultikills());
			map.put("multikillBest", data.getMultikillBest());
			map.put("objectiveKills", data.getObjectiveKills());
			map.put("objectiveKillsAverage", data.getObjectiveKillsAverage());
			map.put("objectiveKillsMostInGame", data.getObjectiveKillsMostInGame());
			map.put("objectiveTime", data.getObjectiveTime());
			map.put("objectiveTimeAverage", data.getObjectiveTimeAverage());
			map.put("objectiveTimeMostInGame", data.getObjectiveTimeMostInGame());
			map.put("offensiveAssists", data.getOffensiveAssists());
			map.put("offensiveAssistsAverage", data.getOffensiveAssistsAverage());
			map.put("offensiveAssistsMostInGame", data.getOffensiveAssistsMostInGame());
			map.put("prestige", data.getPrestige());
			map.put("reconAssists", data.getReconAssists());
			map.put("reconAssistsAverage", data.getReconAssistsAverage());
			map.put("reconAssistsMostInGame", data.getReconAssistsMostInGame());
			map.put("soloKills", data.getSoloKills());
			map.put("soloKillsAverage", data.getSoloKillsAverage());
			map.put("soloKillsMostInGame", data.getSoloKillsMostInGame());
			map.put("teleporterPadsDestroyed", data.getTeleporterPadsDestroyed());
			map.put("ties", data.getTies());
			map.put("timePlayed", data.getTimePlayed());
			map.put("timeSpentOnFire", data.getTimeSpentOnFire());
			map.put("timeSpentOnFireAverage", data.getTimeSpentOnFireAverage());
			map.put("timeSpentOnFireMostInGame", data.getTimeSpentOnFireMostInGame());
			map.put("wins", data.getWins());
			map.put("winRate", data.getWinRate());
			map.put("oprt", "admin");
							
			mapper.insertUserStat(map);
		}

		
		List<UserHeroesModel> UserHeroesList = allModel.getUserModel().getUserHeroes();
		mapper.deleteUserHeroes(userId);
		
		for(UserHeroesModel data : UserHeroesList){
			map = new HashMap<String,Object>();
			map.put("userId", userId);
			map.put("typeId", data.getTypeId());
			map.put("heroesId", data.getHeroesId());
			map.put("timePlayed", data.getTimePlayed());
			map.put("gamesPlayed", data.getGamesPlayed());
			map.put("gamesWon", data.getGamesWon());
			map.put("gamesTied", data.getGamesTied());
			map.put("gamesLost", data.getGamesLost());
			map.put("objectiveTime", data.getObjectiveTime());
			map.put("timeSpentOnFire", data.getTimeSpentOnFire());
			map.put("winPercentage", data.getWinPercentage());
			map.put("eliminations", data.getEliminations());
			map.put("finalBlows", data.getFinalBlows());
			map.put("soloKills", data.getSoloKills());
			map.put("shotsFired", data.getShotsFired());
			map.put("shotsHit", data.getShotsHit());
			map.put("criticalHits", data.getCriticalHits());
			map.put("damageDone", data.getDamageDone());
			map.put("damageBlocked", data.getDamageBlocked());
			map.put("objectiveKills", data.getObjectiveKills());
			map.put("multikills", data.getMultikills());
			map.put("meleeFinalBlows", data.getMeleeFinalBlows());
			map.put("criticalHitPerMinute", data.getCriticalHitAccuracy());
			map.put("criticalHitAccuracy", data.getCriticalHitAccuracy());
			map.put("eliminationsPerLife", data.getEliminationsPerLife());
			map.put("weaponAccuracy", data.getWeaponAccuracy());
			map.put("deaths", data.getDeaths());
			map.put("environmentalDeaths", data.getEnvironmentalDeaths());
			map.put("healingDone", data.getHealingDone());
			map.put("teleporterPadsDestroyed", data.getTeleporterPadsDestroyed());
			map.put("turretsDestroyed", data.getTurretsDestroyed());
			map.put("offensiveAssists", data.getOffensiveAssists());
			map.put("selfHealing", data.getSelfHealing());
			map.put("cards", data.getCards());
			map.put("medals", data.getMedals());
			map.put("medalsGold", data.getMedalsGold());
			map.put("medalsSilver", data.getMedalsSilver());
			map.put("medalsBronze", data.getMedalsBronze());
			map.put("meleeFinalBlowsAverage", data.getMeleeFinalBlowsAverage());
			map.put("selfHealingAverage", data.getSelfHealingAverage());
			map.put("offensiveAssistsAverage", data.getOffensiveAssistsAverage());
			map.put("deathsAverage", data.getDeathsAverage());
			map.put("soloKillsAverage", data.getSoloKillsAverage());
			map.put("objectiveTimeAverage", data.getObjectiveTimeAverage());
			map.put("objectiveKillsAverage", data.getObjectiveKillsAverage());
			map.put("healingDoneAverage", data.getHealingDoneAverage());
			map.put("finalBlowsAverage", data.getFinalBlowsAverage());
			map.put("eliminationsAverage", data.getEliminationsAverage());
			map.put("damageDoneAverage", data.getDamageDoneAverage());
			map.put("damageBlockedAverage", data.getDamageBlockedAverage());
			map.put("eliminationsMostInLife", data.getEliminationsMostInLife());
			map.put("damageDoneMostInLife", data.getDamageDoneMostInLife());
			map.put("healingDoneMostInLife", data.getHealingDoneMostInLife());
			map.put("weaponAccuracyBestInGame", data.getWeaponAccuracyBestInGame());
			map.put("killStreakBest", data.getKillStreakBest());
			map.put("damageDoneMostInGame", data.getDamageDoneMostInGame());
			map.put("eliminationsMostInGame", data.getEliminationsMostInGame());
			map.put("finalBlowsMostInGame", data.getFinalBlowsMostInGame());
			map.put("objectiveKillsMostInGame", data.getObjectiveKillsMostInGame());
			map.put("objectiveTimeMostInGame", data.getObjectiveTimeMostInGame());
			map.put("soloKillsMostInGame", data.getSoloKillsMostInGame());
			map.put("offensiveAssistsMostInGame", data.getOffensiveAssistsMostInGame());
			map.put("criticalHitsMostInGame", data.getCriticalHitsMostInGame());
			map.put("criticalHitsMostInLife", data.getCriticalHitsMostInLife());
			map.put("selfHealingMostInGame", data.getSelfHealingMostInGame());
			map.put("meleeFinalBlowsMostInGame", data.getMeleeFinalBlowsMostInGame());
			map.put("damageBlockedMostInGame", data.getDamageBlockedMostInGame());
			map.put("mostOrder", data.getMostOrder());
			map.put("stat1", data.getStat1());
			map.put("stat2", data.getStat2());
			map.put("stat3", data.getStat3());
			map.put("stat4", data.getStat4());
			map.put("stat5", data.getStat5());
			map.put("stat6", data.getStat6());
			map.put("stat7", data.getStat7());
			map.put("stat8", data.getStat8());
			map.put("stat9", data.getStat9());
			map.put("stat10", data.getStat10());
			map.put("stat11", data.getStat11());
			map.put("stat12", data.getStat12());
			map.put("stat13", data.getStat13());
			map.put("stat14", data.getStat14());
			map.put("stat15", data.getStat15());
			map.put("stat16", data.getStat16());
			map.put("stat17", data.getStat17());
			map.put("stat18", data.getStat18());
			map.put("stat19", data.getStat19());
			map.put("stat20", data.getStat20());
			map.put("oprt", "admin");
							
			mapper.insertUserHeroes(map);
		}
	}

	public int putSyncWlist() {
		return mapper.insertSyncWlist();
	}
}
