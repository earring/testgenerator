package ru.earrring.testgenerator.dbWork;

import ru.earrring.testgenerator.frames.AFrame;

/**
 * Created by nenagleyko on 30.03.2015.
 */
public interface IPresenter {
    abstract void setView(AFrame view);
    abstract void onQuestionAdded();
    abstract void onQuestionRemoved();
}
