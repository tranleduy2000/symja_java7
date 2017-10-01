package org.matheclipse.core.reflection.system;

import org.hipparchus.analysis.UnivariateFunction;
import org.hipparchus.analysis.interpolation.HermiteInterpolator;
import org.hipparchus.analysis.interpolation.SplineInterpolator;
import org.hipparchus.analysis.interpolation.UnivariateInterpolator;
import org.hipparchus.linear.RealMatrix;
import org.matheclipse.core.basic.Config;
import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.WrongArgumentType;
import org.matheclipse.core.eval.interfaces.AbstractEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.INum;
import org.matheclipse.core.interfaces.ISymbol;

public class InterpolatingFunction extends AbstractEvaluator {

	public InterpolatingFunction() {
	}

	@Override
	public IExpr evaluate(final IAST ast, EvalEngine engine) {
		if (ast.head().isAST()) {
			if (ast.isAST1() && ast.arg1() instanceof INum) {
				final IAST function = (IAST) ast.head();
				if (function.isAST1()) {
					try {
						int[] dims = function.arg1().isMatrix();
						if (dims != null && dims[1] == 2) {
							HermiteInterpolator interpolator = interpolate((IAST) function.arg1());
							if (interpolator != null) {
								double[] interpolatedY = interpolator.value(((INum) ast.arg1()).doubleValue());
								return F.num(interpolatedY[0]);
								// double interpolatedY =
								// interpolateSpline(matrix, ((INum)
								// ast.arg1()).doubleValue());
								// return F.num(interpolatedY);
							}
						}
					} catch (final WrongArgumentType e) {
						// WrongArgumentType occurs in list2RealMatrix(),
						// if the matrix elements aren't pure numerical values
						if (Config.SHOW_STACKTRACE) {
							e.printStackTrace();
						}
					}
				}
				return F.NIL;
			}
		}
		return F.NIL;
	}

	private HermiteInterpolator interpolate(IAST matrixAST) {
		HermiteInterpolator interpolator = (HermiteInterpolator) Config.EXPR_CACHE.getIfPresent(matrixAST);
		if (interpolator != null) {
			return interpolator;
		}
		RealMatrix matrix = matrixAST.toRealMatrix();
		if (matrix != null) {
			int rowDim = matrix.getRowDimension();
			int colDim = matrix.getColumnDimension();
			double x[] = new double[colDim - 1];
			double[][] data = matrix.getData();
			interpolator = new HermiteInterpolator();
			for (int i = 0; i < rowDim; i++) {
				System.arraycopy(data[i], 1, x, 0, colDim - 1);
				interpolator.addSamplePoint(data[i][0], x);
			}
			Config.EXPR_CACHE.put(matrixAST, interpolator);
			return interpolator;
		}
		return null;
	}

	private double interpolateSpline(RealMatrix matrix, double interpolationX) {
		int rowDim = matrix.getRowDimension();
		double x[] = new double[rowDim];
		double y[] = new double[rowDim];
		double[][] data = matrix.getData();
		for (int i = 0; i < rowDim; i++) {
			x[i] = data[i][0];
			y[i] = data[i][1];
		}
		UnivariateInterpolator interpolator = new SplineInterpolator();
		UnivariateFunction function = interpolator.interpolate(x, y);
		double interpolatedY = function.value(interpolationX);
		return interpolatedY;
	}

	@Override
	public void setUp(final ISymbol newSymbol) {
		newSymbol.setAttributes(ISymbol.HOLDALL);
	}
}
