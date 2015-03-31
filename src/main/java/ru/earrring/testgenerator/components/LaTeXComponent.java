package ru.earrring.testgenerator.components;

import com.sun.java.swing.plaf.windows.WindowsBorders;
import org.scilab.forge.jlatexmath.ParseException;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class LaTeXComponent extends JPanel {
    private BufferedImage latexImage;
    private String latexString;
    private JLabel imageLabel;

    public LaTeXComponent(String latexString) throws ParseException {
        super();
        createImage(latexString);
        imageLabel = new JLabel(new ImageIcon(latexImage));
        imageLabel.setBorder(new WindowsBorders.DashedBorder(Color.BLACK));
        add(imageLabel);
    }

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
