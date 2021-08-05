package com.example.dif11;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class Registro extends AppCompatActivity implements View.OnClickListener {

    public TextView pis, fn;
    private int dia, mes, ano;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        pis = findViewById(R.id.pis);
        fn = findViewById(R.id.fn);


        pis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View registro) {

                Intent i = new Intent(Registro.this, MainActivity.class);
                startActivity(i);
            }
        });

        fn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final Calendar c = Calendar.getInstance();
        dia=c.get(Calendar.DAY_OF_MONTH);
        mes=c.get(Calendar.MONTH);
        ano=c.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                fn.setText(dayOfMonth+"/"+(month+1)+"/"+year);
            }
        }
        ,dia,mes,ano);
        datePickerDialog.show();
    }
}