package com.nordicsemi.nrfUARTv2;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;




public class SingerViewer extends LinearLayout {

    TextView textView, geum1, geum2;
    TextView textView2;
    LinearLayout imageView;
    public SingerViewer(Context context) {
        super(context);

        init(context);
    }

    public SingerViewer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.singeritem,this,true);

        //textView = (TextView)findViewById(R.id.textView);
      //  textView2 = (TextView)findViewById(R.id.textView2);

       geum1 = (TextView)findViewById(R.id.geum1);
        geum2 = (TextView)findViewById(R.id.geum2);

        imageView = (LinearLayout) findViewById(R.id.imageView);
    }

    public void setItem(SingerItem singerItem){
     //   textView.setText(singerItem.getName());
        geum1.setText(singerItem.getName());
        geum2.setText(singerItem.getTel());
       // textView2.setText(singerItem.getTel());
       // imageView.setImageResource(singerItem.getImage());
        imageView.setBackgroundResource(singerItem.getImage());
    }
}