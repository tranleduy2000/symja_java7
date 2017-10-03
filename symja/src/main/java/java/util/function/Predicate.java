package java.util.function;

public interface Predicate<T> {

    /**
     * Evaluates this predicate on the given argument.
     *
     * @param x the input argument
     * @return {@code true} if the input argument matches the predicate,
     * otherwise {@code false}
     */
    boolean test(T x);
}