package org.matheclipse.core.builtin.listfunction; import java.util.function.Consumer; import java.util.function.Function; import java.util.function.Predicate;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractCoreFunctionEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISymbol;

public final class PrependTo extends AbstractCoreFunctionEvaluator {

    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        Validate.checkSize(ast, 3);
        ISymbol sym = Validate.checkSymbolType(ast, 1, engine);
        if (sym == null) {
            return F.NIL;
        }
        IExpr arg2 = engine.evaluate(ast.arg2());
        Function<IExpr, IExpr> function = new PrependToFunction(arg2);
        IExpr[] results = sym.reassignSymbolValue(function, F.PrependTo, engine);
        if (results != null) {
            return results[1];
        }

        return F.NIL;
    }

    @Override
    public void setUp(final ISymbol newSymbol) {
        newSymbol.setAttributes(ISymbol.HOLDFIRST);
    }


}