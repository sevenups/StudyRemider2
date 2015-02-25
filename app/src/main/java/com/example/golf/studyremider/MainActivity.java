package com.example.golf.studyremider;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener,
        AdapterView.OnItemLongClickListener {

    ToDoDBHelper helper;
    SimpleCursorAdapter adapter;
    SwipeDetector sd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new ToDoDBHelper(this);


        //SQLiteDatabase db1 = helper.getWritableDatabase();
        //ContentValues r = new ContentValues();
        //r.put("title", "ITS327");
        //r.put("cname", "Computer Network Architectures and Protocols");
        //long new_id = db1.insert("todo2", null, r);




        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM todo2 ORDER BY title DESC;", null);
        cursor.moveToFirst(); // get the first row
        String title = cursor.getString(0);


        adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2,
                cursor,
                new String[] {"title", "cname"},
                new int[] {android.R.id.text1, android.R.id.text2},
                0);

        ListView lv = (ListView)findViewById(R.id.listView);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
        lv.setOnItemLongClickListener(this);
        db.close();
        
    }

    @Override
    protected void onResume() {
        super.onResume();

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM todo2 ORDER BY title DESC;", null);
        cursor.moveToFirst(); // get the first row
        String title = cursor.getString(0);


        adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2,
                cursor,
                new String[] {"title", "cname"},
                new int[] {android.R.id.text1, android.R.id.text2},
                0);

        ListView lv = (ListView)findViewById(R.id.listView);
        sd = new SwipeDetector();
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
        lv.setOnItemLongClickListener(this);
        lv.setOnTouchListener(sd);
        db.close();
    }



    public void onClicked(View v) {

        Intent i = new Intent(this, Newsubject.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {




        if(sd.swipeDetected()) {
            if(sd.getAction() == SwipeDetector.Action.RL || sd.getAction() == SwipeDetector.Action.LR) {
                Intent i = new Intent(this, Newsubject.class);
                startActivity(i);
            }
        }else{
            SQLiteDatabase db = helper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM todo2 WHERE _id = " + id, null);
            cursor.moveToFirst(); // get the first row
            String title = cursor.getString(1);
            Intent i = new Intent(this, remiderlist.class);
            i.putExtra("title", title);
            i.putExtra("cid", cursor.getString(0));
            startActivity(i);
        }

    }



    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        ToDoDBHelper helper = new ToDoDBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        int n = db.delete("todo2",
                "_id = ?",
                new String[]{Long.toString(id)});

        if (n == 1) {
            Toast t = Toast.makeText(this.getApplicationContext(),
                    "Successfully deleted the selected item.",
                    Toast.LENGTH_SHORT);
            t.show();

            // retrieve a new collection of records
            Cursor cursor = db.rawQuery(
                    "SELECT * FROM todo2 ORDER BY title DESC;",
                    null);

            // update the adapter
            adapter.changeCursor(cursor);
        }
        db.close();
        return true;
    }

}
