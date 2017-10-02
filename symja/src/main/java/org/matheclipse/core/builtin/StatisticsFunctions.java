package org.matheclipse.core.builtin;

import org.hipparchus.distribution.IntegerDistribution;
import org.hipparchus.distribution.RealDistribution;
import org.hipparchus.linear.FieldMatrix;
import org.hipparchus.linear.RealMatrix;
import org.hipparchus.stat.StatUtils;
import org.matheclipse.core.basic.Config;
import org.matheclipse.core.convert.Convert;
import org.matheclipse.core.eval.EvalAttributes;
import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.exception.WrongArgumentType;
import org.matheclipse.core.eval.interfaces.AbstractEvaluator;
import org.matheclipse.core.eval.interfaces.AbstractFunctionEvaluator;
import org.matheclipse.core.eval.interfaces.AbstractMatrix1Expr;
import org.matheclipse.core.eval.interfaces.AbstractTrigArg1;
import org.matheclipse.core.expression.ASTRealMatrix;
import org.matheclipse.core.expression.ASTRealVector;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISignedNumber;
import org.matheclipse.core.interfaces.ISymbol;

public class StatisticsFunctions {
    final static StatisticsFunctions CONST = new StatisticsFunctions();

    static {
        F.CDF.setEvaluator(new CDF());
        F.PDF.setEvaluator(new PDF());
        F.CentralMoment.setEvaluator(new CentralMoment());
        F.Correlation.setEvaluator(new Correlation());
        F.Covariance.setEvaluator(new Covariance());
        F.Kurtosis.setEvaluator(new Kurtosis());
        F.Mean.setEvaluator(new Mean());
        F.Median.setEvaluator(new Median());
        F.Skewness.setEvaluator(new Skewness());
        F.Variance.setEvaluator(new Variance());
    }

    private StatisticsFunctions() {

    }

    public static StatisticsFunctions initialize() {
        return CONST;
    }

    /**
     * Compute the cumulative distribution function
     */
    private static class CDF extends AbstractFunctionEvaluator {

        @Override
        public IExpr evaluate(final IAST ast, EvalEngine engine) {
            Validate.checkSize(ast, 3);

            if (ast.arg1().isAST()) {
                IAST arg1 = (IAST) ast.arg1();
                IExpr xArg = ast.arg2();

                if (arg1.isAST()) {
                    IAST dist = arg1;

                    if (dist.head().isSymbol()) {
                        ISymbol head = (ISymbol) dist.head();
                        if (engine.isNumericMode()) {
                            // numeric calculations
                            return evaluateNumericMode(dist, xArg, head);
                        } else {
                            // symbolic calculations
                        }
                    }
                }
            }
            return F.NIL;
        }

        private IExpr evaluateNumericMode(IAST dist, IExpr xArg, ISymbol head) {
            try {
                RealDistribution realDistribution;
                IntegerDistribution intDistribution;

                if (dist.isAST1()) {
                    if (head.equals(F.BernoulliDistribution)) {
                    } else if (head.equals(F.PoissonDistribution)) {
                        int n = ((ISignedNumber) dist.arg1()).toInt();
                        int k = ((ISignedNumber) xArg).toInt();
                    }
                } else if (dist.isAST2()) {

                    if (head.equals(F.BinomialDistribution)) {
                        int n = ((ISignedNumber) dist.arg1()).toInt();
                        double p = ((ISignedNumber) dist.arg2()).doubleValue();
                        int k = ((ISignedNumber) xArg).toInt();
                    } else if (head.equals(F.NormalDistribution)) {
                        double mean = ((ISignedNumber) dist.arg1()).doubleValue();
                        double stdDev = ((ISignedNumber) dist.arg2()).doubleValue();
                        double x = ((ISignedNumber) xArg).doubleValue();
                    }
                } else if (dist.isAST3()) {
                    if (head.equals(F.HypergeometricDistribution)) {
                        int n = ((ISignedNumber) dist.arg1()).toInt();
                        int nSucc = ((ISignedNumber) dist.arg2()).toInt();
                        int nTot = ((ISignedNumber) dist.arg3()).toInt();
                        int k = ((ISignedNumber) xArg).toInt();
                    }
                }
            } catch (ArithmeticException ae) {
            } catch (ClassCastException cca) {
            }
            return F.NIL;
        }

    }

    /**
     * <pre>
     * CentralMoment(list, r)
     * </pre>
     * <p>
     * <blockquote>
     * <p>
     * gives the the <code>r</code>th central moment (i.e. the <code>r</code>th
     * moment about the mean) of <code>list</code>.
     * </p>
     * </blockquote>
     * <p>
     * See:<br />
     * </p>
     * <ul>
     * <li><a href="https://en.wikipedia.org/wiki/Central_moment">Wikipedia -
     * Central moment</a></li>
     * </ul>
     * <h3>Examples</h3>
     * <p>
     * <pre>
     * &gt;&gt;&gt; CentralMoment({1.1, 1.2, 1.4, 2.1, 2.4}, 4)
     * 0.10085
     * </pre>
     */
    private final static class CentralMoment extends AbstractEvaluator {

        @Override
        public IExpr evaluate(final IAST ast, EvalEngine engine) {
            Validate.checkSize(ast, 3);
            if (ast.arg1().isList()) {
                IAST list = (IAST) ast.arg1();
                IExpr r = ast.arg2();
                return F.Divide(F.Total(F.Power(F.Subtract(list, F.Mean(list)), r)), F.Length(list));
            }
            return F.NIL;
        }

        @Override
        public void setUp(final ISymbol newSymbol) {
        }

    }

    private final static class Correlation extends AbstractFunctionEvaluator {

        @Override
        public IExpr evaluate(final IAST ast, EvalEngine engine) {
            Validate.checkSize(ast, 3);
            IExpr a = (IAST) ast.arg1();
            IExpr b = (IAST) ast.arg2();
            int dim1 = a.isVector();
            int dim2 = b.isVector();
            if (dim1 >= 0 && dim1 == dim2) {
                return F.Divide(F.Covariance(a, b), F.Times(F.StandardDeviation(a), F.StandardDeviation(b)));
            }
            return F.NIL;
        }

    }

    /**
     * Compute the covariance.
     * <p>
     * See <a href="http://en.wikipedia.org/wiki/Covariance">Covariance</a>
     */
    private final static class Covariance extends AbstractMatrix1Expr {

        public static IExpr vectorCovarianceSymbolic(final IAST arg1, final IAST arg2, int arg1Length) {
            if (arg1Length == 2) {
                return F.Times(F.C1D2, F.Subtract(arg1.arg1(), arg1.arg2()),
                        F.Subtract(F.Conjugate(arg2.arg1()), F.Conjugate(arg2.arg2())));
            }
            IAST num1 = arg1.apply(F.Plus);
            IExpr factor = F.integer(-1 * (arg1.size() - 2));
            IAST v1 = F.Plus();
            for (int i = 1; i < arg1.size(); i++) {
                v1.append(F.Times(F.CN1, num1.setAtClone(i, F.Times(factor, arg1.get(i))), F.Conjugate(arg2.get(i))));
            }
            return F.Divide(v1, F.integer((arg1.size() - 1) * (arg1.size() - 2)));
        }

        @Override
        public IExpr evaluate(final IAST ast, EvalEngine engine) {
            Validate.checkRange(ast, 2, 3);
            if (ast.size() == 2) {
                return super.evaluate(ast, engine);
            }

            if (ast.size() == 3) {
                final IAST arg1 = (IAST) ast.arg1();
                final IAST arg2 = (IAST) ast.arg2();
                return evaluateArg2(arg1, arg2, engine);
            }
            return F.NIL;
        }

        private IExpr evaluateArg2(final IAST arg1, final IAST arg2, EvalEngine engine) {
            try {
                // if (engine.isApfloat()) {
                // FieldMatrix<IExpr> arg1FieldMatrix =
                // Convert.list2Matrix(arg1);
                // if (arg1FieldMatrix != null) {
                // FieldMatrix<IExpr> arg2FieldMatrix =
                // Convert.list2Matrix(arg2);
                // if (arg1FieldMatrix != null) {
                // return matrixEval2(arg1FieldMatrix, arg2FieldMatrix);
                // }
                // }
                // return F.NIL;
                // }
                int arg1Length = arg1.isVector();
                if (arg1Length > 1) {
                    int arg2Length = arg2.isVector();
                    if (arg1Length == arg2Length) {
                        try {
                            double[] arg1DoubleArray = arg1.toDoubleVector();
                            double[] arg2DoubleArray = arg2.toDoubleVector();
                            org.hipparchus.stat.correlation.Covariance cov = new org.hipparchus.stat.correlation.Covariance();
                            return F.num(cov.covariance(arg1DoubleArray, arg2DoubleArray, true));
                        } catch (Exception ex) {
                            //
                        }
                        return vectorCovarianceSymbolic(arg1, arg2, arg1Length);
                    }
                }
            } catch (final WrongArgumentType e) {
                // WrongArgumentType occurs in list2RealMatrix(),
                // if the matrix elements aren't pure numerical values
            } catch (final IndexOutOfBoundsException e) {
                if (Config.SHOW_STACKTRACE) {
                    e.printStackTrace();
                }
            }
            return F.NIL;
        }

        @Override
        public IExpr matrixEval(FieldMatrix<IExpr> matrix) {
            return F.NIL;
        }

        @Override
        public IExpr numericEval(final IAST ast, EvalEngine engine) {
            Validate.checkRange(ast, 2, 3);
            if (ast.size() == 2) {
                return super.numericEval(ast, engine);
            }
            if (ast.size() == 3) {
                final IAST arg1 = (IAST) ast.arg1();
                final IAST arg2 = (IAST) ast.arg2();
                return evaluateArg2(arg1, arg2, engine);
            }
            return F.NIL;
        }

        @Override
        public IExpr realMatrixEval(RealMatrix matrix) {
            org.hipparchus.stat.correlation.Covariance cov = new org.hipparchus.stat.correlation.Covariance(matrix);
            return new ASTRealMatrix(cov.getCovarianceMatrix(), false);
        }
    }

    private final static class Kurtosis extends AbstractEvaluator {

        @Override
        public IExpr evaluate(final IAST ast, EvalEngine engine) {
            Validate.checkSize(ast, 2);
            if (ast.arg1().isList()) {
                IAST list = (IAST) ast.arg1();
                return F.Divide(F.CentralMoment(list, F.C4), F.Power(F.CentralMoment(list, F.C2), F.C2));
            }
            return F.NIL;
        }

        @Override
        public void setUp(final ISymbol newSymbol) {
        }

    }

    /**
     * See <a href="http://en.wikipedia.org/wiki/Arithmetic_mean">Arithmetic
     * mean</a>
     */
    private final static class Mean extends AbstractTrigArg1 {

        @Override
        public IExpr evaluateArg1(final IExpr arg1) {
            if (arg1.isRealVector()) {
                return F.num(StatUtils.mean(arg1.toDoubleVector()));
            }
            if (arg1.isList()) {
                final IAST list = (IAST) arg1;
                return F.Times(list.apply(F.Plus), F.Power(F.integer(list.size() - 1), F.CN1));
            }

            if (arg1.isAST()) {
                IAST dist = (IAST) arg1;
                if (dist.head().isSymbol()) {
                    ISymbol head = (ISymbol) dist.head();
                    if (arg1.isAST1()) {
                        if (head.equals(F.BernoulliDistribution)) {
                            return dist.arg1();
                        } else if (head.equals(F.PoissonDistribution)) {
                            return dist.arg1();
                        }
                    } else if (arg1.isAST2()) {
                        if (head.equals(F.BinomialDistribution)) {
                            return F.Times(dist.arg1(), dist.arg2());
                        } else if (head.equals(F.NormalDistribution)) {
                            return dist.arg1();
                        }
                    } else if (arg1.isAST3()) {
                        IExpr n = dist.arg1();
                        IExpr nSucc = dist.arg2();
                        IExpr nTot = dist.arg3();
                        if (head.equals(F.HypergeometricDistribution)) {
                            return F.Divide(F.Times(n, nSucc), nTot);
                        }
                    }
                }
            }
            return F.NIL;
        }

        @Override
        public void setUp(final ISymbol newSymbol) {
            newSymbol.setAttributes(ISymbol.NOATTRIBUTE);
        }

    }

    /**
     * See <a href="http://en.wikipedia.org/wiki/Median">Median</a>
     */
    private final static class Median extends AbstractTrigArg1 {

        @Override
        public IExpr evaluateArg1(final IExpr arg1) {
            if (arg1.isRealVector()) {
                return F.num(StatUtils.percentile(arg1.toDoubleVector(), 50));
            }
            if (arg1.isList()) {
                final IAST list = (IAST) arg1;
                if (list.size() > 1) {
                    final IAST sortedList = list.copy();
                    EvalAttributes.sort(sortedList);
                    int size = sortedList.size();
                    if ((size & 0x00000001) == 0x00000001) {
                        // odd number of elements
                        size = size / 2;
                        return F.Times(F.Plus(sortedList.get(size), sortedList.get(size + 1)), F.C1D2);
                    } else {
                        return sortedList.get(size / 2);
                    }
                }
            }
            return F.NIL;
        }

        @Override
        public void setUp(final ISymbol newSymbol) {
            newSymbol.setAttributes(ISymbol.NOATTRIBUTE);
        }
    }

    /**
     * Compute the probability density function
     */
    private static class PDF extends AbstractFunctionEvaluator {

        @Override
        public IExpr evaluate(final IAST ast, EvalEngine engine) {
            Validate.checkSize(ast, 3);

            if (ast.arg1().isAST()) {
                IAST arg1 = (IAST) ast.arg1();
                IExpr xArg = ast.arg2();

                if (arg1.isAST()) {
                    IAST dist = arg1;

                    if (dist.head().isSymbol()) {
                        ISymbol head = (ISymbol) dist.head();
                        if (engine.isNumericMode()) {
                            // numeric calculations
                            return evaluateNumericMode(dist, xArg, head);
                        } else {
                            // symbolic calculations
                        }
                    }
                }
            }
            return F.NIL;
        }

        private IExpr evaluateNumericMode(IAST dist, IExpr xArg, ISymbol head) {
            try {
                RealDistribution realDistribution;
                IntegerDistribution intDistribution;

                if (dist.isAST1()) {
                    if (head.equals(F.BernoulliDistribution)) {
                    } else if (head.equals(F.PoissonDistribution)) {
                        int n = ((ISignedNumber) dist.arg1()).toInt();
                        int k = ((ISignedNumber) xArg).toInt();
                        intDistribution = new org.hipparchus.distribution.discrete.PoissonDistribution(n);
                        return F.num(intDistribution.probability(k));
                    }
                } else if (dist.isAST2()) {

                    if (head.equals(F.BinomialDistribution)) {
                        int n = ((ISignedNumber) dist.arg1()).toInt();
                        double p = ((ISignedNumber) dist.arg2()).doubleValue();
                        int k = ((ISignedNumber) xArg).toInt();
                        intDistribution = new org.hipparchus.distribution.discrete.BinomialDistribution(n, p);
                        return F.num(intDistribution.probability(k));
                    } else if (head.equals(F.NormalDistribution)) {
                        double mean = ((ISignedNumber) dist.arg1()).doubleValue();
                        double stdDev = ((ISignedNumber) dist.arg2()).doubleValue();
                        double x = ((ISignedNumber) xArg).doubleValue();
                        realDistribution = new org.hipparchus.distribution.continuous.NormalDistribution(mean, stdDev);
                        return F.num(realDistribution.density(x));
                    }
                } else if (dist.isAST3()) {
                    if (head.equals(F.HypergeometricDistribution)) {
                        int n = ((ISignedNumber) dist.arg1()).toInt();
                        int nSucc = ((ISignedNumber) dist.arg2()).toInt();
                        int nTot = ((ISignedNumber) dist.arg3()).toInt();
                        int k = ((ISignedNumber) xArg).toInt();
                        intDistribution = new org.hipparchus.distribution.discrete.HypergeometricDistribution(nTot,
                                nSucc, n);
                        return F.num(intDistribution.probability(k));
                    }
                }
            } catch (ArithmeticException ae) {
            } catch (ClassCastException cca) {
            }
            return F.NIL;
        }

    }

    /**
     * <pre>
     * Skewness(list)
     * </pre>
     * <p>
     * <blockquote>
     * <p>
     * gives Pearson's moment coefficient of skewness for $list$ (a measure for
     * estimating the symmetry of a distribution).
     * </p>
     * </blockquote>
     * <h3>Examples</h3>
     * <p>
     * <pre>
     * &gt;&gt;&gt; Skewness({1.1, 1.2, 1.4, 2.1, 2.4})
     * 0.40704
     * </pre>
     */
    private final static class Skewness extends AbstractEvaluator {

        @Override
        public IExpr evaluate(final IAST ast, EvalEngine engine) {
            Validate.checkSize(ast, 2);
            if (ast.arg1().isList()) {
                IAST list = (IAST) ast.arg1();
                return F.Divide(F.CentralMoment(list, F.C3), F.Power(F.CentralMoment(list, F.C2), F.C3D2));
            }
            return F.NIL;
        }

        @Override
        public void setUp(final ISymbol newSymbol) {
        }

    }

    /**
     * Compute the variance for a list of elements
     */
    private final static class Variance extends AbstractFunctionEvaluator {

        @Override
        public IExpr evaluate(final IAST ast, EvalEngine engine) {
            Validate.checkSize(ast, 2);

            if (ast.arg1().isAST()) {
                IAST arg1 = (IAST) ast.arg1();
                int[] matrixDimensions = arg1.isMatrix();
                if (matrixDimensions != null) {
                    if (arg1.isRealMatrix()) {
                        double[][] matrix = arg1.toDoubleMatrix();
                        matrix = Convert.toDoubleTransposed(matrix);
                        double[] result = new double[matrixDimensions[1]];
                        for (int i = 0; i < matrix.length; i++) {
                            result[i] = StatUtils.variance(matrix[i]);
                        }
                        return new ASTRealVector(result, false);
                    }
                    IAST result = F.ListAlloc(matrixDimensions[0]);
                    for (int i = 1; i < matrixDimensions[1] + 1; i++) {
                        IAST list = F.ListAlloc(matrixDimensions[1]);
                        IAST variance = F.Variance(list);
                        for (int j = 1; j < matrixDimensions[0] + 1; j++) {
                            list.append(arg1.getPart(j, i));
                        }
                        result.append(variance);
                    }
                    return result;
                }

                int dim = arg1.isVector();
                if (dim >= 0) {
                    if (arg1.isRealVector()) {
                        return F.num(StatUtils.variance(arg1.toDoubleVector()));
                    }
                    return Covariance.vectorCovarianceSymbolic(arg1, arg1, dim);
                }

                if (arg1.isAST()) {
                    IAST dist = arg1;
                    if (dist.head().isSymbol()) {
                        ISymbol head = (ISymbol) dist.head();
                        if (arg1.isAST1()) {
                            if (head.equals(F.BernoulliDistribution)) {
                            } else if (head.equals(F.PoissonDistribution)) {
                            }
                        } else if (arg1.isAST2()) {
                            if (head.equals(F.BinomialDistribution)) {
                            } else if (head.equals(F.NormalDistribution)) {
                            }
                        } else if (arg1.isAST3()) {
                            IExpr n = dist.arg1();
                            IExpr nSucc = dist.arg2();
                            IExpr nTot = dist.arg3();
                            if (head.equals(F.HypergeometricDistribution)) {
                            }
                        }
                    }
                }
            }
            return F.NIL;
        }

    }

}
