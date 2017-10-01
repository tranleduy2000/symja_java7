package org.matheclipse.core.interfaces;

/**
 * An expression representing a complex number
 * 
 */
public interface IComplex extends IBigNumber {

	public IComplex add(IComplex val);

	/**
	 * Returns the imaginary part of a complex number
	 * 
	 * @return imaginary part
	 */
	public IRational getImaginaryPart();

	/**
	 * Returns the real part of a complex number
	 * 
	 * @return real part
	 */
	public IRational getRealPart();

	public IComplex multiply(IComplex val);

	public IComplex pow(int parm1);

	/**
	 * Return the normalized form of this number (i.e. if the imaginary part equals zero, return the real part as a fractional or
	 * integer number).
	 * 
	 * @return
	 */
	public INumber normalize();
}
