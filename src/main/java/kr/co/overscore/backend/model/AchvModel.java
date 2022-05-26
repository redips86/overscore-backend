package kr.co.overscore.backend.model;

import java.util.List;

public class AchvModel {
	String achvId;
	String achvName;
	String achvEng;
	String achvKor;
	List<AchvDetailModel> achvDetail;

	public String getAchvId() {
		return achvId;
	}

	public void setAchvId(String achvId) {
		this.achvId = achvId;
	}

	public String getAchvName() {
		return achvName;
	}

	public void setAchvName(String achvName) {
		this.achvName = achvName;
	}

	public String getAchvEng() {
		return achvEng;
	}

	public void setAchvEng(String achvEng) {
		this.achvEng = achvEng;
	}

	public String getAchvKor() {
		return achvKor;
	}

	public void setAchvKor(String achvKor) {
		this.achvKor = achvKor;
	}

	public List<AchvDetailModel> getAchvDetail() {
		return achvDetail;
	}

	public void setAchvDetail(List<AchvDetailModel> achvDetail) {
		this.achvDetail = achvDetail;
	}


}
