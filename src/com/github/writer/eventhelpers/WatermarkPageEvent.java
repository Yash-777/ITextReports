package com.github.writer.eventhelpers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.commons.lang3.StringUtils;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * @author yashwanth.m
 *
 */
public class WatermarkPageEvent extends PdfPageEventHelper {

	private Font font;

	private String text;

	public WatermarkPageEvent(String text) {
		this.text = text;
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(BaseColor.GRAY);
		font.setStyle(Font.BOLD);
		this.font = font;
	}

	public void onEndPage(PdfWriter writer, Document document) {
		float absoluteX = document.getPageSize().getWidth() / 2,
				absoluteY = document.getPageSize().getHeight() / 2;
		System.out.format("Pase Sizes W, H : [%s , %s]\n", absoluteX, absoluteY);
		float textX = 200, textY = 200, testAngle = 25;
		ColumnText.showTextAligned(writer.getDirectContentUnder(), Element.ALIGN_LEFT,
				new Phrase(StringUtils.isEmpty(text) ? "" : this.text, font), textX, textY, testAngle);
		
		String itextImage = System.getProperty("user.dir") + File.separator + "/Images/ITextPDF.png";
		try {
			Image image = Image.getInstance( itextImage );
			image.setScaleToFitLineWhenOverflow(false);
			// The image must have absolute positioning.
			float imageWidth = 264, imageHeight = 75;
			image.setAbsolutePosition(absoluteX - imageWidth/2, absoluteY - imageHeight/2);
			image.scaleAbsolute(imageWidth, imageHeight);
			
			PdfContentByte pcb = writer.getDirectContentUnder();
			pcb.addImage( image );
		} catch (BadElementException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ColumnText.showTextAligned(writer.getDirectContentUnder(), Element.ALIGN_LEFT,
				new Phrase(StringUtils.isEmpty(text) ? "" : this.text, font), textX, absoluteY + textY, testAngle);
	}
}
