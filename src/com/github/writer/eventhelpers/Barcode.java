package com.github.writer.eventhelpers;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * @author yashwanth.m
 *
 */
public class Barcode {
	
	String codeValue; // "* 000000000000000 *"
	
	public Barcode( String codeVlaue ) {
		this.codeValue = codeVlaue;
	}
	
	public void setBarcode(PdfWriter writer, Document document) {
		// X - Left Side, Y - Up Side.
		float absoluteX = document.getPageSize().getWidth() - 150,
				absoluteY = document.getPageSize().getHeight() - 50;
		
		PdfContentByte canvas = writer.getDirectContent();
		Barcode128 code128 = new Barcode128();
		code128.setCode( codeValue );
		code128.setCodeType(Barcode128.CODE128);
		Image code128Image = code128.createImageWithBarcode(canvas, null, null);
		code128Image.setAbsolutePosition(absoluteX, absoluteY);
		code128Image.scalePercent(100);
		try {
			document.add(code128Image);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
}
