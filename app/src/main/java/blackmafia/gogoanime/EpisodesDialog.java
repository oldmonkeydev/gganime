package blackmafia.gogoanime;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class EpisodesDialog extends DialogFragment {
    private String[] lista;
    private String[] listalinks;

    public EpisodesDialog() {}

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if(listalinks == null){
            listalinks = getArguments().getStringArray("linkLista");
        }
        if(lista == null){
            lista = getArguments().getStringArray("links");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),AlertDialog.THEME_DEVICE_DEFAULT_DARK);

        builder.setTitle(R.string.ChooseServerEN)
                .setItems(lista, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getContext(),Exoplayer_Activity.class);
                        intent.putExtra("link",listalinks[which]);
                        getContext().startActivity(intent);

                    }
                });

        return builder.create();
    }

}
