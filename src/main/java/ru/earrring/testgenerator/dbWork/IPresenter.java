package ru.earrring.testgenerator.dbWork;

import ru.earrring.testgenerator.frames.AFrame;

/**
 * Created by nenagleyko on 30.03.2015.
 */
public interface IPresenter {

    void setUp(AFrame view);

    /**
     * Настройка слушателей (кнопок и других компонентов)
     */
    void setListeners();

    /**
     * Настройка изначальных данных на форме
     */
    void setInitialData();

    void onQuestionAdded();

    void onQuestionRemoved();
}
