package eu.roehrich.mbtimerge;

import org.javatuples.Pair;

import javax.xml.validation.Validator;
import java.util.Collection;
import java.util.Enumeration;

public class MbtiMerge {

    enum CompareResult {
        FIRST_IN_SECOND,
        SECOND_IN_FIRST,
        EQUALS,
        OVERLAP_ABOVE_FIRST,
        OVERLAP_BELOW_FIRST,
        DISJUNCT
    }

    public static Collection<Pair<Integer, Integer>> doMerge(Collection<Pair<Integer, Integer>> input) {



        throw new IllegalStateException("not yet implemented");
    }

    // Assumption: Touching intervals are considered as overlapping
    static CompareResult compare(Pair<Integer, Integer> first, Pair<Integer, Integer> second) {
        if(first.equals(second)) {
            return CompareResult.EQUALS;
        }
        // first:   |--------|
        // second:               |--------------|
        if(first.getValue1() < second.getValue0()) {
            return CompareResult.DISJUNCT;
        }
        // first:                        |--------|
        // second:    |--------------|
        if(first.getValue0() > second.getValue1()) {
            return CompareResult.DISJUNCT;
        }
        if(first.getValue0() <= second.getValue0() && first.getValue1() >= second.getValue1()) {
            return CompareResult.SECOND_IN_FIRST;
        }
        if(first.getValue0() >= second.getValue0() && first.getValue1() <= second.getValue1()) {
            return CompareResult.FIRST_IN_SECOND;
        }
        // first:   |--------|
        // second:       |--------------|
        if(second.getValue0() >= first.getValue0() && second.getValue0() <= first.getValue1() && second.getValue1() > first.getValue1()) {
            return CompareResult.OVERLAP_ABOVE_FIRST;
        }
        // first:                    |--------|
        // second:       |--------------|
        if(second.getValue1() >= first.getValue0() && second.getValue1() <= first.getValue1() && second.getValue0() < first.getValue0()) {
            return CompareResult.OVERLAP_BELOW_FIRST;
        }
        throw new IllegalStateException(String.format("Execution should never reach this line. First: %s, Second: %s", first, second));
    }


}
