package eu.roehrich.mbtimerge;

import org.javatuples.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MbtiMergeTest {

    @Test
    void doMerge() {
        var result = MbtiMerge.doMerge(Arrays.asList(
                Pair.with(25,30),
                Pair.with(2,19),
                Pair.with(14,23),
                Pair.with(4,8)
        ));
        Assertions.assertIterableEquals(
                Arrays.asList(
                        Pair.with(2,23),
                        Pair.with(25,30)
                ),
                result);
    }

    @Test
    void compare_withEqualIntervals() {
        assertEquals(MbtiMerge.CompareResult.EQUALS, MbtiMerge.compare(
                Pair.with(11, 47),
                Pair.with(11, 47)
        ));
    }


    @Test
    void compare_withOverlapAboveFirst() {
        assertEquals(MbtiMerge.CompareResult.OVERLAP_ABOVE_FIRST, MbtiMerge.compare(
                Pair.with(15, 20),
                Pair.with(17, 25)
        ));
        assertEquals(MbtiMerge.CompareResult.OVERLAP_ABOVE_FIRST, MbtiMerge.compare(
                Pair.with(15, 20),
                Pair.with(20, 25)
        ));
        assertNotEquals(MbtiMerge.CompareResult.OVERLAP_ABOVE_FIRST, MbtiMerge.compare(
                Pair.with(15, 20),
                Pair.with(15, 25)
        ));
    }


    @Test
    void compare_withOverlapBelowFirst() {
        assertEquals(MbtiMerge.CompareResult.OVERLAP_BELOW_FIRST, MbtiMerge.compare(
                Pair.with(15, 20),
                Pair.with(1, 16)
        ));
        assertEquals(MbtiMerge.CompareResult.OVERLAP_BELOW_FIRST, MbtiMerge.compare(
                Pair.with(15, 20),
                Pair.with(1, 15)
        ));
        assertNotEquals(MbtiMerge.CompareResult.OVERLAP_BELOW_FIRST, MbtiMerge.compare(
                Pair.with(15, 20),
                Pair.with(1, 20)
        ));
    }

    @Test
    void compare_withDisjunct() {
        assertEquals(MbtiMerge.CompareResult.DISJUNCT, MbtiMerge.compare(
                Pair.with(15, 20),
                Pair.with(45621, 545746)
        ));
        assertEquals(MbtiMerge.CompareResult.DISJUNCT, MbtiMerge.compare(
                Pair.with(45621, 545746),
                Pair.with(15, 20)
        ));
    }

    @Test
    void compare_withFirstInSecond() {
        assertEquals(MbtiMerge.CompareResult.FIRST_IN_SECOND, MbtiMerge.compare(
                Pair.with(15, 20),
                Pair.with(13, 25)
        ));
        assertEquals(MbtiMerge.CompareResult.FIRST_IN_SECOND, MbtiMerge.compare(
                Pair.with(15, 20),
                Pair.with(13, 20)
        ));
        assertEquals(MbtiMerge.CompareResult.FIRST_IN_SECOND, MbtiMerge.compare(
                Pair.with(13, 20),
                Pair.with(13, 25)
        ));
    }

    @Test
    void compare_withSecondInFirst() {
        assertEquals(MbtiMerge.CompareResult.SECOND_IN_FIRST, MbtiMerge.compare(
                Pair.with(15, 20),
                Pair.with(16, 19)
        ));
        assertEquals(MbtiMerge.CompareResult.SECOND_IN_FIRST, MbtiMerge.compare(
                Pair.with(15, 20),
                Pair.with(16, 20)
        ));
        assertEquals(MbtiMerge.CompareResult.SECOND_IN_FIRST, MbtiMerge.compare(
                Pair.with(16, 20),
                Pair.with(16, 19)
        ));
    }
}