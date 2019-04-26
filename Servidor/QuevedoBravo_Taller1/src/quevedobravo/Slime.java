package quevedobravo;

import gifAnimation.Gif;
import processing.core.PApplet;
import processing.core.PVector;

public class Slime extends Enemigo {

	private float maxF;
	private float maxVel;
	private float xoff;
	private float yoff;
	private int timerAtacar;
	private int timerDaño;
	private int timerRecibir;
	private int dirGolpe;
	private boolean golpeado;
	private float desAcc;
	
	public Slime(PApplet app, Mundo m) {
		super(app, m);
		maxF = 0.1f;
	    maxVel = 1.0f;
	    xoff = app.random(2);
	    ene = new Gif[2];
	    tam = 60;
	    desAcc = 0.1f;
	    vida = 2;
	    dirGolpe = 0;
	    golpeado = false;
	    
	    for (int i = 0; i < ene.length; i++) {
			ene[i] = new Gif(app, "slime"+i+".gif");
		}
	    ene[1].loop();
	    
	    timerAtacar = 0; 
	    timerDaño = 0;
	    timerRecibir = 0;
	}

	public void pintar() {
		app.imageMode(app.CENTER);
		app.image(ene[1], pos.x, pos.y);
	}

	public void mover() {
		PVector obj = new PVector(pos.x, pos.y);
		
		if(app.dist(j.getPos().x+64, j.getPos().y+64, pos.x, pos.y) > 250) {
			xoff += .006f;
		    yoff += .005f;
		    
		    try {
		    	obj = new PVector(150+(app.noise(xoff)*900), 200+(app.noise(yoff)*390));
		    } catch(ArithmeticException e) {
		    	e.printStackTrace();
		    }
		    
		    
		} else {
			obj = new PVector(j.getPos().x+64, j.getPos().y+64);
		}
		
		
		
		if(app.dist(j.getPos().x+64, j.getPos().y+64, pos.x, pos.y) > 60) {
			
			PVector deseado = PVector.sub(obj, pos);
			
			deseado.mult(maxVel);
			
		    PVector fuerza = PVector.sub(deseado, vel);
		    fuerza.limit(maxF);
		    acc.add(fuerza);
		    
		    vel.add(acc);
		    vel.limit(maxVel);
		    pos.add(vel);
		    acc.mult(0);
		}  else {
			
			if(timerAtacar == 0) {
				timerAtacar = 150;
			    PVector deseado = PVector.sub(obj, pos);
			    if(deseado.mag()>100) {
			      vel = deseado;
			      vel.setMag(5);
			    } else {
			      float newMag = app.map(deseado.mag(), 0, 100, 0, 5);
			      vel = deseado;
			      vel.setMag(newMag);
			    }
			}
			
			
			
			if(timerDaño == 0) {
				if(pos.x > j.getPos().x+32 && pos.x < j.getPos().x+100 && pos.y > j.getPos().y+50 && pos.y < j.getPos().y+128) {
					if(!j.isBloquear()) {
						j.setVida(j.getVida()-1);
						timerDaño = 150;
					} else {
						if(pos.x <= j.getPos().x && j.getDir() != 1) {
							j.setVida(j.getVida()-1);
						} else if(pos.x > j.getPos().x && !j.isBloquear() && j.getDir() != 0) {
							j.setVida(j.getVida()-1);
						}		
						timerDaño = 150;
					}
				}
			}
			
			pos.add(vel);
		    if(vel.mag()>desAcc) {
		      float mag = vel.mag()-desAcc;
		      vel.setMag(mag);
		    } else  {
		      vel.setMag(0);
		    }
		}
		
		if(timerAtacar > 0)timerAtacar--;
		
		if(timerDaño > 0)timerDaño--;
	}

	public void run() {
		while(vivo) {
			try {
				
				if(timerRecibir == 0) {
					mover();
					golpeado = false;
				} 
				
				if(golpeado) {
					recibirDaño(dirGolpe);
				}
				
				
				sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void recibirDaño(int dir) {
		if(timerRecibir == 60) {
			vida--;
			if(dir == 0) {
				vel = new PVector(5, 0);
			} else {
				vel = new PVector(-5, 0);
			}
			timerRecibir--;
		} else if(timerRecibir > 0){
			pos.add(vel);
		    if(vel.mag()>desAcc) {
		      float mag = vel.mag()-desAcc;
		      vel.setMag(mag);
		    } else  {
		      vel.setMag(0);
		    }
		    timerRecibir--;
		}
		
	}
	
	public void setTiempo(int t) {
		timerRecibir = t;
	}
	
	public void setGolpeado(boolean g) {
		golpeado = g;
	}
	
	public boolean isGolpeado() {
		return golpeado;
	}
	
	public void setDir(int d) {
		dirGolpe = d;
	}
}
