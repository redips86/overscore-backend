package kr.co.overscore.backend.scrap.api;
/*package kr.co.overscore.scrap.api;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import kr.co.overscore.model.AchvDetailModel;
import kr.co.overscore.model.AchvModel;
import kr.co.overscore.model.AllModel;
import kr.co.overscore.model.ApiCode;
import kr.co.overscore.model.GameTypeModel;
import kr.co.overscore.model.HeroesModel;
import kr.co.overscore.model.IMetaModel;
import kr.co.overscore.model.MetaModel;
import kr.co.overscore.model.StatGroupModel;
import kr.co.overscore.model.StatModel;
import kr.co.overscore.model.UserAchvModel;
import kr.co.overscore.model.UserHeroesModel;
import kr.co.overscore.model.UserModel;
import kr.co.overscore.model.UserStatModel;
import kr.co.overscore.scrap.AbstractScraper;
import kr.co.overscore.scrap.Scraper;
import kr.co.overscore.service.CacheService;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

public class ApiScraperMain extends AbstractScraper implements Scraper {
	private static String baseUrl = "https://owapi.net/api/v3/u/%s/blob";
	private MetaModel meta;
	CacheService cacheService;

	public ApiScraperMain() {
		super(ApiCode.API_1);
	}

	@Override
	public List<AllModel> extract(String battleTag, CacheService cacheService) throws Exception {
		String url = String.format(baseUrl, URLEncoder.encode(battleTag, "UTF-8"));
		
		this.meta = cacheService.getMeta();

		List<AllModel> result = new ArrayList<AllModel>();

		String jsonString = scrapJson(url);
//		jsonString = jsonString	.replaceAll("cards", "card")
//					.replaceAll("blows", "blow")
//					.replaceAll("kills", "kill")
//					.replaceAll("deaths", "death")
//					.replaceAll("average", "avg")
//					.replaceAll("packs", "pack")
//					.replaceAll("fields", "field")
//					.replaceAll("hits", "hit")
//					.replaceAll("games", "game")
//					.replaceAll("shields", "shield")
//					.replaceAll("shots", "shot")
//					.replaceAll("barriers", "barrier")
//					.replaceAll("turrets", "turret")
//					.replaceAll("eliminations", "elimination");
		
		JSONObject jsonObject = JSONObject.fromObject(JSONSerializer.toJSON(jsonString));
		
		// Check Meta
		checkMeta(jsonObject);
		
		// Upsert Data
		Iterator<?> servers = jsonObject.keys();
		String server = "";
		while(servers.hasNext()){
			server = (String)servers.next();
			AllModel allModel = getAllModel(jsonObject, server, battleTag);
			result.add(allModel);
		}
		
		return result;
	}
	
	private void checkMeta(JSONObject jsonObject){
		// Level0 : servers
		Iterator<?> servers = jsonObject.keys();
		String server = "";
		while(servers.hasNext()){
			server = (String) servers.next();
			JSONObject jsonServer = jsonObject.getJSONObject(server);
			if(jsonServer.isNullObject())
				continue;
			
			// achievements
			JSONObject jsonAchv = jsonServer.getJSONObject("achievements");
			checkMetaCommon(jsonAchv ,changeIMetaModel(meta.getAchv()),new IInserter(){
				@Override
				public void insert(String key) {
					cacheService.insertAchv(key,key,key);
				}
			});
			
			// achievements > Details
			for(final AchvModel am : meta.getAchv()){
				JSONObject jsonAchvDetail = jsonAchv.getJSONObject(am.getAchvName());
				if(jsonAchvDetail.isNullObject()) continue;
				
				checkMetaCommon(jsonAchvDetail, changeIMetaModel(am.getAchvDetail()),new IInserter(){
					@Override
					public void insert(String key) {
						cacheService.insertAchvDetail(am.getAchvId(), key, key, key);
					}
				});
			}
			
			// heroes > stats, playtime > gameType
			JSONObject jsonHeroes = jsonServer.getJSONObject("heroes"); // heroes
			
			Iterator<?> heroesKeys = jsonHeroes.keys();
			String heroesKey = "";
			while(heroesKeys.hasNext()){
				heroesKey = (String)heroesKeys.next();
				
				JSONObject jsonGameType = jsonHeroes.getJSONObject(heroesKey); // stats, playtime
				
				checkMetaCommon(jsonGameType,changeIMetaModel(meta.getGameType()),new IInserter(){
					@Override
					public void insert(String key) {
						cacheService.insertGameType(key, key, key);
					}
				});
				
				Iterator<?> gameTypeKeys = jsonGameType.keys();
				String gameTypeKey = "";
				while(gameTypeKeys.hasNext()){
					gameTypeKey = (String)gameTypeKeys.next();
					
					JSONObject jsonH = jsonGameType.getJSONObject(gameTypeKey); // quickplay, competitive
					
					if(jsonH.isNullObject())
						continue;
					
					checkMetaCommon(jsonH,changeIMetaModel(meta.getHeroes()),new IInserter(){
						@Override
						public void insert(String key) {
							//cacheService.insertHeroes(key, key, key, key);
						}
					});
					
					if("stats".equals(heroesKey)){
						Iterator<?> hKeys = jsonH.keys();
						String hKey = "";
						
						while(hKeys.hasNext()){
							hKey = (String)hKeys.next();
							JSONObject jsonStatGroup = jsonH.getJSONObject(hKey); // heroesName...
							
							if(jsonStatGroup.isNullObject())
								continue;
							
							checkMetaCommon(jsonStatGroup, changeIMetaModel(meta.getStatGroup()), new IInserter(){
								@Override
								public void insert(String key) {
									cacheService.insertStatGroup(key, key, key);
								}
							});
							
							Iterator<?> groupKeys = jsonStatGroup.keys();
							String groupKey = "";
							
							while(groupKeys.hasNext()){
								groupKey = (String) groupKeys.next();
								JSONObject jsonStats = jsonStatGroup.getJSONObject(groupKey);
								if(jsonStats.isNullObject())
									continue;
								
								checkMetaCommon(jsonStats, changeIMetaModel(meta.getStat()), new IInserter(){
									@Override
									public void insert(String key) {
										//cacheService.insertStat(key, key, key);
									}
								});
							}
						}
					}
				}
			}
			
			// Level1 : stats
			JSONObject jsonStats = jsonServer.getJSONObject("stats");
			checkMetaCommon(jsonStats, changeIMetaModel(meta.getGameType()), new IInserter(){
				@Override
				public void insert(String key) {
					cacheService.insertGameType(key, key, key);
				}
			});
			
			Iterator<?> statsKeys = jsonStats.keys();
			String statsKey = "";
			while(statsKeys.hasNext()){
				statsKey = (String)statsKeys.next(); //quickplay, competitive...
				
				JSONObject jsonGameType = jsonStats.getJSONObject(statsKey);
				if(jsonGameType != null && jsonGameType.isNullObject()) continue;
				
				checkMetaCommon(jsonGameType, changeIMetaModel(meta.getStatGroup()), new IInserter(){
					@Override
					public void insert(String key) {
						cacheService.insertStatGroup(key, key, key);
					}
				});
				
				Iterator<?> typeKeys = jsonGameType.keys();
				String typeKey = "";
				while(typeKeys.hasNext()){
					typeKey = (String)typeKeys.next(); // overall_stats, game_stats...
					if("competitive".equals(typeKey))
						continue;
					
					JSONObject jsonGroup = jsonGameType.getJSONObject(typeKey);
					if(jsonGroup.isNullObject())
						continue;
					
					checkMetaCommon(jsonGroup, changeIMetaModel(meta.getStat()), new IInserter(){
						@Override
						public void insert(String key) {
							//cacheService.insertStat(key, key, key);
						}
					});
				}
			}
		}
	}
	
	private List<IMetaModel> changeIMetaModel(List<?> listMeta){
		List<IMetaModel> rtn = new ArrayList<IMetaModel>();
		for(int i = 0 ; i < listMeta.size() ; i ++){
			rtn.add((IMetaModel)listMeta.get(i));
		}
		
		return rtn;
	}
	
	private void checkMetaCommon(JSONObject jsonMeta, List<IMetaModel> listMeta, IInserter inserter){
		Iterator<?> keys = jsonMeta.keys();
		String key = "";
		
		boolean isChanged = false;
		while(keys.hasNext()){
			key = (String)keys.next();
			
			boolean isExist = false;
			for(IMetaModel m : listMeta){
				if(m.getMetaKey().equals(key)){
					isExist = true;
					break;
				}
			}
			if(!isExist){
				isChanged = true;
				inserter.insert(key);
			}
		}
		if(isChanged){
			cacheService.removeMeta();
			meta = cacheService.getMeta();
		}
	}
	
	private void checkMetaAchv(JSONObject jsonAchv){
		Iterator<?> achvKeys = jsonAchv.keys();
		String achvKey = "";
		
		// check Meta : achv
		boolean isChanged = false;
		while(achvKeys.hasNext()){
			achvKey = (String)achvKeys.next();
			
			boolean isExist = false;
			for(AchvModel am : meta.getAchv()){
				if(am.getAchvName().equals(achvKey)){
					isExist = true;
					break;
				}
			}
			if(!isExist){
				isChanged = true;
				cacheService.insertAchv(achvKey,achvKey,achvKey);
			}
		}
		if(isChanged){
			cacheService.removeMeta();
			meta = cacheService.getMeta();
		}
		
		// Level2 : achievement detail
		isChanged = false;
		for(AchvModel am : meta.getAchv()){
			String achvId = am.getAchvId();
			
			JSONObject jsonAchvDetail = jsonAchv.getJSONObject(am.getAchvName());
			if(jsonAchvDetail.isNullObject())
				continue;
			
			Iterator<?> achvDetailKeys = jsonAchvDetail.keys();
			String achvDetailKey = "";
			while(achvDetailKeys.hasNext()){
				achvDetailKey = (String)achvDetailKeys.next();
				boolean isExist = false;
				for(AchvDetailModel adm : am.getAchvDetail()){
					if(adm.getAchvDetailName().equals(achvDetailKey)){
						isExist = true;
						break;
					}
				}
				if(!isExist){
					isChanged = true;
					cacheService.insertAchvDetail(achvId, achvDetailKey, achvDetailKey, achvDetailKey);
				}
			}
		}
		if(isChanged){
			cacheService.removeMeta();
			meta = cacheService.getMeta();
		}
	}
	
	private void checkMetaHeroes(JSONObject jsonHeroes){
		Iterator<?> heroesKeys = jsonHeroes.keys();
		String achvKey = "";
		
		boolean isChanged = false;
		while(heroesKeys.hasNext()){
			achvKey = (String)heroesKeys.next();
			
			
		}
		
	}

	public AllModel getAllModel(JSONObject jsonObject, String server, String battleTag) {
		AllModel allModel = new AllModel();
		allModel.setServer(server);
		JSONObject rootObject = jsonObject.getJSONObject(server);

		if (!rootObject.isNullObject()) {
			UserModel userModel = new UserModel();
			userModel.setUserName(battleTag.split("-")[0]);
			userModel.setUserTag(battleTag.split("-")[1]);
			userModel.setServer(server);
			
			JSONObject overall = rootObject.getJSONObject("stats").getJSONObject("competitive");
			if(overall != null && overall.isNullObject()){
				overall = rootObject.getJSONObject("stats").getJSONObject("quickplay");
				if(overall != null && overall.isNullObject()){
					userModel.setRankImage("");
					userModel.setAvatar("");
					userModel.setTier("");
					userModel.setLevel(1);
					userModel.setRank(0);
					
					allModel.setUserModel(userModel);
					
					return allModel;
				}
			}
			
			overall = overall.getJSONObject("overall_stats");
			if(overall != null && overall.isNullObject()) return allModel;
			
			userModel.setRankImage(overall.getString("rank_image").equals("null") ? "":overall.getString("rank_image"));
			userModel.setAvatar(overall.getString("avatar"));
			userModel.setTier(overall.getString("tier").equals("null") ? "unranked":overall.getString("tier"));
			userModel.setLevel(Integer.parseInt(overall.getString("prestige")+overall.getString("level")));
			userModel.setRank(overall.getString("comprank").equals("null") ? 0 : overall.getInt("comprank"));

			userModel.setUserAchv(getUserAchvModel(rootObject));
			userModel.setUserStat(getUserStatModel(rootObject));
			userModel.setUserHeroes(getUserHeroesModel(rootObject));

			allModel.setUserModel(userModel);

		}
		return allModel;
	}

	private List<UserAchvModel> getUserAchvModel(JSONObject jsonObject) {
		List<UserAchvModel> listUserAchv = new ArrayList<UserAchvModel>();

		JSONObject achievements = jsonObject.getJSONObject("achievements");

		for (AchvModel achv : this.meta.getAchv()) {
			JSONObject jsonAchv = achievements.getJSONObject(achv.getAchvName());
			String achvId = achv.getAchvId();

			for (AchvDetailModel achvDetail : achv.getAchvDetail()) {
				String key = achvDetail.getAchvDetailName();
				boolean value = jsonAchv.getBoolean(key);
				listUserAchv.add(new UserAchvModel("", achvId, achvDetail.getAchvDetailId(), value));
			}
		}

		return listUserAchv;
	}

	private List<UserStatModel> getUserStatModel(JSONObject jsonObject) {
		List<UserStatModel> listUserStat = new ArrayList<UserStatModel>();

		JSONObject stats = jsonObject.getJSONObject("stats");
		if(stats != null && stats.isNullObject()) return listUserStat;

		for (GameTypeModel gameType : this.meta.getGameType()) {
			JSONObject jsonGameType;
			
			jsonGameType = stats.getJSONObject(gameType.getTypeEng());
			if(jsonGameType != null && jsonGameType.isNullObject()) continue;
			
			String gameTypeId = gameType.getTypeId();

			for (StatGroupModel statGroup : meta.getStatGroup()) {
				JSONObject jsonStatGroup;
				
				try{
					jsonStatGroup = jsonGameType.getJSONObject(statGroup.getGroupEng());
					if(jsonStatGroup != null && jsonStatGroup.isNullObject()) continue;
				}
				catch(Exception e){
					continue;
				}
				
				
				String statGorupId = statGroup.getGroupId();

				for (StatModel stat : meta.getStat()) {
					float value = 0;
					
					try {
						value = Float.parseFloat(jsonStatGroup.getString(stat.getStatEng()));
					} catch (Exception e) {
						continue;
					}

					UserStatModel userStat = new UserStatModel();
					userStat.setTypeId(gameTypeId);
					userStat.setGroupId(statGorupId);
					userStat.setStatId(stat.getStatId());
					userStat.setValue(value);
					listUserStat.add(userStat);
				}
			}
		}

		return listUserStat;
	}

	private List<UserHeroesModel> getUserHeroesModel(JSONObject jsonObject) {
		List<UserHeroesModel> listUserHeroes = new ArrayList<UserHeroesModel>();

		JSONObject heroes = jsonObject.getJSONObject("heroes");
		if(heroes != null && heroes.isNullObject()) return listUserHeroes;
		
		JSONObject playtime = heroes.getJSONObject("playtime");
		if(playtime != null && playtime.isNullObject()) return listUserHeroes;
		
		JSONObject stats = heroes.getJSONObject("stats");
		if(stats != null && stats.isNullObject()) return listUserHeroes;

		for (GameTypeModel gameType : this.meta.getGameType()) {
			String gameTypeId = gameType.getTypeId();

			JSONObject stats_gameType = stats.getJSONObject(gameType.getTypeEng());
			if(stats_gameType != null && stats_gameType.isNullObject()) continue;
			
			JSONObject playtime_gameType = playtime.getJSONObject(gameType.getTypeEng());
			if(playtime_gameType != null && playtime_gameType.isNullObject()) continue;
			
			String playTimeId = "";
			String generalGroupId = "";
			for (HeroesModel hero : this.meta.getHeroes()) {
				String heroesId = hero.getHeroesId();
				JSONObject stats_Hero;
				
				stats_Hero = stats_gameType.getJSONObject(hero.getHeroesName());
				if(stats_Hero != null && stats_Hero.isNullObject()) continue;

				for (StatGroupModel statGroup : this.meta.getStatGroup()) {
					String statGroupId = statGroup.getGroupId();
					JSONObject stats_group = stats_Hero.getJSONObject(statGroup.getGroupEng());
					if(stats_group != null && stats_group.isNullObject()) continue;
					
					if (generalGroupId.equals("") && statGroup.getGroupEng().equals("general_stats")) {
						generalGroupId = statGroupId;
					}

					for (StatModel stat : this.meta.getStat()) {
						String statId = stat.getStatId();

						if (playTimeId.equals("") && stat.getStatEng().equals("playtime")) {
							playTimeId = stat.getStatId();
							continue;
						}

						float value;
						try {
							value = Float.parseFloat(stats_group.getString(stat.getStatEng()));
						} catch (Exception e) {
							continue;
						}

						UserHeroesModel userHeroes = new UserHeroesModel();
						userHeroes.setTypeId(gameTypeId);
						userHeroes.setGroupId(statGroupId);
						userHeroes.setStatId(statId);
						userHeroes.setValue(value);
						userHeroes.setHeroesId(heroesId);
						listUserHeroes.add(userHeroes);
					}
				}
			}
			
			for (HeroesModel hero : this.meta.getHeroes()) {
				String heroesId = hero.getHeroesId();
				float value = 0;
				try{
					value = Float.parseFloat(playtime_gameType.getString(hero.getHeroesName()));
				}
				catch (Exception e){
					continue;
				}
				
				UserHeroesModel userHero = new UserHeroesModel();
				userHero.setTypeId(gameTypeId);
				userHero.setGroupId(generalGroupId);
				userHero.setHeroesId(heroesId);
				userHero.setStatId(playTimeId);
				userHero.setValue(value);
				listUserHeroes.add(userHero);
			}
		}

		return listUserHeroes;
	}

	interface IInserter{
		public void insert(String key);
	}
}
*/