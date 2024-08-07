package bl;

import expressions.*;
import lombok.Getter;
import util.Utils;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Calculator {

    private ExpressionsFactory expressions;
    @Getter
    private Map<String, Double> variables;
    private Utils utils;

    public Calculator() {
        this.variables = new HashMap<>();
        this.utils = new Utils();
        this.expressions = new ExpressionsFactory();
    }

    public void calc(String expression) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Stack<Expression> stack = new Stack<>();
        List<AfterActionOperation> afterActionOperations = new ArrayList<>();

        if(expression.isEmpty()){
            return;
        }

        ShuntingYard shuntingYard = new ShuntingYard(expression);
        Queue<String> queue = shuntingYard.getShuntingYard();

        while (!queue.isEmpty()) {
            String token = queue.poll();

            if (this.utils.isDouble(token)) { // If number
                stack.push(new Num(Double.parseDouble(token)));
            } else if(this.utils.isString(token)){
                stack.push(new Num(this.getLocalVarValue(token)));
                // is there an '++'/'--' operator after
                if(!queue.isEmpty() && this.utils.isOutOfActionOperator(queue.peek())){
                    afterActionOperations.add(new AfterActionOperation(token, queue.poll()));
                }
            } else if (this.utils.isOutOfActionOperator(token) && !queue.isEmpty() && this.utils.isString(queue.peek())) {
                // if operator is '++' or '--'
                this.handelOutOfActionOperator(token,queue.peek());
                stack.push(new Num(this.getLocalVarValue(queue.poll())));
            }
            else { // If regular operator
                Expression right = (stack.isEmpty()) ? new Num(0) : stack.pop();
                Expression left = (stack.isEmpty()) ? new Num(0) : stack.pop();

                stack.push(this.expressions.getExpression(token, right, left));
            }
        }

        this.variables.put(shuntingYard.getVarNameToSaveResult(), stack.pop().calc());
        this.doAfterActionOperations(afterActionOperations);
    }

    private double getLocalVarValue(String key){
        if(!this.variables.containsKey(key)){
            throw new IllegalArgumentException();
        }
        return this.variables.get(key);
    }

    private void doAfterActionOperations(List<AfterActionOperation> afterActionOperations){
        for(AfterActionOperation operation : afterActionOperations){
            this.handelOutOfActionOperator(operation.getOperator(), operation.getKey());
        }
    }

    private void handelOutOfActionOperator(String operator, String token){
        double valueToAdd = operator.equals("++") ? 1.0 : -1.0;
        this.variables.merge(token,valueToAdd,Double::sum);
    }

    public void printResults(){
        System.out.println(this.variables);
    }
}
