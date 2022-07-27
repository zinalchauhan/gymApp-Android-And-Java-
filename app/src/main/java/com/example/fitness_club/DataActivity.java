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

public class DataActivity extends AppCompatActivity {

    DBHelper dbHelper;
    SQLiteDatabase sqLiteDatabase;
    ListView listView;
    String []name ;
    String []phone;
    String []address;
    String []email;
    String []gender;
    String []fees;
    String []payment;
    int []id;
    String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        dbHelper = new DBHelper(DataActivity.this);
        findid();
        dis();

    }

    private void dis(){

        sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from member" , null);

        if(cursor.getCount()>0){

            id = new int[cursor.getCount()];
            name = new String[cursor.getCount()];
            address = new String[cursor.getCount()];
            phone = new String[cursor.getCount()];
            gender = new String[cursor.getCount()];
            email = new String[cursor.getCount()];
            fees = new String[cursor.getCount()];
            payment = new String[cursor.getCount()];

            int i = 0;
            while (cursor.moveToNext()){
                id[i] = cursor.getInt(0);
                name[i] = cursor.getString(1);
                address[i] = cursor.getString(2);
                phone[i] = cursor.getString(3);
                email[i] = cursor.getString(4);
                gender[i] = cursor.getString(5);
                fees[i] = cursor.getString(6);
                payment[i] = cursor.getString(7);
                i++;
            }

            Custom adapter = new Custom();
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

            TextView txt_Name , txt_Gender , txt_Phone , txt_Address , txt_Email , txt_Fees , txt_Payment;
            ImageView edit , delete;

            convertView = LayoutInflater.from(DataActivity.this).inflate(R.layout.singledata , parent , false);
            txt_Name = convertView.findViewById(R.id.txt_Name);
            txt_Address = convertView.findViewById(R.id.txt_Address);
            txt_Gender = convertView.findViewById(R.id.txt_Gender);
            txt_Phone = convertView.findViewById(R.id.txt_Phone);
            txt_Email = convertView.findViewById(R.id.txt_Email);
            txt_Fees = convertView.findViewById(R.id.txt_Fees);
            txt_Payment = convertView.findViewById(R.id.txt_Payment);
            edit = convertView.findViewById(R.id.editdata);
            delete = convertView.findViewById(R.id.deletedata);

            txt_Name.setText(name[position]);
            txt_Address.setText(address[position]);
            txt_Gender.setText(gender[position]);
            txt_Phone.setText(phone[position]);
            txt_Email.setText(email[position]);
            txt_Fees.setText(fees[position]);

            if(payment[position].equals("1")) {
                txt_Payment.setText("Completed");
                txt_Payment.setTextColor(Color.parseColor("#32CD32"));
            } else {
                txt_Payment.setText("Pending");
                txt_Payment.setTextColor(Color.parseColor("#FF0000"));
            }

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Bundle bundle = new Bundle();
                    bundle.putInt("id" , id[position]);
                    bundle.putString("name" , name[position]);
                    bundle.putString("address",address[position]);
                    bundle.putString("gender",gender[position]);
                    bundle.putString("phone",phone[position]);
                    bundle.putString("email",email[position]);
                    bundle.putString("fees",fees[position]);

                    Intent intent = new Intent(DataActivity.this , MembershipActivity.class);
                    intent.putExtra("userdata" , bundle);
                    startActivity(intent);
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    sqLiteDatabase = dbHelper.getReadableDatabase();
                    long recd = sqLiteDatabase.delete("member" , "id = "+id[position] , null);

                    if(recd!=-1){
                        Toast.makeText(DataActivity.this , "Record deleted successfully ... :)" , Toast.LENGTH_SHORT).show();
                        dis();
                    }
                }
            });

            return convertView;
        }
    }
}