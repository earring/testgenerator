package ru.earrring.testgenerator;

import javax.swing.*;
import java.sql.SQLException;

public abstract class AFrame extends JFrame implements IStartableFrame {
    /**
     * Метод, запускающий форму, и делающий её видимой
     */
    @Override
    public void start() {
        setUpPresenter();
        adjustFrameSettings();
        adjustGeneralSettings();
        adjustLayout();
        setListeners();
        setInitialData();
        setVisible(true);
    }

    /**
     * Начальная настройка формы
     */
    abstract protected void setUpPresenter();

    /**
     * Начальная настройка формы
     */
    abstract protected void adjustFrameSettings();

    /**
     * Метод, делающий общий для всех форм настройки
     */
    private void adjustGeneralSettings() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Utils.centerFrame(this);
        setResizable(false);
        setIconImage(Utils.getImageFromResources("icon_image.png"));
    }

    /**
     * Настройка менеджера компоновки и добавление туда необходимых компоненктов
     */
    abstract protected void adjustLayout();

    /**
     * Настройка слушателей (кнопок и других компонентов)
     */
    abstract protected void setListeners();

    /**
     * Настройка изначальных данных на форме
     */
    abstract protected void setInitialData();
}
