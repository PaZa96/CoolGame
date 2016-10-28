package com.example.paza.coolgame;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.paza.coolgame.database.DBHelper;

import java.io.File;

public class NewUsers extends AppCompatActivity {

    public Button btnCreate;
    public String mString ;
    public EditText mTextView;
    public DBHelper mDBHelper = new DBHelper(this);
    private ImageButton imageBtn;
    private final int CAMERA_RESULT = 0;
    final String TAG = "myLogs";
    File directory;

    public void addToDB(String name, String path){
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        cv.put(DBHelper.COLUMN_NAME, name);
        cv.put(DBHelper.COLUMN_IMAGE, path);
        db.insert(DBHelper.TABLE, null, cv);

        db.close();

    }

    private Uri generateFileUri() {
        File file = null;
        file = new File(directory.getPath() + "/" + "photo_" + System.currentTimeMillis() + ".jpg");
        Log.d(TAG, "fileName = " + file);
        mString = file.toString();
        return Uri.fromFile(file);
    }

    private void createDirectory() {
        directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "CoolGame");
        if (!directory.exists())
            directory.mkdirs();
    }

    public void onClickPhoto(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, generateFileUri());
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_users);
        createDirectory();
        btnCreate = (Button) findViewById(R.id.buttonCreate);
        mTextView = (EditText) findViewById(R.id.textView2) ;
        imageBtn = (ImageButton) findViewById(R.id.imageButton);

        View.OnClickListener onclick = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String name = mTextView.getText().toString();
               if(!name.isEmpty()) {
                   addToDB(name, mString);
                   Intent intent = new Intent(NewUsers.this, LoginActivity.class);
                   startActivity(intent);
                   finish();
               }

            }
        };
        btnCreate.setOnClickListener(onclick);

        imageBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, generateFileUri());
                startActivityForResult(intent, CAMERA_RESULT);

            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Log.d(TAG, "Photo uri: " + intent.getData());
        Bundle bndl = intent.getExtras();
        if (bndl != null) {
            Object obj = intent.getExtras().get("data");
            if (obj instanceof Bitmap) {
                Bitmap bitmap = (Bitmap) obj;
                Log.d(TAG, "bitmap " + bitmap.getWidth() + " x "
                        + bitmap.getHeight());
                imageBtn.setImageBitmap(bitmap);
                // imageBtn.setImageURI(Uri.parse(mString));
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu1) {
        getMenuInflater().inflate(R.menu.main1, menu1);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.activity_login:

                Intent intObj = new Intent(NewUsers.this, LoginActivity.class);
                startActivity(intObj);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
