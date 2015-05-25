package ru.earrring.testgenerator.frames;

import com.itextpdf.text.DocumentException;
import ru.earrring.testgenerator.Utils;
import ru.earrring.testgenerator.db.Question;
import ru.earrring.testgenerator.dbWork.QuestionManager;
import ru.earrring.testgenerator.generators.PDFGenerator;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class GenerationFrame extends AFrame {

    private int availableQuestions = 0;
    private JPanel mainPanel;
    private SpinnerNumberModel variantsSpinnerModel;
    private JSpinner variantsSpinner;
    private SpinnerNumberModel questionSpinnerModel;
    private JSpinner questionSpinner;
    private JLabel questionsCountLabel;

    private JPanel categoriesPanel;
    private JButton generateButton;

    private List<String> checkedCategories;

    public GenerationFrame() {
        super();
        checkedCategories = new ArrayList<>();
    }

    private void setQuestionCount(int count) {
        availableQuestions = count;
        questionsCountLabel.setText(String.format("%d", availableQuestions));
        questionSpinnerModel.setMaximum(count);
        variantsSpinnerModel.setMaximum(count);
        Number questions = questionSpinnerModel.getNumber();
        Number variants = variantsSpinnerModel.getNumber();
        if (questions.intValue() > count) {
            questionSpinnerModel.setValue(count);
        }
        if (variants.intValue() > count) {
            variantsSpinnerModel.setValue(count);
        }
    }

    @Override
    protected void adjustFrameSettings() {
        setTitle("Генерация тестов");
        setSize(700, 500);
        setResizable(false);
    }

    @Override
    protected void adjustLayout() {
        // настройка mainPanel
        mainPanel = new JPanel();
        add(mainPanel);

        mainPanel.setLayout(new GridLayout(1, 2));

        JPanel leftPanel = new JPanel(new GridBagLayout());
        JPanel rightPanel = new JPanel(new GridBagLayout());
        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);

        // LEFT PANEL
        JLabel label = new JLabel("Категории:");
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 0;
        c.insets = new Insets(8, 8, 8, 8);
        leftPanel.add(label, c);

        categoriesPanel = new JPanel();
        categoriesPanel.setLayout(new BoxLayout(categoriesPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(categoriesPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTH;
        c.gridx = 0;
        c.gridy = 1;
        c.weighty = 1;
        c.weightx = 1;
        c.insets = new Insets(8, 8, 8, 8);
        leftPanel.add(scrollPane, c);

        // RIGHT PANEL
        JLabel labelCountText = new JLabel("Количество доступных вопросов:");
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 0;
        c.weightx = 1;
        c.insets = new Insets(8, 8, 8, 8);
        rightPanel.add(labelCountText, c);

        questionsCountLabel = new JLabel("0");
        questionsCountLabel.setFont(new Font("Courier", Font.BOLD, 20));
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 1;
        c.gridy = 0;
        c.weighty = 0;
        c.weightx = 1;
        c.insets = new Insets(8, 8, 8, 8);
        rightPanel.add(questionsCountLabel, c);

        JLabel label3 = new JLabel("Количество вариантов:");
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.weighty = 0;
        c.weightx = 1;
        c.insets = new Insets(8, 8, 8, 8);
        rightPanel.add(label3, c);

        variantsSpinnerModel = new SpinnerNumberModel(0, 0, 10, 1);
        variantsSpinner = new JSpinner(variantsSpinnerModel);
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 1;
        c.weighty = 0;
        c.weightx = 1;
        c.insets = new Insets(8, 8, 8, 8);
        rightPanel.add(variantsSpinner, c);

        JLabel label4 = new JLabel("Количество вопросов в каждом варианте:");
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        c.weighty = 0;
        c.weightx = 1;
        c.insets = new Insets(8, 8, 8, 8);
        rightPanel.add(label4, c);

        questionSpinnerModel = new SpinnerNumberModel(0, 0, 10, 1);
        questionSpinner = new JSpinner(questionSpinnerModel);
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 2;
        c.weighty = 0;
        c.weightx = 1;
        c.insets = new Insets(8, 8, 8, 8);
        rightPanel.add(questionSpinner, c);

        JPanel emptyPanel = new JPanel();
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.SOUTH;
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        c.weighty = 1;
        c.weightx = 1;
        rightPanel.add(emptyPanel, c);

        generateButton = new JButton("Сгенерировать PDF");
        generateButton.setFont(new Font("Arial", Font.PLAIN, 22));
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.SOUTH;
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        c.weighty = 0;
        c.weightx = 1;
        c.ipady = 20;
        c.insets = new Insets(8, 8, 8, 8);
        rightPanel.add(generateButton, c);

        setQuestionCount(0);
    }

    @Override
    protected void setListeners() {
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (checkData()) {
                        PDFGenerator.getInstance().createPDF(variantsSpinnerModel.getNumber().intValue(), questionSpinnerModel.getNumber().intValue(), checkedCategories);
                        JOptionPane.showMessageDialog(GenerationFrame.this, "PDF документы были успешно сгенерированы", "Генерация завершена", JOptionPane.INFORMATION_MESSAGE);
                        // открываем папку со сгенерированными PDF-ками
                        Desktop.getDesktop().open(new File(Utils.getPathToMyTests().toString()));
                        GenerationFrame.this.close();
                    }
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(GenerationFrame.this, e1.getMessage(), "Ошибка ввода-вывода", JOptionPane.ERROR_MESSAGE);
                } catch (DocumentException e1) {
                    JOptionPane.showMessageDialog(GenerationFrame.this, e1.getMessage(), "Ошибка взаимодействия с PDF", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    @Override
    public void close() {
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    public void setCategories(List<String> categories) {
        for (int i = 0; i < categories.size(); ++i) {
            String category = categories.get(i);
            JCheckBox checkBox = new JCheckBox(category);
            checkBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (checkBox.isSelected()) {
                        checkedCategories.add(category);
                    } else {
                        checkedCategories.remove(category);
                    }
                    categoryChecked();
                }
            });
            categoriesPanel.add(checkBox);
        }
    }

    private void categoryChecked() {
        HashMap<Integer, Question> checkedQuestions = new HashMap<>();
        for (int i = 0; i < checkedCategories.size(); ++i) {
            String category = checkedCategories.get(i);
            List<Question> questions = QuestionManager.getInstance().getQuestions(category);
            for (int j = 0; j < questions.size(); ++j) {
                Question q = questions.get(j);
                checkedQuestions.put(q.getId(), q);
            }
        }
        setQuestionCount(checkedQuestions.size());
    }

    private boolean checkData() {
        String errors = "";
        if (variantsSpinnerModel.getNumber().intValue() == 0) {
            errors += "Количество вариантов не может быть нулевым \n";
            variantsSpinner.setBorder(new LineBorder(Color.RED));
        } else {
            variantsSpinner.setBorder(null);
        }

        if (questionSpinnerModel.getNumber().intValue() == 0) {
            errors += "Количество вопросов не может быть нулевым \n";
            questionSpinner.setBorder(new LineBorder(Color.RED));
        } else {
            questionSpinner.setBorder(null);
        }

        if (errors.length() > 0) {
            JOptionPane.showMessageDialog(this, errors, "В форме есть ошибки", JOptionPane.ERROR_MESSAGE);
        }
        return errors.equals("");
    }
}
