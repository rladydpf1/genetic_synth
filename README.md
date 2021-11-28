# genetic synthesis

## What is the topic?
My topic is to find the best specification for program synthesis problem. Program synthesis is the task of discovering an executable program from user intent expressed in the form of specification. this specification consists of in/output examples and syntactic restrictions. I wonder if the use of genetic algorithms can search the best combination of in/output examples and syntactic restrictions.

## Justification of why you have chosen your topic.
The program synthesizer I use in this project is Eusolver, which takes in/output examples and syntactic restrictions, and finds a program that satisfies to the specification. Eusolver uses enumerative approach that gradually increases the program size of candidate solutions one by one. These candidates are program combinations that satisfy a given grammar and the candidate program size. If a candidate solution meets all examples, then Eusolver returns the solution. Otherwise, Eusolver increases the candidate program size.  

Here is an example of the synthesis problem. The target program f(x, y) = 10 \* x + y, the set of syntactic restrictions {x, y, 1, +, \*}, and the set of in/output examples {{f(1,1)=11}, {f(1,10)=11}, {f(2,2)=22}}. The candidates of the size 1 are f(x,y)=x, f(x,y)=y, and f(x,y)=1. These candidates are not satisfied with the examples, so Eusolver increases the candidate program size to 3. Then x+y, x\*y, x+1 ... are available. But the problem is that only the constant '1' can be used because of the grammer. Eusolver reluctantly generates f(x,y)=(1+1+...+1) \* x + y, which has a long code size and takes a long time to find.  

As the candidate program size increases, the number of generated candidates increases exponentially. If constant '10' was in the grammar, the target program could be synthesized quickly. However, finding the needed constant is difficult task if the target program code is not public, and you can only use the log data of the target program. The same thing is applied to the parameters and operators. This is why I am doing this project. I want to find the best combination of parameters, operations and constants. This is already NP problem even though it's not combined with selecting the in/output examples due to a combination of numerous constants.  

Now moving on to the in/output example combinations, 
