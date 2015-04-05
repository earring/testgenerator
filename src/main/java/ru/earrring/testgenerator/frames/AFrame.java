package ru.earrring.testgenerator.frames;

import ru.earrring.testgenerator.Utils;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public abstract class AFrame extends JFrame implements IStartableCloseableFrame {

    /**
     * Родительский фрейм. Должен показываться при закрытии дочернего
     */
    private AFrame parentFrame;

    /**
     * Метод, запускающий форму, но не делающий её видимой, видимость задается тогда, когда форма необходима для показа
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
     * Начальная настройка формы, здесь располагаются настройки, специфичные именно для этой формы
     */
    abstract protected void adjustFrameSettings();

    /**
     * Метод, делающий общие для всех форм настройки
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
     * "Финализировние" формы и её завершение
     */
    abstract public void close();

    /**
     * Возврат фрейма, являющегося родительским к вызываемому фрейму
     * @return ссылка на родительский фрейм
     */
    protected AFrame getParentFrame() {
        return parentFrame;
    }

    /**
     * Задание родительского фрейма
     * @param parentFrame ссылка на родительский фрейм
     */
    protected void setParentFrame(AFrame parentFrame) {
        this.parentFrame = parentFrame;
    }

    /**
     * Задание поведения при нажатии кнопки "закрыть" у окна. Если у фрейма нет родительского фрейма, то этот фрейм
     * просто закрывается (вызовом специального метода close()) (с запросом в виде диалогового окна", если есть
     * родительский, родительский делается видимым, а этот закрывается.
     */
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
