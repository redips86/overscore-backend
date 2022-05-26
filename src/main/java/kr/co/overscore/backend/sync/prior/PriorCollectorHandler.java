package kr.co.overscore.backend.sync.prior;

import java.util.List;

import kr.co.overscore.backend.model.AllModel;
import kr.co.overscore.backend.model.ApiCode;
import kr.co.overscore.backend.model.SyncWListModel;
import kr.co.overscore.backend.scrap.ApiScraperFactory;
import kr.co.overscore.backend.service.CommonService;
import kr.co.overscore.backend.service.SyncService;

import com.prismweaver.backend.sync.AbstractMultiProcessRunner;

public class PriorCollectorHandler extends
		AbstractMultiProcessRunner<SyncWListModel> {

	SyncService syncService = new SyncService();

	public PriorCollectorHandler(List<SyncWListModel> dataList, String oprt) {
		super(dataList, oprt);
	}

	@Override
	public void execute(SyncWListModel data) {
		SyncService syncService = new SyncService();

		logger.info(data.getUserName() + " has started..");

		List<AllModel> listAllModel;
		try {
			listAllModel = ApiScraperFactory.GetScrapHandler(ApiCode.API_2).extract(data.getUserName());
			if(listAllModel.size() == 0){
				logger.info(data.getUserName() + " NO DATA");
			}
			else{
				for (AllModel allModel : listAllModel) {
					syncService.upsertAllModel(allModel);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.info(data.getUserName() + " has finished..");
	}

	@Override
	public int updateStatus(SyncWListModel data, String status,
			String preStatus, String oprt) {
		return new CommonService().updatePriorWListStatus(data, status,
				preStatus, oprt);
	}
}
