package com.example.flagpole;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;

public class FlagDialogBox extends DialogFragment
{
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //Get the layout inflater 
        LayoutInflater inflater = getActivity().getLayoutInflater();

        //Inflate and set the layout for the dialog
        //Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.flag_dialog, null))
        //Add action buttons
            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // click ok
                    Dialog f = (Dialog) dialog;
                    EditText flagTitle, flagDescription;
                    flagTitle = (EditText) f.findViewById(R.id.flag_title);
                    flagDescription = (EditText) f.findViewById(R.id.flag_description);
                    //Log.i("flag", flagTitle.getText().toString() + " " + flagDescription.getText().toString());
                    Map.addFlagToMap(flagTitle.getText().toString(), flagDescription.getText().toString());
                }
            })
            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FlagDialogBox.this.getDialog().cancel();

                }
            });
        return builder.create();
    }

}
