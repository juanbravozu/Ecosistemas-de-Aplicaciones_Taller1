package com.eco.juan.taller1_quevedobravo;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.Observable;
import java.util.Observer;

import io.github.controlwear.virtual.joystick.android.JoystickView;

public class Control extends AppCompatActivity implements Observer {

    private Comunicacion ref;
    private JoystickView analogo;
    private ImageButton a;
    private ImageButton b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        ref = Comunicacion.getRef();
        ref.addObserver(this);

        analogo = findViewById(R.id.jv_analogo_control);
        a = findViewById(R.id.btn_A_control);
        b = findViewById(R.id.btn_B_control);

        analogo.setOnMoveListener(new JoystickView.OnMoveListener() {
            public void onMove(int angle, int strength) {
                if(strength > 30) {
                    String msg = "Mover: :"+angle;
                    ref.enviar(msg);
                }
            }
        });

        a.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ref.enviar("A");
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ref.enviar("B");
            }
        });
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
