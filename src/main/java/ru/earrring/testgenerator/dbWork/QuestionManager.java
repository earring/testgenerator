package ru.earrring.testgenerator.dbWork;

import ru.earrring.testgenerator.db.Answer;
import ru.earrring.testgenerator.db.DBFacade;
import ru.earrring.testgenerator.db.Question;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class QuestionManager {
    private static QuestionManager instance;

    /**
     * Функция получения объекта QuestionManager
     */
    public static QuestionManager getInstance() {
        if (instance == null)
            instance = new QuestionManager();
        return instance;
    }

    private List<IPresenter> presenters;

    private QuestionManager() {
        presenters = new ArrayList<>();
    }

    /**
     * Функция регистрации презентера, для того, чтобы, при добавлении и удалении вопроса, у презентера
     * вызывались соответствующие функции
     *
     * @param presenter презентер
     */
    public void registerPresenter(IPresenter presenter) {
        presenters.add(presenter);
    }

    /**
     * Функция, возвращающая все вопросы, имеющиеся в базе
     *
     * @return все вопросы, содержащиеся в базе данных
     */
    public List<Question> getAllQuestions() {
        List<Question> questions = new ArrayList<>();

        try {
            questions = DBFacade.getInstance().getAllQuestions();
        }
        catch (SQLException e) {
            onSqlException(e);
        }

        return questions;
    }

    /**
     * Функция добавления вопроса
     *
     * @param question вопрос. В вопросе коллекцию ответов НЕ ЗАПОЛНЯТЬ! Ответы добавляются самостоятельно, и
     *                 связываются они с вопросами тоже сами
     * @return @c true, если вопрос успешно добавлен, @c false - если произошла ошибка.
     */
    public boolean addQuestion(Question question, List<Answer> answerList) {
        try {
            DBFacade.getInstance().addQuestion(question);
            for (Answer answer : answerList) {
                DBFacade.getInstance().addAnswer(answer);
            }
            onQuestionAdded(question);
        }
        catch (SQLException e) {
            onSqlException(e);
        }
        return true;
    }

    /**
     * Функция удаления вопроса
     *
     * @param question вопрос
     * @return @c true, если вопрос успешно удален, @c false - если произошла ошибка.
     */
    public boolean removeQuestion(Question question)  {
        int id = question.getId();
        try {
            DBFacade.getInstance().deleteQuestion(id);
            onQuestionRemoved(question);
        } catch (SQLException e) {
            onSqlException(e);
        }


        return true;
    }

    /**
     * Функция получения количества вопросов
     */
    public int getQuestionCount() {
        int result = -1;
        try {
            result = DBFacade.getInstance().getAllQuestions().size();
        } catch (SQLException e) {
            onSqlException(e);
        }
        return result;
    }

    /**
     * Функция получения списка вопросов из категории
     *
     * @param category категория
     * @return список вопросов, в которых есть категория @c category
     */
    public List<Question> getQuestions(String category)  {
        List<Question> result = null;
        try {
            result = DBFacade.getInstance().findQuestionsByCategory(category);
        }
        catch (SQLException exception)
        {
            onSqlException(exception);
        }
        return result;
    }

    /**
     * Функция получения списка категорий из вопроса
     *
     * @param question вопрос
     * @return список категорий в вопросе
     */
    public List<String> getCategories(Question question) {
        List<String> result = new ArrayList<>();
        String categoriesList = question.getCategories();
        String[] categories = categoriesList.split("\\|");
        int catCount = categories.length;
        for (int j = 0; j < catCount; j++) {
            String questionCategory = categories[j];
            result.add(questionCategory);
        }
        return result;
    }

    /**
     * Функция получения списка вариантов ответа
     *
     * @param question вопрос
     * @return Список вариантов ответа
     */
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

    /**
     * Функция получения списка правильных ответов
     *
     * @param question вопрос
     * @return список правильных ответов
     */
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

    /**
     * Функция, запускаемая после добавления вопроса
     *
     * @param question добавленный вопрос
     */
    private void onQuestionAdded(Question question) {
        for (int i = 0; i < presenters.size(); i++) {
            IPresenter presenter = presenters.get(i);
            presenter.onQuestionAdded(question);
        }
    }

    /**
     * Функция, запускаемая после удаления вопроса
     *
     * @param question удаленный вопрос
     */
    private void onQuestionRemoved(Question question) {
        for (int i = 0; i < presenters.size(); i++) {
            IPresenter presenter = presenters.get(i);
            presenter.onQuestionRemoved(question);
        }
    }

    public void onSqlException(SQLException exception)
    {

    }


}
