package quevedobravo;

import gifAnimation.Gif;
import processing.core.PApplet;
import processing.core.PVector;

public class Slime extends Enemigo {

	private float maxF;
	private float maxVel;
	private float xoff;
	private float yoff;
	
	public Slime(PApplet app, Mundo m) {
		super(app, m);
		maxF = 0.1f;
	    maxVel = 1.0f;
	    xoff = app.random(2);
	    xoff = app.random(2);
	    ene = new Gif[2];
	    tam = 60;
	    
	    for (int i = 0; i < ene.length; i++) {
			ene[i] = new Gif(app, "slime"+i+".gif");
		}
	    ene[1].loop();
	}

	public void pintar() {
		app.imageMode(app.CENTER);
		app.image(ene[1], pos.x, pos.y);
	}

	public void mover() {
		PVector obj = new PVector(pos.x, pos.y);
		
		if(app.dist(j.getPos().x+64, j.getPos().y+64, pos.x, pos.y) > 250) {
			xoff += .006f;
		    yoff += .005;
		    
		    obj = new PVector(150+(app.noise(xoff)*900), 200+(app.noise(yoff)*390));
		    
		    
		} else/* if(app.dist(j.getPos().x+64, j.getPos().y+64, pos.x, pos.y) > 100)*/ {
			obj = new PVector(j.getPos().x+64, j.getPos().y+64);
		}
		
		PVector deseado = PVector.sub(obj, pos);
		float magD = deseado.mag();
		if (magD < 100) {
			float m = app.map(magD, 0, 100, 0, maxVel);
			deseado.mult(m);
		} else {
			deseado.mult(maxVel);
		}
	    PVector fuerza = PVector.sub(deseado, vel);
	    fuerza.limit(maxF);
	    acc.add(fuerza);
	    
	    vel.add(acc);
	    vel.limit(maxVel);
	    pos.add(vel);
	    acc.mult(0);
	}

	public void run() {
		while(vivo) {
			try {
				
				mover();
				
				sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
