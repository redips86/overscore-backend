package kr.co.overscore.backend.sync.collect;

import java.util.List;

import kr.co.overscore.backend.model.SyncWListModel;
import kr.co.overscore.backend.service.CommonService;

import com.prismweaver.backend.sync.AbstractMultiThreadSync;

public class SyncCollector  extends  AbstractMultiThreadSync<SyncWListModel>{

	private static final String programId = "OS_SYNC_COL";
	
	public SyncCollector() {
		super(programId);
		this.setRunner(SyncCollectorHandler.class);
	}

	public static void main(String[] args) {
		new SyncCollector().execute();
	}

	@Override
	protected List<SyncWListModel> selectSyncItem(int selectCount,
			long lastSyncId) {
		return new CommonService().selectSyncItem(selectCount);
	}

	/*@Override
	protected void run() {
		logger.info("SyncController work has started...");
		
		CommonService commonService = new CommonService();
		SyncService syncService = new SyncService();
		
		List<UserModel> userList = new ArrayList<UserModel>();
		userList = commonService.getUser();
		for(UserModel user : userList){
			try {
				logger.info(user.getUserId() + ", " + user.getUserName() + " has started..");
				
				List<AllModel> listAllModel;
				try {
					listAllModel = ApiScraperFactory.GetScrapHandler(ApiCode.API_2).extract(user.getUserName());
					
					for(AllModel allModel : listAllModel){
						syncService.upsertAllModel(allModel);
					}
					// return listAllModel.size();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					// return 0;
				}
				
				logger.info(user.getUserId() + ", " + user.getUserName() + " has finished..");
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		
		logger.info("SyncController work has finished...");
	}*/
}
