package com.itextpdf.samples;

import com.itextpdf.basics.PdfException;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.model.Document;
import com.itextpdf.model.elements.Paragraph;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Listing_01_01_HelloWorld {

    static public final String DEST = "./target/test/resources/Listing_01_01_HelloWorld.pdf";

    public static void main(String args[]) throws IOException, PdfException {

        new Listing_01_01_HelloWorld().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws PdfException, FileNotFoundException {
        //Initialize writer
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize document
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);

        //Add paragraph to the document
        doc.add(new Paragraph("Hello")).add(new Paragraph("World"));

        //Close document
        doc.close();
    }

}
