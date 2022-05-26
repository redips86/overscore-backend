package kr.co.overscore.backend.persistence;

import java.util.HashMap;
import java.util.List;

import kr.co.overscore.backend.model.AllModel;
import kr.co.overscore.backend.model.UserModel;

import com.prismweaver.backend.dao.AbstractDao;

public class SyncMapper extends AbstractDao{
	
	public void upsertAllModel(HashMap<String, String> map) {
		this.getDBUtils().update("syncMapper.upsertAllModel", map);
	}
	
	public UserModel upsertUser(HashMap<String, Object> dataList) {
		return this.getDBUtils().selectOne("syncMapper.upsertUser", dataList);
	}
	
	public void insertUserStat(HashMap<String, Object> dataList) {
		this.getDBUtils().insert("syncMapper.insertUserStat", dataList);
	}
	
	public void deleteUserStat(int userId) {
		this.getDBUtils().delete("syncMapper.deleteUserStat", userId);
	}
	
	public void insertUserHeroes(HashMap<String, Object> dataList) {
		this.getDBUtils().insert("syncMapper.insertUserHeroes", dataList);
	}
	
	public void deleteUserHeroes(int userId) {
		this.getDBUtils().delete("syncMapper.deleteUserHeroes", userId);
	}

	public int insertSyncWlist() {
		return this.getDBUtils().insert("syncMapper.insertSyncWlist");
	}
	
	
	/*public List<AllModel> selectApiList(@Param("battleTag") String battleTag);

	public void upsertApiList(List<AllModel> dataList);

	public List<MainTopModel> selectMainTopHeroes();

	public UserModel upsertUser(HashMap<String, Object> map);

	public void insertUserStat(HashMap<String, Object> map);
	
	public void insertUserHeroes(HashMap<String, Object> map);
	
	public List<NoticeModel> getNoticeList();
	
	public NoticeModel getNotice(int noticeId);
	
	public List<NoticeModel> getNoticeRecent(int recentDay);
*/}