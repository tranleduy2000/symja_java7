package org.matheclipse.core.interfaces.defaultmethod;

import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.INumber;
import org.matheclipse.core.interfaces.ISignedNumber;


/**
 * Created by Duy on 10/3/2017.
 */

public abstract class ISignedNumberImpl extends INumberImpl implements ISignedNumber {
    @Override
    public INumber conjugate() {
        return this;
    }

    @Override
    public IExpr complexArg() {
        if (sign() < 0) {
            return F.Pi;
        }
        return F.C0;
    }

    @Override
    public IExpr lower() {
        return this;
    }

    /**
     * If this is a <code>Interval[{lower, upper}]</code> expression return the
     * <code>upper</code> value. If this is a <code>ISignedNUmber</code>
     * expression return <code>this</code>.
     *
     * @return <code>F.NIL</code> if this expression is no interval and no
     * signed number.
     */
    @Override
    public IExpr upper() {
        return this;
    }

    @Override
    public abstract ISignedNumber abs();


    @Override
    public abstract ISignedNumber inverse();

    /**
     * Returns (-1) * this
     *
     * @return
     */
    @Override
    public abstract ISignedNumber negate();

    @Override
    public abstract ISignedNumber opposite();
}
