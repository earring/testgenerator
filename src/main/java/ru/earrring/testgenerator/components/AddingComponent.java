package ru.earrring.testgenerator.components;

import javax.swing.*;
import java.awt.*;

/**
 * Компонент, отвечающий за добавление одного "варианта" ответа. Добавляется динамически в форме добавления вопроса
 */
public class AddingComponent extends JPanel {

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

    public AddingComponent() {
        super();
        setLayout(new GridBagLayout());

        // добавление надписи рядом с текстовым полем
        textLabel = new JLabel("Текст ответа:");
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        add(textLabel, c);

        // добавление текстового поля ответа
        answerText = new JTextField(30);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        add(answerText, c);

        // добавление надписи рядом с чекбоксом правильности ответа
        checkBoxLabel = new JLabel("Ответ правильный:");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        add(checkBoxLabel, c);

        // добавление чекбокса правильности ответа
        correctCheckBox = new JCheckBox();
        correctCheckBox.setSelected(false);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 1;
        add(correctCheckBox, c);
    }
}
