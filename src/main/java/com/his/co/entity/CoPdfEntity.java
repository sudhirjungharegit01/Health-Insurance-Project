/**
 * 
 */
package com.his.co.entity;

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
@Table(name = "CO_PDFS")
public class CoPdfEntity {
	@Id()
	@GeneratedValue
	@Column(name = "CO_PDF_ID")
	Integer coPdfId;
	@Column(name = "CASE_NUMBER")
	String caseNumber;
	@Column(name = "PDF_DOCUMENT")
	String pdfDocument;
	@Column(name = "PLAN_NAME")
	String planName;
	@Column(name = "PLAN_STATUS")
	String PlanStatus;

}// CoPdfEntity
