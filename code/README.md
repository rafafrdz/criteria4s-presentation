# Different Criteria4s approach implementations

This repository contains different implementations (for every approach) of the Criteria4s library.

## Approaches

- [Approach 1](src/main/scala/io/github/rafafrdz/criteria4s/presentation/firstapproach): The first approach is a simple
  implementation of the Criteria4s library using the `Criteria` trait or singleton object and String types for the
  criteria.
- [Approach 2](src/main/scala/io/github/rafafrdz/criteria4s/presentation/secondapproach): The second approach is a
  implementation of the Criteria4s library defining the expressions as ADTs.
- [Approach 3](src/main/scala/io/github/rafafrdz/criteria4s/presentation/thirdapproach): The third approach is a
  implementation of the Criteria4s library defining the expressions as types classes.
- [Final Approach](src/main/scala/io/github/rafafrdz/criteria4s/presentation/finalapproach): The fourth approach is a
  implementation of the Criteria4s library defining the expressions as types classes (approach 3) and using
  the `CriteriaTag` in order to restrict the criteria to a specific type.