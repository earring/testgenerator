package ru.earring.testgenerator.test;

import com.j256.ormlite.dao.EagerForeignCollection;
import com.j256.ormlite.dao.ForeignCollection;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.earrring.testgenerator.db.Answer;
import ru.earrring.testgenerator.db.DBFacade;
import ru.earrring.testgenerator.db.Question;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class DBFacadeTest {
    private static DBFacade dbFacade;

    @BeforeClass
    public static void start() throws SQLException {
        dbFacade = DBFacade.getInstance(true);

        Question question1 = new Question("биология|география|математика","В каком году знаменитый биолог, вычисляя квадратный корень, открыл Атлантиду?", dbFacade.getEmptyAnswerCollection());
        dbFacade.addQuestion(question1);
        dbFacade.addAnswer(new Answer("1899", true, question1));
        dbFacade.addAnswer(new Answer("2001", false, question1));
        dbFacade.addAnswer(new Answer("1897", false, question1));

        Question question2 = new Question("математика|география","В какой стране будет подсчитана площадь поверхности упоротого лиса?", dbFacade.getEmptyAnswerCollection());
        dbFacade.addQuestion(question2);
        dbFacade.addAnswer(new Answer("1899", false, question2));
        dbFacade.addAnswer(new Answer("2001", false, question2));
        dbFacade.addAnswer(new Answer("1897", true, question2));
        dbFacade.addAnswer(new Answer("1005", false, question2));
    }

    @Test
    public void testAddingQuestions() throws Exception {

        // запрос всех вопросов и проверка их числа
        List<Question> questionsFromDB = dbFacade.getAllQuestions();
        Assert.assertTrue(questionsFromDB.size() == 2);

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

}
