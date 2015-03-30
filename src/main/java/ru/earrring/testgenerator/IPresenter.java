package ru.earrring.testgenerator;

/**
 * Интерфейс, который должны реализовать все презентеры, управлеяющие формами
 */
public interface IPresenter {
    void setView(AFrame view);
    void onQuestionAdded();
    void onQuestionRemoved();
}
