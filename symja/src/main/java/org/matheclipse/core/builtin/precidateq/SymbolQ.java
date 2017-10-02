package org.matheclipse.core.builtin.precidateq;


import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractCoreFunctionEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;

public class SymbolQ extends AbstractCoreFunctionEvaluator implements Predicate<IExpr> {
    /**
     * Returns <code>True</code> if the 1st argument is a symbol; <code>False</code>
     * otherwise
     */
    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        Validate.checkSize(ast, 2);
        IExpr arg1 = engine.evaluate(ast.arg1());
        return F.bool(arg1.isSymbol());
    }

    @Override
    public boolean test(final IExpr expr) {
        return expr.isSymbol();
    }
}