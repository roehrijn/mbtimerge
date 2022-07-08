package eu.roehrich.mbtimerge;

import java.util.Objects;

/**
 * Represents an interval of integers with some convenience code around.
 */
public final class Interval implements Comparable<Interval> {

    private final Integer begin;

    private final Integer end;

    public static Interval of(final Integer begin, final Integer end) {
        return new Interval(begin, end);
    }

    public Interval(Integer begin, Integer end) {
        if(begin >= end) {
            throw new IllegalArgumentException(String.format("begin < end required, but got [%d, %d]", begin, end));
        }
        this.begin = begin;
        this.end = end;
    }

    public Integer getBegin() {
        return begin;
    }

    public Integer getEnd() {
        return end;
    }

    public Integer length() {
        return end - begin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Interval interval = (Interval) o;
        return Objects.equals(begin, interval.begin) && Objects.equals(end, interval.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(begin, end);
    }


    /*
        To use @java.util.concurrent.ConcurrentSkipListSet it is very important that
        compare results are always the same, regardless of compare order.
     */
    @Override
    public int compareTo(final Interval o) {
        if(begin.compareTo(o.getBegin()) == 0 && end.compareTo(o.getEnd()) == 0) {
            return 0;
        }
        if(begin.compareTo(o.getBegin()) == 0) {
            return this.length().compareTo(o.length());
        }
        return begin.compareTo(o.getBegin());
    }

    @Override
    public String toString() {
        return "[" +begin + ", " + end + ']';
    }
}
