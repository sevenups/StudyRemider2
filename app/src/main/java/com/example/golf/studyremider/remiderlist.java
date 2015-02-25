package com.example.golf.studyremider;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class remiderlist extends ActionBarActivity implements AdapterView.OnItemClickListener,
        AdapterView.OnItemLongClickListener{

    ToDoDBHelper helper;
    SimpleCursorAdapter adapter;
    SwipeDetector sd;
    String cid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remiderlist);

        Intent i = getIntent();
        setTitle(i.getStringExtra("title"));
        cid = i.getStringExtra("cid");

        helper = new ToDoDBHelper(this);

       // SQLiteDatabase db1 = helper.getWritableDatabase();
       // ContentValues r = new ContentValues();
       // r.put("title", "Homework I");
       // r.put("date", "25/02/2015");
      // r.put("time", "17.00");
       // r.put("text", "-");
       // r.put("cid", cid);

        //long new_id = db1.insert("todo3", null, r);

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM todo3 WHERE cid = " + cid, null);
        ListView lv = (ListView)findViewById(R.id.listView2);
        sd = new SwipeDetector();

        if(cursor.getCount() == 0){
            String[] golf = {"Empty"};
            ArrayAdapter itemsAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, golf);

            lv.setAdapter(itemsAdapter);

        }else {

            cursor.moveToFirst(); // get the first row
            String title = cursor.getString(0);


            adapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_1, // A textview
                    cursor, // cursor to a data collection
                    new String[]{"title"}, // column to be displayed
                    new int[]{android.R.id.text1}, // ID of textview to display
                    0);

            lv.setAdapter(adapter);

        }


        lv.setOnItemClickListener(this);
        lv.setOnItemLongClickListener(this);
        lv.setOnTouchListener(sd);
        db.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_remiderlist, menu);

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
                Intent i = new Intent(this, add.class);
                i.putExtra("cid", cid);
                startActivity(i);
            }
        }else{
            SQLiteDatabase db = helper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM todo3 WHERE _id = " + id, null);
            cursor.moveToFirst(); // get the first row
            Intent i = new Intent(this, detail.class);
            i.putExtra("id", cursor.getString(0));
            startActivity(i);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM todo3 WHERE cid = " + cid, null);
        ListView lv = (ListView)findViewById(R.id.listView2);

        if(cursor.getCount() == 0){
            String[] golf = {"Empty"};
            ArrayAdapter itemsAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, golf);

            lv.setAdapter(itemsAdapter);

        }else {

            cursor.moveToFirst(); // get the first row
            String title = cursor.getString(0);


            adapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_1, // A textview
                    cursor, // cursor to a data collection
                    new String[]{"title"}, // column to be displayed
                    new int[]{android.R.id.text1}, // ID of textview to display
                    0);

            lv.setAdapter(adapter);

        }


        lv.setOnItemClickListener(this);
        lv.setOnItemLongClickListener(this);
        lv.setOnTouchListener(sd);
        db.close();

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        ToDoDBHelper helper = new ToDoDBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM todo3 WHERE cid = " + cid, null);
        ListView lv = (ListView)findViewById(R.id.listView2);

        if(cursor.getCount() == 0){

        }else {

            int n = db.delete("todo3",
                    "_id = ?",
                    new String[]{Long.toString(id)});

            if (n == 1) {
                Toast t = Toast.makeText(this.getApplicationContext(),
                        "Successfully deleted the selected item.",
                        Toast.LENGTH_SHORT);
                t.show();

                // retrieve a new collection of records
                Cursor cursor2 = db.rawQuery(
                        "SELECT * FROM todo3 WHERE _id = " + id,
                        null);

                // update the adapter
                adapter.changeCursor(cursor2);
            }
        }
        db.close();
        return true;
    }
}
