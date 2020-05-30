package blackmafia.gogoanime;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ParseAdapter extends RecyclerView.Adapter<ParseAdapter.ViewHolder> {

    private ArrayList<ParseItem> parseItems;
    private Context context;

    public ParseAdapter(ArrayList<ParseItem> parseItems, Context context) {
        this.parseItems = parseItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ParseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.parse_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParseAdapter.ViewHolder holder, int position) {
        ParseItem parseItem = parseItems.get(position);
        holder.textView.setText(parseItem.getTitle());
        holder.textEpisode.setText(parseItem.getDetailUrl());
        Picasso.get().load(parseItem.getImgUrl()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return parseItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView textView;
        TextView textEpisode;
        Button btnPlay, btnDetail;

        public ViewHolder(@NonNull View view) {
            super(view);
            imageView = view.findViewById(R.id.imageView);
            textView = view.findViewById(R.id.textView);
            textEpisode = view.findViewById(R.id.epLabel);
            btnPlay = view.findViewById(R.id.btnPlay);
            btnDetail = view.findViewById(R.id.btnDetails);
            btnPlay.setOnClickListener(this);
            btnDetail.setOnClickListener(this);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            ParseItem parseItem = parseItems.get(position);
            Log.d("getUrlAnime: " , parseItem.getUrlAnime());
            switch (view.getId()){
                case R.id.btnPlay:
                    Intent i = new Intent(context,Exoplayer_Activity.class);
                    i.putExtra("link",Constants.GlobalUrl + parseItem.getUrlAnime());
                    i.putExtra("name",parseItem.getTitle());
                    context.startActivity(i);
                    break;
                case R.id.btnDetails:
                    String temp = ExtractLink(parseItem.getUrlAnime());
                    Log.d("link Extracted: ", temp);

                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra("title", parseItem.getTitle());
                    intent.putExtra("image", parseItem.getImgUrl());
                    intent.putExtra("animeUrl", temp);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("fl",2);

                    context.startActivity(intent);
                    break;
            }
        }
        public String ExtractLink(String link){
           int index = link.lastIndexOf("-episode-");
            return Constants.GlobalUrl + "/category" + link.substring(0,index);
        }
    }

    public void setFilter (ArrayList<ParseItem> newList) {
        parseItems = new ArrayList<>();
        parseItems.addAll(newList);
        notifyDataSetChanged();
    }
}
