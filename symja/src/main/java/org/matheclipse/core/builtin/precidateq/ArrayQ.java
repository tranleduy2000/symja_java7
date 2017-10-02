package org.matheclipse.core.builtin.precidateq;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractCoreFunctionEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.patternmatching.IPatternMatcher;

import java.util.function.Predicate;

/**
 * ArrayQ tests whether an expression is a full array.
 * <p>
 * See the online Symja function reference: <a href=
 * "https://bitbucket.org/axelclk/symja_android_library/wiki/Symbols/ArrayQ">
 * ArrayQ</a>
 * </p>
 */
public class ArrayQ extends AbstractCoreFunctionEvaluator {

    /**
     * Determine the depth of the given expression <code>expr</code> which should be
     * a full array of (possibly nested) lists. Return <code>-1</code> if the
     * expression isn't a full array.
     *
     * @param expr
     * @param depth     start depth of the full array
     * @param predicate an optional <code>Predicate</code> which would be applied to all
     *                  elements which aren't lists.
     * @return <code>-1</code> if the expression isn't a full array.
     */
    private static int determineDepth(final IExpr expr, int depth, Predicate<IExpr> predicate) {
        int resultDepth = depth;
        if (expr.isList()) {
            IAST ast = (IAST) expr;
            int size = ast.size();
            IExpr arg1AST = ast.arg1();
            boolean isList = arg1AST.isList();
            int arg1Size = 0;
            if (isList) {
                arg1Size = ((IAST) ast.arg1()).size();
            }
            resultDepth = determineDepth(arg1AST, depth + 1, predicate);
            if (resultDepth < 0) {
                return -1;
            }
            int tempDepth;
            for (int i = 2; i < size; i++) {
                if (isList) {
                    if (!ast.get(i).isList()) {
                        return -1;
                    }
                    if (arg1Size != ((IAST) ast.get(i)).size()) {
                        return -1;
                    }
                    tempDepth = determineDepth(ast.get(i), depth + 1, predicate);
                    if (tempDepth < 0 || tempDepth != resultDepth) {
                        return -1;
                    }
                } else {
                    if (ast.get(i).isList()) {
                        return -1;
                    }
                    if (predicate != null) {
                        if (!predicate.test(ast.get(i))) {
                            return -1;
                        }
                    }
                }
            }
            return resultDepth;
        }
        if (predicate != null) {
            if (!predicate.test(expr)) {
                return -1;
            }
        }
        return resultDepth;
    }

    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        Validate.checkRange(ast, 2, 4);

        final IExpr arg1 = engine.evaluate(ast.arg1());
        Predicate<IExpr> pred = null;
        if ((ast.size() >= 4)) {
            final IExpr arg3 = engine.evaluate(ast.arg3());
            pred = x -> engine.evalTrue(F.unaryAST1(arg3, x));
        }
        int depth = determineDepth(arg1, 0, pred);
        if (depth >= 0) {
            if ((ast.size() >= 3)) {
                // Match the depth with the second argumnt
                final IPatternMatcher matcher = engine.evalPatternMatcher(ast.arg2());
                if (!matcher.test(F.ZZ(depth), engine)) {
                    return F.False;
                }
            }
            return F.True;
        }
        return F.False;

    }

}