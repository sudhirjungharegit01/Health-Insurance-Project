package com.his.co.dao;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.his.co.entity.CoBatchSummaryEntity;

@Repository("batchSummaryRepo")
public interface CoBatchSummaryRepository extends JpaRepository<CoBatchSummaryEntity, Serializable> {

}// CoBatchSummaryRepository
