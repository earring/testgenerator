package ru.earrring.testgenerator;

import ru.earrring.testgenerator.db.DBFacade;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class MainFramePresenter implements IPresenter {

    private MainFrame mainFrame;

    private ActionListener addingFormButtonAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    };

    private ActionListener viewingFormButtonAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    };

    private ActionListener generatingFormButtonAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    };

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

    public ActionListener getAddingFormButtonAction() {
        return addingFormButtonAction;
    }

    public ActionListener getViewingFormButtonAction() {
        return viewingFormButtonAction;
    }

    public ActionListener getGeneratingFormButtonAction() {
        return generatingFormButtonAction;
    }
}
