package com.example.myapplication111;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainMyPurchase extends FragmentActivity {
    PieChart pieChart;
    static int priceA=2;
    static int priceO=3;
    static int priceB=7;
    static int priceF=5;
    int priceN=0;
    MediaPlayer player;
    private DBSecurities.OpenHelper dbOpenHelper;
    List<Securities2> list=new ArrayList<>();
    MediaPlayer mPlayer;
    private final String[] my= {"Акции","Облигации","Валюта","Фьючерсы"};
    private final int[] prises={priceA,priceO,priceB,priceF,priceN};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);
        if (priceA == 0&& priceB==0 && priceF==0 &&priceO==0)

        {
            priceN = 1;
        }

        ImageButton buttonBack=findViewById(R.id.back);
        player = MediaPlayer.create(this, R.raw.music);
        buttonBack.setOnClickListener(view -> finish());
        TextView wall=findViewById(R.id.textView3);
        wall.setText(String.valueOf(MyData.getMy_purse()));
        TextView text_pr=findViewById(R.id.text_pr );
        pieChart=findViewById(R.id.idPieChart);
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(30f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Мой портфель");
        pieChart.setCenterTextSize(15);
        pieChart.setCenterTextColor(Color.GREEN);
        pieChart.setDrawEntryLabels(true);
        addDataSet();
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                int pos1=e.toString().indexOf("(prise): " );
                String sales=e.toString().substring(pos1+9);
                int sum=0;
                for (int i = 0; i <my.length ; i++) {
                    if(prises[i]==(int)Float.parseFloat(sales)){
                        pos1=i;
                        break;
                    }
                    sum+=prises[i];

                }
                String employee=my[pos1];
                StringBuffer sb = new StringBuffer();
                if(priceA==0&&priceB==0&&priceF==0&&priceO==0){
                    text_pr.setText("У вас еще нет ценных бумаг");
                }else {
                    sb.append(employee).append(" составляют ").append(Math.round(((int)Float.parseFloat(sales) / sum) * 100)).append(" % от общего портфеля.");
                    text_pr.setText(sb);
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });
        SecuritiesAdapter2 adapter = new SecuritiesAdapter2(this, list);
        ListView lv =  findViewById(R.id.list);
        dbOpenHelper = new DBSecurities.OpenHelper(this);

        lv.setOnItemClickListener((parent, view, position, id) -> {
            mPlayer= MediaPlayer.create(this, R.raw.cry);
            TextView name=view.findViewById(R.id.name);
            TextView comp=view.findViewById(R.id.companiya);
            TextView capit=view.findViewById(R.id.capital1);
            TextView count=view.findViewById(R.id.count);
            MainPurchaseSale.t_name.setText(name.getText().toString());
            MainPurchaseSale.t_comp.setText(comp.getText().toString());
            MainPurchaseSale.txt_price.setText(capit.getText().toString());
            MainPurchaseSale.text_count.setText(count.getText().toString());
            Intent intent = new Intent(MainMyPurchase.this, MainPurchaseSale.class);
            MainMyPurchase.this.startActivity(intent);
        });
        lv.setAdapter(adapter);
        buttonBack.setOnClickListener(view -> finish());
        //pieChart.invalidate();

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem shareMenuItem3 = menu.findItem(R.id.menu_search);
        shareMenuItem3.setVisible(false);

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
                        Toast.makeText(MainMyPurchase.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Tk> call, Throwable t) {
                        if (t instanceof IOException) {
                            Toast.makeText(MainMyPurchase.this, "сбой сети :( повторите попытку", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(MainMyPurchase.this, "проблема с конверсией! :(", Toast.LENGTH_SHORT).show();
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

    private void addDataSet() {
        List<PieEntry> yEntry=new ArrayList<>();
        List <String> xEntry=new ArrayList<>();
        for (int pris : prises) {
            yEntry.add(new PieEntry(pris));

        }
        for (int i = 0; i <my.length ; i++) {
            xEntry.add(my[i]);

        }
        PieDataSet pieDataSet = new PieDataSet(yEntry,"Ценные бумаги");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);
        List<Integer> colors=new ArrayList<>();
        colors.add(Color.BLUE);
        colors.add(Color.GREEN);
        colors.add(Color.YELLOW);
        colors.add(Color.RED);
        colors.add(Color.CYAN);
        pieDataSet.setColors(colors);
        Legend legend= pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();



    }
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }




    private void displayDatabaseInfo() {

        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        String[] projection = {
                DBSecurities.COLUMN_ID,
                DBSecurities.COLUMN_NAME,
                DBSecurities.COLUMN_IMAGE,
                DBSecurities.COLUMN_COUNT,
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
            int countColumnIndex = cursor.getColumnIndex(DBSecurities.COLUMN_COUNT);
            int imageColumnIndex = cursor.getColumnIndex(DBSecurities.COLUMN_IMAGE);
            int priceColumnIndex = cursor.getColumnIndex(DBSecurities.COLUMN_PRICE);



            while (cursor.moveToNext()) {
                String currentImage = cursor.getString(imageColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentPrice = cursor.getInt(priceColumnIndex);
                int currentCount = cursor.getInt(countColumnIndex);
                Securities2 securities = new Securities2();
                securities.title = currentName;
                securities.count = currentCount;
                securities.idImage = currentImage;
                securities.price = currentPrice;
                list.add(securities);


            }


        } finally {
            cursor.close();
        }

    }
}

