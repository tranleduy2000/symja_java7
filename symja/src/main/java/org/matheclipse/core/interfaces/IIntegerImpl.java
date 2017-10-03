package org.matheclipse.core.interfaces;

import java.math.BigInteger;

/**
 * Created by Duy on 10/3/2017.
 */

public abstract class IIntegerImpl extends IRationalImpl implements IInteger {
    /**
     * {@inheritDoc}
     */
    @Override
    public abstract IInteger abs();

    /**
     * Multiply this integer with value
     *
     * @param value
     * @return
     */
    @Override
    public abstract IInteger multiply(int value);

    /**
     * Returns an <code>IInteger</code> whose value is <code>(-1) * this</code>.
     *
     * @return
     */
    @Override
    public abstract IInteger negate();

    @Override
    public abstract IInteger pow(final long exp) throws ArithmeticException;

    /**
     * Returns the numerator of this Rational.
     *
     * @return numerator
     */
    @Override
    public abstract BigInteger toBigNumerator();
}
