package ru.earrring.testgenerator.frames;

import javax.swing.*;
import java.awt.*;

/**
 * Created by nenagleyko on 31.03.2015.
 */
public class AddingWidget extends JComponent {
    private JTextField answerText;
    private JCheckBox correctCheckBox;
    private JLabel textLabel;
    private JLabel checkBoxLabel;
    public AddingWidget(){
        super();
        setLayout(new GridBagLayout());

        textLabel = new JLabel("Текст ответа:");
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        add(textLabel, c);

        answerText = new JTextField(30);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        add(answerText, c);

        checkBoxLabel = new JLabel("Ответ правильный:");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        add(checkBoxLabel, c);

        correctCheckBox = new JCheckBox();
        correctCheckBox.setSelected(false);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 1;
        add(correctCheckBox, c);


    }
}