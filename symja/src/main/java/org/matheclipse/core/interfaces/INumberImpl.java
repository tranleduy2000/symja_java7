package org.matheclipse.core.interfaces;

/**
 * Created by Duy on 10/3/2017.
 */

public abstract class INumberImpl extends IExprImpl implements INumber {
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

    @Override
    public abstract INumber opposite();

    /**
     * Returns the imaginary part of a complex number
     *
     * @return real part
     */
    @Override
    public abstract ISignedNumber im();

    /**
     * Returns the real part of a complex number
     *
     * @return real part
     */
    @Override
    public abstract ISignedNumber re();

    @Override
    public abstract INumber conjugate();
}
