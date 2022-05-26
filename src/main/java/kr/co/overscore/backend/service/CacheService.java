package kr.co.overscore.backend.service;

import java.util.HashMap;
import java.util.List;

import kr.co.overscore.backend.model.AchvModel;
import kr.co.overscore.backend.model.GameTypeModel;
import kr.co.overscore.backend.model.HeroesModel;
import kr.co.overscore.backend.model.HeroesStatModel;
import kr.co.overscore.backend.model.ImageModel;
import kr.co.overscore.backend.model.MetaModel;
import kr.co.overscore.backend.model.StatModel;
import kr.co.overscore.backend.model.UserModel;
import kr.co.overscore.backend.persistence.CacheMapper;

public class CacheService {
	
	CacheMapper cacheMapper = new CacheMapper();
	
	public List<GameTypeModel> getGametype() {
		List<GameTypeModel> dataList = cacheMapper.selectGameType();
		return dataList;
	}
	
	public void removeHeroes() {

	}
	
	public List<HeroesStatModel> getHeroesStat() {
		List<HeroesStatModel> dataList = cacheMapper.selectHeroesStat();
		return dataList;
	}
	
	public List<HeroesModel> getHeroes(){
		List<HeroesModel> dataList = cacheMapper.selectHeroes();
		return dataList;
	}
	
	public HeroesModel putHeroes(String heroesName){
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("heroesName", heroesName);
		params.put("oprt", "admin");
		return cacheMapper.insertHeroes(params);
	}
}
