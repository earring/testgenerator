package ru.earrring.testgenerator.frames;

/**
 * Created by AlexRub on 20.04.2015.
 * Форма просмотра вопросов
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
