package com.github.writer.eventhelpers;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPage;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * @author yashwanth.m
 *
 */
public class PageRotateEvent extends PdfPageEventHelper {
	@Override
	public void onStartPage(PdfWriter writer, Document document) {
		writer.addPageDictEntry(PdfName.ROTATE, PdfPage.SEASCAPE);
	}
}