package expressions;

public abstract class BinExp extends Expression {
    protected Expression left, right;

    public BinExp(Expression leftExp, Expression rightExp) {
        this.left = leftExp;
        this.right = rightExp;
    }
}