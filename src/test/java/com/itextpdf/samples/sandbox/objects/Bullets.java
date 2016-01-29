/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/*
 * This example was written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/30369587/how-to-write-bulleted-list-in-pdf-using-itext-jar-but-item-should-not-in-next-li
 */
package com.itextpdf.samples.sandbox.objects;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.font.PdfFontFactory;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Paragraph;
import com.itextpdf.model.element.Text;
import com.itextpdf.samples.GenericTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Bullets extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/objects/bullets.pdf";
    public static final String[] ITEMS = {
            "Insurance system", "Agent", "Agency", "Agent Enrollment", "Agent Settings",
            "Appointment", "Continuing Education", "Hierarchy", "Recruiting", "Contract",
            "Message", "Correspondence", "Licensing", "Party"
    };

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new AddPointer().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new FileOutputStream(dest)));
        Document doc = new Document(pdfDoc);

        PdfFont zapfdingbats = PdfFontFactory.createStandardFont(FontConstants.ZAPFDINGBATS);
        Text bullet = new Text(String.valueOf((char) 108)).setFont(zapfdingbats);
        PdfFont font = PdfFontFactory.createStandardFont(FontConstants.HELVETICA);
        Paragraph p = new Paragraph("Items can be split if they don't fit at the end: ").setFont(font);
        for (String item : ITEMS) {
            p.add(bullet);
            p.add(new Text(" " + item + " ")).setFont(font);
        }
        doc.add(p);
        doc.add(new Paragraph("\n"));
        p = new Paragraph("Items can't be split if they don't fit at the end: ").setFont(font);
        for (String item : ITEMS) {
            p.add(bullet);
            p.add(new Text("\u00a0" + item.replace(' ', '\u00a0') + " ")).setFont(font);
        }
        doc.add(p);
        doc.add(new Paragraph("\n"));
        PdfFont f = PdfFontFactory.createFont("./src/test/resources/sandbox/objects/FreeSans.ttf", "Identity-H", true);
        p = new Paragraph("Items can't be split if they don't fit at the end: ").setFont(f).setFontSize(12);
        for (String item : ITEMS) {
            p.add(new Text("\u2022\u00a0" + item.replace(' ', '\u00a0') + " ").setFontSize(12));
        }
        doc.add(p);

        doc.close();
    }
}
