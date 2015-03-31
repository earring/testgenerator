package ru.earrring.testgenerator.frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddingFrame extends AFrame {
    private int gridy = 0;
    private int answersGridy = 0;
    private JLabel descriptionLabel;
    private JLabel addVariantLabel;
    private JTextArea descriptionArea;
    private JPanel answerWidgets;
    private JButton addAnswerButton;
    private JPanel mainPanel;

    protected void adjustFrameSettings() {
        setTitle("Добавить вопрос");
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setResizable(false);
    }

    protected void adjustLayout() {
        mainPanel = new JPanel();
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        JScrollPane mainScrollPane = new JScrollPane(mainPanel);
        add(mainScrollPane,c);

        mainPanel.setLayout(new GridBagLayout());
        descriptionLabel = new JLabel("Введите текст вопроса:");
        descriptionArea = new JTextArea("В чем смысл жизни?",5,30);

        answerWidgets = new JPanel();
        answerWidgets.setLayout(new GridBagLayout());
        JScrollPane pane = new JScrollPane(answerWidgets);

        addAnswerButton = new JButton("Добавить вариант ответа");

        addWidget(descriptionLabel,0,0);
        addWidget(descriptionArea, 0, 1);
        addWidget(pane,0,2);
        addWidget(addAnswerButton, 0, 3);

        //addAnswerWidget();

/*
        button = new JButton("Button 3");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 2;
        c.gridy = 0;
        add(button, c);

        button = new JButton("Long-Named Button 4");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 40;      //make this component tall
        c.weightx = 0.0;
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 1;
        add(button, c);

        button = new JButton("5");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;       //reset to default
        c.weighty = 1.0;   //request any extra vertical space
        c.anchor = GridBagConstraints.PAGE_END; //bottom of space
        c.insets = new Insets(10,0,0,0);  //top padding
        c.gridx = 1;       //aligned with button 2
        c.gridwidth = 2;   //2 columns wide
        c.gridy = 2;       //third row
        add(button, c);
*/
    }

    /**
     * Настройка слушателей (кнопок и других компонентов)
     */
    protected void setListeners() {
        addAnswerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAnswerWidget();
            }
        });
    }

    @Override
    public void close() {
        setVisible(false);
    }

    private void addWidget(JComponent component, int gridx, int gridy) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = gridx;
        c.gridy = gridy;
        mainPanel.add(component, c);
    }

    private void addAnswerWidget() {
        JLabel answerLabel = new JLabel(String.format("%d)   ",answersGridy+1));
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = answersGridy;
        answerWidgets.add(answerLabel, c);

        AddingWidget newWidget = new AddingWidget();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = answersGridy;
        answerWidgets.add(newWidget, c);

        answersGridy = answersGridy + 1;

        System.out.println("addAnswerWidget");

        validate();
//        answerWidgets.updateUI();
    }
}
