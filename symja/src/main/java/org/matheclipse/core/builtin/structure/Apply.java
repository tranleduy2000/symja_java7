package org.matheclipse.core.builtin.structure;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractCoreFunctionEvaluator;
import org.matheclipse.core.eval.util.Options;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISymbol;
import org.matheclipse.core.visit.VisitorLevelSpecification;
import org.matheclipse.parser.client.math.MathException;

/**
 * <p>
 * See the online Symja function reference: <a href=
 * "https://bitbucket.org/axelclk/symja_android_library/wiki/Symbols/Apply">
 * Apply</a>
 * </p>
 */
public class Apply extends AbstractCoreFunctionEvaluator {

    public static IExpr evalApply(IExpr arg1, IExpr arg2, IAST evaledAST, int lastIndex, boolean heads,
                                  EvalEngine engine) {
        VisitorLevelSpecification level = null;
        com.duy.lambda.Function<IExpr, IExpr> af = x -> x.isAST() ? ((IAST) x).setAtCopy(0, arg1) : F.NIL;
        try {
            if (lastIndex == 3) {
                level = new VisitorLevelSpecification(af, evaledAST.get(lastIndex), heads, engine);
            } else {
                level = new VisitorLevelSpecification(af, 0);
            }

            if (!arg2.isAtom()) {
                return arg2.accept(level).orElse(arg2);
            } else if (evaledAST.isAST2()) {
                if (arg1.isFunction()) {
                    return F.unaryAST1(arg1, arg2);
                }
                return arg2;
            }
        } catch (final MathException e) {
            engine.printMessage(e.getMessage());
        } catch (final ArithmeticException e) {

        }
        return F.NIL;
    }

    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        Validate.checkRange(ast, 3, 5);

        IAST evaledAST = ast.clone();
        for (int i = 1; i < evaledAST.size(); i++) {
            evaledAST.set(i, engine.evaluate(evaledAST.get(i)));
        }
        int lastIndex = evaledAST.size() - 1;
        boolean heads = false;
        final Options options = new Options(evaledAST.topHead(), evaledAST, lastIndex, engine);
        IExpr option = options.getOption("Heads");
        if (option.isPresent()) {
            lastIndex--;
            if (option.isTrue()) {
                heads = true;
            }
        } else {
            Validate.checkRange(evaledAST, 3, 4);
        }

        IExpr arg1 = evaledAST.arg1();
        IExpr arg2 = evaledAST.arg2();
        return evalApply(arg1, arg2, evaledAST, lastIndex, heads, engine);
    }

    @Override
    public void setUp(final ISymbol newSymbol) {
        newSymbol.setAttributes(ISymbol.HOLDALL);
    }

}

 