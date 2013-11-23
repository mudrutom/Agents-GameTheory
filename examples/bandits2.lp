\ENCODING=ISO-8859-1
\Problem name: GAME THEORY BANDITS LP

Minimize
 obj: v_{}
Subject To
 c_root:             r_{}  = 1
 c_choice:           r_{} - r_(7,2)(1,5) - r_(6,1)(1,5) - r_(6,1)(7,2)
                     - r_(1,1)(1,5) - r_(1,1)(7,2) - r_(1,1)(6,1)  = 0
 c_U:                v_{} - v_U >= 0
 c_D:                v_{} - v_D >= 0
 c_UU:               v_U - v_UU >= 0
 c_UUR:              v_UU - v_UUR >= 0
 c_UURR:             v_UUR - v_UURR >= 0
 c_UURRR:            v_UURR - v_UURRR >= 0
 c_UURRRD:           v_UURRR - v_UURRRD >= 0
 c_UURRRR:           v_UURRR - v_UURRRR >= 0
 c_UURRRDD:          v_UURRRD - v_UURRRDD >= 0
 c_UURRRDDD:         v_UURRRDD - v_UURRRDDD >= 0
 c_UURRRDDDD:        v_UURRRDDD - v_UURRRDDDD >= 0
 c_UURRRDDDDL:       v_UURRRDDDD - v_UURRRDDDDL >= 0
 c_UURRRDDDDR:       v_UURRRDDDD - v_UURRRDDDDR >= 0
 c_UURRRDDDDLL:      v_UURRRDDDDL - v_UURRRDDDDLL >= 0
 c_UURRRDDDDLLL:     v_UURRRDDDDLL - v_UURRRDDDDLLL >= 0
 c_UURRRDDDDLLLU:    v_UURRRDDDDLLL >= 0
 c_UURRRDDDDRR:      v_UURRRDDDDR - v_UURRRDDDDRR >= 0
 c_UURRRDDDDRRR:     - 10 r_(7,2)(1,5) - 10 r_(6,1)(1,5) - 10 r_(6,1)(7,2)
                     - 5 r_(1,1)(1,5) - 5 r_(1,1)(7,2) - 5 r_(1,1)(6,1)
                     + v_UURRRDDDDRR >= 0
 c_UURRRRR:          v_UURRRR - v_UURRRRR >= 0
 c_UURRRRRR:         v_UURRRRR - v_UURRRRRR >= 0
 c_UURRRRRRD:        v_UURRRRRR - v_UURRRRRRD >= 0
 c_UURRRRRRDD:       v_UURRRRRRD - v_UURRRRRRDD >= 0
 c_UURRRRRRDDD:      v_UURRRRRRDD - v_UURRRRRRDDD >= 0
 c_UURRRRRRDDDD:     - 5.5 r_(7,2)(1,5) - 5.5 r_(6,1)(1,5) - 2.75 r_(6,1)(7,2)
                     - 5.5 r_(1,1)(1,5) - 2.75 r_(1,1)(7,2) - 2.75 r_(1,1)(6,1)
                     + v_UURRRRRRDDD >= 0
 c_DD:               v_D - v_DD >= 0
 c_DDR:              v_DD - v_DDR >= 0
 c_DDRR:             v_DDR - v_DDRR >= 0
 c_DDRRR:            v_DDRR - v_DDRRR >= 0
 c_DDRRRU:           v_DDRRR - v_DDRRRU >= 0
 c_DDRRRR:           v_DDRRR - v_DDRRRR >= 0
 c_DDRRRUU:          v_DDRRRU - v_DDRRRUU >= 0
 c_DDRRRUUU:         v_DDRRRUU - v_DDRRRUUU >= 0
 c_DDRRRUUUU:        v_DDRRRUUU - v_DDRRRUUUU >= 0
 c_DDRRRUUUUL:       v_DDRRRUUUU - v_DDRRRUUUUL >= 0
 c_DDRRRUUUUR:       v_DDRRRUUUU - v_DDRRRUUUUR >= 0
 c_DDRRRUUUULL:      v_DDRRRUUUUL - v_DDRRRUUUULL >= 0
 c_DDRRRUUUULLL:     v_DDRRRUUUULL - v_DDRRRUUUULLL >= 0
 c_DDRRRUUUULLLD:    v_DDRRRUUUULLL >= 0
 c_DDRRRUUUURR:      v_DDRRRUUUUR - v_DDRRRUUUURR >= 0
 c_DDRRRUUUURRR:     v_DDRRRUUUURR - v_DDRRRUUUURRR >= 0
 c_DDRRRUUUURRRD:    v_DDRRRUUUURRR - v_DDRRRUUUURRRD >= 0
 c_DDRRRUUUURRRDD:   v_DDRRRUUUURRRD - v_DDRRRUUUURRRDD >= 0
 c_DDRRRUUUURRRDDD:  v_DDRRRUUUURRRDD - v_DDRRRUUUURRRDDD >= 0
 c_DDRRRUUUURRRDDDD: - 2.75 r_(7,2)(1,5) - 2.75 r_(6,1)(1,5)
                     - 2.75 r_(6,1)(7,2) - 5.5 r_(1,1)(1,5) - 5.5 r_(1,1)(7,2)
                     - 5.5 r_(1,1)(6,1) + v_DDRRRUUUURRRDDD >= 0
 c_DDRRRRR:          v_DDRRRR - v_DDRRRRR >= 0
 c_DDRRRRRR:         - 5 r_(7,2)(1,5) - 5 r_(6,1)(1,5) - 10 r_(6,1)(7,2)
                     - 5 r_(1,1)(1,5) - 10 r_(1,1)(7,2) - 10 r_(1,1)(6,1)
                     + v_DDRRRRR >= 0
Bounds
 0 <= r_{} <= 1
 0 <= r_(7,2)(1,5) <= 1
 0 <= r_(6,1)(1,5) <= 1
 0 <= r_(6,1)(7,2) <= 1
 0 <= r_(1,1)(1,5) <= 1
 0 <= r_(1,1)(7,2) <= 1
 0 <= r_(1,1)(6,1) <= 1
      v_{} >= 4.94065645841247e-324
      v_U >= 4.94065645841247e-324
      v_D >= 4.94065645841247e-324
      v_UU >= 4.94065645841247e-324
      v_UUR >= 4.94065645841247e-324
      v_UURR >= 4.94065645841247e-324
      v_UURRR >= 4.94065645841247e-324
      v_UURRRD >= 4.94065645841247e-324
      v_UURRRR >= 4.94065645841247e-324
      v_UURRRDD >= 4.94065645841247e-324
      v_UURRRDDD >= 4.94065645841247e-324
      v_UURRRDDDD >= 4.94065645841247e-324
      v_UURRRDDDDL >= 4.94065645841247e-324
      v_UURRRDDDDR >= 4.94065645841247e-324
      v_UURRRDDDDLL >= 4.94065645841247e-324
      v_UURRRDDDDLLL >= 4.94065645841247e-324
      v_UURRRDDDDRR >= 4.94065645841247e-324
      v_UURRRRR >= 4.94065645841247e-324
      v_UURRRRRR >= 4.94065645841247e-324
      v_UURRRRRRD >= 4.94065645841247e-324
      v_UURRRRRRDD >= 4.94065645841247e-324
      v_UURRRRRRDDD >= 4.94065645841247e-324
      v_DD >= 4.94065645841247e-324
      v_DDR >= 4.94065645841247e-324
      v_DDRR >= 4.94065645841247e-324
      v_DDRRR >= 4.94065645841247e-324
      v_DDRRRU >= 4.94065645841247e-324
      v_DDRRRR >= 4.94065645841247e-324
      v_DDRRRUU >= 4.94065645841247e-324
      v_DDRRRUUU >= 4.94065645841247e-324
      v_DDRRRUUUU >= 4.94065645841247e-324
      v_DDRRRUUUUL >= 4.94065645841247e-324
      v_DDRRRUUUUR >= 4.94065645841247e-324
      v_DDRRRUUUULL >= 4.94065645841247e-324
      v_DDRRRUUUULLL >= 4.94065645841247e-324
      v_DDRRRUUUURR >= 4.94065645841247e-324
      v_DDRRRUUUURRR >= 4.94065645841247e-324
      v_DDRRRUUUURRRD >= 4.94065645841247e-324
      v_DDRRRUUUURRRDD >= 4.94065645841247e-324
      v_DDRRRUUUURRRDDD >= 4.94065645841247e-324
      v_DDRRRRR >= 4.94065645841247e-324
End
