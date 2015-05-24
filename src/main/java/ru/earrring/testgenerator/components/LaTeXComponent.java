package ru.earrring.testgenerator.components;

import com.sun.java.swing.plaf.windows.WindowsBorders;
import org.scilab.forge.jlatexmath.ParseException;
import ru.earrring.testgenerator.generators.LaTeXGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Компонент, отрисовывающий формулу LaTeX. Позволяет более-менее корректно нарисовать формулу, которая в LaTeX коде
 * находится в знаках доллара $$...$$. В сам компонент передается та строка, которая находится ВНУТРИ этих
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
            this.latexString = latexString;
            latexImage = LaTeXGenerator.generateBufferedImageFromFormula(latexString, 20);
            imageLabel = new JLabel(new ImageIcon(latexImage));
            imageLabel.setBorder(new WindowsBorders.DashedBorder(Color.BLACK));
            add(imageLabel);
        } catch (ParseException e) {
            imageLabel = new JLabel("<html><center>Ошибка! Проверьте корректность строки: <br>" + e.getMessage() + "</center></html>");
            imageLabel.setForeground(Color.RED);
            add(imageLabel);
        }
    }
}
