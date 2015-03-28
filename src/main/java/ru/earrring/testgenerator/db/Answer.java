package ru.earrring.testgenerator.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Класс, обозначающий один из ответов в вопросе, может быть правильным и неправильным
 */
@DatabaseTable(tableName = "answer")
public class Answer {

    /**
     * Уникальный идентификатор ответа
     */
    @DatabaseField(unique = true, generatedId = true)
    private int id;

    /**
     * Текст ответа
     */
    @DatabaseField(canBeNull = false)
    private String value;

    /**
     * Корректность ответа
     */
    @DatabaseField(canBeNull = false)
    private boolean correct;

    /**
     * Вопрос, которому принадлежит ответ
     */
    @DatabaseField(foreign = true)
    private Question question;

    public Answer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", correct=" + correct +
                '}';
    }
}
