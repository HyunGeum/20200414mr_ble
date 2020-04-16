package com.nordicsemi.nrfUARTv2;

import java.util.List;


import android.app.Activity;

import android.content.ComponentName;
import android.content.Context;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import android.view.View;

import android.view.ViewGroup;

import android.widget.BaseAdapter;

import android.widget.Button;
import android.widget.GridView;

import android.widget.ImageView;

import android.widget.TextView;






public class igs8_main extends AppCompatActivity {


    //셀렉트 되었을때 글씨 색

    Context context;
    private String TAG = "IGS8_MAIN";

    Activity act = this;
    GridView gridView;
    private List<ResolveInfo> apps;
    private PackageManager pm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_igs8_main);

        Intent mainIntent = new Intent(Intent. ACTION_MAIN,null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        pm = getPackageManager();
        apps = pm.queryIntentActivities(mainIntent,0);
        setContentView(R.layout.activity_igs8_main);

        gridView = (GridView)findViewById(R.id.gridv);
        gridView.setAdapter(new gridAdapter());



    }


    public class gridAdapter extends BaseAdapter{
        LayoutInflater inflater;

        public gridAdapter(){
         inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }
        public final int getCount(){
            return apps.size();
        }

        @Override
        public final Object getItem(int position) {
            return apps.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView ==null){
                convertView = inflater.inflate(R.layout.grid_item,parent,false);

            }

            final ResolveInfo info = apps.get(position);

            ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView1);
            TextView textView = (TextView) convertView.findViewById(R.id.textView1);
            imageView.setImageDrawable(info.activityInfo.loadIcon(getPackageManager()));
            textView.setText(info.activityInfo.loadLabel(pm).toString());



            imageView.setOnClickListener(new Button.OnClickListener(){
                public void onClick(View v){

                    Intent intent = new Intent(Intent.ACTION_RUN);
                    intent.setComponent(new ComponentName(info.activityInfo.packageName,info.activityInfo.name));
                    act.startActivity(intent);

                }
            });

            return convertView;
        }


    }

}






























