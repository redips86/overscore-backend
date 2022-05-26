package kr.co.overscore.backend.service;

import java.util.HashMap;
import java.util.List;

import kr.co.overscore.backend.model.UserModel;
import kr.co.overscore.backend.persistence.DeleteMapper;

public class DeleteService {
	public List<UserModel> getOldUserList() {
		return new DeleteMapper().selectOldUserList();
	}
	
	public int removeOldUser(int userId) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		
		return new DeleteMapper().deleteOldUser(params);
	}
}
