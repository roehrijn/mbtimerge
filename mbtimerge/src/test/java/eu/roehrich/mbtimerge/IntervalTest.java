package eu.roehrich.mbtimerge;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntervalTest {

    @Test
    void compareTo() {
        assertEquals(0, Interval.of(5, 8).compareTo(Interval.of(5, 8)));
        assertEquals(-1, Interval.of(4, 8).compareTo(Interval.of(5, 8)));
        assertEquals(1, Interval.of(4, 9).compareTo(Interval.of(4, 8)));
        assertEquals(1, Interval.of(6, 8).compareTo(Interval.of(5, 8)));
        assertEquals(-1, Interval.of(6, 8).compareTo(Interval.of(6, 9)));
    }
}