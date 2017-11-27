package org.matheclipse.core.reflection.system;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractArg2;
import org.matheclipse.core.eval.interfaces.INumeric;
import org.matheclipse.core.expression.ApfloatNum;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.IInteger;
import org.matheclipse.core.interfaces.INum;
import org.matheclipse.core.interfaces.ISymbol;

import static org.matheclipse.core.expression.F.Power;

public class Surd extends AbstractArg2 implements INumeric {
    @Override
    public IExpr e2ApfloatArg(final ApfloatNum af0, final ApfloatNum af1) {
        if (af1.isZero()) {
            EvalEngine ee = EvalEngine.get();
            ee.printMessage("Surd(a,b) division by zero");
            return F.Indeterminate;
        }
        if (af0.isNegative()) {
            return af0.abs().pow(af1.inverse()).negate();
        }
        return af0.pow(af1.inverse());
    }

    @Override
    public IExpr e2DblArg(INum d0, INum d1) {
        double val = d0.doubleValue();
        double r = d1.doubleValue();
        double result = doubleSurd(val, r);
        if (result == Double.NaN) {
            return F.Indeterminate;
        }
        return F.num(result);
    }

    @Override
    public IExpr e2ObjArg(final IExpr o, final IExpr r) {
        if (r.isInteger()) {
            EvalEngine ee = EvalEngine.get();
            if (o.isNegative() && ((IInteger) r).isEven()) {
                ee.printMessage("Surd(a,b) is undefined for negative \"a\" and even \"b\"");
                return F.Indeterminate;
            }
            if (r.isZero()) {
                ee.printMessage("Surd(a,b) division by zero");
                return F.Indeterminate;
            }
            if (o.isMinusOne()) {
                return F.CN1;
            }
            return Power(o, ((IInteger) r).inverse());
        }
        return F.NIL;
    }

    @Override
    public void setUp(final ISymbol newSymbol) {
        newSymbol.setAttributes(ISymbol.LISTABLE | ISymbol.NHOLDREST | ISymbol.NUMERICFUNCTION);
        super.setUp(newSymbol);
    }

    @Override
    public double evalReal(double[] stack, int top, int size) {
        if (size != 2) {
            throw new UnsupportedOperationException();
        }
        return doubleSurd(stack[top - 1], stack[top]);
    }

    private double doubleSurd(double val, double r) {
        if (r == 0.0d) {
            EvalEngine ee = EvalEngine.get();
            ee.printMessage("Surd(a,b) division by zero");
            return Double.NaN;
        }
        if (val < 0.0d) {
            return -Math.pow(Math.abs(val), 1.0d / r);
        }
        return Math.pow(val, 1.0d / r);
    }

    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        Validate.checkSize(ast, 3);
        IExpr arg1 = ast.arg1();
        IExpr arg2 = engine.evaluateNonNumeric(ast.arg2());
        if (arg2.isNumber()) {
            if (arg2.isInteger()) {
                IInteger iArg2 = (IInteger) arg2;
                if (arg1.isNegative()) {
                    if (arg1.isNegative() && iArg2.isEven()) {
                        engine.printMessage("Surd(a,b) - undefined for negative \"a\" and even \"b\" values");
                        return F.Indeterminate;
                    }
                    // return F.Power(arg1, F.QQ(F.C1, iArg2));
                }
            } else {
                engine.printMessage("Surd(a,b) - b should be an integer");
                return F.NIL;
            }
        }
        return binaryOperator(ast.arg1(), ast.arg2());
    }
}