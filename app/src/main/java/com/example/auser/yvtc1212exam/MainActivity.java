package com.example.auser.yvtc1212exam;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList idArray = new ArrayList();
    public static ArrayList<String> stitleArray = new ArrayList<>();
    public static ArrayList<Double> longitudeArray = new ArrayList<>();
    public static ArrayList<Double> latitudeArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBInfo.DB_FILE = getDatabasePath("taipeiviews")+".db";    //database的絕對路徑
        copyDBFile();

        getData();
    }

    public void btnNews(View v){
        Intent it = new Intent();
        it.setClass(MainActivity.this,NewsActivity.class);
        startActivity(it);
    }

    public void btnChat(View v){
        Intent it = new Intent();
        it.setClass(MainActivity.this,FireChat.class);
        startActivity(it);
    }

    public void btnMaps(View v){
        Intent it = new Intent();
        it.setClass(MainActivity.this,MapsActivity.class);
        startActivity(it);
    }

    public void getData(){
        MyDBHelper helper = MyDBHelper.getInstance(this);
        Cursor c = helper.getReadableDatabase().query("PopularView", null, null, null, null, null, null);

        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            int id = c.getInt(c.getColumnIndex("_id"));
            String stitle = c.getString(c.getColumnIndex("stitle"));
            double longitude = c.getDouble(c.getColumnIndex("longitude"));
            double latitude = c.getDouble(c.getColumnIndex("latitude"));
            idArray.add(id);
            stitleArray.add(stitle);
            longitudeArray.add(longitude);
            latitudeArray.add(latitude);

            c.moveToNext();
        }
        c.close();
    }


    public void copyDBFile()
    {
        try {
            File f = new File(DBInfo.DB_FILE);
            File dbDir = new File(DBInfo.DB_FILE.substring(0,DBInfo.DB_FILE.length()-15));
            Log.d("MyDBHelper", "copyFiles : "+DBInfo.DB_FILE);
            dbDir.mkdirs();
            if (! f.exists())
            {

                InputStream is = getResources().openRawResource(R.raw.taipeiviews);
                OutputStream os = new FileOutputStream(DBInfo.DB_FILE);
                int read;
                Log.d("taipeiviews.db", "Start Copy");
                while ((read = is.read()) != -1)
                {
                    os.write(read);
                }
                Log.d("taipeiviews.db", "FilesCopied");
                os.close();
                is.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
