package com.github.writer.eventhelpers;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * @author yashwanth.m
 *
 */
public class PageBreakEvent extends PdfPageEventHelper {
	@Override
	public void onStartPage(final PdfWriter writer, final Document document) {
		// This PageEventHelper should write a small text "Continuation: ..." 
		String someTompic = "PageBreak was forced by \"keepTogether\" \n";
		try {
			document.add(new Paragraph("Continuation: " + someTompic));
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
}