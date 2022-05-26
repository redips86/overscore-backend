package kr.co.overscore.backend.persistence;

import java.util.HashMap;
import java.util.List;

import kr.co.overscore.backend.model.HeroesModel;
import kr.co.overscore.backend.model.UserModel;

import com.prismweaver.backend.dao.AbstractDao;

public class DeleteMapper extends AbstractDao {

	public List<UserModel> selectOldUserList() {
		return this.getDBUtils().selectList("deleteMapper.selectOldUserList");
	}

	public int deleteOldUser(HashMap<String, Object> dataList) {
		return this.getDBUtils().update("deleteMapper.deleteOldUser", dataList);
	}

}
