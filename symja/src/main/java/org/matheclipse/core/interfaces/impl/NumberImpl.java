package org.matheclipse.core.interfaces.impl;

import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.INumber;
import org.matheclipse.core.interfaces.ISignedNumber;

/**
 * Created by Duy on 10/2/2017.
 */

public abstract class NumberImpl extends ExprImpl implements INumber {

    @Override
    public ISignedNumber re() {
        return (ISignedNumber) super.re();
    }

    @Override
    public INumber opposite() {
        return (INumber) super.opposite();
    }

    @Override
    public ISignedNumber im() {
        return (ISignedNumber) super.im();
    }

    @Override
    public INumber conjugate() {
        return (INumber) super.conjugate();
    }

    /**
     * Get the absolute value for a given number
     *
     * @return
     * @deprecated use abs()
     */
    @Deprecated
    public IExpr eabs() {
        return abs();
    }
}
