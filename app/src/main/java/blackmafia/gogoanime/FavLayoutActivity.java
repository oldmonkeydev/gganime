package blackmafia.gogoanime;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class FavLayoutActivity extends Fragment { //implements SearchView.OnQueryTextListener {
    RecyclerView recyclerView;
    List<FavItem> favItems;
    favDB _favDB;
    FavLayoutAdapter adapter;
    //BottomNavigationView bottomNavigationView;
    String titleString = "<font color=#efb810>GG</font><font color=#ffffff>favorites</font>";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fav_layout, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {

        recyclerView = view.findViewById(R.id.recyclerviewFavLayout);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        favItems = new ArrayList<>();
        adapter = new FavLayoutAdapter(getActivity(), favItems);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        _favDB = new favDB(getActivity());
        getAllFavorite();
    }

/*
    @Override
    public  boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.anime_list_search_menu, menu);
        MenuItem search=menu.findItem(R.id.anime_list_search_id);
        SearchView searchView=(SearchView) MenuItemCompat.getActionView(search);
        searchView.setOnQueryTextListener(this);
        return true;
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText=newText.toLowerCase();

        ArrayList<FavItem> newAnimeList=new ArrayList<>();
        for(int i=0;i<favItems.size();i++)
        {
            if(favItems.get(i).getAnimeName().toLowerCase().contains(newText))
                newAnimeList.add(favItems.get(i));
        }
        adapter.setFilter(newAnimeList);

        return false;
    }

*/
    private void getAllFavorite(){
        new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                favItems.clear();
                favItems.addAll(_favDB.getList());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                adapter.notifyDataSetChanged();
            }
        }.execute();
    }

}
