(set-logic BV)

(define-fun shr1 ((x (BitVec 64))) (BitVec 64) (bvlshr x #x0000000000000001))
(define-fun shr4 ((x (BitVec 64))) (BitVec 64) (bvlshr x #x0000000000000004))
(define-fun shr16 ((x (BitVec 64))) (BitVec 64) (bvlshr x #x0000000000000010))
(define-fun shl1 ((x (BitVec 64))) (BitVec 64) (bvshl x #x0000000000000001))
(define-fun if0 ((x (BitVec 64)) (y (BitVec 64)) (z (BitVec 64))) (BitVec 64) (ite (= x #x0000000000000001) y z))

(synth-fun f ( (x (BitVec 64))) (BitVec 64)
(

(Start (BitVec 64) (#x0000000000000000 #x0000000000000001 x (bvnot Start)
                    (shl1 Start)
 		    (shr1 Start)
		    (shr4 Start)
		    (shr16 Start)
		    (bvand Start Start)
		    (bvor Start Start)
		    (bvxor Start Start)
		    (bvadd Start Start)
		    (if0 Start Start Start)
 ))
)
)
(constraint (= (f #xC160FADF15BBA4D9) #x0000C160FADF15BB))
(constraint (= (f #xFA880AF4BD986219) #x0000FA880AF4BD98))
(constraint (= (f #xAFC1504171D1D9AE) #x0000AFC1504171D1))
(constraint (= (f #xD89FB9B7B849EC72) #x0000D89FB9B7B849))
(constraint (= (f #xAE236E8DE5C710A1) #x0000AE236E8DE5C7))
(constraint (= (f #x4D47BB516F2480B9) #x00004D47BB516F24))
(constraint (= (f #xCE6B9BA81F774876) #x0000CE6B9BA81F77))
(constraint (= (f #xF3B569074FA32BD2) #x0000F3B569074FA3))
(constraint (= (f #xDA441C0D1AD7CAD4) #x0000DA441C0D1AD7))
(constraint (= (f #x698001F783BCC260) #x0000698001F783BC))
(constraint (= (f #xFFFFFFFFFFFFFFFF) #x00007FFFFFFFFFFF))
(constraint (= (f #xFFFFFFFFFFFFFFFE) #x0000FFFFFFFFFFFF))
(constraint (= (f #xA873C1413D13F651) #x0000A873C1413D13))
(constraint (= (f #xD4FB8B6F1D89C08E) #x0000D4FB8B6F1D89))
(constraint (= (f #x1CEC50FF5BBE42C8) #x00001CEC50FF5BBE))
(constraint (= (f #x34D231B112AC1B86) #x000034D231B112AC))
(constraint (= (f #x23A435EBE63AA6C6) #x000023A435EBE63A))
(constraint (= (f #x4A006F152E6E015D) #x00004A006F152E6E))
(constraint (= (f #x0DA59FDF2B408916) #x00000DA59FDF2B40))
(constraint (= (f #xCD0F942177F13723) #x0000CD0F942177F1))
(constraint (= (f #x674C15CA8EDED994) #x0000674C15CA8EDE))
(constraint (= (f #xFE236A95D116965C) #x0000FE236A95D116))
(constraint (= (f #xFFFFFFFFFFFFFFFF) #x00007FFFFFFFFFFF))
(constraint (= (f #x7FFFFFFFFFFFFFFF) #x00007FFFFFFFFFFF))
(constraint (= (f #xFFFFFFFFFFFFFFFE) #x0000FFFFFFFFFFFF))
(check-synth)
