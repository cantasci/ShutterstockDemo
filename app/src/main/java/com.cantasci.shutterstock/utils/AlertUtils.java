package com.cantasci.shutterstock.utils;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class AlertUtils {

    public static void showAlert(Activity activity, String header, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setTitle(header);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
