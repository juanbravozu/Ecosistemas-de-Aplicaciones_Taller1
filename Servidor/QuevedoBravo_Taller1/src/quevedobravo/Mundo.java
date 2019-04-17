package quevedobravo;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

public class Mundo extends Observable implements Observer {

	private PApplet app;
	private boolean iniciado;
	private boolean atacando;
	private Comunicacion ref;
	
	/**
	 * 0=Negativo para validar movimiento
	 * 1=Nivel 1
	 * 2=Arboles Nivel 1
	 */
	private PImage[] fondos;
	private PImage[] ui;
	private int nivel;
	private int tiempo;
	private Jugador jug;
	private ArrayList<Enemigo> enemigos;
	private int nuevoEnemigo;
	private int timerEnemigo;
	private int oleada;
	private int puntos;
	
	public Mundo(PApplet app) {
		this.app = app;
		
		iniciado = false;
		tiempo = 120;
		
		jug = new Jugador(app);
		atacando = false;
		
		ref = Comunicacion.getRef();
		ref.addObserver(this);
		
		nivel = 1;
		
		fondos = new PImage[3];
		ui = new PImage[2];
		for (int i = 0; i < ui.length; i++) {
			ui[i] = app.loadImage("barra"+i+".png");
		}
		
		for(int i = 0; i < fondos.length; i++) {
			fondos[i] = app.loadImage("fondo"+i+".png");
		}			
		
		enemigos = new ArrayList<Enemigo>();
		nuevoEnemigo = 1;
		oleada = 0;
		timerEnemigo = 420;
		
		for (int i = 0; i < 2; i++) {
			Enemigo e = new Slime(app, this);
			
			enemigos.add(e);
		}
	}
	
	public void pintar() {
		
		for(int i = 0; i < enemigos.size(); i++) {
			if(enemigos.get(i).getState() == Thread.State.NEW) {
				enemigos.get(i).start();
			}
		}
		
		if(atacando) {
			for(int i = enemigos.size()-1; i >= 0; i--) {
				Enemigo e = enemigos.get(i);
				PVector enePos = e.getPos();
				PVector jugPos = jug.getPos();
				if(jug.getDir() == 0) {
					if(enePos.x > jugPos.x+96 && enePos.x < jugPos.x+192 && enePos.y > jugPos.y+30 && enePos.y < jugPos.y + 128 && jug.getAtacarDer() && !((Slime)e).isGolpeado()) {
						((Slime)e).setTiempo(60);
						((Slime)e).setGolpeado(true);
						((Slime)e).setDir(jug.getDir());
						if(e.getVida() == 0) {
							e.setVivo(false);
							enemigos.remove(e);
							atacando = false;
							puntos += 50;
						}
					}
				} else {
					if(enePos.x < jugPos.x+30 && enePos.x > jugPos.x-64 && enePos.y > jugPos.y+30 && enePos.y < jugPos.y + 128 && jug.getAtacarIzq() && !((Slime)e).isGolpeado()) {
						((Slime)e).setTiempo(60);
						((Slime)e).setGolpeado(true);
						((Slime)e).setDir(jug.getDir());
						if(e.getVida() == 0) {
							e.setVivo(false);
							enemigos.remove(e);
							atacando = false;
						}
					}
				}
			}
		}
		
		switch(nivel) {
		case 1:
			app.imageMode(app.CORNER);
			app.image(fondos[1], 0, 0);
			
			for (int i = 0; i < enemigos.size(); i++) {
				enemigos.get(i).pintar();
			}
			app.imageMode(app.CORNER);
			jug.pintar();
			
			app.image(fondos[2], 0, 0);
			app.fill(200, 0, 0);
			app.rect(27, 16, jug.getVida()*43, 16);
			app.image(ui[0], 0, 10);
			app.fill(50, 84, 97);
			app.rect(27,46, app.map(tiempo, 0, 120, 0, 212), 16);
			app.image(ui[1], 0, 40);
			
			break;
		}
		
		if(timerEnemigo == 0) {
			for(int i = 0; i < nuevoEnemigo; i++) {
				Enemigo e = new Slime(app, this);
				e.start();
				enemigos.add(e);
			}
			timerEnemigo = 420;
			oleada++;
			if(oleada % 3 == 0) nuevoEnemigo++;
		}
		
		if(timerEnemigo > 0) {
			timerEnemigo--;
		}
		
		if(jug.getVida() > 0) {
			if(app.frameCount%60 == 0) tiempo--;
			if(tiempo == 0) {
				String[] msg = new String[2];
				msg[0] = "Gana";
				msg[1] = Integer.toString(puntos);
				setChanged();
				notifyObservers(msg);
				clearChanged();
			}
		} else {
			puntos -= tiempo*10;
			String[] msg = new String[2];
			msg[0] = "Pierde";
			msg[1] = Integer.toString(puntos);
			setChanged();
			notifyObservers(msg);
			clearChanged();
		}
	}

	public void update(Observable arg0, Object arg1) {
		String mensaje[] = (String[]) arg1;
		
		if(iniciado) {
			if(mensaje[0].matches("Mover") && !jug.isAccion() && !jug.isBloquear() && mensaje.length>1) {
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
			
			if(mensaje[0].matches("A")) {
				if(jug.atacar()) {
					atacando = true;
				}
			}
			
			if(mensaje[0].matches("B")) {
				jug.bloquear();
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
	
	public Jugador getJugador() {
		return jug;
	}
}
