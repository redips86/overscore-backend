package kr.co.overscore.backend.delete;

import java.util.List;

import kr.co.overscore.backend.model.UserModel;
import kr.co.overscore.backend.service.DeleteService;

import com.prismweaver.backend.batch.AbstractBatch;

public class DeleteProcessor  extends  AbstractBatch{
	private static final String programId = "OS_SYNC_DEL";
	
	public DeleteProcessor() {
		super(programId);
	}

	public static void main(String[] args) {
		new DeleteProcessor().execute();
	}

	@Override
	protected void run() {
		logger.info("DeleteProcessor work has started...");
		
		DeleteService deleteService = new DeleteService();
		
		List<UserModel> dataList = deleteService.getOldUserList();
		
		int totalCnt = 0;
		for(UserModel data : dataList){
			logger.info("OLD_ID : " + data.getUserId() + ", OLD_NAME : " + data.getUserName());
			int cnt = deleteService.removeOldUser(data.getUserId());
			totalCnt += cnt;
		}
		
		logger.info("TOTAL CNT : " + totalCnt);
		
		logger.info("DeleteProcessor work has finished...");
	}
}
