package eu.roehrich.mbtimerge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Merges the intervals given as a collection of @{@link Interval}s according to the specification
 * of https://docs.google.com/document/d/1FS272sy-boGQ49TBFKirIbn5YLasZi1XcyAq5NZ2uBI/edit?usp=sharing
 * using a brute force algorithm, explained in {@link #doMerge(List)}
 *
 * Expected memory footprint:
 * The collection of input intervals is copied as a first step. Algorithm needs approx. twice the
 * amount of memory of the input collection in peak.
 *
 * Expected effort:
 * Cycle of quadratic iterations which are canceled at certain criteria. Base of iterations is getting
 * smaller the more iterations are done. O(n * log n) < x < O(n * n)
 *
 * Expected runtime behavior:
 * Algorithm is single threaded. See expected effort.
 */
public class BruteForceMerge {

    private static final Logger LOGGER = LoggerFactory.getLogger(BruteForceMerge.class);

    static final IntervalComparator INTERVAL_COMPARATOR = new IntervalComparator();

    /**
     * The biggest possible input collection count (only exemplary value)
     */
    static final int INPUT_TOO_BIG_THRESHOLD = 10000;

    /**
     * Compare every interval with every other interval and merge if the combination is subject to be merged.
     * After that, start from the beginning until the loops are finished without further modifications.
     *
     * @param input The intervals to process (not modified)
     * @return the merged intervals
     */
    public static Collection<Interval> doMerge(final List<Interval> input) {

        if(input.size() > INPUT_TOO_BIG_THRESHOLD) {
            throw new IllegalArgumentException(String.format("Input size must no exceed %d but is: %d", INPUT_TOO_BIG_THRESHOLD, input.size()));
        }

        LOGGER.debug("Computing merged intervals of input: {}...", input.subList(0, Math.min(input.size(), 50))); // TODO: would be much more efficient to decorate the list with an alternative toString implementation.

        // concurrency aware Set implementation is needed otherwise the two competing iterators in the outer and inner loop
        // would cause mutual @java.util.ConcurrentModificationException when deleting or inserting objects
        var result = new ConcurrentSkipListSet<Interval>(input);

        boolean somethingChanged;
        do {
            somethingChanged = false;
            Iterator<Interval> firstIter = result.iterator();
            while (firstIter.hasNext()) {
                var first = firstIter.next();

                Iterator<Interval> secondIter = result.iterator();
                while (secondIter.hasNext()) {
                    var second = secondIter.next();

                    var compareResult = INTERVAL_COMPARATOR.compare(first, second);
                    if (compareResult == IntervalComparator.CompareResult.SECOND_IN_FIRST) {
                        somethingChanged = true;
                        secondIter.remove();
                    } else if (compareResult == IntervalComparator.CompareResult.FIRST_IN_SECOND) {
                        somethingChanged = true;
                        firstIter.remove();
                        break; // this break just reduces CPU cycles. It does not break the algorithm in case it is removed.
                    } else if (compareResult == IntervalComparator.CompareResult.OVERLAP_ABOVE_FIRST) {
                        somethingChanged = true;
                        result.add(Interval.of(first.getBegin(), second.getEnd()));
                    } else if (compareResult == IntervalComparator.CompareResult.OVERLAP_BELOW_FIRST) {
                        somethingChanged = true;
                        result.add(Interval.of(second.getBegin(), first.getEnd()));
                    }
                }
            }
        } while (somethingChanged);
        LOGGER.info("Merge finished successfully.\nInput: {}...\nResult: {}", input.subList(0, Math.min(input.size(), 50)), result);
        return result;
    }
}
