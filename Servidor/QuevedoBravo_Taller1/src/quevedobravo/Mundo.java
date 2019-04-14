package quevedobravo;

import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Mundo implements Observer {

	private PApplet app;
	private boolean iniciado;
	private Comunicacion ref;
	
	/**
	 * 0=Negativo para validar movimiento
	 * 1=Nivel 1
	 * 2=Arboles Nivel 1
	 */
	private PImage[] fondos;
	private int nivel;
	private Jugador jug;
	
	public Mundo(PApplet app) {
		this.app = app;
		
		iniciado = false;
		app.textAlign(app.CENTER, app.TOP);
		app.background(0);
		app.text("Cargando...", app.width/2, app.height/2);
		
		jug = new Jugador(app);
		
		ref = Comunicacion.getRef();
		ref.addObserver(this);
		
		nivel = 1;
		
		fondos = new PImage[3];
		
		for(int i = 0; i < fondos.length; i++) {
			fondos[i] = app.loadImage("fondo"+i+".png");
		}			
	}
	
	public void pintar() {
		switch(nivel) {
		case 1:
			app.image(fondos[1], 0, 0);
			jug.pintar();
			app.image(fondos[2], 0, 0);
			break;
		}
	}

	public void update(Observable arg0, Object arg1) {
		String mensaje[] = (String[]) arg1;
		
		if(iniciado) {
			if(mensaje[0].matches("Mover")) {
				if(Integer.parseInt(mensaje[2]) > 30) {
					float ang = app.radians(Integer.parseInt(mensaje[1]));	
					PVector vel = PVector.fromAngle(-ang);
					
					vel.normalize();
					vel.mult(7);
					PVector pos = jug.getPos();
					int x = (int) (pos.x+vel.x);
					int y = (int)(pos.y+vel.y);
					if(fondos[0].get(x, y) == -1) {
						vel.normalize();
						vel.mult(6);
						jug.setVel(vel);
						jug.mover();
					}									
				} else {
					PVector vel = new PVector(0, 0);
					jug.setVel(vel);
				}
				
			}
		}
		System.out.println(mensaje[0]);
	}
	
	public void setIniciado(boolean i) {
		iniciado = i;
	}
	
	public boolean getIniciado() {
		return iniciado;
	}
	
}
