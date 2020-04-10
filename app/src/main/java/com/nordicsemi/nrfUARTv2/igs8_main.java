package com.nordicsemi.nrfUARTv2;



import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;


public class igs8_main extends AppCompatActivity {
    //셀렉트 되었을때 글씨 색
    private int text_sel_color = Color.parseColor("#EE4500");
    private int text_nosel_color = Color.parseColor("#000000");
    Context context;
    int st1,st2,st3,st4,st5,st6,st7,st8,st9,st10,st11,st12,st13,st14,st15,st16,st17,st18;
    int sw1, sw2, sw3,sw4, sw5, sw6, sw7, sw8, sw9, sw10, sw11, sw12, sw13, sw14,sw15, sw16, sw17, sw18;
    int flag, ty_flag;
    Button Bt1, Bt2,  Bt3,  Bt4,  Bt5,  Bt6,  Bt7, Bt8, Bt9 , Bt10, Bt11,  Bt12 , Bt13,  Bt14, Bt15, Bt16,  Bt17,  Bt18, Ty_i, Ty_g, Ty_s, Ty_m ;
    EditText Sw_name;
    SeekBar seekbar1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_igs8_main);

        seekbar1 = findViewById(R.id.seekBar1);

//edittext 글씨 삽입
        Sw_name = findViewById(R.id.sw_name);
        Sw_name.setText("IGS SWITCH");


        Bt1 = findViewById(R.id.bt1);        Bt2 = findViewById(R.id.bt2);        Bt3 = findViewById(R.id.bt3);
        Bt4 = findViewById(R.id.bt4);        Bt5 = findViewById(R.id.bt5);        Bt6 = findViewById(R.id.bt6);
        Bt7 = findViewById(R.id.bt7);        Bt8 = findViewById(R.id.bt8);        Bt9 = findViewById(R.id.bt9);
        Bt10 = findViewById(R.id.bt10);        Bt11 = findViewById(R.id.bt11);        Bt12 = findViewById(R.id.bt12);
        Bt13 = findViewById(R.id.bt13);        Bt14 = findViewById(R.id.bt14);        Bt15 = findViewById(R.id.bt15);
        Bt16 = findViewById(R.id.bt16);        Bt17 = findViewById(R.id.bt17);        Bt18 = findViewById(R.id.bt18);
        Ty_i = findViewById(R.id.ty_i);    Ty_g = findViewById(R.id.ty_g);    Ty_s = findViewById(R.id.ty_s);    Ty_m = findViewById(R.id.ty_m);


//롱클릭
        Bt1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                sw_sel(sw1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 1);
                flag = 1; if (sw1==0){sw1 = 1; }else{sw1 = 0; }  return true;}
        }); //sw값 반대로 주는 부분은 상태값을 정상으로 받아올떄는 삭제
        Bt2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                sw_sel(0,sw2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 2);
                flag = 2; if (sw2==0){sw2 = 1; }else{sw2 = 0; }  return true;}
        });
        Bt3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                sw_sel(0,0,sw3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 3);
                flag = 3; if (sw3==0){sw3 = 1; }else{sw3 = 0; }  return true;}
        });
        Bt4.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                sw_sel(0,0,0,sw4,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 4);
                flag = 4; if (sw4==0){sw4 = 1; }else{sw4 = 0; }  return true;}});
        Bt5.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                sw_sel(0,0,0,0,sw5,0,0,0,0,0,0,0,0,0,0,0,0,0, 5);
                flag = 5; if (sw5==0){sw5 = 1; }else{sw5 = 0; }  return true;}});
        Bt6.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                sw_sel(0,0,0,0,0,sw6,0,0,0,0,0,0,0,0,0,0,0,0, 6);
                flag = 6; if (sw6==0){sw6 = 1; }else{sw6 = 0; }  return true;}
        });
        Bt7.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                sw_sel(0,0,0,0,0,0,sw7,0,0,0,0,0,0,0,0,0,0,0, 7);
                flag = 7; if (sw7==0){sw7 = 1; }else{sw7 = 0; }  return true;}
        });
        Bt8.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                sw_sel(0,0,0,0,0,0,0,sw8,0,0,0,0,0,0,0,0,0,0, 8);
                flag = 8; if (sw8==0){sw8 = 1; }else{sw8 = 0; }  return true;}
        });
        Bt9.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                sw_sel(0,0,0,0,0,0,0,0,sw9,0,0,0,0,0,0,0,0,0, 9);
                flag = 9; if (sw9==0){sw9 = 1; }else{sw9 = 0; }  return true;}
        });
        Bt10.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                sw_sel(0,0,0,0,0,0,0,0,0,sw10,0,0,0,0,0,0,0,0, 10);
                flag = 10; if (sw10==0){sw10 = 1; }else{sw10 = 0; }  return true;}
        });
        Bt11.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                sw_sel(0,0,0,0,0,0,0,0,0,0,sw11,0,0,0,0,0,0,0, 11);
                flag = 11; if (sw11==0){sw11 = 1; }else{sw11 = 0; }  return true;}
        });
        Bt12.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                sw_sel(0,0,0,0,0,0,0,0,0,0,0,sw12,0,0,0,0,0,0, 12);
                flag = 12; if (sw12==0){sw12 = 1; }else{sw12 = 0; }  return true;}
        });
        Bt13.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                sw_sel(0,0,0,0,0,0,0,0,0,0,0,0,sw13,0,0,0,0,0, 13);
                flag = 13; if (sw13==0){sw13 = 1; }else{sw13 = 0; }  return true;}
        });
        Bt14.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                sw_sel(0,0,0,0,0,0,0,0,0,0,0,0,0,sw14,0,0,0,0, 14);
                flag = 14; if (sw14==0){sw14 = 1; }else{sw14 = 0; }  return true;}
        });
        Bt15.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                sw_sel(0,0,0,0,0,0,0,0,0,0,0,0,0,0,sw15,0,0,0, 15);
                flag = 15; if (sw15==0){sw15 = 1; }else{sw15 = 0; }  return true;}
        });
        Bt16.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                sw_sel(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,sw16,0,0, 16);
                flag = 16; if (sw16==0){sw16 = 1; }else{sw16 = 0; }  return true;}
        });
        Bt17.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                sw_sel(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,sw17,0, 17);
                flag = 17; if (sw17==0){sw17 = 1; }else{sw17 = 0; }  return true;}
        });
        Bt18.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                sw_sel(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,sw18, 18);
                flag = 18; if (sw18==0){sw18 = 1; }else{sw18 = 0; }  return true;}
        });



//숏클릭
        Bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 1;
                sw_sel(sw1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 1);
                if (sw1==0){Bt1.setBackground(getDrawable(R.drawable.lig_off));sw1 = 1; }else{Bt1.setBackground(getDrawable(R.drawable.lig_on));sw1 = 0;}    }
        });

        Bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 2;
                sw_sel(0,sw2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 2);
                if (sw2==0){Bt2.setBackground(getDrawable(R.drawable.lig_off));sw2 = 1; }else{Bt2.setBackground(getDrawable(R.drawable.lig_on));sw2 = 0;}}
        });

        Bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 3;
                sw_sel(0,0,sw3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 3);
                if (sw3==0){Bt3.setBackground(getDrawable(R.drawable.lig_off));sw3 = 1; }else{Bt3.setBackground(getDrawable(R.drawable.lig_on));sw3 = 0;}}
        });
        Bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 4;
                sw_sel(0,0,0,sw4,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 4);
                if (sw4==0){Bt4.setBackground(getDrawable(R.drawable.lig_off));sw4 = 1; }else{Bt4.setBackground(getDrawable(R.drawable.lig_on));sw4 = 0;}}
        });

        Bt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 5;
                sw_sel(0,0,0,0,sw5,0,0,0,0,0,0,0,0,0,0,0,0,0, 5);
                if (sw5==0){Bt5.setBackground(getDrawable(R.drawable.lig_off));sw5 = 1; }else{Bt5.setBackground(getDrawable(R.drawable.lig_on));sw5 = 0;}}
        });
        Bt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 6;
                sw_sel(0,0,0,0,0,sw6,0,0,0,0,0,0,0,0,0,0,0,0, 6);
                if (sw6==0){Bt6.setBackground(getDrawable(R.drawable.lig_off));sw6 = 1; }else{Bt6.setBackground(getDrawable(R.drawable.lig_on));sw6 = 0;}}
        });
        Bt7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 7;
                sw_sel(0,0,0,0,0,0,sw7,0,0,0,0,0,0,0,0,0,0,0, 7);
                if (sw7==0){Bt7.setBackground(getDrawable(R.drawable.lig_off));sw7 = 1; }else{Bt7.setBackground(getDrawable(R.drawable.lig_on));sw7 = 0;}}
        });
        Bt8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 8;
                sw_sel(0,0,0,0,0,0,0,sw8,0,0,0,0,0,0,0,0,0,0, 8);
                if (sw8==0){Bt8.setBackground(getDrawable(R.drawable.lig_off));sw8 = 1; }else{Bt8.setBackground(getDrawable(R.drawable.lig_on));sw8 = 0;}}
        });
        Bt9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 9;
                sw_sel(0,0,0,0,0,0,0,0,sw9,0,0,0,0,0,0,0,0,0, 9);
                if (sw9==0){Bt9.setBackground(getDrawable(R.drawable.lig_off));sw9 = 1; }else{Bt9.setBackground(getDrawable(R.drawable.lig_on));sw9 = 0;}}
        });
        Bt10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 10;
                sw_sel(0,0,0,0,0,0,0,0,0,sw10,0,0,0,0,0,0,0,0, 10);
                if (sw10==0){Bt10.setBackground(getDrawable(R.drawable.lig_off));sw10 = 1; }else{Bt10.setBackground(getDrawable(R.drawable.lig_on));sw10 = 0;}}
        });
        Bt11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 11;
                sw_sel(0,0,0,0,0,0,0,0,0,0,sw11,0,0,0,0,0,0,0, 11);
                if (sw11==0){Bt11.setBackground(getDrawable(R.drawable.lig_off));sw11 = 1; }else{Bt11.setBackground(getDrawable(R.drawable.lig_on));sw11 = 0;}}
        });
        Bt12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 12;
                sw_sel(0,0,0,0,0,0,0,0,0,0,0,sw12,0,0,0,0,0,0, 12);
                if (sw12==0){Bt12.setBackground(getDrawable(R.drawable.lig_off));sw12 = 1; }else{Bt12.setBackground(getDrawable(R.drawable.lig_on));sw12 = 0;}}
        });
        Bt13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 13;
                sw_sel(0,0,0,0,0,0,0,0,0,0,0,0,sw13,0,0,0,0,0, 13);
                if (sw13==0){Bt13.setBackground(getDrawable(R.drawable.lig_off));sw13 = 1; }else{Bt13.setBackground(getDrawable(R.drawable.lig_on));sw13 = 0;}}
        });
        Bt14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 14;
                sw_sel(0,0,0,0,0,0,0,0,0,0,0,0,0,sw14,0,0,0,0, 14);
                if (sw14==0){Bt14.setBackground(getDrawable(R.drawable.lig_off));sw14 = 1; }else{Bt14.setBackground(getDrawable(R.drawable.lig_on));sw14 = 0;}}
        });
        Bt15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 15;
                sw_sel(0,0,0,0,0,0,0,0,0,0,0,0,0,0,sw15,0,0,0, 15);
                if (sw15==0){Bt15.setBackground(getDrawable(R.drawable.lig_off));sw15 = 1; }else{Bt3.setBackground(getDrawable(R.drawable.lig_on));sw15 = 0;}}
        });
        Bt16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 16;
                sw_sel(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,sw16,0,0, 16);
                if (sw16==0){Bt16.setBackground(getDrawable(R.drawable.lig_off));sw16 = 1; }else{Bt16.setBackground(getDrawable(R.drawable.lig_on));sw16 = 0;}}
        });
        Bt17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 17;
                sw_sel(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,sw17,0, 17);
                if (sw17==0){Bt17.setBackground(getDrawable(R.drawable.lig_off));sw17 = 1; }else{Bt17.setBackground(getDrawable(R.drawable.lig_on));sw17 = 0;}}
        });
        Bt18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 18;
                sw_sel(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,sw18, 18);
                if (sw18==0){Bt18.setBackground(getDrawable(R.drawable.lig_off));sw18 = 1; }else{Bt18.setBackground(getDrawable(R.drawable.lig_on));sw18 = 0;}}
        });

        //igsm 버튼색 바꾸기
        Ty_i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Ty_i.setBackground(getDrawable(R.drawable.i_on_01));
                Ty_g.setBackground(getDrawable(R.drawable.g_off_01));
                Ty_s.setBackground(getDrawable(R.drawable.s_off_01));
                Ty_m.setBackground(getDrawable(R.drawable.m_off_01));


            }
        });

        Ty_g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ty_i.setBackground(getDrawable(R.drawable.i_off_01));
                Ty_g.setBackground(getDrawable(R.drawable.g_on_01));
                Ty_s.setBackground(getDrawable(R.drawable.s_off_01));
                Ty_m.setBackground(getDrawable(R.drawable.m_off_01));



            }
        });
        Ty_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Ty_i.setBackground(getDrawable(R.drawable.i_off_01));
                Ty_g.setBackground(getDrawable(R.drawable.g_off_01));
                Ty_s.setBackground(getDrawable(R.drawable.s_on_01));
                Ty_m.setBackground(getDrawable(R.drawable.m_off_01));



            }
        });

        Ty_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ty_i.setBackground(getDrawable(R.drawable.i_off_01));
                Ty_g.setBackground(getDrawable(R.drawable.g_off_01));
                Ty_s.setBackground(getDrawable(R.drawable.s_off_01));
                Ty_m.setBackground(getDrawable(R.drawable.m_on_01));




            }
        });

        seekbar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


                if(flag == 1 && sw1 == 0){Bt1.setText(flag+"버튼 \n"+progress);}
                else if(flag == 2 && sw2 == 0){Bt2.setText(flag+"버튼 \n"+progress); }
                else if(flag == 3 && sw3 == 0){Bt3.setText(flag+"버튼 \n"+progress); }
                else if(flag == 4 && sw4 == 0){Bt4.setText(flag+"버튼 \n"+progress); }
                else if(flag == 5 && sw5 == 0){Bt5.setText(flag+"버튼 \n"+progress); }
                else if(flag == 6 && sw6 == 0){Bt6.setText(flag+"버튼 \n"+progress); }
                else if(flag == 7 && sw7 == 0){Bt7.setText(flag+"버튼 \n"+progress); }
                else if(flag == 8 && sw8 == 0){Bt8.setText(flag+"버튼 \n"+progress); }
                else if(flag == 9 && sw9 == 0){Bt9.setText(flag+"버튼 \n"+progress); }
                else if(flag == 10 && sw10 == 0){Bt10.setText(flag+"버튼 \n"+progress); }
                else if(flag == 11 && sw11 == 0){Bt11.setText(flag+"버튼 \n"+progress); }
                else if(flag == 12 && sw12 == 0){Bt12.setText(flag+"버튼 \n"+progress); }
                else if(flag == 13 && sw13 == 0){Bt13.setText(flag+"버튼 \n"+progress); }
                else if(flag == 14 && sw14 == 0){Bt14.setText(flag+"버튼 \n"+progress); }
                else if(flag == 15 && sw15 == 0){Bt15.setText(flag+"버튼 \n"+progress); }
                else if(flag == 16 && sw16 == 0){Bt16.setText(flag+"버튼 \n"+progress); }
                else if(flag == 17 && sw17 == 0){Bt17.setText(flag+"버튼 \n"+progress); }
                else if(flag == 18 && sw18 == 0){Bt18.setText(flag+"버튼 \n"+progress); }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });






    }



    private  void sw_sel(int sw1, int sw2, int sw3, int sw4, int sw5,int sw6,int sw7,int sw8,int sw9,int sw10,int sw11,int sw12,int sw13,int sw14,int sw15,int sw16,int sw17,int sw18,int flag){


//시크바 숨김
        if( flag == 1 && sw1 == 0){seekbar1.setVisibility(INVISIBLE);}else if(flag == 1 && sw1 == 1){seekbar1.setVisibility(VISIBLE);}
        if( flag == 2 && sw2 == 0){seekbar1.setVisibility(INVISIBLE);}else if(flag == 2 && sw2 == 1){seekbar1.setVisibility(VISIBLE);}
        if( flag == 3 && sw3 == 0){seekbar1.setVisibility(INVISIBLE);}else if(flag == 3 && sw3 == 1){seekbar1.setVisibility(VISIBLE);}
        if( flag == 4 && sw4 == 0){seekbar1.setVisibility(INVISIBLE);}else if(flag == 4 && sw4 == 1){seekbar1.setVisibility(VISIBLE);}
        if( flag == 5 && sw5 == 0){seekbar1.setVisibility(INVISIBLE);}else if(flag == 5 && sw5 == 1){seekbar1.setVisibility(VISIBLE);}
        if( flag == 6 && sw6 == 0){seekbar1.setVisibility(INVISIBLE);}else if(flag == 6 && sw6 == 1){seekbar1.setVisibility(VISIBLE);}
        if( flag == 7 && sw7 == 0){seekbar1.setVisibility(INVISIBLE);}else if(flag == 7 && sw7 == 1){seekbar1.setVisibility(VISIBLE);}
        if( flag == 8 && sw8 == 0){seekbar1.setVisibility(INVISIBLE);}else if(flag == 8 && sw8 == 1){seekbar1.setVisibility(VISIBLE);}
        if( flag == 9 && sw9== 0){ seekbar1.setVisibility(INVISIBLE);}else if(flag == 9 && sw9 == 1){seekbar1.setVisibility(VISIBLE);}
        if( flag == 10 && sw10 == 0){seekbar1.setVisibility(INVISIBLE);}else if(flag == 10 && sw10 == 1){seekbar1.setVisibility(VISIBLE);}
        if( flag == 11 && sw11 == 0){seekbar1.setVisibility(INVISIBLE);}else if(flag == 11 && sw11 == 1){seekbar1.setVisibility(VISIBLE);}
        if( flag == 12 && sw12 == 0){           seekbar1.setVisibility(INVISIBLE);        }else if(flag == 12 && sw12 == 1){    seekbar1.setVisibility(VISIBLE);}
        if( flag == 13 && sw13 == 0){            seekbar1.setVisibility(INVISIBLE);        }else if(flag == 13 && sw13 == 1){     seekbar1.setVisibility(VISIBLE);}
        if( flag == 14 && sw14 == 0){            seekbar1.setVisibility(INVISIBLE);        }else if(flag == 14 && sw14 == 1){     seekbar1.setVisibility(VISIBLE);}
        if( flag == 15 && sw15 == 0){            seekbar1.setVisibility(INVISIBLE);        }else if(flag == 15 && sw15 == 1){     seekbar1.setVisibility(VISIBLE);}
        if( flag == 16 && sw16 == 0){            seekbar1.setVisibility(INVISIBLE);        }else if(flag == 16 && sw16 == 1){     seekbar1.setVisibility(VISIBLE);}
        if( flag == 17 && sw17 == 0){            seekbar1.setVisibility(INVISIBLE);        }else if(flag == 17 && sw17== 1){     seekbar1.setVisibility(VISIBLE);}
        if( flag == 18 && sw18 == 0){            seekbar1.setVisibility(INVISIBLE);        }else if(flag == 18 && sw18 == 1){     seekbar1.setVisibility(VISIBLE);}
        if(flag == 0){
            Bt1.setTextColor(text_nosel_color);Bt2.setTextColor(text_nosel_color); Bt3.setTextColor(text_nosel_color);Bt4.setTextColor(text_nosel_color);
            Bt5.setTextColor(text_nosel_color);Bt6.setTextColor(text_nosel_color); Bt7.setTextColor(text_nosel_color);Bt8.setTextColor(text_nosel_color);
            Bt9.setTextColor(text_nosel_color);Bt10.setTextColor(text_nosel_color); Bt11.setTextColor(text_nosel_color);Bt12.setTextColor(text_nosel_color);
            Bt13.setTextColor(text_nosel_color);Bt14.setTextColor(text_nosel_color); Bt15.setTextColor(text_nosel_color);Bt16.setTextColor(text_nosel_color);
            Bt17.setTextColor(text_nosel_color);Bt18.setTextColor(text_nosel_color);

        }else if(flag == 1){
            Bt1.setTextColor(text_sel_color);Bt2.setTextColor(text_nosel_color); Bt3.setTextColor(text_nosel_color);Bt4.setTextColor(text_nosel_color);
            Bt5.setTextColor(text_nosel_color);Bt6.setTextColor(text_nosel_color); Bt7.setTextColor(text_nosel_color);Bt8.setTextColor(text_nosel_color);
            Bt9.setTextColor(text_nosel_color);Bt10.setTextColor(text_nosel_color); Bt11.setTextColor(text_nosel_color);Bt12.setTextColor(text_nosel_color);
            Bt13.setTextColor(text_nosel_color);Bt14.setTextColor(text_nosel_color); Bt15.setTextColor(text_nosel_color);Bt16.setTextColor(text_nosel_color);
            Bt17.setTextColor(text_nosel_color);Bt18.setTextColor(text_nosel_color);

        }
        else if(flag == 2){
            Bt1.setTextColor(text_sel_color);Bt2.setTextColor(text_sel_color); Bt3.setTextColor(text_nosel_color);Bt4.setTextColor(text_nosel_color);
            Bt5.setTextColor(text_nosel_color);Bt6.setTextColor(text_nosel_color); Bt7.setTextColor(text_nosel_color);Bt8.setTextColor(text_nosel_color);
            Bt9.setTextColor(text_nosel_color);Bt10.setTextColor(text_nosel_color); Bt11.setTextColor(text_nosel_color);Bt12.setTextColor(text_nosel_color);
            Bt13.setTextColor(text_nosel_color);Bt14.setTextColor(text_nosel_color); Bt15.setTextColor(text_nosel_color);Bt16.setTextColor(text_nosel_color);
            Bt17.setTextColor(text_nosel_color);Bt18.setTextColor(text_nosel_color);

        }
        else if(flag == 3){
            Bt1.setTextColor(text_sel_color);Bt2.setTextColor(text_nosel_color); Bt3.setTextColor(text_sel_color);Bt4.setTextColor(text_nosel_color);
            Bt5.setTextColor(text_nosel_color);Bt6.setTextColor(text_nosel_color); Bt7.setTextColor(text_nosel_color);Bt8.setTextColor(text_nosel_color);
            Bt9.setTextColor(text_nosel_color);Bt10.setTextColor(text_nosel_color); Bt11.setTextColor(text_nosel_color);Bt12.setTextColor(text_nosel_color);
            Bt13.setTextColor(text_nosel_color);Bt14.setTextColor(text_nosel_color); Bt15.setTextColor(text_nosel_color);Bt16.setTextColor(text_nosel_color);
            Bt17.setTextColor(text_nosel_color);Bt18.setTextColor(text_nosel_color);

        }
        else if(flag == 4){
            Bt1.setTextColor(text_sel_color);Bt2.setTextColor(text_nosel_color); Bt3.setTextColor(text_nosel_color);Bt4.setTextColor(text_sel_color);
            Bt5.setTextColor(text_nosel_color);Bt6.setTextColor(text_nosel_color); Bt7.setTextColor(text_nosel_color);Bt8.setTextColor(text_nosel_color);
            Bt9.setTextColor(text_nosel_color);Bt10.setTextColor(text_nosel_color); Bt11.setTextColor(text_nosel_color);Bt12.setTextColor(text_nosel_color);
            Bt13.setTextColor(text_nosel_color);Bt14.setTextColor(text_nosel_color); Bt15.setTextColor(text_nosel_color);Bt16.setTextColor(text_nosel_color);
            Bt17.setTextColor(text_nosel_color);Bt18.setTextColor(text_nosel_color);

        }
        else if(flag == 5){
            Bt1.setTextColor(text_sel_color);Bt2.setTextColor(text_nosel_color); Bt3.setTextColor(text_nosel_color);Bt4.setTextColor(text_nosel_color);
            Bt5.setTextColor(text_sel_color);Bt6.setTextColor(text_nosel_color); Bt7.setTextColor(text_nosel_color);Bt8.setTextColor(text_nosel_color);
            Bt9.setTextColor(text_nosel_color);Bt10.setTextColor(text_nosel_color); Bt11.setTextColor(text_nosel_color);Bt12.setTextColor(text_nosel_color);
            Bt13.setTextColor(text_nosel_color);Bt14.setTextColor(text_nosel_color); Bt15.setTextColor(text_nosel_color);Bt16.setTextColor(text_nosel_color);
            Bt17.setTextColor(text_nosel_color);Bt18.setTextColor(text_nosel_color);

        }
        else if(flag == 6){
            Bt1.setTextColor(text_sel_color);Bt2.setTextColor(text_nosel_color); Bt3.setTextColor(text_nosel_color);Bt4.setTextColor(text_nosel_color);
            Bt5.setTextColor(text_nosel_color);Bt6.setTextColor(text_sel_color); Bt7.setTextColor(text_nosel_color);Bt8.setTextColor(text_nosel_color);
            Bt9.setTextColor(text_nosel_color);Bt10.setTextColor(text_nosel_color); Bt11.setTextColor(text_nosel_color);Bt12.setTextColor(text_nosel_color);
            Bt13.setTextColor(text_nosel_color);Bt14.setTextColor(text_nosel_color); Bt15.setTextColor(text_nosel_color);Bt16.setTextColor(text_nosel_color);
            Bt17.setTextColor(text_nosel_color);Bt18.setTextColor(text_nosel_color);

        }
        else if(flag == 7){
            Bt1.setTextColor(text_sel_color);Bt2.setTextColor(text_nosel_color); Bt3.setTextColor(text_nosel_color);Bt4.setTextColor(text_nosel_color);
            Bt5.setTextColor(text_nosel_color);Bt6.setTextColor(text_nosel_color); Bt7.setTextColor(text_sel_color);Bt8.setTextColor(text_nosel_color);
            Bt9.setTextColor(text_nosel_color);Bt10.setTextColor(text_nosel_color); Bt11.setTextColor(text_nosel_color);Bt12.setTextColor(text_nosel_color);
            Bt13.setTextColor(text_nosel_color);Bt14.setTextColor(text_nosel_color); Bt15.setTextColor(text_nosel_color);Bt16.setTextColor(text_nosel_color);
            Bt17.setTextColor(text_nosel_color);Bt18.setTextColor(text_nosel_color);

        }
        else if(flag == 8){
            Bt1.setTextColor(text_sel_color);Bt2.setTextColor(text_nosel_color); Bt3.setTextColor(text_nosel_color);Bt4.setTextColor(text_nosel_color);
            Bt5.setTextColor(text_nosel_color);Bt6.setTextColor(text_nosel_color); Bt7.setTextColor(text_nosel_color);Bt8.setTextColor(text_sel_color);
            Bt9.setTextColor(text_nosel_color);Bt10.setTextColor(text_nosel_color); Bt11.setTextColor(text_nosel_color);Bt12.setTextColor(text_nosel_color);
            Bt13.setTextColor(text_nosel_color);Bt14.setTextColor(text_nosel_color); Bt15.setTextColor(text_nosel_color);Bt16.setTextColor(text_nosel_color);
            Bt17.setTextColor(text_nosel_color);Bt18.setTextColor(text_nosel_color);

        }
        else if(flag == 9){
            Bt1.setTextColor(text_sel_color);Bt2.setTextColor(text_nosel_color); Bt3.setTextColor(text_nosel_color);Bt4.setTextColor(text_nosel_color);
            Bt5.setTextColor(text_nosel_color);Bt6.setTextColor(text_nosel_color); Bt7.setTextColor(text_nosel_color);Bt8.setTextColor(text_nosel_color);
            Bt9.setTextColor(text_sel_color);Bt10.setTextColor(text_nosel_color); Bt11.setTextColor(text_nosel_color);Bt12.setTextColor(text_nosel_color);
            Bt13.setTextColor(text_nosel_color);Bt14.setTextColor(text_nosel_color); Bt15.setTextColor(text_nosel_color);Bt16.setTextColor(text_nosel_color);
            Bt17.setTextColor(text_nosel_color);Bt18.setTextColor(text_nosel_color);

        }
        else if(flag == 10){
            Bt1.setTextColor(text_sel_color);Bt2.setTextColor(text_nosel_color); Bt3.setTextColor(text_nosel_color);Bt4.setTextColor(text_nosel_color);
            Bt5.setTextColor(text_nosel_color);Bt6.setTextColor(text_nosel_color); Bt7.setTextColor(text_nosel_color);Bt8.setTextColor(text_nosel_color);
            Bt9.setTextColor(text_nosel_color);Bt10.setTextColor(text_sel_color); Bt11.setTextColor(text_nosel_color);Bt12.setTextColor(text_nosel_color);
            Bt13.setTextColor(text_nosel_color);Bt14.setTextColor(text_nosel_color); Bt15.setTextColor(text_nosel_color);Bt16.setTextColor(text_nosel_color);
            Bt17.setTextColor(text_nosel_color);Bt18.setTextColor(text_nosel_color);

        }
        else if(flag == 11){
            Bt1.setTextColor(text_sel_color);Bt2.setTextColor(text_nosel_color); Bt3.setTextColor(text_nosel_color);Bt4.setTextColor(text_nosel_color);
            Bt5.setTextColor(text_nosel_color);Bt6.setTextColor(text_nosel_color); Bt7.setTextColor(text_nosel_color);Bt8.setTextColor(text_nosel_color);
            Bt9.setTextColor(text_nosel_color);Bt10.setTextColor(text_nosel_color); Bt11.setTextColor(text_sel_color);Bt12.setTextColor(text_nosel_color);
            Bt13.setTextColor(text_nosel_color);Bt14.setTextColor(text_nosel_color); Bt15.setTextColor(text_nosel_color);Bt16.setTextColor(text_nosel_color);
            Bt17.setTextColor(text_nosel_color);Bt18.setTextColor(text_nosel_color);

        }
        else if(flag == 12){
            Bt1.setTextColor(text_sel_color);Bt2.setTextColor(text_nosel_color); Bt3.setTextColor(text_nosel_color);Bt4.setTextColor(text_nosel_color);
            Bt5.setTextColor(text_nosel_color);Bt6.setTextColor(text_nosel_color); Bt7.setTextColor(text_nosel_color);Bt8.setTextColor(text_nosel_color);
            Bt9.setTextColor(text_nosel_color);Bt10.setTextColor(text_nosel_color); Bt11.setTextColor(text_nosel_color);Bt12.setTextColor(text_sel_color);
            Bt13.setTextColor(text_nosel_color);Bt14.setTextColor(text_nosel_color); Bt15.setTextColor(text_nosel_color);Bt16.setTextColor(text_nosel_color);
            Bt17.setTextColor(text_nosel_color);Bt18.setTextColor(text_nosel_color);

        }
        else if(flag == 13){
            Bt1.setTextColor(text_sel_color);Bt2.setTextColor(text_nosel_color); Bt3.setTextColor(text_nosel_color);Bt4.setTextColor(text_nosel_color);
            Bt5.setTextColor(text_nosel_color);Bt6.setTextColor(text_nosel_color); Bt7.setTextColor(text_nosel_color);Bt8.setTextColor(text_nosel_color);
            Bt9.setTextColor(text_nosel_color);Bt10.setTextColor(text_nosel_color); Bt11.setTextColor(text_nosel_color);Bt12.setTextColor(text_nosel_color);
            Bt13.setTextColor(text_sel_color);Bt14.setTextColor(text_nosel_color); Bt15.setTextColor(text_nosel_color);Bt16.setTextColor(text_nosel_color);
            Bt17.setTextColor(text_nosel_color);Bt18.setTextColor(text_nosel_color);

        }
        else if(flag == 14){
            Bt1.setTextColor(text_sel_color);Bt2.setTextColor(text_nosel_color); Bt3.setTextColor(text_nosel_color);Bt4.setTextColor(text_nosel_color);
            Bt5.setTextColor(text_nosel_color);Bt6.setTextColor(text_nosel_color); Bt7.setTextColor(text_nosel_color);Bt8.setTextColor(text_nosel_color);
            Bt9.setTextColor(text_nosel_color);Bt10.setTextColor(text_nosel_color); Bt11.setTextColor(text_nosel_color);Bt12.setTextColor(text_nosel_color);
            Bt13.setTextColor(text_nosel_color);Bt14.setTextColor(text_sel_color); Bt15.setTextColor(text_nosel_color);Bt16.setTextColor(text_nosel_color);
            Bt17.setTextColor(text_nosel_color);Bt18.setTextColor(text_nosel_color);

        }
        else if(flag == 15){
            Bt1.setTextColor(text_sel_color);Bt2.setTextColor(text_nosel_color); Bt3.setTextColor(text_nosel_color);Bt4.setTextColor(text_nosel_color);
            Bt5.setTextColor(text_nosel_color);Bt6.setTextColor(text_nosel_color); Bt7.setTextColor(text_nosel_color);Bt8.setTextColor(text_nosel_color);
            Bt9.setTextColor(text_nosel_color);Bt10.setTextColor(text_nosel_color); Bt11.setTextColor(text_nosel_color);Bt12.setTextColor(text_nosel_color);
            Bt13.setTextColor(text_nosel_color);Bt14.setTextColor(text_nosel_color); Bt15.setTextColor(text_sel_color);Bt16.setTextColor(text_nosel_color);
            Bt17.setTextColor(text_nosel_color);Bt18.setTextColor(text_nosel_color);

        }
        else if(flag == 16){
            Bt1.setTextColor(text_sel_color);Bt2.setTextColor(text_nosel_color); Bt3.setTextColor(text_nosel_color);Bt4.setTextColor(text_nosel_color);
            Bt5.setTextColor(text_nosel_color);Bt6.setTextColor(text_nosel_color); Bt7.setTextColor(text_nosel_color);Bt8.setTextColor(text_nosel_color);
            Bt9.setTextColor(text_nosel_color);Bt10.setTextColor(text_nosel_color); Bt11.setTextColor(text_nosel_color);Bt12.setTextColor(text_nosel_color);
            Bt13.setTextColor(text_nosel_color);Bt14.setTextColor(text_nosel_color); Bt15.setTextColor(text_nosel_color);Bt16.setTextColor(text_sel_color);
            Bt17.setTextColor(text_nosel_color);Bt18.setTextColor(text_nosel_color);

        } else if(flag == 17){
            Bt1.setTextColor(text_sel_color);Bt2.setTextColor(text_nosel_color); Bt3.setTextColor(text_nosel_color);Bt4.setTextColor(text_nosel_color);
            Bt5.setTextColor(text_nosel_color);Bt6.setTextColor(text_nosel_color); Bt7.setTextColor(text_nosel_color);Bt8.setTextColor(text_nosel_color);
            Bt9.setTextColor(text_nosel_color);Bt10.setTextColor(text_nosel_color); Bt11.setTextColor(text_nosel_color);Bt12.setTextColor(text_nosel_color);
            Bt13.setTextColor(text_nosel_color);Bt14.setTextColor(text_nosel_color); Bt15.setTextColor(text_nosel_color);Bt16.setTextColor(text_nosel_color);
            Bt17.setTextColor(text_sel_color);Bt18.setTextColor(text_nosel_color);

        }else if(flag == 18){
            Bt1.setTextColor(text_sel_color);Bt2.setTextColor(text_nosel_color); Bt3.setTextColor(text_nosel_color);Bt4.setTextColor(text_nosel_color);
            Bt5.setTextColor(text_nosel_color);Bt6.setTextColor(text_nosel_color); Bt7.setTextColor(text_nosel_color);Bt8.setTextColor(text_nosel_color);
            Bt9.setTextColor(text_nosel_color);Bt10.setTextColor(text_nosel_color); Bt11.setTextColor(text_nosel_color);Bt12.setTextColor(text_nosel_color);
            Bt13.setTextColor(text_nosel_color);Bt14.setTextColor(text_nosel_color); Bt15.setTextColor(text_nosel_color);Bt16.setTextColor(text_nosel_color);
            Bt17.setTextColor(text_nosel_color);Bt18.setTextColor(text_sel_color);

        }

    }

}
