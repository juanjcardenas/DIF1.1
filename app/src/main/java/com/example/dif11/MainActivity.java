package com.example.dif11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    public Button r1;
    public TextView rc;
    public EditText user, clave;
    private FirebaseAuth mAuth;
    ToggleButton ojo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        r1 = findViewById(R.id.cc);
        rc = findViewById(R.id.rc);
        ojo = findViewById(R.id.ojo);
        user = findViewById(R.id.user);
        clave = findViewById(R.id.clave);

        mAuth = FirebaseAuth.getInstance();

        ojo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    rc.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Registro.class);
                startActivity(i);
            }
        });
        rc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Estamos trabajano en ello...", Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    private void updateUI(FirebaseUser currentUser) {

        Log.i("User:", ""+currentUser);
    }

    public void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (!task.isSuccessful()) {
                            Log.d("ÉXITO", "signInWinthEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);

                            Intent i = new Intent(MainActivity.this, operacion.class);
                            startActivity(i);
                            Toast.makeText(MainActivity.this, "La Autenticacion llego Correctamente.", Toast.LENGTH_SHORT).show();
                            //ENVIAR a la lista general
                        } else {
                            Log.w("ERROR", "signInWinthEmail::failure", task.getException());

                            Toast.makeText(MainActivity.this, "La Autenticacion no llego.", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }
    public void buttonPress1(View view){
        String email = user.getText().toString();
        String pass = clave.getText().toString();

        if (!email.isEmpty() && !pass.isEmpty()){

            if(pass.length()>5){
                login(email,pass);
            }else
                Toast.makeText(this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(this,"llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

}