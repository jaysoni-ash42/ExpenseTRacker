package com.example.jaysoni.expensetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class Splash_Activity extends AppCompatActivity {
    FirebaseAuth mAuth;
    SignInButton signInButton;
    private static final int RC_SIGN_IN = 234;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_);
        mAuth = FirebaseAuth.getInstance();
        signInButton = findViewById(R.id.SignIn_button);
        createRequest();
        if (mAuth.getCurrentUser() != null) {
            signInButton.setVisibility(View.GONE);

            new Handler().postDelayed(new Runnable() {
                public void run() {
                    startActivity(new Intent(Splash_Activity.this,
                            MainActivity.class));
                    finish();
                }
            }, 3000);
        }
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    private void createRequest() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                assert account != null;
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Snackbar.make(signInButton, "Try Again", Snackbar.LENGTH_SHORT).setBackgroundTint(ContextCompat.getColor(Splash_Activity.this, R.color.colorPrimary))
                        .setTextColor(ContextCompat.getColor(Splash_Activity.this, R.color.colorPrimaryDark));
                Log.d("debugger",e.getMessage());

            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Snackbar.make(signInButton, "Logged in", Snackbar.LENGTH_SHORT).setBackgroundTint(ContextCompat.getColor(Splash_Activity.this, R.color.colorPrimary))
                                    .setTextColor(ContextCompat.getColor(Splash_Activity.this, R.color.colorPrimaryDark));
                            startActivity(new Intent(Splash_Activity.this,
                                    MainActivity.class));
                            finish();

                        } else {
                            Snackbar.make(signInButton, "Something went wrong", Snackbar.LENGTH_SHORT).setBackgroundTint(ContextCompat.getColor(Splash_Activity.this, R.color.colorPrimary))
                                    .setTextColor(ContextCompat.getColor(Splash_Activity.this, R.color.colorPrimaryDark));

                        }


                    }
                });
    }
}