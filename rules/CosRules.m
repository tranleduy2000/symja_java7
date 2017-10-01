{
Cos(0)=1,
Cos(1/12*Pi)=(1+Sqrt(3))/(2*Sqrt(2)),
Cos(1/10*Pi)=1/2*Sqrt((5+Sqrt(5))/2),
Cos(1/8*Pi)=1/2*Sqrt(2+Sqrt(2)),
Cos(1/6*Pi)=1/2*Sqrt(3),
Cos(1/5*Pi)=1/4*(Sqrt(5)+1),
Cos(1/4*Pi)=1/Sqrt(2),
Cos(3/10*Pi)=1/2*Sqrt((5-Sqrt(5))/2),
Cos(1/3*Pi)=1/2,
Cos(3/8*Pi)=1/2*Sqrt(2-Sqrt(2)),
Cos(2/5*Pi)=1/4*(Sqrt(5)-1),
Cos(5/12*Pi)=(-1+Sqrt(3))/(2*Sqrt(2)),
Cos(1/2*Pi)=0,
Cos(7/12*Pi)=-(-1+Sqrt(3))/(2*Sqrt(2)),
Cos(3/5*Pi)=-1/4*(Sqrt(5)-1),
Cos(5/8*Pi)=-1/2*Sqrt(2-Sqrt(2)),
Cos(2/3*Pi)=-1/2,
Cos(7/10*Pi)=-1/2*Sqrt((5-Sqrt(5))/2),
Cos(3/4*Pi)=-1/Sqrt(2),
Cos(4/5*Pi)=-1/4*(Sqrt(5)+1),
Cos(5/6*Pi)=-1/2*Sqrt(3),
Cos(7/8*Pi)=-1/2*Sqrt(2+Sqrt(2)),
Cos(9/10*Pi)=-1/2*Sqrt((5+Sqrt(5))/2),
Cos(11/12*Pi)=-(1+Sqrt(3))/(2*Sqrt(2)),
Cos(Pi)=-1,
Cos(13/12*Pi)=-(1+Sqrt(3))/(2*Sqrt(2)),
Cos(11/10*Pi)=-1/2*Sqrt((5+Sqrt(5))/2),
Cos(9/8*Pi)=-1/2*Sqrt(2+Sqrt(2)),
Cos(7/6*Pi)=-1/2*Sqrt(3),
Cos(6/5*Pi)=-1/4*(Sqrt(5)+1),
Cos(5/4*Pi)=-1/Sqrt(2),
Cos(13/10*Pi)=-1/2*Sqrt((5-Sqrt(5))/2),
Cos(4/3*Pi)=-1/2,
Cos(11/8*Pi)=-1/2*Sqrt(2-Sqrt(2)),
Cos(7/5*Pi)=-1/4*(Sqrt(5)-1),
Cos(17/12*Pi)=-(-1+Sqrt(3))/(2*Sqrt(2)),
Cos(3/2*Pi)=0,
Cos(19/12*Pi)=(-1+Sqrt(3))/(2*Sqrt(2)),
Cos(8/5*Pi)=1/4*(Sqrt(5)-1),
Cos(13/8*Pi)=1/2*Sqrt(2-Sqrt(2)),
Cos(5/3*Pi)=1/2,
Cos(17/10*Pi)=1/2*Sqrt((5-Sqrt(5))/2),
Cos(7/4*Pi)=1/Sqrt(2),
Cos(9/5*Pi)=1/4*(Sqrt(5)+1),
Cos(11/6*Pi)=1/2*Sqrt(3),
Cos(15/8*Pi)=1/2*Sqrt(2+Sqrt(2)),
Cos(19/10*Pi)=1/2*Sqrt((5+Sqrt(5))/2),
Cos(23/12*Pi)=(1+Sqrt(3))/(2*Sqrt(2)),
Cos(2*Pi)=1,

Cos(I)=Cosh(1),
Cos(ArcTan(x_)):=(1+x^2)^(-1/2),
Cos(Pi*x_NumberQ):=If(x<1,(-1)*Cos((1-x)*Pi),If(x<2,Cos((2-x)*Pi),Cos((x-2*Quotient(IntegerPart(x),2))*Pi)))/;x>=1/2,
Cos(ArcCos(x_)):=x,
Cos(ArcSin(x_)):=(1-x^2)^(1/2),
Cos(Sqrt(x_^2)):=Cos(x),
	 
Cos(I*Infinity)=Infinity,
Cos(-I*Infinity)=Infinity,
Cos(ComplexInfinity)=Indeterminate,

Cos(Infinity)=Interval({-1,1}),
Cos(-Infinity)=Interval({-1,1}) 
}