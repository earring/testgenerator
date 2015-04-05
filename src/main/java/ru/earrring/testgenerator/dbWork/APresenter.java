package ru.earrring.testgenerator.dbWork;

import ru.earrring.testgenerator.frames.AFrame;

/**
 * Абстрактный презентер
 */
public abstract class APresenter implements IPresenter {

    /**
     * Настройка презентера, вызов всех необходимых функций
     *
     * @param view абстрактная форма, которая привязана к презентеру
     */
    public void setUp(AFrame view) {
        setView(view);
        setListeners();
        setInitialData();
    }

    /**
     * Привязка вида, который соответствует презентеру
     *
     * @param view абстрактная форма, которая привязана к презентеру
     */
    public abstract void setView(AFrame view);

    /**
     * Настройка слушателей (кнопок и других компонентов)
     */
    public abstract void setListeners();

    /**
     * Настройка изначальных данных на форме
     */
    public abstract void setInitialData();

    /**
     * Коллбек, вызывающийся при добавлении вопроса
     */
    public abstract void onQuestionAdded();

    /**
     * Коллбек, вызывающийся при удалении вопроса
     */
    public abstract void onQuestionRemoved();
}
