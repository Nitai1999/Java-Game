package geometry;
/**
 * Represents special math calculations.
 */
public class MathUtils {

    public static final double EPSILON = 0.00001;
    /**
     * returns true if doubles equal.
     * @param a first one
     * @param b second one
     * @return true if equal.
     */
    public static boolean doubleEquals(double a, double b) {
        return Math.abs(a - b) < EPSILON;
    }
}