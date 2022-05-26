package kr.co.overscore.backend.model;

public class NoticeModel {
	int		noticeId;
	String	noticeType;
	String	noticeTitle;
	String	noticeContent;
	String	insDate;
	int		noticeTimer;
	
	public int getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(int noticeId) {
		this.noticeId = noticeId;
	}
	public String getNoticeType() {
		return noticeType;
	}
	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}
	public String getNoticeTitle() {
		return noticeTitle;
	}
	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}
	public String getNoticeContent() {
		return noticeContent;
	}
	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}
	public String getInsDate() {
		return insDate;
	}
	public void setInsDate(String insDate) {
		this.insDate = insDate;
	}
	public int getNoticeTimer() {
		return noticeTimer;
	}
	public void setNoticeTimer(int noticeTimer) {
		this.noticeTimer = noticeTimer;
	}
}
