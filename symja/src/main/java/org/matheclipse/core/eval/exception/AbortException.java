package org.matheclipse.core.eval.exception;

public class AbortException extends FlowControlException {
    public final static AbortException ABORTED = new AbortException();
    /**
     *
     */
    private static final long serialVersionUID = 7641731563890805624L;

    public AbortException() {
        super("Abort evaluation.");
    }

}
