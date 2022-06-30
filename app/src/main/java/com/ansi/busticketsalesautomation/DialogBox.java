package com.ansi.busticketsalesautomation;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentManager;

public class DialogBox extends AppCompatDialogFragment {

    public String title;
    public String message;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title).setMessage(message).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        return builder.create();
    }

    public static void showDialog(FragmentManager fragmentManager, String title, String message){
        DialogBox dialogBox = new DialogBox();
        dialogBox.title = title;
        dialogBox.message = message;
        dialogBox.show(fragmentManager, "Alert");
    }
}
