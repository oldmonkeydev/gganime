package blackmafia.gogoanime;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Objects;

public class Movies_Activity extends AppCompatActivity {
    RecyclerView recyclerView;
    ProgressBar progressBar;
    AppCompatButton buttonPrevius, buttonNext;
    Context context;
    String link = Constants.GlobalUrl + "/anime-movies.html";
    ArrayList<ParseItem> parseItems;
    CategoryAdapter adapter;
    ContentMovies contentMovies;

    String titleString = "<font color=#efb810>GG</font><font color=#ffffff>anime</font>";
    int currentPage = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_withnavbuttons);
        InitValues();
        contentMovies = new ContentMovies();
        contentMovies.execute(link);
        buttonPrevius.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentPage <= 1 ){
                    Toast.makeText(context,"Page 1",Toast.LENGTH_SHORT).show();
                }else{
                    if(currentPage == 2){
                        parseItems.clear();
                        adapter.notifyDataSetChanged();
                        contentMovies = new ContentMovies();
                        contentMovies.execute(link);
                        currentPage--;
                    }else{
                        parseItems.clear();
                        adapter.notifyDataSetChanged();
                        currentPage --;
                        contentMovies = new ContentMovies();
                        contentMovies.execute(link + "?page="+currentPage);

                    }

                }

            }
        });
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parseItems.clear();
                adapter.notifyDataSetChanged();
                currentPage++;
                contentMovies = new ContentMovies();
                contentMovies.execute(link + "?page="+currentPage);
            }
        });

    }
    void InitValues(){
        recyclerView = findViewById(R.id.recyclerviewwithbuttons);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getSupportActionBar().setTitle(Html.fromHtml(titleString));
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        parseItems = new ArrayList<>();
        adapter = new CategoryAdapter(parseItems,getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.setItemViewCacheSize(30);
        progressBar = findViewById(R.id.progressBarRVwithbuttons);
        context = getApplicationContext();
        buttonPrevius = findViewById(R.id.btnprevious);
        buttonNext = findViewById(R.id.btnNext);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private class ContentMovies extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(progressBar.getVisibility() == View.GONE){
                progressBar.setVisibility(View.VISIBLE);
                progressBar.startAnimation(AnimationUtils.loadAnimation(context, android.R.anim.fade_in));
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(progressBar.getVisibility() == View.VISIBLE){
                progressBar.setVisibility(View.GONE);
//                progressBar.startAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_out));
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        protected Void doInBackground(String... strings) {
            String url = strings[0];

            try{
                Document doc = Jsoup.connect(url).get();

                Elements data = doc.select("div[class=last_episodes]").select("ul[class=items]").select("li");
                int size = data.size();
                for(int i=0;i<size;i++){
                    ParseItem parseItem = new ParseItem();
                    parseItem.setImgUrl(data.select("div[class=img]").eq(i).select("a")
                            .select("img")
                            .attr("src"));
                    parseItem.setTitle(data.select("div[class=img]").eq(i).select("a")
                            .attr("title"));
                    parseItem.setUrlAnime(data.select("div[class=img]").eq(i).select("a")
                            .attr("href"));

                    parseItems.add(parseItem);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }
}
