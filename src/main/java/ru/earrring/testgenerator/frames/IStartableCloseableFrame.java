package ru.earrring.testgenerator.frames;

/**
 * Интерфейс, отвечающий за запускаемую форму
 */
public interface IStartableCloseableFrame {

    /**
     * Метод, запускающий форму, но не делающий её видимой, видимость задается тогда, когда форма необходима для показа
     */
    void start();

    /**
     * "Финализировние" формы и её завершение
     */
    void close();
}
