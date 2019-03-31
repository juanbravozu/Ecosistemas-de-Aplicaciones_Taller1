package com.eco.juan.taller1_quevedobravo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;

public class Comunicacion extends Observable implements Runnable {

    private Socket socket;
    private DataInputStream entrada;
    private DataOutputStream salida;
    private boolean conectado;

    private static Comunicacion ref;

    private Comunicacion() {
        conectado = false;
        System.out.print("Esperando Conexión");
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

                    socket = new Socket(InetAddress.getByName("172.30.182.176"), 5000);
                    entrada = new DataInputStream(socket.getInputStream());
                    salida = new DataOutputStream(socket.getOutputStream());
                    conectado = true;
                } else {
                    recibir();
                    Thread.sleep(33);
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void recibir() throws IOException {
        String mensaje = entrada.readUTF();
        setChanged();
        notifyObservers(mensaje);
        clearChanged();
    }

    public void enviar(final String id) {
        if(socket != null && socket.isConnected() && salida != null) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        salida.writeUTF(id);
                        salida.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
