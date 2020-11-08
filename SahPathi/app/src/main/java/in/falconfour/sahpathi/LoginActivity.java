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

    private FirebaseAuth mAuth;

    private String loginEmail;
    private String loginPassword;

    // Access a Cloud Firestore instance from your Activity
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //basic findview stuff
        loginEmailEt = findViewById(R.id.login_email);
        loginPasswordEt = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        signupTv = findViewById(R.id.signup_tv);
        loginProgressBar = findViewById(R.id.progressBar_login);

        //get FireBase Authentication object instance
        mAuth = FirebaseAuth.getInstance();

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
                mAuth.signInWithEmailAndPassword(loginEmail,loginPassword).addOnCompleteListener(LoginActivity.this,
                        new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            loginProgressBar.setVisibility(View.INVISIBLE);
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            goToApplication(currentUser);
                        } else {

                        }
                    }
                });
            }
        });
    }

    private void goToApplication(FirebaseUser currentUser) {
        Intent myIntent2 =new Intent(LoginActivity.this, MainActivity.class);
        startActivity(myIntent2);
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            //Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        // Get new FCM registration token
                        String token = task.getResult();
                        /*SharedPreferences loginActivitySharedPreferences = getPreferences(MODE_PRIVATE);
                        loginActivitySharedPreferences.edit().putString("TOKEN", token);*/
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("token", token);
                        hashMap.put("createdAt", Calendar.getInstance().getTime());
                        db.collection("tokens").add(hashMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("TAG", "Error adding document", e);
                                    }
                                    // Log and toast
                        /*String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, msg);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();*/
                                });
                    }
                });
    }
}
