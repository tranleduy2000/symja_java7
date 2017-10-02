package org.matheclipse.core.builtin.stringfunction;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.exception.WrongArgumentType;
import org.matheclipse.core.eval.interfaces.AbstractFunctionEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.IStringX;
import org.matheclipse.core.interfaces.ISymbol;

import java.util.function.Predicate;

/**
 * Returns <code>True</code>, if the given expression is a string which only
 * contains lower case characters
 */
public class LowerCaseQ extends AbstractFunctionEvaluator implements Predicate<IExpr> {

    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        Validate.checkSize(ast, 2);
        if (!(ast.arg1() instanceof IStringX)) {
            throw new WrongArgumentType(ast, ast.arg1(), 1);
        }

        return F.bool(test(ast.arg1()));
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
            if (!(Character.isLowerCase(ch))) {
                return false;
            }
        }
        return true;
    }
}
