package quevedobravo;

import gifAnimation.Gif;
import processing.core.PApplet;
import processing.core.PVector;

public class Jugador {

	private PApplet app;
	private PVector pos;
	private PVector vel;
	private int dir; //0=Derecha 1=Izquierda
	private Gif[] pj;//0=QuietoDerecha 1=QuietoIzquierda 2=CaminandoDerecha 3=CaminandoIzquierda
	
	public Jugador(PApplet app) {
		this.app = app;
		pos = new PVector(app.width/2, app.height/2);
		vel = new PVector(0, 0);
		dir = 0;
		
		pj = new Gif[4];
		for(int i = 0; i < pj.length; i++) {
			pj[i] = new Gif(app, "pj"+i+".gif");
			pj[i].loop();
		}
	}
	
	public void pintar() {
	
		if(dir == 0) {
			if(vel.mag()>0) {
				app.image(pj[2], pos.x, pos.y);
			} else {
				app.image(pj[0], pos.x, pos.y);
			}
		} else {
			if(vel.mag()>0) {
				app.image(pj[3], pos.x, pos.y);
			} else {
				app.image(pj[1], pos.x, pos.y);				
			}
		}
		
	}
	
	public void mover() {
		pos.add(vel);
		if(vel.x >= 0) {
			dir = 0;
		} else {
			dir = 1;
		}
	}
	
	public void setVel(PVector vel) {
		this.vel = vel;
	}
	
	public PVector getPos() {
		return pos;
	}
}
