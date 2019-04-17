package quevedobravo;

import gifAnimation.Gif;
import processing.core.PApplet;
import processing.core.PVector;
import processing.sound.SoundFile;

public class Jugador {

	private PApplet app;
	private PVector pos;
	private PVector vel;
	private int dir; //0=Derecha 1=Izquierda
	private Gif[] pj;//0=QuietoDerecha 1=QuietoIzquierda 2=CaminandoDerecha 3=CaminandoIzquierda 4=AtacandoDerecha 5=AtacandoIzquierda 6=BloqueandoDerecha 7=BloqueandoIzquierda
	private SoundFile ataque;
	private int vida;
	private boolean accion;
	private boolean bloquear;
	
	public Jugador(PApplet app) {
		this.app = app;
		pos = new PVector(app.width/2, app.height-200);
		vel = new PVector(0, 0);
		dir = 0;
		vida = 1;
		ataque = new SoundFile(app, "swoosh.mp3");
		accion = false;
		bloquear = false;
		
		pj = new Gif[8];
		for(int i = 0; i < 4; i++) {
			pj[i] = new Gif(app, "pj"+i+".gif");
			pj[i].loop();
		}
		pj[4] = new Gif(app, "pj4.gif");
		pj[5] = new Gif(app, "pj5.gif");
		pj[6] = new Gif(app, "pj6.gif");
		pj[7] = new Gif(app, "pj7.gif");
	}
	
	public void pintar() {
	
		if(!pj[4].isPlaying() && !pj[5].isPlaying() && !pj[6].isPlaying() && !pj[7].isPlaying()) {
			accion = false;
			bloquear = false;
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
		} else if(!pj[6].isPlaying() && !pj[7].isPlaying()){
			if(dir == 0) {
				app.image(pj[4], pos.x, pos.y);
			} else {
				app.image(pj[5],  pos.x-33,  pos.y);
			}
		} else {
			if(dir == 0) {
				app.image(pj[6], pos.x, pos.y);
			} else {
				app.image(pj[7],  pos.x,  pos.y);
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
	
	public boolean atacar() {
		if(dir == 0) {
			accion = true;
			if(!pj[4].isPlaying()) {
				ataque.play();
				pj[4].play();
				return true;
			} else {
				return false;
			}
		} else {
			accion = true;
			if(!pj[5].isPlaying()) {
				ataque.play();
				pj[5].play();
				return true;
			} else {
				return false;
			}
		}
	} 
	
	public void bloquear() {
		
		if(dir == 0) {
			bloquear = true;
			pj[6].play();
		} else {
			bloquear = true;
			pj[7].play();
		}
	}
	
	public void setVel(PVector vel) {
		this.vel = vel;
	}
	
	public PVector getPos() {
		return pos;
	}

	public int getVida() {
		return vida;
	}

	public void setVida(int vida) {
		this.vida = vida;
	}

	public boolean isAccion() {
		return accion;
	}

	public void setAccion(boolean accion) {
		this.accion = accion;
	}
	
	public int getDir() {
		return dir;
	}
	
	public boolean isBloquear() {
		return bloquear;
	}
	
	public boolean getAtacarDer() {
		if(pj[4].currentFrame() == 2 || pj[4].currentFrame() == 3) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean getAtacarIzq() {
		if(pj[5].currentFrame() == 2 || pj[5].currentFrame() == 3) {
			return true;
		} else {
			return false;
		}
	}
}
