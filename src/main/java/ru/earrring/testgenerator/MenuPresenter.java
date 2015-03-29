package ru.earrring.testgenerator;

import ru.earrring.testgenerator.db.DBFacade;
import ru.earrring.testgenerator.db.IPresenter;

import javax.swing.*;
import java.sql.SQLException;

/**
 * Created by nenagleyko on 30.03.2015.
 */
public class MenuPresenter implements IPresenter{
    private MenuFrame menuFrame;
    private JLabel dbInfoLabel;
    private String dbInfoText = "Количество вопросов в базе: ";
    @Override
    public void setView(AFrame view) {
        menuFrame = (MenuFrame)view;
        dbInfoLabel = new JLabel(dbInfoText);
        menuFrame.addWidget(dbInfoLabel);
    }

    @Override
    public void onQuestionAdded() {
        try {
            int questionCount = DBFacade.getInstance(false).getAllQuestions().size();
            dbInfoLabel.setText(String.format("%s%d",dbInfoText,questionCount));
        }
        catch(SQLException e)
        {

        }

    }

    @Override
    public void onQuestionRemoved() {

    }
}
