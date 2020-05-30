package blackmafia.gogoanime;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.Inflater;


public class activity_fragment_sumary extends Fragment {

    private String link ,detailString, typeString, genreString, releasedString,statusString;
    private String animeUrl;
    private String baseUrl = Constants.GlobalUrl;
    private int fl = 1;


    RecyclerView recyclerView;
    ProgressBar progressBar;
    ArrayList<summary_class> texts;
    ParseAdaptSummary adapter;



    public activity_fragment_sumary() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_activity_fragment_sumary, container, false);
        progressBar = view.findViewById(R.id.progressBarSummary);
        recyclerView = view.findViewById(R.id.recyclerViewSumary);
        texts = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ParseAdaptSummary(texts,getContext());
        recyclerView.setAdapter(adapter);

        animeUrl = this.getArguments().getString("animeUrl");
        fl = this.getArguments().getInt("fl");

        ContentSummary content = new ContentSummary();
        content.execute();


        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }
    private class ContentSummary extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(progressBar.getVisibility() == View.GONE){
                progressBar.setVisibility(View.VISIBLE);
                progressBar.startAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_in));
                adapter.notifyDataSetChanged();
            }



        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(progressBar.getVisibility() == View.VISIBLE){
                progressBar.setVisibility(View.GONE);
                //progressBar.startAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.fade_out));
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
            String url = "";
            if(fl == 1){
                try {
                    url = baseUrl + animeUrl;

                    Document doc = Jsoup.connect(url).get();

                    Elements data = doc.select("div.anime_video_body").select("div.anime_video_body_cate");


                    link = data.select("div.anime-info")
                            .select("a")
                            .attr("href");
                    url = baseUrl + link;


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                url = animeUrl;
            }


            try{

                Document docDesc = Jsoup.connect(url).get();
                Elements data = docDesc.select("div.anime_info_body").select("div.anime_info_body_bg");
                int size = data.size();

                for(int i=0; i<size; i++){
                    typeString = "Type: " + data.select("p.type").select("a[href]")
                            .eq(i)
                            .text();
                    detailString = data.select("p.type")
                            .eq(i+1)
                            .text();

                    genreString = data.select("p.type")
                            .eq(i+2)
                            .text();
                    releasedString =  data.select("p.type")
                            .eq(i+3)
                            .text();
                    statusString =  data.select("p.type")
                            .eq(i+4)
                            .text();

                }
                summary_class s1 = new summary_class(typeString);
                summary_class s2 = new summary_class(detailString);
                summary_class s3 = new summary_class(genreString);
                summary_class s4 = new summary_class(releasedString);
                summary_class s5 = new summary_class(statusString);

                texts.add(s1);
                texts.add(s2);
                texts.add(s3);
                texts.add(s4);
                texts.add(s5);



            }catch (IOException e){
                e.printStackTrace();
            }


            return null;
        }
    }

}

