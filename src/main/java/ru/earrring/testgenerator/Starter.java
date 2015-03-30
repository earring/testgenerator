package ru.earrring.testgenerator;

import ru.earrring.testgenerator.db.DBFacade;
import ru.earrring.testgenerator.frames.AddingFrame;
import ru.earrring.testgenerator.frames.MainFrame;
import javax.swing.*;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Класс-стартер системы. Инициализирует все классы, необходимые для работы программы и запускает основную форму
 */
public class Starter {

    private static MainFrame mainFrame;
    private static DBFacade dbFacade;

    private static void init()
    {
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
            dbFacade = DBFacade.getInstance(false);
            mainFrame = new MainFrame();

            AddingFrame addingFrame = new AddingFrame();
            mainFrame.addFrameButton(addingFrame,"Добавить вопрос");

        } catch (Exception e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            e.printStackTrace();
            JOptionPane.showMessageDialog(mainFrame, e.toString(), e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }
    public static void main(String[] args) {
        init();
        mainFrame.setVisible(true);
    }


}
