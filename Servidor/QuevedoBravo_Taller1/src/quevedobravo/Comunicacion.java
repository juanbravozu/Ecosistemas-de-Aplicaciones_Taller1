package quevedobravo;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;

public class Comunicacion extends Observable implements Runnable {

	private DataInputStream entrada;
	private DataOutputStream salida;
	private boolean conectado;

	private static Comunicacion ref;

	private Comunicacion() {
		conectado = false;
		System.out.println("Esperando conexion");
	}

	public static Comunicacion getRef() {
		if(ref == null) {
			ref = new Comunicacion();
			Thread t = new Thread(ref);
			t.start();
		}
		return ref;
	}

	public void run() {
		while(true) {
			try {
				if(!conectado) {
					ServerSocket server = new ServerSocket(5000);

					Socket s = server.accept();
					System.out.println("Conexion aceptada");

					entrada = new DataInputStream(s.getInputStream());
					salida = new DataOutputStream(s.getOutputStream());
					conectado = true;
				} else {
					recibir();
					Thread.sleep(33);
				}

			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	private void recibir() throws IOException {
		String mensaje[] = entrada.readUTF().split(": :");
		for(int i = 0; i < mensaje.length; i++) {
			mensaje[i] = mensaje[i].trim();
		}
		setChanged();
		notifyObservers(mensaje);
		clearChanged();
	}

	public void enviar(String mensaje) {
		try {
			salida.writeUTF(mensaje);
			salida.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}