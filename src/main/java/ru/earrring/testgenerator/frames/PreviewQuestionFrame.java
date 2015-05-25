package ru.earrring.testgenerator.frames;

import com.itextpdf.text.DocumentException;
import ru.earrring.testgenerator.Utils;
import ru.earrring.testgenerator.db.Question;
import ru.earrring.testgenerator.generators.PDFGenerator;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Created by Artem on 25.05.2015.
 */
public class PreviewQuestionFrame extends AFrame {

    private Question question;
    private JScrollPane questionScrollPane;

    PreviewQuestionFrame(Question question) {
        this.question = question;
    }

    @Override
    protected void adjustFrameSettings() {
        setResizable(false);
        setTitle("Предпросмотр вопроса");
    }

    @Override
    protected void adjustLayout() {
        //setLayout(new BorderLayout());

        try {
            Image image = PDFGenerator.getInstance().generatePDFImage(0, question, 1.5f);
            questionScrollPane = new JScrollPane(new JLabel(new ImageIcon(image)));
            questionScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            questionScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            setSize(image.getWidth(null), 600);
            Utils.centerFrame(this);
            getContentPane().add(questionScrollPane);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        add(questionScrollPane, BorderLayout.CENTER);
    }

    @Override
    protected void setListeners() {

    }

    @Override
    public void close() {
        setVisible(false);
        dispose();
    }
}
