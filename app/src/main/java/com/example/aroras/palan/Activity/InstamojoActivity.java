package com.example.aroras.palan.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import instamojo.library.InstapayListener;
import instamojo.library.InstamojoPay;
import instamojo.library.Config;
import android.app.Activity;
import org.json.JSONObject;
import org.json.JSONException;
import android.content.IntentFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.os.Bundle;

import com.example.aroras.palan.R;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class InstamojoActivity extends AppCompatActivity {

    Number num;
    double ngo_donation;

    Button btnPay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instamojo);

        btnPay=findViewById(R.id.buttonPay);

        Bundle extras;
        String newString;

        if (savedInstanceState == null) {
            //fetching extra data passed with intents ina bundle type variable
            extras = getIntent().getExtras();
            if (extras == null) {
                newString = "";
                Toast.makeText(this, newString, Toast.LENGTH_SHORT).show();
            } else {
                //fetching the string passed using extras
                newString = extras.getString("TotalPrice");

                //Donation Calculation
                String currentString;
                currentString=newString.substring(1);
                try {
                   num= NumberFormat.getNumberInstance(Locale.US).parse(currentString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                ngo_donation=0.05 * Double.parseDouble(num.toString());
                Toast.makeText(this, ("Total Price:"+newString+"   "+"Donation :$"+ ngo_donation), Toast.LENGTH_SHORT).show();


            }
        }
        try{
        btnPay.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                doPayment(v);
            }
        });
        }
        catch (Exception e)
        {
            Toast.makeText(this,"You are not connected to the  server",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void doPayment(View v){
        String email="palakarora1401@gmail.com";
        String phone="9785375117";
        String amount="11";
        String purpose="shopping";
        String buyername="palak arora";

        callInstamojoPay(email,phone,amount,purpose,buyername);
    }

    private void callInstamojoPay(String email, String phone, String amount, String purpose, String buyername) {
        final Activity activity = this;
        InstamojoPay instamojoPay = new InstamojoPay();
        IntentFilter filter = new IntentFilter("ai.devsupport.instamojo");
        registerReceiver(instamojoPay, filter);
        JSONObject pay = new JSONObject();
        try {
            pay.put("email", email);
            pay.put("phone", phone);
            pay.put("purpose", purpose);
            pay.put("amount", amount);
            pay.put("name", buyername);
            pay.put("send_sms", true);
            pay.put("send_email", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        initListener();
        instamojoPay.start(activity, pay, listener);

    }

    InstapayListener listener;


    private void initListener() {
        listener = new InstapayListener() {
            @Override
            public void onSuccess(String response) {
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG)
                        .show();
                Toast.makeText(getApplicationContext(),"Thank you,Order placed",Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(int code, String reason) {
                Toast.makeText(getApplicationContext(), "Failed: " + reason, Toast.LENGTH_LONG)
                        .show();
            }
        };
    }

}
