package org.matheclipse.core.interfaces.impl;

import org.matheclipse.core.interfaces.IRational;

/**
 * Created by Duy on 10/2/2017.
 */
//public interface IRational extends ISignedNumber, IBigNumber {

public abstract class RationalImpl extends SignedNumberImpl implements IRational {

    @Override
    public IRational abs() {
        return null;
    }

    @Override
    public IRational negate() {
        return null;
    }
}
