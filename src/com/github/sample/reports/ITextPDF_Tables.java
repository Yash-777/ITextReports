package com.github.sample.reports;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import com.github.writer.eventhelpers.Barcode;
import com.github.writer.eventhelpers.PageBreakEvent;
import com.github.writer.eventhelpers.WatermarkPageEvent;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * @author yashwanth.m
 *
 */
public class ITextPDF_Tables {
	public static void main(String[] args) {
		String fileName = "Yash_TempPDFFile", fileFormat = ".pdf";
		File reportFile = create_TempFile(fileName, fileFormat);
		createItextPdf( reportFile );
	}
	private static void createItextPdf(File reportFile) {
		try {
			Document document = new com.itextpdf.text.Document(PageSize.A4);
			OutputStream file = new FileOutputStream( reportFile );
			PdfWriter writer = PdfWriter.getInstance(document, file);
			
			writer.setPageEvent(new PageBreakEvent() );
			writer.setPageEvent(new WatermarkPageEvent("IText PDF Water Mark"));

			document.open();

			// define font for table header row
			Font font = FontFactory.getFont(FontFactory.HELVETICA);
			font.setColor(BaseColor.WHITE);
			font.setStyle(Font.BOLD);
			font.setStyle(Element.ALIGN_CENTER);
			
			document.add(Chunk.NEWLINE);
			document.add( new Paragraph("ParaGraph Text") );
			document.add(Chunk.NEWLINE);
			
			Paragraph paragraph = new Paragraph("PDF Paragraph Cell Information");
			PdfPCell cell = new PdfPCell( paragraph );
			cell.setColspan(2);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setPadding(5.0f);
			cell.setBackgroundColor(new BaseColor (31, 154, 42, 1));// R, G, B, A
			document.add( paragraph );
			document.add(Chunk.NEWLINE);
			
		// The content gets wrapped into an table. If the table breaks we utilize the "headerRows" to print Data.
			PdfPTable table = new PdfPTable(1);
			table.setWidthPercentage(100);
			table.setHeadersInEvent(false);
			table.setHeaderRows(1);
			table.setSkipFirstHeader(false);
			table.getDefaultCell().setBorder(1);
			table.getDefaultCell().setPadding(0);
			
			table.setSpacingBefore(30.0f);
			table.addCell( new Paragraph("This is the header we print ONLY if the page breaks.\n"
					+ "Continuation of Table of contents.") );
			table.setSpacingAfter(30.0f);
			
			PdfPTable firstText = new PdfPTable(3);
			firstText.getDefaultCell().setBackgroundColor(new BaseColor(166,162,162));
			firstText.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			firstText.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			firstText.addCell("TR2, \t Table TR1 - TD1");
			firstText.addCell("TR2, \t Table TR1 - TD2");
			firstText.addCell("TR2, \t Table TR1 - TD3");
			table.addCell(firstText);
			
			PdfPTable secondText = new PdfPTable(1);
			secondText.addCell("TR3, \n\t Table \t\n TR1 \t\n - \t\t\n TD1");
			table.addCell(secondText);
			
			
			document.add(table);
			
			final Paragraph paragraph2 = new Paragraph("What is \nthe answer \nto life the \nuniverse and \neverything?\n\nThe Answer to \nthis question \nis:\n42");
			paragraph2.setKeepTogether(true);
			document.add(paragraph2);

			document.newPage();
			createNewTable( font, document);

			document.add(Chunk.NEWLINE);
			document.add(new Paragraph("Document Generated On - "+ new Date().toString()));
			
			new Barcode("* 000000000000000 *").setBarcode(writer, document);
			
			document.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	public static void createNewTable(Font font, Document document) {
		try {
			PdfPTable table = new PdfPTable(3);
			table.setWidthPercentage(100.0f);
			table.setWidths(new float[] {5.0f, 4.0f, 1.0f});
			table.setSpacingBefore(10);
			
			// define table header cell
			PdfPCell cell = new PdfPCell();
			cell.setBackgroundColor(BaseColor.BLUE);
			cell.setPadding(3);
			
			// write table header
			cell.setPhrase(new Phrase("Book Title", font));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase("Author", font));
			table.addCell(cell);
			
			cell.setPhrase(new Phrase("Price", font));
			table.addCell(cell);
			
			table.addCell("iText in Action");
			table.addCell("Bruno Lowagie");
			table.addCell("39.48");
			
			document.add(table);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	public static File create_TempFile(String fileName, String fileFormat) {
		File tempFile = null;
		try {
			tempFile = File.createTempFile(fileName, fileFormat);
			String tempFileName = tempFile.getName();
			System.out.println("Temp file to be deleted : "+tempFileName);
			
			String changeFileName = fileName+fileFormat;
			File newFile = new File(tempFile.getParent()+File.separator+changeFileName);
			tempFile = newFile;
			
			newFile = new File(tempFile.getParent()+File.separator+tempFileName);
			if( newFile.exists() ) {
				System.out.println("Deleting the file « "+newFile.getAbsolutePath());
				newFile.delete();
			}
			
			System.out.println( "Temp File Location : "+ tempFile.getAbsolutePath() );
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tempFile;
	}
}