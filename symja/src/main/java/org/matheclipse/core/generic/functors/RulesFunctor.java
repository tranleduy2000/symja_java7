package org.matheclipse.core.generic.functors;

import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IExpr;

import java.util.Map;
import java.util.function.Function;

import javax.annotation.Nonnull;

public class RulesFunctor implements Function<IExpr, IExpr> {
    private final Map<? extends IExpr, ? extends IExpr> fEqualRules;

    /**
     * @param plusAST  the complete AST which should be cloned in the {@code apply}
     *                 method
     * @param position the position which should be replaced in the <code>apply()</code>
     *                 method.
     */
    public RulesFunctor(Map<? extends IExpr, ? extends IExpr> rulesMap) {
        fEqualRules = rulesMap;
    }

    @Override
    @Nonnull
    public IExpr apply(final IExpr arg) {
        IExpr temp = fEqualRules.get(arg);
        return temp != null ? temp : F.NIL;
    }

}
