package org.matheclipse.core.builtin.algebra;

import org.matheclipse.core.basic.Config;
import org.matheclipse.core.convert.VariablesSet;
import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.JASConversionException;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.exception.WrongArgumentType;
import org.matheclipse.core.expression.ASTRange;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;

import java.util.List;

/**
 * <h2>FactorSquareFreeList</h2>
 * <p>
 * <pre>
 * <code>FactorSquareFreeList(polynomial)
 * </code>
 * </pre>
 * <p>
 * <blockquote>
 * <p>
 * get the square free factors of the polynomial expression
 * <code>polynomial</code>.
 * </p>
 * </blockquote>
 */
public class FactorSquareFreeList extends Factor {

    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        Validate.checkSize(ast, 2);

        VariablesSet eVar = new VariablesSet(ast.arg1());
        if (!eVar.isSize(1)) {
            throw new WrongArgumentType(ast, ast.arg1(), 1,
                    "Factorization only implemented for univariate polynomials");
        }
        try {
            IExpr expr = F.evalExpandAll(ast.arg1(), engine);
            ASTRange r = new ASTRange(eVar.getVarList(), 1);
            List<IExpr> varList = r;

            // if (ast.isAST2()) {
            // return factorWithOption(ast, expr, varList, true);
            // }
            return factorList(expr, varList, true);

        } catch (JASConversionException jce) {
            // toInt() conversion failed
            if (Config.DEBUG) {
                jce.printStackTrace();
            }
        }
        return F.NIL;
    }

}
