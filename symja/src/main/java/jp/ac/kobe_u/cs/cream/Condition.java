/*
 * @(#)Condition.java
 */
package jp.ac.kobe_u.cs.cream;

import java.util.List;

/**
 * Codition abstract class represents a condition of a
 * {@linkplain Constraint constraint} under the current solution.
 * See {@link Code} for more details.
 *
 * @author Naoyuki Tamura (tamura@kobe-u.ac.jp)
 * @version 1.4
 * @see Code
 * @see Serialized
 * @since 1.0
 */
public abstract class Condition {
    protected int index = -1;

    /**
     * Sets the condition to the network.
     *
     * @param network the network
     */
    public abstract void setTo(Network network);

    /**
     * Returns a list of {@linkplain Operation operations} applicable to the
     * condition.
     *
     * @return the list of operations
     */
    public abstract List<Operation> operations();

}
