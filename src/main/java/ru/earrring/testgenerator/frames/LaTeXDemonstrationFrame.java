package ru.earrring.testgenerator.frames;

import ru.earrring.testgenerator.components.LaTeXComponent;

import javax.swing.*;
import java.awt.*;

/**
 * Форма, показывающая возможности рендеринга формул LaTeX с помощью библиотеки JLaTeXMath
 */
public class LaTeXDemonstrationFrame extends AFrame {

    /**
     * Переменная для хранения номера строки (нужно для менеджера компоновки)
     */
    private int gridy = 0;

    @Override
    protected void adjustFrameSettings() {
        setResizable(false);
        setSize(600, 750);
        setTitle("Демонстрация LaTeX");
    }

    @Override
    protected void adjustLayout() {
        setLayout(new GridBagLayout());

        // добавление виджетов с текстом LaTeX и картинкой LaTeX
        addWidget("\\frac{(a+b )^2}{4} - \\frac{(a-b )^2}{4} = ab");
        addWidget("\\iiint_{x^2 + y^2 + z^2 = 1} f(x, y, z) dx dy dz");
        addWidget("\\frac{(a+b )^2}{4} - \\frac{(a-b )^2}{4} = ab");
        addWidget("f(x,y,\\alpha, \\beta) = \\frac{\\sum \\limits_{n=1}^{\\infty} A_n \\cos \\left( \\frac{2 n \\pi x}{\\nu} \\right)} {\\prod \\mathcal{F} {g(x,y)} }");
        addWidget("\\begin{eqnarray} S_{\\text{вых}}(x_2, y_2) = \\iint dx_0 dy_0 A_0 g(x_0, y_0) \\cdot h(x_2-x_0, y_2 -y_0) = \\\\ = A_0 \\underbrace{\\iint dx_0 dy_0 \\; g(x_0, y_0)  \\cdot h(x_2-x_0, y_2 -y_0)}_{\\text{по определению это есть свёртка }} = A_0 g \\otimes h  \\end{eqnarray}");
    }

    /**
     * Добавление строки в форму
     *
     * @param latexString   строка LaTeX для рендеринга
     */
    private void addWidget(String latexString) {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 0, 5);

        // добавление TexArea c кодом
        c.gridx = 0;
        c.gridy = gridy;
        JTextArea latexStringTextArea = new JTextArea(latexString);
        latexStringTextArea.setLineWrap(true);
        latexStringTextArea.setColumns(80);
        latexStringTextArea.setEnabled(false);
        latexStringTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(latexStringTextArea, c);
        gridy++;

        // добавление компонента с картинкой формулы
        LaTeXComponent laTeXComponent = new LaTeXComponent(latexString);
        c.insets = new Insets(0, 5, 5, 5);
        c.gridx = 0;
        c.gridy = gridy;
        add(laTeXComponent, c);
        gridy++;
    }

    @Override
    protected void setListeners() {

    }

    @Override
    public void close() {
        setVisible(false);
    }
}
