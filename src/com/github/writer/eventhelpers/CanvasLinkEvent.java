package com.github.writer.eventhelpers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;

/**
 * <UL>Page Event
 * <LI>onChapter() method to create a list of chapter titles and page numbers.
 * <LI>onSection() method to keep track of the sections.
 * 
 * <P>Taken Example from this <a href='http://stackoverflow.com/a/22108091/5081877'>POST<a>
 * @author yashwanth.m
 *
 */
public class CanvasLinkEvent  extends PdfPageEventHelper {
	
	PdfWriter writer;
	
	public CanvasLinkEvent(PdfWriter writer) {
		this.writer = writer;
	}

	// table to store placeholder for all chapters and sections
	public final Map<String, PdfTemplate> tocPlaceholder = new HashMap<String, PdfTemplate>();

	// store the chapters and sections with their title here.
	final Map<String, Integer> pageByTitle = new HashMap<String, Integer>();
	
	@Override
	public void onChapter(final PdfWriter writer, final Document document,
			final float paragraphPosition, final Paragraph title) {
		this.pageByTitle.put(title.getContent(), writer.getPageNumber());
	}

	@Override
	public void onSection(final PdfWriter writer, final Document document, 
			final float paragraphPosition, final int depth, final Paragraph title) {
		this.pageByTitle.put(title.getContent(), writer.getPageNumber());
	}
	
	final float canvasWidth = 50,canvasHeight = 50;
	public void addTOCInformation(Document document, PdfWriter writer, final String title) {
		try {
			final Chunk chunk = new Chunk(title).setLocalGoto(title);
			document.add(new Paragraph(chunk));
			// Add a placeholder for the page reference
			document.add(new VerticalPositionMark() {
				@Override
				public void draw(final PdfContentByte canvas, final float llx, 
						final float lly, final float urx, final float ury, final float y) {
					final PdfTemplate createTemplate = canvas.createTemplate(canvasWidth, canvasHeight);
					tocPlaceholder.put(title, createTemplate);
				
					canvas.addTemplate(createTemplate, urx - canvasWidth, y);
				}
			}
			);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	public void addTemplateInformation(final String title) {
		BaseFont baseFont;
		try {
			baseFont = BaseFont.createFont();
			float fontSize = 12f;
			String text = String.valueOf(writer.getPageNumber());
			// When we wrote the chapter, we now the page number
			final PdfTemplate template = tocPlaceholder.get(title);
			template.beginText();
			template.setFontAndSize(baseFont, fontSize);
			template.setTextMatrix( canvasWidth - baseFont.getWidthPoint(text, fontSize), 0);
			template.showText( text );
			template.endText();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
