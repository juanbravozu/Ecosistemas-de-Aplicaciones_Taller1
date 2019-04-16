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
	    maxVel = 2.0f;
	    xoff = app.random(2);
	    xoff = app.random(2);
	    ene = new Gif[2];
	    
	    for (int i = 0; i < ene.length; i++) {
			ene[i] = new Gif(app, "slime"+i+".gif");
		}
	    ene[1].loop();
	}

	public void pintar() {
		app.image(ene[1], pos.x, pos.y);
	}

	public void mover() {
		if(app.dist(j.getPos().x, j.getPos().y, pos.x, pos.y) > 250) {
			xoff += .006f;
		    yoff += .005;
		    
		    PVector obj = new PVector(100+(app.noise(xoff)*1000), 160+(app.noise(yoff)*420));
		    
		    PVector deseado = PVector.sub(obj, pos);
		    deseado.normalize();
		    deseado.mult(maxVel);
		    PVector fuerza = PVector.sub(deseado, vel);
		    fuerza.limit(maxF);
		    acc.add(fuerza);
		    
		    vel.add(acc);
		    vel.limit(maxVel);
		    pos.add(vel);
		    acc.mult(0);
		}
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
