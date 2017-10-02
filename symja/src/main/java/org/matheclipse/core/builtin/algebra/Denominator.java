package org.matheclipse.core.builtin.algebra;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.exception.WrongArgumentType;
import org.matheclipse.core.eval.interfaces.AbstractEvaluator;
import org.matheclipse.core.eval.util.Options;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.IRational;
import org.matheclipse.core.interfaces.ISymbol;

import static org.matheclipse.core.builtin.algebra.Algebra.fractionalParts;

/**
 * <h2>Denominator</h2>
 * <p>
 * <pre>
 * <code>Denominator(expr)
 * </code>
 * </pre>
 * <p>
 * <blockquote>
 * <p>
 * gives the denominator in <code>expr</code>.
 * </p>
 * </blockquote>
 * <h3>Examples</h3>
 * <p>
 * <pre>
 * <code>&gt;&gt; Denominator(a / b)
 * b
 * &gt;&gt; Denominator(2 / 3)
 * 3
 * &gt;&gt; Denominator(a + b)
 * 1
 * </code>
 * </pre>
 */
public class Denominator extends AbstractEvaluator {

    /**
     * Get the &quot;denominator form&quot; of the given function. Example:
     * <code>Csc[x]</code> gives <code>Sin[x]</code>.
     *
     * @param function the function which should be transformed to &quot;denominator
     *                 form&quot; determine the denominator by splitting up functions
     *                 like <code>Tan[],Cot[], Csc[],...</code>
     * @param trig
     * @return <code>F.NIL</code> if <code>trig</code> is false or no form is found;
     * may return <code>1</code> if no denominator form is available
     * (Example Cos[]).
     */
    public static IExpr getTrigForm(IAST function, boolean trig) {
        if (trig) {
            if (function.isAST1()) {
                for (int i = 0; i < F.DENOMINATOR_NUMERATOR_SYMBOLS.length; i++) {
                    ISymbol sym = F.DENOMINATOR_NUMERATOR_SYMBOLS[i];
                    if (function.head().equals(sym)) {
                        IExpr result = F.DENOMINATOR_TRIG_TRUE_EXPRS[i];
                        if (result.isSymbol()) {
                            return F.unaryAST1(result, function.arg1());
                        }
                        return result;
                    }
                }
            }
        }
        return F.NIL;
    }

    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        Validate.checkRange(ast, 2, 3);

        boolean trig = false;
        if (ast.isAST2()) {
            final Options options = new Options(ast.topHead(), ast, 2, engine);
            IExpr option = options.getOption("Trig");

            if (option.isTrue()) {
                trig = true;
            } else if (!option.isPresent()) {
                throw new WrongArgumentType(ast, ast.get(2), 2, "Option expected!");
            }
        }

        IExpr expr = ast.arg1();
        if (expr.isRational()) {
            return ((IRational) expr).getDenominator();
        }
        IExpr[] parts = fractionalParts(expr, trig);
        if (parts == null) {
            return F.C1;
        }
        return parts[1];
    }

    @Override
    public void setUp(final ISymbol newSymbol) {
        newSymbol.setAttributes(ISymbol.LISTABLE);
    }

}
