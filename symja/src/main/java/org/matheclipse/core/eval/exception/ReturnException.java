package org.matheclipse.core.eval.exception;

import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IExpr;

import javax.annotation.Nonnull;

public class ReturnException extends FlowControlException {
    public final static ReturnException RETURN_FALSE = new ReturnException(F.False);
    public final static ReturnException RETURN_TRUE = new ReturnException(F.True);
    /**
     *
     */
    private static final long serialVersionUID = 6165872840807864554L;
    final private IExpr value;

    public ReturnException() {
        this(F.Null);
    }

    public ReturnException(@Nonnull final IExpr val) {
        super("Return from a function definition.");
        value = val;
    }

    public IExpr getValue() {
        return value;
    }
}
