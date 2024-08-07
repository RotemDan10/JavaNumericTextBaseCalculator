package expressions;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class ExpressionsFactory {

    private Map<String, Class<? extends BinExp>> expressions;

    public ExpressionsFactory() {
        this.expressions = Map.of(
                "+", Plus.class,
                "-", Minus.class,
                "*", Mul.class,
                "/", Div.class
        );
    }

    public BinExp getExpression(String operator, Expression right, Expression left) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<? extends BinExp> constructor = this.expressions.get(operator).getConstructor(Expression.class, Expression.class);
        return constructor.newInstance(left,right);
    }
}
