package kr.co.overscore.backend.sync.search;

import java.util.List;

import kr.co.overscore.backend.model.SyncWListModel;
import kr.co.overscore.backend.service.CommonService;

import com.prismweaver.backend.sync.AbstractMultiThreadSync;

public class SearchCollector  extends  AbstractMultiThreadSync<SyncWListModel>{

	private static final String programId = "OS_SEARCH_COL";
	
	public SearchCollector() {
		super(programId);
		this.setRunner(SearchCollectorHandler.class);
	}

	public static void main(String[] args) {
		new SearchCollector().execute();
	}

	@Override
	protected List<SyncWListModel> selectSyncItem(int selectCount,
			long lastSyncId) {
		return new CommonService().selectSearchItem(selectCount);
	}
}
