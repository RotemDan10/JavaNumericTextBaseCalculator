package expressions;

public class Mul extends BinExp {
    public Mul(Expression leftExp, Expression rightExp) {
        super(leftExp, rightExp);
    }

    @Override
    public double calc() {
        return this.left.calc() * this.right.calc();
    }
}
