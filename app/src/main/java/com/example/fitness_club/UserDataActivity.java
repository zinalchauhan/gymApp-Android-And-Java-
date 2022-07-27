package com.example.fitness_club;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class UserDataActivity extends AppCompatActivity {

    DBHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;
    ListView listView;
    String []name ;
    String []email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);

        dbHelper = new DBHelper(UserDataActivity.this);
        findid();
        dis();

    }

    private void dis(){

        sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from users" , null);

        if(cursor.getCount()>0){

            name = new String[cursor.getCount()];
            email = new String[cursor.getCount()];

            int i = 0;
            while (cursor.moveToNext()){
                name[i] = cursor.getString(0);
                email[i] = cursor.getString(2);
                i++;
            }

            UserDataActivity.Custom adapter = new UserDataActivity.Custom();
            listView.setAdapter(adapter);
        }
    }

    private void findid(){
        listView = findViewById(R.id.listview);
    }

    private class Custom extends BaseAdapter {

        @Override
        public int getCount() {
            return name.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position  ) {
            return 0;
        }

        @SuppressLint("Range")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            TextView txt_Name , txt_Email ;
            ImageView delete;

            convertView = LayoutInflater.from(UserDataActivity.this).inflate(R.layout.singledatauser , parent , false);
            txt_Name = convertView.findViewById(R.id.txt_Name);
            txt_Email = convertView.findViewById(R.id.txt_Email);
            delete = convertView.findViewById(R.id.deletedata);

            txt_Name.setText(name[position]);
            txt_Email.setText(email[position]);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    sqLiteDatabase = dbHelper.getReadableDatabase();
                    long recd = sqLiteDatabase.delete("users" , "username = ?" , new String[]{name[position]});

                    if(recd!=-1){
                        Toast.makeText(UserDataActivity.this , "Record deleted successfully ... :)" , Toast.LENGTH_SHORT).show();
                        dis();
                    }
                }
            });

            return convertView;
        }
    }
}