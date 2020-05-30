package blackmafia.gogoanime;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.io.File;
import java.util.Objects;

public class info_activity extends AppCompatActivity {

    String titleString = "<font color=#efb810>GG</font><font color=#ffffff>info</font>";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_layout);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(Html.fromHtml(titleString));

        CardView cardRate = findViewById(R.id.CardViewRate);
        CardView cardPolicy = findViewById(R.id.CardViewPrivacyPolicy);
        CardView cardTerms = findViewById(R.id.CardViewTermNCondic);
        CardView cardFeedBack = findViewById(R.id.CardViewFeedback);
        TextView VersionText = findViewById(R.id.tvVersionApp);

        try{
            PackageInfo pInfo = this.getApplicationContext().getPackageManager().getPackageInfo(this.getPackageName(), 0);
            String version = pInfo.versionName;
            VersionText.setText("version: " + version);

        }catch (Exception e){
            e.printStackTrace();
        }
        cardRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rateMe();
            }
        });
        cardPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        cardTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        cardFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private void rateMe() {
        try{
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + this.getPackageName())));

        }catch (ActivityNotFoundException e){
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/details?id=" +
                    this.getPackageName())));

        }
    }
}
