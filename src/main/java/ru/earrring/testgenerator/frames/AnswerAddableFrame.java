package ru.earrring.testgenerator.frames;

public interface AnswerAddableFrame {

    void addAnswerVariantComponent(String text, boolean isCorrect);

    void deleteAnswerVariantComponent(int position);
}
