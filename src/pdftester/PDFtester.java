package pdftester;

import com.ibm.icu.text.ArabicShaping;
import com.ibm.icu.text.ArabicShapingException;
import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;

public class PDFtester {

    public static void main(String[] args) throws IOException, ArabicShapingException {
        System.setProperty("sun.java2d.cmm", "sun.java2d.cmm.kcms.KcmsServiceProvider");
        try (PDDocument doc = PDDocument.load(new File("c:\\data\\immigration_letters\\templates\\RPRequest.pdf"))) {
            PDAcroForm acroForm = doc.getDocumentCatalog().getAcroForm();
            PDResources formResources = acroForm.getDefaultResources();
            PDType0Font font = PDType0Font.load(doc, new File("C:\\data\\arial.ttf"));
//            acroForm.setXFA(null);
            formResources.put(COSName.getPDFName("F0"), font);
            String s = "نحن معشر الانبياء امرنا ان نخاطب الناس على قدر عقولهم";
//            PDTextField formField = (PDTextField) acroForm.getField("15");
//            formField.setDefaultAppearance("/F0 0 Tf 0 g");
            for (PDField field : acroForm.getFields()) {
                if (field.getFieldType().equals("Tx")) {
                    ((PDTextField) field).setDefaultAppearance("/F0 0 Tf 2 Tr .5 w 0 g");
                    field.setValue(field.getFullyQualifiedName());
                }
            }
//        formField.setValue(new StringBuilder(new ArabicShaping(ArabicShaping.LETTERS_SHAPE_TASHKEEL_ISOLATED).shape(s)).reverse().toString());
//        formField.setDefaultAppearance("/Helv 0 Tf 0 g");

            doc.save("c:\\data\\immigration_letters\\templates\\RPRequest.pdf");
        }
    }
}
