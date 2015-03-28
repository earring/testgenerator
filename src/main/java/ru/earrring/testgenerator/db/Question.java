package ru.earrring.testgenerator.db;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable (tableName = "question")
public class Question {

    /**
     * Уникальный идентификатор вопроса
     */
    @DatabaseField(unique = true, generatedId = true)
    private int id;

    /**
     * Список категорий, к которому относится вопрос. Список разделен вертикальными чертами |
     */
    @DatabaseField(canBeNull = false)
    private String category;

    /**
     * Текст вопроса
     */
    @DatabaseField(canBeNull = false)
    private String question;

    /**
     * Список ответов на вопрос
     */
    @ForeignCollectionField
    private ForeignCollection<Answer> answers;

    public Question() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ForeignCollection<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ForeignCollection<Answer> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", category=" + category +
                ", question='" + question + '\'' +
                ", answers=" + answers +
                '}';
    }
}
