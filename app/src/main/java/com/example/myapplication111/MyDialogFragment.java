package com.example.myapplication111;

import android.app.Dialog;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class MyDialogFragment extends DialogFragment {
    @NonNull
    @Override

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String[] sorting = new String[]{"Цене", "Названию","Стандартно"};
        String itemText="Стандартно";
        final boolean[] checkedItemsArray = {false, false, true};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Вы сортируете список")
                .setIcon(R.mipmap.ic_launcher12_foreground)
                .setTitle("Сортировка списка")
                .setMultiChoiceItems(sorting, checkedItemsArray,
                        (dialog, which, isChecked) -> {
                            if(isChecked){
                                for (int i = 0; i < checkedItemsArray .length; i++) {
                                    checkedItemsArray[i]=false;

                                }
                                checkedItemsArray[which] = isChecked;
                            }

                        })
                .setPositiveButton("OK", (dialog, id) -> {
                    for (int i = 0; i < sorting.length ; i++) {
                        if(checkedItemsArray[i]){
                            if(sorting[i].equals("Цене")){
                                MainListSecurities.n=1;
                            }
                            if(sorting[i].equals("Названию")){
                                MainListSecurities.n=2;
                            }if(sorting[i].equals("Стандартно")){
                                MainListSecurities.n=0;
                            }
                        }

                    }
                    dialog.cancel();
                })
                .setNegativeButton("Отмена", (dialog, id) -> dialog.cancel());

        return builder.create();
    }


}