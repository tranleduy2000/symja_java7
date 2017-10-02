package org.matheclipse.core.interfaces.impl;

import org.matheclipse.core.interfaces.IInteger;

import java.math.BigInteger;

/**
 * Created by Duy on 10/2/2017.
 */
//public interface IInteger extends IRational {

public abstract class IntegerImpl extends RationalImpl implements IInteger {
    @Override
    public IInteger abs() {
        return null;
    }

    @Override
    public IInteger multiply(int value) {
        return null;
    }

    @Override
    public IInteger negate() {
        return null;
    }

    @Override
    public IInteger pow(long exp) throws ArithmeticException {
        return null;
    }

    @Override
    public BigInteger toBigNumerator() {
        return null;
    }
}
