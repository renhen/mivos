package com.renhen.optical_shop.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.renhen.optical_shop.ReceptionActivity;
import com.renhen.optical_shop.data.OpticalShopDBHelper;

public class AcceptDeleteReceptionDialog extends DialogFragment {


    ReceptionActivity activity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (ReceptionActivity) context;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final String receptionID = getArguments().getString("receptionID");

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        return builder.setTitle("Подтвердите ваше действие")
                .setMessage("Вы действительно хотите отменить данную запись на прием?")
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db = new OpticalShopDBHelper(getActivity()).getReadableDatabase();
                        db.execSQL("DELETE FROM reception WHERE _id = ?", new String[]{receptionID});
                        activity.updateList();
                    }
                })
                .setNegativeButton("Отмена",null)
                .create();
    }
}