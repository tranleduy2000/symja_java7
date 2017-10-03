package org.matheclipse.core.graphics;

//import java.io.IOException;

import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.ISignedNumber;

import java.io.IOException;

public class Dimensions2D {

//	public Color color;
	public int width;
	public int height;

	public double xMin;
	public double xMax;
	public double yMin;
	public double yMax;

	public boolean plotRange;
	public boolean axes;

	public Dimensions2D() {
		this(600, 400);
	}

	public Dimensions2D(int width, int height) {
//		this.color = Color.BLACK;
		this.width = width;
		this.height = height;
		this.xMin = Double.MAX_VALUE;
		this.xMax = -Double.MAX_VALUE;
		this.yMin = Double.MAX_VALUE;
		this.yMax = -Double.MAX_VALUE;
		this.plotRange = false;
		this.axes = false;
	}

	public void getColorRGB(Appendable buf) throws IOException {
//		float[] rgb = color.getRGBColorComponents(null);
//		buf.append(Float.toString(rgb[0] * 100));
//		buf.append("%, ");
//		buf.append(Float.toString(rgb[1] * 100));
//		buf.append("%, ");
//		buf.append(Float.toString(rgb[2] * 100));
//		buf.append("%");
	}

	public double getXScale() {
		double diff = xMax - xMin;
		if (F.isZero(diff)) {
			return 0.0;
		}
		return width / diff;
	}

	public double getYScale() {
		double diff = yMax - yMin;
		if (F.isZero(diff)) {
			return 0.0;
		}
		return height / (yMax - yMin);
	}

	public boolean isAxes() {
		return axes;
	}

	public boolean isValidRange() {
		return xMin != Double.MAX_VALUE && xMax != -Double.MAX_VALUE && yMin != Double.MAX_VALUE
				&& yMax != -Double.MAX_VALUE;
	}

	public void minMax(double xmin, double xmax, double ymin, double ymax) {
		if (!plotRange) {
			if (xmin < xMin) {
				xMin = xmin;
			}
			if (xmax > xMax) {
				xMax = xmax;
			}
			if (ymin < yMin) {
				yMin = ymin;
			}
			if (ymax > yMax) {
				yMax = ymax;
			}
		}
	}

	public void setAxes(boolean axes) {
		this.axes = axes;
	}

	public void setColorRGB(String color) {
//		String c = color.toLowerCase();
//		if (c.equals("white")) {
//			this.color = Color.WHITE;
//		} else if (c.equals("black")) {
//			this.color = Color.BLACK;
//		} else if (c.equals("blue")) {
//			this.color = Color.BLUE;
//		} else if (c.equals("green")) {
//			this.color = Color.GREEN;
//		} else if (c.equals("magenta")) {
//			this.color = Color.MAGENTA;
//		} else if (c.equals("orange")) {
//			this.color = Color.ORANGE;
//		} else if (c.equals("red")) {
//			this.color = Color.RED;
//		} else if (c.equals("yellow")) {
//			this.color = Color.YELLOW;
//		}
	}

	public void setPlotRange(IAST p1, IAST p2) {
		double x1 = ((ISignedNumber) p1.arg1()).doubleValue();
		double y1 = ((ISignedNumber) p1.arg2()).doubleValue();
		double x2 = ((ISignedNumber) p2.arg1()).doubleValue();
		double y2 = ((ISignedNumber) p2.arg2()).doubleValue();
		minMax(x1, y1, x2, y2);
		plotRange = true;
	}
}
