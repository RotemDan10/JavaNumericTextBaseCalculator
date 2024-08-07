package expressions;

public class Minus extends BinExp {
    public Minus(Expression leftExp, Expression rightExp) {
        super(leftExp, rightExp);
    }

    @Override
    public double calc() {
        return this.left.calc() - this.right.calc();
    }
}
