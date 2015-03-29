package ru.earrring.testgenerator.db;

import ru.earrring.testgenerator.AFrame;

/**
 * Created by nenagleyko on 30.03.2015.
 */
public interface IPresenter {
    abstract void setView(AFrame view);
    abstract void onQuestionAdded();
    abstract void onQuestionRemoved();
}
