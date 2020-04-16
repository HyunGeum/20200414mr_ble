package com.nordicsemi.nrfUARTv2;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class login {
    private String TAG = "login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login2);

//text font
        TextView textView1 =(TextView)findViewById(R.id.title1);
       Button Bt_login =(Button) findViewById(R.id.bt_login);
        TextView Bt_demo =(TextView)findViewById(R.id.bt_demo);
        Typeface arialnbi = Typeface.createFromAsset(getAssets(),"ARIALNBI.TTF");
        Typeface h2hdrm= Typeface.createFromAsset(getAssets(),"H2HDRM.TTF");
        textView1.setTypeface(arialnbi);
        Bt_login.setTypeface(h2hdrm);
        Bt_demo.setTypeface(h2hdrm);


    }
    public void on_demo(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
