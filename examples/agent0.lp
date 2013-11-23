\ENCODING=ISO-8859-1
\Problem name: GAME THEORY AGENT LP

Maximize
 obj: v_{}
Subject To
 c_root:  r_{}  = 1
 c_{}:    r_{} - r_U - r_R  = 0
 c_U:     r_U - r_UR  = 0
 c_UR:    r_UR - r_URD - r_URR  = 0
 c_URD:   r_URD - r_URDR  = 0
 c_URR:   r_URR - r_URRD  = 0
 c_R:     r_R - r_RU - r_RR  = 0
 c_RU:    r_RU - r_RUL - r_RUR  = 0
 c_RUR:   r_RUR - r_RURD  = 0
 c_(2,2): - 5.5 r_URDR - 11 r_URRD - 5 r_RR - 5 r_RURD + v_{} <= 0
 c_(3,1): - 11 r_URDR - 5.5 r_URRD - 10 r_RR - 5 r_RURD + v_{} <= 0
Bounds
 0 <= r_{} <= 1
 0 <= r_U <= 1
 0 <= r_R <= 1
 0 <= r_UR <= 1
 0 <= r_URD <= 1
 0 <= r_URR <= 1
 0 <= r_URDR <= 1
 0 <= r_URRD <= 1
 0 <= r_RU <= 1
 0 <= r_RR <= 1
 0 <= r_RUL <= 1
 0 <= r_RUR <= 1
 0 <= r_RURD <= 1
      v_{} >= 4.94065645841247e-324
End
