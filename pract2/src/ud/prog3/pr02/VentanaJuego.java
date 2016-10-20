package ud.prog3.pr02;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.*;

import javax.swing.*;

/** Clase principal de minijuego de coche para Práctica 02 - Prog III
 * Ventana del minijuego.
 * @author Andoni Eguíluz
 * Facultad de Ingeniería - Universidad de Deusto (2014)
 */
public class VentanaJuego extends JFrame {
	private static final long serialVersionUID = 1L;  // Para serialización
	JPanel pPrincipal;         // Panel del juego (layout nulo)
	MundoJuego miMundo;        // Mundo del juego
	CocheJuego miCoche;        // Coche del juego
	MiRunnable miHilo = null;  // Hilo del bucle principal de juego	
	public static boolean[] flechas=new boolean[4];//Array de booleanos que se actualizara para el movimiento
	JLabel lMensaje;

	/** Constructor de la ventana de juego. Crea y devuelve la ventana inicializada
	 * sin coches dentro
	 */
	public VentanaJuego() {
		// Liberación de la ventana por defecto al cerrar
		setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		// Creación contenedores y componentes
		pPrincipal = new JPanel();
		this.lMensaje = new JLabel(" ");
		JPanel pBotonera = new JPanel();
		pBotonera.add(this.lMensaje);
		
		

		// Formato y layouts
		pPrincipal.setLayout( null );
		pPrincipal.setBackground( Color.white );
		// Añadido de componentes a contenedores
		add( pPrincipal, BorderLayout.CENTER );
		add( pBotonera, BorderLayout.SOUTH );
		// Formato de ventana
		setSize( 1000, 750 );
		setResizable( false );
		
	
		
		// Añadido para que también se gestione por teclado con el KeyListener
		pPrincipal.addKeyListener( new KeyAdapter() {
			
			@Override
			public void keyPressed(KeyEvent e) {
				
				//0=arriba
				//1-abajo
				//2-izquierda
				//3-derecha
				
				


				switch (e.getKeyCode()) {
				
				case KeyEvent.VK_UP: {
					flechas[0]=true;
					break;
				}
				case KeyEvent.VK_DOWN: {
					flechas[1]=true;
					break;

				}
				case KeyEvent.VK_LEFT: {
					flechas[2]=true;
					
					break;
				}
				case KeyEvent.VK_RIGHT: {
					flechas[3]=true;
					
					break;
				}
			}
				
				
				
				/*
				switch (e.getKeyCode()) {
					case KeyEvent.VK_UP: {
						miCoche.acelera( +5, 1 );
						break;
					}
					case KeyEvent.VK_DOWN: {
						miCoche.acelera( -5, 1 );
						break;
					}
					case KeyEvent.VK_LEFT: {
						miCoche.gira( +10 );
						break;
					}
					case KeyEvent.VK_RIGHT: {
						miCoche.gira( -10 );
						break;
					}
				}*/
			}
			
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
				
				case KeyEvent.VK_UP: {
					flechas[0]=false;
					break;
				}
				case KeyEvent.VK_DOWN: {
					flechas[1]=false;
					break;

				}
				case KeyEvent.VK_LEFT: {
					flechas[2]=false;
					
					break;
				}
				case KeyEvent.VK_RIGHT: {
					flechas[3]=false;
					
					break;
				}
			}
			}
		});
		
		
		
		
		
		
		
		pPrincipal.setFocusable(true);
		pPrincipal.requestFocus();
		pPrincipal.addFocusListener( new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				pPrincipal.requestFocus();
			}
		});
		// Cierre del hilo al cierre de la ventana
		addWindowListener( new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (miHilo!=null) miHilo.acaba();
			}
		});
	}
	
	/** Programa principal de la ventana de juego
	 * @param args
	 */
	public static void main(String[] args) {
		// Crea y visibiliza la ventana con el coche
		try {
			final VentanaJuego miVentana = new VentanaJuego();
			SwingUtilities.invokeAndWait( new Runnable() {
				@Override
				public void run() {
					miVentana.setVisible( true );
				}
			});
			miVentana.miMundo = new MundoJuego( miVentana.pPrincipal );
			miVentana.miMundo.creaCoche( 150, 100 );
			miVentana.miCoche = miVentana.miMundo.getCoche();
			miVentana.miCoche.setPiloto( "Fernando Alonso" );
			// Crea el hilo de movimiento del coche y lo lanza
			miVentana.miHilo = miVentana.new MiRunnable();  // Sintaxis de new para clase interna
			Thread nuevoHilo = new Thread( miVentana.miHilo );
			nuevoHilo.start();
		} catch (Exception e) {
			System.exit(1);  // Error anormal
		}
	}
	
	/** Clase interna para implementación de bucle principal del juego como un hilo
	 * @author Andoni Eguíluz
	 * Facultad de Ingeniería - Universidad de Deusto (2014)
	 */
	class MiRunnable implements Runnable {
		boolean sigo = true;
		@Override
		public void run() {
			// Bucle principal forever hasta que se pare el juego...
			while (sigo) {
				// Mover coche
				miCoche.mueve( 0.040 );
				
				 double fuerzaAceleracion=0;
			       
			    
			    
				if(flechas[0]==true){fuerzaAceleracion = miCoche.fuerzaAceleracionAdelante();}
				
				if(flechas[1]==true){fuerzaAceleracion = -VentanaJuego.this.miCoche.fuerzaAceleracionAtras();}
				
				 MundoJuego.aplicarFuerza(fuerzaAceleracion,miCoche);
				 
				 
				if(flechas[2]==true){miCoche.gira( +10 );}
				
				
				if(flechas[3]==true){miCoche.gira( -10 );}
				
				
				 int estrellasPerdidas = miMundo.quitaYRotaEstrellas(6000);
			        if (estrellasPerdidas > 0)
			        {
			          String mensaje = "Puntos: "+miMundo.getPuntos();
			          mensaje = mensaje + "  -  ESTRELLAS PERDIDAS: "+miMundo.getEstrellasPerdidas();
			          lMensaje.setText(mensaje);
			        }
			        VentanaJuego.this.miMundo.creaEstrella();
			        int choquesEstrellas = VentanaJuego.this.miMundo.choquesConEstrellas();
			        if (choquesEstrellas > 0)
			        {
			          String mensaje = "Puntos: " + VentanaJuego.this.miMundo.getPuntos();
			         lMensaje.setText(mensaje);
			        }
				
				
				
				// Chequear choques
				// (se comprueba tanto X como Y porque podría a la vez chocar en las dos direcciones (esquinas)
				if (miMundo.hayChoqueHorizontal(miCoche)) // Espejo horizontal si choca en X
					miMundo.rebotaHorizontal(miCoche);
				if (miMundo.hayChoqueVertical(miCoche)) // Espejo vertical si choca en Y
					miMundo.rebotaVertical(miCoche);
				
				
				
				 if (MundoJuego.fin())
			        {
			          sigo = false;
			          lMensaje.setText("SE ACABO EL JUEGO!!! Has sacado " + miMundo.getPuntos() + " puntos.");
			          try
			          {
			            Thread.sleep(8000);
			          }
			          catch (Exception localException) {}
			          VentanaJuego.this.dispose();
			        }
				// Dormir el hilo 40 milisegundos
				try {
					Thread.sleep( 40 );
				} catch (Exception e) {}
				}
				
			
		}
		/** Ordena al hilo detenerse en cuanto sea posible
		 */
		public void acaba() {
			sigo = false;
		}
	};
	
}
