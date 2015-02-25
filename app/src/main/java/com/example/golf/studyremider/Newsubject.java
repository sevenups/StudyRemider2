package com.example.golf.studyremider;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class Newsubject extends ActionBarActivity implements View.OnFocusChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsubject);

        EditText ed1 = (EditText)findViewById(R.id.editText);
        EditText ed2 = (EditText)findViewById(R.id.editText2);
        //ed1.setOnFocusChangeListener(this);
        //ed2.setOnFocusChangeListener(this);

        //hasFocus1 = false;
        //hasFocus2 = false;
    }

    boolean hasFocus1;
    boolean hasFocus2;

    public void onClicked2(View v) {

        EditText ed1 = (EditText)findViewById(R.id.editText);
        EditText ed2 = (EditText)findViewById(R.id.editText2);

        ToDoDBHelper helper = new ToDoDBHelper(this);
        SQLiteDatabase db1 = helper.getWritableDatabase();
        ContentValues r = new ContentValues();
        r.put("title", ed1.getText().toString());
        r.put("cname", ed2.getText().toString());
        long new_id = db1.insert("todo2", null, r);

        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_newsubject, menu);
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
    public void onFocusChange(View v, boolean hasFocus) {

        if(v == findViewById(R.id.editText)){
           // hasFocus1 = hasFocus;
        }else{
            hasFocus2 = hasFocus;
        }

        if(hasFocus1 && hasFocus2){

            EditText ed1 = (EditText)findViewById(R.id.editText);
            EditText ed2 = (EditText)findViewById(R.id.editText2);

            ToDoDBHelper helper = new ToDoDBHelper(this);
            SQLiteDatabase db1 = helper.getWritableDatabase();
            ContentValues r = new ContentValues();
            r.put("title", ed1.getText().toString());
            r.put("cname", ed2.getText().toString());
            long new_id = db1.insert("todo2", null, r);

            finish();

        }

    }
}
