package com.example.myapplication111;

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

import androidx.appcompat.app.AppCompatActivity;

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

public class MainPurchaseSale extends AppCompatActivity {
    static TextView t_name;
    static TextView t_comp;
    static TextView txt_price;
    static int countsec;
    static int count_seccurit;
    private DBSecurities.OpenHelper dbOpenHelper;
    static TextView text_count;
    MediaPlayer mPlayer;
    LineChart chart;
    boolean flagScale = true;
    boolean flagRedrawing;
    MediaPlayer player;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main9);
        ImageButton buttonBack=findViewById(R.id.back);
        player = MediaPlayer.create(this, R.raw.music);
        buttonBack.setOnClickListener(view -> finish());
        List<Integer> listPrice = new ArrayList<>();
        t_name = findViewById(R.id.t_name);
        t_comp = findViewById(R.id.t_comp);
        Call<String> call = MyServer.service.price("price", t_name.getText().toString(), t_comp.getText().toString());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    int beginIndex = 0;
                    for (int i = 0; i < response.body().length(); i++) {
                        if (response.body().substring(beginIndex).contains(",")) {
                            if (response.body().charAt(i) == ',') {
                                listPrice.add(Integer.parseInt(response.body().substring(beginIndex, i)));
                                beginIndex = i + 1;
                            }
                        } else
                            listPrice.add(Integer.parseInt(response.body().substring(beginIndex)));

                    }

                }
                Toast.makeText(MainPurchaseSale.this, "Данная акция сошла с рынка", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(MainPurchaseSale.this, "сбой сети :( повторите попытку", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainPurchaseSale.this, "проблема с конверсией! :(", Toast.LENGTH_SHORT).show();
                }
            }

        });
        ImageView im_robot_purchase = findViewById(R.id.im_robot_purche);
        ImageView im_robot_sale = findViewById(R.id.im_robot_sale);
        final Animation animation = AnimationUtils.loadAnimation(this, R.anim.earthquake);
        final View linear = findViewById(R.id.linear);
        chart = findViewById(R.id.chart);
        txt_price = findViewById(R.id.textprice);
        text_count = findViewById(R.id.textView4);
        ImageButton purchase = findViewById(R.id.btn_price);
        ImageButton sale = findViewById(R.id.btn_pr);
        String value = text_count.getText().toString();
        count_seccurit = Integer.parseInt(value);
        ArrayList<Entry> entries1 = new ArrayList<>();
        ArrayList<Entry> entries2 = new ArrayList<>();


        if (listPrice.size() >= 180) {
            float sumPrice = 0;
            for (int i = 1; i <= 180; i++) {
                sumPrice += listPrice.get(i - 1);
                if (i % 30 == 0) {
                    entries1.add(new Entry((float) (i / 30), (float) (sumPrice / 30)));
                    sumPrice = 0;
                }
            }
            Collections.sort(entries1, new EntryXComparator());

        } else flagScale = false;
        for (int i = 0; i < listPrice.size(); i++) {
            entries2.add(new Entry((float) (i + 1), (float) (listPrice.get(i))));


        }
        Collections.sort(entries2, new EntryXComparator());
        if (flagScale) {
            chart = panache(chart, entries1, 6f);
            chart.invalidate();
        } else chart = panache(chart, entries2, (float) (listPrice.size() + 1));
        chart.invalidate();
        flagRedrawing = flagScale;
        chart.setOnChartGestureListener(new OnChartGestureListener() {
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
                if (flagScale && flagRedrawing) {
                    chart = panache(chart, entries2, (float) (listPrice.size() + 1));
                    flagRedrawing = false;
                    chart.invalidate();
                } else if (flagScale && !flagRedrawing) {
                    chart = panache(chart, entries1, 6f);
                    flagRedrawing = true;
                    chart.invalidate();
                }


            }

            @Override
            public void onChartSingleTapped(MotionEvent me) {
                Toast.makeText(MainPurchaseSale.this, "График изменения цены на протяжении времени", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

            }

            @Override
            public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
                if (flagScale && flagRedrawing) {
                    chart = panache(chart, entries2, (float) (listPrice.size() + 1));
                    flagRedrawing = false;
                    chart.invalidate();
                } else if (flagScale && !flagRedrawing) {
                    chart = panache(chart, entries1, 6f);
                    flagRedrawing = true;
                    chart.invalidate();
                }

            }

            @Override
            public void onChartTranslate(MotionEvent me, float dX, float dY) {

            }
        }
        );


        purchase.setOnClickListener(view -> {
            mPlayer = MediaPlayer.create(this, R.raw.cry);
            AlertDialog.Builder alert = new AlertDialog.Builder(MainPurchaseSale.this);
            alert.setMessage("Введите желаемое количество инвестиций для покупки");
            alert.setTitle("Покупка");
            final EditText input = new EditText(MainPurchaseSale.this);
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
                            Toast.makeText(getApplicationContext(), "Данные введены неккоректно", Toast.LENGTH_SHORT).show();
                            linear.startAnimation(animation);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(MainPurchaseSale.this);
                builder.setTitle("Ошибка!")
                        .setMessage("У вас недостаточно средств!")
                        .setIcon(R.mipmap.ic_vv_round)
                        .setPositiveButton("ОК", (dialog, id) -> {
                            // Закрываем окно
                            dialog.cancel();
                        });
                alert.show();
            } else {

                Call<Tk> call2 = MyServer.service.purchase("purchase", MyData.getToken(), countsec * Integer.parseInt(txt_price.getText().toString()));
                call2.enqueue(new Callback<Tk>() {
                    @Override
                    public void onResponse(Call<Tk> call, Response<Tk> response) {

                        if (response.isSuccessful()) {
                            MyData myData = new MyData();
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
                                int countColumnIndex = cursor.getColumnIndex(DBSecurities.COLUMN_COUNT);
                                int priceColumnIndex = cursor.getColumnIndex(DBSecurities.COLUMN_PRICE);


                                // Проходим через все ряды
                                while (cursor.moveToNext()) {
                                    // Используем индекс для получения строки или числа
                                    int currentID = cursor.getInt(idColumnIndex);
                                    byte[] currentImage = cursor.getBlob(imageColumnIndex);
                                    int currentCount = cursor.getInt(countColumnIndex);
                                    String currentName = cursor.getString(nameColumnIndex);
                                    int currentPrice = cursor.getInt(priceColumnIndex);
                                    String currentCompany = cursor.getString(companyColumnIndex);
                                    if (currentName.equals(t_name.getText().toString()) && currentCompany.equals(t_comp.getText().toString())) {
                                        DBSecurities.update5(new Cell2(currentID, currentName, currentCompany, currentImage, currentPrice, currentCount + countsec));
                                        break;

                                    }


                                }

                            } finally {
                                cursor.close();
                            }
                            if (MainChoice.flag_registr) {
                                im_robot_purchase.setVisibility(View.VISIBLE);
                                linear.startAnimation(animation);
                                try {
                                    Thread.sleep(10000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                im_robot_purchase.setVisibility(View.INVISIBLE);


                            }

                        } else {
                            Toast.makeText(MainPurchaseSale.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Tk> call, Throwable t) {
                        if (t instanceof IOException) {
                            Toast.makeText(MainPurchaseSale.this, "сбой сети :( повторите попытку", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainPurchaseSale.this, "проблема с конверсией! :(", Toast.LENGTH_SHORT).show();
                        }
                    }


                });
            }
        });

        sale.setOnClickListener(view -> {
            mPlayer = MediaPlayer.create(this, R.raw.cry);
            AlertDialog.Builder alert = new AlertDialog.Builder(MainPurchaseSale.this);
            alert.setMessage("Введите желаемое количество инвестиций для продажи");
            alert.setTitle("Продажа");
            final EditText input = new EditText(MainPurchaseSale.this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            input.setLayoutParams(lp);
            alert.setView(input);
            alert.setIcon(R.mipmap.ic_launcher12_foreground);
            alert.setPositiveButton("Продать",
                    (dialog, which) -> {

                        try {
                            countsec = Integer.parseInt(input.getText().toString());
                        } catch (NumberFormatException e) {
                            Toast.makeText(getApplicationContext(), "Данные введены неккоректно", Toast.LENGTH_SHORT).show();
                            linear.startAnimation(animation);
                            dialog.cancel();

                        }
                        if (countsec <= 0) {
                            countsec = 1;
                        }
                        dialog.cancel();

                    });

            alert.setNegativeButton("Отмена", (dialog, whichButton) -> dialog.cancel());

            alert.show();


            Call<Tk> call2 = MyServer.service.sale("Sale", MyData.getToken(), countsec * Integer.parseInt(txt_price.getText().toString()));
            call2.enqueue(new Callback<Tk>() {
                @Override
                public void onResponse(Call<Tk> call, Response<Tk> response) {

                    if (response.isSuccessful()) {
                        MyData myData = new MyData();
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
                            int countColumnIndex = cursor.getColumnIndex(DBSecurities.COLUMN_COUNT);
                            int priceColumnIndex = cursor.getColumnIndex(DBSecurities.COLUMN_PRICE);

                            while (cursor.moveToNext()) {
                                int currentID = cursor.getInt(idColumnIndex);
                                byte[] currentImage = cursor.getBlob(imageColumnIndex);
                                int currentCount = cursor.getInt(countColumnIndex);
                                String currentName = cursor.getString(nameColumnIndex);
                                int currentPrice = cursor.getInt(priceColumnIndex);
                                String currentCompany = cursor.getString(companyColumnIndex);
                                if (currentName.equals(t_name.getText().toString()) && currentCompany.equals(t_comp.getText().toString())) {
                                    DBSecurities.update5(new Cell2(currentID, currentName, currentCompany, currentImage, currentPrice, currentCount - countsec));

                                    break;

                                }


                            }
                        } finally {
                            cursor.close();
                        }
                        if (MainChoice.flag_registr) {
                            im_robot_sale.setVisibility(View.VISIBLE);
                            linear.startAnimation(animation);
                            try {
                                Thread.sleep(10000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            im_robot_sale.setVisibility(View.INVISIBLE);


                        }

                    } else {
                        Toast.makeText(MainPurchaseSale.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Tk> call, Throwable t) {
                    if (t instanceof IOException) {
                        Toast.makeText(MainPurchaseSale.this, "сбой сети :( повторите попытку", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainPurchaseSale.this, "проблема с конверсией! :(", Toast.LENGTH_SHORT).show();
                    }
                }


            });
        });
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

    public static LineChart panache(LineChart chart, List<Entry> entries, float xValue) {
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


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menutoken:
                item.setChecked(true);
                Call<Tk> call = MyServer.service.token("Token");
                call.enqueue(new Callback<Tk>() {
                    @Override
                    public void onResponse(Call<Tk> call, Response<Tk> response) {
                        if (response.isSuccessful()) {
                            MyData myData = new MyData();
                            myData.setToken(response.body().getToken());

                        }
                        Toast.makeText(MainPurchaseSale.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Tk> call, Throwable t) {
                        if (t instanceof IOException) {
                            Toast.makeText(MainPurchaseSale.this, "сбой сети :( повторите попытку", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainPurchaseSale.this, "проблема с конверсией! :(", Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(this, MainAuthorization.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}