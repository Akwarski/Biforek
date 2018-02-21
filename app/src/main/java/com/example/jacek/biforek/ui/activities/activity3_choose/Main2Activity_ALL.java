package com.example.jacek.biforek.ui.activities.activity3_choose;

import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.jacek.biforek.R;
import com.example.jacek.biforek.ui.fragments.BlankFragment;
import com.example.jacek.biforek.utils.BaseActivity;
import com.example.jacek.biforek.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class Main2Activity_ALL extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ValueEventListener mUserValueEventListener;
    private TextView mNameTextView;
    private TextView mEmailTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2__all); //było activity_main2__all
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //TA AKTYWNOŚC JEST DO BLANKFRAGMENT MA WYŚWIETLAĆ WYBÓR MIĘDZY DODANIEM A WYŚWIETLENIEM EVENTÓW!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


        getSupportFragmentManager().beginTransaction().replace(R.id.container, new BlankFragment()).commit();


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Do wyświetlania w nav zrobić
        View navHeaderView = navigationView.getHeaderView(0);
        initNavHeader(navHeaderView);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Funkcja do wyświetlania w panelu nav
    private void initNavHeader(View view) {
        mNameTextView = view.findViewById(R.id.textview_name);
        mEmailTextView = view.findViewById(R.id.textView_email);

        mUserValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    User users = dataSnapshot.getValue(User.class);

                    //getUName bo na żółto
                    //users.setNameAndSurname(users.getUName(), users.getUSurname());
                    mNameTextView.setText(users.getUSurname());
                    mEmailTextView.setText(users.getEmail());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }


     /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2_activity__all, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //signOut();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    // DODAJE
    // To Wyświtla dane z serwera:
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
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

*/

}















/*






package com.example.jacek.biforek.ui.activities.activity3_choose;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.annotation.NonNull; // dodałem
import android.content.Intent; // dodałem
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide; // dodałem

import com.example.jacek.biforek.R;
import com.example.jacek.biforek.ui.fragments.BlankFragment;
import com.example.jacek.biforek.utils.BaseActivity;
import com.example.jacek.biforek.utils.FirebaseUtils;
import com.example.jacek.biforek.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class Main2Activity_ALL extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DatabaseReference mUserRef;
    private ValueEventListener mUserValueEventListener;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    //private ImageView mDisplayImageView;
    private TextView mNameTextView;
    private TextView mEmailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2__all);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        init();
        //getSupportFragmentManager().beginTransaction().replace(R.id.container, new BlankFragment()).commit();




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Do wyświetlania w nav zrobić
        View navHeaderView = navigationView.getHeaderView(0);
        initNavHeader(navHeaderView);
    }

    private void init() {
        if (mFirebaseUser != null) {
            mUserRef = FirebaseUtils.getUserRef(mFirebaseUser.getEmail().replace(".", ","));
        }
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Funkcja do wyświetlania w panelu nav
    private void initNavHeader(View view) {
        //mDisplayImageView = (ImageView) view.findViewById(R.id.imageView_display);  //Do wyświetlenia zdjęcia w kółeczku
        mNameTextView = (TextView) view.findViewById(R.id.textview_name);
        mEmailTextView = (TextView) view.findViewById(R.id.textView_email);

        mUserValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    User users = dataSnapshot.getValue(User.class);

                    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    //Do zdjęcia
                    //
                    //Glide.with(Main2Activity_ALL.this)
                    //        .load(users.getPhotoUrl())
                    //        .into(mDisplayImageView);


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Coś z getUName trzeba zrobić bo na żółto
                    users.setNameAndSurname(users.getUName(), users.getUSurname());
                            mNameTextView.setText(users.getNameAndSurname());
                            mEmailTextView.setText(users.getEmail());
                            }
                            }

@Override
public void onCancelled(DatabaseError databaseError) {

        }
        };
        }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////




@Override
public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
        drawer.closeDrawer(GravityCompat.START);
        } else {
        super.onBackPressed();
        }
        }

@Override
public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2_activity__all, menu);
        return true;
        }

@Override
public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
        //signOut();
        }

        return super.onOptionsItemSelected(item);
        }

@SuppressWarnings("StatementWithEmptyBody")
@Override
public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
        // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
        }

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // DODAJE

    //@Override
    //protected void onStart() {
    //    super.onStart();
    //    mAuth.addAuthStateListener(mAuthStateListener);
    //    if (mUserRef != null) {
    //        mUserRef.addValueEventListener(mUserValueEventListener);
    //    }
    //}

    //@Override
    //protected void onStop() {
    //    super.onStop();
    //    if (mAuthStateListener != null)
    //        mAuth.removeAuthStateListener(mAuthStateListener);
    //    if (mUserRef != null)
    //        mUserRef.removeEventListener(mUserValueEventListener);
   // }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////




        }







 */