package blackmafia.gogoanime;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;

public class category_list_activity extends Fragment {

    private ArrayList<model_categories_list> lista;
    RecyclerView recyclerView;
    BottomNavigationView bottomNavigationView;
    String titleString = "<font color=#efb810>GG</font><font color=#ffffff>category</font>";
    adapterforcategoryactivity mDataAdapter;
    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category_list, container, false);
        Constants con = new Constants();
        lista = new ArrayList<>(con.FillArrayList());
        recyclerView = view.findViewById(R.id.recyclerviewFavLayout);
        bottomNavigationView = view.findViewById(R.id.btnNav);
        context = getActivity();
        mDataAdapter = new adapterforcategoryactivity(lista,context);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        recyclerView.setAdapter(mDataAdapter);

        return view;
    }

}
