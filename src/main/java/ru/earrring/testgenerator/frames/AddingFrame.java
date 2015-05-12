package ru.earrring.testgenerator.frames;

import ru.earrring.testgenerator.components.AddingComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddingFrame extends AFrame {

    private final static int MAX_VARIANT = 15;

    private int gridy = 0;
    private int answersGridy = 0;
    private JLabel addVariantLabel;
    private JTextArea descriptionArea;
    private JPanel answerWidgets;
    private JScrollPane answerScrollPane;
    private JButton addAnswerButton;
    private JPanel mainPanel;

    protected void adjustFrameSettings() {
        setTitle("Добавить вопрос");
        setSize(700, 700);
        setResizable(false);
    }

    protected void adjustLayout() {
        mainPanel = new JPanel();
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        mainPanel.setLayout(new GridBagLayout());

        descriptionArea = new JTextArea(5, 50);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);
        descriptionArea.setToolTipText("Введите текст вопроса:");
        descriptionArea.setFont(new Font("Serif", Font.ITALIC, 16));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        descriptionScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        addComponentToMainPanel(descriptionScrollPane, 0, 0, 1.5);

        addAnswerButton = new JButton("Добавить вариант ответа");
        addAnswerButton.setFont(new Font("Arial", Font.PLAIN, 22));
        addComponentToMainPanel(addAnswerButton, 0, 1, 0.2);

        answerWidgets = new JPanel();
        answerWidgets.setLayout(new GridBagLayout());
        answerScrollPane = new JScrollPane(answerWidgets);

        answerScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        answerScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        addComponentToMainPanel(answerScrollPane, 0, 2, 2.5);
    }

    /**
     * Настройка слушателей (кнопок и других компонентов)
     */
    protected void setListeners() {
        addAnswerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAnswerWidget();
                if (answersGridy == MAX_VARIANT) {
                    addAnswerButton.setEnabled(false);
                }
            }
        });
    }

    @Override
    public void close() {
        setVisible(false);
    }

    private void addComponentToMainPanel(JComponent component, int gridx, int gridy, double weighty) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTH;
        c.weightx = 1.0;
        c.weighty = weighty;
        c.gridx = gridx;
        c.gridy = gridy;
        mainPanel.add(component, c);
    }

    private void addAnswerWidget() {
        JLabel answerLabel = new JLabel(String.format("%d)   ", answersGridy + 1));
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTH;
        c.weightx = 0.1;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = answersGridy;
        answerLabel.setFont(new Font("Arial", Font.PLAIN, 22));
        answerWidgets.add(answerLabel, c);

        AddingComponent newWidget = new AddingComponent();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = 1;
        c.gridy = answersGridy;
        answerWidgets.add(newWidget, c);

        answersGridy = answersGridy + 1;

        answerScrollPane.revalidate();
        answerScrollPane.repaint();
    }
}
