package kr.co.overscore.backend.sync.search;

import java.util.List;

import kr.co.overscore.backend.model.AllModel;
import kr.co.overscore.backend.model.ApiCode;
import kr.co.overscore.backend.model.SyncWListModel;
import kr.co.overscore.backend.scrap.ApiScraperFactory;
import kr.co.overscore.backend.service.CommonService;
import kr.co.overscore.backend.service.SearchService;
import kr.co.overscore.backend.service.SyncService;

import com.prismweaver.backend.sync.AbstractMultiProcessRunner;

public class SearchCollectorHandler extends
		AbstractMultiProcessRunner<SyncWListModel> {

	SearchService service = new SearchService();

	public SearchCollectorHandler(List<SyncWListModel> dataList, String oprt) {
		super(dataList, oprt);
	}

	@Override
	public void execute(SyncWListModel data) {
		logger.info(data.getUserName() + " has started..");

		List<AllModel> listAllModel;
		try {
			listAllModel = ApiScraperFactory.GetScrapHandler(ApiCode.API_SEARCH).extract(data.getUserName());

			service.deleteSearchModel(data.getUserName());
			
			for (AllModel allModel : listAllModel) {
				service.insertSearchModel(allModel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info(data.getUserName() + " has finished..");
	}

	@Override
	public int updateStatus(SyncWListModel data, String status,
			String preStatus, String oprt) {
		return new CommonService().updateSearchWListStatus(data, status,
				preStatus, oprt);
	}
}
