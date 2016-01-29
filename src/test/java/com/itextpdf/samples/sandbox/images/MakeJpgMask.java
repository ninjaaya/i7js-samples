/*

    This file is part of the iText (R) project.
    Copyright (c) 1998-2016 iText Group NV

*/

/**
 * This code sample was written by Bruno Lowagie in answer to this question:
 * http://stackoverflow.com/questions/29096314/how-to-make-an-image-a-qualified-mask-candidate-in-itext
 */
package com.itextpdf.samples.sandbox.images;

import com.itextpdf.core.geom.PageSize;
import com.itextpdf.io.image.Image;
import com.itextpdf.io.image.ImageFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.model.Document;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.test.annotations.type.SampleTest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class MakeJpgMask extends GenericTest {
    public static final String IMAGE = "./src/test/resources/sandbox/images/javaone2013.jpg";
    public static final String MASK = "./src/test/resources/sandbox/images/berlin2013.jpg";
    public static final String DEST = "./target/test/resources/sandbox/images/make_jpg_mask.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new MakeJpgMask().manipulatePdf(DEST);
    }

    public static Image makeBlackAndWhitePng(String image) throws IOException {
        BufferedImage bi = ImageIO.read(new File(image));
        BufferedImage newBi = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_USHORT_GRAY);
        newBi.getGraphics().drawImage(bi, 0, 0, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(newBi, "png", baos);
        return ImageFactory.getImage(baos.toByteArray());
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        FileOutputStream fos = new FileOutputStream(dest);
        PdfWriter writer = new PdfWriter(fos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc, PageSize.A4.rotate());

        Image image = ImageFactory.getImage(IMAGE);
        Image mask = makeBlackAndWhitePng(MASK);
        mask.makeMask();
        image.setImageMask(mask);
        com.itextpdf.model.element.Image img = new com.itextpdf.model.element.Image(image);
        img.scaleAbsolute(PageSize.A4.rotate().getWidth(), PageSize.A4.rotate().getHeight());
        img.setFixedPosition(0, 0);
        doc.add(img);
        doc.close();
    }
}
