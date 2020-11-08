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
    // Access a Cloud Firestore instance from your Activity
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private HashMap<String,Object> userHashMap = new HashMap<>();

   private EditText signupEmailTv;
   private EditText signupPasswordTv;
   private EditText signupCollegeTv;
   private EditText signupBranchTv;
   private ProgressBar signupProgressBar;

   private String email;
   private String password;
   public String branchSignUp;
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
                branchSignUp = signupBranchTv.getText().toString();
                college = signupCollegeTv.getText().toString();
                SharedPreferences.Editor signupSharedPreferenceEditor = signupSharedPref.edit();
                signupSharedPreferenceEditor.putString("BRANCH", branchSignUp).apply();
                signupSharedPreferenceEditor.putString("COLLEGE",college).apply();
                if(password.length() >= 6 && email != null ){
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignupActivity.this,
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        FirebaseUser currentUser = mAuth.getCurrentUser();
                                        //userHashMap.put(getString(R.string.user_data_email),email);
                                        userHashMap.put(getString(R.string.user_data_college),college);
                                        userHashMap.put(getString(R.string.user_data_branch), branchSignUp);
                                        db.collection(getString(R.string.collection_user)).document(email).set(userHashMap)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d("yo congo","user add ho gaya");
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d("ye lo bd","yahi tull ho gaya");
                                            }
                                        });
                                        goToApplication(currentUser);
                                    } else {
                                        Log.d("bcc","ye user ka identity nahi accept kiya bc");
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
        Log.d("ye to ho gaya be","congo");
        Intent myIntent2 =new Intent(SignupActivity.this, MainActivity.class);
        startActivity(myIntent2);

    }

}
