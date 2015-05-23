package ru.earrring.testgenerator.frames;

import com.itextpdf.text.DocumentException;
import ru.earrring.testgenerator.db.Question;
import ru.earrring.testgenerator.dbWork.QuestionManager;
import ru.earrring.testgenerator.generators.PDFGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Created by nenagleyko on 22.05.2015.
 */
public class GenerationFrame extends AFrame {

    private int availableQuestions = 0;
    private JPanel mainPanel;
    private SpinnerNumberModel variantsSpinnerModel;
    private SpinnerNumberModel questionSpinnerModel;
    private JLabel questionsCountLabel;

    private JPanel categoriesPanel;
    private JButton generateButton;

    private List<String> checkedCategories;

   public GenerationFrame()
    {
        super();
        checkedCategories = new ArrayList<>();
    }
    private void setQuestionCount(int count)
    {
        availableQuestions = count;
        questionsCountLabel.setText(String.format("%d", availableQuestions));
        questionSpinnerModel.setMaximum(count);
        variantsSpinnerModel.setMaximum(count);
        Number questions = questionSpinnerModel.getNumber();
        Number variants = variantsSpinnerModel.getNumber();
        if (questions.intValue() > count)
        {
            questionSpinnerModel.setValue(count);
        }
        if (variants.intValue() > count)
        {
            variantsSpinnerModel.setValue(count);
        }

    }

    @Override
    protected void adjustFrameSettings() {
        setTitle("Генерация тестов");
        setSize(700, 550);
        setResizable(false);
    }

    @Override
    protected void adjustLayout() {
// настройка mainPanel
        mainPanel = new JPanel();
        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        mainPanel.setLayout(new GridLayout(1,2));

        JPanel leftPanel = new JPanel(new GridBagLayout());
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setMinimumSize(new Dimension(200,300));

        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);

        JLabel label = new JLabel("Категории:");


        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 0;
        c.gridy = 0;
        c.weighty = 0;
        leftPanel.add(label,c);

        categoriesPanel = new JPanel(new GridBagLayout());
        JScrollPane scrollPane = new JScrollPane(categoriesPanel);
        scrollPane.setMinimumSize(new Dimension(350, 100));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTH;
        c.gridx = 0;
        c.gridy = 1;
        c.weighty = 3;
        c.weightx = 1;
        leftPanel.add(scrollPane, c);

        JLabel label1 = new JLabel("Количество доступных вопросов:");
        c = new GridBagConstraints();
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 0;
        c.gridy = 0;
        rightPanel.add(label1,c);

        questionsCountLabel = new JLabel("0");
        questionsCountLabel.setFont(new Font("Courier",Font.BOLD,20));
        c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 1;
        c.gridy = 0;
        rightPanel.add(questionsCountLabel, c);

        JLabel label3 = new JLabel("Количество вариантов:");
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        rightPanel.add(label3,c);

        variantsSpinnerModel = new SpinnerNumberModel(0,0,10,1);
        JSpinner variantsSpinner = new JSpinner(variantsSpinnerModel);
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 1;
        rightPanel.add(variantsSpinner,c);

        JLabel label4 = new JLabel("Количество вопросов в каждом варианте:");
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        rightPanel.add(label4,c);

        questionSpinnerModel = new SpinnerNumberModel(0,0,10,1);
        JSpinner questionSpinner = new JSpinner(questionSpinnerModel);
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 2;
        rightPanel.add(questionSpinner,c);


        generateButton = new JButton("Сгенерировать PDF");
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;
        rightPanel.add(generateButton,c);

    }

    //public GridBagConstraints(int gridx, int gridy,
//    int gridwidth, int gridheight,
//    double weightx, double weighty,
//    int anchor, int fill,
//    Insets insets, int ipadx, int ipady) {

    private void addComponent(JPanel panel,
                              Component component,
                              int gridx,
                              int gridy,
                              double weighty)
    {
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.NORTH;
        c.weightx = 1.0;
        c.gridx = gridx;
        c.gridy = gridy;
        c.weighty = weighty;
        panel.add(component, c);

    }

    @Override
    protected void setListeners() {
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    PDFGenerator.getInstance().createPDF(variantsSpinnerModel.getNumber().intValue(), questionSpinnerModel.getNumber().intValue(), checkedCategories);
                    JOptionPane.showMessageDialog(GenerationFrame.this, "PDF документ был успешно сгенерирован", "Генерация завершена", JOptionPane.INFORMATION_MESSAGE);
                    GenerationFrame.this.close();
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(GenerationFrame.this, e1.getMessage(), "Ошибка ввода-вывода", JOptionPane.ERROR_MESSAGE);
                }  catch (DocumentException e1) {
                    JOptionPane.showMessageDialog(GenerationFrame.this, e1.getMessage(), "Ошибка взаимодействия с PDF", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    @Override
    public void close() {

    }

    public void setCategories(List<String> categories)
    {
        for (int i = 0; i<categories.size(); ++i)
        {
            String category = categories.get(i);
            JCheckBox checkBox = new JCheckBox(category);
            checkBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (checkBox.isSelected())
                        checkedCategories.add(category);
                    else
                        checkedCategories.remove(category);

                        categoryChecked();
                }
            });
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.HORIZONTAL;
            c.anchor = GridBagConstraints.NORTH;
            c.weightx = 1.0;
//            c.weighty = 1.0;
            c.gridx = 1;
            c.gridy = i;
            categoriesPanel.add(checkBox, c);
            System.out.println("Category" + category);
        }

    }

    private void categoryChecked()
    {
        HashMap<Integer,Question> checkedQuestions = new HashMap<>();
        for (int i = 0; i<checkedCategories.size();++i)
        {
            String category = checkedCategories.get(i);
            List<Question> questions = QuestionManager.getInstance().getQuestions(category);
            for (int j = 0; j<questions.size();++j)
            {
                Question q = questions.get(j);
                checkedQuestions.put(q.getId(),q);
            }
        }

        setQuestionCount(checkedQuestions.size());

    }
}
