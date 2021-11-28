# genetic synthesis

## What is the topic?
My topic is to find the best specification to improve the performance of program synthesis. Program synthesis is the task of discovering an executable program from user intent expressed in the form of specification. this specification consists of in/output examples and syntactic restrictions. I wonder if the use of genetic algorithms can search the best combination of in/output examples and syntactic restrictions.

## Justification of why you have chosen your topic.
The program synthesizer I use in this project is Eusolver, which takes in/output examples and syntactic restrictions, and finds a program that satisfies to the specification. Eusolver uses enumerative approach that gradually increases the size of candidate solutions one by one. These candidates are program combinations that satisfy a given grammar and program size. If a candidate solution meets all examples, then Eusolver returns the solution. Otherwise, Eusolver increases the size of candidate programs.  

Here is an example of the synthesis problem. The target program f(x, y) = 10 \* x + y, the set of syntactic restrictions {x, y, 1, +, \*}, and the set of in/output examples {{f(1,1)=11}, {f(1,10)=11}, {f(2,2)=22}}. The candidates of the size 1 are f(x,y)=x, f(x,y)=y, and f(x,y)=1. These candidates are not satisfied with the examples, so Eusolver increases the size to 3. Then x+y, x\*y, x+1 ... are available. The problem is that only the constant '1' can be used because of the grammer. Eusolver reluctantly generates f(x,y)=(1+1+...+1) \* x + y, which has a long code size and takes a long time to find.
