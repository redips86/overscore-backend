package kr.co.overscore.backend.scrap;

import kr.co.overscore.backend.model.ApiCode;
import kr.co.overscore.backend.scrap.api.ApiScraperMain2;
import kr.co.overscore.backend.scrap.api.ApiScraperSearch;

public class ApiScraperFactory {
	public static Scraper GetScrapHandler(ApiCode apiCode) {

		Scraper scraper = null;

		switch (apiCode) {
		case API_1:
			//scraper = new ApiScraperMain();
			break;
			
		case API_2:
			scraper = new ApiScraperMain2();
			break;
			
		case API_SEARCH:
			scraper = new ApiScraperSearch();
			break;
			
		default:
			break;
		}

 		return scraper;
	}
}
