package com.his.co.batches;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.his.co.model.CoTriggersModel;
import com.his.co.service.CoTriggersService;
import com.his.ed.model.EligibilityDetailModel;
import com.his.ed.service.EligibilityDetailService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service("batch")
public class CoPlanReportDlyBatch extends CoAbstractBatch {

	private static final String BATH_NAME = "PLAN_RPRT_DLY";

	private static Integer TOTAL_TRGS = null;
	private static Integer SUCCESS_TRGS = null;
	private static Integer FAILURE_TRGS = null;

	public void init() {
		preProcess(BATH_NAME);
		start();
		postProcess(BATH_NAME, TOTAL_TRGS, SUCCESS_TRGS, FAILURE_TRGS);
	}

	/*
	 * public static void main(String[] args) { CoPlanReportDlyBatch crdb = new
	 * CoPlanReportDlyBatch(); crdb.init(); }
	 */

	private static final String FILE_NAME = "E:\\AshokAK\\HealthInsurance\\plan_ap.pdf";

	@Autowired
	private CoTriggersService coTrgService;

	@Autowired
	private EligibilityDetailService edService;

	/**
	 * This method is used to process each trigger
	 */
	@Override
	public void process(CoTriggersModel coTrgModel) throws Exception {
		EligibilityDetailModel edModel = edService.findByCaseNum(coTrgModel.getCaseNum());

		if (null != edModel) {
			String planStatus = edModel.getPlanStatus();

			if (planStatus.equals("AP")) {
				// generate approved pdf
				// buildPlanApPdf(edModel);
			} else {
				// generate denied pdf
				buildPlanDnPdf(edModel);
			}
			// store that pdf in db
			// send that pdf to email
			updatePendingTrigger(coTrgModel);
		}

	}

	private void updatePendingTrigger(CoTriggersModel coTrgModel) {
		coTrgModel.setTriggerStatus("C");
		coTrgService.updatePendingTrigger(coTrgModel);
	}

	@Override
	public void start() {
		List<CoTriggersModel> models = coTrgService.findPendingTriggers();
		if (!models.isEmpty()) {
			TOTAL_TRGS = models.size();
			// processing each trigger
			for (CoTriggersModel model : models) {
				try {
					process(model);
					SUCCESS_TRGS++;
				} catch (Exception e) {
					FAILURE_TRGS++;
					e.printStackTrace();
				}
			}
		} else {
			System.exit(0);
		}
	}

	/**
	 * This method is used to create pdf with plan details
	 * 
	 * @param edModel
	 * @throws FileNotFoundException
	 * @throws DocumentException
	 */
	public void buildPlanApPdf(EligibilityDetailModel edModel) throws FileNotFoundException, DocumentException {

		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream("new.pdf"));

		// open document
		document.open();

		// Creating paragraph
		Paragraph p = new Paragraph();
		p.add("Approved Plan Details");
		p.setAlignment(Element.ALIGN_CENTER);

		// adding paragraph to document
		document.add(p);

		// Create Table object, Here 2 specify the no. of columns
		PdfPTable pdfPTable = new PdfPTable(2);

		// First row in table
		PdfPCell pdfPCell1 = new PdfPCell(new Paragraph("Case Number"));
		PdfPCell pdfPCell2 = new PdfPCell(new Paragraph(edModel.getCaseNum().toString()));

		// Add cells to table
		pdfPTable.addCell(pdfPCell1);
		pdfPTable.addCell(pdfPCell2);

		// Second row in table
		PdfPCell pdfPCell3 = new PdfPCell(new Paragraph("Plan Name"));
		PdfPCell pdfPCell4 = new PdfPCell(new Paragraph(edModel.getPlanName().toString()));

		// Add cells to table
		pdfPTable.addCell(pdfPCell3);
		pdfPTable.addCell(pdfPCell4);

		// Third Row
		pdfPTable.addCell(new PdfPCell(new Paragraph("Plan Status")));
		pdfPTable.addCell(new PdfPCell(new Paragraph(edModel.getPlanStatus())));

		// Fourth Row
		pdfPTable.addCell(new PdfPCell(new Paragraph("Plan Start Date")));
		pdfPTable.addCell(new PdfPCell(new Paragraph(edModel.getPlanStartDate())));

		// Fifth Row
		pdfPTable.addCell(new PdfPCell(new Paragraph("Plan End Date")));
		pdfPTable.addCell(new PdfPCell(new Paragraph(edModel.getPlanEndDate())));

		// sixth Row
		pdfPTable.addCell(new PdfPCell(new Paragraph("Benfit Amount")));
		pdfPTable.addCell(new PdfPCell(new Paragraph(edModel.getBenefitAmt())));

		// Add content to the document using Table objects.

		document.add(pdfPTable);
		document.close();
	}

	/**
	 * This method is used to create with denied plan details
	 * 
	 * @param edModel
	 */
	public void buildPlanDnPdf(EligibilityDetailModel edModel) throws Exception {

		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream("new.pdf"));

		// open document
		document.open();

		// Creating paragraph
		Paragraph p = new Paragraph();
		p.add("Denied Plan Details");
		p.setAlignment(Element.ALIGN_CENTER);

		// adding paragraph to document
		document.add(p);

		// Create Table object, Here 2 specify the no. of columns
		PdfPTable pdfPTable = new PdfPTable(2);

		// First row in table
		PdfPCell pdfPCell1 = new PdfPCell(new Paragraph("Case Number"));
		PdfPCell pdfPCell2 = new PdfPCell(new Paragraph(edModel.getCaseNum().toString()));

		// Add cells to table
		pdfPTable.addCell(pdfPCell1);
		pdfPTable.addCell(pdfPCell2);

		// Second row in table
		PdfPCell pdfPCell3 = new PdfPCell(new Paragraph("Plan Name"));
		PdfPCell pdfPCell4 = new PdfPCell(new Paragraph(edModel.getPlanName().toString()));

		// Add cells to table
		pdfPTable.addCell(pdfPCell3);
		pdfPTable.addCell(pdfPCell4);

		// Third Row
		pdfPTable.addCell(new PdfPCell(new Paragraph("Plan Status")));
		pdfPTable.addCell(new PdfPCell(new Paragraph(edModel.getPlanStatus())));

		// Fourth Row
		pdfPTable.addCell(new PdfPCell(new Paragraph("Denial Reason")));
		pdfPTable.addCell(new PdfPCell(new Paragraph(edModel.getDenialReason())));

		// Add content to the document using Table objects.

		document.add(pdfPTable);
		document.close();

	}

	/**
	 * This is used to fetch dummy data
	 * 
	 * @return
	 */
	public EligibilityDetailModel getEdData() {
		EligibilityDetailModel model = new EligibilityDetailModel();
		model.setCaseNum(78956l);
		model.setPlanName("SNAP");
		model.setPlanStartDate("18-Feb-2019");
		model.setPlanEndDate("18-March-2019");
		model.setPlanStatus("AP");
		model.setBenefitAmt("$350.00");
		return model;
	}

}
