 { 
   LegendreP(x_, 1) = 1,
   
   LegendreP(x_NumericQ, 0) :=  Sqrt(Pi)/(Gamma((1 - x)/2) * Gamma(1 + x/2)),
   LegendreP(x_IntegerQ, -1) := (-1)^x
   
 }