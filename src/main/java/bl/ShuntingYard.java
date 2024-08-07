package bl;

import lombok.Getter;
import util.Utils;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class ShuntingYard {

    private String varNameToSaveResult;
    private Queue<String> shuntingYard;
    private Map<String, Set<String>> precedenceRules;
    private Utils utils;

    public ShuntingYard(String expression) {
        this.utils = new Utils();
        this.precedenceRules = Map.of(
                "+", Set.of("-", "*", "/"),
                "-", Set.of("*", "/"),
                "*", Set.of("/")
        );
        this.shuntingYard(expression);
    }

    /**
     * The Shunting Yard algorithm is a method for parsing mathematical expressions specified in infix notation.
     * It converts infix expressions (e.g., "3 + 4") into postfix notation (e.g., "3 4 +"), which is easier to evaluate.
     *
     * The algorithm uses two structures:
     * 1. An operator stack to hold operators and parentheses.
     * 2. An output queue to build the final postfix expression.
     *
     * The steps are as follows:
     * 1. Read tokens from the input expression:
     *    - If the token is a number or a variable, add it to the output queue.
     *    - If the token is a left parenthesis '(', push it onto the stack.
     *    - If the token is a right parenthesis ')', pop operators from the stack to the output queue until
     *      a left parenthesis is encountered. Remove the left parenthesis from the stack.
     *    - If the token is an operator, pop operators from the stack to the output queue until the stack is empty
     *      or the top of the stack has an operator of lower precedence. Then push the token onto the stack.
     * 2. After reading all tokens, pop any remaining operators from the stack to the output queue.
     *
     * This algorithm ensures that the precedence and associativity of operators are respected, resulting in
     * a postfix expression that can be easily evaluated.
     */
    private void shuntingYard(String expression) {
        Queue<String> queue = new LinkedList<>();
        Stack<String> stack = new Stack<>();
        List<String> expList = this.splitToExpressionsList(expression);

        this.handelJavaNumericOperators(expList);

        int i = 0;
        while (i < expList.size()) {
            String token = expList.get(i);

            if (this.isTokenNumberOrVar(token)) {
                queue.add(token);
            } else if (token.equals("(")) { // If (
                stack.push(token);
            } else if (token.equals(")")) { // If )
                String pop = stack.pop();

                // Check if there is a ( at the top of the stack
                while (!pop.equals("(")) {
                    queue.add(pop);
                    pop = stack.pop();
                }
                // all elements were popped till ( was popped
            } else { // If regular operator
                // Check if at the top of the stack there's operator with greater precedence
                while (!stack.isEmpty() && hasHigherPrecedence(stack.peek(), token)) {
                    queue.add(stack.pop());
                }
                stack.push(token);
            }
            i++;
        }

        while (!stack.isEmpty()) {
            queue.add(stack.pop());
        }

        this.shuntingYard = queue;
    }

    // replace '+='/'-='/'++'/'--' with 'x +/- (expression)'
    private void handelJavaNumericOperators(List<String> expList){
        if(this.utils.isOutOfActionOperator(expList.get(0)) || this.utils.isOutOfActionOperator(expList.get(1))){
            this.handelOutOfActionOnly(expList);
        } else {
            // Extract indication to which var to save the result to
            this.varNameToSaveResult = expList.get(0);

            String equalsOperator = expList.get(1);
            if(!equalsOperator.equals("=")){
                expList.add(2, this.varNameToSaveResult);
                String operator = String.valueOf(equalsOperator.charAt(0));
                expList.add(3, operator);
                expList.add(4,"(");
                expList.add(")");
            }
            // remove var and '='/'+='/'-=' operator
            expList.remove(0);
            expList.remove(0);
        }
    }

    // Replace '++X'/ '--X' / 'X++' / 'X--' with 'X +/- 1'
    private void handelOutOfActionOnly(List<String> expList){
        String operator;
        int operatorIndex = this.utils.isOutOfActionOperator(expList.get(0)) ? 0 : 1;
        operator = expList.get(operatorIndex);
        expList.remove(operatorIndex);
        this.varNameToSaveResult = expList.get(0);

        expList.set(0, this.varNameToSaveResult);
        expList.add(1, String.valueOf(operator.charAt(0)));
        expList.add(2, "1");
    }

    private boolean hasHigherPrecedence(String top, String current) {
        return precedenceRules.getOrDefault(current, Set.of()).contains(top);
    }

    private List<String> splitToExpressionsList(String expression){
        Pattern pattern = Pattern.compile("(\\d+\\.?\\d*|[+\\-*/=]+|[a-zA-Z]+|\\S)");
        Matcher matcher = pattern.matcher(expression);
        List<String> expList = new ArrayList<>();
        while (matcher.find()) {
            expList.add(matcher.group());
        }
        return expList;
    }

    private boolean isTokenNumberOrVar(String token){
        return this.utils.isDouble(token) || this.utils.isString(token) || this.utils.isOutOfActionOperator(token);
    }
}
