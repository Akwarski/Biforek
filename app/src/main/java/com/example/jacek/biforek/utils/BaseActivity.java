package com.example.jacek.biforek.utils;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.jacek.biforek.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class BaseActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener {

    protected FirebaseUser mFirebaseUser;
    protected GoogleApiClient mGoogleApiClient;
    protected FirebaseAuth mAuth;
    private ProgressDialog mProgressDialog; //Not really needed but I like having it
    private GoogleSignInOptions mGso;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //In comment were each command in this class I think
        mAuth = FirebaseAuth.getInstance();
        if (mAuth != null) {
            mFirebaseUser = mAuth.getCurrentUser();
        }

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Co zamiast tego GoogleSignInOptions:

        mGso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, mGso)
                .build();
    }

    protected void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            //In comment was only below command
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCancelable(false);
        }
        else {
            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }
        }
    }

    protected void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide(); //było mProgressDialog.dismiss();
        }
    }

    //All was in comment
    protected void signOut() {
        mAuth.signOut();

        Auth.GoogleSignInApi.signOut(mGoogleApiClient)
                /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                // Tu było ResultCallback<Status>()     bez "s" czyli tak jak napisałem
                .setResultCallback(new ResultCallbacks<Status>() {

                    @Override
                    public void onSuccess(@NonNull Status status) {
                        Toast.makeText(BaseActivity.this, getString(R.string.Signed_Out), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(@NonNull Status status) {
                        Toast.makeText(BaseActivity.this, getString(R.string.Signed_Out_Failed), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissProgressDialog();
    }

}
