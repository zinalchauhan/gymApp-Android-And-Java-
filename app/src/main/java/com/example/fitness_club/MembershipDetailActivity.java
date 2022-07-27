package com.example.fitness_club;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class MembershipDetailActivity extends AppCompatActivity implements PaymentResultListener {

    DBHelper obj = null;
    TextView txt_Name , txt_Email , txt_Address , txt_Phone , txt_Gender , txt_Fees , txt_Payment;
    Button btnPay;
    String name , email , phone , fees;
    int status , payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership_detail);

        txt_Name = findViewById(R.id.txt_Name);
        txt_Email = findViewById(R.id.txt_Email);
        txt_Phone = findViewById(R.id.txt_Phone);
        txt_Address = findViewById(R.id.txt_Address);
        txt_Gender = findViewById(R.id.txt_Gender);
        txt_Fees = findViewById(R.id.txt_Fees);
        txt_Payment = findViewById(R.id.txt_Payment);
        btnPay = findViewById(R.id.btnPay);

        name = MainActivity.getActivityInstance().getData();
        getMember();

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPayment();
            }
        });

    }

    public void getMember() {
        obj = new DBHelper(this);
        Cursor result = obj.getMember(name);

        while (result.moveToNext()) {

            txt_Name.setText(result.getString(1));
            txt_Address.setText(result.getString(2));
            txt_Phone.setText(result.getString(3));
            txt_Email.setText(result.getString(4));
            txt_Gender.setText(result.getString(5));
            txt_Fees.setText(result.getString(6));
            status = result.getInt(7);
            phone = result.getString(3);
            email = result.getString(4);
            fees = result.getString(6);
        }

        if(status == 0){
            txt_Payment.setText("Pending");
            txt_Payment.setTextColor(Color.parseColor("#FF0000"));
            btnPay.setVisibility(View.VISIBLE);

        } else {
            txt_Payment.setText("Completed");
            txt_Payment.setTextColor(Color.parseColor("#32CD32"));
            btnPay.setVisibility(View.GONE);
        }

        if(fees.equals("  12 Months Rs. 15,000")) {
            payment = 15000;
        } else {
            payment = 25000;
        }

    }

    public void startPayment() {

        Checkout checkout = new Checkout();

        checkout.setImage(R.mipmap.ic_launcher);

        final Activity activity = this;

        try {
            JSONObject options = new JSONObject();
            options.put("name", R.string.app_name);
            options.put("description", "Payment for Anything");
            options.put("send_sms_hash", true);
            options.put("allow_rotation", false);

            //You can omit the image option to fetch the image from dashboard
            options.put("currency", "INR");

            options.put("amount", payment*100);

            JSONObject preFill = new JSONObject();
            preFill.put("Email", email);
            preFill.put("Contact", phone);

            options.put("prefill", preFill);

            checkout.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Payment Successfull :) " , Toast.LENGTH_SHORT).show();
        obj = new DBHelper(this);
        obj.updateStatus(name);
        btnPay.setVisibility(View.GONE);
        txt_Payment.setText("Completed");
        txt_Payment.setTextColor(Color.parseColor("#32CD32"));
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Failure :( ", Toast.LENGTH_SHORT).show();
    }
}