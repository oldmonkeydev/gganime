package blackmafia.gogoanime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView recyclerView2;
    private ProgressBar progressBar;
    private LinearLayout noanime;
    private String searchurl;
    private FrameLayout viewPager2;
    private SearchAdapter DataAdapter;
    private ArrayList<Anime> mAnimeList = new ArrayList<>();
    private DrawerLayout drawerLayout;
    private ViewPagerAdapter viewPagerAdapter;
    private fragment_recent f_recent;
    private FavLayoutActivity f_fav;
    private category_list_activity f_cat;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private BottomNavigationView bottomNavigationView;
    private SearchingAnime x = new SearchingAnime();
    String titleString = "<font color=#efb810>GG</font><font color=#ffffff>anime</font>";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!haveNetworkConnection(getApplicationContext()))
        {
            LinearLayout linearLayout1=findViewById(R.id.notvisiblelinearlayout);
            linearLayout1.setVisibility(View.VISIBLE);

        }else{
            InitItems();
        }


    }
    private void InitItems(){
        getSupportActionBar().setTitle(Html.fromHtml(titleString));
        drawerLayout = findViewById(R.id.NavigationLayout);
        f_recent = new fragment_recent();
        f_fav = new FavLayoutActivity();
        f_cat = new category_list_activity();
        AnimeDataBase animeDataBase = AnimeDataBase.getInstance(getApplicationContext());
        viewPager2 = findViewById(R.id.viewPagerMain);

        bottomNavigationView = findViewById(R.id.btnNav);
        getSupportFragmentManager().beginTransaction().replace(R.id.viewPagerMain,new fragment_recent()).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){
                    case R.id.btn_home:
                        fragment = new fragment_recent();
                        break;
                    case R.id.btn_Fav:
                        fragment = new FavLayoutActivity();
                        break;
                    case R.id.category:
                        fragment = new category_list_activity();
                        break;
                    case R.id.btn_conf:
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.viewPagerMain,fragment).commit();
                return true;
            }
        });
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent i;

                switch (item.getItemId()){
                    case R.id.anime_list_search:
                        i = new Intent(getApplicationContext(),AnimeList.class);
                        startActivity(i);
                        break;

                    case R.id.btn_news:
                        i = new Intent(getApplicationContext(),NewsActivity.class);
                        startActivity(i);
                        break;
                    case R.id.NewSeason:
                        i = new Intent(getApplicationContext(),NewSeasonActivity.class);
                        startActivity(i);
                        break;
                    case R.id.movies:
                        i = new Intent(getApplicationContext(),Movies_Activity.class);
                        startActivity(i);
                        break;
                    case R.id.Popular:
                        i = new Intent(getApplicationContext(),PopularSeriesActivity.class);
                        startActivity(i);
                        break;
                    case R.id.gacha:
                        i = new Intent(getApplicationContext(),roulette_activity.class);
                        startActivity(i);
                        break;
                    case R.id.info:
                        i = new Intent(getApplicationContext(),info_activity.class);
                        startActivity(i);
                        break;

                }
                item.setChecked(true);
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        noanime=findViewById(R.id.noanime);
        progressBar = findViewById(R.id.progressBar);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(haveNetworkConnection(getApplicationContext()))
        {getMenuInflater().inflate(R.menu.toolbar_item, menu);

        //handleNavDrawer();
        toggle = new ActionBarDrawerToggle(this,drawerLayout,
                R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            MenuItem search = menu.findItem(R.id.action_search);
            SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    newText = newText.toLowerCase();
                    noanime.setVisibility(View.GONE);

                    if (newText.length() >= 3) {
                        RecyclerView recyclerView2 = findViewById(R.id.recyclerview2);
                        recyclerView2.setVisibility(View.VISIBLE);
                        viewPager2.setVisibility(View.GONE);

                        searchurl = Constants.GlobalUrl +"/search.html?keyword=" + newText;
                        if (x.getStatus() == AsyncTask.Status.RUNNING)
                            x.cancel(true);
                        x = new SearchingAnime();
                        x.execute();

                    }
                    else
                    {
                        if (x.getStatus() == AsyncTask.Status.RUNNING)
                            x.cancel(true);
                        RecyclerView recyclerView2 = findViewById(R.id.recyclerview2);
                        recyclerView2.setVisibility(View.GONE);
                        viewPager2.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);

                    }
                    return false;
                }
            });
            return true;
        }


        return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        item.setChecked(true);
        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @SuppressLint("StaticFieldLeak")
    private class SearchingAnime extends AsyncTask<Void, Void, Void>{
        public SearchingAnime() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            recyclerView2 = findViewById(R.id.recyclerview2);
            recyclerView2.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressBar.setVisibility(View.GONE);
            if (mAnimeList.size() == 0)
                noanime.setVisibility(View.VISIBLE);
            else {
                recyclerView2.setVisibility(View.VISIBLE);

                DataAdapter = new SearchAdapter(getApplicationContext(), mAnimeList,MainActivity.this);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView2.setHasFixedSize(true);
                recyclerView2.setDrawingCacheEnabled(true);
                recyclerView2.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                recyclerView2.setItemViewCacheSize(30);
                recyclerView2.setLayoutManager(mLayoutManager);
                recyclerView2.setAdapter(DataAdapter);

            }


        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {

                org.jsoup.nodes.Document searching = Jsoup.connect(searchurl).get();
                DataAdapter = new SearchAdapter();
                DataAdapter.notifyItemRangeRemoved(0, mAnimeList.size());
                mAnimeList.clear();

                Elements li = searching.select("div[class=main_body]").select("div[class=last_episodes]").select("ul[class=items]").select("li");
                for (int i = 0; i < li.size(); i++) {
                    Anime anime = new Anime();
                    anime.setLink(li.select("div[class=img]").eq(i).select("a").attr("abs:href"));
                    anime.setName(li.select("div[class=img]").eq(i).select("a").attr("title"));
                    anime.setImageLink(li.select("div[class=img]").eq(i).select("img").attr("src"));
                    Log.d("items","link: " + anime.getLink() + " Name: " + anime.getName() + " imageLink: " + anime.getImageLink());
                    mAnimeList.add(anime);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    boolean haveNetworkConnection(android.content.Context context) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }


}
