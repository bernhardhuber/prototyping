# Overview 

This project implements an algorithm for calculating 
transitive closure.

# Problem description

* Precondition:
* Given groups with elements. 
* An element may be member of more than one group.

If an element is member of more than one group, merge both groups to
a single group, containing members of both groups.

* Postcondition:
* An element is member of exactly one group.
* Silblings of the element are merged too.


# Examples

# Example 1

Given 
```
g1 := { A1, A2 }
g2 := { A2, A3 }
g3 := { A4 }
```

Resulting groups
```
g1 := { A1, A2, A3 }
g3 := { A4 }
```

# Example 2

Given 
```
g1 := { A1, A2 }
g2 := { A2, A3 }
g3 := { A4 }
g4 := { A1, A4 }
```

Resulting groups
```
g1 := { A1, A2, A3, A4 }
```
# Example 3

Given 
```
g1 := { A2, A4 }
g2 := { A1 }
g3 := { A4, A6 }
g4 := { A3 }
```

Resulting groups
```
g1 := { A2, A4, A6 }
g2 := { A1 }
g4 := { A3 }
```
 
# References

* https://en.wikipedia.org/wiki/Transitive_closure#Algorithms
