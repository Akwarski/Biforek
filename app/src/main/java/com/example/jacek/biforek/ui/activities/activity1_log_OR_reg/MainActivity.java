package com.example.jacek.biforek.ui.activities.activity1_log_OR_reg;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jacek.biforek.R;
import com.example.jacek.biforek.ui.activities.activity2_reg.Register;
import com.example.jacek.biforek.ui.activities.activity3_choose.Main2Activity_ALL;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

//zmienna prywatna
    private Button register, log;
    private EditText IDnumber;
    private EditText password;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IDnumber = (EditText) findViewById(R.id.editText1);
        password = (EditText) findViewById(R.id.editText2);
        log = (Button) findViewById(R.id.Button_log);
        register = (Button) findViewById(R.id.Button_Register_Now);

        firebaseAuth = FirebaseAuth.getInstance();
        //FirebaseUser user = firebaseAuth.getCurrentUser();
        /*if (user != null) {
            finish();
            startActivity(new Intent(MainActivity.this, ActionActivity.class));
        }*/

//Przycisk "Register Now" do przejścia do innej aktywności
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });

//Przycisk logowanie
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(IDnumber.getText().toString(), password.getText().toString());
            }
        });
    }

//Logowanie

    private void validate(String userName, String userPassword) {
        if(Check()) {
            firebaseAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, Main2Activity_ALL.class));
                    } else {
                        Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

// Sprawdzanie czy wszystkie pola zostały wypełnione
    private Boolean Check() {
        Boolean result = false;

        String e = IDnumber.getText().toString();
        String p = password.getText().toString();

        if(e.isEmpty() || p.isEmpty()){
            Toast.makeText(this, "Enter all filds", Toast.LENGTH_SHORT).show();
        }
        else
            result = true;

        return result;
    }
}
