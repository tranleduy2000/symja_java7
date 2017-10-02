package org.matheclipse.core.builtin.algebra;

import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.visit.VisitorExpr;

import static org.matheclipse.core.builtin.algebra.PowerExpand.powerExpand;
import static org.matheclipse.core.expression.F.Arg;
import static org.matheclipse.core.expression.F.C1D2;
import static org.matheclipse.core.expression.F.C2;
import static org.matheclipse.core.expression.F.Divide;
import static org.matheclipse.core.expression.F.E;
import static org.matheclipse.core.expression.F.Floor;
import static org.matheclipse.core.expression.F.I;
import static org.matheclipse.core.expression.F.Im;
import static org.matheclipse.core.expression.F.Log;
import static org.matheclipse.core.expression.F.Negate;
import static org.matheclipse.core.expression.F.Null;
import static org.matheclipse.core.expression.F.Pi;
import static org.matheclipse.core.expression.F.Plus;
import static org.matheclipse.core.expression.F.Power;
import static org.matheclipse.core.expression.F.Subtract;
import static org.matheclipse.core.expression.F.Times;

public class PowerExpandVisitor extends VisitorExpr {
    final boolean assumptions;

    public PowerExpandVisitor(boolean assumptions) {
        super();
        this.assumptions = assumptions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IExpr visit2(IExpr head, IExpr arg1) {
        boolean evaled = false;
        IExpr x1 = arg1;
        IExpr result = arg1.accept(this);
        if (result.isPresent()) {
            evaled = true;
            x1 = result;
        }
        if (head.equals(Log)) {
            if (x1.isPower()) {
                IAST powerAST = (IAST) x1;
                // Log[x_ ^ y_] :> y * Log(x)
                IAST logResult = Times(powerAST.arg2(), powerExpand(Log(powerAST.arg1()), assumptions));
                if (assumptions) {
                    IAST floorResult = Floor(Divide(Subtract(Pi, Im(logResult)), Times(C2, Pi)));
                    IAST timesResult = Times(C2, I, Pi, floorResult);
                    return Plus(logResult, timesResult);
                }
                return logResult;
            }
            if (x1.isTimes()) {
                IAST timesAST = (IAST) x1;
                // Log[x_ * y_ * z_] :> Log(x)+Log(y)+Log(z)
                IAST logResult = timesAST.setAtClone(0, Plus);
                logResult = logResult.mapThread(Log(F.Null), 1);
                return powerExpand(logResult, assumptions);
            }
        }
        if (evaled) {
            return F.unaryAST1(head, x1);
        }
        return F.NIL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IExpr visit3(IExpr head, IExpr arg1, IExpr arg2) {
        boolean evaled = false;
        IExpr x1 = arg1;
        IExpr result = arg1.accept(this);
        if (result.isPresent()) {
            evaled = true;
            x1 = result;
        }
        IExpr x2 = arg2;
        result = arg2.accept(this);
        if (result.isPresent()) {
            evaled = true;
            x2 = result;
        }
        if (head.equals(Power)) {
            if (x1.isTimes()) {
                // Power[x_ * y_, z_] :> x^z * y^z
                IAST timesAST = (IAST) x1;
                IAST timesResult = timesAST.mapThread(Power(Null, x2), 1);
                if (assumptions) {
                    IAST plusResult = Plus(C1D2);
                    for (int i = 1; i < timesAST.size(); i++) {
                        plusResult.append(Negate(Divide(Arg(timesAST.get(i)), Times(C2, Pi))));
                    }
                    IAST expResult = Power(E, Times(C2, I, Pi, x2, Floor(plusResult)));
                    timesResult.append(expResult);
                    return timesResult;
                }
                return timesResult;
            }
            if (x1.isPower()) {
                // Power[x_ ^ y_, z_] :> x ^(y*z)
                IAST powerAST = (IAST) x1;
                IExpr base = powerAST.arg1();
                IExpr exponent = powerAST.arg2();
                IAST powerResult = Power(base, Times(exponent, x2));
                if (assumptions) {
                    IAST floorResult = Floor(
                            Divide(Subtract(Pi, Im(Times(exponent, Log(base)))), Times(C2, Pi)));
                    IAST expResult = Power(E, Times(C2, I, Pi, x2, floorResult));
                    IAST timesResult = Times(powerResult, expResult);
                    return timesResult;
                }
                return powerResult;
            }
        }
        if (evaled) {
            return F.binaryAST2(head, x1, x2);
        }
        return F.NIL;
    }
}
