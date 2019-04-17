package quevedobravo;

import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;
import processing.sound.*;

import static java.nio.charset.StandardCharsets.*;

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
	private int opacidadDos;
	private Mundo m;
	private boolean transition;
	private int puntos;
	
	public Logica(PApplet app) {
		this.app = app;
		
		fuente = app.loadFont("pixellari-30.vlw");	
		
		app.textAlign(app.CENTER, app.TOP);
		app.textFont(fuente);
		app.background(0);
		app.text("Cargando...", app.width/2, app.height/2);
		
		pantalla = 0;
		opacidad = 255;		
		opacidadDos = 0;
		
		song1 = new SoundFile(app, "05 - Forbidden Steps.mp3");
		
		imagenes = new PImage[5];
		for (int i = 0; i < imagenes.length; i++) {
			imagenes[i] = app.loadImage("imagen"+i+".png");
		}
		
		ref = Comunicacion.getRef();
		ref.addObserver(this);
		ip = ref.getIp().getHostAddress();
		m = new Mundo(app);
		m.addObserver(this);
		
		puntos = 0;
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
			app.text("En el reino lejano de Tal'Vastol se cuenta una vieja leyenda. Una historia legendaria sobre el mal que acechaba el mundo y los héroes que lo combatieron. Sin embargo, solo uno de los héroes se atrevió a viajar más allá, donde el bosque sagrado cubría todo y los viajeros desprevenidos desaparecían sin rastro alguno. Allí, desafió valientemente al mal corrupto y lo desterró para siempre.", 300, 150, 600, 700);
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
			app.text("Una leyenda olvidada en el tiempo. Un héroe que desafió el bosque y devolvió la paz al reino. "+nombre+" es el nombre de este héroe. Su historia se conoce como...", 300, 250, 600, 700);
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
			app.fill(0, 255-opacidad);
			app.image(imagenes[0], 0, 0);
			app.rect(0, 0, 1200, 700);
			
			
			app.fill(0, 255-opacidadDos);
			app.image(imagenes[1], 135, 380);
			app.rect(134, 379, 400, 60);
			
			
			if(opacidad < 255 && !transition) {
				opacidad++;
				if(opacidad == 255) {
					transition = true;
				}
			}
			
			if(opacidad == 255 && opacidadDos < 255) {
				opacidadDos++;
			}
			
			break;
			
		case 4:
			if(!m.getIniciado()) {
				m.setIniciado(true);
			} else {
				m.pintar();
				if(!song1.isPlaying()) {
					song1.play();
				}
			}
			break;
			
		case 5:
			if(m.getIniciado()) {
				m.setIniciado(false);
			}
			
			app.fill(0, 255-opacidad);
			app.image(imagenes[2], 0, 0);
			app.image(imagenes[4], 800, 400);
			app.rect(0, 0, 1200, 700);
			
			app.fill(255, opacidad);
			app.text("¡GANASTE!" , 960, 160);
			app.text("Has devuelto la paz al bosque" , 960, 200);
			app.text("Puntos: "+puntos, 960, 300);
			
			if(opacidad < 255 && !transition) {
				opacidad++;
				if(opacidad == 255) {
					transition = true;
				}
			}
			break;
			
		case 6:
			if(m.getIniciado()) {
				m.setIniciado(false);
			}
			
			app.fill(0, 255-opacidad);
			app.image(imagenes[3], 0, 0);
			app.image(imagenes[4], 780, 480);
			app.rect(0, 0, 1200, 700);
			
			app.fill(255, opacidad);
			app.text("PERDISTE" , 920, 220);
			app.text("La leyenda era solo un cuento de hadas" , 800, 250, 260, 300);
			app.text("Puntos: "+puntos, 920, 400);
			
			if(opacidad < 255 && !transition) {
				opacidad++;
				if(opacidad == 255) {
					transition = true;
				}
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
							}
							if(opacidad<=0) {
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
							}
							if(opacidad<=0) {
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
			} else if(pantalla == 3) {
				new Thread(()->{
					opacidad = opacidadDos;
					while(true) {
						try {
							if(opacidad<=255) {
								opacidad-=2;
								opacidadDos-=2;
							}
							if(opacidad<=0) {
								opacidad = 0;
								opacidadDos=0;
								pantalla = 4;
								transition = false;
								break;
							}
							Thread.sleep(17);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}	
				}).start();
			} else if((pantalla == 5 || pantalla == 6) && opacidad >= 255) {
				m = new Mundo(app);
				m.addObserver(this);
				pantalla = 4;
				puntos = 0;
			}
		}
		
		if(mensaje[0].matches("Gana")) {
			pantalla = 5;
			puntos = Integer.parseInt(mensaje[1]);
		}
		
		if(mensaje[0].matches("Pierde")) {
			pantalla = 6;
			puntos = Integer.parseInt(mensaje[1]);
			if(puntos < 0) puntos = 0;
		}
	}

}