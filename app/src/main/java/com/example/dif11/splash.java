package com.example.dif11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TimerTask splash = new TimerTask(){//metodo que le asignamos una tarea que se puede programar para una ejecución única o repetida por un temporizador.
            public  void run(){ //clse

                Intent i = new Intent(splash.this, MainActivity.class);//cambia de evento y se la asigan al activity que quiera
                startActivity(i);
                finish();
            }
        };
        Timer t = new Timer();//llama al metodo Timer
        t.schedule(splash, 1000); //y se le asigna un tiempo de respuesta
    }
}