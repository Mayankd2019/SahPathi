package in.falconfour.sahpathi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Calendar;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    // Access a Cloud Firestore instance from your Activity
    private FirebaseFirestore db ;
    private FirebaseAuth mAuth;
    private DocumentReference documentReference;
    private FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        ActionBar mainActionBar = getSupportActionBar();
        mainActionBar.setLogo(R.drawable.sahpathilogo);
        mainActionBar.setDisplayUseLogoEnabled(true);

        NavController navController = Navigation.findNavController(this,R.id.fragment);
        bottomNavigationView = findViewById(R.id.main_bottom_nav_view);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

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
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("token", token);
                        hashMap.put("createdAt", Calendar.getInstance().getTime());
                        db.collection("users").document(mUser.getEmail()).update(hashMap);
                    }
                });

    }
}
