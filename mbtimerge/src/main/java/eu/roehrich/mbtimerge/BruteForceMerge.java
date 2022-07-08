package eu.roehrich.mbtimerge;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Merges the intervals given as a collection of @{@link Interval}s according to the specification
 * of https://docs.google.com/document/d/1FS272sy-boGQ49TBFKirIbn5YLasZi1XcyAq5NZ2uBI/edit?usp=sharing
 * using a brute force algorithm, explained in {@link #doMerge(Collection)}
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

    enum CompareResult {
        FIRST_IN_SECOND,
        SECOND_IN_FIRST,
        EQUALS,
        OVERLAP_ABOVE_FIRST,
        OVERLAP_BELOW_FIRST,
        DISJUNCT
    }

    /**
     * Compare every interval with every other interval and merge if the combination is subject to be merged.
     * After that, start from the beginning until the loops are finished without further modifications.
     *
     * @param input The intervals to process (not modified)
     * @return the merged intervals
     */
    public static Collection<Interval> doMerge(final Collection<Interval> input) {

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

                    var compareResult = compare(first, second);
                    if (compareResult == CompareResult.SECOND_IN_FIRST) {
                        somethingChanged = true;
                        secondIter.remove();
                    } else if (compareResult == CompareResult.FIRST_IN_SECOND) {
                        somethingChanged = true;
                        firstIter.remove();
                        break; // this break just reduces CPU cycles. It does not break the algorithm in case it is removed.
                    } else if (compareResult == CompareResult.OVERLAP_ABOVE_FIRST) {
                        somethingChanged = true;
                        result.add(Interval.of(first.getBegin(), second.getEnd()));
                    } else if (compareResult == CompareResult.OVERLAP_BELOW_FIRST) {
                        somethingChanged = true;
                        result.add(Interval.of(second.getBegin(), first.getEnd()));
                    }
                }
            }
        } while (somethingChanged);
        return result;
    }

    // Assumption: Touching intervals are considered as overlapping
    static CompareResult compare(Interval first, Interval second) {
        if(first == second || first.equals(second)) {
            return CompareResult.EQUALS;
        }
        // first:   |--------|
        // second:               |--------------|
        if(first.getEnd() < second.getBegin()) {
            return CompareResult.DISJUNCT;
        }
        // first:                        |--------|
        // second:    |--------------|
        if(first.getBegin() > second.getEnd()) {
            return CompareResult.DISJUNCT;
        }
        // first:   |---------------|
        // second:    |----------|
        if(first.getBegin() <= second.getBegin() && first.getEnd() >= second.getEnd()) {
            return CompareResult.SECOND_IN_FIRST;
        }
        // first:      |-------|
        // second:    |----------|
        if(first.getBegin() >= second.getBegin() && first.getEnd() <= second.getEnd()) {
            return CompareResult.FIRST_IN_SECOND;
        }
        // first:   |--------|
        // second:       |--------------|
        if(second.getBegin() >= first.getBegin() && second.getBegin() <= first.getEnd() && second.getEnd() > first.getEnd()) {
            return CompareResult.OVERLAP_ABOVE_FIRST;
        }
        // first:                    |--------|
        // second:       |--------------|
        if(second.getEnd() >= first.getBegin() && second.getEnd() <= first.getEnd() && second.getBegin() < first.getBegin()) {
            return CompareResult.OVERLAP_BELOW_FIRST;
        }
        throw new IllegalStateException(String.format("Execution should never reach this line. First: %s, Second: %s", first, second));
    }


}
