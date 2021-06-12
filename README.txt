Read Me file for COMPX301 assignment 3 

Authors:
    Narid Drake - 1363139
    Alessandra Macdonald - 1506517


The grammar we used for our REcompile is as follows:

E -> T
E -> T|E

T -> F
T -> F*
T -> F+
T -> F?
T -> FT


F -> .
F -> any non-operator character
F -> \any symbol
F -> [S]
F -> []S]
F -> (E)

S -> I
S -> IS

I -> any symbol other than ']'



Our REcompile will attempt to compile as much of the given regex as it can. If there is more 
of the regex that it was unable to compile it will only pass what was correctly compiled on to 
the REsearch. If this happens an error message is printed to the console telling the user that 
not all of the regex was able to be compiled and it prints out what it did manage to compile. 
This means that things such as a*)b will compile as a* rather than not compiling at all.  