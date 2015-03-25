package ru.earrring.testgenerator;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

/**
 * Класс с полезными статическими функциями, полезными по всей программе
 */
public class Utils {
    /**
     * Версия программы
     */
    public static String VERSION = "0.1 alpha";

    /**
     * Получение изображения из встроенных ресурсов приложения. Ресурсы лежат в директории, заданной в Gradle.
     * @param name полное имя изображения, например "image.png"
     * @return класс изображения
     */
    public static Image getImageFromResources(String name) {
        Image image = null;
        try {
            URL url = ClassLoader.getSystemResource(name);
            image = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * Центрирование формы
     * @param frame форма, для которой нужно выполнить центрирование на экране
     */
    public static void centerFrame(JFrame frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dimension.width / 2 - frame.getWidth() / 2, dimension.height / 2 - frame.getHeight() / 2);
    }
}
