package org.matheclipse.core.interfaces.impl;

import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.INumber;
import org.matheclipse.core.interfaces.ISignedNumber;

/**
 * Created by Duy on 10/2/2017.
 */

public abstract class SignedNumberImpl extends NumberImpl implements ISignedNumber {


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

    /**
     * If this is a <code>Interval[{lower, upper}]</code> expression return the
     * <code>lower</code> value. If this is a <code>ISignedNUmber</code>
     * expression return <code>this</code>.
     *
     * @return <code>F.NIL</code> if this expression is no interval and no
     * signed number.
     */
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
    public ISignedNumber abs() {
        return null;
    }


    @Override
    public ISignedNumber inverse() {
        return null;
    }

    @Override
    public ISignedNumber negate() {
        return null;
    }

    @Override
    public ISignedNumber opposite() {
        return null;
    }
}
