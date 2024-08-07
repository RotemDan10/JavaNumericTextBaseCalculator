package expressions;

public class Num extends Expression {
    private double x;

    public Num(double num) {
        this.x = num;
    }

    @Override
    public double calc() {
        return this.x;
    }
}