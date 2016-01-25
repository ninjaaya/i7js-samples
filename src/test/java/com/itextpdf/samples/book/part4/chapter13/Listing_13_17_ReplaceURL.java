package com.itextpdf.samples.book.part4.chapter13;

import com.itextpdf.basics.geom.Rectangle;
import com.itextpdf.core.color.DeviceGray;
import com.itextpdf.core.pdf.PdfDictionary;
import com.itextpdf.core.pdf.PdfDocument;
import com.itextpdf.core.pdf.PdfName;
import com.itextpdf.core.pdf.PdfReader;
import com.itextpdf.core.pdf.PdfString;
import com.itextpdf.core.pdf.PdfWriter;
import com.itextpdf.core.pdf.action.PdfAction;
import com.itextpdf.core.utils.CompareTool;
import com.itextpdf.test.annotations.type.SampleTest;
import com.itextpdf.core.xmp.XMPException;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfButtonFormField;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.forms.fields.PdfTextFormField;
import com.itextpdf.model.Document;
import com.itextpdf.model.element.Cell;
import com.itextpdf.model.element.Table;
import com.itextpdf.samples.GenericTest;
import com.itextpdf.samples.book.part2.chapter08.Listing_08_14_ChildFieldEvent;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.experimental.categories.Category;

@Category(SampleTest.class)
public class Listing_13_17_ReplaceURL extends GenericTest {
    // DEST is alias for RESULT2
    public static final String DEST = "./target/test/resources/book/part4/chapter13/Listing_13_17_ReplaceURL.pdf";
    public static final String[] RESULT = {
            "./target/test/resources/book/part4/chapter13/Listing_13_17_ReplaceURL_submit1.pdf",
            "./target/test/resources/book/part4/chapter13/Listing_13_17_ReplaceURL_submit2.pdf"
    };
    public static final String[] CMP_RESULT = {
            "./src/test/resources/book/part4/chapter13/cmp_Listing_13_17_ReplaceURL_submit1.pdf",
            "./src/test/resources/book/part4/chapter13/cmp_Listing_13_17_ReplaceURL_submit2.pdf"
    };

    /**
     * Creates a PDF document.
     *
     * @param dest the path to the new PDF document
     * @throws IOException
     * @throws SQLException
     */
    public void createPdf(String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
        Document doc = new Document(pdfDoc);

        PdfFormField personal = PdfFormField.createEmptyField(pdfDoc);
        personal.setFieldName("personal");
        Table table = new Table(3);
        Cell cell;

        table.addCell("Your name:");
        cell = new Cell(1, 2);
        PdfTextFormField field = PdfFormField.createText(pdfDoc, new Rectangle(0, 0), "name", "name");
        // TODO DEVSIX-233
        // field.setFontSize(12);
        personal.addKid(field);
        cell.setNextRenderer(new Listing_08_14_ChildFieldEvent(field, 1, cell));
        table.addCell(cell);
        table.addCell("Login:");
        cell = new Cell();
        field = PdfFormField.createText(pdfDoc, new Rectangle(0, 0), "loginname", "loginname");
        // TODO DEVSIX-233
        // field.setFontSize(12);
        personal.addKid(field);
        cell.setNextRenderer(new Listing_08_14_ChildFieldEvent(field, 1, cell));
        table.addCell(cell);
        cell = new Cell();
        field = PdfFormField.createText(pdfDoc, new Rectangle(0, 0), "password", "password");
        field.setFieldFlag(PdfFormField.FF_PASSWORD);
        // TODO DEVSIX-233
        // field.setFontSize(12);
        personal.addKid(field);
        cell.setNextRenderer(new Listing_08_14_ChildFieldEvent(field, 1, cell));
        table.addCell(cell);
        table.addCell("Your motivation:");
        cell = new Cell(1, 2);
        cell.setHeight(60);
        field = PdfFormField.createText(pdfDoc, new Rectangle(0, 0), "reason", "reason");
        field.setMultiline(true);
        // TODO DEVSIX-233
        // field.setFontSize(12);
        personal.addKid(field);
        cell.setNextRenderer(new Listing_08_14_ChildFieldEvent(field, 1, cell));
        table.addCell(cell);
        doc.add(table);
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        form.addField(personal);

        PdfButtonFormField button1 = PdfFormField.createPushButton(pdfDoc, new Rectangle(90, 660, 50, 30), "post", "POST");
        button1.setBackgroundColor(new DeviceGray(0.7f));
        button1.setVisibility(PdfFormField.VISIBLE_BUT_DOES_NOT_PRINT);
        button1.setAction(PdfAction.createSubmitForm("/book/request", null,
                PdfAction.SUBMIT_HTML_FORMAT | PdfAction.SUBMIT_COORDINATES));
        form.addField(button1);

        doc.close();
    }

    /**
     * Manipulates a PDF file src with the file dest as result
     *
     * @param src  the original PDF
     * @param dest the resulting PDF
     * @throws IOException
     */
    public void changePdf(String src, String dest) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(dest));
        PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
        PdfFormField item = form.getField("post");
        // TODO No getMerged()
//        PdfDictionary field = item.getMerged(0);
        PdfDictionary field = item.getPdfObject();
        PdfDictionary action = field.getAsDictionary(PdfName.A);
        PdfDictionary f = action.getAsDictionary(PdfName.F);
        f.put(PdfName.F, new PdfString("http://itextpdf.com:8080/book/request"));
        pdfDoc.close();
    }

    public static void main(String args[]) throws IOException, SQLException, XMPException {
        new Listing_13_17_ReplaceURL().manipulatePdf(DEST);
    }

    @Override
    protected void manipulatePdf(String dest) throws IOException, SQLException, XMPException {
        createPdf(RESULT[0]);
        changePdf(RESULT[0], RESULT[1]);
    }

    @Override
    protected void comparePdf(String dest, String cmp) throws Exception {
        CompareTool compareTool = new CompareTool();
        String outPath;
        for (int i = 0; i < RESULT.length; i++) {
            outPath = new File(RESULT[i]).getParent();
            if (compareXml) {
                if (!compareTool.compareXmls(RESULT[i], CMP_RESULT[i])) {
                    addError("The XML structures are different.");
                }
            } else {
                if (compareRenders) {
                    addError(compareTool.compareVisually(RESULT[i], CMP_RESULT[i], outPath, differenceImagePrefix));
                    addError(compareTool.compareLinkAnnotations(dest, cmp));
                } else {
                    addError(compareTool.compareByContent(RESULT[i], CMP_RESULT[i], outPath, differenceImagePrefix));
                }
                addError(compareTool.compareDocumentInfo(RESULT[i], CMP_RESULT[i]));
            }
        }

        if (errorMessage != null) Assert.fail(errorMessage);
    }
}
