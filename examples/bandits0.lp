\ENCODING=ISO-8859-1
\Problem name: GAME THEORY BANDITS LP

Minimize
 obj: v_{}
Subject To
 c_root:   r_{}  = 1
 c_choice: r_{} - r_(2,2) - r_(3,1)  = 0
 c_U:      v_{} - v_U >= 0
 c_R:      v_{} - v_R >= 0
 c_UR:     v_U - v_UR >= 0
 c_URD:    v_UR - v_URD >= 0
 c_URR:    v_UR - v_URR >= 0
 c_URDR:   - 5.5 r_(2,2) - 11 r_(3,1) + v_URD >= 0
 c_URRD:   - 11 r_(2,2) - 5.5 r_(3,1) + v_URR >= 0
 c_RU:     v_R - v_RU >= 0
 c_RUR:    v_RU - v_RUR >= 0
 c_RUL:    v_RU >= 0
 c_RURD:   - 5 r_(2,2) - 5 r_(3,1) + v_RUR >= 0
 c_RR:     - 5 r_(2,2) - 10 r_(3,1) + v_R >= 0
Bounds
 0 <= r_{} <= 1
 0 <= r_(2,2) <= 1
 0 <= r_(3,1) <= 1
      v_{} >= 4.94065645841247e-324
      v_U >= 4.94065645841247e-324
      v_R >= 4.94065645841247e-324
      v_UR >= 4.94065645841247e-324
      v_URD >= 4.94065645841247e-324
      v_URR >= 4.94065645841247e-324
      v_RU >= 4.94065645841247e-324
      v_RUR >= 4.94065645841247e-324
End
