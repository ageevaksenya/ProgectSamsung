package com.example.myapplication111;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Fragment;

import android.app.FragmentManager;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.LayerDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainListSecurities extends AppCompatActivity{

    private DBSecurities.OpenHelper dbOpenHelper;
    static String heading;
    public static int n=0;
    static String s="ascending";
    List<Securities> list=new ArrayList<>();
    MediaPlayer mPlayer;
    MediaPlayer player;



    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        player = MediaPlayer.create(this, R.raw.music);
        Bundle arguments = getIntent().getExtras();
        heading = arguments.getString("textbutton");
        TextView text = findViewById(R.id.textView6);
        text.setText(heading);
        ImageButton button =  findViewById(R.id.back);
        ImageButton sort=findViewById(R.id.btn_sort);
        Call<List<Securities>> call = null;
        if(heading.equals("АКЦИИ")) {
             call = MyServer.service.sec("Table", "Seccurities");
        }
        if(heading.equals("ОБЛИГАЦИИ")) {
            call = MyServer.service.sec("Table", "Bonds");
        }
        if(heading.equals("ВАЛЮТЫ")) {
            call = MyServer.service.sec("Table", "Currency");
        }
        if(heading.equals("ФЬЮЧЕРСЫ")) {
            call = MyServer.service.sec("Table", "Futures");
        }
        assert call != null;
        call.enqueue(new Callback<List<Securities>>() {
            @Override
            public void onResponse(@NonNull Call<List<Securities>> call, @NonNull Response<List<Securities>> response) {
                if (response.isSuccessful()) {
                    SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
                    String[] projection = {
                            DBSecurities.COLUMN_ID,
                            DBSecurities.COLUMN_COMPANY,
                            DBSecurities.COLUMN_NAME,
                            DBSecurities.COLUMN_IMAGE,
                            DBSecurities.COLUMN_PRICE};
                    Cursor cursor = db.query(
                            DBSecurities.TABLE_NAME5,
                            projection,
                            null,
                            null,
                            null,
                            null,
                            null);
                    try {
                        int nameColumnIndex = cursor.getColumnIndex(DBSecurities.COLUMN_NAME);
                        int companyColumnIndex = cursor.getColumnIndex(DBSecurities.COLUMN_COMPANY);
                        int idColumnIndex = cursor.getColumnIndex(DBSecurities.COLUMN_ID);
                        int countColumnIndex=cursor.getColumnIndex(DBSecurities.COLUMN_COUNT);


                        for (int i = 0; i < Objects.requireNonNull(response.body()).size(); i++) {
                            while (cursor.moveToNext()) {
                                int currentID = cursor.getInt(idColumnIndex);
                                String currentCompany = cursor.getString(companyColumnIndex);
                                String currentName = cursor.getString(nameColumnIndex);
                                int currentCount = cursor.getInt(countColumnIndex);
                                if(list.get(i).company.equals(currentCompany)&&list.get(i).title.equals(currentName)){
                                    DBSecurities.update5(new Cell2(currentID,currentName,currentCompany,response.body().get(i).idImage, response.body().get(i).price,currentCount));
                                }
                            }
                            if (heading.equals("АКЦИИ")) {
                                DBSecurities.update(new Cell(response.body().get(i).id, response.body().get(i).title, response.body().get(i).company, response.body().get(i).idImage, response.body().get(i).price));

                            }
                            if (heading.equals("ОБЛИГАЦИИ")) {
                                DBSecurities.update2(new Cell(response.body().get(i).id, response.body().get(i).title, response.body().get(i).company, response.body().get(i).idImage, response.body().get(i).price));
                            }
                            if (heading.equals("ВАЛЮТА")) {
                                DBSecurities.update3(new Cell(response.body().get(i).id, response.body().get(i).title, response.body().get(i).company, response.body().get(i).idImage, response.body().get(i).price));
                            }
                            if (heading.equals("ФЬЮЧЕРСЫ")) {
                                DBSecurities.update4(new Cell(response.body().get(i).id, response.body().get(i).title, response.body().get(i).company, response.body().get(i).idImage, response.body().get(i).price));
                            }
                        }

                    } finally {
                        cursor.close();
                    }
                }
                else {
                    assert response.body() != null;
                    Toast.makeText(MainListSecurities.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<List<Securities>> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(MainListSecurities.this, "сбой сети :( повторите попытку", Toast.LENGTH_SHORT).show();
                }
                 else {
                    Toast.makeText(MainListSecurities.this, "проблема с конверсией! :(", Toast.LENGTH_SHORT).show();
                }
            }



        });
        sort.setOnClickListener(view -> {

           androidx.fragment.app.FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction tx = fragmentManager.beginTransaction();

            DialogFragment dialogFragment = new MyDialogFragment();
            dialogFragment.show(tx, "hello");
            tx.commit();

        });


        button.setOnClickListener(view -> {
            finish();
        });

        SecuritiesAdapter adapter = new SecuritiesAdapter(this, list);
        ListView lv =  findViewById(R.id.listView);
        dbOpenHelper = new DBSecurities.OpenHelper(this);

        lv.setOnItemClickListener((parent, view, position, id) -> {
                  mPlayer=MediaPlayer.create(this, R.raw.cry);
                  TextView name=view.findViewById(R.id.name);
                  TextView comp=view.findViewById(R.id.companiya);
                  ImageView icon =  view.findViewById(R.id.icons);
                  TextView capit=view.findViewById(R.id.capital1);
                  final Bitmap selectedImage = ((BitmapDrawable)((LayerDrawable)icon.getDrawable()).getDrawable(0)).getBitmap();
                  ByteArrayOutputStream stream = new ByteArrayOutputStream();
                  selectedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                  byte[] byteArray = stream.toByteArray();
                  selectedImage.recycle();
                  MainPurchase.im=byteArray;
                  MainPurchase.t_name.setText(name.getText().toString());
                  MainPurchase.t_comp.setText(comp.getText().toString());
                  MainPurchase.txt_price.setText(capit.getText().toString());
                  Intent intent = new Intent(MainListSecurities.this, MainPurchase.class);
                  MainListSecurities.this.startActivity(intent);
        });
        lv.setAdapter(adapter);


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        if(heading.equals("АКЦИИ")) SearchableActivity.TAG="MainActivity4_S";
        if(heading.equals("ОБЛИГАЦИИ")) SearchableActivity.TAG="MainActivity4_B";
        if(heading.equals("ВАЛЮТА")) SearchableActivity.TAG="MainActivity4_C";
        if(heading.equals("ФЬЮЧЕРСЫ")) SearchableActivity.TAG="MainActivity4_F";
        MenuItem shareMenuItem2 = menu.findItem(R.id.menusound);
        shareMenuItem2.setVisible(false);
        return true;
    }
    @SuppressLint("NonConstantResourceId")
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menutoken:
                item.setChecked(true);
                Call<Tk> call = MyServer.service.token("Token");
                call.enqueue(new Callback<Tk>() {
                    @Override
                    public void onResponse(Call<Tk> call, Response<Tk> response) {
                        if(response.isSuccessful()){
                            MyData myData=new MyData();
                            myData.setToken(response.body().getToken());

                        }
                        Toast.makeText(MainListSecurities.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Tk> call, Throwable t) {
                        if (t instanceof IOException) {
                            Toast.makeText(MainListSecurities.this, "сбой сети :( повторите попытку", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(MainListSecurities.this, "проблема с конверсией! :(", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
                return true;
            case R.id.menusoundturn:
                item.setChecked(true);
                if( MainChoice.flag_sound){
                    stopService(new Intent(this, MyService.class));
                    item.setTitle("включить музыку");
                    MainChoice.flag_sound=false;
                }else{

                    startService(new Intent(this, MyService.class));
                    item.setTitle("выключить музыку");
                    MainChoice.flag_sound=true;
                }

                return true;

            case R.id.menuoutput:
                item.setChecked(true);
                Intent intent=new Intent(this, MainAuthorization.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }




    protected void onStart() {
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
                DBSecurities.COLUMN_PRICE};
        Cursor cursor = null;
        if (heading.equals("Акции")) {


            if (n == 0) {
                cursor = db.query(
                        DBSecurities.TABLE_NAME,
                        projection,
                        null,
                        null,
                        null,
                        null,
                        null);
            }
            if (n == 1) {
                if (s.equals("ascending")) {
                    cursor = db.query(
                            DBSecurities.TABLE_NAME,
                            projection,
                            null,
                            null,
                            null,
                            null,
                            DBSecurities.COLUMN_PRICE+"ASC");

                } else {
                    cursor = db.query(
                            DBSecurities.TABLE_NAME,
                            projection,
                            null,
                            null,
                            null,
                            null,
                            DBSecurities.COLUMN_PRICE + "DESC");
                }
            }
            if (n == 2) {
                if (s.equals("ascending")) {
                    cursor = db.query(
                            DBSecurities.TABLE_NAME,
                            projection,
                            null,
                            null,
                            null,
                            null,
                            DBSecurities.COLUMN_NAME+"ASC"
                    );


                } else {
                    cursor = db.query(
                            DBSecurities.TABLE_NAME,
                            projection,
                            null,
                            null,
                            null,
                            null,
                            DBSecurities.COLUMN_NAME + "DESC"
                    );
                }
            }
        }

        if (heading.equals("Облигации")) {

            if (n == 0) {
                cursor = db.query(
                        DBSecurities.TABLE_NAME2,
                        projection,
                        null,
                        null,
                        null,
                        null,
                        null);
            }
            if (n == 1) {
                if (s.equals("ascending")) {
                    cursor = db.query(
                            DBSecurities.TABLE_NAME2,
                            projection,
                            null,
                            null,
                            null,
                            null,
                            DBSecurities.COLUMN_PRICE+"ASC");

                } else {
                    cursor = db.query(
                            DBSecurities.TABLE_NAME2,
                            projection,
                            null,
                            null,
                            null,
                            null,
                            DBSecurities.COLUMN_PRICE + "DESC");
                }
            }
            if (n == 2) {
                if (s.equals("ascending")) {
                    cursor = db.query(
                            DBSecurities.TABLE_NAME2,
                            projection,
                            null,
                            null,
                            null,
                            null,
                            DBSecurities.COLUMN_NAME+"ASC"
                    );


                } else {
                    cursor = db.query(
                            DBSecurities.TABLE_NAME2,
                            projection,
                            null,
                            null,
                            null,
                            null,
                            DBSecurities.COLUMN_NAME + "DESC"
                    );
                }
            }


        }
        if (heading.equals("Фьючерсы")) {

            if (n == 0) {
                cursor = db.query(
                        DBSecurities.TABLE_NAME3,
                        projection,
                        null,
                        null,
                        null,
                        null,
                        null);
            }
            if (n == 1) {
                if (s.equals("ascending")) {
                    cursor = db.query(
                            DBSecurities.TABLE_NAME3,
                            projection,
                            null,
                            null,
                            null,
                            null,
                            DBSecurities.COLUMN_PRICE+"ASC");

                } else {
                    cursor = db.query(
                            DBSecurities.TABLE_NAME3,
                            projection,
                            null,
                            null,
                            null,
                            null,
                            DBSecurities.COLUMN_PRICE + "DESC");
                }
            }
            if (n == 2) {
                if (s.equals("ascending")) {
                    cursor = db.query(
                            DBSecurities.TABLE_NAME3,
                            projection,
                            null,
                            null,
                            null,
                            null,
                            DBSecurities.COLUMN_NAME+"ASC"
                    );


                } else {
                    cursor = db.query(
                            DBSecurities.TABLE_NAME3,
                            projection,
                            null,
                            null,
                            null,
                            null,
                            DBSecurities.COLUMN_NAME + "DESC"
                    );
                }
            }
        }
        if (heading.equals("Валюта")) {


            if (n == 0) {
                cursor = db.query(
                        DBSecurities.TABLE_NAME4,
                        projection,
                        null,
                        null,
                        null,
                        null,
                        null);
            }
            if (n == 1) {
                if (s.equals("ascending")) {
                    cursor = db.query(
                            DBSecurities.TABLE_NAME4,
                            projection,
                            null,
                            null,
                            null,
                            null,
                            DBSecurities.COLUMN_PRICE+"ASC");

                } else {
                    cursor = db.query(
                            DBSecurities.TABLE_NAME4,
                            projection,
                            null,
                            null,
                            null,
                            null,
                            DBSecurities.COLUMN_PRICE + "DESC");
                }
            }
            if (n == 2) {
                if (s.equals("ascending")) {
                    cursor = db.query(
                            DBSecurities.TABLE_NAME4,
                            projection,
                            null,
                            null,
                            null,
                            null,
                            DBSecurities.COLUMN_NAME+"ASC"
                    );


                } else {
                    cursor = db.query(
                            DBSecurities.TABLE_NAME4,
                            projection,
                            null,
                            null,
                            null,
                            null,
                            DBSecurities.COLUMN_NAME + "DESC"
                    );
                }
            }
        }
        try {

            assert cursor != null;
            int nameColumnIndex = cursor.getColumnIndex(DBSecurities.COLUMN_NAME);
            int companyColumnIndex = cursor.getColumnIndex(DBSecurities.COLUMN_COMPANY);
            int imageColumnIndex = cursor.getColumnIndex(DBSecurities.COLUMN_IMAGE);
            int priceColumnIndex = cursor.getColumnIndex(DBSecurities.COLUMN_PRICE);



            while (cursor.moveToNext()) {
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


        } finally {
            cursor.close();
        }
    }



}


