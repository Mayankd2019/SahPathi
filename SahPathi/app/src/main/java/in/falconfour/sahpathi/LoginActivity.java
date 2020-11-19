package in.falconfour.sahpathi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Calendar;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private EditText loginEmailEt;
    private EditText loginPasswordEt;
    private Button loginButton;
    private TextView signupTv;
    private ProgressBar loginProgressBar;

    private String loginEmail;
    private String loginPassword;

    // Access a Cloud Firestore instance from your Activity
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //basic findview stuff
        initializingViews();
        settingUpClickListeners();
    }

    private void initializingViews() {
        loginEmailEt = findViewById(R.id.login_email);
        loginPasswordEt = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        signupTv = findViewById(R.id.signup_tv);
        loginProgressBar = findViewById(R.id.progressBar_login);

        //get FireBase Authentication object instance
        mAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user =  firebaseAuth.getCurrentUser();
                if (user != null){
                    //signed in
                    loginProgressBar.setVisibility(View.INVISIBLE);
                    goToApplication(user);
                } else {
                    //signed out
                }
            }
        };
    }

    private void settingUpClickListeners() {
        signupTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SignupActivity.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginProgressBar.setVisibility(View.VISIBLE);
                loginEmail = loginEmailEt.getText().toString();
                loginPassword = loginPasswordEt.getText().toString();
                mAuth.signInWithEmailAndPassword(loginEmail,loginPassword);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAuth.removeAuthStateListener(mAuthStateListener);
    }

    private void goToApplication(FirebaseUser currentUser) {

        Intent myIntent2 =new Intent(LoginActivity.this, MainActivity.class);
        startActivity(myIntent2);
    }
}
