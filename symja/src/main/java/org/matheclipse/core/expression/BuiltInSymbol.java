package org.matheclipse.core.expression;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.interfaces.ICoreFunctionEvaluator;
import org.matheclipse.core.eval.interfaces.ISignedNumberConstant;
import org.matheclipse.core.eval.interfaces.ISymbolEvaluator;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IBuiltInSymbol;
import org.matheclipse.core.interfaces.IEvaluator;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISymbol;
import org.matheclipse.core.interfaces.defaultmethod.IExprImpl;

import java.io.IOException;
import java.io.ObjectStreamException;
import com.duy.lambda.DoubleFunction;

/**
 * Implements Symbols for function, constant and variable names
 */
public class BuiltInSymbol extends Symbol implements IBuiltInSymbol {

    protected static final DummyEvaluator DUMMY_EVALUATOR = new DummyEvaluator();
    /**
     *
     */
    private static final long serialVersionUID = -4991038487281911261L;
    /**
     * The evaluation class of this built-in-function. See packages: package
     * <code>org.matheclipse.core.builtin.function</code> and
     * <code>org.matheclipse.core.reflection.system</code>.
     */
    private transient IEvaluator fEvaluator;

    public BuiltInSymbol(final String symbolName) {
        this(symbolName, null);
    }

    public BuiltInSymbol(final String symbolName, final IEvaluator evaluator) {
        super(symbolName, Context.SYSTEM);
        fEvaluator = evaluator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String definitionToString() throws IOException {
        // dummy call to ensure, that the associated rules are loaded:
        getEvaluator();
        return super.definitionToString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IExpr evaluate(EvalEngine engine) {
        if (hasLocalVariableStack()) {
            return IExprImpl.ofNullable(get());
        }
        IExpr result;
        if ((result = evalDownRule(engine, this)).isPresent()) {
            return result;
        }
        final IEvaluator module = getEvaluator();
        if (module instanceof ISymbolEvaluator) {
            IExpr temp;
            if (engine.isNumericMode()) {
                if (engine.isApfloat()) {
                    temp = ((ISymbolEvaluator) module).apfloatEval(this, engine);
                } else {
                    temp = ((ISymbolEvaluator) module).numericEval(this);
                }
            } else {
                temp = ((ISymbolEvaluator) module).evaluate(this);
            }
            return temp;
        }
        return F.NIL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IExpr evaluateHead(IAST ast, EvalEngine engine) {
        return isConstant() ? F.NIL : super.evaluateHead(ast, engine);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IEvaluator getEvaluator() {
        // use "Double-Checked Locking" idiom
        // https://en.wikipedia.org/wiki/Double-checked_locking
        if (fEvaluator == null) {
            synchronized (this) {
                if (fEvaluator == null) {
                    fEvaluator = DUMMY_EVALUATOR;
                    // if (Config.PARSER_USE_LOWERCASE_SYMBOLS) {
                    // SystemNamespace.DEFAULT.setEvaluator(this);
                    // } else {
                    // if (Character.isUpperCase(fSymbolName.charAt(0))) {
                    // SystemNamespace.DEFAULT.setEvaluator(this);
                    // }
                    // }
                }
            }
        }
        return fEvaluator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void setEvaluator(final IEvaluator evaluator) {
        evaluator.setUp(this);
        fEvaluator = evaluator;
    }

    @Override
    public boolean isBuiltInSymbol() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCoreFunctionSymbol() {
        return fEvaluator instanceof ICoreFunctionEvaluator;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isHoldOrHoldFormOrDefer() {
        return this.equals(F.Defer) || this.equals(F.Hold) || this.equals(F.HoldForm);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isE() {
        return this == F.E;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isFalse() {
        return this == F.False;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isIndeterminate() {
        return this == F.Indeterminate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isPi() {
        return this == F.Pi;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSignedNumberConstant() {
        return fEvaluator instanceof ISignedNumberConstant;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTrue() {
        return this == F.True;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IExpr mapConstantDouble(DoubleFunction<IExpr> function) {
        if (fEvaluator instanceof ISignedNumberConstant) {
            double value = ((ISignedNumberConstant) fEvaluator).evalReal();
            if (value < Integer.MAX_VALUE && value > Integer.MIN_VALUE) {
                return function.apply(value);
            }
        }
        return F.NIL;
    }

    public Object readResolve() throws ObjectStreamException {
        ISymbol sym = fContext.get(fSymbolName);
        if (sym != null) {
            return sym;
        }
        BuiltInSymbol symbol = new BuiltInSymbol(fSymbolName);
        fContext.put(fSymbolName, symbol);
        symbol.fAttributes = fAttributes;
        return symbol;
    }

    static class DummyEvaluator implements IEvaluator {

        @Override
        public void join() {
            F.join();
        }

        @Override
        public void setUp(ISymbol newSymbol) {
            // do nothing because of dummy evaluator
        }
    }
}