package com.amg.medicinanatural;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/* loaded from: classes3.dex */
public class MyDialogFragment extends DialogFragment {
    public static String STitle = "TITLE";
    public static String SText = "TEXT";
    String title = "";
    String text = "";

    @Override // android.app.DialogFragment
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        this.title = getArguments().getString(STitle, "");
        this.text = getArguments().getString(SText, "");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(this.title);
        builder.setMessage(this.text);
        builder.setPositiveButton(getString(R.string.aceptar), new DialogInterface.OnClickListener() { // from class: com.amg.medicinanatural2.MyDialogFragment.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialog, int id) {
                MyDialogFragment.this.dismiss();
            }
        });
        return builder.create();
    }
}
