package ru.earrring.testgenerator.components;

import ru.earrring.testgenerator.frames.AnswerAddableFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Компонент, отвечающий за добавление одного "варианта" ответа. Добавляется динамически в форме добавления вопроса
 */
public class AnswerVariantComponent extends JPanel {

    /**
     * Номер ответа в вопросе (нумерация с единицы)
     */
    private int number;

    private JLabel numberLabel;

    /**
     * Текст ответа
     */
    private JTextField answerText;

    /**
     * Чекбокс, показывающий, правильный этот вариант ответа или нет. Таких правильных ответов может быть несколько
     */
    private JCheckBox correctCheckBox;

    /**
     * Надпись рядом с текстовым полем
     */
    private JLabel textLabel;

    /**
     * Надпись рядом с чекбоксом
     */
    private JLabel checkBoxLabel;

    /**
     * Кнопка удаления варианта ответа
     */
    private JButton deleteButton;

    /**
     * Ссылка на форму, содержащую нечто, что позволяет удалять \ добавлять варианты вопросов
     */
    private AnswerAddableFrame answerFrame;

    /**
     * Конструктор варианта ответа на вопрос
     * @param number номер ответа на вопров (нумерация начинается с нуля)
     * @param text текст ответа на вопрос
     * @param isCorrect верный ли ответ на вопрос
     */
    public AnswerVariantComponent(AnswerAddableFrame frame, int number, String text, boolean isCorrect) {
        super();
        setLayout(new GridBagLayout());

        this.answerFrame = frame;
        this.number = number;

        GridBagConstraints c = new GridBagConstraints();

        // добавление надписи рядом с текстовым полем
        textLabel = new JLabel("Текст ответа:");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        add(textLabel, c);

        // добавление текстового поля ответа
        answerText = new JTextField(30);
        answerText.setText(text);
        answerText.setFont(new Font("Serif", Font.ITALIC, 16));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 0;
        add(answerText, c);


        // добавление надписи рядом с чекбоксом правильности ответа
        checkBoxLabel = new JLabel("Ответ правильный:");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        add(checkBoxLabel, c);

        // добавление чекбокса правильности ответа
        correctCheckBox = new JCheckBox();
        correctCheckBox.setSelected(isCorrect);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 2;
        c.gridy = 1;
        add(correctCheckBox, c);

        numberLabel = new JLabel(String.format("%d)", number));
        numberLabel.setFont(new Font("Arial", Font.PLAIN, 22));
        numberLabel.setHorizontalAlignment(JLabel.CENTER);
        numberLabel.setVerticalAlignment(JLabel.CENTER);
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.1;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 2;
        add(numberLabel, c);

        // добавление кнопки удаления вопроса
        deleteButton = new JButton("Удалить");
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.5;
        c.gridx = 3;
        c.gridy = 0;
        c.gridheight = 2;
        add(deleteButton, c);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                answerFrame.deleteAnswerVariantComponent(AnswerVariantComponent.this.number);
            }
        });
    }

    public void setNumber(int number) {
        this.number = number;
        numberLabel.setText(String.format("%d)", number));
    }

    public String getText() {
        return answerText.getText();
    }

    public boolean isCorrect() {
        return correctCheckBox.isSelected();
    }
}