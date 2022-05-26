package kr.co.overscore.backend.persistence;

import java.util.HashMap;
import java.util.List;

import kr.co.overscore.backend.model.AchvDetailModel;
import kr.co.overscore.backend.model.AchvModel;
import kr.co.overscore.backend.model.GameTypeModel;
import kr.co.overscore.backend.model.HeroesModel;
import kr.co.overscore.backend.model.HeroesStatModel;
import kr.co.overscore.backend.model.ImageModel;
import kr.co.overscore.backend.model.StatGroupModel;
import kr.co.overscore.backend.model.StatModel;
import kr.co.overscore.backend.model.UserModel;

import org.apache.ibatis.annotations.Param;

import com.prismweaver.backend.dao.AbstractDao;

public class CacheMapper extends AbstractDao{
	
	public List<GameTypeModel> selectGameType() {
		return this.getDBUtils().selectList("cacheMapper.selectGameType");
	}
	
	public List<HeroesStatModel> selectHeroesStat() {
		return this.getDBUtils().selectList("cacheMapper.selectHeroesStat");
	}
	
	public List<HeroesModel> selectHeroes() {
		return this.getDBUtils().selectList("cacheMapper.selectHeroes");
	}
	
	public HeroesModel insertHeroes(HashMap<String, Object> dataList) {
		return this.getDBUtils().selectOne("syncMapper.insertUserHeroes", dataList);
	}

}