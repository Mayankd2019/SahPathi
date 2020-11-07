package in.falconfour.sahpathi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignupActivity extends AppCompatActivity {

   private TextView login ;
   private Button done;

   private FirebaseAuth mAuth;
    // Access a Cloud Firestore instance from your Activity
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

   private EditText signupEmailTv;
   private EditText signupPasswordTv;
   private EditText signupCollegeTv;
   private EditText signupBranchTv;
   private ProgressBar signupProgressBar;

   private String email;
   private String password;
   private String branch;
   private String college;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //basic  find view stuff
        login= (TextView)findViewById(R.id.logintext);
        done=(Button)findViewById(R.id.done_button);
        signupEmailTv = findViewById(R.id.sign_up_email);
        signupBranchTv = findViewById(R.id.sign_up_branch);
        signupPasswordTv = findViewById(R.id.sign_up_password);
        signupCollegeTv = findViewById(R.id.sign_up_college);
        signupProgressBar = findViewById(R.id.progressBar_signup);
        final SharedPreferences signupSharedPref = getPreferences(Context.MODE_PRIVATE);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


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
                branch = signupBranchTv.getText().toString();
                college = signupCollegeTv.getText().toString();
                SharedPreferences.Editor signupSharedPreferenceEditor = signupSharedPref.edit();
                signupSharedPreferenceEditor.putString("BRANCH",branch);
                signupSharedPreferenceEditor.putString("COLLEGE",college);
                if(password.length() >= 6 && email != null ){
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignupActivity.this,
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        FirebaseUser currentUser = mAuth.getCurrentUser();
                                        goToApplication(currentUser);
                                    } else {

                                    }
                                }
                            });
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //check whether the user is already signed in or  not
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            goToApplication(currentUser);
        }
    }

    private void goToApplication(FirebaseUser currentUser) {
        Intent myIntent2 =new Intent(SignupActivity.this, MainActivity.class);
        startActivity(myIntent2);
    }

}