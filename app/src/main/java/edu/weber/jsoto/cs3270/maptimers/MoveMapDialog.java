package edu.weber.jsoto.cs3270.maptimers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;

public class MoveMapDialog extends DialogFragment {
    private int position;
    private int listID;

    public MoveMapDialog() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        position = getArguments().getInt("listPosition");
        listID = getArguments().getInt("listID");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.txtUpdate_spawn)
                .setSingleChoiceItems(R.array.local_map_change_array, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("MoveMapDialog", "Item selected " + which);
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("MoveMapDialog", "Positive choice");
                        // move position selected into 'which' list
                        MainActivity ma = (MainActivity) getActivity();
                        // move list item at position from listID, into list which
                        ma.moveMap(position, listID, which);
                    }
                })
                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("MoveMapDialog", "Neutral Choice");
                        // nothing
                    }
                })
                .create();

        return builder.create();
    }
}
