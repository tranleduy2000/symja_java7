package org.matheclipse.core.basic;

import java.util.HashSet;
import java.util.Set;

import org.hipparchus.util.Precision;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISymbol;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * General configuration settings.
 */
public class Config {

	/**
	 * A global expression cache which compares keys with <code>==</code> object identity instead of
	 * <code>equals()</code>. The keys and values are weak references.
	 */
	public static Cache<IExpr, Object> EXPR_CACHE;

	private static final int MAX_EXPR_CACHE_SIZE = 10000;

	static {
		EXPR_CACHE = CacheBuilder.newBuilder().maximumSize(MAX_EXPR_CACHE_SIZE).weakKeys().weakValues().build();
	}
	/**
	 * COMPILER switch - set this boolean variable to <code>true</code>, if you would force a direct plot frame creation
	 * from the Plot[], Plot3D[] and ParametricPlot[] functions
	 * 
	 * On the server this switch should be set to <code>false</code>
	 */
	public static boolean SWING_PLOT_FRAME = false;

	/**
	 * The time in milliseconds an evaluation thread should run.<br/>
	 * 0 => forever
	 * 
	 */
	public static long TIME_CONSTRAINED_MILLISECONDS = 60000L;

	/**
	 * The time in milliseconds an evaluation thread should run.<br/>
	 * 0 => forever
	 * 
	 */
	public final static long FOREVER = 0L;

	/**
	 * The time in milliseconds an evaluation thread should sleep until <code>Thread#stop()</code> will be called.
	 * 
	 */
	public final static long TIME_CONSTRAINED_SLEEP_MILLISECONDS = 500;

	/**
	 * Switch debug mode on/off
	 * 
	 */
	public final static boolean DEBUG = false;

	/**
	 * Show the stack trace, if an exception is thrown in evaluation
	 * 
	 */
	public final static boolean SHOW_STACKTRACE = false;

	/**
	 * Show the console output, if an expression has a head symbol with attribute <code>ISymbol.CONSOLE_OUTPUT</code>.
	 * 
	 */
	public final static boolean SHOW_CONSOLE = true;

	/**
	 * Show the pattern-matching evaluation steps in the console output.
	 * 
	 */
	public final static boolean SHOW_PATTERN_EVAL_STEPS = false;

	public final static Set<ISymbol> SHOW_PATTERN_SYMBOL_STEPS = new HashSet<ISymbol>();

	/**
	 * If <code>true</code> the parser doesn't distinguish between lower- or uppercase symbols (i.e. constants, function
	 * names,...), with the exception of symbols with only one character (i.e. the variable &quot;i&quot; is different
	 * from the imaginary unit &quot;I&quot;)
	 */
	public static boolean PARSER_USE_LOWERCASE_SYMBOLS = true;

	/**
	 * If <code>true</code> the parser doesn't allow "square brackets" instead of "parentheses" for enclosing function
	 * arguments in relaxed mode. The syntax <code>f[x, y, ...]</code> isn't allowed then. Always use
	 * <code>f(x, y, ...)</code>.
	 * 
	 */
	public final static boolean PARSER_USE_STRICT_SYNTAX = false;

	/**
	 * Used to parse Rubi files. See <a href="http://www.apmaths.uwo.ca/~arich/">Rubi - Symbolic Integration Rules</a>
	 */
	public static boolean RUBI_CONVERT_SYMBOLS = false;

	/**
	 * Used to serialize the internal Rubi rules or the <code>org.matheclipse.core.reflection.system.rules</code>
	 * classes to a file.
	 */
	public static boolean SERIALIZE_SYMBOLS = false;

	/**
	 * If set to true the <code>Integrate</code> initialization Rules will be read from ressource
	 * <code>/ser/integrate.ser</code>
	 */
	// public static boolean LOAD_SERIALIZED_RULES = false;

	/**
	 * <code>true</code> if the engine is started by a servlet<br/>
	 * In <i>server mode</i> the user can only assign values to variables with prefix '$' <br/>
	 * <br/>
	 * SERVER_MODE should be set to <code>true</code> in the initialization of a servlet
	 * 
	 */
	public static boolean SERVER_MODE = false;

	/**
	 * See <a href="http://en.wikipedia.org/wiki/Machine_epsilon">Wikipedia: Machine epsilon</a>
	 */
	public static double DOUBLE_EPSILON = Precision.EPSILON;

	/**
	 * The double tolerance used for comparisons.
	 */
	public final static double DOUBLE_TOLERANCE = DOUBLE_EPSILON * 10d;

	/**
	 * Replace <code>double</code> values in root algorithms by 0 if they are below this tolerance.
	 */
	public final static double DEFAULT_ROOTS_CHOP_DELTA = 1.0e-5;

	/**
	 * <p>
	 * Flag for thread usage.
	 * </p>
	 * <p>
	 * <b>Note:</b> Only introduced because Google app engine does not support threads.
	 * </p>
	 * 
	 * @see edu.jas.ufd.GCDFactory#getProxy(edu.jas.structure.RingFactory)
	 */
	public static boolean JAS_NO_THREADS = false;

	/**
	 * Use <code>Num</code> objects for numeric calculations up to 15 digits precision.
	 */
	public static final int MACHINE_PRECISION = 15;

	/**
	 * Enable tests with local files.
	 */
	public static final boolean TEST_FILESYSTEM = false;
}
