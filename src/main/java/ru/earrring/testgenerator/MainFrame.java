package ru.earrring.testgenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Форма в виде "меню", на которой пользователь выбирает свои дальнейшие действия
 */
public class MainFrame extends AFrame {

    /**
     * Лейбл с картинкой для меню
     */
    private JLabel pictureLabel;
    /**
     * Кнопка перехода на форму добавления вопроса
     */
    private JButton addingFormButton;
    /**
     * Кнопка перехода на форму просмотра вопросов
     */
    private JButton viewingFormButton;
    /**
     * Кнопка перехода на форму генерации вопросов
     */
    private JButton generatingFormButton;
    /**
     * Лейбл, показывающий текующую версию программы
     */
    private JLabel versionLabel;

    protected void adjustFrameSettings() {
        setSize(500, 500);
        setTitle("Генератор тестов");
    }


    protected void adjustLayout() {
        setLayout(new GridBagLayout());

        // настройка картинки в меню
        pictureLabel = new JLabel(new ImageIcon(Utils.getImageFromResources("menu_picture.png")));
        pictureLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10, 10, 10, 10);
        c.anchor = GridBagConstraints.NORTH;
        c.weighty = 1.0;
        add(pictureLabel, c);

        // добавление кнопок меню
        addingFormButton = new JButton("Добавить вопрос");
        setButtonConstraints(addingFormButton, 1);
        viewingFormButton = new JButton("Просмотр вопросов");
        setButtonConstraints(viewingFormButton, 2);
        generatingFormButton = new JButton("Генерация тестов");
        setButtonConstraints(generatingFormButton, 3);

        // добавление надписи с версией
        versionLabel = new JLabel(Utils.VERSION);
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 4;
        c.insets = new Insets(10, 10, 10, 10);
        c.anchor = GridBagConstraints.SOUTH;
        c.weighty = 1.0;
        add(versionLabel, c);
    }

    protected void setListeners() {
        addingFormButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        viewingFormButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        generatingFormButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    /**
     * Вспомогательный класс, помогающий настроить местоположение кнопки меню в главной форме
     * @param button кнопка, которая настраивается
     * @param gridy номер ячейки в таблице (начинается с 1, потому что в 0й ячейке находится картинка меню)
     */
    private void setButtonConstraints(JButton button, int gridy) {
        GridBagConstraints c = new GridBagConstraints();
        button.setFont(new Font("Arial", Font.PLAIN, 22));
        c.gridx = 0;
        c.gridy = gridy;
        c.insets = new Insets(10, 100, 10, 100);
        c.ipadx = 150;
        c.ipady = 20;
        c.fill = GridBagConstraints.BOTH;
        add(button, c);
    }
}