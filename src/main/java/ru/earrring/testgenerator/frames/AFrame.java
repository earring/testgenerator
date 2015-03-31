package ru.earrring.testgenerator.frames;

import ru.earrring.testgenerator.Utils;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public abstract class AFrame extends JFrame implements IStartableCloseableFrame {

    private AFrame parentFrame;

    /**
     * Метод, запускающий форму, и делающий её видимой
     */
    @Override
    public void start() {
        adjustFrameSettings();
        adjustGeneralSettings();
        adjustLayout();
        setListeners();
        setClosingBehaviour();
    }

    /**
     * Начальная настройка формы
     */
    abstract protected void adjustFrameSettings();

    /**
     * Метод, делающий общий для всех форм настройки
     */
    private void adjustGeneralSettings() {
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        Utils.centerFrame(this);
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
     * Финализировние формы и её завершение
     */
    abstract public void close();

    protected AFrame getParentFrame() {
        return parentFrame;
    }

    protected void setParentFrame(AFrame parentFrame) {
        this.parentFrame = parentFrame;
    }

    private void setClosingBehaviour() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                if (parentFrame == null) {
                    if (JOptionPane.showConfirmDialog(AFrame.this, "Вы действительно хотите выйти?", "Выход",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                        AFrame.this.close();
                    }
                } else {
                    AFrame.this.close();
                    parentFrame.setVisible(true);
                }
            }
        });
    }
}
