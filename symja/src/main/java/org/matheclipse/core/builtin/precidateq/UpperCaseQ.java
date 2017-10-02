package org.matheclipse.core.builtin.precidateq;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.WrongArgumentType;
import org.matheclipse.core.eval.interfaces.AbstractCorePredicateEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.IStringX;
import org.matheclipse.core.interfaces.ISymbol;

import java.util.function.Predicate;

/**
 * Returns <code>True</code>, if the given expression is a string which only
 * contains upper case characters
 */
public class UpperCaseQ extends AbstractCorePredicateEvaluator implements Predicate<IExpr> {

    public UpperCaseQ() {
        super(F.UpperCaseQ);
    }

    @Override
    public boolean evalArg1Boole(final IExpr arg1, EvalEngine engine) {
        if (!(arg1 instanceof IStringX)) {
            throw new WrongArgumentType(null, arg1, 1);
        }
        return test(arg1);
    }

    @Override
    public void setUp(final ISymbol newSymbol) {
        newSymbol.setAttributes(ISymbol.LISTABLE);
    }

    @Override
    public boolean test(final IExpr obj) {
        final String str = obj.toString();
        char ch;
        for (int i = 0; i < str.length(); i++) {
            ch = str.charAt(i);
            if (!(Character.isUpperCase(ch))) {
                return false;
            }
        }
        return true;
    }
}
