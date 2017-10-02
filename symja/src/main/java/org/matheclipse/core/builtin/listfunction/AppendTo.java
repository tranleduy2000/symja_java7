package org.matheclipse.core.builtin.listfunction;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractCoreFunctionEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISymbol;

import java.util.function.Function;

/**
 * <p>
 * See the online Symja function reference: <a href=
 * "https://bitbucket.org/axelclk/symja_android_library/wiki/Symbols/AppendTo">AppendTo</a>
 * </p>
 */
public final class AppendTo extends AbstractCoreFunctionEvaluator {

    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        Validate.checkSize(ast, 3);
        ISymbol sym = Validate.checkSymbolType(ast, 1, engine);
        if (sym == null) {
            return F.NIL;
        }
        IExpr arg2 = engine.evaluate(ast.arg2());
        Function<IExpr, IExpr> function = new AppendToFunction(arg2);
        IExpr[] results = sym.reassignSymbolValue(function, F.AppendTo, engine);
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