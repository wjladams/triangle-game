# triangle-game
Little project to solve the triangle peg board game, written in Java

# Required environment

* Java 8 or greater.  On ubuntu/debian linux systems `sudo apt install openjdk-8-jdk`.
You can also install later versions.
* Maven (just search for maven install YOUR_OPERATING_SYSTEM).  On ubuntu/debian
based linux systems, simply install `sudo apt install maven`

# Running

```
cd REPO/java
mvn exec:java
```

The output looks like:
```
8
false
false false
true true true
false false false false
true true true true true
Move: 0
X
o o
o o o
o o o o
o o o o o
Move: 1
E
o J
o o S
o o o o
o o o o o
```
This means:

1. We found a set of moves to leave 8 items.
2. The final state of the board is next, where false=no_peg, and true=peg
3. Move: 0 is the starting state, and the X indicates the open whole to leave
4. Move: 1 is the first jump, S indicates the the starting place of the peg to jump.
E is the ending place, and J is the peg jumped over, and the one that should be removed.
