package org.matheclipse.core.builtin.precidateq;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractCoreFunctionEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;

public class RealNumberQ extends AbstractCoreFunctionEvaluator {
    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        if (ast.isAST1()) {
            IExpr arg1 = ast.arg1();
            if (arg1.isNumber()) {
                return F.bool(arg1.isRealNumber());
            }
            IExpr temp = engine.evaluate(arg1);
            if (temp.isSignedNumber()) {
                return F.True;
            }
            if (temp.isNumericFunction()) {
                temp = engine.evalN(arg1);
                if (temp.isSignedNumber()) {
                    return F.True;
                }
            }
            return F.False;
        }
        Validate.checkSize(ast, 2);
        return F.NIL;
    }
}
