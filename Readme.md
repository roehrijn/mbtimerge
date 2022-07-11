# Interval merge
## Purpose
This repository contains an example implementation of an interval merging algorithm in
order to show coding skills during an interview process.

## Assumptions
* The lower and upper boundaries for the intervals is defined as java.lang.Integer with values ranging from -(2^30) to 2^30-1. This is because the length of the interval shall also be representable as (signed) Integer.
* The first number of the interval must always be smaller than the second one.
* Mutually touching intervals are considered as subject to merge

## Implementation
The algorithm has been implemented in brute-force style using an inner and outer loop
over all the values and so comparing every value with every other. There are definitely 
smarter ways to implement this but because I didn't know anything about the amount how 
ofter the algorithm is called and with which size of input data, I choose to implement 
the simplest approach at first glance.

Possible better solutions is to pre-sort the input in two ways:
* according to the lower value and interval length
* according to the upper value and interval length

Operating on those two sorted collections one full iteration per collection should be sufficient. 
Together with sorting (eg. quick-sort) the effort should be O(2*log n + 2n).

With some drawbacks it would also be possible to implement a recursive algorithm.

## Run

1. Checkout Git repository
2. Compile (you need to have Maven 3.x and Java 12+ installed): `mvn compile`
3. Execution of the tests (this executes the example): `mvn verify`

## Additional questions 
### Runtime
Please see Javadoc in eu.roehrich.mbtimerge.BruteForceMerge

### Robustness
Robustness against big input values could be achieved by throwing a fail-fast exception in 
the case of input collection size exceeding a certain threshold (has been implemented exemplarily) 
or by accepting input only a references to files / blobs and build the algorithm to only run on
certain parts of the input data (e.g. divide and conquer). 

### Memory footprint
Please see Javadoc in eu.roehrich.mbtimerge.BruteForceMerge