package ru.earring.testgenerator.test;

import org.junit.Assert;
import org.junit.Test;

import javax.swing.*;

/**
 * Набор тестов, проверяющих корректность работы стартера
 */
public class StarterTest extends ATest {

    /**
     * Проверка, сформировался ли класс основной формы
     */
    @Test
    public void testExistingMainFrame() {
        Assert.assertNotNull(starter.getMainFrame());
    }

    /**
     * Проверка темы Swing
     */
    @Test
    public void testUIScheme() {
        Assert.assertEquals("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel", UIManager.getLookAndFeel().getClass().getName());
    }
}
