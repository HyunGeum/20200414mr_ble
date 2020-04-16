package com.nordicsemi.nrfUARTv2;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class rlcm {
    private String TAG = "rlcm";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);




        setContentView(R.layout.activity_rlcm);
//text font
        TextView textView1 =(TextView)findViewById(R.id.title1);
        TextView textView2 =(TextView)findViewById(R.id.title2);
        TextView textView3 =(TextView)findViewById(R.id.title3);
        TextView textView4 =(TextView)findViewById(R.id.title4);
        TextView textView5 =(TextView)findViewById(R.id.title5);
        TextView textView6 =(TextView)findViewById(R.id.title6);
        TextView textView7 =(TextView)findViewById(R.id.title7);
        TextView textView8 =(TextView)findViewById(R.id.title8);
        TextView copy =(TextView)findViewById(R.id.text_copy5);
        Typeface arialnbi = Typeface.createFromAsset(getAssets(),"ARIALNBI.TTF");
        Typeface arial = Typeface.createFromAsset(getAssets(),"ARIAL.TTF");
        Typeface h2hdrm= Typeface.createFromAsset(getAssets(),"H2HDRM.TTF");
        textView1.setTypeface(arialnbi);
        textView2.setTypeface(arialnbi);
        textView3.setTypeface(arialnbi);
        textView4.setTypeface(arialnbi);
        textView5.setTypeface(arial);
        textView6.setTypeface(arial);
        textView7.setTypeface(arial);
        textView8.setTypeface(h2hdrm);
        copy.setTypeface(arial);

    }
    public void m_st(View view){
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
        finish();
    }
}
