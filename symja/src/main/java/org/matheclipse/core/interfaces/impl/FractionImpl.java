package org.matheclipse.core.interfaces.impl;

import org.matheclipse.core.interfaces.IFraction;
import org.matheclipse.core.interfaces.INumber;

import java.math.BigInteger;

/**
 * Created by Duy on 10/2/2017.
 */

public abstract class FractionImpl extends RationalImpl implements IFraction {
    @Override
    public IFraction pow(long exp) throws ArithmeticException {
        return null;
    }

    @Override
    public BigInteger toBigNumerator() {
        return null;
    }

    @Override
    public BigInteger toBigDenominator() {
        return null;
    }

    @Override
    public INumber normalize() {
        return null;
    }

    @Override
    public IFraction negate() {
        return null;
    }

    @Override
    public IFraction inverse() {
        return null;
    }

    @Override
    public IFraction floorFraction() {
        return null;
    }

    @Override
    public IFraction abs() {
        return null;
    }
}
