package org.matheclipse.core.reflection.system;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractFunctionEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.reflection.system.function.ShareFunction;
import org.matheclipse.core.reflection.system.function.ShareReplaceAll;
import org.matheclipse.core.visit.AbstractVisitor;

/**
 * Try to share common sub-<code>IASTs</code> expressions with the same
 * object-id internally to minimize memory consumption. Returns the number f
 * shared sub-expressions
 */
public class Share extends AbstractFunctionEvaluator {


    public Share() {
    }

    /**
     * Try to share common sub-<code>IASTs</code> expressions with the same
     * object-id internally to minimize memory consumption and return the number
     * of shared sub-expressions
     *
     * @param ast the ast whose internal memory consumption should be minimized
     * @return the number of shared sub-expressions
     */
    public static int shareAST(final IAST ast) {
        ShareReplaceAll sra = (ShareReplaceAll) createVisitor();
        ast.accept(sra);
        return sra.fCounter;
    }

    public static AbstractVisitor createVisitor() {
        return new ShareReplaceAll(new ShareFunction(), 1);
    }

    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        Validate.checkSize(ast, 2);

        if (ast.arg1().isAST()) {
            return F.integer(shareAST((IAST) ast.arg1()));
        }
        return F.C0;
    }

}
