package ru.earrring.testgenerator.generators;

import org.scilab.forge.jlatexmath.ParseException;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class LaTeXGenerator {
    /**
     * Генерация изображения из формулы LaTeX
     * @param latexString строка, из которой генерируется формула (без знаков доллара)
     * @param size 70 - для PDF (для того, чтобы формула была четко видна при увеличении),
     *             20 - для отображения в самой программе. Эти числа - экспериментальная магия.
     * @return
     * @throws ParseException
     */
    public static BufferedImage generateBufferedImageFromFormula(String latexString, float size) throws ParseException {
        // создание формулы
        TeXFormula formula = new TeXFormula(latexString);

        // рендеринг формулы в изображение такого же размера, как и формула
        TeXIcon icon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, size);

        // отступы вокруг формулы
        icon.setInsets(new Insets(0, 0, 0, 0));

        // создание изображения
        BufferedImage latexImage = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = latexImage.createGraphics();
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, icon.getIconWidth(), icon.getIconHeight());
        JLabel tempLabel = new JLabel();
        tempLabel.setForeground(Color.BLACK);
        icon.paintIcon(tempLabel, g2, 0, 0);
        // к этому времени изображение сохранено в latexImage, можно его использовать с помощью ImageIO

        return latexImage;
    }
}
