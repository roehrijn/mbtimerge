package eu.roehrich.mbtimerge;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IntervalTest {

    @Test
    void constructor_withValuesOutOfRange() {
        assertDoesNotThrow(() -> new Interval(Interval.MIN_VALUE, 0));
        assertDoesNotThrow(() -> new Interval(0, Interval.MAX_VALUE));
        assertThrows(IllegalArgumentException.class, () -> new Interval(Interval.MIN_VALUE-1, 0));
        assertThrows(IllegalArgumentException.class, () -> new Interval(0, Interval.MAX_VALUE+1));
    }

    @Test
    void constructor_withBeginNotSmallerThanEnd() {
        assertThrows(IllegalArgumentException.class, () -> new Interval(6, 5));
        assertThrows(IllegalArgumentException.class, () -> new Interval(0, 0));
        assertThrows(IllegalArgumentException.class, () -> new Interval(-4, -5));
    }

    @Test
    void compareTo() {
        assertEquals(0, Interval.of(5, 8).compareTo(Interval.of(5, 8)));
        assertEquals(-1, Interval.of(4, 8).compareTo(Interval.of(5, 8)));
        assertEquals(1, Interval.of(4, 9).compareTo(Interval.of(4, 8)));
        assertEquals(1, Interval.of(6, 8).compareTo(Interval.of(5, 8)));
        assertEquals(-1, Interval.of(6, 8).compareTo(Interval.of(6, 9)));
    }
}