package ru.earrring.testgenerator.frames;

import ru.earrring.testgenerator.Utils;
import ru.earrring.testgenerator.db.DBFacade;
import ru.earrring.testgenerator.frames.AFrame;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Форма в виде "меню", на которой пользователь выбирает свои дальнейшие действия
 */
public class MainFrame extends AFrame {

    /**
     * Лейбл с картинкой для меню
     */
    private JLabel pictureLabel;

    /**
     * Лейбл с именем БД и информацией о ней
     */
    private JLabel dbInformationLabel;

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

    private int gridy = 1;
    @Override
    protected void adjustFrameSettings() {
        setSize(500, 500);
        setTitle("Генератор тестов");
    }

    @Override
    protected void adjustLayout() {
        setLayout(new GridBagLayout());

        // настройка картинки в меню
        pictureLabel = new JLabel(new ImageIcon(Utils.getImageFromResources("menu_picture.png")));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.anchor = GridBagConstraints.NORTH;
        c.weighty = 1.0;
        addWidget(pictureLabel, c);

        dbInformationLabel = new JLabel();
        dbInformationLabel.setHorizontalAlignment(JLabel.CENTER);
        c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 0.5;
        addWidget(dbInformationLabel, c);

        /*
        viewingFormButton = new JButton("Просмотр вопросов");
        setButtonConstraints(viewingFormButton, 3);
        generatingFormButton = new JButton("Генерация тестов");
        setButtonConstraints(generatingFormButton, 4);
        */

        // добавление надписи с версией
        versionLabel = new JLabel(Utils.VERSION);
        c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        c.anchor = GridBagConstraints.SOUTH;
        c.weighty = 1.0;
        addWidget(versionLabel, c);
    }

    @Override
    protected void setListeners() {

    }

    public void addFrameButton(AFrame frame, String text)
    {
        JButton button = new JButton(text);
        setButtonConstraints(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(true);
            }
        });
    }


    /**
     * Вспомогательный класс, помогающий настроить местоположение кнопки меню в главной форме
     * @param button кнопка, которая настраивается
     * @param gridy номер ячейки в таблице (начинается с 1, потому что в 0й ячейке находится картинка меню)
     */
    private void setButtonConstraints(JButton button) {
        GridBagConstraints c = new GridBagConstraints();
        button.setFont(new Font("Arial", Font.PLAIN, 22));
        c.insets = new Insets(10, 100, 10, 100);
        c.ipadx = 150;
        c.ipady = 20;
        c.fill = GridBagConstraints.BOTH;
        addWidget(button, c);
    }

    private void addWidget(JComponent component, GridBagConstraints c)
    {
        c.gridx = 0;
        c.gridy = gridy;

        add(component,c);
        gridy = gridy +1;
    }
    public JLabel getDbInformationLabel() {
        return dbInformationLabel;
    }

    public JButton getAddingFormButton() {
        return addingFormButton;
    }

    public JButton getViewingFormButton() {
        return viewingFormButton;
    }

    public JButton getGeneratingFormButton() {
        return generatingFormButton;
    }
}