package com.example.myapplication111;


import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageButton;
import androidx.paging.PagedList;
import android.widget.SearchView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.Observer;

import androidx.lifecycle.ViewModelProviders;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainLeaders extends AppCompatActivity {
    private DBSecurities.OpenHelper dbOpenHelper;
    MediaPlayer player;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        player = MediaPlayer.create(this, R.raw.music);
        dbOpenHelper = new DBSecurities.OpenHelper(this);
        ImageButton button=findViewById(R.id.back);
        RecyclerView recyclerView=findViewById(R.id.list);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        EmployeeAdapter adapter = new EmployeeAdapter(this);
        ItemViewModel itemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
        itemViewModel.itemPagedList.observe( this, new Observer<PagedList<UsersB>>() {
            @Override
            public void onChanged(@Nullable PagedList<UsersB> items) {
                adapter.submitList(items);
            }
        });


        recyclerView.setAdapter(adapter);

        recyclerView.setAdapter(adapter);
        Call<List<String>> call = MyServer.service.users_list("UsersList");
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    DBSecurities.list =response.body();

                }
                assert response.body() != null;
                Toast.makeText(MainLeaders.this, response.body().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(MainLeaders.this, "сбой сети :( повторите попытку", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainLeaders.this, "проблема с конверсией! :(", Toast.LENGTH_SHORT).show();
                }

            }


        });


        button.setOnClickListener(view -> {
            finish();
        });

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        SearchableActivity.TAG="MainActivity6";
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
                        assert response.body() != null;
                        Toast.makeText(MainLeaders.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Tk> call, Throwable t) {
                        if (t instanceof IOException) {
                            Toast.makeText(MainLeaders.this, "сбой сети :( повторите попытку", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(MainLeaders.this, "проблема с конверсией! :(", Toast.LENGTH_SHORT).show();
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

