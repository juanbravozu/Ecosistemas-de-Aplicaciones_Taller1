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
	protected int vida;
	protected boolean vivo;
	
	public Enemigo(PApplet app, Mundo m) {
		this.app = app;
		this.m = m;
		j = m.getJugador();
		vivo = true;
		int spawn = (int)app.random(2);
		if(spawn == 0) {
			pos = new PVector(100, app.random(350, 500));
		} else {
			pos = new PVector(1100, app.random(350, 500));
		}
		vel = new PVector(0, 0);
		acc = new PVector(0, 0);
	}
	
	public abstract void pintar();
	
	public abstract void mover();
	
	public abstract void run();
	
	public PVector getPos() {
		return pos;
	}

	public boolean isVivo() {
		return vivo;
	}

	public void setVivo(boolean vivo) {
		this.vivo = vivo;
	}

	public int getVida() {
		return vida;
	}

	public void setVida(int vida) {
		this.vida = vida;
	}
	
	
}
