package blackmafia.gogoanime;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;
import java.util.Random;

public class roulette_activity extends AppCompatActivity {
    ImageView roulletIMG;
    TextView resultText;
    Button btnStart;
    Random r;
    int degree, degree_old;
    String titleString = "<font color=#efb810>GG</font><font color=#ffffff>anime</font>";
    private static final float FACTOR = 4.86f;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.roulette);
        roulletIMG = findViewById(R.id.rouletteID);
        resultText = findViewById(R.id.tvResult);
        btnStart = findViewById(R.id.btnRouletteStart);
        getSupportActionBar().setTitle(Html.fromHtml(titleString));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        r = new Random();
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                degree_old = degree % 360;
                degree = r.nextInt(3600) + 720;
                RotateAnimation rotate = new RotateAnimation(degree_old,degree,RotateAnimation.RELATIVE_TO_SELF,
                        0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
                rotate.setDuration(3600);
                rotate.setFillAfter(true);
                rotate.setInterpolator(new DecelerateInterpolator());
                rotate.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        resultText.setText("");
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        resultText.setText(CurrentPosition(360 - (degree % 360)));
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                roulletIMG.startAnimation(rotate);

            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private String CurrentPosition(int degrees){
        String result = "";

        if(degrees >= (FACTOR * 73) && degrees < 360 || (degrees >=0 && degrees < (FACTOR * 1))) {
            result = "Congratulation you won unlimited premium account";
        }else{
            result = "Good luck next time";
        }

        return result;
    }
}
