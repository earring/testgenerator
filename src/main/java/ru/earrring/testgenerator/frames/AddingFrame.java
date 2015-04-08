package ru.earrring.testgenerator.frames;

import ru.earrring.testgenerator.components.AddingComponent;

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
        setResizable(true);
    }

    protected void adjustLayout() {
        mainPanel = new JPanel();
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        JScrollPane mainScrollPane = new JScrollPane(mainPanel);
        add(mainScrollPane, c);

        mainPanel.setLayout(new GridBagLayout());
        descriptionLabel = new JLabel("Введите текст вопроса:");
        descriptionArea = new JTextArea("В чем смысл жизни?", 5, 30);

        answerWidgets = new JPanel();
        answerWidgets.setLayout(new GridBagLayout());
        JScrollPane pane = new JScrollPane(answerWidgets);

        addAnswerButton = new JButton("Добавить вариант ответа");

        addComponent(descriptionLabel, 0, 0);
        addComponent(descriptionArea, 0, 1);
        addComponent(pane, 0, 2);
        addComponent(addAnswerButton, 0, 3);
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

    private void addComponent(JComponent component, int gridx, int gridy) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = gridx;
        c.gridy = gridy;
        mainPanel.add(component, c);
    }

    private void addAnswerWidget() {
        JLabel answerLabel = new JLabel(String.format("%d)   ", answersGridy + 1));
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = answersGridy;
        answerWidgets.add(answerLabel, c);

        AddingComponent newWidget = new AddingComponent();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = answersGridy;
        answerWidgets.add(newWidget, c);

        answersGridy = answersGridy + 1;

        validate();
    }
}
