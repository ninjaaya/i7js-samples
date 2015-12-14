package com.itextpdf.samples.book.part3.chapter10;


import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.basics.image.Image;
import com.itextpdf.basics.image.ImageFactory;
import com.itextpdf.canvas.PdfCanvas;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.xobject.PdfFormXObject;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.samples.GenericTest;

import java.io.IOException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_10_20_TemplateClip extends GenericTest {
    public static final String DEST = "./target/test/resources/book/part3/chapter10/Listing_10_20_TemplateClip.pdf";
    /** One of the resources. */
    public static final String RESOURCE
            = "./src/test/resources/book/part3/chapter10/bruno_ingeborg.jpg";

    public static void main(String args[]) throws IOException {
        new Listing_10_20_TemplateClip().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        Document doc = new Document(pdfDoc);
        Image img = ImageFactory.getImage(RESOURCE);
        com.itextpdf.model.element.Image imgModel = new com.itextpdf.model.element.Image(img);
        float w = imgModel.getImageScaledWidth();
        float h = imgModel.getImageScaledHeight();
        PdfFormXObject xObject = new PdfFormXObject(new Rectangle(850, 600));
        PdfCanvas xObjectCanvas = new PdfCanvas(xObject, pdfDoc);
        xObjectCanvas.addImage(img, w, 0, 0, h, 0, -600);
        com.itextpdf.model.element.Image clipped = new com.itextpdf.model.element.Image(xObject);
        clipped.scale(0.5f, 0.5f);
        doc.add(clipped);
        doc.close();
    }
}