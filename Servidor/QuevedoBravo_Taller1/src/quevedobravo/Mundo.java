package quevedobravo;

import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Mundo implements Observer {

	private PApplet app;
	private Comunicacion ref;
	private PImage[] fondos;
	private int nivel;
	private Jugador jug;
	
	public Mundo(PApplet app) {
		this.app = app;
		
		app.textAlign(app.CENTER, app.TOP);
		app.background(0);
		app.text("Cargando...", app.width/2, app.height/2);
		
		jug = new Jugador(app);
		
		ref = Comunicacion.getRef();
		ref.addObserver(this);
		
		nivel = 1;
		
		fondos = new PImage[2];
		
		for(int i = 0; i < fondos.length; i++) {
			fondos[i] = app.loadImage("fondo"+i+".png");
		}			
	}
	
	public void pintar() {
		switch(nivel) {
		case 1:
			app.image(fondos[0], 0, 0);
			jug.pintar();
			app.image(fondos[1], 0, 0);
			break;
		}
	}

	public void update(Observable arg0, Object arg1) {
		String mensaje[] = (String[]) arg1;
		
		if(mensaje[0].matches("Mover")) {
			if(Integer.parseInt(mensaje[2]) > 30) {
				float ang = app.radians(Integer.parseInt(mensaje[1]));	
				PVector vel = PVector.fromAngle(-ang);
				vel.normalize();
				vel.mult(6);
				jug.setVel(vel);
				jug.mover();				
			} else {
				PVector vel = new PVector(0, 0);
				jug.setVel(vel);
			}
			
		}
	}
	
	

	
}
