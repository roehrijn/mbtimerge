package eu.roehrich.mbtimerge;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class BruteForceMergeTest {

    @Test
    void doMerge() {

        // Check example from the original task
        Assertions.assertThat(
                BruteForceMerge.doMerge(Arrays.asList(
                        Interval.of(25, 30),
                        Interval.of(2, 19),
                        Interval.of(14, 23),
                        Interval.of(4, 8)
                ))
        ).hasSameElementsAs(
                Arrays.asList(Interval.of(25, 30), Interval.of(2, 23))
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
    void doMerge_withTooBigInput() {
        var inputOkElementCount = new ArrayList<Interval>(BruteForceMerge.INPUT_TOO_BIG_THRESHOLD);
        for(int i = 0; i < 10000; i++) {
            inputOkElementCount.add(Interval.of(0, i + 1));
        }
        assertDoesNotThrow(() -> BruteForceMerge.doMerge(inputOkElementCount));

        var inputTooBigElementcount = new ArrayList<Interval>(BruteForceMerge.INPUT_TOO_BIG_THRESHOLD + 1);
        inputTooBigElementcount.addAll(inputOkElementCount);
        inputTooBigElementcount.add(Interval.of(BruteForceMerge.INPUT_TOO_BIG_THRESHOLD, BruteForceMerge.INPUT_TOO_BIG_THRESHOLD + 1));

        assertThrows(IllegalArgumentException.class, () -> BruteForceMerge.doMerge(inputTooBigElementcount));
    }
}