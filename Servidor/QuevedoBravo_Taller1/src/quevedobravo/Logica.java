package quevedobravo;

import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;
import processing.core.PVector;

public class Logica implements Observer {

	private PApplet app;
	private PVector pos;
	private Comunicacion ref;

	public Logica(PApplet app) {
		this.app = app;
		pos = new PVector(app.width/2, app.height/2);
		ref = Comunicacion.getRef();
		ref.addObserver(this);
		app.fill(0);
	}

	public void pintar() {
		app.background(255);
		app.ellipse(pos.x, pos.y, 50, 50);
	}

	public void update(Observable arg0, Object arg1) {
		String mensaje[] = (String[]) arg1;
		if(mensaje[0].matches("Mover")) {
			int ang = Integer.parseInt(mensaje[1]);
			mover(ang);
		}
	}

	public void mover(int ang) {
		PVector mov = PVector.fromAngle(app.radians(-ang));
		mov.normalize();
		mov.mult(7);
		pos.add(mov);
	}
}