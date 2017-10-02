package org.matheclipse.core.interfaces.impl;

import org.matheclipse.core.interfaces.IComplexNum;

/**
 * Created by Duy on 10/2/2017.
 */

public abstract class ComplexNumImpl extends NumberImpl implements IComplexNum {
    @Override
    public long leafCount() {
        return 3;
    }

    @Override
    public IComplexNum conjugate() {
        return null;
    }
}
