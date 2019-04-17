package quevedobravo;

import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.sound.*;

public class Logica implements Observer {

	private PApplet app;
	private PFont fuente;
	private PImage[] imagenes;
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
	private boolean transition;
	
	public Logica(PApplet app) {
		this.app = app;
		
		fuente = app.loadFont("pixellari-30.vlw");	
		
		app.textAlign(app.CENTER, app.TOP);
		app.textFont(fuente);
		app.background(0);
		app.text("Cargando...", app.width/2, app.height/2);
		
		pantalla = 0;
		opacidad = 255;		
		
		song1 = new SoundFile(app, "05 - Forbidden Steps.mp3");
		song1.loop();
		
		imagenes = new PImage[3];
		for (int i = 0; i < imagenes.length; i++) {
			imagenes[i] = app.loadImage("imagen"+i+".png");
		}
		
		ref = Comunicacion.getRef();
		ref.addObserver(this);
		ip = ref.getIp().getHostAddress();
		m = new Mundo(app);
		m.addObserver(this);
		
		transition = false;
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
			app.text("En el reino lejano de Tal’Vastol se cuenta una vieja leyenda. Una historia legendaria sobre el mal que acechaba el mundo y los héroes que lo combatieron. Sin embargo, solo uno de los héroes se atrevió a viajar más allá, donde el bosque sagrado cubría todo y los viajeros desprevenidos desaparecían sin rastro alguno. Allí, desafió valientemente al mal corrupto y lo desterró para siempre.", 300, 150, 600, 700);
			if(opacidad < 255 && !transition) {
				opacidad++;
				if(opacidad == 255) {
					transition = true;
				}
			}
			
			app.fill(0, 255-opacidad);
			app.image(imagenes[1], 800, app.height-60);
			app.rect(790, app.height-61, 400, 60);
			
			break;
			
		case 2:
			app.fill(255, opacidad);
			app.text("Una leyenda olvidada en el tiempo. Un héroe que desafió el bosque y devolvió la paz al reino. "+nombre+" es el nombre de este héroe. Su historia se conoce como..." , 300, 250, 600, 700);
			if(opacidad < 255 && !transition) {
				opacidad++;
				if(opacidad == 255) {
					transition = true;
				}
			}
			
			app.fill(0, 255-opacidad);
			app.image(imagenes[1], 800, app.height-60);
			app.rect(790, app.height-61, 400, 60);
			break;
			
		case 3:
			
			break;
			
		case 4:
			if(!m.getIniciado()) {
				m.setIniciado(true);
			} else {
				m.pintar();
			}
			break;
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
			if(pantalla==1) {
				new Thread(()->{
					while(true) {
						try {
							if(opacidad<=255) {
								opacidad-=2;
								System.out.println("Está bajando");
							}
							if(opacidad<=0) {
								System.out.println("Pasó");
								opacidad = 0;
								pantalla = 2;
								transition = false;
								break;
							}
							Thread.sleep(17);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}	
				}).start();
			} else if(pantalla == 2) {
				new Thread(()->{
					while(true) {
						try {
							if(opacidad<=255) {
								opacidad-=2;
								System.out.println("Está bajando");
							}
							if(opacidad<=0) {
								System.out.println("Pasó");
								opacidad = 0;
								pantalla = 3;
								transition = false;
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