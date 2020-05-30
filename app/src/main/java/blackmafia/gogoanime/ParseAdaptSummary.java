package blackmafia.gogoanime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ParseAdaptSummary extends RecyclerView.Adapter<ParseAdaptSummary.ViewHolder>{

    ArrayList<summary_class> list;
    private Context context;

    public ParseAdaptSummary(ArrayList<summary_class> list, Context context) {
        this.list = list;
        this.context = context;

    }

    @NonNull
    @Override
    public ParseAdaptSummary.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.summary_cardview,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParseAdaptSummary.ViewHolder holder, int position) {
        summary_class str = list.get(position);
        holder.tv.setText(str.getStr());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tvSummary);
        }
    }
    public void setFilter (ArrayList<summary_class> newList) {
        list = new ArrayList<>();
        list.addAll(newList);
        notifyDataSetChanged();
    }
}