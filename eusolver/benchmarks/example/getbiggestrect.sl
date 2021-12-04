(set-logic LIA)

(synth-fun numFriendRequests ( (arg_0_ Int) (arg_1_ Int) (arg_2_ Int)) Int
((Start Int (
	-1 0 1 2 3 5 7
	arg_0_ arg_1_ arg_2_
	(+ Start Start)
	(- Start Start)
	(* Start Start)
	(/ Start Start)
	(ite StartBool Start Start)
))
(StartBool Bool (
	(or StartBool StartBool)
	(and StartBool StartBool)
	(not StartBool)
	(= Start Start)
	(< Start Start)
	(>= Start Start)
)) ))

(constraint (= (numFriendRequests 98 117 50 ) 1))
(constraint (= (numFriendRequests 108 114 43 ) 1))
(constraint (= (numFriendRequests 58 114 93 ) 2))
(check-synth)