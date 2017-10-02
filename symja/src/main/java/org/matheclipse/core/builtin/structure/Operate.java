package org.matheclipse.core.builtin.structure;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractFunctionEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.IInteger;

public class Operate extends AbstractFunctionEvaluator {

    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        Validate.checkRange(ast, 3, 4);

        int headDepth = 1;
        if (ast.isAST3()) {
            if (!ast.arg3().isInteger()) {
                return F.NIL;
            }
            IInteger depth = (IInteger) ast.arg3();
            if (depth.isNegative()) {
                engine.printMessage("Non-negative integer expected at position 3 in Operate()");
                return F.NIL;
            }

            headDepth = depth.toIntDefault(Integer.MIN_VALUE);
            if (headDepth == Integer.MIN_VALUE) {
                return F.NIL;
            }

        }

        IExpr p = ast.arg1();
        IExpr arg2 = ast.arg2();
        if (headDepth == 0) {
            // act like Apply()
            return F.unaryAST1(p, arg2);
        }

        if (!arg2.isAST()) {
            return arg2;
        }

        IExpr expr = arg2;
        for (int i = 1; i < headDepth; i++) {
            expr = expr.head();
            if (!expr.isAST()) {
                // headDepth is higher than the depth of heads in arg2
                // return arg2 unmodified.
                return arg2;
            }
        }

        IAST result = ((IAST) arg2).clone();
        IAST last = result;
        IAST head = result;

        for (int i = 1; i < headDepth; i++) {
            head = ((IAST) head.head()).clone();
            last.set(0, head);
            last = head;
        }

        head.set(0, F.unaryAST1(p, head.head()));
        return result;
    }
}
