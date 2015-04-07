package ru.earrring.testgenerator.dbWork;

import ru.earrring.testgenerator.db.Answer;
import ru.earrring.testgenerator.db.DBFacade;
import ru.earrring.testgenerator.db.Question;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by nenagleyko on 06.04.2015.
 */
public class QuestionManager {
    private static QuestionManager instance;

    public static QuestionManager getInstance() {
        if (instance == null)
            instance = new QuestionManager();
        return instance;
    }

    private List<IPresenter> presenters;

    private QuestionManager() {
        presenters = new ArrayList<>();
    }

    public void registerPresenter(IPresenter presenter) {
        presenters.add(presenter);
    }

    public boolean addQuestion(Question question) {
        try {
            DBFacade.getInstance().addQuestion(question);
            onQuestionAdded(question);
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public boolean removeQuestion(Question question) {
        try {
            int id = question.getId();
            DBFacade.getInstance().deleteQuestion(id);
            onQuestionRemoved(question);
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public int getQuestionCount() {
        int result = -1;
        try {
            result = DBFacade.getInstance().getAllQuestions().size();
        } catch (SQLException e) {

        }
        return result;
    }

    public List<Question> getQuestions(String category) {
        List<Question> result = new ArrayList<>();
        try {
            List<Question> allQuestions =
                    DBFacade.getInstance().getAllQuestions();
            int count = allQuestions.size();
            for (int i = 0; i < count; i++) {
                Question question = allQuestions.get(i);
                String categoriesList = question.getCategories();
                String[] categories = categoriesList.split("\\|");
                int catCount = categories.length;
                for (int j = 0; j < catCount; j++) {
                    String questionCategory = categories[j];
                    if (category.equals(questionCategory))
                        result.add(question);
                }
            }
        } catch (SQLException e) {

        }
        return result;
    }

    public List<String> getCategories(Question question) {
        List<String> result = new ArrayList<>();
        String categoriesList = question.getCategories();
        String[] categories = categoriesList.split("|");
        int catCount = categories.length;
        for (int j = 0; j < catCount; j++) {
            String questionCategory = categories[j];
            result.add(questionCategory);
        }
        return result;
    }

    public List<String> getVariants(Question question) {
        List<String> result = new ArrayList<>();
        Collection<Answer> variants = question.getAnswers();
        Iterator<Answer> iterator = variants.iterator();
        while (iterator.hasNext()) {
            Answer answer = iterator.next();
            result.add(answer.getValue());

        }
        return result;

    }

    public List<String> getAnswers(Question question) {
        List<String> result = new ArrayList<>();
        Collection<Answer> variants = question.getAnswers();
        Iterator<Answer> iterator = variants.iterator();
        while (iterator.hasNext()) {
            Answer answer = iterator.next();
            if (answer.isCorrect())
                result.add(answer.getValue());

        }
        return result;
    }

    private void onQuestionAdded(Question question) {
        for (int i = 0; i < presenters.size(); i++) {
            IPresenter presenter = presenters.get(i);
            presenter.onQuestionAdded(question);
        }
    }

    private void onQuestionRemoved(Question question) {
        for (int i = 0; i < presenters.size(); i++) {
            IPresenter presenter = presenters.get(i);
            presenter.onQuestionRemoved(question);
        }
    }

}
