package com.example.fitness_club;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.PublicKey;

public class MainActivity extends AppCompatActivity {

    static MainActivity INSTANCE;

    EditText userName , password;
    Button btnLogin , btnRegister;
    DBHelper DB;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        INSTANCE = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.btnLogin);
        userName = findViewById(R.id.userName);
        password = findViewById(R.id.password);
        btnRegister = findViewById(R.id.btnRegister);
        DB = new DBHelper(this);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                user = userName.getText().toString();
                String pass = password.getText().toString();

                if(user.equals("")||pass.equals(""))
                    Toast.makeText(MainActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else if(user.equals("admin") && pass.equals("admin")){
                    Toast.makeText(MainActivity.this, "Welcome Admin", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                } else {
                    Boolean checkuserpass = DB.checkusernamepassword(user, pass);
                    if (checkuserpass == true) {
                        Toast.makeText(MainActivity.this, "Welcome User", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), UserHomeActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this , RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    public static MainActivity getActivityInstance() {
        return INSTANCE;
    }

    public String getData(){
        return this.user;
    }
}