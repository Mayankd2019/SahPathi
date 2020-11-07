package in.falconfour.sahpathi;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;




public class SplashScreen extends AppCompatActivity {

    public static final int SPLASH_TIME_OUT=4000;

    //Animation
    private Animation top_animation, bottom_animation, middle_animation;
    private View first, second, third, fourth, fifth, main;
    private TextView splash_msg;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {
                Intent intent= new Intent(SplashScreen.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);

    }
}
