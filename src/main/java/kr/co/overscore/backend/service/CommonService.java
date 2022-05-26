package kr.co.overscore.backend.service;

import java.util.List;

import kr.co.overscore.backend.model.SyncWListModel;
import kr.co.overscore.backend.model.UserModel;
import kr.co.overscore.backend.persistence.CommonMapper;

public class CommonService {
	public List<UserModel> getUser() {
		return new CommonMapper().selectUser();
	}

	public List<SyncWListModel> selectSyncItem(int selectCount) {
		return new CommonMapper().selectSyncWlist(selectCount);
	}
	
	public List<SyncWListModel> selectPriorItem(int selectCount) {
		return new CommonMapper().selectPriorWlist(selectCount);
	}
	
	public List<SyncWListModel> selectSearchItem(int selectCount) {
		return new CommonMapper().selectSearchWlist(selectCount);
	}

	public int updateSyncWListStatus(SyncWListModel data, String status,
			String preStatus, String oprt) {
		return new CommonMapper().updateSyncWListStatus(data, status,
				preStatus, oprt);
	}
	
	public int updatePriorWListStatus(SyncWListModel data, String status,
			String preStatus, String oprt) {
		return new CommonMapper().updatePriorWListStatus(data, status,
				preStatus, oprt);
	}
	
	public int updateSearchWListStatus(SyncWListModel data, String status,
			String preStatus, String oprt) {
		return new CommonMapper().updateSearchWListStatus(data, status,
				preStatus, oprt);
	}
}
