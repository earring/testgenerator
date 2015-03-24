package ru.earrring.testgenerator;

import javax.swing.*;

public class MainForm extends JFrame{
    public static void main(String[] args) {
        new MainForm().setVisible(true);
    }

    public MainForm() {
        setSize(300, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
