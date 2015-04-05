package ru.earrring.testgenerator.dbWork;

import ru.earrring.testgenerator.frames.AFrame;

public interface IPresenter {

    /**
     * Настройка презентера, вызов всех необходимых функций
     *
     * @param view абстрактная форма, которая привязана к презентеру
     */
    void setUp(AFrame view);

    /**
     * Настройка слушателей (кнопок и других компонентов)
     */
    void setListeners();

    /**
     * Настройка изначальных данных на форме
     */
    void setInitialData();

    /**
     * Коллбек, вызывающийся при добавлении вопроса
     */
    void onQuestionAdded();

    /**
     * Коллбек, вызывающийся при удалении вопроса
     */
    void onQuestionRemoved();
}
