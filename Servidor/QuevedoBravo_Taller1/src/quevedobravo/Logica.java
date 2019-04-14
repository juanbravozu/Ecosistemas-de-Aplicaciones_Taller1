package quevedobravo;

import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;
import processing.core.PFont;
import processing.sound.*;

public class Logica implements Observer {

	private PApplet app;
	private PFont fuente;
	private Comunicacion ref;
	private SoundFile song1;
	private String ip;
	private String nombre;
	
	/**
	 * Pantalla 0 = Pantalla ip
	 * Pantalla 1 = Introduccion
	 * Pantalla 2 = Juego
	 */
	private int pantalla;
	private int opacidad;
	private Mundo m;
	
	public Logica(PApplet app) {
		this.app = app;
		
		
		pantalla = 0;
		opacidad = 255;
		fuente = app.loadFont("pixellari-30.vlw");
		
		app.textAlign(app.CENTER, app.TOP);
		app.textFont(fuente);
		app.background(0);
		app.text("Cargando...", app.width/2, app.height/2);
		
		song1 = new SoundFile(app, "05 - Forbidden Steps.mp3");
		song1.loop();
		
		ref = Comunicacion.getRef();
		ref.addObserver(this);
		ip = ref.getIp().getHostAddress();
		m = new Mundo(app);
	}

	public void pintar() {
		app.background(0);
		switch(pantalla) {
		case 0:			
			app.fill(255, opacidad);
			app.text("Introduce tu nombre de personaje y la dirección ip para iniciar la conexión", 350, 100, 500, 700);
			app.text(ip, 600, 300);
			if(opacidad == 0) {
				pantalla = 1;
			}
			break;
			
		case 1:
			app.fill(255, opacidad);
			app.text("Existe una leyenda olvidada en el tiempo, una leyenda sobre un héroe que acabó con la oscuridad antes de que esta pudiera actuar. "+nombre+" es el nombre de este héroe.", 300, 100, 600, 700);
			if(opacidad < 255)opacidad++;
			break;
			
		case 2:
			if(!m.getIniciado()) {
				m.setIniciado(true);
			} else {
				m.pintar();
			}
		}
	}

	public void update(Observable arg0, Object arg1) {
		String mensaje[] = (String[]) arg1;
		
		if(mensaje[0].matches("Crear") && pantalla == 0) {
			nombre = mensaje[1];
			System.out.println(nombre);
			new Thread(new Runnable() {
				public void run() {
					while(true) {
						try {
							opacidad -= 2;
							System.out.println(opacidad);
							if(opacidad <= 0) {
								opacidad = 0;
								break;
							}
							Thread.sleep(17);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}).start();
		} else if(mensaje[0].matches("A")) {
			if(opacidad == 255) {
				new Thread(()->{
					while(true) {
						try {
							opacidad-=2;
							System.out.println(opacidad);
							if(opacidad<=0) {
								opacidad = 0;
								pantalla = 2;
								break;
							}
							
							Thread.sleep(17);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}	
				}).start();
			}
		}
	}

}