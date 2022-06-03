package com.example.myapplication111;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class ItemFragment extends ListFragment {
    private DBSecurities.OpenHelper dbOpenHelper;
    List<Securities> list = new ArrayList<>();

    public View onGreatView(LayoutInflater inflater,
                            ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        SecuritiesAdapter adapter = new SecuritiesAdapter(requireActivity().getApplicationContext(), list);
        dbOpenHelper = new DBSecurities.OpenHelper(requireActivity().getApplicationContext());
        setListAdapter(adapter);
        return view;
    }
    public void onListItemClick(ListView l, View v, int position, long id) {
        TextView name=v.findViewById(R.id.name);
        TextView comp=v.findViewById(R.id.companiya);
        ImageView icon =  v.findViewById(R.id.icons);
        TextView capit=v.findViewById(R.id.capital1);
        MainPurchaseSale.t_name.setText(name.getText().toString());
        MainPurchaseSale.t_comp.setText(comp.getText().toString());
        MainPurchaseSale.txt_price.setText(capit.getText().toString());
        Intent intent = new Intent(getActivity(), MainPurchaseSale.class);
        startActivity(intent);
     }


    public void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }
    private void displayDatabaseInfo() {
        // Создадим и откроем для чтения базу данных
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        String[] projection = {
                DBSecurities.COLUMN_ID,
                DBSecurities.COLUMN_COMPANY,
                DBSecurities.COLUMN_NAME,
                DBSecurities.COLUMN_IMAGE,
                String.valueOf(DBSecurities.COLUMN_PRICE)};
        Cursor cursor = db.query(
                DBSecurities.TABLE_NAME5,
                projection,
                null,
                null,
                null,
                null,
                null);
        try {
            int idColumnIndex = cursor.getColumnIndex(DBSecurities.COLUMN_ID);
            int nameColumnIndex = cursor.getColumnIndex(DBSecurities.COLUMN_NAME);
            int companyColumnIndex = cursor.getColumnIndex(DBSecurities.COLUMN_COMPANY);
            int imageColumnIndex = cursor.getColumnIndex(DBSecurities.COLUMN_IMAGE);
            int priceColumnIndex = cursor.getColumnIndex(String.valueOf(DBSecurities.COLUMN_PRICE));


            // Проходим через все ряды
            while (cursor.moveToNext()) {
                // Используем индекс для получения строки или числа
                int currentID = cursor.getInt(idColumnIndex);
                byte[] currentImage=cursor.getBlob(imageColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentPrice = cursor.getInt(priceColumnIndex);
                String currentCompany = cursor.getString(companyColumnIndex);
                Securities securities = new Securities();
                securities.title =currentName;
                securities.company =currentCompany ;
                securities.idImage=currentImage;
                securities.price= currentPrice;
                list.add(securities);


            }
            // Выводим значения каждого столбца

        } finally {
            cursor.close();
        }
    }

}
