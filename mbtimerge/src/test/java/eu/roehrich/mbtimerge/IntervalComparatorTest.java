package eu.roehrich.mbtimerge;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IntervalComparatorTest {

    IntervalComparator testant = new IntervalComparator();

    @Test
    void compare_withEqualIntervals() {
        assertEquals(IntervalComparator.CompareResult.EQUALS, testant.compare(
                Interval.of(11, 47),
                Interval.of(11, 47)
        ));
    }


    @Test
    void compare_withOverlapAboveFirst() {
        assertEquals(IntervalComparator.CompareResult.OVERLAP_ABOVE_FIRST, testant.compare(
                Interval.of(15, 20),
                Interval.of(17, 25)
        ));
        assertEquals(IntervalComparator.CompareResult.OVERLAP_ABOVE_FIRST, testant.compare(
                Interval.of(15, 20),
                Interval.of(20, 25)
        ));
        assertNotEquals(IntervalComparator.CompareResult.OVERLAP_ABOVE_FIRST, testant.compare(
                Interval.of(15, 20),
                Interval.of(15, 25)
        ));
    }


    @Test
    void compare_withOverlapBelowFirst() {
        assertEquals(IntervalComparator.CompareResult.OVERLAP_BELOW_FIRST, testant.compare(
                Interval.of(15, 20),
                Interval.of(1, 16)
        ));
        assertEquals(IntervalComparator.CompareResult.OVERLAP_BELOW_FIRST, testant.compare(
                Interval.of(15, 20),
                Interval.of(1, 15)
        ));
        assertNotEquals(IntervalComparator.CompareResult.OVERLAP_BELOW_FIRST, testant.compare(
                Interval.of(15, 20),
                Interval.of(1, 20)
        ));
    }

    @Test
    void compare_withDisjunct() {
        assertEquals(IntervalComparator.CompareResult.DISJUNCT, testant.compare(
                Interval.of(15, 20),
                Interval.of(45621, 545746)
        ));
        assertEquals(IntervalComparator.CompareResult.DISJUNCT, testant.compare(
                Interval.of(45621, 545746),
                Interval.of(15, 20)
        ));
    }

    @Test
    void compare_withFirstInSecond() {
        assertEquals(IntervalComparator.CompareResult.FIRST_IN_SECOND, testant.compare(
                Interval.of(15, 20),
                Interval.of(13, 25)
        ));
        assertEquals(IntervalComparator.CompareResult.FIRST_IN_SECOND, testant.compare(
                Interval.of(15, 20),
                Interval.of(13, 20)
        ));
        assertEquals(IntervalComparator.CompareResult.FIRST_IN_SECOND, testant.compare(
                Interval.of(13, 20),
                Interval.of(13, 25)
        ));
    }

    @Test
    void compare_withSecondInFirst() {
        assertEquals(IntervalComparator.CompareResult.SECOND_IN_FIRST, testant.compare(
                Interval.of(15, 20),
                Interval.of(16, 19)
        ));
        assertEquals(IntervalComparator.CompareResult.SECOND_IN_FIRST, testant.compare(
                Interval.of(15, 20),
                Interval.of(16, 20)
        ));
        assertEquals(IntervalComparator.CompareResult.SECOND_IN_FIRST, testant.compare(
                Interval.of(16, 20),
                Interval.of(16, 19)
        ));
    }

}