package com.example.fitness_club;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MembershipActivity extends AppCompatActivity {

    private static DBHelper dbHelper;
    private static EditText edtName , edtPhone , edtAddress , edtEmail;
    RadioGroup radioGroupGender , radioGroupFees;
    RadioButton radioMale , radioFemale , radioGender , radio12 , radio24 , radioFees;
    private static Button submit , display , edit;
    private static SQLiteDatabase sqLiteDatabase;
    private static int id = 0 , selectedIdGender , selectedIdFees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership);

        dbHelper = new DBHelper(MembershipActivity.this );
        findid();
        getData();
        clear();
        editData();
    }

    public void editData() {
        if(getIntent().getBundleExtra("userdata")!=null){
            Bundle bundle = getIntent().getBundleExtra("userdata");
            id = bundle.getInt("id");
            edtName.setText(bundle.getString("name"));
            edtAddress.setText(bundle.getString("address"));
            edtPhone.setText(bundle.getString("phone"));
            edtEmail.setText(bundle.getString("email"));
            radioGroupGender.check(selectedIdGender);
            radioGroupFees.check(selectedIdFees);
            edit.setVisibility(View.VISIBLE);
            submit.setVisibility(View.GONE);
        }
    }

    public void clear(){
        edtName.setText("");
        edtPhone.setText("");
        edtAddress.setText("");
        edtEmail.setText("");
        radioGroupGender.clearCheck();
        radioGroupFees.clearCheck();
    }

    public void getData(){

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ContentValues contentValues = new ContentValues();

                selectedIdGender = radioGroupGender.getCheckedRadioButtonId();
                radioGender = (RadioButton) findViewById(selectedIdGender);
                if(selectedIdGender==-1){
                    Toast.makeText(MembershipActivity.this,"Nothing selected", Toast.LENGTH_SHORT).show();
                }
                else{
                    //Toast.makeText(MainActivity.this,radioGender.getText(), Toast.LENGTH_SHORT).show();
                    contentValues.put("gender",radioGender.getText().toString());
                }

                selectedIdFees = radioGroupFees.getCheckedRadioButtonId();
                radioFees = (RadioButton) findViewById(selectedIdFees);
                if(selectedIdFees==-1){
                    Toast.makeText(MembershipActivity.this,"Nothing selected", Toast.LENGTH_SHORT).show();
                }
                else{
                    //Toast.makeText(MainActivity.this,radioGender.getText(), Toast.LENGTH_SHORT).show();
                    contentValues.put("fees",radioFees.getText().toString());
                }

                contentValues.put("name" , edtName.getText().toString() );
                contentValues.put("address" , edtAddress.getText().toString());
                contentValues.put("phone" , edtPhone.getText().toString());
                contentValues.put("email" , edtEmail.getText().toString());
                contentValues.put("status" , 0);

                sqLiteDatabase = dbHelper.getWritableDatabase();
                Long recid = sqLiteDatabase.insert("member" , null , contentValues );

                if(recid!=null){
                    Toast.makeText(MembershipActivity.this , "Data Inserted Successfully ... :) " , Toast.LENGTH_SHORT).show();
                    clear();
                } else{
                    Toast.makeText(MembershipActivity.this , "Something went Wrong ! Try again :(" , Toast.LENGTH_SHORT).show();
                }
            }
        });

        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MembershipActivity.this , DataActivity.class));
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ContentValues contentValues = new ContentValues();

                selectedIdGender = radioGroupGender.getCheckedRadioButtonId();
                radioGender = (RadioButton) findViewById(selectedIdGender);
                if(selectedIdGender==-1){
                    Toast.makeText(MembershipActivity.this,"Nothing selected", Toast.LENGTH_SHORT).show();
                }
                else{
                    //Toast.makeText(MainActivity.this,radioGender.getText(), Toast.LENGTH_SHORT).show();
                    contentValues.put("gender",radioGender.getText().toString());
                }

                selectedIdFees = radioGroupFees.getCheckedRadioButtonId();
                radioFees = (RadioButton) findViewById(selectedIdFees);
                if(selectedIdFees==-1){
                    Toast.makeText(MembershipActivity.this,"Nothing selected", Toast.LENGTH_SHORT).show();
                }
                else{
                    //Toast.makeText(MainActivity.this,radioGender.getText(), Toast.LENGTH_SHORT).show();
                    contentValues.put("fees",radioFees.getText().toString());
                }


                contentValues.put("name" , edtName.getText().toString() );
                contentValues.put("address" , edtAddress.getText().toString());
                contentValues.put("phone" , edtPhone.getText().toString());
                contentValues.put("email" , edtEmail.getText().toString());

                sqLiteDatabase = dbHelper.getWritableDatabase();

                long recid = sqLiteDatabase.update("member" , contentValues , "id = " + id , null);

                if(recid!=-1){
                    Toast.makeText(MembershipActivity.this , "Data Updated Successfully ... :) " , Toast.LENGTH_SHORT).show();
                    submit.setVisibility(View.VISIBLE);
                    edit.setVisibility(View.GONE);
                    clear();
                } else {
                    Toast.makeText(MembershipActivity.this , "Something went Wrong ! Try Again :(" , Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void findid(){
        edtName = findViewById(R.id.edtName);
        edtAddress = findViewById(R.id.edtAddress);
        edtPhone = findViewById(R.id.edtPhone);
        edtEmail = findViewById(R.id.edtEmail);
        radioMale = findViewById(R.id.radioMale);
        radioFemale = findViewById(R.id.radioFemale);
        radioGroupGender=(RadioGroup)findViewById(R.id.radioGroupGender);
        radio12 = findViewById(R.id.radio12);
        radio24 = findViewById(R.id.radio24);
        radioGroupFees=(RadioGroup)findViewById(R.id.radioGroupFees);
        submit = findViewById(R.id.btnSubmit);
        edit = findViewById(R.id.btnEdit);
        display = findViewById(R.id.btnDisplay);
    }
}