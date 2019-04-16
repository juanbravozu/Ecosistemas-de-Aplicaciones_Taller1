package quevedobravo;

import gifAnimation.Gif;
import processing.core.PApplet;
import processing.core.PVector;

public abstract class Enemigo extends Thread {

	protected PApplet app;
	protected PVector pos;
	protected PVector vel;
	protected PVector acc;
	protected int tam;
	protected Gif[] ene;
	protected Mundo m;
	protected Jugador j;
	protected boolean vivo;
	
	public Enemigo(PApplet app, Mundo m) {
		this.app = app;
		this.m = m;
		j = m.getJugador();
		vivo = true;
		pos = new PVector(app.random(260, 760), app.random(300, 400));
		vel = new PVector(0, 0);
		acc = new PVector(0, 0);
	}
	
	public abstract void pintar();
	
	public abstract void mover();
	
	public abstract void run();
}
