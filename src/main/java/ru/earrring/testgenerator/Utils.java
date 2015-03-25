package ru.earrring.testgenerator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class Utils {
    public static String VERSION = "0.1 alpha";

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
}
