package blackmafia.gogoanime;

import android.os.Bundle;
import android.text.Html;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class webview_news_activity extends AppCompatActivity {

    WebView webView;
    String url;
    String titleString = "<font color=#efb810>GG</font><font color=#ffffff>favorites</font>";
    MyWebViewClient myWebViewClient;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_webview);
        initWebView();
        myWebViewClient = new MyWebViewClient();
        try{
            url = getIntent().getStringExtra("link");
        }catch (Exception e){
            e.printStackTrace();
        }

        webView.setWebViewClient(myWebViewClient);
        webView.loadUrl(url);


    }
    public void initWebView(){
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(Html.fromHtml(titleString));
        webView = findViewById(R.id.webview_news);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NORMAL);

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String url) {
            return false;
        }


    }
}
