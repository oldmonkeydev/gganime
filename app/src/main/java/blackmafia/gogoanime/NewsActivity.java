package blackmafia.gogoanime;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Objects;

public class NewsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressBar progressBar;
    String BaseUrl = "https://www.cbr.com";
    String homePage = "/category/anime-news/";
    NewsAdapter adapter;
    ArrayList<NewsModel> list;
    String titleString = "<font color=#efb810>GG</font><font color=#ffffff>news</font>";
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity);
        InitElements();
        loadItems();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadItems();
            }
        });

    }
    private void loadItems(){
        ContentNews content = new ContentNews();
        content.execute();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public void InitElements(){
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(Html.fromHtml(titleString));
        recyclerView = findViewById(R.id.recyclerViewNews);
        progressBar = findViewById(R.id.progressBarNews);
        swipeRefreshLayout = findViewById(R.id.SwipeRefreshLayoutNews);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new NewsAdapter(list,getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.setItemViewCacheSize(30);



    }



    private class ContentNews extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(NewsActivity.this, android.R.anim.fade_in));
            list.clear();
            adapter.notifyDataSetChanged();
        }
        @Override
        protected void onCancelled() {
            super.onCancelled();
            if(this.getStatus() == Status.RUNNING){
                this.cancel(true);
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(NewsActivity.this, android.R.anim.fade_out));
            adapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try{
                String url = BaseUrl + homePage;

                Log.d("BaseUrl: ", url);

                Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
                        "(KHTML, like Gecko) Chrome/81.0.4044.129 Safari/537.36 Edg/81.0.416.68").get();
                Elements data = doc.select("div[class=listing]").select("section[class=listing-content]")
                        .select("div[class=browse-half clip-half]").select("article");

                for(int i = 0; i<data.size(); i++){

                    String title = data.select("div[class=bc-info]").eq(i).select("h3[class=bc-title]").select("a")
                            .attr("title");
                    String info = data.select("div[class=bc-info]").eq(i).select("p[class=bc-excerpt]")
                            .text();
                    String link = data.select("a[class=bc-img-link]").eq(i)
                            .attr("href");
                    String date = data.select("div[class=bc-info]").eq(i).select("div[class=bc-details]")
                            .select("time[class=bc-date]")
                            .text();

                    String tempLink = BaseUrl + link;

                    Log.d("info: ","title: " + title + " info: " + info + " link: " + link);

                    NewsModel model = new NewsModel(title,info,tempLink,date);
                    list.add(model);
                }





            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }
}
