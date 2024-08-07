import bl.Calculator;
import java.util.*;

public class main {

    public static void main(String[] args){
        Calculator calculator = new Calculator();
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.println("Enter an expression to calculate:");
            String expression = scanner.nextLine();
            if(expression.equals("exit")){
                break;
            }
            try {
                calculator.calc(expression);
            } catch (NoSuchMethodException e) {
                System.out.println("Invalid Operator in Expression");
            } catch (IllegalArgumentException e) {
                System.out.println("Unknown variable is used");
            } catch (Exception e) {
                System.out.println("Problem evaluating expression please try again");
            }
            calculator.printResults();
        }
        calculator.printResults();
    }
}
