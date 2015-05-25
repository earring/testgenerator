package ru.earrring.testgenerator.generators;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import ru.earrring.testgenerator.Utils;
import ru.earrring.testgenerator.db.Question;
import ru.earrring.testgenerator.dbWork.QuestionManager;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PDFGenerator {

    private static PDFGenerator pdfGenerator;

    //подключаем файл шрифта, который поддерживает кириллицу
    private BaseFont baseRegularFont;
    private BaseFont baseBoldFont;
    private BaseFont baseItalicFont;
    private Font timesNewRomanRegularfont;
    private Font timesNewRomanBoldBigFont;
    private Font timesNewRomanBoldFont;
    private Font timesNewRomanItalicfont;

    private PDFGenerator() throws IOException, DocumentException {
        baseRegularFont = BaseFont.createFont("/fonts/TimesNewRomanRegular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        baseBoldFont = BaseFont.createFont("/fonts/TimesNewRomanBold.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        baseItalicFont = BaseFont.createFont("/fonts/TimesNewRomanItalic.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        timesNewRomanRegularfont = new Font(baseRegularFont, 13);
        timesNewRomanBoldBigFont = new Font(baseBoldFont, 15);
        timesNewRomanBoldFont = new Font(baseBoldFont, 13);
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
        List<Question> questionList = QuestionManager.getInstance().getQuestions(categoriesList);

        // создание файлика для ответов
        Document documentAnswer = new Document(PageSize.A4, 50, 50, 50, 50);
        PdfWriter writerAnswer = PdfWriter.getInstance(documentAnswer, new FileOutputStream("Ответы.pdf"));
        documentAnswer.open();

        // создание PDF-файлика для каждого варианта
        for (int i = 0; i < variantCount; i++) {
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Вариант №" + (i + 1) + ".pdf"));

            document.open();
            document.addCreator("Генератор тестов " + Utils.VERSION);
            document.addCreationDate();
            document.addTitle("Вариант №" + (i + 1));

            Paragraph titleParagraph = new Paragraph("Вариант №" + (i + 1), timesNewRomanBoldBigFont);
            titleParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(titleParagraph);

            Paragraph answerParagraph = new Paragraph("Ответы на вариант №" + (i + 1), timesNewRomanBoldBigFont);
            answerParagraph.setAlignment(Element.ALIGN_CENTER);
            documentAnswer.add(answerParagraph);

            Paragraph categoryParagraph = new Paragraph();
            if (categoriesList.size() == 1) {
                categoryParagraph.add(new Phrase("Тест по теме ", timesNewRomanRegularfont));
            } else if (categoriesList.size() > 1) {
                categoryParagraph.add(new Phrase("Тест по темам: ", timesNewRomanRegularfont));
            }
            categoryParagraph.add(new Phrase(String.join(", ", categoriesList), timesNewRomanItalicfont));
            categoryParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(categoryParagraph);

            // создание заголовка таблицы с ответами
            PdfPTable answerTable = new PdfPTable(2);
            float[] columnWidths = {1f, 4f};
            answerTable.setWidths(columnWidths);
            answerTable.setSpacingBefore(5f);
            answerTable.setSpacingAfter(5f);
            PdfPCell cell1 = new PdfPCell(new Paragraph("№ вопроса", timesNewRomanBoldFont));
            PdfPCell cell2 = new PdfPCell(new Paragraph("Верный ответ", timesNewRomanBoldFont));
            answerTable.addCell(cell1);
            answerTable.addCell(cell2);

            // добавление вопросов в тест
            // создание копии списка вопросов, т.к в процессе генерации вопросы будут из списка удаляться
            List<Question> questionListCopy = new ArrayList<>(questionList);
            for (int j = 0; j < questionCount; j++) {
                Random rand = new Random();
                // случайный номер (нумерация от нуля)
                int randomNum = rand.nextInt(questionListCopy.size());

                Question question = questionListCopy.get(randomNum);
                generateQuestion(writer, document, j, question);

                // добавление ответа в таблицу ответов
                PdfPCell cellNumber = new PdfPCell(new Paragraph("" + (j + 1), timesNewRomanRegularfont));
                String questionAnswers = String.join(", ", QuestionManager.getInstance().getAnswers(question));
                PdfPCell cellAnswer = new PdfPCell(new Paragraph(questionAnswers, timesNewRomanBoldFont));
                answerTable.addCell(cellNumber);
                answerTable.addCell(cellAnswer);

                // добавление разделителя в текст варианта
                if (j != questionCount - 1) {
                    LineSeparator lineSeparator = new LineSeparator();
                    lineSeparator.setOffset(-10);
                    document.add(lineSeparator);
                }

                questionListCopy.remove(randomNum);
            }
            document.close();
            
            documentAnswer.add(answerTable);
        }
        documentAnswer.close();
    }

    /**
     * Генерация отдельного вопроса
     *
     * @param writer
     * @param document
     * @param questionNumber нумерация начинается с нуля
     * @param question
     * @throws DocumentException
     * @throws IOException
     */
    private void generateQuestion(PdfWriter writer, Document document, int questionNumber, Question question) throws DocumentException, IOException {
        Paragraph questionNumberParagraph = new Paragraph();
        questionNumberParagraph.setSpacingBefore(10);
        questionNumberParagraph.add(new Phrase("Вопрос №" + (questionNumber + 1), timesNewRomanBoldFont));
        document.add(questionNumberParagraph);
        addPhraseWithFormulas(writer, document, question.getDescription(), timesNewRomanRegularfont);

        com.itextpdf.text.List variantList = new com.itextpdf.text.List(com.itextpdf.text.List.ORDERED);
        variantList.setIndentationLeft(10);
        for (String variant : QuestionManager.getInstance().getVariants(question)) {
            System.out.println(variant);
            variantList.add(new ListItem(variant, timesNewRomanRegularfont));
        }
        document.add(variantList);
    }

    /**
     * Генерируется Phrase со всеми встроенными картинками. Переданная строка должна быть УЖЕ проверена на корректность
     * (корректное применение $$...$$)
     *
     * @param text текст с формулами или без них
     * @param font шрифт, использующийся для текста (должен поддерживать кириллицу)
     * @return
     */
    private void addPhraseWithFormulas(PdfWriter pdfWriter, Document document, String text, Font font) throws IOException, DocumentException {
        // регексп для формул (мы разбиваем по формулам текст)
        Pattern formulaPattern = Pattern.compile("\\$\\$.+?\\$\\$");
        Matcher matcher = formulaPattern.matcher(text);
        int currentEnd = 0;
        while (matcher.find()) {
            // если до формулы в самом начале встречается текст
            if (matcher.start() > currentEnd) {
                String string = text.substring(currentEnd, matcher.start());
                Paragraph paragraph = new Paragraph();
                paragraph.add(new Phrase(string, font));
                document.add(paragraph);
            }
            // добавление самой формулы
            String formula = text.substring(matcher.start() + 2, matcher.end() - 2);
            BufferedImage image = LaTeXGenerator.generateBufferedImageFromFormula(formula, 70);
            Image textImage = Image.getInstance(pdfWriter, image, 1.0f);
            textImage.scalePercent(20f);
            textImage.setAlignment(Image.ALIGN_CENTER | Image.DEFAULT);
            Paragraph paragraph = new Paragraph();
            paragraph.add(textImage);
            document.add(paragraph);

            currentEnd = matcher.end();
        }
        // если текст встречается после формулы (и если формул в тексте вообще нет)
        String string = text.substring(currentEnd);
        Paragraph paragraph = new Paragraph();
        paragraph.add(new Phrase(string, font));
        document.add(paragraph);
    }

    /**
     * Создание Image, сгенерированного из PDF. Рендер, как будет выглядеть PDF, другими словами
     *
     * @param questionNumber номер вопроса, который будет отображаться (нумерация с нуля)
     * @param question       сам вопрос
     * @return Image, который нужно будет отобразить
     * @throws DocumentException
     * @throws IOException
     */
    public java.awt.Image generatePDFImage(int questionNumber, Question question, float zoom) throws DocumentException, IOException {
        // генерация PDF и передача данных пдф в байтовый поток
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        ByteArrayOutputStream baosPDF = new ByteArrayOutputStream();
        PdfWriter docWriter = PdfWriter.getInstance(document, baosPDF);
        document.open();
        generateQuestion(docWriter, document, questionNumber, question);
        document.close();
        docWriter.close();

        ByteBuffer buf = ByteBuffer.wrap(baosPDF.toByteArray());

        // use the PDF Renderer library on the buf which contains the in memory PDF document
        PDFFile pdffile = new PDFFile(buf);
        PDFPage page = pdffile.getPage(1);

        //get the width and height for the doc
        java.awt.Rectangle rect = new Rectangle(0, 0, (int) page.getWidth(), (int) page.getHeight());

        //generate the image
        java.awt.Image img = page.getImage((int) (rect.width * zoom), (int) (rect.height * zoom), //width height
                rect, // clip rect
                null, // null for the ImageObserver
                true, // fill background with white
                true) // block until drawing is done
                ;

        return img;
    }
}
