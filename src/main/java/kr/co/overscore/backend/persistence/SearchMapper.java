package kr.co.overscore.backend.persistence;

import java.util.HashMap;
import java.util.List;

import kr.co.overscore.backend.model.AllModel;
import kr.co.overscore.backend.model.UserModel;

import com.prismweaver.backend.dao.AbstractDao;

public class SearchMapper extends AbstractDao{

	public void deleteSearchModel(String userName) {
		this.getDBUtils().delete("syncMapper.deleteSearchModel", userName);
	}
	
	public int insertSearchModel(HashMap<String, Object> map) {
		return this.getDBUtils().insert("syncMapper.insertSearchModel", map);
	}
	
}