package ru.earrring.testgenerator.db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Фасад БД. Вызывая функции этого класса, можно выполнить непосредственную работу с БД (фасад работает с ORM). Для
 * упрощения работы с БД, этот класс не используется напрямую, а функции этого фасада вызываются классом QuestionManager.
 * Двойной синглтон, возвращается тот фасад (пользовательской БД или тестовой БД), который задан в методе getInstance()
 */
public class DBFacade {

    /**
     * Адрес доступа к БД. Формат: jdbc:sqlite:test.sqlite, разделен двоеточиями, где первая часть: jdbc - протокол
     * доступа к БД, вторая: sqlite - тип БД, третья: test.sqlite - название файла БД
     */
    private String url;

    /**
     * Объект "соединения с БД"
     */
    private ConnectionSource source;

    /**
     * DAO, предназначенный для работы с сущностью "Вопрос" или Question. Любезно предоставлен ORM.
     */
    private Dao<Question, Integer> daoQuestion;

    /**
     * DAO, предназначенный для работы с сущностью "Ответ на вопрос" или Answer. Любезно предоставлен ORM.
     */
    private Dao<Answer, Integer> daoAnswer;

    /**
     * Внутренняя ссылка для хранения фасада БД для обычной (пользовательской) работы с БД "main.sqlite"
     */
    private static DBFacade dbFacade;

    public void init() throws SQLException
    {
        source = new JdbcConnectionSource(url);
        // создать таблицы, если не существует старых
        TableUtils.createTableIfNotExists(source, Question.class);
        TableUtils.createTableIfNotExists(source, Answer.class);

        daoQuestion = DaoManager.createDao(source, Question.class);
        daoAnswer = DaoManager.createDao(source, Answer.class);
    }
    public void setUrl(String url)
    {
        this.url = url;
    }
    /**
     * Запрос экземпляра фасада БД в зависимости от переданной булевской переменной
     *
     * @return ссылка на фасад БД
     * @throws SQLException
     */
    public static DBFacade getInstance()  {
        if (dbFacade == null) {
            dbFacade = new DBFacade();
        }
        return dbFacade;
    }

    /**
     * Возвращает имя базы данных с расширением, например "main.sqlite"
     *
     * @return имя базы данных с расширением
     */
    public String getDBName() {
        String url = ((JdbcConnectionSource) source).getUrl();
        String[] urlParts = url.split(":");
        return urlParts[2];
    }

    /**
     * Добавление вопроса в БД
     *
     * @param question вопрос, добавляемый в БД
     * @throws SQLException
     */
    public void addQuestion(Question question) throws SQLException {
        TransactionManager.callInTransaction(source,
                () -> {
                    // выбор наименьшего незанятого id
                    int idIndex = 1;
                    while (daoQuestion.queryForId(idIndex) != null) {
                        idIndex++;
                    }
                    question.setId(idIndex);
                    int rowsChanged = daoQuestion.create(question);
                    if (rowsChanged != 1) {
                        throw new SQLException("adding question " + question + " is not succesful!");
                    }
                    return null;
                });
    }

    /**
     * Запрос всех вопросов в БД
     *
     * @return список всех вопросов
     * @throws SQLException
     */
    public List<Question> getAllQuestions() throws SQLException {
        return daoQuestion.queryForAll();
    }

    /**
     * Запрос определенного вопроса по его идентификатору
     *
     * @param id идентификатор вопроса
     * @return вопрос, соответствующий переданному идентификатору
     * @throws SQLException
     */
    public Question getQuestionById(int id) throws SQLException {
        return daoQuestion.queryForId(id);
    }

    /**
     * Поиск вопросов по подстроке в описании вопроса
     *
     * @param description подстрока для поиска в описании вопроса
     * @return список найденных вопрсов
     * @throws SQLException
     */
    public List<Question> findQuestionsByDescription(String description) throws SQLException {
        List<Question> questionList = getAllQuestions();
        List<Question> questionResultList = new ArrayList<Question>();
        for (Question question : questionList) {
            if (question.getDescription().contains(description)) {
                questionResultList.add(question);
            }
        }
        return questionResultList;
    }

    /**
     * Обновление вопроса в БД
     *
     * @param question обновляемый вопрос (новые данные). Вопрос, который нужно обновить, определяется по идентификатору
     * @throws SQLException
     */
    public void updateQuestion(Question question) throws SQLException {
        daoQuestion.update(question);
    }

    /**
     * Удаление вопроса из БД
     *
     * @param id идентификатор удаляемого вопроса
     * @throws SQLException
     */
    public void deleteQuestion(int id) throws SQLException {
        Collection<Answer> answers = daoQuestion.queryForId(id).getAnswers();
        for (Answer answer : answers) {
            daoAnswer.delete(answer);
        }
        daoQuestion.deleteById(id);
    }

    /**
     * Запрос пустой коллекции ответов для дальнейшего добавления туда новых ответов
     *
     * @return пустая коллекция ответов
     * @throws SQLException
     */
    public ForeignCollection<Answer> getEmptyAnswerCollection() throws SQLException {
        return daoQuestion.getEmptyForeignCollection("answers");
    }

    /**
     * Добавление ответа в БД
     *
     * @param answer ответ, добавляемый в БД
     * @throws SQLException
     */
    public void addAnswer(Answer answer) throws SQLException {
        TransactionManager.callInTransaction(source,
                () -> {
                    int rowsChanged = daoAnswer.create(answer);
                    if (rowsChanged != 1) {
                        throw new SQLException("adding answer " + answer + " is not succesful!");
                    }
                    return null;
                });
    }

    /**
     * Обновление варианта ответа в БД
     *
     * @param answer обновляемый вариант ответа (новые данные). Вариант ответа, который нужно обновить, определяется
     *               по идентификатору
     * @throws SQLException
     */
    public void updateAnswer(Answer answer) throws SQLException {
        daoAnswer.update(answer);
    }

    /**
     * Удаление варианта ответа из БД
     *
     * @param id идентификатор удаляемого варианта ответа
     * @throws SQLException
     */
    public void deleteAnswer(int id) throws SQLException {
        daoAnswer.deleteById(id);
    }
}
