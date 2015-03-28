package ru.earrring.testgenerator;

import ru.earrring.testgenerator.db.DBConnector;

import javax.swing.*;

/**
 * Класс-стартер системы. Инициализирует все классы, необходимые для работы программы и запускает основную форму
 */
public class Starter {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
            new DBConnector().getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        new MainFrame().start();
    }
}
