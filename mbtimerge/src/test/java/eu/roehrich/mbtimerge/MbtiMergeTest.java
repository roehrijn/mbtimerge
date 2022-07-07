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
}