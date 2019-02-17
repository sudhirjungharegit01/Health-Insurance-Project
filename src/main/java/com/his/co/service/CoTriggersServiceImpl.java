package com.his.co.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.his.co.dao.CoBatchSummaryRepository;
import com.his.co.dao.CoTriggersRepository;
import com.his.co.entity.CoBatchSummaryEntity;
import com.his.co.entity.CoTriggersEntity;
import com.his.co.model.CoBatchSummaryModel;
import com.his.co.model.CoTriggersModel;
import com.his.util.AppConstants;

@Service("coTrgService")
public class CoTriggersServiceImpl implements CoTriggersService {

	@Autowired
	private CoTriggersRepository coTrgRepository;
	@Autowired
	private CoBatchSummaryRepository batchSummaryRepo;

	@Override
	public List<CoTriggersModel> findPendingTriggers() {

		// making db call
		List<CoTriggersEntity> entities = coTrgRepository.findByTriggerStatus(AppConstants.PENDING_STATUS);

		List<CoTriggersModel> models = new ArrayList();

		for (CoTriggersEntity entity : entities) {
			CoTriggersModel model = new CoTriggersModel();

			// copying props from entity to model
			BeanUtils.copyProperties(entities, model);

			models.add(model);
		}

		return models;
	}

	@Override
	public boolean updatePendingTrigger(CoTriggersModel model) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public CoBatchSummaryModel saveBatchSummary(CoBatchSummaryModel model) {
		CoBatchSummaryEntity entity=null;
		entity=new CoBatchSummaryEntity();
		//copy model to entity
		BeanUtils.copyProperties(model, entity);
		entity=batchSummaryRepo.save(entity);
		model.setSummaryId(entity.getSummaryId());
		return model;
	}//saveBatchSummary(CoBatchSummaryModel model)
}
