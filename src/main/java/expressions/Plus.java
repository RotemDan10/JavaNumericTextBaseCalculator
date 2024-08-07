package expressions;

public class Plus extends BinExp {
    public Plus(Expression leftExp, Expression rightExp) {
        super(leftExp, rightExp);
    }

    @Override
    public double calc() {
        return this.left.calc() + this.right.calc();
    }
}