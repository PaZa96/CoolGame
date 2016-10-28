package com.example.paza.coolgame;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.paza.coolgame.database.DBHelper;

import java.net.URI;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    public Button signin;
    public Spinner mSpinner;
    public static long setId;
    public Context context;
    public SimpleCursorAdapter spinAdapter;
    private TextView mTVWin;
    private String imageUrl;
    private TextView mTVLose;
    private Cursor c;

    public DBHelper dbHelper;
    public static SQLiteDatabase db;
    private ImageView mImageView;

    public static void setId(long id) {
        setId = id;
    }
    public long getSetId() {
        return setId;
    }

    public String getFromDb(long id, String columnName){
        String out;

        c = db.rawQuery("SELECT "+DBHelper.COLUMN_WIN_GAME+", "+ DBHelper.COLUMN_ID+", "+DBHelper.COLUMN_LOSE_GAME+", "+DBHelper.COLUMN_IMAGE+" FROM "+DBHelper.TABLE+" WHERE "+ DBHelper.COLUMN_ID + "==" + id +"", null);
        c.moveToFirst();
        out = c.getString(c.getColumnIndex(columnName));
        c.close();
        return out;
    }

    public void addCount(String columnName){

        String where = DBHelper.COLUMN_ID + "=" + getSetId() ;
        c = db.rawQuery("SELECT "+columnName+" FROM "+DBHelper.TABLE+" WHERE "+DBHelper.COLUMN_ID + "="+ getSetId() +"", null);


        if (c != null ) {
            if  (c.moveToFirst()) {
                int out = c.getInt(c.getColumnIndex(columnName));
                ContentValues cv = new ContentValues();
                cv.put(columnName, out+1);
                db.update(DBHelper.TABLE, cv, where, null);
            }
        }
        c.close();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mImageView = (ImageView) findViewById(R.id.imageView);
        dbHelper = new DBHelper(this);
        if (db == null) {
            db = dbHelper.getWritableDatabase();
        }

        mTVWin = (TextView) findViewById(R.id.textView5);
        mTVLose = (TextView) findViewById(R.id.textView6);

        signin = (Button) findViewById(R.id.button3);
        View.OnClickListener onclick = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(mSpinner.getSelectedItem() != null) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Create new User", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        };
        signin.setOnClickListener(onclick);

        mSpinner = (Spinner) findViewById(R.id.spinner3);

        c = db.rawQuery("SELECT "+DBHelper.COLUMN_WIN_GAME+","+DBHelper.COLUMN_IMAGE +", "+ DBHelper.COLUMN_ID+","+DBHelper.COLUMN_NAME+" FROM "+DBHelper.TABLE+"", null);
        final String[] from = new String[] {"name"};
        int[] to = new int[] {android.R.id.text1};
        spinAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, c, from, to,0);


        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(spinAdapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setId(id);
                ((TextView) parent.getChildAt(0)).setTextSize(25);
                mTVWin.setText(getFromDb(id, DBHelper.COLUMN_WIN_GAME));
                mTVLose.setText(getFromDb(id, DBHelper.COLUMN_LOSE_GAME));
                if(mSpinner.getSelectedItem() != null) {
                    imageUrl = getFromDb(id, DBHelper.COLUMN_IMAGE);
                    mImageView.setImageURI(Uri.parse(imageUrl));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                }
            });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.activity_new_users:
                Intent intObj = new Intent(this, NewUsers.class);
                startActivity(intObj);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }




}
