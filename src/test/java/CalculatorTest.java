import bl.Calculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    public void setUp() {
        calculator = new Calculator();
    }

    @Test
    public void testCalculatorBasicExpression() throws Exception {
        calculator.calc("result = 3 + 4");
        assertEquals(7.0, calculator.getVariables().get("result"));
    }

    @Test
    public void testCalculatorExpressionWithVariables() throws Exception {
        calculator.calc("a = 5");
        calculator.calc("b = 10");
        calculator.calc("result = a + b * 2");
        assertEquals(25.0, calculator.getVariables().get("result"));
    }

    @Test
    public void testCalculatorExpressionWithUnaryOperators() throws Exception {
        calculator.calc("x = 10");
        calculator.calc("x++");
        assertEquals(11.0, calculator.getVariables().get("x"));

        calculator.calc("x--");
        assertEquals(10.0, calculator.getVariables().get("x"));
    }

    @Test
    public void testCalculatorExpressionWithParentheses() throws Exception {
        calculator.calc("result = (3 + 2) * (4 - 1)");
        assertEquals(15.0, calculator.getVariables().get("result"));
    }

    @Test
    public void testCalculatorExpressionWithMultipleOperators() throws Exception {
        calculator.calc("result = 5 + 2 * 3 - 4 / 2");
        assertEquals(9.0, calculator.getVariables().get("result"));
    }

    @Test
    public void testCalculatorHandlingOfUndefinedVariables() throws Exception {
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.calc("result = a + b");
        });
    }

    @Test
    public void testCalculatorAfterActionOperations() throws Exception {
        calculator.calc("y = 10");
        calculator.calc("y++");
        assertEquals(11.0, calculator.getVariables().get("y"));

        calculator.calc("y--");
        assertEquals(10.0, calculator.getVariables().get("y"));
    }
}
