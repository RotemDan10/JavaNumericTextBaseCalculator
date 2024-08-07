package expressions;

public class Div extends BinExp {
    public Div(Expression leftExp, Expression rightExp) {
        super(leftExp, rightExp);
    }

    @Override
    public double calc() {
        return this.left.calc() / this.right.calc();
    }
}
