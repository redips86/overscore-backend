package kr.co.overscore.backend.model;

public class UserAchvModel {
	String userId;
	String achvId;
	String achvDetailId;
	boolean value;
	
	public UserAchvModel(String userId, String achvId, String achvDetailId, boolean value){
		this.setUserId(userId);
		this.setAchvId(achvId);
		this.setAchvDetailId(achvDetailId);
		this.setValue(value);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAchvId() {
		return achvId;
	}

	public void setAchvId(String achvId) {
		this.achvId = achvId;
	}

	public String getAchvDetailId() {
		return achvDetailId;
	}

	public void setAchvDetailId(String achvDetailId) {
		this.achvDetailId = achvDetailId;
	}

	public boolean isValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}
}
