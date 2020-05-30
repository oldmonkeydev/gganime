package blackmafia.gogoanime;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.TextView;
import android.content.Context;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static android.content.Context.CONNECTIVITY_SERVICE;


public class ParseAdaptEpisodes extends RecyclerView.Adapter<ParseAdaptEpisodes.ViewHolder> {
    ArrayList<episode_class> list;
    private Context context;
    private FragmentManager manager;
    EpisodesDialog dialog;

    String host;



    public ParseAdaptEpisodes(ArrayList<episode_class> list, Context context) {
        this.list = list;
        this.context = context;
        manager = ((FragmentActivity) context).getSupportFragmentManager();

    }

    public String getHost() {
        return host;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.episodes_cardview, parent, false);
        dialog = new EpisodesDialog();


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        episode_class cls = list.get(position);
        holder.tv.setText(cls.getName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.nameEp);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(haveNetworkConnection(context)){
                int position = getAdapterPosition();

                episode_class ep = list.get(position);
                Intent intent = new Intent(context,Exoplayer_Activity.class);
                intent.putExtra("link",ep.getEpLink());
                intent.putExtra("name",ep.getAnimeName());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }

        }
    }
    boolean haveNetworkConnection(android.content.Context context) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

}
