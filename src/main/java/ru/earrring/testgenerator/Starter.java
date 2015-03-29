package ru.earrring.testgenerator;

import ru.earrring.testgenerator.db.DBConnector;

import javax.swing.*;

/**
 * Класс-стартер системы. Инициализирует все классы, необходимые для работы программы и запускает основную форму
 */
public class Starter {

    private static MainFrame mainFrame;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
            new DBConnector().getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }

        mainFrame = new MainFrame();
        mainFrame.start();
    }

    public static MainFrame getMainFrame() {
        return mainFrame;
    }
}