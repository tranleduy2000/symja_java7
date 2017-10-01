package org.matheclipse.core.interfaces;

import org.hipparchus.fraction.BigFraction;
import java.math.BigInteger;


/**
 * interface for "fractional" numbers
 * 
 */
public interface IFraction extends IRational {

	/** {@inheritDoc} */
	@Override
	public IFraction abs();

	
	@Override
	public abstract IFraction floorFraction();
	
	/**
	 * Return the fractional part of this fraction
	 * @return
	 */
	public IFraction fractionalPart();
	
	public IFraction add(IFraction parm1);

	public IFraction div(IFraction other);

	/**
	 * Returns an array of two BigIntegers containing (numerator / denominator)
	 * followed by (numerator % denominator).
	 * 
	 * @return
	 */
	public IInteger[] divideAndRemainder();

	/**
	 * Returns a new rational representing the inverse of <code>this</code>.
	 * 
	 * @return Inverse of <code>this</code>.
	 */
	@Override
	public IFraction inverse();

	public IFraction mul(IFraction other);

	/**
	 * Returns a new rational equal to <code>-this</code>.
	 * 
	 * @return <code>-this</code>.
	 */
	@Override
	public IFraction negate();

	@Override
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
	@Override
	public IFraction pow(final long exp) throws ArithmeticException;

	public IFraction sub(IFraction parm1);

	/**
	 * Returns the denominator of this fraction.
	 * 
	 * @return denominator
	 */
	@Override
	public BigInteger toBigDenominator();

	/**
	 * Return the <code>org.apache.commons.math3.fraction.BigFraction</code>
	 * representation.
	 * 
	 * @return
	 */
	public BigFraction toBigFraction();

	/**
	 * Returns the numerator of this fraction.
	 * 
	 * @return numerator
	 */
	@Override
	public BigInteger toBigNumerator();
	
	 
}
