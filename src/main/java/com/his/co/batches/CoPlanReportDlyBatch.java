package com.his.co.batches;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

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

public class CoPlanReportDlyBatch extends CoAbstractBatch {

	public static void main(String[] args) {
		CoPlanReportDlyBatch crdb = new CoPlanReportDlyBatch();
		crdb.process(null);
	}

	private static final String FILE_NAME = "E:\\AshokAK\\HealthInsurance\\plan_ap.pdf";

	// @Autowired
	private CoTriggersService coTrgService;

	// @Autowired
	private EligibilityDetailService edService;

	@Override
	public void process(CoTriggersModel model) {
		// EligibilityDetailModel edModel = edService.findByCaseNum(model.getCaseNum());
		EligibilityDetailModel edModel = getEdData();
		String planStatus = edModel.getPlanStatus();

		if (planStatus.equals("AP")) {
			// generate approved pdf
			try {
				buildPlanApPdf(edModel);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		} else {
			// generate denied pdf
			buildPlanDnPdf(edModel);
		}

		// store that pdf in db

		// send that pdf to email

		// update the trigger as completed

	}

	@Override
	public void start() {
		List<CoTriggersModel> models = coTrgService.findPendingTriggers();
		if (!models.isEmpty()) {
			for (CoTriggersModel model : models) {
				process(model);
			}
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
	public void buildPlanDnPdf(EligibilityDetailModel edModel) {

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
