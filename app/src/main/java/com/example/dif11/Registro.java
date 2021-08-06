package com.example.dif11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

public class Registro extends AppCompatActivity implements View.OnClickListener {

    private TextView nom, ap, gm, pass, passconf, tel, fn, pis;
    public Button resgistrar;
    private int dia, mes, ano;

    //Variable de los datos que vamos a registrar;
    private String nombre="";
    private String apellido="";
    private String gmail ="";
    private String clave ="";
    private String verificacionclave ="";
    private String telefono="";
    private String fecha="";


    private FirebaseAuth mAuth;//base de datos


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        //ATRAPAMOS LOS VALORES DE LOS ID
        nom = findViewById(R.id.nom);
        ap = findViewById(R.id.ap);
        gm = findViewById(R.id.gmail);
        pass = findViewById(R.id.pass);
        passconf = findViewById(R.id.passconf);
        tel = findViewById(R.id.tel);
        fn = findViewById(R.id.fn);

        //DEFINIMOS --PIS- COMO VARIABLE PARA VOLVER AL LOGIN
        pis = findViewById(R.id.pis);

        //linea de la instancia
        mAuth = FirebaseAuth.getInstance();//base de datos necesita una instancia de autenticación de base de fuego

        resgistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre = nom.getText().toString();
                apellido = ap.getText().toString();
                gmail = gm.getText().toString();
                clave = pass.getText().toString();
                verificacionclave = passconf.getText().toString();
                telefono = tel.getText().toString();
                fecha = fn.getText().toString();

                if (!nombre.isEmpty() && !apellido.isEmpty() && !gmail.isEmpty() && !clave.isEmpty() && !verificacionclave.isEmpty() && !telefono.isEmpty() && !fecha.isEmpty()){

                    if (clave.length() >= 8 && verificacionclave.length() >= 8){
                        if(clave.length()==verificacionclave.length()){
                            registrarUser();
                        }
                    }else{
                        Toast.makeText( Registro.this, "La clave debe tener almenos 8 caracteres", Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText( Registro.this, "Debe completar los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });



        
        pis.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View registro) {

                Intent i = new Intent(Registro.this, MainActivity.class);
                startActivity(i);
            }
        });

        fn.setOnClickListener(this);

    }//aca termina el metodo onCreate

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);

    }

    private void updateUI(FirebaseUser currentUser) {
        Log.i("User:", ""+currentUser);
    }

    public void createUserWithEmaiñAndpasswored(nombre, apellido){


    }

    /** private void registrarUser() {
        mAuth.createUserWithEmailAndPassword(gmail, clave).addOnCanceledListener(new OnCanceledListener<AuthResult>() {
            @Override
            public void onCanceled() {
                Object task;
                if (task.isSuccessful()){

                }
            }
        });
    }**/



    //se invoca los parametros de la fecha en el registro
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