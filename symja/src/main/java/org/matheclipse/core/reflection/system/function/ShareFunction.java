package org.matheclipse.core.reflection.system.function;

import org.matheclipse.core.interfaces.IExpr;

import java.util.HashMap;

public class ShareFunction implements Function<IExpr, IExpr> {
    java.util.Map<IExpr, IExpr> map;

    public ShareFunction() {
        map = new HashMap<IExpr, IExpr>();
    }

    @Override
    public IExpr apply(IExpr t) {
        IExpr value = map.get(t);
        if (value == null) {
            map.put(t, t);
        } else {
            if (value == t) {
                return null;
            }
        }
        return value;
    }
}