package com.github.sample.reports;

import java.io.File;
import java.io.FileOutputStream;

import com.github.writer.eventhelpers.CanvasLinkEvent;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;


/**
 * This example is applicable if the chapter data is available of single page.
 * 
 * <P>Bruno Lowagie <a 
 * href='http://developers.itextpdf.com/examples/actions-and-annotations/adding-links-existing-documents'>
 * Link Examples<a>
 * @author yashwanth.m
 *
 */
public class ITextPDF_TOC extends CanvasLinkEvent {
	public ITextPDF_TOC(PdfWriter writer) {
		super(writer);
	}

	static Document document;
	static PdfWriter writer;
	static CanvasLinkEvent links;
	
	public static void main(final String[] args) throws Exception {
		document = new Document(PageSize.A4);
		
		String fileName = "Yash_TempPDFFile_TOC", fileFormat = ".pdf";
		File pdfFile = ITextPDF_Tables.create_TempFile( fileName, fileFormat );
		
		writer = PdfWriter.getInstance( document, new FileOutputStream( pdfFile ) );
		
		links = new CanvasLinkEvent( writer );
		writer.setPageEvent( links );
		document.open();
		
		document.add(new Paragraph("This is an example to generate a TOC."));
		Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 24, Font.NORMAL);
		createTOC(chapterFont, 10);
		createChaptersInformation(chapterFont, 10);
		document.close();
	}

	private static void createTOC(Font chapterFont, final int count) throws DocumentException {
		// add a small introduction chapter the shouldn't be counted.
		final Chapter intro = new Chapter(new Paragraph("This is TOC ", chapterFont), 0);
		intro.setNumberDepth(0);
		document.add(intro);
	
		for (int i = 1; i < count + 1; i++) {
			if( i == 2 ) {
				final String title = "Sample Info";
				links.addTOCInformation(document, writer, title);
			} else {
				final String title = "Chapter " + i;
				links.addTOCInformation(document, writer, title);
			}
		}
	}

	private static void createChaptersInformation(Font chapterFont, final int count) throws DocumentException {
		for (int i = 1; i < count + 1; i++) {
			// append the chapter
			if( i == 2 ) {
				final String title = "Sample Info";
				final Chunk chunk = new Chunk(title, chapterFont).setLocalDestination(title);
				final Chapter chapter = new Chapter(new Paragraph(chunk), i);
				chapter.setNumberDepth(0);
				
				chapter.addSection("Section 1");
				chapter.addSection("Section 2");
				document.add(chapter);
				
				links.addTOCInformation(document, writer, title);
			} else {
				final String title = "Chapter " + i;
				final Chunk chunk = new Chunk(title, chapterFont).setLocalDestination(title);
				final Chapter chapter = new Chapter(new Paragraph(chunk), i);
				chapter.setNumberDepth(0);
				
				chapter.addSection("Foobar1");
				chapter.addSection("Foobar2");
				document.add(chapter);
				
				links.addTOCInformation(document, writer, title);
			}
		}
	}
}