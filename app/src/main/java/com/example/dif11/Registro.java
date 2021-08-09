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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

public class Registro extends AppCompatActivity implements View.OnClickListener {

    private TextView nom, ap, gm, pass, passconf, tel, fn, pis;
    //Button registrar;
    private int dia, mes, ano;

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
        pis = findViewById(R.id.pis);//DEFINIMOS --PIS- COMO VARIABLE PARA VOLVER AL LOGIN

        /** aca se lleva a la instancia principal del Main **/
        pis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View registro) {

                Intent i = new Intent(Registro.this, MainActivity.class);
                startActivity(i);
            }
        });
        fn.setOnClickListener(this);

        //linea de la instancia
        mAuth = FirebaseAuth.getInstance();//base de datos necesita una instancia de autenticación de base de fuego


    }/** aca termina el metodo onCreate **/

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
        },dia,mes,ano);
        datePickerDialog.show();
    }

    /** los metodos onStart y updateUI **/
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    private void updateUI(FirebaseUser currentUser) {

        Log.i("User:", ""+currentUser);
    }

    public void createUserWithEmailAndPassword(String email, String password){
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (!task.isSuccessful()) {
                            Intent i = new Intent(Registro.this, MainActivity.class);
                            startActivity(i);

                            Log.d("ÉXITO","createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            Toast.makeText(Registro.this, "El registro fue exitoso.", Toast.LENGTH_SHORT).show();
                            updateUI(user);

                            //ENVIAR A UNA NUEVA LSITA
                        } else {
                            Log.w("ERROR", "AcreateUserWithEmail:failure", task.getException());

                            Toast.makeText(Registro.this, "La Autenticacion no llego.", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    public void buttonpress(View view){

        String nombre = nom.getText().toString();
        String apellido = ap.getText().toString();
        String email = gm.getText().toString();
        String contrasena = pass.getText().toString();
        String rclave = passconf.getText().toString();
        String telefono = tel.getText().toString();
        String fechan = fn.getText().toString();

        if (!nombre.isEmpty() && !apellido.isEmpty() && !email.isEmpty() && !contrasena.isEmpty() && !rclave.isEmpty() && !telefono.isEmpty() && !fechan.isEmpty()){

            if (contrasena.length() >=  6 && rclave.length() >= 6){
                if(contrasena.length() == rclave.length()){
                    createUserWithEmailAndPassword(email,contrasena);
                }
            }else{
                Toast.makeText( Registro.this, "La clave debe tener almenos 8 caracteres", Toast.LENGTH_SHORT).show();
            }

        }
        else
            Toast.makeText( Registro.this, "Debe completar los campos", Toast.LENGTH_SHORT).show();
    }
}

