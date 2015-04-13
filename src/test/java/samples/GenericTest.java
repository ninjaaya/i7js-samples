package samples;

import com.itextpdf.testutils.CompareTool;
import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.LoggerFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.management.OperationsException;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class GenericTest {

    /** The logger class */
    private final static Logger LOGGER = LoggerFactory.getLogger(GenericTest.class.getName());

    /** The class file for the example we're going to test. */
    protected Class<?> klass;
    protected String className;
    protected boolean compareRenders = false;
    /** An error message */
    private String errorMessage;
    /** A prefix that is part of the error message. */
    private String differenceImagePrefix = "difference";

    /**
     * Gets triggered before the test is performed.
     * When writing tests, you need to override this method to set
     * the klass variable (using the setKlass() method)
     */
    @Before
    public void setup() {

    }

    /**
     * Creates a Class object for the example you want to test.
     * @param	className	the class you want to test
     */
    protected void setKlass(String className) {
        this.className = className;
        try {
            klass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(className + " not found");
        }
    }

    protected void setCompareRenders(boolean compareRenders) {
        this.compareRenders = compareRenders;
    }

    /**
     * Tests the example.
     * If SRC and DEST are defined, the example manipulates a PDF;
     * if only DEST is defined, the example creates a PDF.
     */
    @Test(timeout = 120000)
    public void test() throws Exception {
        if (this.getClass().getName().equals(GenericTest.class.getName()))
            return;
        LOGGER.info("Starting test " + className + ".");
        // Getting the destination PDF file (must be there!)
        String dest= getDest();
        if (dest == null || dest.length() == 0)
            throw new OperationsException("DEST cannot be empty!");
        // Compare the destination PDF with a reference PDF
        manipulatePdf(dest);
        System.out.println(dest + "\n" + getCmpPdf());
        comparePdf(dest, getCmpPdf());
        LOGGER.info("Test complete.");
    }

    /**
     * Manupulates a PDF by invoking the manipulatePdf() method in the
     * original sample class.
     * @param	dest	the resulting PDF
     */
    protected void manipulatePdf(String dest) throws Exception {
        LOGGER.info("Manipulating PDF.");
        Method method = klass.getDeclaredMethod("manipulatePdf", String.class);
        method.invoke(klass.getConstructor().newInstance(), dest);
    }

    /**
     * Gets the path to the resulting PDF from the sample class;
     * this method also creates directories if necessary.
     * @return	a path to a resulting PDF
     */
    protected String getDest() {
        String dest = getStringField("DEST");
        if (dest != null) {
            File file = new File(dest);
            file.getParentFile().mkdirs();
        }
        return dest;
    }

    /**
     * Returns a string value that is stored as a static variable
     * inside an example class.
     * @param name	the name of the variable
     * @return	the value of the variable
     */
    protected String getStringField(String name) {
        try {
            Field field = klass.getField(name);
            if (field == null)
                return null;
            Object obj = field.get(null);
            if (obj == null || ! (obj instanceof String))
                return null;
            return (String)obj;
        }
        catch(Exception e) {
            return null;
        }
    }

    /**
     * Compares two PDF files using iText's CompareTool.
     * @param	dest	the PDF that resulted from the test
     * @param	cmp		the reference PDF
     */
    protected void comparePdf(String dest, String cmp) throws Exception {
        if (cmp == null || cmp.length() == 0) return;
        CompareTool compareTool = new CompareTool();
        String outPath = new File(dest).getParent();
        new File(outPath).mkdirs();
        if (compareRenders) {
            addError(compareTool.compare(dest, cmp, outPath, differenceImagePrefix));
            addError(compareTool.compareLinks(dest, cmp));
        } else {
            addError(compareTool.compareByContent(dest, cmp, outPath, differenceImagePrefix));
        }
        addError(compareTool.compareDocumentInfo(dest, cmp));


        if (errorMessage != null) Assert.fail(errorMessage);
    }

    /**
     * Every test needs to know where to find its reference file.
     */
    protected String getCmpPdf() {
        String tmp = getDest();
        if (tmp == null)
            return null;
        int i = tmp.lastIndexOf("/");
        return "./src"+tmp.substring(8, i + 1) + "cmp_" + tmp.substring(i + 1);
    }

    /**
     * Helper method to construct error messages.
     * @param	error	part of an error message.
     */
    private void addError(String error) {
        if (error != null && error.length() > 0) {
            if (errorMessage == null)
                errorMessage = "";
            else
                errorMessage += "\n";

            errorMessage += error;
        }
    }
}
