package ru.earrring.testgenerator;

import ru.earrring.testgenerator.db.DBFacade;
import ru.earrring.testgenerator.dbWork.GenerationFramePresenter;
import ru.earrring.testgenerator.dbWork.MainFramePresenter;
import ru.earrring.testgenerator.dbWork.QuestionManager;
import ru.earrring.testgenerator.frames.AddingFrame;
import ru.earrring.testgenerator.frames.GenerationFrame;
import ru.earrring.testgenerator.frames.LaTeXDemonstrationFrame;
import ru.earrring.testgenerator.frames.MainFrame;

import javax.swing.*;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Класс-стартер системы. Инициализирует все классы, необходимые для работы программы и запускает основную форму
 */
public class Starter {

    /**
     * Ссылка на основную, первую показываемую пользователю форму
     */
    private static MainFrame mainFrame;

    /**
     * Ссылка на форму добавления нового вопроса
     */
    private static AddingFrame addingFrame;

    private static GenerationFrame generationFrame;

    /**
     * Ссылка на форму демонстрации возможностей LaTeX в этой программе
     */
    private static LaTeXDemonstrationFrame laTeXDemonstrationFrame;

    /**
     * Ссылка на фасад БД. Позже надо будет убрать, и обращаться к QuestionManager
     */
    private static DBFacade dbFacade;

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    /**
     * Инициализирующий метод
     */
    private static void init() {
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
            dbFacade = DBFacade.getInstance();
            dbFacade.init("jdbc:sqlite:main.sqlite");

            // настройка основной формы
            mainFrame = new MainFrame();
            mainFrame.start();
            MainFramePresenter mainFramePresenter = new MainFramePresenter();
            mainFramePresenter.setUp(mainFrame);
            QuestionManager.getInstance().registerPresenter(mainFramePresenter);

            // настройка формы добавления вопросов
            addingFrame = new AddingFrame();
            addingFrame.start();


            generationFrame = new GenerationFrame();
            generationFrame.start();
            GenerationFramePresenter generationPresenter = new GenerationFramePresenter();
            generationPresenter.setUp(generationFrame);
            QuestionManager.getInstance().registerPresenter(generationPresenter);

            // настройка формы демонстрации LaTeX
            laTeXDemonstrationFrame = new LaTeXDemonstrationFrame();
            laTeXDemonstrationFrame.start();

            // добавление кнопок на основную форму
            mainFrame.addFrameButton(addingFrame, "Добавить вопрос");
            mainFrame.addFrameButton(generationFrame, "Генерация тестов");
            mainFrame.addFrameButton(laTeXDemonstrationFrame, "Демонстрация LaTeX");
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
