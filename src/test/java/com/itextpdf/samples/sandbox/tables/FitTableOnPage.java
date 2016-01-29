/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * Example written by Bruno Lowagie in answer to:
 * http://stackoverflow.com/questions/24616920/last-row-in-itext-table-extending-when-it-shouldnt
 */
package com.itextpdf.samples.sandbox.tables;


import com.itextpdf.basics.source.ByteArrayOutputStream;
import com.itextpdf.core.geom.PageSize;
import com.itextpdf.core.geom.Rectangle;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Table;
import com.itextpdf.model.layout.LayoutArea;
import com.itextpdf.model.layout.LayoutContext;
import com.itextpdf.model.layout.LayoutResult;
import com.itextpdf.model.renderer.IRenderer;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import java.io.File;

import org.junit.Ignore;
import org.junit.experimental.categories.Category;

@Ignore
@Category(SampleTest.class)
public class FitTableOnPage extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/tables/fit_table_on_page.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new FitTableOnPage().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        Table table = new Table(1);
        table.setWidth(550);
//        for (int i = 0; i < 10; i++) {
//            Cell cell;
//            if (i == 9) {
//                cell = new Cell().add(new Paragraph("Two\nLines"));
//            } else {
//                cell = new Cell().add(new Paragraph(Integer.toString(i)));
//            }
//            table.addCell(cell);
//        }
        // TODO Replace the next line with the commented lines above
        table.addCell("Two\nLines");

        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(new ByteArrayOutputStream()));
        Document doc = new Document(pdfDoc);
        IRenderer tableRenderer = table.createRendererSubTree().setParent(doc.getRenderer());
        LayoutResult tableLayoutResult = tableRenderer.layout(new LayoutContext(new LayoutArea(0, new Rectangle(612, 1000))));


        PdfWriter writer = new PdfWriter(dest);
        pdfDoc = new PdfDocument(writer);
        // TODO Problems with layout
        doc = new Document(pdfDoc, new PageSize(612, tableLayoutResult.getOccupiedArea().getBBox().getHeight()+72));
        doc.add(table);
        doc.close();
    }
}
