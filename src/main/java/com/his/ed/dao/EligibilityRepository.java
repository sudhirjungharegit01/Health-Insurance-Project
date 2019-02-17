package com.his.ed.dao;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.his.ed.entity.EligibilityDetailEntity;

@Repository("eligRepository")
public interface EligibilityRepository extends JpaRepository<EligibilityDetailEntity, Serializable> {

}
