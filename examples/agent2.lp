\ENCODING=ISO-8859-1
\Problem name: GAME THEORY AGENT LP

Maximize
 obj: v_{}
Subject To
 c_root:            r_{}  = 1
 c_{}:              r_{} - r_U - r_D  = 0
 c_U:               r_U - r_UU  = 0
 c_UU:              r_UU - r_UUR  = 0
 c_UUR:             r_UUR - r_UURR  = 0
 c_UURR:            r_UURR - r_UURRR  = 0
 c_UURRR:           r_UURRR - r_UURRRD - r_UURRRR  = 0
 c_UURRRD:          r_UURRRD - r_UURRRDD  = 0
 c_UURRRDD:         r_UURRRDD - r_UURRRDDD  = 0
 c_UURRRDDD:        r_UURRRDDD - r_UURRRDDDD  = 0
 c_UURRRDDDD:       r_UURRRDDDD - r_UURRRDDDDL - r_UURRRDDDDR  = 0
 c_UURRRDDDDL:      r_UURRRDDDDL - r_UURRRDDDDLL  = 0
 c_UURRRDDDDLL:     r_UURRRDDDDLL - r_UURRRDDDDLLL  = 0
 c_UURRRDDDDLLL:    r_UURRRDDDDLLL - r_UURRRDDDDLLLU  = 0
 c_UURRRDDDDR:      r_UURRRDDDDR - r_UURRRDDDDRR  = 0
 c_UURRRDDDDRR:     r_UURRRDDDDRR - r_UURRRDDDDRRR  = 0
 c_UURRRR:          r_UURRRR - r_UURRRRR  = 0
 c_UURRRRR:         r_UURRRRR - r_UURRRRRR  = 0
 c_UURRRRRR:        r_UURRRRRR - r_UURRRRRRD  = 0
 c_UURRRRRRD:       r_UURRRRRRD - r_UURRRRRRDD  = 0
 c_UURRRRRRDD:      r_UURRRRRRDD - r_UURRRRRRDDD  = 0
 c_UURRRRRRDDD:     r_UURRRRRRDDD - r_UURRRRRRDDDD  = 0
 c_D:               r_D - r_DD  = 0
 c_DD:              r_DD - r_DDR  = 0
 c_DDR:             r_DDR - r_DDRR  = 0
 c_DDRR:            r_DDRR - r_DDRRR  = 0
 c_DDRRR:           r_DDRRR - r_DDRRRU - r_DDRRRR  = 0
 c_DDRRRU:          r_DDRRRU - r_DDRRRUU  = 0
 c_DDRRRUU:         r_DDRRRUU - r_DDRRRUUU  = 0
 c_DDRRRUUU:        r_DDRRRUUU - r_DDRRRUUUU  = 0
 c_DDRRRUUUU:       r_DDRRRUUUU - r_DDRRRUUUUL - r_DDRRRUUUUR  = 0
 c_DDRRRUUUUL:      r_DDRRRUUUUL - r_DDRRRUUUULL  = 0
 c_DDRRRUUUULL:     r_DDRRRUUUULL - r_DDRRRUUUULLL  = 0
 c_DDRRRUUUULLL:    r_DDRRRUUUULLL - r_DDRRRUUUULLLD  = 0
 c_DDRRRUUUUR:      r_DDRRRUUUUR - r_DDRRRUUUURR  = 0
 c_DDRRRUUUURR:     r_DDRRRUUUURR - r_DDRRRUUUURRR  = 0
 c_DDRRRUUUURRR:    r_DDRRRUUUURRR - r_DDRRRUUUURRRD  = 0
 c_DDRRRUUUURRRD:   r_DDRRRUUUURRRD - r_DDRRRUUUURRRDD  = 0
 c_DDRRRUUUURRRDD:  r_DDRRRUUUURRRDD - r_DDRRRUUUURRRDDD  = 0
 c_DDRRRUUUURRRDDD: r_DDRRRUUUURRRDDD - r_DDRRRUUUURRRDDDD  = 0
 c_DDRRRR:          r_DDRRRR - r_DDRRRRR  = 0
 c_DDRRRRR:         r_DDRRRRR - r_DDRRRRRR  = 0
 c_(7,2)(1,5):      - 10 r_UURRRDDDDRRR - 5.5 r_UURRRRRRDDDD
                    - 2.75 r_DDRRRUUUURRRDDDD - 5 r_DDRRRRRR + v_{} <= 0
 c_(6,1)(1,5):      - 10 r_UURRRDDDDRRR - 5.5 r_UURRRRRRDDDD
                    - 2.75 r_DDRRRUUUURRRDDDD - 5 r_DDRRRRRR + v_{} <= 0
 c_(6,1)(7,2):      - 10 r_UURRRDDDDRRR - 2.75 r_UURRRRRRDDDD
                    - 2.75 r_DDRRRUUUURRRDDDD - 10 r_DDRRRRRR + v_{} <= 0
 c_(1,1)(1,5):      - 5 r_UURRRDDDDRRR - 5.5 r_UURRRRRRDDDD
                    - 5.5 r_DDRRRUUUURRRDDDD - 5 r_DDRRRRRR + v_{} <= 0
 c_(1,1)(7,2):      - 5 r_UURRRDDDDRRR - 2.75 r_UURRRRRRDDDD
                    - 5.5 r_DDRRRUUUURRRDDDD - 10 r_DDRRRRRR + v_{} <= 0
 c_(1,1)(6,1):      - 5 r_UURRRDDDDRRR - 2.75 r_UURRRRRRDDDD
                    - 5.5 r_DDRRRUUUURRRDDDD - 10 r_DDRRRRRR + v_{} <= 0
Bounds
 0 <= r_{} <= 1
 0 <= r_U <= 1
 0 <= r_D <= 1
 0 <= r_UU <= 1
 0 <= r_UUR <= 1
 0 <= r_UURR <= 1
 0 <= r_UURRR <= 1
 0 <= r_UURRRD <= 1
 0 <= r_UURRRR <= 1
 0 <= r_UURRRDD <= 1
 0 <= r_UURRRDDD <= 1
 0 <= r_UURRRDDDD <= 1
 0 <= r_UURRRDDDDL <= 1
 0 <= r_UURRRDDDDR <= 1
 0 <= r_UURRRDDDDLL <= 1
 0 <= r_UURRRDDDDLLL <= 1
 0 <= r_UURRRDDDDLLLU <= 1
 0 <= r_UURRRDDDDRR <= 1
 0 <= r_UURRRDDDDRRR <= 1
 0 <= r_UURRRRR <= 1
 0 <= r_UURRRRRR <= 1
 0 <= r_UURRRRRRD <= 1
 0 <= r_UURRRRRRDD <= 1
 0 <= r_UURRRRRRDDD <= 1
 0 <= r_UURRRRRRDDDD <= 1
 0 <= r_DD <= 1
 0 <= r_DDR <= 1
 0 <= r_DDRR <= 1
 0 <= r_DDRRR <= 1
 0 <= r_DDRRRU <= 1
 0 <= r_DDRRRR <= 1
 0 <= r_DDRRRUU <= 1
 0 <= r_DDRRRUUU <= 1
 0 <= r_DDRRRUUUU <= 1
 0 <= r_DDRRRUUUUL <= 1
 0 <= r_DDRRRUUUUR <= 1
 0 <= r_DDRRRUUUULL <= 1
 0 <= r_DDRRRUUUULLL <= 1
 0 <= r_DDRRRUUUULLLD <= 1
 0 <= r_DDRRRUUUURR <= 1
 0 <= r_DDRRRUUUURRR <= 1
 0 <= r_DDRRRUUUURRRD <= 1
 0 <= r_DDRRRUUUURRRDD <= 1
 0 <= r_DDRRRUUUURRRDDD <= 1
 0 <= r_DDRRRUUUURRRDDDD <= 1
 0 <= r_DDRRRRR <= 1
 0 <= r_DDRRRRRR <= 1
      v_{} >= 4.94065645841247e-324
End
