package ru.earring.testgenerator.test;

import org.junit.Test;
import ru.earrring.testgenerator.frames.MainFrame;

public class MainFrameTest extends ATest{

    private MainFrame mainFrame = starter.getMainFrame();

    @Test
    public void testAddingFormButton(){
        mainFrame.getAddingFormButton().doClick();
    }

    @Test
    public void testGeneratingFormButton(){
        mainFrame.getGeneratingFormButton().doClick();
    }

    @Test
    public void testViewingFormButton(){
        mainFrame.getViewingFormButton().doClick();
    }
}
