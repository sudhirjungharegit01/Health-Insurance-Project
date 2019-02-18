package com.his.ed.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.his.ed.dao.EligibilityRepository;
import com.his.ed.entity.EligibilityDetailEntity;
import com.his.ed.model.EligibilityDetailModel;

@Service("edDetailService")
public class EligibilityDetailServiceImpl implements EligibilityDetailService {

	@Autowired
	private EligibilityRepository eligRepository;

	@Override
	public EligibilityDetailModel findByCaseNum(Long caseNum) {
		EligibilityDetailEntity entity = eligRepository.findByCaseNum(caseNum);
		EligibilityDetailModel model = new EligibilityDetailModel();
		BeanUtils.copyProperties(entity, model);
		return model;
	}

}
