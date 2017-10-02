package org.matheclipse.core.generic.precidates;

import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;

import java.io.Serializable;
import java.util.function.Predicate;

public class InASTPredicate implements Predicate<IExpr>, Serializable {
    private static final long serialVersionUID = 0;

    private final IAST target;

    public InASTPredicate(IAST target) {
        this.target = target;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof InASTPredicate) {
            InASTPredicate that = (InASTPredicate) obj;
            return target.equals(that.target);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return target.hashCode();
    }

    @Override
    public boolean test(IExpr t) {
        for (IExpr expr : target) {
            if (expr.equals(t)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "In(" + target + ")";
    }
}
