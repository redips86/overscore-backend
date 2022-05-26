package kr.co.overscore.backend.model;

import java.util.List;

public class MainTopModel {
	int heroesId;
	int typeId;
	String heroesImg;
	String heroesType;
	List<MainTopHeroesModel> mainTopHeroesModel;

	public int getHeroesId() {
		return heroesId;
	}

	public void setHeroesId(int heroesId) {
		this.heroesId = heroesId;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getHeroesImg() {
		return heroesImg;
	}

	public void setHeroesImg(String heroesImg) {
		this.heroesImg = heroesImg;
	}

	public List<MainTopHeroesModel> getMainTopHeroesModel() {
		return mainTopHeroesModel;
	}

	public void setMainTopHeroesModel(
			List<MainTopHeroesModel> mainTopHeroesModel) {
		this.mainTopHeroesModel = mainTopHeroesModel;
	}

	public String getHeroesType() {
		return heroesType;
	}

	public void setHeroesType(String heroesType) {
		this.heroesType = heroesType;
	}
	
}
