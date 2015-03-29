package ru.earring.testgenerator.test;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import ru.earrring.testgenerator.db.Answer;
import ru.earrring.testgenerator.db.DBFacade;
import ru.earrring.testgenerator.db.Question;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class DBFacadeTest {
    private static DBFacade dbFacade;

    @BeforeClass
    public static void start() throws SQLException {
        dbFacade = DBFacade.getInstance(true);

        addQuestion1();

        addQuestion2();
    }

    @Test
    public void testAddedQuestions() throws Exception {
        List<Question> questionsFromDB = checkNumberOfQuestions(2);

        // запрос первого вопроса и проверка его текста и категорий
        Question question1FromDB = questionsFromDB.get(0);
        Assert.assertTrue(question1FromDB.getDescription().equals("В каком году знаменитый биолог, вычисляя квадратный корень, открыл Атлантиду?"));
        Assert.assertTrue(question1FromDB.getCategory().equals("биология|география|математика"));

        // запрос списка вариантов и проверка их числа
        Collection<Answer> answers1FromDB = question1FromDB.getAnswers();
        Assert.assertTrue(answers1FromDB.size() == 3);

        Iterator<Answer> answerIterator = answers1FromDB.iterator();

        // проверка первого варианта
        Answer answer1FromQuestion1 = answerIterator.next();
        Assert.assertTrue(answer1FromQuestion1.getValue().equals("1899"));
        Assert.assertTrue(answer1FromQuestion1.isCorrect() == true);
        Assert.assertTrue(answer1FromQuestion1.getQuestion() == question1FromDB);

        // проверка второго варианта
        Answer answer2FromQuestion1 = answerIterator.next();
        Assert.assertTrue(answer2FromQuestion1.getValue().equals("2001"));
        Assert.assertTrue(answer2FromQuestion1.isCorrect() == false);
        Assert.assertTrue(answer2FromQuestion1.getQuestion() == question1FromDB);

        // проверка третьего варианта
        Answer answer3FromQuestion1 = answerIterator.next();
        Assert.assertTrue(answer3FromQuestion1.getValue().equals("1897"));
        Assert.assertTrue(answer3FromQuestion1.isCorrect() == false);
        Assert.assertTrue(answer3FromQuestion1.getQuestion() == question1FromDB);
    }

    @Test
    public void testAddingDeletingQuestion() throws SQLException {
        // добавление еще одного (третьего) вопроса в базу
        Question question = new Question("программирование|история","В каком году был изобретен абак?", dbFacade.getEmptyAnswerCollection());
        dbFacade.addQuestion(question);
        dbFacade.addAnswer(new Answer("23", true, question));
        dbFacade.addAnswer(new Answer("1005", false, question));
        dbFacade.addAnswer(new Answer("975", false, question));

        // запрос всех вопросов и проверка их числа
        List<Question> questionsFromDB = checkNumberOfQuestions(3);

        // запрос третьего вопроса и проверка его текста и категорий
        Question questionFromDB = questionsFromDB.get(2);
        Assert.assertTrue(questionFromDB.getDescription().equals("В каком году был изобретен абак?"));
        Assert.assertTrue(questionFromDB.getCategory().equals("программирование|история"));

        // запрос списка вариантов и проверка их числа
        Collection<Answer> answersFromDB = questionFromDB.getAnswers();
        Assert.assertTrue(answersFromDB.size() == 3);

        Iterator<Answer> answerIterator = answersFromDB.iterator();

        // проверка первого варианта
        Answer answer1FromQuestion1 = answerIterator.next();
        Assert.assertTrue(answer1FromQuestion1.getValue().equals("23"));
        Assert.assertTrue(answer1FromQuestion1.isCorrect() == true);
        Assert.assertTrue(answer1FromQuestion1.getQuestion() == questionFromDB);

        // проверка второго варианта
        Answer answer2FromQuestion1 = answerIterator.next();
        Assert.assertTrue(answer2FromQuestion1.getValue().equals("1005"));
        Assert.assertTrue(answer2FromQuestion1.isCorrect() == false);
        Assert.assertTrue(answer2FromQuestion1.getQuestion() == questionFromDB);

        // проверка третьего варианта
        Answer answer3FromQuestion1 = answerIterator.next();
        Assert.assertTrue(answer3FromQuestion1.getValue().equals("975"));
        Assert.assertTrue(answer3FromQuestion1.isCorrect() == false);
        Assert.assertTrue(answer3FromQuestion1.getQuestion() == questionFromDB);

        // удаление третьего вопроса
        dbFacade.deleteQuestion(questionFromDB.getId());
        checkNumberOfQuestions(2);
    }

    @Test
    public void testGettingQuestionById() throws SQLException {
        checkNumberOfQuestions(2);

        // получение вопроса с идентификатором "1"
        Question question1 = dbFacade.getQuestionById(1);

        // проверка некоторых данных вопроса
        Assert.assertTrue(question1.getDescription().equals("В каком году знаменитый биолог, вычисляя квадратный корень, открыл Атлантиду?"));
        Assert.assertTrue(question1.getCategory().equals("биология|география|математика"));
    }

    @Test
    public void testFindingQuestionByDescription() throws SQLException {
        // добавление еще одного (третьего) вопроса в базу
        Question question = new Question("программирование|история","В каком году был изобретен абак?", dbFacade.getEmptyAnswerCollection());
        dbFacade.addQuestion(question);
        dbFacade.addAnswer(new Answer("23", true, question));
        dbFacade.addAnswer(new Answer("1005", false, question));
        dbFacade.addAnswer(new Answer("975", false, question));

        // запрос всех вопросов и проверка их числа
        List<Question> questionsFromDB = checkNumberOfQuestions(3);

        // запрос поиска и проверка числа результатов
        List<Question> questionFinds = dbFacade.findQuestionsByDescription("году");
        Assert.assertTrue(questionFinds.size() == 2);

        // проверка текстов полученных вопросов
        Assert.assertTrue(questionFinds.get(0).getDescription().equals("В каком году знаменитый биолог, вычисляя квадратный корень, открыл Атлантиду?"));
        Assert.assertTrue(questionFinds.get(1).getDescription().equals("В каком году был изобретен абак?"));

        // запрос поиска и проверка числа результатов
        questionFinds = dbFacade.findQuestionsByDescription("упорот");
        Assert.assertTrue(questionFinds.size() == 1);

        // проверка текстов полученных вопросов
        Assert.assertTrue(questionFinds.get(0).getDescription().equals("В какой стране будет подсчитана площадь поверхности упоротого лиса?"));

        // удаление третьего вопроса
        dbFacade.deleteQuestion(question.getId());
        checkNumberOfQuestions(2);
    }

    @Test
    public void testUpdatingQuestion() throws SQLException {
        List<Question> questionsFromDB = checkNumberOfQuestions(2);

        // запрос первого вопроса и его изменение
        Question questionFromDB = questionsFromDB.get(0);
        questionFromDB.setDescription("Сколько длилась война между кошками и собаками?");
        questionFromDB.getAnswers().add(new Answer("27", false, questionFromDB));
        dbFacade.updateQuestion(questionFromDB);

        // проверка
        questionsFromDB = checkNumberOfQuestions(2);

        // запрос первого вопроса и проверка его текста и категорий
        Question question1FromDB = questionsFromDB.get(0);
        Assert.assertTrue(question1FromDB.getDescription().equals("Сколько длилась война между кошками и собаками?"));
        Assert.assertTrue(question1FromDB.getCategory().equals("биология|география|математика"));

        // запрос списка вариантов и проверка их числа
        Collection<Answer> answers1FromDB = question1FromDB.getAnswers();
        Assert.assertTrue(answers1FromDB.size() == 4);

        Iterator<Answer> answerIterator = answers1FromDB.iterator();

        // проверка первого варианта
        Answer answer1FromQuestion1 = answerIterator.next();
        Assert.assertTrue(answer1FromQuestion1.getValue().equals("1899"));
        Assert.assertTrue(answer1FromQuestion1.isCorrect() == true);
        Assert.assertTrue(answer1FromQuestion1.getQuestion() == question1FromDB);

        // проверка второго варианта
        Answer answer2FromQuestion1 = answerIterator.next();
        Assert.assertTrue(answer2FromQuestion1.getValue().equals("2001"));
        Assert.assertTrue(answer2FromQuestion1.isCorrect() == false);
        Assert.assertTrue(answer2FromQuestion1.getQuestion() == question1FromDB);

        // проверка третьего варианта
        Answer answer3FromQuestion1 = answerIterator.next();
        Assert.assertTrue(answer3FromQuestion1.getValue().equals("1897"));
        Assert.assertTrue(answer3FromQuestion1.isCorrect() == false);
        Assert.assertTrue(answer3FromQuestion1.getQuestion() == question1FromDB);

        // проверка четвертого варианта
        Answer answer4FromQuestion1 = answerIterator.next();
        Assert.assertTrue(answer4FromQuestion1.getValue().equals("27"));
        Assert.assertTrue(answer4FromQuestion1.isCorrect() == false);
        Assert.assertTrue(answer4FromQuestion1.getQuestion() == question1FromDB);

        // возвращение БД в исходное состояние
        questionFromDB = questionsFromDB.get(0);
        questionFromDB.setDescription("В каком году знаменитый биолог, вычисляя квадратный корень, открыл Атлантиду?");
        dbFacade.deleteAnswer(answer4FromQuestion1.getId());
        dbFacade.updateQuestion(questionFromDB);

        // запрос первого вопроса и проверка его текста и категорий
        question1FromDB = questionsFromDB.get(0);
        Assert.assertTrue(question1FromDB.getDescription().equals("В каком году знаменитый биолог, вычисляя квадратный корень, открыл Атлантиду?"));
        Assert.assertTrue(question1FromDB.getCategory().equals("биология|география|математика"));

        // запрос списка вариантов и проверка их числа
        answers1FromDB = question1FromDB.getAnswers();
        Assert.assertTrue(answers1FromDB.size() == 3);

        answerIterator = answers1FromDB.iterator();

        // проверка первого варианта
        answer1FromQuestion1 = answerIterator.next();
        Assert.assertTrue(answer1FromQuestion1.getValue().equals("1899"));
        Assert.assertTrue(answer1FromQuestion1.isCorrect() == true);
        Assert.assertTrue(answer1FromQuestion1.getQuestion() == question1FromDB);

        // проверка второго варианта
        answer2FromQuestion1 = answerIterator.next();
        Assert.assertTrue(answer2FromQuestion1.getValue().equals("2001"));
        Assert.assertTrue(answer2FromQuestion1.isCorrect() == false);
        Assert.assertTrue(answer2FromQuestion1.getQuestion() == question1FromDB);

        // проверка третьего варианта
        answer3FromQuestion1 = answerIterator.next();
        Assert.assertTrue(answer3FromQuestion1.getValue().equals("1897"));
        Assert.assertTrue(answer3FromQuestion1.isCorrect() == false);
        Assert.assertTrue(answer3FromQuestion1.getQuestion() == question1FromDB);
    }

    @Test
    public void checkDeletingQuestions() throws SQLException {
        // проверка количества вопросов
        List<Question> questionsFromDB = checkNumberOfQuestions(2);

        // удаляем второй вопрос
        dbFacade.deleteQuestion(questionsFromDB.get(1).getId());

        // проверка оставшихся вопросов
        questionsFromDB = checkNumberOfQuestions(1);
        Assert.assertTrue(questionsFromDB.get(0).getDescription().equals("В каком году знаменитый биолог, вычисляя квадратный корень, открыл Атлантиду?"));

        // восстановление БД
        addQuestion2();
        questionsFromDB = checkNumberOfQuestions(2);
    }

    /**
     * запрос всех вопросов и проверка их числа
     * @param number число вопросов, которое должно быть в базе
     * @return список вопросов из базы
     */
    private List<Question> checkNumberOfQuestions(int number) throws SQLException {
        List<Question> questionsFromDB = dbFacade.getAllQuestions();
        Assert.assertTrue(questionsFromDB.size() == number);
        return questionsFromDB;
    }

    /**
     * добавление первого вопроса в БД
     * @throws SQLException
     */
    private static void addQuestion1() throws SQLException {
        Question question1 = new Question("биология|география|математика","В каком году знаменитый биолог, вычисляя квадратный корень, открыл Атлантиду?", dbFacade.getEmptyAnswerCollection());
        dbFacade.addQuestion(question1);
        dbFacade.addAnswer(new Answer("1899", true, question1));
        dbFacade.addAnswer(new Answer("2001", false, question1));
        dbFacade.addAnswer(new Answer("1897", false, question1));
    }

    /**
     * добавление первого вопроса в БД
     * @throws SQLException
     */
    private static void addQuestion2() throws SQLException {
        Question question2 = new Question("математика|география","В какой стране будет подсчитана площадь поверхности упоротого лиса?", dbFacade.getEmptyAnswerCollection());
        dbFacade.addQuestion(question2);
        dbFacade.addAnswer(new Answer("1899", false, question2));
        dbFacade.addAnswer(new Answer("2001", false, question2));
        dbFacade.addAnswer(new Answer("1897", true, question2));
        dbFacade.addAnswer(new Answer("1005", false, question2));
    }
}
