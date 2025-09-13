package counters;

/**
 * A Counter is a simple utility class used for counting.
 * It can increase, decrease, and return the current count value.
 */
public class Counter {
    private int counter;

    /**
     * Constructs a new Counter initialized to 0.
     */
    public Counter() {
        this.counter = 0;
    }

    /**
     * Increases the current count by the specified number.
     * @param number the amount to increase the count by
     */
    public void increase(int number) {
        counter += number;
    }

    /**
     * Decreases the current count by the specified number.
     * @param number the amount to decrease the count by
     */
    public void decrease(int number) {
        counter -= number;
    }

    /**
     * Returns the current count.
     * @return the current value of the counter
     */
    public int getValue() {
        return this.counter;
    }
}

