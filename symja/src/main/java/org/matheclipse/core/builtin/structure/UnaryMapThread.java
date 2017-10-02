package org.matheclipse.core.builtin.structure;

import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;

public class UnaryMapThread implements java.util.function.Function<IExpr, IExpr> {
    final IExpr fConstant;

    public UnaryMapThread(final IExpr constant) {
        fConstant = constant;
    }

    @Override
    public IExpr apply(final IExpr firstArg) {
        if (firstArg.isAST()) {
            return Thread.threadList((IAST) firstArg, F.List, fConstant).orElse(firstArg);
        }
        return firstArg;
    }

}