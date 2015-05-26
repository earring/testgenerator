package ru.earrring.testgenerator.components;

import ru.earrring.testgenerator.frames.AnswerAddableFrame;

import javax.swing.*;
import javax.swing.border.LineBorder;
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
     *
     * @param number    номер ответа на вопров (нумерация начинается с нуля)
     * @param text      текст ответа на вопрос
     * @param isCorrect верный ли ответ на вопрос
     */
    public AnswerVariantComponent(AnswerAddableFrame frame, int number, String text, boolean isCorrect) {
        super();
        GroupLayout layout = new GroupLayout(this);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        setLayout(layout);

        this.answerFrame = frame;
        this.number = number;

        // добавление надписи рядом с текстовым полем
        textLabel = new JLabel("Текст ответа:");

        // добавление текстового поля ответа
        answerText = new JTextField(30);
        answerText.setText(text);
        answerText.setFont(new Font("Serif", Font.ITALIC, 16));

        // добавление надписи рядом с чекбоксом правильности ответа
        checkBoxLabel = new JLabel("Правильность");

        // добавление чекбокса правильности ответа
        correctCheckBox = new JCheckBox();
        correctCheckBox.setSelected(isCorrect);

        numberLabel = new JLabel(String.format("%d)", number));
        numberLabel.setFont(new Font("Arial", Font.PLAIN, 22));
        numberLabel.setHorizontalAlignment(JLabel.CENTER);
        numberLabel.setVerticalAlignment(JLabel.CENTER);

        // добавление кнопки удаления вопроса
        deleteButton = new JButton("Удалить");
        deleteButton.setFont(new Font("Arial", Font.PLAIN, 16));

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addComponent(numberLabel)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(textLabel)
                                .addComponent(answerText))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(checkBoxLabel)
                                .addComponent(correctCheckBox))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(deleteButton))
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(numberLabel)
                                .addComponent(textLabel)
                                .addComponent(checkBoxLabel))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(answerText)
                                .addComponent(correctCheckBox)
                                .addComponent(deleteButton))
        );

        setMaximumSize(getPreferredSize());

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