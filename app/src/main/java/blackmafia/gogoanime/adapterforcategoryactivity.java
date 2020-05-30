package blackmafia.gogoanime;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class adapterforcategoryactivity extends RecyclerView.Adapter<adapterforcategoryactivity.MyViewHolder> {

    private ArrayList<model_categories_list> mlist = new ArrayList<>();
    private Context context;
    String link;

    public adapterforcategoryactivity(ArrayList<model_categories_list> mlist, Context context){
        this.mlist = mlist;
        this.context = context;


    }


    @NonNull
    @Override
    public adapterforcategoryactivity.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapterforcategorylist, parent, false);

        return new adapterforcategoryactivity.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull adapterforcategoryactivity.MyViewHolder holder, int position) {
        holder.textview.setText(mlist.get(position).getName());
        Picasso.get().load(mlist.get(position).getImgLink()).into(holder.imageView);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                link = Constants.GlobalUrl + "/genre/" + mlist.get(position).getName().toLowerCase().replace(" ","-");
                Intent intent = new Intent(context,categories_activity.class);
                intent.putExtra("link",link);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textview;
        CardView linearLayout;
        ImageView imageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textview = itemView.findViewById(R.id.tvcatlist);
            linearLayout = itemView.findViewById(R.id.llayout);
            imageView = itemView.findViewById(R.id.imageviewcatlist);
        }
    }
}
