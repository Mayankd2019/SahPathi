package in.falconfour.sahpathi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OnBoarding extends AppCompatActivity implements View.OnClickListener {
    private ViewPager mSlideViewPager;
    private LinearLayout mDotsLayout;

    private TextView[] mDots;

    private SliderAdapter sliderAdapter;

    private Button buttonPrevious, buttonNext;

    private  int mCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        mSlideViewPager = findViewById(R.id.slider);
        mDotsLayout = findViewById(R.id.dots);
        buttonNext = (Button)findViewById(R.id.next_btn);
        buttonPrevious= (Button)findViewById(R.id.btn_previous);


        sliderAdapter = new SliderAdapter(this);

        mSlideViewPager.setAdapter(sliderAdapter);

        addDotsIndicator(0);

        mSlideViewPager.addOnPageChangeListener(viewListener);

        buttonNext.setOnClickListener((View.OnClickListener) this);
        buttonPrevious.setOnClickListener((View.OnClickListener) this);

    }

    public void addDotsIndicator(int position){

        mDots = new TextView[3];
        mDotsLayout.removeAllViews(); //without this multiple number of dots will be created

        for (int i = 0; i< mDots.length; i++){
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;")); //code for the dot icon like thing
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorAccent));

            mDotsLayout.addView(mDots[i]);
        }

        if (mDots.length>0){
            mDots[position] .setTextColor(getResources().getColor(R.color.colorPrimary)); //setting currently selected dot to white
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            addDotsIndicator(position);

            mCurrentPage = position;

            if (position == 0){//we are on first page
                buttonNext.setEnabled(true);
                buttonPrevious.setEnabled(false);
                buttonPrevious.setVisibility(View.INVISIBLE);

                buttonNext.setText("Next");
                buttonPrevious.setText("");
            } else if (position == mDots.length - 1){ //last page
                buttonNext.setEnabled(true);
                buttonPrevious.setEnabled(true);
                buttonPrevious.setVisibility(View.INVISIBLE);

                buttonNext.setText("Finish");
                buttonPrevious.setText("Back");
            } else { //neither on first nor on last page
                buttonNext.setEnabled(true);
                buttonPrevious.setEnabled(true);
                buttonPrevious.setVisibility(View.INVISIBLE);

                buttonNext.setText("Next");
                buttonPrevious.setText("Back");
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.next_btn:
                if (buttonNext.getText().toString().equalsIgnoreCase("next")){
                    mSlideViewPager.setCurrentItem(mCurrentPage + 1);
                } else {
                    startActivity(new Intent(OnBoarding.this, LoginActivity.class));
                }
                break;
            default: break;
        }
    }
}
