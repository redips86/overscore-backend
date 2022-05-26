package kr.co.overscore.backend.model;

import java.util.List;

public class UserModel {
	int	userId;
	String	userName;
	String	userTag;
	String	server;
	String	rankImage;
	String	avatar;
	String	tier;
	int		level;
	int		rank;
	List<UserAchvModel> userAchv;
	List<UserStatModel> userStat;
	List<UserHeroesModel> userHeroes;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserTag() {
		return userTag;
	}

	public void setUserTag(String userTag) {
		this.userTag = userTag;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getRankImage() {
		return rankImage;
	}

	public void setRankImage(String rankImage) {
		this.rankImage = rankImage;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getTier() {
		return tier;
	}

	public void setTier(String tier) {
		this.tier = tier;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public List<UserAchvModel> getUserAchv() {
		return userAchv;
	}

	public void setUserAchv(List<UserAchvModel> userAchv) {
		this.userAchv = userAchv;
	}

	public List<UserStatModel> getUserStat() {
		return userStat;
	}

	public void setUserStat(List<UserStatModel> userStat) {
		this.userStat = userStat;
	}

	public List<UserHeroesModel> getUserHeroes() {
		return userHeroes;
	}

	public void setUserHeroes(List<UserHeroesModel> userHeroes) {
		this.userHeroes = userHeroes;
	}

}
