package org.matheclipse.core.expression;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.hipparchus.fraction.BigFraction;
import org.matheclipse.core.basic.Config;
import org.matheclipse.core.form.output.OutputFormFactory;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.IFraction;
import org.matheclipse.core.interfaces.IInteger;
import org.matheclipse.core.interfaces.INumber;
import org.matheclipse.core.interfaces.IRational;
import org.matheclipse.core.interfaces.ISignedNumber;

/**
 * IFraction implementation which uses methods of the Apache <code>org.apache.commons.math3.fraction.BigFraction</code>
 * methods.
 * 
 * @see IFraction
 * @see FractionSym
 */
public class BigFractionSym extends AbstractFractionSym {

	/**
	 * 
	 */
	private static final long serialVersionUID = -553051997353641162L;

	transient int fHashValue;

	BigFraction fFraction;

	BigFractionSym(BigFraction fraction) {
		fFraction = fraction;
	}

	/**
	 * <p>
	 * Construct a rational from two BigIntegers.
	 * </p>
	 * <b>Note:</b> the constructor is package private and does not normalize. Use the static constructor valueOf
	 * instead.
	 * 
	 * @param nom
	 *            Numerator
	 * @param denom
	 *            Denominator
	 */
	BigFractionSym(BigInteger nom, BigInteger denom) {
		fFraction = new BigFraction(nom, denom);
	}

	@Override
	public IFraction abs() {
		return new BigFractionSym(fFraction.abs());
	}

	/**
	 * Return a new rational representing <code>this + other</code>.
	 * 
	 * @param other
	 *            Rational to add.
	 * @return Sum of <code>this</code> and <code>other</code>.
	 */
	@Override
	public IFraction add(IFraction other) {
		if (other.isZero())
			return this;

		BigInteger tdenom = toBigDenominator();
		BigInteger odenom = other.toBigDenominator();
		if (tdenom.equals(odenom)) {
			return valueOf(toBigNumerator().add(other.toBigNumerator()), tdenom);
		}
		BigInteger gcd = tdenom.gcd(odenom);
		BigInteger tdenomgcd = tdenom.divide(gcd);
		BigInteger odenomgcd = odenom.divide(gcd);
		BigInteger newnum = toBigNumerator().multiply(odenomgcd).add(other.toBigNumerator().multiply(tdenomgcd));
		BigInteger newdenom = tdenom.multiply(odenomgcd);
		return valueOf(newnum, newdenom);
	}

	@Override
	public IRational add(IRational parm1) {
		if (parm1.isZero()) {
			return this;
		}
		if (parm1 instanceof IFraction) {
			return add((IFraction) parm1);
		}
		IInteger p1 = (IInteger) parm1;
		BigInteger newnum = toBigNumerator().add(toBigDenominator().multiply(p1.toBigNumerator()));
		return valueOf(newnum, toBigDenominator());
	}

	@Override
	public IInteger ceil() {
		if (isIntegral()) {
			return AbstractIntegerSym.valueOf(toBigNumerator());
		}
		BigInteger div = toBigNumerator().divide(toBigDenominator());
		if (toBigNumerator().signum() > 0)
			div = div.add(BigInteger.ONE);
		return AbstractIntegerSym.valueOf(div);
	}

	/**
	 * Return a new rational representing the smallest integral rational not smaller than <code>this</code>.
	 * 
	 * @return Next bigger integer of <code>this</code>.
	 */
	@Override
	public IFraction ceilFraction() {
		if (isIntegral()) {
			return this;
		}
		BigInteger div = toBigNumerator().divide(toBigDenominator());
		if (toBigNumerator().signum() > 0) {
			div = div.add(BigInteger.ONE);
		}
		return AbstractFractionSym.valueOf(div, BigInteger.ONE);
	}

	@Override
	public int compareAbsValueToOne() {
		BigFraction temp = fFraction;
		if (fFraction.compareTo(BigFraction.ZERO) < 0) {
			temp = temp.negate();
		}
		return temp.compareTo(BigFraction.ONE);
	}

	@Override
	public int compareInt(final int value) {
		BigInteger dOn = toBigDenominator().multiply(BigInteger.valueOf(value));
		return toBigNumerator().compareTo(dOn);
	}

	@Override
	public int compareTo(IExpr expr) {
		if (expr instanceof IFraction) {
			BigInteger valthis = toBigNumerator().multiply(((IFraction) expr).toBigDenominator());
			BigInteger valo = ((IFraction) expr).toBigNumerator().multiply(toBigDenominator());
			return valthis.compareTo(valo);
		}
		if (expr instanceof IInteger) {
			return fFraction.compareTo(new BigFraction(((IInteger) expr).toBigNumerator(), BigInteger.ONE));
		}
		if (expr.isSignedNumber()) {
			return Double.compare(fFraction.doubleValue(), ((ISignedNumber) expr).doubleValue());
		}
		return super.compareTo(expr);
	}

	@Override
	public ComplexNum complexNumValue() {
		// double precision complex number
		double nr = toBigNumerator().doubleValue();
		double dr = toBigDenominator().doubleValue();
		return ComplexNum.valueOf(nr / dr);
	}

	/** {@inheritDoc} */
	@Override
	public IExpr dec() {
		return add(F.CN1);
	}

	/** {@inheritDoc} */
	@Override
	public IExpr inc() {
		return add(F.C1);
	}

	/**
	 * Return a new rational representing <code>this / other</code>.
	 * 
	 * @param other
	 *            Rational to divide.
	 * @return Quotient of <code>this</code> and <code>other</code>.
	 */
	@Override
	public IFraction div(IFraction other) {
		if (other.isOne()) {
			return this;
		}
		if (other.isMinusOne()) {
			return this.negate();
		}
		BigInteger denom = toBigDenominator().multiply(other.toBigNumerator());
		BigInteger nom = toBigNumerator().multiply(other.toBigDenominator());
		// +-inf : -c = -+inf
		if (denom.equals(BigInteger.ZERO) && other.toBigNumerator().signum() == -1)
			nom = nom.negate();
		return valueOf(nom, denom);
	}

	@Override
	public IInteger[] divideAndRemainder() {
		IInteger[] result = new IInteger[2];
		BigInteger[] intResult = toBigNumerator().divideAndRemainder(toBigDenominator());
		result[0] = AbstractIntegerSym.valueOf(intResult[0]);
		result[1] = AbstractIntegerSym.valueOf(intResult[1]);
		return result;
	}

	@Override
	public double doubleValue() {
		return fFraction.doubleValue();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof BigFractionSym) {
			BigFractionSym r = (BigFractionSym) o;
			return fFraction.equals(r.fFraction);
		}
		if (o instanceof FractionSym) {
			final FractionSym r = (FractionSym) o;
			return equalsFraction(r.fNumerator, r.fDenominator);
		}
		return false;
	}

	@Override
	public final boolean equalsFraction(final int numerator, final int denominator) {
		BigInteger num = fFraction.getNumerator();
		BigInteger den = fFraction.getDenominator();
		return num.intValue() == numerator && den.intValue() == denominator && num.bitLength() <= 31
				&& den.bitLength() <= 31;
	}

	@Override
	public boolean equalsInt(final int numerator) {
		BigInteger num = fFraction.getNumerator();
		return num.intValue() == numerator && fFraction.getDenominator().equals(BigInteger.ONE)
				&& num.bitLength() <= 31;
	}

	@Override
	public IInteger floor() {
		if (isIntegral()) {
			return AbstractIntegerSym.valueOf(toBigNumerator());
		}
		BigInteger div = toBigNumerator().divide(toBigDenominator());
		if (toBigNumerator().signum() < 0) {
			div = div.subtract(BigInteger.ONE);
		}
		return AbstractIntegerSym.valueOf(div);
	}

	/**
	 * Return a new rational representing the biggest integral rational not bigger than <code>this</code>.
	 * 
	 * @return Next smaller integer of <code>this</code>.
	 */
	@Override
	public IFraction floorFraction() {
		if (isIntegral()) {
			return this;
		}
		BigInteger div = toBigNumerator().divide(toBigDenominator());
		if (toBigNumerator().signum() < 0) {
			div = div.subtract(BigInteger.ONE);
		}
		return AbstractFractionSym.valueOf(div, BigInteger.ONE);
	}

	/**
	 * Returns the fractional part of the rational
	 * 
	 * @return Next smaller integer of <code>this</code>.
	 */
	@Override
	public IFraction fractionalPart() {
		if (isIntegral()) {
			return AbstractFractionSym.ZERO;
		}
		BigInteger den = fFraction.getDenominator();
		BigInteger newnum = fFraction.getNumerator().mod(den);
		if (isNegative()) {
			return AbstractFractionSym.valueOf(newnum.negate(), den);
		}
		return AbstractFractionSym.valueOf(newnum, den);
	}

	@Override
	public String fullFormString() {
		StringBuilder buf = new StringBuilder("Rational");
		if (Config.PARSER_USE_LOWERCASE_SYMBOLS) {
			buf.append('(');
		} else {
			buf.append('[');
		}
		buf.append(fFraction.getNumerator().toString());
		buf.append(',');
		buf.append(fFraction.getDenominator().toString());
		if (Config.PARSER_USE_LOWERCASE_SYMBOLS) {
			buf.append(')');
		} else {
			buf.append(']');
		}
		return buf.toString();
	}

	@Override
	public IExpr gcd(IExpr that) {
		if (that instanceof IFraction) {
			BigFraction arg2 = ((IFraction) that).toBigFraction();
			return valueOf(fFraction.getNumerator().gcd(arg2.getNumerator()),
					AbstractIntegerSym.lcm(fFraction.getDenominator(), arg2.getDenominator()));
		}
		return super.gcd(that);
	}

	/**
	 * Compute the gcd of two rationals (this and other). The gcd is the rational number, such that dividing this and
	 * other with the gcd will yield two co-prime integers.
	 * 
	 * @param other
	 *            the second rational argument.
	 * @return the gcd of this and other.
	 */
	public IFraction gcd(IFraction other) {
		if (other.isZero()) {
			return this;
		}
		BigInteger tdenom = this.toBigDenominator();
		BigInteger odenom = other.toBigDenominator();
		BigInteger gcddenom = tdenom.gcd(odenom);
		BigInteger denom = tdenom.divide(gcddenom).multiply(odenom);
		BigInteger num = toBigNumerator().gcd(other.toBigNumerator());
		return AbstractFractionSym.valueOf(num, denom);
	}

	@Override
	public BigInteger toBigDenominator() {
		return fFraction.getDenominator();
	}

	@Override
	public BigInteger toBigNumerator() {
		return fFraction.getNumerator();
	}

	/** {@inheritDoc} */
	@Override
	public BigFraction getFraction() {
		return fFraction;
	}

	/** {@inheritDoc} */
	@Override
	public BigFraction toBigFraction() {
		return fFraction;
	}

	@Override
	public int hashCode() {
		if (fHashValue == 0) {
			fHashValue = fFraction.hashCode();
		}
		return fHashValue;
	}

	/**
	 * Return a new rational representing <code>this / other</code>.
	 * 
	 * @param other
	 *            Rational to divide.
	 * @return Quotient of <code>this</code> and <code>other</code>.
	 */
	public IFraction idiv(IFraction other) {
		BigInteger num = toBigDenominator().multiply(other.toBigNumerator());
		BigInteger denom = toBigNumerator().multiply(other.toBigDenominator());

		if (denom.equals(BigInteger.ZERO) && toBigNumerator().signum() == -1) {
			num = num.negate();
		}
		return valueOf(num, denom);
	}

	@Override
	public String internalJavaString(boolean symbolsAsFactoryMethod, int depth, boolean useOperators) {
		try {
			int numerator = NumberUtil.toInt(fFraction.getNumerator());
			int denominator = NumberUtil.toInt(fFraction.getDenominator());
			if (numerator == 1) {
				switch (denominator) {
				case 2:
					return "C1D2";
				case 3:
					return "C1D3";
				case 4:
					return "C1D4";
				default:
				}
			}
			if (numerator == -1) {
				switch (denominator) {
				case 2:
					return "CN1D2";
				case 3:
					return "CN1D3";
				case 4:
					return "CN1D4";
				default:
				}
			}
		} catch (RuntimeException e) {
			return "QQ(" + fFraction.getNumerator().toString() + "L," + fFraction.getDenominator().toString() + "L)";
		}
		return "QQ(" + fFraction.getNumerator().toString() + "L," + fFraction.getDenominator().toString() + "L)";
	}

	/**
	 * Returns a new rational representing the inverse of <code>this</code>.
	 * 
	 * @return Inverse of <code>this</code>.
	 */
	@Override
	public IFraction inverse() {
		return new BigFractionSym(fFraction.reciprocal());
	}

	/**
	 * Check whether this rational represents an integral value (i.e. the denominator equals 1).
	 * 
	 * @return <code>true</code> iff value is integral.
	 */
	@Override
	public boolean isIntegral() {
		return fFraction.getDenominator().equals(BigInteger.ONE);
	}

	/** {@inheritDoc} */
	@Override
	public boolean isMinusOne() {
		return fFraction.equals(BigFraction.MINUS_ONE);
	}

	/** {@inheritDoc} */
	@Override
	public boolean isNegative() {
		return fFraction.getNumerator().compareTo(BigInteger.ZERO) < 0;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isOne() {
		return fFraction.equals(BigFraction.ONE);
	}

	/** {@inheritDoc} */
	@Override
	public boolean isPositive() {
		return fFraction.getNumerator().compareTo(BigInteger.ZERO) < 0;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isZero() {
		return fFraction.equals(BigFraction.ZERO);
	}

	/**
	 * Return a new rational representing <code>this * other</code>.
	 * 
	 * @param other
	 *            big integer to multiply.
	 * @return Product of <code>this</code> and <code>other</code>.
	 */
	public IFraction mul(BigInteger other) {
		if (other.bitLength() < 2) {
			int oint = other.intValue();
			if (oint == 1)
				return this;
			if (oint == -1)
				return this.negate();
			if (oint == 0)
				return AbstractFractionSym.ZERO;
		}
		return valueOf(toBigNumerator().multiply(other), toBigDenominator());
	}

	@Override
	public IRational multiply(IRational parm1) {
		if (parm1.isOne()) {
			return this;
		}
		if (parm1.isZero()) {
			return parm1;
		}
		if (parm1.isMinusOne()) {
			return this.negate();
		}
		if (parm1 instanceof IFraction) {
			return mul((IFraction) parm1);
		}
		IInteger p1 = (IInteger) parm1;
		BigInteger newnum = toBigNumerator().multiply(p1.toBigNumerator());
		return valueOf(newnum, toBigDenominator());
	}

	/**
	 * Returns a new rational equal to <code>-this</code>.
	 * 
	 * @return <code>-this</code>
	 */
	@Override
	public IFraction negate() {
		return new BigFractionSym(fFraction.negate());
	}

	@Override
	public INumber normalize() {
		if (toBigDenominator().equals(BigInteger.ONE)) {
			return F.integer(toBigNumerator());
		}
		if (toBigNumerator().equals(BigInteger.ZERO)) {
			return F.C0;
		}
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public IInteger round() {
		return AbstractIntegerSym.valueOf(NumberUtil.round(fFraction, BigDecimal.ROUND_HALF_EVEN));
	}

	/** {@inheritDoc} */
	@Override
	public int sign() {
		return fFraction.getNumerator().signum();
	}

	/** {@inheritDoc} */
	@Override
	public int toInt() throws ArithmeticException {
		if (toBigDenominator().equals(BigInteger.ONE)) {
			return NumberUtil.toInt(toBigNumerator());
		}
		if (toBigNumerator().equals(BigInteger.ZERO)) {
			return 0;
		}
		throw new ArithmeticException("toInt: denominator != 1");
	}

	/** {@inheritDoc} */
	@Override
	public long toLong() throws ArithmeticException {
		if (toBigDenominator().equals(BigInteger.ONE)) {
			return NumberUtil.toLong(toBigNumerator());
		}
		if (toBigNumerator().equals(BigInteger.ZERO)) {
			return 0L;
		}
		throw new ArithmeticException("toLong: denominator != 1");
	}

	@Override
	public String toString() {
		try {
			StringBuilder sb = new StringBuilder();
			OutputFormFactory.get().convertFraction(sb, this, Integer.MIN_VALUE, OutputFormFactory.NO_PLUS_CALL);
			return sb.toString();
		} catch (Exception e1) {
			// fall back to simple output format
			return fFraction.getNumerator().toString() + "/" + fFraction.getDenominator().toString();
		}
	}
}
