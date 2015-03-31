package ru.earrring.testgenerator.db;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Collection;

@DatabaseTable(tableName = "question")
public class Question {

    /**
     * Уникальный идентификатор вопроса
     */
    @DatabaseField(unique = true, id = true)
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
    private String description;

    /**
     * Список ответов на вопрос
     */
    @ForeignCollectionField
    private Collection<Answer> answers;

    /**
     * Конструктор по умолчанию, необходимый для ORM Lite. В коде им не пользоваться!!
     */
    public Question() {
    }

    /**
     * Конструктор для создания нового вопроса
     *
     * @param category    список категорий для вопроса - одна строка, разделенная знаками вертикальной черты |
     * @param description текст вопроса
     * @param answers     коллекция ответов на вопрос
     */
    public Question(String category, String description, Collection<Answer> answers) {
        this.category = category;
        this.description = description;
        this.answers = answers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Collection<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Collection<Answer> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", category=" + category +
                ", description='" + description + '\'' +
                ", answers=" + answers +
                '}';
    }
}
