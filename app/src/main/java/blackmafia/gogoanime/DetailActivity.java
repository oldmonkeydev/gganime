package blackmafia.gogoanime;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;

import java.util.Objects;

public class DetailActivity extends AppCompatActivity {

    private ImageView imageView;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Context context;
    private AnimeDataBase db;

    private String animeUrl;
    private activity_fragment_sumary fragmentDetailActivity;
    private FragmentEpisodesActivity activity_fragment_episodes;
    private CharSequence titlee;
    private CollapsingToolbarLayout collapse;
    private favDB _favDB;
    private int fl = 1;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Toolbar toolbar = findViewById(R.id.toolbar_id);
        collapse = findViewById(R.id.collapsingTollbar);
        titlee = getIntent().getStringExtra("title");
        fl = getIntent().getIntExtra("fl",1);
        collapse.setTitle(titlee);
        db = AnimeDataBase.getInstance(context);

        context = this;
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        animeUrl = getIntent().getStringExtra("animeUrl");

        Log.d("AnimeImg",getIntent().getStringExtra("image"));
        Log.d("Title",titlee.toString());
        Log.d("animeUrl: ", animeUrl);


        imageView = findViewById(R.id.imageView);


        Picasso.get().load(getIntent().getStringExtra("image")).into(imageView);
        viewPager = findViewById(R.id.ViewPager_id);
        tabLayout = findViewById(R.id.tabLayout_id);

        fragmentDetailActivity = new activity_fragment_sumary();
        Bundle bundle = new Bundle();
        bundle.putString("animeUrl",animeUrl);
        bundle.putInt("fl",fl);

        fragmentDetailActivity.setArguments(bundle);
        activity_fragment_episodes = new FragmentEpisodesActivity();
        activity_fragment_episodes.setArguments(bundle);
        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),0);
        viewPagerAdapter.AddFragment(fragmentDetailActivity,"Summary");
        viewPagerAdapter.AddFragment(activity_fragment_episodes,"Episodes");
        viewPager.setAdapter(viewPagerAdapter);
        MaterialFavoriteButton materialFavoriteButton = findViewById(R.id.favorite_button);
        if(Exists(titlee.toString())) {
            materialFavoriteButton.setFavorite(true);
            materialFavoriteButton.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {

                @Override
                public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                    if (favorite) {
                        if (fl == 1) {
                            String tmp = AnimeNameCut(animeUrl);
                            saveFav(tmp);
                        } else {
                            saveFav(animeUrl);
                        }

                        Snackbar.make(buttonView, "Added to Favorite",
                                Snackbar.LENGTH_SHORT).show();
                    } else {
                        String tmp = titlee.toString();
                        _favDB = new favDB(context);
                        _favDB.RemoveFav(tmp);
                        Snackbar.make(buttonView, "Removed from Favorite",
                                Snackbar.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            materialFavoriteButton.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {

                @Override
                public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                    if (favorite) {
                        if (fl == 1) {
                            String tmp = AnimeNameCut(animeUrl);
                            saveFav(tmp);
                        } else {
                            saveFav(animeUrl);
                        }

                        Snackbar.make(buttonView, "Added to Favorite",
                                Snackbar.LENGTH_SHORT).show();
                    } else {
                        String tmp = titlee.toString();
                        _favDB = new favDB(context);
                        _favDB.RemoveFav(tmp);
                        Snackbar.make(buttonView, "Removed from Favorite",
                                Snackbar.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public void saveFav(String animerl){
        _favDB = new favDB(context);
        FavItem fav = new FavItem();
        fav.setAnimeName(titlee.toString());
        fav.setImgUrl(getIntent().getStringExtra("image"));
        fav.setLinkSummary(animerl);
        fav.setFavStatus("1");

        _favDB.InsertFavorite(fav.getAnimeName(),fav.getImgUrl(),fav.getLinkSummary(),fav.getFavStatus());
    }



    public String AnimeNameCut(String name){
        char[] tmp = name.toCharArray();
        int count = 0;
        String temp = "";
        for(int i = tmp.length - 1;i>= 1; i--){
            if(Character.isDigit(tmp[i])){
                count++;
            }else{
                break;
            }
        }
        temp = name.substring(1,name.length() - 9 - count);
        return Constants.GlobalUrl + "/category/" + temp;

    }
    public boolean Exists(String animeName){
        _favDB = new favDB(context);
        mDb = _favDB.getWritableDatabase();

        String[] projection = {
                favDB.KEY,
                favDB.ITEM_ANIME_NAME,
                favDB.IMG_URL,
                favDB.LINK_SUMMARY,
                favDB.STATUS_FAV
        };
        String Selection = favDB.ITEM_ANIME_NAME + " =?";
        String[] selectionArgs = { animeName };
        String limit = "1";

        Cursor cursor = mDb.query(favDB.TABLE_FAV,projection,Selection,selectionArgs,null,null,null,limit);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }



}
