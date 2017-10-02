package org.matheclipse.core.builtin.arithmetic;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractTrigArg1;
import org.matheclipse.core.eval.interfaces.INumeric;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.INumber;
import org.matheclipse.core.interfaces.ISymbol;
import org.matheclipse.core.reflection.system.rules.ConjugateRules;

/**
 * Conjugate the given argument.
 * <p>
 * See
 * <a href="http://en.wikipedia.org/wiki/Complex_conjugation">Wikipedia:Complex
 * conjugation</a>
 */
public class Conjugate extends AbstractTrigArg1 implements INumeric, ConjugateRules {

    /**
     * Conjugate numbers and special objects like <code>Infinity</code> and
     * <code>Indeterminate</code>.
     *
     * @param arg1
     * @return <code>F.NIL</code> if the evaluation wasn't possible
     */
    private IExpr conjugate(IExpr arg1) {
        if (arg1.isNumber()) {
            return ((INumber) arg1).conjugate();
        }
        if (arg1.isRealResult()) {
            return arg1;
        }
        if (arg1.isDirectedInfinity()) {
            IAST directedInfininty = (IAST) arg1;
            if (directedInfininty.isComplexInfinity()) {
                return F.CComplexInfinity;
            }
            if (directedInfininty.isAST1()) {
                if (directedInfininty.isInfinity()) {
                    return F.CInfinity;
                }
                IExpr conjug = F.eval(F.Conjugate(directedInfininty.arg1()));
                return F.Times(conjug, F.CInfinity);
            }
        }
        return F.NIL;
    }

    @Override
    public double evalReal(final double[] stack, final int top, final int size) {
        if (size != 1) {
            throw new UnsupportedOperationException();
        }
        return stack[top];
    }

    @Override
    public IExpr numericEval(final IAST ast, EvalEngine engine) {
        Validate.checkSize(ast, 2);
        IExpr arg1 = ast.arg1();
        return evaluateArg1(arg1);
    }

    @Override
    public IExpr evaluateArg1(final IExpr arg1) {
        IExpr temp = conjugate(arg1);
        if (temp.isPresent()) {
            return temp;
        }
        if (arg1.isPower()) {
            IExpr base = ((IAST) arg1).arg1();
            if (base.isPositiveResult()) {
                return F.Power(base, F.Conjugate(((IAST) arg1).arg2()));
            }
        }
        if (arg1.isPlus()) {
            return ((IAST) arg1).mapThread(F.Conjugate(F.Null), 1);
        }
        if (arg1.isTimes()) {
            IAST result = F.NIL;
            IAST clone = ((IAST) arg1).clone();
            int i = 1;
            while (i < clone.size()) {
                temp = conjugate(clone.get(i));
                if (temp.isPresent()) {
                    clone.remove(i);
                    if (!result.isPresent()) {
                        result = ((IAST) arg1).copyHead();
                    }
                    result.append(temp);
                    continue;
                }
                i++;
            }
            if (result.isPresent()) {
                if (clone.isAST0()) {
                    return result;
                }
                if (clone.isAST0()) {
                    result.append(F.Conjugate(clone.arg1()));
                    return result;
                }
                result.append(F.Conjugate(clone));
                return result;
            }
        } else if (arg1.isConjugate()) {
            return arg1.getAt(1);
        } else if (arg1.isAST(F.Zeta, 2)) {
            return F.Zeta(F.Conjugate(arg1.getAt(1)));
        } else if (arg1.isAST(F.Zeta, 3) && arg1.getAt(1).isSignedNumber() && arg1.getAt(2).isSignedNumber()) {
            return F.Zeta(F.Conjugate(arg1.getAt(1)), F.Conjugate(arg1.getAt(2)));
        }
        return F.NIL;
    }

    @Override
    public IAST getRuleAST() {
        return RULES;
    }

    @Override
    public void setUp(final ISymbol newSymbol) {
        newSymbol.setAttributes(ISymbol.LISTABLE | ISymbol.NUMERICFUNCTION);
        super.setUp(newSymbol);
    }

}