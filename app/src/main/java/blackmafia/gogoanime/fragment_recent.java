package blackmafia.gogoanime;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class fragment_recent extends Fragment {
    ProgressBar progressBar;
    RecyclerView recyclerView;
    private ArrayList<ParseItem> parseItems = new ArrayList<>();
    private ParseAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Content content;

    public fragment_recent(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recent_fragment, container, false);
        progressBar = view.findViewById(R.id.progressBarRecent);
        recyclerView = view.findViewById(R.id.recyclerViewRecent);
        swipeRefreshLayout = view.findViewById(R.id.SwipeRefreshLayoutRecent);
        parseItems = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ParseAdapter(parseItems, getActivity());
        recyclerView.setAdapter(adapter);
        LoadItems();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadItems();
            }
        });

        return view;

    }
    private void LoadItems() {
        content = new Content();
        content.execute();
    }

    private class Content extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            progressBar.startAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in));
            parseItems.clear();
            adapter.notifyDataSetChanged();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
//            progressBar.startAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out));
            swipeRefreshLayout.setRefreshing(false);
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
        protected Void doInBackground(Void... voids) {

            try {
                String url = Constants.GlobalUrl;

                Document doc = Jsoup.connect(url).get();

                Elements data = doc.select("ul.items").select("li");
                int size = data.size();
                Log.d("doc", "doc: "+doc);
                Log.d("data", "data: "+data);
                Log.d("size", ""+size);
                for (int i = 0; i < size; i++) {
                    String imgUrl = data.select("div.img").select("a[href]")
                            .select("img")
                            .eq(i)
                            .attr("src");

                    String title = data.select("p.name").select("a[href]")
                            .select("a[title]")
                            .eq(i)
                            .text();

                    String detailUrl = data.select("p.episode")
                            .eq(i)
                            .text();

                    String link = data.select("div.img")
                            .select("a[href]")
                            .eq(i)
                            .attr("href");




                    parseItems.add(new ParseItem(imgUrl, title, detailUrl, link));

                    Log.d("items", "img: " + imgUrl + " . title: " + title + "episode:" + detailUrl + "link:" + link);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }
    }

}
