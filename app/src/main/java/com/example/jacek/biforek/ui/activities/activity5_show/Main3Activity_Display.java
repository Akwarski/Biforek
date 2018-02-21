package com.example.jacek.biforek.ui.activities.activity5_show;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.jacek.biforek.R;
import com.example.jacek.biforek.utils.BaseActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Main3Activity_Display extends BaseActivity {

private DatabaseReference mDatabase;
private ListView listView;
private ArrayList<String> arrayList = new ArrayList<>();
private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3__display);


        mDatabase = FirebaseDatabase.getInstance().getReference();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);

        listView = findViewById(R.id.database_list_view);

        listView.setAdapter(adapter);

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String string = dataSnapshot.getValue(String.class);

                arrayList.add(string);

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}

























/*








package com.example.jacek.biforek.ui.activities.activity5_show;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jacek.biforek.R;
import com.example.jacek.biforek.ui.fragments.BlankFragment;
import com.example.jacek.biforek.utils.BaseActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.example.jacek.biforek.utils.FirebaseUtils;
import com.example.jacek.biforek.ui.fragments.BlankFragment;
import com.example.jacek.biforek.ui.activities.activity5_show.fragments.ShowMyEventFragment;
import com.example.jacek.biforek.models.User;

public class Main3Activity_Display extends BaseActivity {

    private ValueEventListener mUserValueEventListener;
    private DatabaseReference mUserRef;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private TextView mNameTextView;
    private TextView mEmailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3__display);

        getSupportFragmentManager().beginTransaction().replace(R.id.container2, new ShowMyEventFragment()).commit();
        //init();

        FrameLayout Space = findViewById(R.id.container2);
        View ListSpace = Space;
        initSpace(ListSpace);
    }

    private void init() {
        if (mFirebaseUser != null) {
            mUserRef = FirebaseUtils.getUserRef(mFirebaseUser.getEmail().replace(".", ","));
        }
    }

    private void initSpace(View listSpace) {


        mUserValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    User users = dataSnapshot.getValue(User.class);

                    mNameTextView.setText(users.getNameAndSurname());
                    mEmailTextView.setText(users.getEmail());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }




/*

    @Override
    protected void onStart() {
        super.onStart();
        //mAuth.addAuthStateListener(mAuthStateListener);
        if (mUserRef != null) {
            mUserRef.addValueEventListener(mUserValueEventListener);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthStateListener != null)
            mAuth.removeAuthStateListener(mAuthStateListener);
        if (mUserRef != null)
            mUserRef.removeEventListener(mUserValueEventListener);
    }*/
/*
}















 */