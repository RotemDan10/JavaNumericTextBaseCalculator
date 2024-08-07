import bl.ShuntingYard;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShuntingYardTest {

    @Test
    public void testShuntingYardBasicExpression() {
        ShuntingYard shuntingYard = new ShuntingYard("a = 3 + 4");
        Queue<String> expected = new LinkedList<>();
        expected.add("3");
        expected.add("4");
        expected.add("+");

        assertEquals(expected, shuntingYard.getShuntingYard());
        assertEquals("a", shuntingYard.getVarNameToSaveResult());
    }

    @Test
    public void testShuntingYardBasicJavaNumericPlusPreExpression() {
        ShuntingYard shuntingYard = new ShuntingYard("++a");
        Queue<String> expected = new LinkedList<>();
        expected.add("a");
        expected.add("1");
        expected.add("+");

        assertEquals(expected, shuntingYard.getShuntingYard());
        assertEquals("a", shuntingYard.getVarNameToSaveResult());
    }

    @Test
    public void testShuntingYardBasicJavaNumericMinusPostExpression() {
        ShuntingYard shuntingYard = new ShuntingYard("a--");
        Queue<String> expected = new LinkedList<>();
        expected.add("a");
        expected.add("1");
        expected.add("-");

        assertEquals(expected, shuntingYard.getShuntingYard());
        assertEquals("a", shuntingYard.getVarNameToSaveResult());
    }

    @Test
    public void testShuntingYardExpressionWithParentheses() {
        ShuntingYard shuntingYard = new ShuntingYard("a = (3 + 4) * 5");
        Queue<String> expected = new LinkedList<>();
        expected.add("3");
        expected.add("4");
        expected.add("+");
        expected.add("5");
        expected.add("*");

        assertEquals(expected, shuntingYard.getShuntingYard());
        assertEquals("a", shuntingYard.getVarNameToSaveResult());
    }

    @Test
    public void testShuntingYardExpressionWithNegativeNumber() {
        ShuntingYard shuntingYard = new ShuntingYard("a = 5 - (3 - 2)");
        Queue<String> expected = new LinkedList<>();
        expected.add("5");
        expected.add("3");
        expected.add("2");
        expected.add("-");
        expected.add("-");

        assertEquals(expected, shuntingYard.getShuntingYard());
        assertEquals("a", shuntingYard.getVarNameToSaveResult());
    }

    @Test
    public void testShuntingYardExpressionWithEqualsOperator() {
        ShuntingYard shuntingYard = new ShuntingYard("result += 2 * (3 + 4)");
        Queue<String> expected = new LinkedList<>();
        expected.add("result");
        expected.add("2");
        expected.add("3");
        expected.add("4");
        expected.add("+");
        expected.add("*");
        expected.add("+");

        assertEquals(expected, shuntingYard.getShuntingYard());
        assertEquals("result", shuntingYard.getVarNameToSaveResult());
    }

    @Test
    public void testShuntingYardExpressionWithNestedParentheses() {
        ShuntingYard shuntingYard = new ShuntingYard("x = (1 + (2 * (3 / (4 - 1))))");
        Queue<String> expected = new LinkedList<>();
        expected.add("1");
        expected.add("2");
        expected.add("3");
        expected.add("4");
        expected.add("1");
        expected.add("-");
        expected.add("/");
        expected.add("*");
        expected.add("+");

        assertEquals(expected, shuntingYard.getShuntingYard());
        assertEquals("x", shuntingYard.getVarNameToSaveResult());
    }

    @Test
    public void testShuntingYardExpressionWithVariables() {
        ShuntingYard shuntingYard = new ShuntingYard("result = a + b * c - (d / e)");
        Queue<String> expected = new LinkedList<>();
        expected.add("a");
        expected.add("b");
        expected.add("c");
        expected.add("*");
        expected.add("d");
        expected.add("e");
        expected.add("/");
        expected.add("-");
        expected.add("+");

        assertEquals(expected, shuntingYard.getShuntingYard());
        assertEquals("result", shuntingYard.getVarNameToSaveResult());
    }
}
