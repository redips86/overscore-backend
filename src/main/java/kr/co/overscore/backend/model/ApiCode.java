package kr.co.overscore.backend.model;

public enum ApiCode {
	API_1(1), API_2(2), API_SEARCH(3);

	private int value;

	ApiCode(int value) {
		this.value = value;
	}
	 
	public int getValue(){
		return value;
	}
}