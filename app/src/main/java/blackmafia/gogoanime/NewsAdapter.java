package blackmafia.gogoanime;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewModelNews> {

    private ArrayList<NewsModel> list;
    private Context context;

    public NewsAdapter(ArrayList<NewsModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewModelNews onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_cardview, parent, false);
        return new ViewModelNews(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewModelNews holder, int position) {

        NewsModel model = list.get(position);
        holder.textviewTitle.setText(model.getTitle());
        holder.textviewInfo.setText(model.getInfo());
        holder.textViewDate.setText(model.getDate());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewModelNews extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textviewTitle;
        TextView textviewInfo;
        TextView textViewDate;

        ViewModelNews(@NonNull View view){
            super(view);
            textviewTitle = view.findViewById(R.id.textviewNewTitle);
            textviewInfo = view.findViewById(R.id.textviewNewInfo);
            textViewDate = view.findViewById(R.id.textviewDate);
            view.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            NewsModel modelTemp = list.get(position);
            Intent i = new Intent(context,webview_news_activity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("link",modelTemp.getLink());
            context.startActivity(i);

        }
    }
}
