package com.nordicsemi.nrfUARTv2;



import android.app.Activity;

import android.content.ComponentName;
import android.content.Context;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;

import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.BaseAdapter;

import android.widget.Button;
import android.widget.GridView;

import android.widget.ImageView;

import android.widget.Switch;
import android.widget.TextView;

import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class igs4_main extends AppCompatActivity {


    Activity act = this;
    private String TAG = "IGS4_MAIN";

    ArrayList<Bitmap> picArr = new ArrayList<Bitmap>();

    ArrayList<String> textArr = new ArrayList<String>();
    private int text_on_color = Color.parseColor("#EE4500");
    private int text_off_color = Color.parseColor("#000000");
    GridView gridView;

int sw_st = 0;
int flag = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_igs4_main);



        //꺼져있는 기본 전구 이미지
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.lig_off);



int switch_ea = 16; //스위치 갯수 -1 (0~15)


        for (int i = 0; i < switch_ea;i++) {


            picArr.add(bm); //picarr 비트맵 배열에 bm 변수 추가

            textArr.add("버튼" + Integer.toString(i));  //textarr배멸에 text를 함께 추가

        }


        gridView = (GridView) findViewById(R.id.gridView1);

        gridView.setAdapter(new gridAdapter());


       // final TextView textView = (TextView) findViewById(R.id.textView1);
       // textView.setTextColor(text_off_color);




    }//온크레딧



        public class gridAdapter extends BaseAdapter {

            LayoutInflater inflater;

            public gridAdapter() {

                inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            }



            @Override

            public int getCount() {

// TODO Auto-generated method stub

                return picArr.size();    //그리드뷰에 출력할 목록 수

            }



            @Override

            public Object getItem(int position) {

// TODO Auto-generated method stub

                return picArr.get(position);    //아이템을 호출할 때 사용하는 메소드

            }



            @Override

            public long getItemId(int position) {

// TODO Auto-generated method stub

                return position;    //아이템의 아이디를 구할 때 사용하는 메소드

            }



            @Override

            public View getView(final int position, View convertView, ViewGroup parent) {

// TODO Auto-generated method stub

                if(convertView == null) {

                    convertView = inflater.inflate(R.layout.grid_item, parent, false);

                }


                final ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView1);

                final TextView textView = (TextView) convertView.findViewById(R.id.textView1);


                imageView.setImageBitmap(picArr.get(position));


                textView.setText(textArr.get(position));


                switch(position){
                    case 0:

                        Log.e("position-컨버터", "" + position);


                        break;
                    case 1:     Log.e("position-컨버터", "" + position);break;
                    case 2:     Log.e("position-컨버터", "" + position);break;
                    case 3: break;
                    case 4: break;
                    case 5: break;



                }



                imageView.setOnLongClickListener(new View.OnLongClickListener() {

                    public boolean onLongClick(View v) {

                        switch (position) {
                            case 0:
                                flag =0;
                                Log.e(TAG,"롱클릭0");
                                break;
                            case 1:
                                flag = 1;
                                Log.e(TAG,"롱클릭1");


                                break;
                            case 2:
                                flag = position;
                                break;
                            case 3:
                                flag = position;
                                break;
                            case 4:
                                flag = position;
                                break;
                            case 5:
                                flag = position;
                                break;
                            case 6:
                                flag = position;
                                break;
                            case 7:
                                flag = position;
                                break;
                            case 8:
                                flag = position;
                                break;
                            case 9:
                                flag = position;
                                break;
                            case 10:
                                flag = position;
                                break;
                            case 11:
                                flag = position;
                                break;
                            case 12:
                                flag = position;
                                break;
                            case 13:
                                flag = position;
                                break;
                            case 14:
                                flag = position;
                                break;
                            case 15:
                                flag = position;
                                break;

                        }
                        return true;
                    }
                });
//textArr.set(1,"11"); //부분 텍스트 변경 FLAG를 변경점으로 삼을것

           //   textView.setTextColor(text_off_color);

            //    textView.setTextColor(text_off_color);

//숏클릭
                imageView.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {

                        switch (position) {
                            case 0:

                                flag = 0;

                                    textView.setTextColor(text_on_color);


                                Log.e("button0",  " position:"+ position + "flag : " + flag);

                                break;
                            case 1:


                                flag = 1;


                                textView.setText("퉤퉤"); //글씨 변경
                                Log.e("button1",  " position:"+ position + "flag : " + flag);


                                if (sw_st == 0) {


                                    textView.setText("꺼짐"); //글씨 변경
                                    imageView.setImageResource(R.drawable.lig_off); //꺼짐 이미지
                               //     Log.e(TAG, "1번 포지션 2번 버튼");
                                    sw_st = 1; //상태 수신가능 구현될때 이부분 삭제


                                } else if (sw_st == 1) {
                                    textView.setText("켜짐"); //글씨 변경
                                    imageView.setImageResource(R.drawable.lig_on); //켜짐 이미지
                                    sw_st = 0; //상태 수신가능 구현될때 이부분 삭제
                                }

                                break;
                            case 2:
                                flag = 2;
                                if (position == 2){
                                    textView.setTextColor(text_on_color);
                                }else{
                                    textView.setTextColor(text_off_color);
                                }

                                break;
                            case 3:flag = 3;

                                break;
                            case 4:flag = 4;

                                break;
                            case 5:flag = 5;
                                break;
                            case 6:    flag = position;
                                break;
                            case 7:    flag = position;
                                break;
                            case 8:    flag = position;
                                break;
                            case 9:    flag = position;
                                break;
                            case 10:    flag = position;
                                break;
                            case 11:    flag = position;
                                break;
                            case 12:    flag = position;
                                break;
                            case 13:    flag = position;
                                break;
                            case 14:    flag = position;
                                break;
                            case 15:    flag = position;
                                break;

                        }

                    }


                });


      //          this.notifyDataSetChanged();



                return convertView;

            }//겟뷰









        }//grid아답터













}//main







































