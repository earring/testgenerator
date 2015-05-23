package ru.earrring.testgenerator.generators;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import org.scilab.forge.jlatexmath.ParseException;
import ru.earrring.testgenerator.Utils;
import ru.earrring.testgenerator.db.Question;
import ru.earrring.testgenerator.dbWork.QuestionManager;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
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

            Paragraph categoryParagraph = new Paragraph();
            if (categoriesList.size() == 1) {
                categoryParagraph.add(new Phrase("Тест по теме ", timesNewRomanRegularfont));
            } else if (categoriesList.size() > 1) {
                categoryParagraph.add(new Phrase("Тест по темам: ", timesNewRomanRegularfont));
            }
            categoryParagraph.add(new Phrase(String.join(", ", categoriesList), timesNewRomanItalicfont));
            categoryParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(categoryParagraph);

            // добавление вопросов в тест
            // создание копии списка вопросов, т.к в процессе генерации вопросы будут из списка удаляться
            List<Question> questionListCopy = new ArrayList<>(questionList);
            for (int j = 0; j < questionCount; j++) {
                Random rand = new Random();
                // случайный номер (нумерация от нуля)
                int randomNum = rand.nextInt(questionListCopy.size());

                Question question = questionListCopy.get(randomNum);
                Paragraph questionDescriptionParagraph = new Paragraph();
                questionDescriptionParagraph.setSpacingBefore(10);
                questionDescriptionParagraph.add(new Phrase((j + 1) + ") ", timesNewRomanBoldFont));
                addPhraseWithFormulas(writer, questionDescriptionParagraph, question.getDescription(), timesNewRomanRegularfont);
                document.add(questionDescriptionParagraph);

                com.itextpdf.text.List variantList = new com.itextpdf.text.List(com.itextpdf.text.List.ORDERED);
                variantList.setIndentationLeft(10);
                for (String variant : QuestionManager.getInstance().getVariants(question)) {
                    System.out.println(variant);
                    variantList.add(new ListItem(variant, timesNewRomanRegularfont));
                }
                document.add(variantList);

                if (j != questionCount - 1) {
                    LineSeparator lineSeparator = new LineSeparator();
                    lineSeparator.setOffset(-10);
                    document.add(lineSeparator);
                }

                questionListCopy.remove(randomNum);
            }
            document.close();
        }
    }

    /**
     * Генерируется Phrase со всеми встроенными картинками. Переданная строка должна быть УЖЕ проверена на корректность
     * (корректное применение $...$ и $$...$$)
     *
     * @param text текст с формулами или без них
     * @param font шрифт, использующийся для текста (должен поддерживать кириллицу)
     * @return
     */
    private void addPhraseWithFormulas(PdfWriter pdfWriter, Paragraph paragraph, String text, Font font) throws IOException, BadElementException {
        // регексп для формул (мы разбиваем по формулам текст)
        Pattern formulaPattern = Pattern.compile("(\\$\\$.+?\\$\\$)|(\\$.+?\\$)");
        Matcher matcher = formulaPattern.matcher(text);
        int currentEnd = 0;
        boolean isFormulaInner = false;
        while (matcher.find()) {
            // если до формулы в самом начале встречается текст
            if (matcher.start() > currentEnd) {
                String string = text.substring(currentEnd, matcher.start());
                paragraph.add(new Phrase(string, font));
            }
            String formula = text.substring(matcher.start(), matcher.end());
            if (formula.matches("\\$\\$.+?\\$\\$")) {
                formula = formula.substring(2, formula.length() - 2);
                isFormulaInner = false;
            } else if (formula.matches("\\$.+?\\$")) {
                formula = formula.substring(1, formula.length() - 1);
                isFormulaInner = true;
            } else {
                throw new ParseException("Incorrect dollar signs");
            }
            BufferedImage image = LaTeXGenerator.generateBufferedImageFromFormula(formula, 72);

            // TODO сделать отличия одного знака доллара от двух
            //if (isFormulaInner) {
            Image textImage = Image.getInstance(pdfWriter, image, 1.0f);
            textImage.scalePercent(20f);
            paragraph.add(textImage);
            //} else {
            currentEnd = matcher.end();
        }
        // если текст встречается после формулы (и если формул в тексте вообще нет)
        String string = text.substring(currentEnd);
        paragraph.add(new Phrase(string, font));
    }
}
