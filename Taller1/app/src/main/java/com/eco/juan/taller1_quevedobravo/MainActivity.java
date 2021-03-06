package com.eco.juan.taller1_quevedobravo;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Observable;
import java.util.Observer;

import io.github.controlwear.virtual.joystick.android.JoystickView;

public class MainActivity extends AppCompatActivity  {

    private EditText nombre;
    private EditText ip;
    private Button conectar;
    private Comunicacion ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        nombre = findViewById(R.id.edt_nombre_main);
        ip = findViewById(R.id.edt_ip_main);
        conectar = findViewById(R.id.btn_conectar_main);

        conectar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String nombrePj = nombre.getText().toString();
                final String direccion = ip.getText().toString();

                if(nombrePj != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            ref = Comunicacion.getRef(direccion);
                            try {
                                Thread.sleep(500);
                                ref.enviar("Crear: :" + nombrePj);
                                Intent i = new Intent(MainActivity.this, Control.class);
                                startActivity(i);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }


                        }
                    }).start();
                }
            }
        });
    }


}
