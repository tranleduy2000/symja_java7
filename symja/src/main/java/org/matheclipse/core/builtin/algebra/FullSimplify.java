package org.matheclipse.core.builtin.algebra;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;

/**
 * Try to simplify a given expression
 * <p>
 * TODO currently FullSimplify simply calls Simplify
 */
public class FullSimplify extends Simplify {

    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        Validate.checkSize(ast, 2);
        return super.evaluate(ast, engine);
    }

}


    
