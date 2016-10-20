package ud.prog3.pr02;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class test {

	  Coche coche=new Coche();
	  

	
	
	@Test
	 public void testFuerzaAceleracionAtras() {
	 double[] tablaVel = { -500, -425, -300, -250, -200, -100, 0, 125, 250, 500,
	1100 };
	 double[] tablaFuerza = { 0, 0.5, 1, 1, 1, 0.65, 0.3, 0.575, 0.85, 0.85,
	0.85 };
	 for (int i=0;i<tablaVel.length;i++) {
	 coche.setVelocidad( tablaVel[i] );
	 assertEquals( "Velocidad " + tablaVel[i], tablaFuerza[i]*Coche.FuerzaBaseAtras,
	coche.fuerzaAceleracionAtras(), 0.0000001 );
	 }
	 } 

}
