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

public class categories_activity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressBar progressBar;
    AppCompatButton buttonPrevius, buttonNext;
    String Category;
    Context context;
    CategoryAdapter adapter;
    ArrayList<ParseItem> list;
    int currentPage = 1;
    String link;
    ContentCategory contentCategory;
    String titleString = "<font color=#efb810>GG</font><font color=#ffffff>anime</font>";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_withnavbuttons);
        InitValues();
        link = getIntent().getStringExtra("link");
        contentCategory = new ContentCategory();
        contentCategory.execute(link);

        buttonPrevius.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentPage <= 1 ){
                    Toast.makeText(context,"Page 1",Toast.LENGTH_SHORT).show();
                }else{
                    if(currentPage == 2){
                        list.clear();
                        adapter.notifyDataSetChanged();
                        contentCategory = new ContentCategory();
                        contentCategory.execute(link);
                        currentPage--;
                    }else{
                        list.clear();
                        adapter.notifyDataSetChanged();
                        currentPage --;
                        contentCategory = new ContentCategory();
                        contentCategory.execute(link + "?page="+currentPage);

                    }

                }

            }
        });
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.clear();
                adapter.notifyDataSetChanged();
                currentPage++;
                contentCategory = new ContentCategory();
                contentCategory.execute(link + "?page="+currentPage);
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
        list = new ArrayList<>();
        adapter = new CategoryAdapter(list,getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.setItemViewCacheSize(30);
        progressBar = findViewById(R.id.progressBarRVwithbuttons);
        Category = getIntent().getStringExtra("category_id");
        context = getApplicationContext();
        buttonPrevius = findViewById(R.id.btnprevious);
        buttonNext = findViewById(R.id.btnNext);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private class ContentCategory extends AsyncTask<String, Void, Void> {

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
        protected void onCancelled() {
            super.onCancelled();
            if(this.getStatus() == Status.RUNNING){
                this.cancel(true);
            }
        }

        @Override
        protected Void doInBackground(String... strings) {
            String url = strings[0];

            try{
                Document doc = Jsoup.connect(url).get();
                Elements data = doc.select("div[class=last_episodes]").select("ul[class=items]").select("li");

                int size = data.size();
                for(int i = 0; i<size;i++){
                    String imgUrl = data.select("div[class=img]").eq(i).select("a")
                            .select("img")
                            .attr("src");
                    String title = data.select("div[class=img]").eq(i).select("a")
                            .attr("title");
                    String AnimeUrl = data.select("div[class=img]").eq(i).select("a")
                            .attr("href");
                    ParseItem temp = new ParseItem();
                    temp.setImgUrl(imgUrl);
                    temp.setTitle(title);
                    temp.setUrlAnime(AnimeUrl);
                    list.add(temp);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }
}
