package ru.earrring.testgenerator.db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

public class DBConnector {
    private final String url = "jdbc:sqlite:main.sqlite";
    private ConnectionSource source;
    private Dao<Question, String> daoQuestion;

    public DBConnector() throws SQLException {
        source = new JdbcConnectionSource(url);
        daoQuestion = DaoManager.createDao(source, Question.class);

        TableUtils.createTableIfNotExists(source, Question.class);
        TableUtils.createTableIfNotExists(source, Answer.class);
    }

    public List<Question> getAll() throws SQLException {
        return daoQuestion.queryForAll();
    }
}
