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
	private PImage[] ui;
	private int nivel;
	private int tiempo;
	private Jugador jug;
	
	public Mundo(PApplet app) {
		this.app = app;
		
		iniciado = false;
		tiempo = 120;
		app.textAlign(app.CENTER, app.TOP);
		app.background(0);
		app.text("Cargando...", app.width/2, app.height/2);
		
		jug = new Jugador(app);
		
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
	}
	
	public void pintar() {
		switch(nivel) {
		case 1:
			app.image(fondos[1], 0, 0);
			jug.pintar();
			app.image(fondos[2], 0, 0);
			app.fill(200, 0, 0);
			app.rect(27, 16, jug.getVida()*43, 16);
			app.image(ui[0], 0, 10);
			app.fill(50, 84, 97);
			app.rect(27,46, app.map(tiempo, 0, 120, 0, 212), 16);
			app.image(ui[1], 0, 40);
			if(app.frameCount%60 == 0)tiempo--;
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
			
			if(mensaje[0].matches("A"))jug.atacar();
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
