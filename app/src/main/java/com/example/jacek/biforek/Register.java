package com.example.jacek.biforek;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;

public class Register extends AppCompatActivity {

//zmienna prywatna
    private Button register;
    private EditText Name, Surname, Mail, Id, Password;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setupUIViews();

        firebaseAuth = FirebaseAuth.getInstance();

//Rejestracja użytkownika
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
//Wprowadzamy na serwer // wybór za pomocą czego logowanie
                    //String user_Id = Id.getText().toString().trim();
                    String user_email = Mail.getText().toString().trim();
                    String user_password = Password.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Register.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Register.this, MainActivity.class));
                            }
                            else{
                                Toast.makeText(Register.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

//Wskazanie odpowiednich elementów
    private void setupUIViews() {
        Name = (EditText) findViewById(R.id.editText1);
        Surname = (EditText) findViewById(R.id.editText2);
        Mail = (EditText) findViewById(R.id.editText3);
        Id = (EditText) findViewById(R.id.editText4);
        Password = (EditText) findViewById(R.id.editText5);
        register = (Button) findViewById(R.id.Button_Register);
    }

// Sprawdzanie czy wszystkie pola zostały wypełnione
    private Boolean validate() {
        Boolean result = false;

        String n = Name.getText().toString();
        String s = Surname.getText().toString();
        String m = Mail.getText().toString();
        String i = Id.getText().toString();
        String p = Password.getText().toString();

        if(n.isEmpty() || s.isEmpty() || m.isEmpty() || i.isEmpty() || p.isEmpty()){
            Toast.makeText(this, "Enter all filds", Toast.LENGTH_SHORT).show();
        }
        else
            result = true;

        return result;
    }
}
