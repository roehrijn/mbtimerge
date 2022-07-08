package eu.roehrich.mbtimerge;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BruteForceMergeTest {

    @Test
    void doMerge() {

        // Check example from the original task
        Assertions.assertThat(
                BruteForceMerge.doMerge(Arrays.asList(
                        Interval.of(25,30),
                        Interval.of(2,19),
                        Interval.of(14,23),
                        Interval.of(4,8)
                ))
        ).hasSameElementsAs(
                Arrays.asList(Interval.of(25,30), Interval.of(2,23))
        );

        // Check with negative interval
        Assertions.assertThat(
                BruteForceMerge.doMerge(Arrays.asList(
                        Interval.of(-30, -25),
                        Interval.of(-19, -2),
                        Interval.of(-23, -14),
                        Interval.of(-8, -4)
                ))
        ).hasSameElementsAs(
                Arrays.asList(Interval.of(-30, -25), Interval.of(-23, -2))
        );

        // Check with miged interval
        Assertions.assertThat(
                BruteForceMerge.doMerge(Arrays.asList(
                        Interval.of(-30, -25),
                        Interval.of(-23, -14),
                        Interval.of(-8, 4),
                        Interval.of(-1, 5)
                ))
        ).hasSameElementsAs(
                Arrays.asList(Interval.of(-30, -25), Interval.of(-8, 5), Interval.of(-23, -14))
        );
    }

    @Test
    void compare_withEqualIntervals() {
        assertEquals(BruteForceMerge.CompareResult.EQUALS, BruteForceMerge.compare(
                Interval.of(11, 47),
                Interval.of(11, 47)
        ));
    }


    @Test
    void compare_withOverlapAboveFirst() {
        assertEquals(BruteForceMerge.CompareResult.OVERLAP_ABOVE_FIRST, BruteForceMerge.compare(
                Interval.of(15, 20),
                Interval.of(17, 25)
        ));
        assertEquals(BruteForceMerge.CompareResult.OVERLAP_ABOVE_FIRST, BruteForceMerge.compare(
                Interval.of(15, 20),
                Interval.of(20, 25)
        ));
        assertNotEquals(BruteForceMerge.CompareResult.OVERLAP_ABOVE_FIRST, BruteForceMerge.compare(
                Interval.of(15, 20),
                Interval.of(15, 25)
        ));
    }


    @Test
    void compare_withOverlapBelowFirst() {
        assertEquals(BruteForceMerge.CompareResult.OVERLAP_BELOW_FIRST, BruteForceMerge.compare(
                Interval.of(15, 20),
                Interval.of(1, 16)
        ));
        assertEquals(BruteForceMerge.CompareResult.OVERLAP_BELOW_FIRST, BruteForceMerge.compare(
                Interval.of(15, 20),
                Interval.of(1, 15)
        ));
        assertNotEquals(BruteForceMerge.CompareResult.OVERLAP_BELOW_FIRST, BruteForceMerge.compare(
                Interval.of(15, 20),
                Interval.of(1, 20)
        ));
    }

    @Test
    void compare_withDisjunct() {
        assertEquals(BruteForceMerge.CompareResult.DISJUNCT, BruteForceMerge.compare(
                Interval.of(15, 20),
                Interval.of(45621, 545746)
        ));
        assertEquals(BruteForceMerge.CompareResult.DISJUNCT, BruteForceMerge.compare(
                Interval.of(45621, 545746),
                Interval.of(15, 20)
        ));
    }

    @Test
    void compare_withFirstInSecond() {
        assertEquals(BruteForceMerge.CompareResult.FIRST_IN_SECOND, BruteForceMerge.compare(
                Interval.of(15, 20),
                Interval.of(13, 25)
        ));
        assertEquals(BruteForceMerge.CompareResult.FIRST_IN_SECOND, BruteForceMerge.compare(
                Interval.of(15, 20),
                Interval.of(13, 20)
        ));
        assertEquals(BruteForceMerge.CompareResult.FIRST_IN_SECOND, BruteForceMerge.compare(
                Interval.of(13, 20),
                Interval.of(13, 25)
        ));
    }

    @Test
    void compare_withSecondInFirst() {
        assertEquals(BruteForceMerge.CompareResult.SECOND_IN_FIRST, BruteForceMerge.compare(
                Interval.of(15, 20),
                Interval.of(16, 19)
        ));
        assertEquals(BruteForceMerge.CompareResult.SECOND_IN_FIRST, BruteForceMerge.compare(
                Interval.of(15, 20),
                Interval.of(16, 20)
        ));
        assertEquals(BruteForceMerge.CompareResult.SECOND_IN_FIRST, BruteForceMerge.compare(
                Interval.of(16, 20),
                Interval.of(16, 19)
        ));
    }
}