package com.example.fitness_club;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class UserAccountActivity extends AppCompatActivity {

    DBHelper obj = null;
    TextView txt_name , txt_email , txt_password;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        txt_name = findViewById(R.id.txt_name);
        txt_email = findViewById(R.id.txt_email);
        txt_password = findViewById(R.id.txt_password);

        username = MainActivity.getActivityInstance().getData();
        getData();
    }

    public void getData() {
        obj = new DBHelper(this);
        Cursor result = obj.getData(username);

        while (result.moveToNext()) {
            txt_name.setText(result.getString(0));
            txt_password.setText(result.getString(1));
            txt_email.setText(result.getString(2));
        }
    }

}