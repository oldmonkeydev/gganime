package blackmafia.gogoanime;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.util.Log;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    private ArrayList<Anime> mAnimeList = new ArrayList<>();


    private Context context;
    private Activity activity;
    public  SearchAdapter(Context context, ArrayList<Anime> AnimeList,Activity activity) {
        this.mAnimeList = AnimeList;
        this.context=context;
        this.activity=activity;
    }
    public    SearchAdapter()
    {

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        private TextView title;
        private ImageView imageofanime;

        MyViewHolder(View view) {
            super(view);
            title =  view.findViewById(R.id.textviewfav);
            imageofanime= view.findViewById(R.id.imageviewfav);
            cardView= view.findViewById(R.id.cardviewfav);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorite_anime_cardview, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.title.setText(mAnimeList.get(position).getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, DetailActivity.class);
                intent.putExtra("animeUrl",mAnimeList.get(position).getLink());
                intent.putExtra("title",mAnimeList.get(position).getName());
                intent.putExtra("image",mAnimeList.get(position).getImageLink());
                intent.putExtra("fl",2);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Log.d("items: ", "animeUrl: " + mAnimeList.get(position).getLink()
                + " title: " + mAnimeList.get(position).getName() + " image: " +
                        mAnimeList.get(position).getImageLink());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.getApplicationContext().startActivity(intent);
            }
        });
        Picasso.get().load(mAnimeList.get(position).getImageLink()).into(holder.imageofanime);
    }

    @Override
    public int getItemCount() {
        return mAnimeList.size();
    }

}