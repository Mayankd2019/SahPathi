package in.falconfour.sahpathi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
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
        /*bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getTitle().toString()){
                    case "Time Table" :

                        break;
                    case "Resources" :
                        break;
                    case "Profile" :
                        break;
                }
                return false;
            }
        });*/
    }
}
