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
(constraint (= (f #xE992C3BA0FCC0FFF) #xE992C3BA0FCC0FFE))
(constraint (= (f #x007C3F053476BD36) #x007C3F053476BD37))
(constraint (= (f #x63AFCD35722E346E) #x63AFCD35722E346F))
(constraint (= (f #x56802F85C2EA8435) #x56802F85C2EA8434))
(constraint (= (f #x5E1D3AA146C00269) #x5E1D3AA146C00268))
(constraint (= (f #x34960A50D20A4839) #x34960A50D20A4838))
(constraint (= (f #xC21A0521614A583D) #xC21A0521614A583C))
(constraint (= (f #x10E0034183C03439) #x10E0034183C03438))
(constraint (= (f #x16029080421C283D) #x16029080421C283C))
(constraint (= (f #xD24961A1492C3C3D) #xD24961A1492C3C3C))
(constraint (= (f #x9C92FA98C62EA106) #x9C92FA98C62EA107))
(constraint (= (f #xD79D3790D22C5F85) #xD79D3790D22C5F84))
(constraint (= (f #x8DB1ECBB726A5F89) #x8DB1ECBB726A5F88))
(constraint (= (f #x43364B84B6E23AD0) #x43364B84B6E23AD1))
(constraint (= (f #xF8BB4A80108AA68E) #xF8BB4A80108AA68F))
(constraint (= (f #xED9DF6AC9555CFBB) #x126209536AAA3044))
(constraint (= (f #xB7225279FC193EB5) #x48DDAD8603E6C14A))
(constraint (= (f #xE6FF87F07249F5EA) #x1900780F8DB60A15))
(constraint (= (f #x9C443CD4C4E7366D) #x63BBC32B3B18C992))
(constraint (= (f #x9A8C0CBA46359423) #x6573F345B9CA6BDC))
(constraint (= (f #x861C2481C2D03813) #x861C2481C2D03812))
(constraint (= (f #x616968050C30B0D1) #x616968050C30B0D0))
(constraint (= (f #x30D0081816828417) #x30D0081816828416))
(constraint (= (f #xC2C2042D0A4A50D1) #xC2C2042D0A4A50D0))
(constraint (= (f #x407801A4868420D3) #x407801A4868420D2))
(constraint (= (f #x81021E040F0B0435) #x3E7CD2F9E96F79B0))
(constraint (= (f #xE021A41A40252431) #xAFCD89D89FC849B6))
(constraint (= (f #xB410F09243852031) #xF1E697249AB84FB6))
(constraint (= (f #x8481E0824B0D24B5) #x393D2F3C8F6C48F0))
(constraint (= (f #x85280E1A5083843D) #x3843EAD8873AB9A4))
(constraint (= (f #x19B6E9F6D84BE804) #x19B6E9F6D84BE805))
(constraint (= (f #xAF4FF0BE8673F404) #xAF4FF0BE8673F405))
(constraint (= (f #x8B5627A99187C58F) #x8B5627A99187C58E))
(constraint (= (f #x940202B4ABB7C0C9) #x940202B4ABB7C0C8))
(constraint (= (f #xD31384B0FBE18786) #xD31384B0FBE18787))
(constraint (= (f #x9492041C25834251) #x9492041C25834250))
(constraint (= (f #x34861E1694078219) #x34861E1694078218))
(constraint (= (f #xB4B4B43838096851) #xB4B4B43838096850))
(constraint (= (f #x0E0F02C38481685B) #x0E0F02C38481685A))
(constraint (= (f #x685850E108296093) #x685850E108296092))
(check-synth)
