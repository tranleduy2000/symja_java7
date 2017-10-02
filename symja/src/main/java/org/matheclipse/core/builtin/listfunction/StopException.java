package org.matheclipse.core.builtin.listfunction; import java.util.function.Consumer; import java.util.function.Function; import java.util.function.Predicate;

import org.matheclipse.parser.client.math.MathException;

/**
 * StopException will be thrown, if maximum number of Cases results are reached
 */
@SuppressWarnings("serial")
public class StopException extends MathException {
    public StopException() {
        super("Stop Cases() evaluation");
    }
}
