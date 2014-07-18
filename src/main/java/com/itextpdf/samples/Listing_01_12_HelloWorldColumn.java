package com.itextpdf.samples;

import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.model.elements.Paragraph;
import com.itextpdf.model.layout.DefaultLayoutMgr;

import java.io.FileOutputStream;
import java.io.IOException;

public class Listing_01_12_HelloWorldColumn {

    static private final String RESULT = "./result.pdf";

    public static void main(String args[]) throws IOException {

        //Initialize writer
        FileOutputStream fos = new FileOutputStream(RESULT);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize document
        PdfDocument pdfDoc = new PdfDocument(writer);

        //Add paragraph to the document
        Paragraph hello = new Paragraph("Hello World");
        PdfCanvas canvas = new PdfCanvas(pdfDoc.addNewPage().getContentStream());   //Adds new page to the document and initializes canvas by page content stream.
        DefaultLayoutMgr.showTextAligned(canvas, 0, hello, 36, 788, 0); //alignment is set to '0'. No constants for alignment are available yet.

        //Close document
        pdfDoc.close();

    }

}