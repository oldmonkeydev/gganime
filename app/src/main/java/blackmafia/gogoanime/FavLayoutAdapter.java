package blackmafia.gogoanime;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FavLayoutAdapter extends RecyclerView.Adapter<FavLayoutAdapter.FavHolder> {

    Context context;
    List<FavItem> favItems;


    public FavLayoutAdapter(){}
    public FavLayoutAdapter(Context context, List<FavItem> favItems){
        this.context = context;
        this.favItems = favItems;
    }


    @NonNull
    @Override
    public FavHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_anime_cardview, parent, false);
        return new FavHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavHolder holder, int position) {
        FavItem favItem = favItems.get(position);
        holder.textView.setText(favItem.getAnimeName());
        Picasso.get().load(favItem.getImgUrl()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return favItems.size();
    }

    public void setFilter(ArrayList<FavItem> newAnimeList) {
        favItems=new ArrayList<>();
        favItems.addAll(newAnimeList);

        notifyDataSetChanged();
    }

    public class FavHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        TextView textView;

        public FavHolder(@NonNull View view){
            super(view);
            imageView = view.findViewById(R.id.imageviewfav);
            textView = view.findViewById(R.id.textviewfav);
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            FavItem favitem = favItems.get(position);

            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("title", favitem.getAnimeName());
            intent.putExtra("image", favitem.getImgUrl());
            intent.putExtra("detailUrl", favitem.getLinkSummary());
            intent.putExtra("animeUrl", favitem.getLinkSummary());
            intent.putExtra("fl",2);
            intent.putExtra("flagLink",false);

            context.startActivity(intent);
        }
    }
}
