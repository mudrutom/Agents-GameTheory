\ENCODING=ISO-8859-1
\Problem name: GAME THEORY BANDITS LP

Minimize
 obj: v_{}
Subject To
 c_root:       r_{}  = 1
 c_choice:     r_{} - r_(4,3)(7,5) - r_(7,1)(7,5) - r_(7,1)(4,3)  = 0
 c_U:          v_{} - v_U >= 0
 c_D:          v_{} - v_D >= 0
 c_R:          v_{} - v_R >= 0
 c_UU:         v_U - v_UU >= 0
 c_UUR:        v_UU - v_UUR >= 0
 c_UURR:       v_UUR - v_UURR >= 0
 c_UURRR:      v_UURR - v_UURRR >= 0
 c_UURRRR:     v_UURRR - v_UURRRR >= 0
 c_UURRRRR:    v_UURRRR - v_UURRRRR >= 0
 c_UURRRRRR:   v_UURRRRR - v_UURRRRRR >= 0
 c_UURRRRRRD:  v_UURRRRRR - v_UURRRRRRD >= 0
 c_UURRRRRRDD: - 11 r_(4,3)(7,5) - 5.5 r_(7,1)(7,5) - 5.5 r_(7,1)(4,3)
               + v_UURRRRRRD >= 0
 c_DD:         v_D - v_DD >= 0
 c_DDR:        v_DD - v_DDR >= 0
 c_DDRR:       v_DDR - v_DDRR >= 0
 c_DDRRR:      v_DDRR - v_DDRRR >= 0
 c_DDRRRR:     v_DDRRR - v_DDRRRR >= 0
 c_DDRRRRR:    v_DDRRRR - v_DDRRRRR >= 0
 c_DDRRRRRR:   v_DDRRRRR - v_DDRRRRRR >= 0
 c_DDRRRRRRU:  v_DDRRRRRR - v_DDRRRRRRU >= 0
 c_DDRRRRRRUU: - 5.5 r_(4,3)(7,5) - 5.5 r_(7,1)(7,5) - 11 r_(7,1)(4,3)
               + v_DDRRRRRRU >= 0
 c_RR:         v_R - v_RR >= 0
 c_RRR:        v_RR - v_RRR >= 0
 c_RRRR:       v_RRR - v_RRRR >= 0
 c_RRRRR:      v_RRRR - v_RRRRR >= 0
 c_RRRRRR:     - 5 r_(4,3)(7,5) - 10 r_(7,1)(7,5) - 5 r_(7,1)(4,3) + v_RRRRR
               >= 0
Bounds
 0 <= r_{} <= 1
 0 <= r_(4,3)(7,5) <= 1
 0 <= r_(7,1)(7,5) <= 1
 0 <= r_(7,1)(4,3) <= 1
      v_{} >= 4.94065645841247e-324
      v_U >= 4.94065645841247e-324
      v_D >= 4.94065645841247e-324
      v_R >= 4.94065645841247e-324
      v_UU >= 4.94065645841247e-324
      v_UUR >= 4.94065645841247e-324
      v_UURR >= 4.94065645841247e-324
      v_UURRR >= 4.94065645841247e-324
      v_UURRRR >= 4.94065645841247e-324
      v_UURRRRR >= 4.94065645841247e-324
      v_UURRRRRR >= 4.94065645841247e-324
      v_UURRRRRRD >= 4.94065645841247e-324
      v_DD >= 4.94065645841247e-324
      v_DDR >= 4.94065645841247e-324
      v_DDRR >= 4.94065645841247e-324
      v_DDRRR >= 4.94065645841247e-324
      v_DDRRRR >= 4.94065645841247e-324
      v_DDRRRRR >= 4.94065645841247e-324
      v_DDRRRRRR >= 4.94065645841247e-324
      v_DDRRRRRRU >= 4.94065645841247e-324
      v_RR >= 4.94065645841247e-324
      v_RRR >= 4.94065645841247e-324
      v_RRRR >= 4.94065645841247e-324
      v_RRRRR >= 4.94065645841247e-324
End
