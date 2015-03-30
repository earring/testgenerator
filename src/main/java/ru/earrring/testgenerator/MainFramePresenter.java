package ru.earrring.testgenerator;

import ru.earrring.testgenerator.db.DBFacade;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class MainFramePresenter extends APresenter {

    private MainFrame mainFrame;

    @Override
    public void setView(AFrame view) {
        mainFrame = (MainFrame) view;
    }

    @Override
    public void onQuestionAdded() {
        try {
            String dbName = DBFacade.getInstance(false).getDBName();
            int questionCount = DBFacade.getInstance(false).getAllQuestions().size();
            mainFrame.getDbInformationLabel().setText("<html>БД: <b>" + dbName + "</b>; Вопросов в БД: <b>" + questionCount + "</b></html>");
        } catch (SQLException e) {
            mainFrame.getDbInformationLabel().setText("<html><font color=#ff5555>БД недоступна!</font></html>");
        }
    }

    @Override
    public void onQuestionRemoved() {

    }

    @Override
    public void setListeners() {
        mainFrame.getAddingFormButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        mainFrame.getViewingFormButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        mainFrame.getGeneratingFormButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    @Override
    public void setInitialData() {
        // симулируем добавление вопроса для того, чтобы обновилась надпись о количестве вопросов
        onQuestionAdded();
    }
}
