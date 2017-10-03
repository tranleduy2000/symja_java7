package org.matheclipse.core.interfaces.defaultmethod;

import org.matheclipse.core.interfaces.IComplexNum;

/**
 * Created by Duy on 10/3/2017.
 */

public abstract class IComplexNumImpl extends INumberImpl implements IComplexNum {
    @Override
    public long leafCount() {
        return 3;
    }

    @Override
    public abstract IComplexNum conjugate();
}
