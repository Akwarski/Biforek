package com.example.jacek.biforek.ui.activities.activity2_reg;

import android.content.Intent;
import android.support.annotation.NonNull;
import com.example.jacek.biforek.models.User;
import com.example.jacek.biforek.utils.FirebaseUtils;
import com.example.jacek.biforek.utils.BaseActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jacek.biforek.R;
import com.example.jacek.biforek.ui.activities.activity1_log_OR_reg.MainActivity;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

public class Register extends BaseActivity { //tu było: extends AppCompatActivity

//zmienna prywatna
    private Button register;
    private EditText Name, Surname, Mail, Id, Password;
    private FirebaseAuth firebaseAuth;
    private static final String TAG = Register.class.getSimpleName();

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
                    final String user_email = Mail.getText().toString().trim();
                    String user_password = Password.getText().toString().trim();
                    final String n = Name.getText().toString();
                    final String s = Surname.getText().toString();


                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Register.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                User user = new User();
                                //String photoUrl = null;
                                /*if (account.getPhotoUrl() != null) {
                                    user.setPhotoUrl(account.getPhotoUrl().toString());
                                }*/

                                user.setEmail(user_email);
                                user.setUName(n);
                                user.setUSurname(s);
                                user.setUid(mAuth.getCurrentUser().getUid());

                                FirebaseUtils.getUserRef(user_email.replace(".", ","))
                                        .setValue(user, new DatabaseReference.CompletionListener() {
                                            @Override
                                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                                mFirebaseUser = mAuth.getCurrentUser();
                                                finish();
                                            }
                                        });
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
