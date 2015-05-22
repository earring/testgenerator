package ru.earrring.testgenerator.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class PDFGenerator {

    private static PDFGenerator pdfGenerator;

    private PDFGenerator() {
    }

    public static PDFGenerator getInstance() {
        if (pdfGenerator == null) {
            return new PDFGenerator();
        } else {
            return pdfGenerator;
        }
    }

    public void createPDF(int variantCount, int questionCount, List<String> categoriesList) throws IOException, DocumentException {
        for (int i = 0; i < variantCount; i++) {
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Вариант №" + (i + 1) + ".pdf"));

            document.open();

            Anchor anchorTarget = new Anchor("First page of the document.");
            anchorTarget.setName("BackToTop");
            Paragraph paragraph1 = new Paragraph();

            paragraph1.setSpacingBefore(50);

            paragraph1.add(anchorTarget);
            document.add(paragraph1);

            document.add(new Paragraph("Вариант №" + (i + 1), FontFactory.getFont(FontFactory.COURIER, 14, Font.BOLD,	new CMYKColor(0, 255, 0, 0))));

            document.close();

        }
    }
}
