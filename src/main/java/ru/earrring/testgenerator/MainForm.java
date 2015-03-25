package ru.earrring.testgenerator;

import javax.swing.*;
import java.awt.*;

public class MainForm extends JFrame {

    private JLabel pictureLabel;
    private JButton addingFormButton;
    private JButton viewingFormButton;
    private JButton generatingFormButton;
    private JLabel versionLabel;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        new MainForm().setVisible(true);
    }

    public MainForm() {
        initialize();
        adjustLayout();
    }

    private void initialize() {
        setSize(500, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Генератор тестов");
        setCenter();
        setResizable(false);
        setIconImage(Utils.getImageFromResources("icon_image.png"));
    }

    private void adjustLayout() {
        setLayout(new GridBagLayout());

        pictureLabel = new JLabel(new ImageIcon(Utils.getImageFromResources("menu_picture.png")));
        pictureLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10, 10, 10, 10);
        c.anchor = GridBagConstraints.NORTH;
        c.weighty = 1.0;
        add(pictureLabel, c);

        addingFormButton = new JButton("Добавить вопрос");
        setButtonConstraints(addingFormButton, 1);

        viewingFormButton = new JButton("Просмотр вопросов");
        setButtonConstraints(viewingFormButton, 2);

        generatingFormButton = new JButton("Генерация тестов");
        setButtonConstraints(generatingFormButton, 3);

        versionLabel = new JLabel(Utils.VERSION);
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 4;
        c.insets = new Insets(10, 10, 10, 10);
        c.anchor = GridBagConstraints.SOUTH;
        c.weighty = 1.0;
        add(versionLabel, c);
    }

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

    private void setCenter() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dimension.width / 2 - getWidth() / 2, dimension.height / 2 - getHeight() / 2);
    }
}