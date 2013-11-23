\ENCODING=ISO-8859-1
\Problem name: GAME THEORY AGENT LP

Maximize
 obj: v_{}
Subject To
 c_root:       r_{}  = 1
 c_{}:         r_{} - r_U - r_D - r_R  = 0
 c_U:          r_U - r_UU  = 0
 c_UU:         r_UU - r_UUR  = 0
 c_UUR:        r_UUR - r_UURR  = 0
 c_UURR:       r_UURR - r_UURRR  = 0
 c_UURRR:      r_UURRR - r_UURRRR  = 0
 c_UURRRR:     r_UURRRR - r_UURRRRR  = 0
 c_UURRRRR:    r_UURRRRR - r_UURRRRRR  = 0
 c_UURRRRRR:   r_UURRRRRR - r_UURRRRRRD  = 0
 c_UURRRRRRD:  r_UURRRRRRD - r_UURRRRRRDD  = 0
 c_D:          r_D - r_DD  = 0
 c_DD:         r_DD - r_DDR  = 0
 c_DDR:        r_DDR - r_DDRR  = 0
 c_DDRR:       r_DDRR - r_DDRRR  = 0
 c_DDRRR:      r_DDRRR - r_DDRRRR  = 0
 c_DDRRRR:     r_DDRRRR - r_DDRRRRR  = 0
 c_DDRRRRR:    r_DDRRRRR - r_DDRRRRRR  = 0
 c_DDRRRRRR:   r_DDRRRRRR - r_DDRRRRRRU  = 0
 c_DDRRRRRRU:  r_DDRRRRRRU - r_DDRRRRRRUU  = 0
 c_R:          r_R - r_RR  = 0
 c_RR:         r_RR - r_RRR  = 0
 c_RRR:        r_RRR - r_RRRR  = 0
 c_RRRR:       r_RRRR - r_RRRRR  = 0
 c_RRRRR:      r_RRRRR - r_RRRRRR  = 0
 c_(4,3)(7,5): - 11 r_UURRRRRRDD - 5.5 r_DDRRRRRRUU - 5 r_RRRRRR + v_{} <= 0
 c_(7,1)(7,5): - 5.5 r_UURRRRRRDD - 5.5 r_DDRRRRRRUU - 10 r_RRRRRR + v_{} <= 0
 c_(7,1)(4,3): - 5.5 r_UURRRRRRDD - 11 r_DDRRRRRRUU - 5 r_RRRRRR + v_{} <= 0
Bounds
 0 <= r_{} <= 1
 0 <= r_U <= 1
 0 <= r_D <= 1
 0 <= r_R <= 1
 0 <= r_UU <= 1
 0 <= r_UUR <= 1
 0 <= r_UURR <= 1
 0 <= r_UURRR <= 1
 0 <= r_UURRRR <= 1
 0 <= r_UURRRRR <= 1
 0 <= r_UURRRRRR <= 1
 0 <= r_UURRRRRRD <= 1
 0 <= r_UURRRRRRDD <= 1
 0 <= r_DD <= 1
 0 <= r_DDR <= 1
 0 <= r_DDRR <= 1
 0 <= r_DDRRR <= 1
 0 <= r_DDRRRR <= 1
 0 <= r_DDRRRRR <= 1
 0 <= r_DDRRRRRR <= 1
 0 <= r_DDRRRRRRU <= 1
 0 <= r_DDRRRRRRUU <= 1
 0 <= r_RR <= 1
 0 <= r_RRR <= 1
 0 <= r_RRRR <= 1
 0 <= r_RRRRR <= 1
 0 <= r_RRRRRR <= 1
      v_{} >= 4.94065645841247e-324
End
