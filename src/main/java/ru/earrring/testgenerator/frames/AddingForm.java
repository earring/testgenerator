package ru.earrring.testgenerator.frames;

import javax.swing.*;
import java.awt.*;
/**
 * Created by nenagleyko on 29.03.2015.
 */
public class AddingForm extends AFrame {

    private JLabel descriptionLabel;
    private JLabel addVariantLabel;
    private JTextArea descriptionArea;

    protected void adjustFrameSettings()
    {
        setSize(500, 500);
        setTitle("Добавить вопрос");

    }
    protected void adjustLayout()
    {
        setLayout(new GridBagLayout());

        //добавление надписи над текстом вопроса
        descriptionLabel = new JLabel("Текст вопроса:");

        addWidget(descriptionLabel,0);

        descriptionArea = new JTextArea("Текст вопроса...");
        descriptionArea.setSize(400,200);

        addWidget(descriptionArea, 1);

        addVariantLabel = new JLabel("Добавить вариант ответа:");

    }

    /**
     * Настройка слушателей (кнопок и других компонентов)
     */
    protected void setListeners()
    {

    }

    private void addWidget(JComponent component, int gridy)
    {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = gridy;
        c.insets = new Insets(10, 100, 10, 100);
        c.anchor = GridBagConstraints.NORTH;
        c.weighty = 1.0;
        add(component, c);
    }
}
