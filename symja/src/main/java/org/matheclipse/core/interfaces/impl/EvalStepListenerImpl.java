package org.matheclipse.core.interfaces.impl;

import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IEvalStepListener;
import org.matheclipse.core.polynomials.ExprPolynomial;

/**
 * Created by Duy on 10/2/2017.
 */

public abstract class EvalStepListenerImpl implements IEvalStepListener {

    public IAST rootsOfQuadraticPolynomial(ExprPolynomial polynomial) {
        return F.NIL;
    }
}
