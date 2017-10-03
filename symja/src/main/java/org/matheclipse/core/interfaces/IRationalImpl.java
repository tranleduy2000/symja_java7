package org.matheclipse.core.interfaces;

/**
 * Created by Duy on 10/3/2017.
 */

public abstract class IRationalImpl extends ISignedNumberImpl implements IRational {
    /**
     * {@inheritDoc}
     */
    @Override
    public abstract IRational abs();

    @Override
    public abstract IRational negate();
}
