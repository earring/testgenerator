package ru.earrring.testgenerator.frames;

import ru.earrring.testgenerator.Utils;

import javax.swing.*;

public abstract class AFrame extends JFrame implements IStartableFrame {
    public AFrame() {
        super();
        start();
    }
    /**
     * Метод, запускающий форму, и делающий её видимой
     */
    @Override
    public void start(){
        adjustFrameSettings();
        adjustGeneralSettings();
        adjustLayout();
        setListeners();
    }

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
}
