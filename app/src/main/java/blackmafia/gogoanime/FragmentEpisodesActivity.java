package blackmafia.gogoanime;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import com.google.android.exoplayer2.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


public class FragmentEpisodesActivity extends Fragment {

    private String animeUrl;
    private String baseUrl = Constants.GlobalUrl;
    private String edepisode;
    private ArrayList<multilinks_class> multilinks;
    private int fl= 1;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    ArrayList<episode_class> list;
    ParseAdaptEpisodes adapter;

    public FragmentEpisodesActivity() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_fragment_episodes, container, false);
        progressBar = view.findViewById(R.id.progressBarEp);
        recyclerView = view.findViewById(R.id.recyclerViewEp);
        list = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ParseAdaptEpisodes(list, getContext());
        recyclerView.setAdapter(adapter);

        animeUrl = this.getArguments().getString("animeUrl");
        fl = this.getArguments().getInt("fl");

        ContentEpisodes contentEpisodes = new ContentEpisodes();
        contentEpisodes.execute();

        return view;
    }

    public String ExtractAnimeName(String link){
        char[] strArray = link.toCharArray();
        int numbercount = 1;
        for(int i = strArray.length - 1; i>= 1; i--){
            if(!Character.toString(strArray[i]).equals("/")){
                numbercount ++;
            }else{
                break;
            }

        }
        return link.substring(link.length() - numbercount);
    }


    public String stringShaper(String link) {

        char[] strArray = link.toCharArray();
        int numbercount = 0;
        for (int i = strArray.length - 1; i >= 1; i--) {
            if (Character.isDigit(strArray[i])) {
                numbercount++;
            } else {
                break;
            }
        }
        return link.substring(0, link.length() - 1 - numbercount);
    }

    private class ContentEpisodes extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(progressBar.getVisibility() == View.GONE){
                progressBar.setVisibility(View.VISIBLE);
                progressBar.startAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in));
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
        protected Void doInBackground(Void... voids) {
            String url;
            try {
                if(fl == 1){
                    url = baseUrl + animeUrl;
                }else{
                    url = animeUrl;
                }

                multilinks = new ArrayList<>();

                Document doc = Jsoup.connect(url).get();

                Elements data = doc.select("div.main_body").select("div.anime_video_body")
                        .select("ul");
                int size = data.size();

                    for (int i = 0; i < size; i++) {
                        edepisode = data.select("li").last().select("a")
                                .attr("ep_end");
                    }



            } catch (IOException e) {
                e.printStackTrace();
            }
            int count = Integer.parseInt(edepisode);
            for (int i = count; i >= 1; i--) {
                String str = "";
                if(fl == 1){
                    str = stringShaper(baseUrl + animeUrl);
                }else{
                    String name = ExtractAnimeName(animeUrl);
                    String build = baseUrl + "/" + name + "-episode-" + 12;
                    str = stringShaper(build);

                }

                str += "-" + i;
                String AnimeNamee = ExtractAnimeName(str).replace("-"," ").substring(1);
                String primeraLetra = AnimeNamee.substring(0,1).toUpperCase();
                String resto = AnimeNamee.substring(1);
                String union = primeraLetra + resto;


                //Log.d("videoLink"," link: " + str);

                if(AnimeNamee.length() > 42){
                    episode_class ep = new episode_class(AnimeNamee.substring(0,40) + "..." +
                            AnimeNamee.substring(AnimeNamee.length() - 2),str,union);
                    list.add(ep);
                }else{
                    episode_class ep = new episode_class(AnimeNamee ,str,union);
                    list.add(ep);
                }

            }
            return null;
        }
    }
}