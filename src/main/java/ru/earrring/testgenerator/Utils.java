package ru.earrring.testgenerator;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Класс с полезными статическими функциями, полезными по всей программе
 */
public class Utils {
    /**
     * Версия программы
     */
    public static String VERSION = "1.0 beta";

    /**
     * Получение изображения из встроенных ресурсов приложения. Ресурсы лежат в директории, заданной в Gradle
     *
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
     *
     * @param frame форма, для которой нужно выполнить центрирование на экране
     */
    public static void centerFrame(JFrame frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dimension.width / 2 - frame.getWidth() / 2, dimension.height / 2 - frame.getHeight() / 2);
    }

    public static Path getPathToMyDocuments() throws IOException {
        Path path = Paths.get(new JFileChooser().getFileSystemView().getDefaultDirectory().toString() + "\\Генератор тестов");
        if (!Files.exists(path)) {
            Files.createDirectory(path);
        }
        return path;
    }

    public static Path getPathToMyDB() throws IOException {
        Path path = Paths.get(getPathToMyDocuments() + "\\БД");
        if (!Files.exists(path)) {
            Files.createDirectory(path);
        }
        return path;
    }

    public static Path getPathToMyTests() throws IOException {
        Path path = Paths.get(getPathToMyDocuments() + "\\Тесты");
        if (!Files.exists(path)) {
            Files.createDirectory(path);
        }
        return path;
    }
}
