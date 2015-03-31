package ru.earrring.testgenerator.frames;

import ru.earrring.testgenerator.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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
     * Лейбл, показывающий текующую версию программы
     */
    private JLabel versionLabel;

    /**
     * Панель, на которую добавляются кнопки меню
     */
    private JPanel buttonsPanel;

    /**
     * Счетчик для позиции строки в лэйауте всей формы
     */
    private int gridy = 0;

    /**
     * Счетчик для позиции строки в панели кнопок меню
     */
    private int buttonsPanelGridy = 0;

    @Override
    protected void adjustFrameSettings() {
        setResizable(false);
        setSize(500, 550);
        setTitle("Генератор тестов");
    }

    @Override
    protected void adjustLayout() {
        setLayout(new GridBagLayout());

        // настройка картинки в меню
        pictureLabel = new JLabel(new ImageIcon(Utils.getImageFromResources("menu_picture.png")));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        addWidget(pictureLabel, c);

        // настройка надписи с информацией о БД
        dbInformationLabel = new JLabel();
        c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        addWidget(dbInformationLabel, c);

        // настройка панели с кнопками запуска других форм
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        addWidget(buttonsPanel, c);

        // добавление надписи с версией программы
        versionLabel = new JLabel(Utils.VERSION);
        c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);
        addWidget(versionLabel, c);
    }

    @Override
    protected void setListeners() {

    }

    @Override
    public void close() {
        System.exit(0);
    }

    /**
     * Добавление кнопки меню в главную панель и настройка обработчика нажатий этой кнопки
     *
     * @param frame форма, которую нужно сделать видимой по нажатию созданной кнопки
     * @param text  текст на кнопке
     */
    public void addFrameButton(AFrame frame, String text) {
        JButton button = new JButton(text);
        setButtonConstraints(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(true);
                frame.setParentFrame(MainFrame.this);
                MainFrame.this.setVisible(false);
            }
        });
    }


    /**
     * Вспомогательный класс, помогающий настроить местоположение кнопки меню в главной форме
     *
     * @param button кнопка, которая настраивается
     */
    private void setButtonConstraints(JButton button) {
        GridBagConstraints c = new GridBagConstraints();
        button.setFont(new Font("Arial", Font.PLAIN, 22));
        c.insets = new Insets(10, 10, 10, 10);
        c.ipadx = 150;
        c.ipady = 20;
        c.fill = GridBagConstraints.BOTH;
        addWidgetToButtonPanel(button, c);
    }

    /**
     * Добавление компонента на главную форму
     *
     * @param component добавляемый компонент
     * @param c         настройки для GridBagLayout
     */
    private void addWidget(JComponent component, GridBagConstraints c) {
        //component.setBorder(new WindowsBorders.DashedBorder(Color.BLACK));
        c.gridx = 0;
        c.gridy = gridy;
        add(component, c);
        gridy++;
    }

    /**
     * Добавление компонента в панель кнопок меню на главной форме
     *
     * @param component добавляемый компонент
     * @param c         настройки для GridBagLayout
     */
    private void addWidgetToButtonPanel(JComponent component, GridBagConstraints c) {
        c.gridx = 0;
        c.gridy = buttonsPanelGridy;
        buttonsPanel.add(component, c);
        buttonsPanelGridy++;
    }

    public JLabel getDbInformationLabel() {
        return dbInformationLabel;
    }
}