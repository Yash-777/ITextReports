package com.github.sample.reports;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

/**
 * @author yashwanth.m
 *
 */
public class IText_Tables {
	public static void main(String[] args) {
		String fileName = "Yash_TempFile", fileFormat = ".pdf";
		File reportFile = ITextPDF_Tables.create_TempFile(fileName, fileFormat);
		createItext( reportFile );
	}
	private static void createItext(File reportFile) {
		try {
			Document document = new com.lowagie.text.Document(PageSize.A4);
			OutputStream file = new FileOutputStream( reportFile );
			PdfWriter writer = PdfWriter.getInstance(document, file);
			document.open();
			
			PdfPTable table = new PdfPTable(1);
			table.setWidthPercentage(100);
			table.getDefaultCell().setPaddingTop(0f);
			table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.getDefaultCell().setFixedHeight(30);
			table.getDefaultCell().setBackgroundColor(new Color(166,162,162));
			Font font = FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD, new Color(102,178,255));
			table.addCell(new Phrase(18, new Chunk("Test Report", font)));
			//table.addCell(new Phrase(new Chunk(img, 5, -5)));
			document.add(table);
			
			// Header and Footer information is added from the 2nd Page of Document.
			HeaderFooter header = new HeaderFooter(
					new Phrase("Git HUB \t\t"),
					new Phrase("Stack Overfolw"));
			header.setBorder(Rectangle.BOX);
			header.setBorderColor(Color.GREEN);
			//header.setAlignment(Element.ALIGN_CENTER);
			
			String copyrightText = "Copyright © 2017 GIT HUB";
			HeaderFooter footer = new HeaderFooter(new Phrase(copyrightText), true);
			document.setHeader(header);
			document.setFooter(footer);
			
			document.newPage();
			
			document.add(Chunk.NEWLINE);
			document.add( new Paragraph("\n\nParaGraph Text") );
			document.add(Chunk.NEWLINE);
			
			writer.close();
			document.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	public static PdfPCell createCell_Style(String text) {
		Font font = new Font();
		PdfPCell cell = new PdfPCell(new Phrase(text, font));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setPaddingLeft(3f);
		cell.setPaddingTop(0f);
		cell.setBackgroundColor(new Color(159,182,205));
		cell.setBorder(4);
		cell.setBorderWidthBottom(1);
		cell.setBorderWidthRight(1);
		cell.setBorderWidthTop(1);
		cell.setBorderColorBottom(Color.GRAY);
		cell.setMinimumHeight(18f);
		return cell;
	}
}