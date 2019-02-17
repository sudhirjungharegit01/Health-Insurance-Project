/**
 * 
 */
package com.his.co.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author vinay
 *
 */

@Data()
@Entity()
@Table(name="BATCH_RUN_DETAILS")
public class CoBatchRunDetailsEntity {
	@Id()
	@GeneratedValue
	@Column(name="BATCH_RUN_ID")
	Integer batchRunId;
	@Column(name="BATCH_NAME")
	String batchName;
	@Column(name="START_DATE")
	Date startDate;
	@Column(name="START_TIME")
	Date startTime;
	@Column(name="END_DATE")
	Date endDate;
	@Column(name="END_TIME")
	Date endTime;

}//CoBatchRunDetailsEntity
