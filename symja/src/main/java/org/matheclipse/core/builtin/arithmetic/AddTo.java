package org.matheclipse.core.builtin.arithmetic;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractFunctionEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISymbol;

/**
 * Operator +=
 */
public class AddTo extends AbstractFunctionEvaluator {

    protected IAST getAST(final IExpr value) {
        return F.Plus(null, value);
    }

    protected ISymbol getFunctionSymbol() {
        return F.AddTo;
    }

    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        Validate.checkSize(ast, 3);
        ISymbol sym = Validate.checkSymbolType(ast, 1);
        IExpr arg2 = engine.evaluate(ast.arg2());
        IExpr[] results = sym.reassignSymbolValue(getAST(arg2), getFunctionSymbol(), engine);
        if (results != null) {
            return results[1];
        }
        return F.NIL;
    }

    @Override
    public void setUp(final ISymbol newSymbol) {
        newSymbol.setAttributes(ISymbol.HOLDALL);
    }
}