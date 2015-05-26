package ru.earrring.testgenerator.frames;

import org.scilab.forge.jlatexmath.ParseException;
import ru.earrring.testgenerator.components.AnswerVariantComponent;
import ru.earrring.testgenerator.db.Answer;
import ru.earrring.testgenerator.db.Question;
import ru.earrring.testgenerator.dbWork.QuestionManager;
import ru.earrring.testgenerator.generators.LaTeXGenerator;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class AddingFrame extends AFrame implements AnswerAddableFrame {

    private final static int MAX_VARIANT = 15;

    private int gridy = 0;
    private int answersNumber = 0;
    private JLabel addVariantLabel;
    private JTextArea descriptionArea;
    private JTextField categoriesField;
    private JPanel answerVariantsPanel;
    private JScrollPane answerScrollPane;
    private JButton addAnswerButton;
    private JPanel mainPanel;
    private JButton addQuestionButton;
    private JButton previewQuestionButton;
    private ArrayList<AnswerVariantComponent> answerVariantComponentList;

    protected void adjustFrameSettings() {
        setTitle("Добавление вопроса");
        setSize(700, 700);
        setResizable(false);
    }

    protected void adjustLayout() {
        // настройка mainPanel
        mainPanel = new JPanel();
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
        mainPanel.setLayout(new GridBagLayout());

        // настройка поля с описанием вопроса
        descriptionArea = new JTextArea(5, 50);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionArea);
        descriptionArea.setToolTipText("Введите текст вопроса:");
        descriptionArea.setFont(new Font("Serif", Font.ITALIC, 16));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        descriptionScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        addComponentToMainPanel(descriptionScrollPane, 0, 0, 1.5, 2);

        // настройка поля с категориями вопроса
        JPanel categoriesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        categoriesPanel.add(new JLabel("Категории вопроса через запятую: "));
        categoriesField = new JTextField("", 20);
        categoriesField.setFont(new Font("Serif", Font.ITALIC, 16));
        categoriesPanel.add(categoriesField);
        addComponentToMainPanel(categoriesPanel, 0, 1, 0.15, 2);

        // настройка кнопки с добавлением варианта ответа
        addAnswerButton = new JButton("Добавить вариант ответа");
        addAnswerButton.setFont(new Font("Arial", Font.PLAIN, 22));
        addComponentToMainPanel(addAnswerButton, 0, 2, 0.15, 2);

        // настройка добавления вариантов ответов
        answerVariantComponentList = new ArrayList<>();
        answerVariantsPanel = new JPanel();
        answerVariantsPanel.setLayout(new BoxLayout(answerVariantsPanel, BoxLayout.Y_AXIS));
        answerScrollPane = new JScrollPane(answerVariantsPanel);
        answerScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        answerScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        addComponentToMainPanel(answerScrollPane, 0, 3, 2.5, 2);

        // настройка кнопки предпросмотра вопроса
        previewQuestionButton = new JButton("Предпросмотр");
        previewQuestionButton.setFont(new Font("Arial", Font.PLAIN, 22));
        addComponentToMainPanel(previewQuestionButton, 0, 4, 0.15, 1);

        // настройка кнопки добавления вопроса
        addQuestionButton = new JButton("Добавить вопрос в БД");
        addQuestionButton.setFont(new Font("Arial", Font.PLAIN, 22));
        addComponentToMainPanel(addQuestionButton, 1, 4, 0.15, 1);
    }

    /**
     * Настройка слушателей (кнопок и других компонентов)
     */
    protected void setListeners() {
        addAnswerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAnswerVariantComponent("", false);
                if (answersNumber == MAX_VARIANT) {
                    addAnswerButton.setEnabled(false);
                }
            }
        });
        addQuestionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkQuestionData()) {
                    Question question = createQuestion();
                    List<Answer> answerList = createAnswerList(question);
                    QuestionManager.getInstance().addQuestion(question, answerList);
                    descriptionArea.setText("");
                    categoriesField.setText("");
                    // храним переменную числа ответов из-за модификации числа ответов в цикле
                    int answers = answersNumber;
                    for (int i = 0; i < answers; i++) {
                        deleteAnswerVariantComponent(1);
                    }
                    JOptionPane.showMessageDialog(AddingFrame.this, "Вопрос успешно добавлен", "Успех", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        previewQuestionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkQuestionData()) {
                    Question question = createQuestion();
                    List<Answer> answerList = createAnswerList(question);
                    question.setAnswers(answerList);
                    AFrame previewFrame = new PreviewQuestionFrame(question);
                    previewFrame.start();
                    previewFrame.setParentFrame(AddingFrame.this);
                    previewFrame.setVisible(true);
                }
            }
        });
    }

    private List<Answer> createAnswerList(Question question) {
        List<Answer> answerList = new ArrayList<>();

        // задание ответов на вопрос
        for (AnswerVariantComponent component : answerVariantComponentList) {
            Answer answer = new Answer();
            answer.setQuestion(question);
            answer.setCorrect(component.isCorrect());
            answer.setValue(component.getText());
            answerList.add(answer);
        }

        return answerList;
    }

    private Question createQuestion() {
        Question question = new Question();

        // задание текста вопроса
        question.setDescription(descriptionArea.getText());

        // задание категорий вопроса
        // переделывание разделителей с , на | с убиранием лишних пробелов
        String[] categoryArray = categoriesField.getText().split(",");
        ArrayList<String> categoryList = new ArrayList<>();
        for (int i = 0; i < categoryArray.length; i++) {
            String category = categoryArray[i].trim();
            if (!category.equals("")) {
                categoryList.add(category);
            }
        }
        String categoryString = String.join("|", categoryList);
        question.setCategory(categoryString);

        return question;
    }

    private boolean checkQuestionData() {
        String errors = "";
        if (descriptionArea.getText().length() == 0) {
            errors += "Текст вопроса должен быть заполнен \n";
            descriptionArea.setBorder(new LineBorder(Color.RED));
        } else {
            try {
                LaTeXGenerator.checkPhraseWithFormulas(descriptionArea.getText());
            } catch (ParseException e) {
                errors += "Ошибка в формуле: " + e.getMessage() + "\n";
            }
            // если ошибок с текстовым полем так и не было
            if (errors.equals("")) {
                descriptionArea.setBorder(null);
            }
        }

        if (categoriesField.getText().length() == 0) {
            errors += "У вопроса должна быть хотя бы одна категория \n";
            categoriesField.setBorder(new LineBorder(Color.RED));
        } else {
            categoriesField.setBorder(null);
        }

        if (answerVariantComponentList.size() == 0) {
            errors += "В вопросе должен быть хотя бы один вариант ответа \n";
            answerVariantsPanel.setBorder(new LineBorder(Color.RED));
        }
        boolean isAnswersCorrect = true;
        for (AnswerVariantComponent component : answerVariantComponentList) {
            if (component.getText().length() == 0) {
                errors += "Варианты ответа не должны быть пусты";
                answerVariantsPanel.setBorder(new LineBorder(Color.RED));
                isAnswersCorrect = false;
                break;
            }
        }
        if (isAnswersCorrect && answerVariantComponentList.size() != 0) {
            answerVariantsPanel.setBorder(null);
        }

        if (errors.length() > 0) {
            JOptionPane.showMessageDialog(this, errors, "В форме есть ошибки", JOptionPane.ERROR_MESSAGE);
        }
        return errors.equals("");
    }

    @Override
    public void close() {
        setVisible(false);
    }

    /**
     * Добавление компонента на основную панель фрейма
     *
     * @param component компонент, который добавляется
     * @param gridx     параметр x в таблице размещения элементов
     * @param gridy     параметр y в таблице размещения элементов
     * @param weighty   "вес" по вертикали ячейки, в которой размещен элемент
     */
    private void addComponentToMainPanel(JComponent component, int gridx, int gridy, double weighty, int gridwidth) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTH;
        c.weightx = 1.0;
        c.weighty = weighty;
        c.gridwidth = gridwidth;
        c.gridx = gridx;
        c.gridy = gridy;
        mainPanel.add(component, c);
    }

    public void addAnswerVariantComponent(String text, boolean isCorrect) {
        AnswerVariantComponent newAnswerVariantComponent = new AnswerVariantComponent(this, answersNumber + 1, text, isCorrect);
        answerVariantsPanel.add(newAnswerVariantComponent);
        answerVariantComponentList.add(newAnswerVariantComponent);

        answersNumber = answersNumber + 1;

        answerScrollPane.revalidate();
        answerScrollPane.repaint();
    }

    /**
     * Удаление варианта ответа на вопрос
     *
     * @param position номер варианта ответа на вопрос (нумерация начинается с единицы)
     */
    public void deleteAnswerVariantComponent(int position) {
        answerVariantsPanel.remove(answerVariantComponentList.get(position - 1));
        answerVariantComponentList.remove(position - 1);
        for (int i = position - 1; i < answerVariantComponentList.size(); i++) {
            answerVariantComponentList.get(i).setNumber(i + 1);
        }
        answersNumber = answersNumber - 1;
        answerScrollPane.revalidate();
        answerScrollPane.repaint();
    }
}
