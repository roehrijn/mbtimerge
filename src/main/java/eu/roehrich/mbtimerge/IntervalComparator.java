package eu.roehrich.mbtimerge;

/**
 * Provides a 6-way compare method which returns a value of @{@link CompareResult}.
 */
public class IntervalComparator {

    /**
     * 6-way compare
     *
     * @param first First @{@link Interval} to compare
     * @param second Second @{@link Interval} to compare
     * @return instance of @{@link CompareResult} depending on the relationshipf of the two compared intervals
     */
    public CompareResult compare(Interval first, Interval second) {
        if(first == null || second == null) {
            throw new IllegalArgumentException("Arguments must not be null");
        }

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

    enum CompareResult {
        FIRST_IN_SECOND,
        SECOND_IN_FIRST,
        EQUALS,
        OVERLAP_ABOVE_FIRST,
        OVERLAP_BELOW_FIRST,
        DISJUNCT
    }
}
