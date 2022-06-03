package com.example.myapplication111;

import androidx.appcompat.app.AppCompatActivity;


import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuInflater;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.MenuItem;

public class SearchableActivity extends ListActivity {
     DBSecurities mDbHelper;
     static String TAG ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
        Intent intent = getIntent();
        mDbHelper=new DBSecurities(this);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
    }
    private void doMySearch(String query) {
        Cursor cursor = null;
        if(TAG.equals("MainActivity4_S") ) {
            cursor = mDbHelper.fetchRecordsByQuery1(query);
        }
        if(TAG.equals("MainActivity4_B") ) {
            cursor = mDbHelper.fetchRecordsByQuery2(query);
        }
        if(TAG.equals("MainActivity4_F") ) {
            cursor = mDbHelper.fetchRecordsByQuery3(query);
        }
        if(TAG.equals("MainActivity4_C") ) {
            cursor = mDbHelper.fetchRecordsByQuery4(query);
        }
        String[] from = new String[]{DBSecurities.COLUMN_NAME};
        int[] to = new int[]{R.id.textr};
        startManagingCursor(cursor);

        if(TAG.equals("MainActivity6")){
           ArrayAdapter<String> records = new ArrayAdapter<String>(this,
                    R.layout.reqord,  mDbHelper.fetchRecordsByQuery6(query));
            setListAdapter(records);
        }else{
            SimpleCursorAdapter records = new SimpleCursorAdapter(this,
                    R.layout.reqord, cursor, from, to);
            setListAdapter(records);
        }

    }

}

