package com.itextpdf.samples.sandbox.acroforms;

import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.basics.io.ByteArrayOutputStream;
import com.itextpdf.basics.io.RandomAccessSourceFactory;
import com.itextpdf.core.font.PdfFont;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.testutils.annotations.type.SampleTest;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.forms.fields.PdfTextFormField;
import com.itextpdf.model.Document;
import com.itextpdf.samples.GenericTest;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Category(SampleTest.class)
public class ReadOnlyField3 extends GenericTest {
    public static final String DEST = "./target/test/resources/sandbox/acroforms/read_only_field3.pdf";

    public static void main(String[] args) throws Exception {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
        new ReadOnlyField3().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws Exception {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(new RandomAccessSourceFactory().createSource(createForm()),
                null, null, null, null, null), new PdfWriter(new FileOutputStream(DEST)));
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        form.getField("text1").setReadOnly(true).setValue("A B C D E F G H I J K L M N O P Q R S T U V W X Y Z");
        form.getField("text2").setReadOnly(true).setValue("A B C D E F G H I J K L M N O P Q R S T U V W X Y Z");
        form.getField("text3").setReadOnly(true).setValue("A B C D E F G H I J K L M N O P Q R S T U V W X Y Z");
        form.getField("text4").setReadOnly(true).setValue("A B C D E F G H I J K L M N O P Q R S T U V W X Y Z");
        pdfDoc.close();
    }

    public byte[] createForm() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document doc = new Document(pdfDoc);
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);

        Rectangle rect = new Rectangle(36, 770, 144 - 36, 806 - 770);
        PdfTextFormField tf1 = PdfFormField.createText(pdfDoc, rect,
                PdfFont.getDefaultFont(pdfDoc), 18, "text1", "text1");
        tf1.setMultiline(true);
        form.addField(tf1);
        rect = new Rectangle(148, 770, 256 - 148, 806 - 770);
        PdfTextFormField tf2 = PdfFormField.createText(pdfDoc, rect,
                PdfFont.getDefaultFont(pdfDoc), 18, "text2", "text2");
        tf2.setMultiline(true);
        form.addField(tf2);
        rect = new Rectangle(36, 724, 144 - 36, 760 - 724);
        PdfTextFormField tf3 = PdfFormField.createText(pdfDoc, rect,
                PdfFont.getDefaultFont(pdfDoc), 18, "text3", "text3");
        tf3.setMultiline(true);
        form.addField(tf3);
        rect = new Rectangle(148, 727, 256 - 148, 760 - 727);
        PdfTextFormField tf4 = PdfFormField.createText(pdfDoc, rect,
                PdfFont.getDefaultFont(pdfDoc), 18, "text4", "text4");
        tf4.setMultiline(true);
        form.addField(tf4);
        doc.close();
        return baos.toByteArray();
    }

}