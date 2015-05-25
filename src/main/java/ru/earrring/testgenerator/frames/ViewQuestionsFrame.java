package ru.earrring.testgenerator.frames;

/**
 * Created by AlexRub on 20.04.2015.
 * Форма просмотра вопросов
 */

import com.itextpdf.text.DocumentException;
import ru.earrring.testgenerator.dbWork.QuestionManager;
import ru.earrring.testgenerator.generators.PDFGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ViewQuestionsFrame extends AFrame {
    private JPanel viewPanel; //Панель вопроса
    private JPanel listPanel; //Панель списка вопросов
    private JTree questionLsist; //Дерево вопросов

    protected void adjustFrameSettings() {
        setTitle("Просмотр вопросов");
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setResizable(false);
    }
    protected void adjustLayout() {
        setLayout(new BorderLayout());

        viewPanel = new JPanel();
        listPanel = new JPanel();

        viewPanel.setLayout(new BorderLayout());
        listPanel.setLayout(new BorderLayout());

        JScrollPane viewScrollPane = new JScrollPane(viewPanel);
        JScrollPane listScrollPane = new JScrollPane(listPanel);

        try {
            if (QuestionManager.getInstance().getQuestionCount() > 0) {
                add(new JLabel(new ImageIcon(PDFGenerator.getInstance().generatePDFImage(0, QuestionManager.getInstance().getAllQuestions().get(0), 1.0f))));
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Настройка слушателей (кнопок и других компонентов)
     */
    protected void setListeners() {

    }
    @Override
    public void close() {
        setVisible(false);
    }

}
