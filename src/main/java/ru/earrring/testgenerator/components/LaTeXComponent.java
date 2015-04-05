package ru.earrring.testgenerator.components;

import com.sun.java.swing.plaf.windows.WindowsBorders;
import org.scilab.forge.jlatexmath.ParseException;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Компонент, отрисовывающий формулу LaTeX. Позволяет более-менее корректно нарисовать формулу, которая в LaTeX коде
 * находится в знаках доллара $...$ или $$...$$. В сам компонент передается та строка, которая находится ВНУТРИ этих
 * знаков доллара.
 */
public class LaTeXComponent extends JPanel {

    /**
     * Изображение, которое сгенерировано библиотекой JLaTexMath, и которое выведется в компоненте
     */
    private BufferedImage latexImage;

    /**
     * Строка, передающаяся в библиотеку JLaTeXMath, которая затем будет преобразована в изображение формулы
     */
    private String latexString;

    /**
     * Лейбл, куда будет помещаться изображение формулы
     */
    private JLabel imageLabel;

    /**
     * Конструктор, создающий компонент с уже нарисованной формулой
     * @param latexString строка LaTeX-формулы (текст, находящийся внутри знаков доллара)
     */
    public LaTeXComponent(String latexString) {
        super();
        try {
            createImage(latexString);
            imageLabel = new JLabel(new ImageIcon(latexImage));
            imageLabel.setBorder(new WindowsBorders.DashedBorder(Color.BLACK));
            add(imageLabel);
        } catch (ParseException e) {
            imageLabel = new JLabel("<html><center>Ошибка! Проверьте корректность строки: <br>" + e.getMessage() + "</center></html>");
            imageLabel.setForeground(Color.RED);
            add(imageLabel);
        }
    }

    /**
     * Создание формулы и запись её в изображение формулы, хранящееся в этом классе
     * @param latexString строка, из которой формируется изображение формулы
     */
    private void createImage(String latexString) {
        // создание формулы
        TeXFormula formula = new TeXFormula(latexString);

        // рендеринг формулы в изображение такого же размера, как и формула
        TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, 20);

        // отступы вокруг формулы
        icon.setInsets(new Insets(5, 5, 5, 5));

        // создание изображения
        latexImage = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = latexImage.createGraphics();
        g2.setColor(new Color(0f, 0f, 0f, 0f));
        g2.fillRect(0, 0, icon.getIconWidth(), icon.getIconHeight());
        JLabel tempLabel = new JLabel();
        tempLabel.setForeground(new Color(0, 0, 0));
        icon.paintIcon(tempLabel, g2, 0, 0);
        // к этому времени изображение сохранено в latexImage, можно его использовать с помощью ImageIO
    }
}
