package kr.co.overscore.backend.sync.generate;

import kr.co.overscore.backend.service.SyncService;

import com.prismweaver.backend.batch.AbstractBatch;

public class SyncGenerator  extends  AbstractBatch{
	private static final String programId = "OS_SYNC_GEN";
	
	public SyncGenerator() {
		super(programId);
	}

	public static void main(String[] args) {
		new SyncGenerator().execute();
	}

	@Override
	protected void run() {
		logger.info("SyncGenerator work has started...");
		
		SyncService syncService = new SyncService();
		
		int cnt = syncService.putSyncWlist();
		logger.info("TOTAL CNT : " + cnt);
		
		logger.info("SyncGenerator work has finished...");
	}
}
