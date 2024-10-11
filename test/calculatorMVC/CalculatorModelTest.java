package calculatorMVC;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import calculatorMVC.CalculatorModel;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatorModelTest {
    private CalculatorModel model;

    @BeforeEach
    public void setUp() {
        model = new CalculatorModel();
    }

    @Test
    public void testAddOperation() {
        assertEquals(8, model.calculate(5, 3, "+"), "5 + 3 should equal 8");
    }

    @Test
    public void testSubtractOperation() {
        assertEquals(-2, model.calculate(1, 3, "-"), "1 - 3 should equal -2");
    }

    @Test
    public void testMultiplyOperation() {
        assertEquals(-15, model.calculate(-5, 3, "*"), "-5 * 3 should equal -15");
    }

    @Test
    public void testDivideOperation() {
        assertEquals(3, model.calculate(6, 2, "/"), "6 / 2 should equal 3");
    }

    @Test
    public void testDivideByZero() {
        assertThrows(ArithmeticException.class, () -> model.calculate(5, 0, "/"), "Dividing by zero should throw ArithmeticException");
    }

    @Test
    public void testSquareOperation() {
        assertEquals(16, model.calculateSquare(4), "Square of 4 should be 16");
    }

    @Test
    public void testSquareRootOperation() {
        assertEquals(4, model.calculateSquareRoot(16), "Square root of 16 should be 4");
    }

    @Test
    public void testSquareRootNegativeInput() {
        assertThrows(ArithmeticException.class, () -> model.calculateSquareRoot(-4), "Square root of negative number should throw ArithmeticException");
    }

    @Test
    public void testMemoryAdd() {
        model.calculate(5, 3, "+");
        model.addToMemory(model.getCurrentValue());
        assertEquals(8, model.getMemoryValue(), "Memory should have the result of 5 + 3");
    }

    @Test
    public void testMemorySubtract() {
        model.addToMemory(8); // Assuming memory has 8
        model.calculate(2, 3, "+");
        model.subtractFromMemory(model.getCurrentValue());
        assertEquals(3, model.getMemoryValue(), "Memory should have 8 - 5");
    }

    @Test
    public void testMemoryRecall() {
        model.addToMemory(8); // Add 8 to memory
        double recalledValue = model.getMemoryValue();
        assertEquals(8, recalledValue, "Recalled memory value should be 8");
    }

    @Test
    public void testMemoryClear() {
        model.addToMemory(8); // Add 8 to memory
        model.clearMemory();
        assertEquals(0, model.getMemoryValue(), "Memory should be cleared to 0");
    }
}
