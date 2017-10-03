package org.matheclipse.core.interfaces;

import org.hipparchus.fraction.BigFraction;
import java.math.BigInteger;

/**
 * Interface for "rational" numbers (i.e. numbers implementing IInteger or
 * IFraction)
 * 
 */
public interface IRational extends ISignedNumber, IBigNumber {

	/** {@inheritDoc} */
	@Override
	public IRational abs();

	public IRational add(IRational parm1);

	public IInteger ceil();

	public int compareInt(final int value);

	public IRational divideBy(IRational parm1);

	/**
	 * Check if this number equals the given fraction
	 * <code>numerator/denominator</code> number.
	 * <code>GCD(numerator, /denominator)</code> should be 1;
	 * 
	 * @param numerator
	 *            the numerator
	 * @param denominator
	 *            the denominator
	 * @return
	 */
	public boolean equalsFraction(final int numerator, final int denominator);

	/**
	 * Return the prime factors paired with their exponents for integer and
	 * fractional numbers. For factors of the denominator part of fractional
	 * numbers the exponents are negative.
	 * 
	 * <pre>
	 * factorInteger(-4) ==> {{-1,1},{2,2}}
	 * </pre>
	 * 
	 * @return the list of prime factors paired with their exponents
	 */
	public IAST factorInteger();

	public IInteger floor();

	/**
	 * Returns the denominator of this fraction.
	 * 
	 * @return denominator
	 */
	public BigInteger toBigDenominator();

	/**
	 * Returns the numerator of this fraction.
	 * 
	 * @return denominator
	 */
	public BigInteger toBigNumerator();

	/**
	 * Returns the denominator of this fraction.
	 * 
	 * @return denominator
	 */
	public IInteger getDenominator();

	/**
	 * Returns this number as <code>BigFraction</code> number.
	 * 
	 * @return <code>this</code> number s big fraction.
	 */
	public BigFraction getFraction();

	/**
	 * Returns the numerator of this fraction.
	 * 
	 * @return denominator
	 */
	public IInteger getNumerator();

	public IRational multiply(IRational parm1);

	@Override
	public IRational negate();

	/**
	 * Return the normalized form of this number (i.e. if the denominator part
	 * equals one, return the numerator part as an integer number).
	 * 
	 * @return
	 */
	public INumber normalize();

	/**
	 * Returns this number raised at the specified exponent.
	 * 
	 * @param exp
	 *            the exponent.
	 * @return <code>this<sup>exp</sup></code>
	 * @throws ArithmeticException
	 *             if {@code 0^0} is given.
	 */
	public IRational pow(final long exp) throws ArithmeticException;

	public IRational subtract(IRational parm1);

}
