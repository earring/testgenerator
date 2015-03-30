package ru.earrring.testgenerator;

/**
 * Интерфейс, который должны реализовать все презентеры, управлеяющие формами
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
