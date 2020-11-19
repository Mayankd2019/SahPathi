package in.falconfour.sahpathi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

   private TextView login ;
   private Button done;

   private FirebaseAuth mAuth;
   private FirebaseAuth.AuthStateListener mAuthStateListener;
    // Access a Cloud Firestore instance from your Activity
    private FirebaseFirestore db;
    private HashMap<String,Object> userHashMap = new HashMap<>();

   private EditText signupEmailTv;
   private EditText signupPasswordTv;
   private EditText signupCollegeTv;
   private EditText signupBranchTv;
   private ProgressBar signupProgressBar;
   private SharedPreferences signupSharedPref;

   private static String email;
   private static String password;
   public static String branchSignUp;
   private static String college;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //basic  find view stuff
        initializingViews();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(myIntent);
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signupProgressBar.setVisibility(View.VISIBLE);
                email = signupEmailTv.getText().toString();
                password = signupPasswordTv.getText().toString();
                branchSignUp = signupBranchTv.getText().toString();
                college = signupCollegeTv.getText().toString();
                if(password.length() >= 6 && email != null ){
                    SharedPreferences.Editor signupSharedPreferenceEditor = signupSharedPref.edit();
                    signupSharedPreferenceEditor.putString("BRANCH", branchSignUp).apply();
                    signupSharedPreferenceEditor.putString("COLLEGE",college).apply();
                    userHashMap.put("BRANCH",branchSignUp);
                    userHashMap.put("COLLEGE",college);
                    userHashMap.put("token",null);
                    userHashMap.put("createdAt",null);
                    mAuth.createUserWithEmailAndPassword(email,password);
                }
            }
        });
    }

    private void initializingViews() {
        login= findViewById(R.id.logintext);
        done= findViewById(R.id.done_button);
        signupEmailTv = findViewById(R.id.sign_up_email);
        signupBranchTv = findViewById(R.id.sign_up_branch);
        signupPasswordTv = findViewById(R.id.sign_up_password);
        signupCollegeTv = findViewById(R.id.sign_up_college);
        signupProgressBar = findViewById(R.id.progressBar_signup);
        signupSharedPref = getApplicationContext().getSharedPreferences("SIGN_UP_DETAILS",Context.MODE_PRIVATE);
        db = FirebaseFirestore.getInstance();
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    //signed in
                    db.collection("users").document(user.getEmail()).set(userHashMap).addOnSuccessListener(SignupActivity.this,
                            new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            signupProgressBar.setVisibility(View.INVISIBLE);
                            Log.d("user info","written successfully");
                            goToApplication();
                        }
                    });
                } else {
                    //signed out
                }
            }
        };
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

    private void goToApplication() {
        Log.d("Sign up","done ");
        Intent myIntent2 =new Intent(SignupActivity.this, MainActivity.class);
        startActivity(myIntent2);

    }

}
