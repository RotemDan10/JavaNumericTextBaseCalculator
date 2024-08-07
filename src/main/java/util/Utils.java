package util;

public class Utils {

    public boolean isOutOfActionOperator(String operator){
        return operator.equals("++") || operator.equals("--");
    }

    public boolean isDouble(String num) {
        try {
            Double.parseDouble(num);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isString(String token) {
        return token.matches("[a-zA-Z0-9]+");
    }
}
