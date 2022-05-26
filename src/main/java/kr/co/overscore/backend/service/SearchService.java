package kr.co.overscore.backend.service;

import java.util.HashMap;

import kr.co.overscore.backend.model.AllModel;
import kr.co.overscore.backend.persistence.SearchMapper;

public class SearchService {
	SearchMapper mapper = new SearchMapper();
	
	public void deleteSearchModel(String userName) {
		mapper.deleteSearchModel(userName);
	}
	
	public int insertSearchModel(AllModel allModel) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		map.put("userName", allModel.getUserModel().getUserName());
		map.put("userTag", allModel.getUserModel().getUserTag());
		map.put("server", allModel.getUserModel().getServer());
		map.put("avatar", allModel.getUserModel().getAvatar());
		map.put("level", String.valueOf(allModel.getUserModel().getLevel()));
		map.put("oprt", "admin");
		
		return mapper.insertSearchModel(map);
	}
}
