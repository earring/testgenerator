package ru.earrring.testgenerator.dbWork;

import ru.earrring.testgenerator.db.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nenagleyko on 06.04.2015.
 */
public class QuestionManager{
    private static QuestionManager instance;
    public static QuestionManager getInstance()
    {
        if (instance == null)
            instance = new QuestionManager();
        return instance;
    }

    private List<IPresenter> presenters;

    private QuestionManager()
    {
        presenters = new ArrayList<>();
    }
    public void registerPresenter(IPresenter presenter)
    {
        presenters.add(presenter);
    }

    public boolean addQuestion(Question question)
    {
//        DBFacade.getInstance();
        onQuestionAdded(question);
        return false;
    }

    public boolean removeQuestion(Question question)
    {
        onQuestionRemoved(question);
        return false;
    }

    public int getQuestionCount()
    {
        return 0;
    }

    public List<Question> getQuestions(String category)
    {
        return new ArrayList<Question>();
    }

    public List<String> getCategories(Question question)
    {
        return new ArrayList<>();
    }

    public List<String> getVariants(Question question)
    {
        return new ArrayList<>();
    }

    public List<String> getAnswers(Question question)
    {
        return new ArrayList<>();
    }

    private void onQuestionAdded(Question question)
    {
        for (int i = 0; i<presenters.size(); i++)
        {
            IPresenter presenter = presenters.get(i);
            presenter.onQuestionAdded(question);
        }
    }

    private void onQuestionRemoved(Question question)
    {
        for (int i = 0; i<presenters.size(); i++)
        {
            IPresenter presenter = presenters.get(i);
            presenter.onQuestionRemoved(question);
        }

    }




}
