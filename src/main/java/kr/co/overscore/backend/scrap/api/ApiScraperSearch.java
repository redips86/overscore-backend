package kr.co.overscore.backend.scrap.api;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import kr.co.overscore.backend.model.AllModel;
import kr.co.overscore.backend.model.GameTypeModel;
import kr.co.overscore.backend.model.HeroesModel;
import kr.co.overscore.backend.model.HeroesStatModel;
import kr.co.overscore.backend.model.UserAchvModel;
import kr.co.overscore.backend.model.UserHeroesModel;
import kr.co.overscore.backend.model.UserModel;
import kr.co.overscore.backend.model.UserStatModel;
import kr.co.overscore.backend.scrap.AbstractScraper;
import kr.co.overscore.backend.scrap.Scraper;
import kr.co.overscore.backend.service.CacheService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ApiScraperSearch extends AbstractScraper implements Scraper {
	String baseUrl = "https://playoverwatch.com/search?q=";

	public List<AllModel> extract(String userName) throws Exception {

		List<AllModel> allList = new ArrayList<AllModel>();
		
		String baseUrl = "https://playoverwatch.com/en-us";
		String searchUrl = "/search/account-by-name/";
		String jsonString = scrapJson(baseUrl.concat(searchUrl).concat(URLEncoder.encode(userName, "UTF-8").replaceAll("\\+", "%20")));

		JSONArray urlArray = JSONArray.fromObject(JSONSerializer.toJSON(jsonString));
		
		for (int i = 0; i < urlArray.size(); i++) {
			AllModel allModel = new AllModel();
			
			JSONObject urlObject = urlArray.getJSONObject(i);
			String battleTags[] = urlObject.getString("platformDisplayName").split("#");
			String server = urlObject.getString("platform");
			String avatar = urlObject.getString("portrait");
			String level = urlObject.getString("level");
			
			UserModel user = new UserModel();
			user.setUserName(userName);
			user.setUserTag((server.equals("pc")) ? battleTags[1] : server);
			user.setAvatar(avatar);
			user.setLevel(Integer.parseInt(level));
			user.setServer(server);
			
			allModel.setServer(server);
			allModel.setUserModel(user);
			allList.add(allModel);
		}
		
		return allList;
	}
}
