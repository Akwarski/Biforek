package com.example.jacek.biforek;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddEventActivity extends AppCompatActivity {

//Zmienne prywatne
    private EditText where, when, which, alko, club, addition;
    private ProgressBar progressBar;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        where = (EditText) findViewById(R.id.Where);
        when = (EditText) findViewById(R.id.When);
        which = (EditText) findViewById(R.id.Which);
        alko = (EditText) findViewById(R.id.Alko);
        club = (EditText) findViewById(R.id.Club);
        addition = (EditText) findViewById(R.id.Addition);

        progressBar = (ProgressBar) findViewById(R.id.Progres);
        storageReference = FirebaseStorage.getInstance().getReference();

    }

    public void ShareButton(View view){
        String Where = where.getText().toString().trim();
        String When = when.getText().toString().trim();
        String Which = which.getText().toString().trim();
        String Alko = alko.getText().toString().trim();
        String Club = club.getText().toString().trim();


    }

    //wprowadzanie info na serwer


}
