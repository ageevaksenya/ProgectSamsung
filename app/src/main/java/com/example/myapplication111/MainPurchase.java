package com.example.myapplication111;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.utils.EntryXComparator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainPurchase extends AppCompatActivity {
    public static TextView  t_name;
    static TextView txt_price;
    static byte[] im;
    static TextView t_comp;
    private DBSecurities.OpenHelper dbOpenHelper;
    int countsec=0;
    static int count_seccurit1=1;
    List<Integer> listPrice;
    boolean flagScale=true;
    boolean flagRedrawing;
    LineChart chart;
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        listPrice=new ArrayList<>();
        t_name=findViewById(R.id.t_name);
        player = MediaPlayer.create(this, R.raw.music);
        t_comp=findViewById(R.id.t_comp);
        ImageButton buttonBack=findViewById(R.id.back);
        buttonBack.setOnClickListener(view -> finish());
        Call<String> call = MyServer.service.price("price",t_name.getText().toString(),t_comp.getText().toString());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    int beginIndex = 0;
                    for (int i = 0; i < response.body().length(); i++) {
                        if (response.body().substring(beginIndex).contains(",")) {
                            if (response.body().charAt(i) == ',') {
                                listPrice.add(Integer.parseInt(response.body().substring(beginIndex, i)));
                                beginIndex = i + 1;
                            }
                        } else listPrice.add(Integer.parseInt(response.body().substring(beginIndex)));

                    }

                }
                Toast.makeText(MainPurchase.this, response.body(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(MainPurchase.this, "сбой сети :( повторите попытку", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainPurchase.this, "проблема с конверсией! :(", Toast.LENGTH_SHORT).show();
                }
            }

        });
        ImageView im_robot= findViewById(R.id.im_robot);
        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.earthquake);
        final View linear =  findViewById(R.id.linear);


        ImageButton btn =findViewById(R.id.btn_price);

        btn.setOnClickListener(view -> {
            if(count_seccurit1<1){
                count_seccurit1=1;
            }
        });
         txt_price=findViewById(R.id.textprice);
         txt_price.setText(listPrice.get(listPrice.size()-1));
         chart = findViewById(R.id.chart);
        ArrayList<Entry> entries1 = new ArrayList<>();
        ArrayList<Entry> entries2 = new ArrayList<>();


        if(listPrice.size()>=180){
            float sumPrice=0;
            for (int i = 1; i <=180 ; i++) {
                sumPrice+=listPrice.get(i-1);
                if(i%30==0){
                    entries1.add(new Entry((float)(i / 30), (float)(sumPrice/30)));
                    sumPrice=0;
                }
            }
            Collections.sort(entries1, new EntryXComparator());

        }else flagScale=false;
            for (int i = 0; i <listPrice.size() ; i++) {
                entries2.add(new Entry((float)(i+1), (float)(listPrice.get(i))));


            }
        Collections.sort(entries2, new EntryXComparator());



        if(flagScale) {
            chart=panache(chart, entries1,6f);
            chart.invalidate();
        } else chart=panache(chart,entries2,(float)(listPrice.size()+1));
           chart.invalidate();
        flagRedrawing=flagScale;



        chart.setOnChartGestureListener(new OnChartGestureListener()
        {
            @Override
            public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

            }

            @Override
            public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

            }

            @Override
            public void onChartLongPressed(MotionEvent me) {

            }

            @Override
            public void onChartDoubleTapped(MotionEvent me) {
                if(flagScale&&flagRedrawing){
                    chart=panache(chart,entries2,(float)(listPrice.size()+1));
                    flagRedrawing=false;
                    chart.invalidate();
                }else if(flagScale&&!flagRedrawing){
                    chart=panache(chart,entries1,6f);
                    flagRedrawing=true;
                    chart.invalidate();
                }


            }

            @Override
            public void onChartSingleTapped(MotionEvent me) {
                Toast.makeText(MainPurchase.this,"График изменения цены на протяжении времени", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

            }

            @Override
            public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
               if(flagScale&&flagRedrawing){
                   chart=panache(chart,entries2,(float)(listPrice.size()+1));
                   flagRedrawing=false;
                   chart.invalidate();
               }else if(flagScale&&!flagRedrawing){
                   chart=panache(chart,entries1,6f);
                   flagRedrawing=true;
                   chart.invalidate();
               }

            }

            @Override
            public void onChartTranslate(MotionEvent me, float dX, float dY) {

            }
        }
        );


        btn.setOnClickListener(view -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(MainPurchase.this);
            alert.setMessage("Введите желаемое количество инвестиций для покупки");
            alert.setTitle("Покупка");
            final EditText input = new EditText(MainPurchase.this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            input.setLayoutParams(lp);
            alert.setView(input);
            alert.setIcon(R.mipmap.ic_launcher12_foreground);
            alert.setPositiveButton("Купить",
                    (dialog, which) -> {

                        try {
                            countsec = Integer.parseInt(input.getText().toString());
                        } catch (NumberFormatException e) {
                            linear.startAnimation(animation);
                            Toast.makeText(getApplicationContext(), "Данные введены неккоректно", Toast.LENGTH_SHORT).show();
                            dialog.cancel();

                        }
                        if (countsec <= 0) {
                            countsec = 1;
                        }
                        dialog.cancel();

                    });

            alert.setNegativeButton("Отмена", (dialog, whichButton) -> dialog.cancel());

            alert.show();
            if (MyData.getMy_purse() - countsec * Integer.parseInt(txt_price.getText().toString()) < 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainPurchase.this);
                builder.setTitle("Ошибка!")
                        .setMessage("У вас недостаточно средств!")
                        .setIcon(R.mipmap.ic_vv_round)
                        .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Закрываем окно
                                dialog.cancel();
                            }
                        });
                alert.show();
            } else {

                Call<Tk> call2 = MyServer.service.purchase("purchase",MyData.getToken(),countsec * Integer.parseInt(txt_price.getText().toString()));
                call2.enqueue(new Callback<Tk>() {
                    @Override
                    public void onResponse(Call<Tk> call, Response<Tk> response) {
                        boolean flag=false;
                        if (response.isSuccessful()) {
                            MyData myData=new MyData();
                            if(MainListSecurities.heading.equals("АКЦИИ")) MainMyPurchase.priceA+=countsec*Integer.parseInt(txt_price.getText().toString());
                            if(MainListSecurities.heading.equals("ОБЛИГАЦИИ")) MainMyPurchase.priceO+=countsec*Integer.parseInt(txt_price.getText().toString());
                            if(MainListSecurities.heading.equals("ВАЛЮТЫ")) MainMyPurchase.priceB+=countsec*Integer.parseInt(txt_price.getText().toString());
                            if(MainListSecurities.heading.equals("ФБЮЧЕРСЫ")) MainMyPurchase.priceF+=countsec*Integer.parseInt(txt_price.getText().toString());
                            myData.setToken(response.body().getToken());

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
                                int idColumnIndex = cursor.getColumnIndex(DBSecurities.COLUMN_ID);
                                int nameColumnIndex = cursor.getColumnIndex(DBSecurities.COLUMN_NAME);
                                int companyColumnIndex = cursor.getColumnIndex(DBSecurities.COLUMN_COMPANY);
                                int imageColumnIndex = cursor.getColumnIndex(DBSecurities.COLUMN_IMAGE);
                                int countColumnIndex=cursor.getColumnIndex(DBSecurities.COLUMN_COUNT);
                                int priceColumnIndex = cursor.getColumnIndex(DBSecurities.COLUMN_PRICE);



                                // Проходим через все ряды
                                while (cursor.moveToNext()) {
                                    // Используем индекс для получения строки или числа
                                    int currentID = cursor.getInt(idColumnIndex);
                                    byte[] currentImage=cursor.getBlob(imageColumnIndex);
                                    int currentCount = cursor.getInt(countColumnIndex);
                                    String currentName = cursor.getString(nameColumnIndex);
                                    int currentPrice = cursor.getInt(priceColumnIndex);
                                    String currentCompany = cursor.getString(companyColumnIndex);
                                    if(currentName.equals(t_name.getText().toString()) && currentCompany.equals(t_comp.getText().toString())){
                                        DBSecurities.update5(new Cell2(currentID,currentName,currentCompany,currentImage,currentPrice,currentCount+countsec));
                                        flag=true;
                                        break;

                                    }



                                }
                                // Выводим значения каждого столбца

                            } finally {
                                cursor.close();
                            }
                            if(!flag){
                                DBSecurities.insert5(t_comp.getText().toString(),im,Integer.parseInt(txt_price.getText().toString()),t_name.getText().toString(),countsec);
                            }
                            if(MainChoice.flag_registr ){
                                im_robot.setVisibility(View.VISIBLE);
                                linear.startAnimation(animation);
                                try {
                                    Thread.sleep(10000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                im_robot.setVisibility(View.INVISIBLE);


                            }
                        }


                        else {
                            Toast.makeText(MainPurchase.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Tk> call, Throwable t) {
                        if (t instanceof IOException) {
                            Toast.makeText(MainPurchase.this, "сбой сети :( повторите попытку", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainPurchase.this, "проблема с конверсией! :(", Toast.LENGTH_SHORT).show();
                        }
                    }

                });

            }
        });

    }

    public static LineChart panache(LineChart chart,List<Entry>entries,float xValue){
        LineDataSet dataset = new LineDataSet(entries, "Цена");
        LineData data = new LineData(dataset);
        chart.setData(data);
        chart.setNoDataText("Данных нет");
        chart.setGridBackgroundColor(Color.YELLOW);
        chart.setDragEnabled(true);
        chart.setAutoScaleMinMaxEnabled(true);
        chart.moveViewToX(xValue);
        chart.setVisibleXRangeMaximum(20f);
        chart.invalidate();
        dataset.setDrawFilled(true);


        // График будет зеленого цвета
        dataset.setColor(Color.GREEN);

        // График будет плавным
        dataset.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        // График будет анимироваться 0.5 секунды
        chart.animateY(500);
        return chart;
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
                        Toast.makeText(MainPurchase.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Tk> call, Throwable t) {
                        if (t instanceof IOException) {
                            Toast.makeText(MainPurchase.this, "сбой сети :( повторите попытку", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(MainPurchase.this, "проблема с конверсией! :(", Toast.LENGTH_SHORT).show();
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
}
