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
	private int pantalla;
	
	public Logica(PApplet app) {
		this.app = app;
		
		pantalla = 0;
		fuente = app.loadFont("pixellari-30.vlw");
		song1 = new SoundFile(app, "05 - Forbidden Steps.mp3");
		song1.loop();
		
		ref = Comunicacion.getRef();
		ref.addObserver(this);
		ip = ref.getIp().getHostAddress();
		
		app.textAlign(app.CENTER, app.TOP);
		app.textFont(fuente);
		
	}

	public void pintar() {
		switch(pantalla) {
		case 0:
			app.background(0);
			app.text("Introduce tu nombre de personaje y la dirección ip para iniciar la conexión", 350, 100, 500, 700);
			app.text(ip, 600, 300);
		}
	}

	public void update(Observable arg0, Object arg1) {
		String mensaje[] = (String[]) arg1;
		
		if(mensaje[0].matches("Crear"))System.out.println(mensaje[1]);
	}

}