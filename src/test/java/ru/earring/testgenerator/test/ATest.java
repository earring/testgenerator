package ru.earring.testgenerator.test;

import org.junit.BeforeClass;
import ru.earrring.testgenerator.Starter;

public abstract class ATest {
    protected static Starter starter = new Starter();

    @BeforeClass
    public static void start() {
        starter.main(null);
    }

}