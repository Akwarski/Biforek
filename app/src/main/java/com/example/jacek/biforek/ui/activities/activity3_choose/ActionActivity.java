package com.example.jacek.biforek.ui.activities.activity3_choose;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.jacek.biforek.R;
import com.example.jacek.biforek.ui.activities.activity5_show.ShowEventsActivity;
import com.example.jacek.biforek.ui.fragments.BlankFragment;

public class ActionActivity extends AppCompatActivity {

//Zm viewHolder.setNumCOmments(String.valueOf(model.getNumComments()));ienne prywatne
    private Button add, show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);

        add = (Button) findViewById(R.id.button1);
        show = (Button) findViewById(R.id.button2);

// Przejście do okna z dodawaniem eventów
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActionActivity.this, BlankFragment.class);
                startActivity(intent);
            }
        });

// Przejście do okna z wyświetlaniem eventów
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActionActivity.this, ShowEventsActivity.class);
                startActivity(intent);
            }
        });
    }
}
