package ru.earrring.testgenerator.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import ru.earrring.testgenerator.Utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class PDFGenerator {

    private static PDFGenerator pdfGenerator;

    //подключаем файл шрифта, который поддерживает кириллицу
    private BaseFont baseRegularFont;
    private BaseFont baseBoldFont;
    private BaseFont baseItalicFont;
    private Font timesNewRomanRegularfont;
    private Font timesNewRomanBoldBigFont;
    private Font timesNewRomanItalicfont;

    private PDFGenerator() throws IOException, DocumentException {
        baseRegularFont = BaseFont.createFont("/fonts/TimesNewRomanRegular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        baseBoldFont = BaseFont.createFont("/fonts/TimesNewRomanBold.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        baseItalicFont = BaseFont.createFont("/fonts/TimesNewRomanItalic.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        timesNewRomanRegularfont = new Font(baseRegularFont, 13);
        timesNewRomanBoldBigFont = new Font(baseBoldFont, 15);
        timesNewRomanItalicfont = new Font(baseItalicFont, 13);
    }

    public static PDFGenerator getInstance() throws IOException, DocumentException {
        if (pdfGenerator == null) {
            return new PDFGenerator();
        } else {
            return pdfGenerator;
        }
    }

    public void createPDF(int variantCount, int questionCount, List<String> categoriesList) throws IOException, DocumentException {

        // создание PDF-файлика для каждого варианта
        for (int i = 0; i < variantCount; i++) {
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Вариант №" + (i + 1) + ".pdf"));

            document.open();
            document.addCreator("Генератор тестов " + Utils.VERSION);
            document.addCreationDate();
            document.addTitle("Вариант №" + (i + 1));

            Paragraph titleParagraph = new Paragraph("Вариант №" + (i + 1), timesNewRomanBoldBigFont);
            document.add(titleParagraph);

            document.close();
        }
    }
}
