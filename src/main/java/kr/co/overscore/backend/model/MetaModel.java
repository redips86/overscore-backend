package kr.co.overscore.backend.model;

import java.util.List;

public class MetaModel {
	List<AchvModel> achv;
	List<GameTypeModel> gameType;
	List<StatModel> stat;
	List<StatGroupModel> statGroup;
	List<HeroesModel> heroes;

	public List<AchvModel> getAchv() {
		return achv;
	}

	public void setAchv(List<AchvModel> achv) {
		this.achv = achv;
	}

	public List<StatModel> getStat() {
		return stat;
	}

	public void setStat(List<StatModel> stat) {
		this.stat = stat;
	}

	public List<StatGroupModel> getStatGroup() {
		return statGroup;
	}

	public void setStatGroup(List<StatGroupModel> statGroup) {
		this.statGroup = statGroup;
	}

	public List<HeroesModel> getHeroes() {
		return heroes;
	}

	public void setHeroes(List<HeroesModel> heroes) {
		this.heroes = heroes;
	}

	public List<GameTypeModel> getGameType() {
		return gameType;
	}

	public void setGameType(List<GameTypeModel> gameType) {
		this.gameType = gameType;
	}

}
