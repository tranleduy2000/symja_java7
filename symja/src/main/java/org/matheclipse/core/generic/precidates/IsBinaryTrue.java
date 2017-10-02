package org.matheclipse.core.generic.precidates;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;

import java.util.Comparator;
import java.util.function.BiPredicate;

/**
 * Check if the evaluation of a binary AST object gives <code>True</code>
 */
public class IsBinaryTrue implements BiPredicate<IExpr, IExpr>, Comparator<IExpr> {
    protected final EvalEngine fEngine;

    protected final IAST fAST;

    /**
     * Define a binary AST with the header <code>head</code>.
     *
     * @param head the AST's head expression
     */
    public IsBinaryTrue(final IExpr head) {
        this(head, EvalEngine.get());
    }

    public IsBinaryTrue(final IExpr head, EvalEngine engine) {
        fEngine = engine;
        fAST = F.binaryAST2(head, null, null);
    }

    @Override
    public int compare(final IExpr firstArg, final IExpr secondArg) {
        fAST.set(1, firstArg);
        fAST.set(2, secondArg);
        IExpr temp = fEngine.evaluate(fAST);
        if (temp.isTrue()) {
            return 1;
        }
        if (temp.isFalse()) {
            return -1;
        }
        return 0;
    }

    /**
     * Check if the evaluation of a binary AST object gives
     * <code>True</code> by settings it's first argument to
     * <code>firstArg</code> and settings it's second argument to
     * <code>secondArg</code>
     */
    @Override
    public boolean test(final IExpr firstArg, final IExpr secondArg) {
        fAST.set(1, firstArg);
        fAST.set(2, secondArg);
        if (fEngine.evaluate(fAST).isTrue()) {
            return true;
        }
        return false;
    }

}