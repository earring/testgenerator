package ru.earrring.testgenerator;

import javax.swing.*;

/**
 * �����-������� �������. �������������� ��� ������, ����������� ��� ������ ��������� � ��������� �������� �����
 */
public class Starter {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        new MainFrame().setVisible(true);
    }
}
