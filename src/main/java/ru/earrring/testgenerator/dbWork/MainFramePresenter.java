package ru.earrring.testgenerator.dbWork;

import ru.earrring.testgenerator.db.DBFacade;
import ru.earrring.testgenerator.db.Question;
import ru.earrring.testgenerator.frames.MainFrame;
import ru.earrring.testgenerator.frames.AFrame;

import javax.swing.*;
import java.sql.SQLException;

/**
 * Презентер основной формы, управляющий её содержимым, и так далее
 */
public class MainFramePresenter extends APresenter {

    /**
     * Ссылка на основную форму
     */
    private MainFrame mainFrame;
    private String textFormat = "<html>БД: <b> %s </b>; Вопросов в БД: <b> %d </b></html>";
    private String errorMessage = "<html><font color=#ff5555>БД недоступна!</font></html>";

    @Override
    public void setView(AFrame view) {
        mainFrame = (MainFrame) view;
    }

    private int getDBCount()
    {
        int questionCount = -1;
        try {
            questionCount = DBFacade.getInstance().getAllQuestions().size();
        }
        catch (SQLException e)
        {
        }
        return questionCount;

    }

    private void setText(int count) {
        String text;
        JLabel label = mainFrame.getDbInformationLabel();
        if (count != -1) {
            String dbName = DBFacade.getInstance().getDBName();
            text = String.format(textFormat,dbName,count);
        }
        else
            text = errorMessage;

        label.setText(text);
    }

    @Override
    public void onQuestionAdded(Question question) {
        int count = getDBCount();
        setText(count);
    }

    @Override
    public void onQuestionRemoved(Question question) {
        int count = getDBCount();
        setText(count);
    }

    @Override
    public void setListeners() {

    }

    @Override
    public void setInitialData() {
        // симулируем добавление вопроса для того, чтобы обновилась надпись о количестве вопросов
        onQuestionAdded(new Question());
    }
}
