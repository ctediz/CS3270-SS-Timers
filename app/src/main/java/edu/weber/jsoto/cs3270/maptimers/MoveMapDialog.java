package edu.weber.jsoto.cs3270.maptimers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.ListView;

public class MoveMapDialog extends DialogFragment {
    private int position;
    private int listID;
    private int arrayResource;
    private int selected;

    public MoveMapDialog() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        position = getArguments().getInt("listPosition");
        listID = getArguments().getInt("listID");
        arrayResource = getArguments().getInt("arrayResource");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.txtUpdate_spawn)
                .setSingleChoiceItems(arrayResource, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("MoveMapDialog", "Item selected " + which);
                        selected = which;
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("MoveMapDialog", "Positive choice");
                        // move position selected into 'which' list
                        MainActivity ma = (MainActivity) getActivity();
                        // move list item at position from listID, into list selected
                        ma.moveMap(position, listID, selected);
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
