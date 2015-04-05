package ru.earring.testgenerator.test;

import org.junit.BeforeClass;
import ru.earrring.testgenerator.Starter;

/**
 * Абстрактный тест, в котором нужно выполнять всю необходимую подготовочную работу для каждого теста
 */
public abstract class ATest {

    /**
     * Ссылка на класс-стартер системы для того, чтобы можно было перед запуском всех тестов "запустить" систему
     */
    protected static Starter starter = new Starter();

    /**
     * Запуск системы. Выполняется перед запуском всех тестов
     */
    @BeforeClass
    public static void start() {
        Starter.main(null);
    }

}