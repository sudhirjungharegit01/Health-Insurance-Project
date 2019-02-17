package com.his.co.service;

import java.util.List;

import com.his.co.model.CoBatchSummaryModel;
import com.his.co.model.CoTriggersModel;

public interface CoTriggersService {

	public List<CoTriggersModel> findPendingTriggers();

	public boolean updatePendingTrigger(CoTriggersModel model);
	public CoBatchSummaryModel saveBatchSummary(CoBatchSummaryModel model);

}
