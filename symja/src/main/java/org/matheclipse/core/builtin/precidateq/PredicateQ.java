package org.matheclipse.core.builtin.precidateq;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IExpr;

public class PredicateQ {
    /**
     * Constructor for the unary predicate
     */
    public final static AtomQ ATOMQ = new AtomQ();
    final static PredicateQ CONST = new PredicateQ();

    static {
        F.AntisymmetricMatrixQ.setEvaluator(new AntisymmetricMatrixQ());
        F.AntihermitianMatrixQ.setEvaluator(new AntihermitianMatrixQ());
        F.ArrayQ.setEvaluator(new ArrayQ());
        F.AtomQ.setEvaluator(ATOMQ);
        F.BooleanQ.setEvaluator(new BooleanQ());
        F.DigitQ.setEvaluator(new DigitQ());
        F.EvenQ.setEvaluator(new EvenQ());
        F.ExactNumberQ.setEvaluator(new ExactNumberQ());
        F.FreeQ.setEvaluator(new FreeQ());
        F.HermitianMatrixQ.setEvaluator(new HermitianMatrixQ());
        F.InexactNumberQ.setEvaluator(new InexactNumberQ());
        F.IntegerQ.setEvaluator(new IntegerQ());
        F.ListQ.setEvaluator(new ListQ());
        F.MachineNumberQ.setEvaluator(new MachineNumberQ());
        F.MatchQ.setEvaluator(new MatchQ());
        F.MatrixQ.setEvaluator(new MatrixQ());
        F.MemberQ.setEvaluator(new MemberQ());
        F.MissingQ.setEvaluator(new MissingQ());
        F.NotListQ.setEvaluator(new NotListQ());
        F.NumberQ.setEvaluator(new NumberQ());
        F.NumericQ.setEvaluator(new NumericQ());
        F.OddQ.setEvaluator(new OddQ());
        F.PossibleZeroQ.setEvaluator(new PossibleZeroQ());
        F.PrimeQ.setEvaluator(new PrimeQ());
        F.RealNumberQ.setEvaluator(new RealNumberQ());
        F.SymbolQ.setEvaluator(new SymbolQ());
        F.SymmetricMatrixQ.setEvaluator(new SymmetricMatrixQ());
        F.SyntaxQ.setEvaluator(new SyntaxQ());
        F.UpperCaseQ.setEvaluator(new UpperCaseQ());
        F.ValueQ.setEvaluator(new ValueQ());
        F.VectorQ.setEvaluator(new VectorQ());
    }


    private PredicateQ() {

    }

    public static boolean possibleZeroQ(IExpr expr, EvalEngine engine) {
        if (expr.isNumber()) {
            return expr.isZero();
        }
        if (expr.isAST()) {
            expr = F.expandAll(expr, true, true);
            if (expr.isZero()) {
                return true;
            }
            if (expr.isPlus() || expr.isPower() || expr.isTimes()) {
                expr = engine.evaluate(expr);
                if (expr.isZero()) {
                    return true;
                }
                if (expr.isPlus() || expr.isPower() || expr.isTimes()) {
                    expr = engine.evaluate(F.Together(expr));
                    if (expr.isZero()) {
                        return true;
                    }
                }
            }
        }
        if (expr.isNumericFunction()) {
            IExpr temp = engine.evalN(expr);
            return temp.isZero();
        }

        return expr.isZero();
    }

    public static PredicateQ initialize() {
        return CONST;
    }


}