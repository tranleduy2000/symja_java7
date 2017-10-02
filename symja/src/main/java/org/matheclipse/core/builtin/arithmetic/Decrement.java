package org.matheclipse.core.builtin.arithmetic;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractFunctionEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISymbol;

public class Decrement extends AbstractFunctionEvaluator {

    public Decrement() {
        super();
    }

    protected IAST getAST() {
        return F.Plus(null, F.CN1);
    }

    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        Validate.checkSize(ast, 2);
        IExpr symbol = ast.arg1();
        if (symbol.isSymbol() && ((ISymbol) symbol).hasAssignedSymbolValue()) {
            IExpr[] results = ((ISymbol) symbol).reassignSymbolValue(getAST(), getFunctionSymbol(), engine);
            if (results != null) {
                return getResult(results[0], results[1]);
            }
        }
        return F.NIL;
    }

    protected ISymbol getFunctionSymbol() {
        return F.Decrement;
    }

    protected IExpr getResult(IExpr symbolValue, IExpr calculatedResult) {
        return symbolValue;
    }

    @Override
    public void setUp(final ISymbol newSymbol) {
        newSymbol.setAttributes(ISymbol.HOLDALL);
    }
}


