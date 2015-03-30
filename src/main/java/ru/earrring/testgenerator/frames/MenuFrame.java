package ru.earrring.testgenerator.frames;

import ru.earrring.testgenerator.Utils;
import ru.earrring.testgenerator.frames.AFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by nenagleyko on 30.03.2015.
 */
public class MenuFrame extends AFrame {
    /**
     * Лейбл с картинкой для меню
     */
    private JLabel pictureLabel;


    private int gridy = 1;

    /**
     * Вспомогательный класс, помогающий настроить местоположение кнопки меню в главной форме
     * @param button кнопка, которая настраивает
     */
    private void setButtonConstraints(JButton button) {

        GridBagConstraints c = new GridBagConstraints();
        button.setFont(new Font("Arial", Font.PLAIN, 22));
        c.gridx = 0;
        c.insets = new Insets(10, 100, 10, 100);
        c.ipadx = 100;
        c.ipady = 20;
        c.fill = GridBagConstraints.BOTH;

        addWidget(button, c);
    }

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
        c.gridx = 0;
        c.insets = new Insets(10, 10, 10, 10);
        c.anchor = GridBagConstraints.NORTH;
        c.weighty = 1.0;
        addWidget(pictureLabel, c);
    }

    @Override
    protected void setListeners(){

    }

    public void addButton(String text,
                          AFrame frame){
        JButton button = new JButton(text);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(true);
            }
        });
        setButtonConstraints(button);
    }

    public void addWidget(JComponent widget){

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.insets = new Insets(10, 10, 10, 10);
        c.anchor = GridBagConstraints.NORTH;
        c.weighty = 1.0;

        addWidget(widget,c);

    }
    public void addWidget(JComponent widget, GridBagConstraints c){
        c.gridy = gridy;
        add(widget, c);
        gridy = gridy + 1;
    }

}
