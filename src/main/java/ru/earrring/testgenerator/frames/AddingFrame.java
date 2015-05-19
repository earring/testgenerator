package ru.earrring.testgenerator.frames;

import ru.earrring.testgenerator.components.AnswerVariantComponent;
import ru.earrring.testgenerator.db.Answer;
import ru.earrring.testgenerator.db.Question;
import ru.earrring.testgenerator.dbWork.QuestionManager;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class AddingFrame extends AFrame implements AnswerAddableFrame {

    private final static int MAX_VARIANT = 15;

    private int gridy = 0;
    private int answersGridy = 0;
    private JLabel addVariantLabel;
    private JTextArea descriptionArea;
    private JTextField categoriesField;
    private JPanel answerVariantsPanel;
    private JScrollPane answerScrollPane;
    private JButton addAnswerButton;
    private JPanel mainPanel;
    private JButton addQuestionButton;
    private ArrayList<AnswerVariantComponent> answerVariantComponentList;

    protected void adjustFrameSettings() {
        setTitle("Добавить вопрос");
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
        addComponentToMainPanel(descriptionScrollPane, 0, 0, 1.5);

        // настройка поля с категориями вопроса
        JPanel categoriesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        categoriesPanel.add(new JLabel("Категории вопроса через запятую: "));
        categoriesField = new JTextField("", 20);
        categoriesField.setFont(new Font("Serif", Font.ITALIC, 16));
        categoriesPanel.add(categoriesField);
        addComponentToMainPanel(categoriesPanel, 0, 1, 0.15);

        // настройка кнопки с добавлением варианта ответа
        addAnswerButton = new JButton("Добавить вариант ответа");
        addAnswerButton.setFont(new Font("Arial", Font.PLAIN, 22));
        addComponentToMainPanel(addAnswerButton, 0, 2, 0.15);

        // настройка добавления вариантов ответов
        answerVariantComponentList = new ArrayList<>();
        answerVariantsPanel = new JPanel();
        answerVariantsPanel.setLayout(new GridBagLayout());
        answerScrollPane = new JScrollPane(answerVariantsPanel);
        answerScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        answerScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        addComponentToMainPanel(answerScrollPane, 0, 3, 2.5);

        // настройка кнопки добавления вопроса
        addQuestionButton = new JButton("Добавить вопрос в БД");
        addQuestionButton.setFont(new Font("Arial", Font.PLAIN, 22));
        addComponentToMainPanel(addQuestionButton, 0, 4, 0.15);
    }

    /**
     * Настройка слушателей (кнопок и других компонентов)
     */
    protected void setListeners() {
        addAnswerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAnswerVariantComponent("", false);
                if (answersGridy == MAX_VARIANT) {
                    addAnswerButton.setEnabled(false);
                }
            }
        });
        addQuestionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkQuestionData()) {
                    try {
                        QuestionManager.getInstance().addQuestion(createQuestion());
                        System.out.println("Adding question called");
                    } catch (SQLException exception) {
                        JOptionPane.showMessageDialog(AddingFrame.this, exception.getMessage(), "Ошибка при выполнении SQL запроса", JOptionPane.ERROR_MESSAGE);
                        exception.printStackTrace();
                    }
                }
            }
        });
    }

    private Question createQuestion() throws SQLException {
        Question question = new Question();
        Collection<Answer> answerCollection = QuestionManager.getInstance().getEmptyCollectionAnswer();

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

        // задание ответов на вопрос
        for (AnswerVariantComponent component : answerVariantComponentList) {
            Answer answer = new Answer();
            answer.setQuestion(question);
            answer.setCorrect(component.isCorrect());
            answer.setValue(component.getText());
            answerCollection.add(answer);
        }
        question.setAnswers(answerCollection);

        System.out.println(question);
        return question;
    }

    private boolean checkQuestionData() {
        String errors = "";

        if (descriptionArea.getText().length() == 0) {
            errors += "Текст вопроса должен быть заполнен \n";
            descriptionArea.setBorder(new LineBorder(Color.RED));
        } else {
            descriptionArea.setBorder(null);
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
    private void addComponentToMainPanel(JComponent component, int gridx, int gridy, double weighty) {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTH;
        c.weightx = 1.0;
        c.weighty = weighty;
        c.gridx = gridx;
        c.gridy = gridy;
        mainPanel.add(component, c);
    }

    public void addAnswerVariantComponent(String text, boolean isCorrect) {
        GridBagConstraints c = new GridBagConstraints();

        AnswerVariantComponent newAnswerVariantComponent = new AnswerVariantComponent(this, answersGridy + 1, text, isCorrect);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTH;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = 1;
        c.gridy = answersGridy;
        answerVariantsPanel.add(newAnswerVariantComponent, c);
        answerVariantComponentList.add(newAnswerVariantComponent);

        answersGridy = answersGridy + 1;

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
        answersGridy = answersGridy - 1;
        answerScrollPane.revalidate();
        answerScrollPane.repaint();
    }
}
