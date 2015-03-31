package ru.earring.testgenerator.test;

import org.junit.Assert;
import org.junit.Test;

import javax.swing.*;

public class StarterTest extends ATest {

    @Test
    public void testExistingMainFrame() {
        Assert.assertNotNull(starter.getMainFrame());
    }

    @Test
    public void testUIScheme() {
        Assert.assertEquals("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel", UIManager.getLookAndFeel().getClass().getName());
    }
}
