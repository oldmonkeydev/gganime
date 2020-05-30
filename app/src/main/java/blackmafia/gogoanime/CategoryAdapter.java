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

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolderCategory> {
    ArrayList<ParseItem> list;
    Context context;
    CategoryAdapter(ArrayList<ParseItem> list, Context context){
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolderCategory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_anime_cardview,parent,false);
        return new ViewHolderCategory(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCategory holder, int position) {
        ParseItem parseItem = list.get(position);
        holder.textView.setText(parseItem.getTitle());
        Picasso.get().load(parseItem.getImgUrl()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolderCategory extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textView;
        ImageView imageView;

        public ViewHolderCategory(View view){
            super(view);
            textView = view.findViewById(R.id.textviewfav);
            imageView = view.findViewById(R.id.imageviewfav);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            ParseItem parseItem = list.get(position);
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("title", parseItem.getTitle());
            intent.putExtra("image", parseItem.getImgUrl());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("animeUrl", Constants.GlobalUrl + parseItem.getUrlAnime());
            intent.putExtra("fl",2);

            context.startActivity(intent);

        }
    }
}
