package com.eco.juan.taller1_quevedobravo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Observable;
import java.util.Observer;

import io.github.controlwear.virtual.joystick.android.JoystickView;

public class MainActivity extends AppCompatActivity implements Observer {

    private JoystickView analogo;
    private Comunicacion ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ref = Comunicacion.getRef();
        ref.addObserver(this);

        analogo = findViewById(R.id.jv_analogo_main);

        analogo.setOnMoveListener(new JoystickView.OnMoveListener() {
            public void onMove(int angle, int strength) {
                if(strength > 20) {
                    ref.enviar("Mover: :"+angle);
                }
            }
        });
    }

    public void update(Observable o, Object arg) {

    }
    
}
