package ru.earrring.testgenerator;

import ru.earrring.testgenerator.db.DBFacade;
import ru.earrring.testgenerator.db_work.MenuPresenter;
import ru.earrring.testgenerator.frames.AddingForm;
import ru.earrring.testgenerator.frames.MenuFrame;

import javax.swing.*;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Класс-стартер системы. Инициализирует все классы, необходимые для работы программы и запускает основную форму
 */
public class Starter {

    private static MenuFrame mainFrame;
    private static DBFacade dbFacade;

    private static void init()
    {
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
            dbFacade = DBFacade.getInstance(false);
            mainFrame = new MenuFrame();

            AddingForm addingForm = new AddingForm();
            mainFrame.addButton("Добавить вопрос1", addingForm);

            MenuPresenter presenter = new MenuPresenter();
            presenter.setView(mainFrame);

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
