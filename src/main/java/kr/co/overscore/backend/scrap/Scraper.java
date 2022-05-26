package kr.co.overscore.backend.scrap;

import java.util.List;

import kr.co.overscore.backend.model.AllModel;
import kr.co.overscore.backend.service.CacheService;

public interface Scraper {
	abstract public List<AllModel> extract(String battleTag) throws Exception;
}