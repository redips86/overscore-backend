package kr.co.overscore.backend.persistence;

import java.util.HashMap;
import java.util.List;

import kr.co.overscore.backend.model.SyncWListModel;
import kr.co.overscore.backend.model.UserModel;

import com.prismweaver.backend.dao.AbstractDao;

public class CommonMapper extends AbstractDao {
	public List<UserModel> selectUser() {
		return this.getDBUtils().selectList("commonMapper.selectUser");
	}

	public List<SyncWListModel> selectSyncWlist(int selectCount) {
		HashMap<String, Object> params = new HashMap<String, Object>();

		params.put("selectCount", selectCount);
		
		List<SyncWListModel> data = this.getDBUtils().selectList("commonMapper.selectSyncWlist", params); 
		return  data;
	}
	
	public List<SyncWListModel> selectPriorWlist(int selectCount) {
		HashMap<String, Object> params = new HashMap<String, Object>();

		params.put("selectCount", selectCount);
		
		List<SyncWListModel> data = this.getDBUtils().selectList("commonMapper.selectPriorWlist", params); 
		return  data;
	}
	
	public List<SyncWListModel> selectSearchWlist(int selectCount) {
		HashMap<String, Object> params = new HashMap<String, Object>();

		params.put("selectCount", selectCount);
		
		List<SyncWListModel> data = this.getDBUtils().selectList("commonMapper.selectSearchWlist", params); 
		return  data;
	}

	public int updateSyncWListStatus(SyncWListModel data, String status,
			String preStatus, String oprt) {
		int result = 0;
		
		HashMap<String, Object> params = new HashMap<String, Object>();				
		
		params.put("syncId", data.getSyncId());
		params.put("status", status);
		params.put("preStatus", preStatus);
		params.put("oprt", oprt);
		
		result = this.getDBUtils().update("commonMapper.updateSyncWListStatus", params);
		
		return result;
	}
	
	public int updatePriorWListStatus(SyncWListModel data, String status,
			String preStatus, String oprt) {
		int result = 0;
		
		HashMap<String, Object> params = new HashMap<String, Object>();				
		
		params.put("syncId", data.getSyncId());
		params.put("status", status);
		params.put("preStatus", preStatus);
		params.put("oprt", oprt);
		
		result = this.getDBUtils().update("commonMapper.updatePriorWListStatus", params);
		
		return result;
	}
	
	public int updateSearchWListStatus(SyncWListModel data, String status,
			String preStatus, String oprt) {
		int result = 0;
		
		HashMap<String, Object> params = new HashMap<String, Object>();				
		
		params.put("syncId", data.getSyncId());
		params.put("status", status);
		params.put("preStatus", preStatus);
		params.put("oprt", oprt);
		
		result = this.getDBUtils().update("commonMapper.updateSearchWListStatus", params);
		
		return result;
	}
}
