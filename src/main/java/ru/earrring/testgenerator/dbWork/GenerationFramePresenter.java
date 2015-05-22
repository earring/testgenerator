package ru.earrring.testgenerator.dbWork;

import ru.earrring.testgenerator.db.DBFacade;
import ru.earrring.testgenerator.db.Question;
import ru.earrring.testgenerator.frames.AFrame;
import ru.earrring.testgenerator.frames.GenerationFrame;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nenagleyko on 22.05.2015.
 */
public class GenerationFramePresenter extends APresenter {
    private GenerationFrame generationFrame;
    @Override
    public void setView(AFrame view) {
        generationFrame = (GenerationFrame)view;

    }

    @Override
    public void setListeners() {

    }

    @Override
    public void setInitialData() {
        List<String> categories = getCategories();
        generationFrame.setCategories(categories);
    }

    @Override
    public void onQuestionAdded(Question question) {
        setInitialData();
    }

    @Override
    public void onQuestionRemoved(Question question) {
        setInitialData();
    }

    private List<String> getCategories()
    {
        ArrayList<String> categories = new ArrayList<>();


        List<Question> questions = QuestionManager.getInstance().getAllQuestions();
        for (int i = 0; i<questions.size(); ++i)
        {
            Question question = questions.get(i);
            List<String> questionCategories = QuestionManager.getInstance().getCategories(question);
            for (int j = 0; j<questionCategories.size(); ++j)
            {
                String category = questionCategories.get(j);
                if (!categories.contains(category))
                    categories.add(category);
            }

        }


        return categories;
    }

}
