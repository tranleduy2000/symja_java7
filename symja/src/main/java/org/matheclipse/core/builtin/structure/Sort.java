package org.matheclipse.core.builtin.structure;

import org.matheclipse.core.eval.EvalAttributes;
import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractFunctionEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.generic.precidates.IsBinaryFalse;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;

public class Sort extends AbstractFunctionEvaluator {

    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        Validate.checkRange(ast, 2, 3);

        if (ast.arg1().isAST()) {
            final IAST shallowCopy = ((IAST) ast.arg1()).copy();
            if (shallowCopy.size() <= 2) {
                return shallowCopy;
            }
            try {
                if (ast.isAST1()) {
                    EvalAttributes.sort(shallowCopy);
                } else {
                    // use the 2nd argument as a head for the comparator operation:
                    EvalAttributes.sort(shallowCopy, new IsBinaryFalse(ast.arg2()));
                }
                return shallowCopy;
            } catch (Exception ex) {

            }
        }

        return F.NIL;
    }
}


 