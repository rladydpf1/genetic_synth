# genetic synthesis

## What is the topic?
My topic is to find the best specification for the program synthesis problem. Program synthesis is the task of discovering an executable program from user intent expressed in the form of specification. this specification consists of in/output examples and syntactic restrictions. I wonder if the use of genetic algorithms can search for the best combination of in/output examples and syntactic restrictions.

## Justification of why you have chosen your topic.
The program synthesizer I use in this project is Eusolver, which takes in/output examples and syntactic restrictions, and finds a program that satisfies the specification. Eusolver uses an enumerative approach that gradually increases the program size of candidate solutions one by one. These candidates are program combinations that satisfy a given grammar and the candidate program size. If a candidate solution meets all in/output examples, then Eusolver returns the solution. Otherwise, Eusolver increases the candidate program size.  

Here is an example of the synthesis problem. The target program f(x, y) = 10 \* x + y, the set of syntactic restrictions {x, y, 1, +, \*}, and the set of in/output examples {{f(1,1)=11}, {f(1,10)=11}, {f(2,2)=22}}. The candidate programs of the size 1 are f(x,y)=x, f(x,y)=y, and f(x,y)=1. These are not satisfied with the examples, so Eusolver increases the candidate program size to 3. Then x+y, x\*y, x+1 ... are generated. But the problem is that only the constant '1' can be used because of the grammer. Eusolver reluctantly generates f(x,y)=(1+1+...+1) \* x + y, which has a long code size and takes a long time to find.  

As the candidate program size increases, the number of generated candidates increases exponentially. If constant '10' was in the grammar, the more efficient program could be synthesized quickly. However, finding the needed constant is a difficult task if the target program code is not public, and you can only use the log data of the target program. The same thing is applied to the parameters and operators. This is why I'm doing this project. I want to find the best combination of parameters, operations, and constants. This is already the NP problem even though it's not combined with selecting the in/output examples due to a combination of numerous constants.  

Now moving on to the in/output example combinations, It is also the NP problem because a portion of in/output examples are selected from all possible examples. This also means that the larger the input range, the greater the number of combinations will increase. If the number of examples is too small, then the synthesized program will be too different from the target program. On the contrary, if the number of examples is too large, then it will take a very long time to synthesize. After all, the goal is to synthesize programs that can be synthesized within a given time, have high accuracy, and have small code sizes.  

## Design decision explaining why you select:
### Parameters such as the size of an initial population.
It depends on the difficulty of the problem, but each synthesis problem takes a very long time compared to other problems. Still, since it uses little memory, the synthesis tasks (initial population) can be synthesized concurrently. So the size of the initial population is the number of times the synthesis is successful within a given time (ex. within one minute). it depends on hardware specifications such as CPU cores.

### Stopping criteria.
It stops if the target accuracy and code size that are given to the user are achieved, or the total execution timeout (ex. within 1 hour) is exceeded.

### Fitness function.
The accuracy of each synthesized program (individual) is calculated by executing 1,000 in/output tests. The number of uses of parameters, operators, and constants used in each synthesized program (code size) is also calculated. This may be said to be a multi-objective optimization problem. But the most important thing is accuracy, so the time we need a code size is when the accuracy is similar (ex. +/-10%).

### Selection operator.
I will use the tournament selection method.

### Crossover operator.
Before crossover, we need to collect parameters, operators, and constants used in the actual synthesized, not in the specification. In addition, a simple constant folding is also needed (ex. from 1 + 1 + 1 to 3). In this case, the constant '3' is actually used, and if the constant '1' is not used elsewhere, '1' is not actually used. Then the child only has the union of actually used parts between its parents. That is the crossover operation of syntactic restrictions. The crossover operator of in/output examples is just the union of random combinations of in/output examples in each parent.

### Mutation operator.
There are deleting in/output examples, deleting grammars, adding in/output examples, and adding grammars. These operators are randomly performed.

### Generational selection strategy.
All individuals are sorted in the order of high scores in the fitness function, and only the number of the initial population is selected as the next population.
