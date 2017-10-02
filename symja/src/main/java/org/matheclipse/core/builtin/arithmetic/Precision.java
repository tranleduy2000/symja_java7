package org.matheclipse.core.builtin.arithmetic;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractCoreFunctionEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IComplexNum;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.INum;

public class Precision extends AbstractCoreFunctionEvaluator {

    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        if (ast.size() == 2) {
            IExpr arg1 = engine.evaluate(ast.arg1());
            if (arg1 instanceof INum) {
                return F.integer(((INum) arg1).precision());
            }
            if (arg1 instanceof IComplexNum) {
                return F.integer(((IComplexNum) arg1).precision());
            }
            engine.printMessage("Precision: Numeric expression expected");
            return F.NIL;
        }
        return Validate.checkSize(ast, 2);
    }
}

 
 