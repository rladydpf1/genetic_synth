(set-logic SLIA)

(synth-fun f ((name String)) String
 ((Start String (ntString))
  (ntString String (name "" "US" "CAN" 
		    (str.replace ntString ntString ntString)
		    (str.at ntString ntInt)
		    (int.to.str ntInt)
			(ite ntBool ntString ntString)
		    (str.substr ntString ntInt ntInt)))
  (ntInt Int (0 1 2
	      (+ ntInt ntInt)
	      (- ntInt ntInt)
	      (str.len ntString)
	      (str.to.int ntString)
	      (str.indexof ntString ntString ntInt)))
  (ntBool Bool (true false
  		(= ntInt ntInt)
		(str.prefixof ntString ntString)
		(str.suffixof ntString ntString)
		(str.contains ntString ntString)))))

(constraint (= (f "Mining US") "Mining"))
(constraint (= (f "Soybean Farming CAN") "Soybean Farming"))
(constraint (= (f "Soybean Farming") "Soybean Farming"))
(constraint (= (f "Oil Extraction US") "Oil Extraction"))
(constraint (= (f "Fishing") "Fishing"))

(check-synth)