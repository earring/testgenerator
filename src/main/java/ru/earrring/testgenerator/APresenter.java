package ru.earrring.testgenerator;

import ru.earrring.testgenerator.AFrame;
import ru.earrring.testgenerator.IPresenter;

public abstract class APresenter implements IPresenter {

    public void setUp(AFrame view) {
        setView(view);
        setListeners();
        setInitialData();
    }

    public abstract void setView(AFrame view);

    /**
     * Настройка слушателей (кнопок и других компонентов)
     */
    public abstract void setListeners();

    /**
     * Настройка изначальных данных на форме
     */
    public abstract void setInitialData();

    public abstract void onQuestionAdded();

    public abstract void onQuestionRemoved();
}
