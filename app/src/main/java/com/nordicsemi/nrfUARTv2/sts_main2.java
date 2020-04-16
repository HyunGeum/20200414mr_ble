package com.nordicsemi.nrfUARTv2;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;


public class sts_main2 implements RadioGroup.OnCheckedChangeListener {
    private static final int REQUEST_SELECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    private static final int UART_PROFILE_READY = 10;
    private String TAG = "sts_main2";
    private static final int UART_PROFILE_CONNECTED = 20;
    private static final int UART_PROFILE_DISCONNECTED = 21;
    private static final int STATE_OFF = 10;
    private static final int PROTO_TYPE_ETLC = 1;
    private static final int PROTO_TYPE_RLCM = 2;
    TextView mRemoteRssiVal, seek_level;
    RadioGroup mRg;
    private int mState = UART_PROFILE_DISCONNECTED;
    private UartService mService = null;
    private BluetoothDevice mDevice = null;
    private BluetoothAdapter mBtAdapter = null;
    private ListView messageListView;
    private ArrayAdapter<String> listAdapter;
    private Button btnConnectDisconnect, btnSend;

    private EditText edtMessage;
    private Timer mTimer;
    private ETLC_Manager etlc_manager;
    private RLCM_Manager rlcm_manager;
    private int proto_type = 0;
    private int m_conn_state = 0;
    private int text_on_color = Color.parseColor("#EE4500");
    private int text_off_color = Color.parseColor("#000000");
    private int mode_text_on_color = Color.parseColor("#0080FF");
    private byte[] switch_state = new byte[8];
    private int switch_mode = 0;
    String pic[] = {"테스트1", "테스트2", "테스트3", "테스트4",};
    int selectedIndex;


    //셀렉트 되었을때 글씨 색

    Context context;
    int sw1, sw2, sw3, sw4, sw5, sw6, sw7, sw8, sw9, sw10, sw11, sw12, sw13, sw14, sw15, sw16, sw17;
    int flag, ty_flag;
    LinearLayout Bt1, Bt2, Bt3, Bt4, Bt5, Bt6, Bt7, Bt8, Bt9, Bt10, Bt11, Bt12, Bt13, Bt14, Bt15, Bt16, Bt17,Bt18;
    Button Ty_i, Ty_g, Ty_s, Ty_m;
    TextView Sw_name, bt1_name, bt2_name, bt3_name, bt4_name, bt5_name, bt6_name, bt7_name, bt8_name, bt9_name, bt10_name, bt11_name, bt12_name, bt13_name, bt14_name, bt15_name, bt16_name, bt17_name;
    TextView bt1_value, bt2_value, bt3_value, bt4_value, bt5_value, bt6_value, bt7_value, bt8_value, bt9_value, bt10_value, bt11_value, bt12_value, bt13_value, bt14_value, bt15_value, bt16_value, bt17_value;
    SeekBar seekbar1;
    TextView tv1, tv2, tv3, tv4;
    ImageView img;
    int dianum;
    LinearLayout Line1, Line2, Line3, Line4, Line5, Line6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sts_main3);




        //버튼 갯수 표시






        //  getSupportActionBar().setIcon(R.drawable.lv1); //로고 이미지
        //getSupportActionBar().setDisplayUseLogoEnabled(true); //로고 활성


        getSupportActionBar().setTitle("STS SWITCH"); //액션바 이름
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF6991B8)); //액션바 색깔

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//홈버튼 활성


        //액션바 숨기기
        //hideActionBar();


        etlc_init();
        rlcm_init();


        //블루투스 연결 장치 표시
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBtAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        img = findViewById(R.id.imageView);
        messageListView = (ListView) findViewById(R.id.listMessage);
        listAdapter = new ArrayAdapter<String>(this, R.layout.message_detail);
        messageListView.setAdapter(listAdapter);
        messageListView.setDivider(null);
        btnConnectDisconnect = (Button) findViewById(R.id.btn_select);
        btnSend = (Button) findViewById(R.id.sendButton);
        edtMessage = (EditText) findViewById(R.id.sendText);
        service_init();
        switch_init();


        btnConnectDisconnect = (Button) findViewById(R.id.btn_select);
        btnConnectDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mBtAdapter.isEnabled()) {
                    Log.i(TAG, "onClick - BT not enabled yet");
                    Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
                } else {
                    if (btnConnectDisconnect.getText().equals("Connect")) {

                        //Connect button pressed, open DeviceListActivity class, with popup windows that scan for devices

                        Intent newIntent = new Intent(sts_main2.this, DeviceListActivity.class);
                        startActivityForResult(newIntent, REQUEST_SELECT_DEVICE);
                    } else {
                        //Disconnect button pressed
                        if (mDevice != null) {
                            mService.disconnect();

                        }
                    }
                }
            }
        });


//시크바
        seekbar1 = findViewById(R.id.seekBar1);

//edittext 글씨 삽입
        Sw_name = findViewById(R.id.sw_name);
        Sw_name.setText(R.string.sw_name);

        //  bt1_name = findViewById(R.id.bt_text1);
        bt1_name = findViewById(R.id.bt_text2);
        bt2_name = findViewById(R.id.bt_text3);
        bt3_name = findViewById(R.id.bt_text4);
        bt4_name = findViewById(R.id.bt_text5);
        bt5_name = findViewById(R.id.bt_text6);
        bt6_name = findViewById(R.id.bt_text7);
        bt7_name = findViewById(R.id.bt_text8);
        bt8_name = findViewById(R.id.bt_text9);
        bt9_name = findViewById(R.id.bt_text10);
        bt10_name = findViewById(R.id.bt_text11);
        bt11_name = findViewById(R.id.bt_text12);
        bt12_name = findViewById(R.id.bt_text13);
        bt13_name = findViewById(R.id.bt_text14);
        bt14_name = findViewById(R.id.bt_text15);
        bt15_name = findViewById(R.id.bt_text16);
        bt16_name = findViewById(R.id.bt_text17);
        bt17_name = findViewById(R.id.bt_text18);


        //bt1_value = findViewById(R.id.bt_value1);
        bt1_value = findViewById(R.id.bt_value2);
        bt2_value = findViewById(R.id.bt_value3);
        bt3_value = findViewById(R.id.bt_value4);
        bt4_value = findViewById(R.id.bt_value5);
        bt5_value = findViewById(R.id.bt_value6);
        bt6_value = findViewById(R.id.bt_value7);
        bt7_value = findViewById(R.id.bt_value8);
        bt8_value = findViewById(R.id.bt_value9);
        bt9_value = findViewById(R.id.bt_value10);
        bt10_value = findViewById(R.id.bt_value11);
        bt11_value = findViewById(R.id.bt_value12);
        bt12_value = findViewById(R.id.bt_value13);
        bt13_value = findViewById(R.id.bt_value14);
        bt14_value = findViewById(R.id.bt_value15);
        bt15_value = findViewById(R.id.bt_value16);
        bt16_value = findViewById(R.id.bt_value17);
        bt17_value = findViewById(R.id.bt_value18);


        //  Bt1 = findViewById(R.id.bt1);
        Bt1 = findViewById(R.id.bt2);
        Bt2 = findViewById(R.id.bt3);
        Bt3 = findViewById(R.id.bt4);
        Bt4 = findViewById(R.id.bt5);
        Bt5 = findViewById(R.id.bt6);
        Bt6 = findViewById(R.id.bt7);
        Bt7 = findViewById(R.id.bt8);
        Bt8 = findViewById(R.id.bt9);
        Bt9 = findViewById(R.id.bt10);
        Bt10 = findViewById(R.id.bt11);
        Bt11 = findViewById(R.id.bt12);
        Bt12 = findViewById(R.id.bt13);
        Bt13 = findViewById(R.id.bt14);
        Bt14 = findViewById(R.id.bt15);
        Bt15 = findViewById(R.id.bt16);
        Bt16 = findViewById(R.id.bt17);
        Bt17 = findViewById(R.id.bt18);
        Ty_i = findViewById(R.id.ty_i);
        Ty_g = findViewById(R.id.ty_g);
        Ty_s = findViewById(R.id.ty_s);
        Ty_m = findViewById(R.id.ty_m);

        tv1 = findViewById(R.id.sensor_temper);
        tv2 = findViewById(R.id.sensor_humidity);
        tv3 = findViewById(R.id.sensor_co2);
        tv4 = findViewById(R.id.sensor_tvoc);

        tv1.setText(R.string.sensor_temp); //초기문자
        tv2.setText(R.string.sensor_humi);
        tv3.setText(R.string.sensor_co);
        tv4.setText(R.string.sensor_tvoc);


//4회로  8회로  16회로



//롱클릭
        Bt1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                sw_sel(sw1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1);
                flag = 1;
                if (sw1 == 0) { //거짓으로 온오프 상태를 표시함
                    sw1 = 1;
                } else {
                    sw1 = 0;
                }
                Log.e("sts_main2", "버튼1 롱클릭");
                return true;
            }
        }); //sw값 반대로 주는 부분은 상태값을 정상으로 받아올떄는 삭제
        Bt2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                sw_sel(0, sw2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2);
                flag = 2;
                if (sw2 == 0) {
                    sw2 = 1;
                    if (switch_state[0] == 0) {
                        switch_state[0] = 1;
                    } else switch_state[0] = 0;
                    SwitchOnOff(1, switch_state[0]);
                } else {
                    sw2 = 0;
                }
                Log.e("sts_main2", "버튼2 롱클릭");
                return true;
            }
        });
        Bt3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                sw_sel(0, 0, sw3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3);
                flag = 3;
                if (sw3 == 0) {
                    sw3 = 1;
                } else {
                    sw3 = 0;
                }
                return true;
            }
        });
        Bt4.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                sw_sel(0, 0, 0, sw4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4);
                flag = 4;
                if (sw4 == 0) {
                    sw4 = 1;
                } else {
                    sw4 = 0;
                }
                return true;
            }
        });
        Bt5.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                sw_sel(0, 0, 0, 0, sw5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5);
                flag = 5;
                if (sw5 == 0) {
                    sw5 = 1;
                } else {
                    sw5 = 0;
                }
                return true;
            }
        });
        Bt6.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                sw_sel(0, 0, 0, 0, 0, sw6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6);
                flag = 6;
                if (sw6 == 0) {
                    sw6 = 1;
                } else {
                    sw6 = 0;
                }
                return true;
            }
        });
        Bt7.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                sw_sel(0, 0, 0, 0, 0, 0, sw7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7);
                flag = 7;
                if (sw7 == 0) {
                    sw7 = 1;
                } else {
                    sw7 = 0;
                }
                return true;
            }
        });
        Bt8.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                sw_sel(0, 0, 0, 0, 0, 0, 0, sw8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8);
                flag = 8;
                if (sw8 == 0) {
                    sw8 = 1;
                } else {
                    sw8 = 0;
                }
                return true;
            }
        });
        Bt9.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                sw_sel(0, 0, 0, 0, 0, 0, 0, 0, sw9, 0, 0, 0, 0, 0, 0, 0, 0, 9);
                flag = 9;
                if (sw9 == 0) {
                    sw9 = 1;
                } else {
                    sw9 = 0;
                }
                return true;
            }
        });
        Bt10.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                sw_sel(0, 0, 0, 0, 0, 0, 0, 0, 0, sw10, 0, 0, 0, 0, 0, 0, 0, 10);
                flag = 10;
                if (sw10 == 0) {
                    sw10 = 1;
                } else {
                    sw10 = 0;
                }
                return true;
            }
        });
        Bt11.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                sw_sel(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, sw11, 0, 0, 0, 0, 0, 0, 11);
                flag = 11;
                if (sw11 == 0) {
                    sw11 = 1;
                } else {
                    sw11 = 0;
                }
                return true;
            }
        });
        Bt12.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                sw_sel(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, sw12, 0, 0, 0, 0, 0, 12);
                flag = 12;
                if (sw12 == 0) {
                    sw12 = 1;
                } else {
                    sw12 = 0;
                }
                return true;
            }
        });
        Bt13.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                sw_sel(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, sw13, 0, 0, 0, 0, 13);
                flag = 13;
                if (sw13 == 0) {
                    sw13 = 1;
                } else {
                    sw13 = 0;
                }
                return true;
            }
        });
        Bt14.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                sw_sel(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, sw14, 0, 0, 0, 14);
                flag = 14;
                if (sw14 == 0) {
                    sw14 = 1;
                } else {
                    sw14 = 0;
                }
                return true;
            }
        });
        Bt15.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                sw_sel(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, sw15, 0, 0, 15);
                flag = 15;
                if (sw15 == 0) {
                    sw15 = 1;
                } else {
                    sw15 = 0;
                }
                return true;
            }
        });
        Bt16.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                sw_sel(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, sw16, 0, 16);
                flag = 16;
                if (sw16 == 0) {
                    sw16 = 1;
                } else {
                    sw16 = 0;
                }
                return true;
            }
        });

        Bt17.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                sw_sel(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, sw17, 16);
                flag = 17;
                if (sw17 == 0) {
                    sw17 = 1;
                } else {
                    sw17 = 0;
                }
                return true;
            }
        });


//숏클릭
        Bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 1;
                sw_sel(sw1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1);
                if (sw1 == 0) {
                    Bt1.setBackground(getDrawable(R.drawable.lig_off));
                    sw1 = 1;
                } else {
                    Bt1.setBackground(getDrawable(R.drawable.lig_on));
                    sw1 = 0;
                }
                Log.e("sts_main2", "버튼1 숏클릭" + sw1 + flag);


            }
        });

        Bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 2;
                sw_sel(0, sw2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2);
                if (sw2 == 0) {
                    Bt2.setBackground(getDrawable(R.drawable.lig_off));
                    sw2 = 1;
                } else {
                    Bt2.setBackground(getDrawable(R.drawable.lig_on));
                    sw2 = 0;
                }
                Log.e("sts_main2", "버튼2 숏클릭" + sw2 + flag);
            }
        });

        Bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 3;
                sw_sel(0, 0, sw3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3);
                if (sw3 == 0) {
                    Bt3.setBackground(getDrawable(R.drawable.lig_off));
                    sw3 = 1;
                } else {
                    Bt3.setBackground(getDrawable(R.drawable.lig_on));
                    sw3 = 0;
                }
            }
        });
        Bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 4;
                sw_sel(0, 0, 0, sw4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4);
                if (sw4 == 0) {
                    Bt4.setBackground(getDrawable(R.drawable.lig_off));
                    sw4 = 1;
                } else {
                    Bt4.setBackground(getDrawable(R.drawable.lig_on));
                    sw4 = 0;
                }
            }
        });

        Bt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 5;
                sw_sel(0, 0, 0, 0, sw5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5);
                if (sw5 == 0) {
                    Bt5.setBackground(getDrawable(R.drawable.lig_off));
                    sw5 = 1;
                } else {
                    Bt5.setBackground(getDrawable(R.drawable.lig_on));
                    sw5 = 0;
                }
            }
        });
        Bt6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 6;
                sw_sel(0, 0, 0, 0, 0, sw6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6);
                if (sw6 == 0) {
                    Bt6.setBackground(getDrawable(R.drawable.lig_off));
                    sw6 = 1;
                } else {
                    Bt6.setBackground(getDrawable(R.drawable.lig_on));
                    sw6 = 0;
                }
            }
        });
        Bt7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 7;
                sw_sel(0, 0, 0, 0, 0, 0, sw7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7);
                if (sw7 == 0) {
                    Bt7.setBackground(getDrawable(R.drawable.lig_off));
                    sw7 = 1;
                } else {
                    Bt7.setBackground(getDrawable(R.drawable.lig_on));
                    sw7 = 0;
                }
            }
        });
        Bt8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 8;
                sw_sel(0, 0, 0, 0, 0, 0, 0, sw8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8);
                if (sw8 == 0) {
                    Bt8.setBackground(getDrawable(R.drawable.lig_off));
                    sw8 = 1;
                } else {
                    Bt8.setBackground(getDrawable(R.drawable.lig_on));
                    sw8 = 0;
                }
            }
        });
        Bt9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 9;
                sw_sel(0, 0, 0, 0, 0, 0, 0, 0, sw9, 0, 0, 0, 0, 0, 0, 0, 0, 9);
                if (sw9 == 0) {
                    Bt9.setBackground(getDrawable(R.drawable.lig_off));
                    sw9 = 1;
                } else {
                    Bt9.setBackground(getDrawable(R.drawable.lig_on));
                    sw9 = 0;
                }
            }
        });
        Bt10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 10;
                sw_sel(0, 0, 0, 0, 0, 0, 0, 0, 0, sw10, 0, 0, 0, 0, 0, 0, 0, 10);
                if (sw10 == 0) {
                    Bt10.setBackground(getDrawable(R.drawable.lig_off));
                    sw10 = 1;
                } else {
                    Bt10.setBackground(getDrawable(R.drawable.lig_on));
                    sw10 = 0;
                }
            }
        });
        Bt11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 11;
                sw_sel(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, sw11, 0, 0, 0, 0, 0, 0, 11);
                if (sw11 == 0) {
                    Bt11.setBackground(getDrawable(R.drawable.lig_off));
                    sw11 = 1;
                } else {
                    Bt11.setBackground(getDrawable(R.drawable.lig_on));
                    sw11 = 0;
                }
            }
        });
        Bt12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 12;
                sw_sel(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, sw12, 0, 0, 0, 0, 0, 12);
                if (sw12 == 0) {
                    Bt12.setBackground(getDrawable(R.drawable.lig_off));
                    sw12 = 1;
                } else {
                    Bt12.setBackground(getDrawable(R.drawable.lig_on));
                    sw12 = 0;
                }
            }
        });
        Bt13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 13;
                sw_sel(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, sw13, 0, 0, 0, 0, 13);
                if (sw13 == 0) {
                    Bt13.setBackground(getDrawable(R.drawable.lig_off));
                    sw13 = 1;
                } else {
                    Bt13.setBackground(getDrawable(R.drawable.lig_on));
                    sw13 = 0;
                }
            }
        });
        Bt14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 14;
                sw_sel(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, sw14, 0, 0, 0, 14);
                if (sw14 == 0) {
                    Bt14.setBackground(getDrawable(R.drawable.lig_off));
                    sw14 = 1;
                } else {
                    Bt14.setBackground(getDrawable(R.drawable.lig_on));
                    sw14 = 0;
                }
            }
        });
        Bt15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 15;
                sw_sel(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, sw15, 0, 0, 15);
                if (sw15 == 0) {
                    Bt15.setBackground(getDrawable(R.drawable.lig_off));
                    sw15 = 1;
                } else {
                    Bt3.setBackground(getDrawable(R.drawable.lig_on));
                    sw15 = 0;
                }
            }
        });
        Bt16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 16;
                sw_sel(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, sw16, 0, 16);
                if (sw16 == 0) {
                    Bt16.setBackground(getDrawable(R.drawable.lig_off));
                    sw16 = 1;
                } else {
                    Bt16.setBackground(getDrawable(R.drawable.lig_on));
                    sw16 = 0;
                }
            }
        });
        Bt17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 17;
                sw_sel(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, sw17, 17);
                if (sw17 == 0) {
                    Bt16.setBackground(getDrawable(R.drawable.lig_off));
                    sw17 = 1;
                } else {
                    Bt17.setBackground(getDrawable(R.drawable.lig_on));
                    sw17 = 0;
                }
            }
        });


        //igsm 버튼색 바꾸기
        Ty_i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ty_flag = 1;


                Ty_i.setBackground(getDrawable(R.drawable.i_on_01));
                Ty_g.setBackground(getDrawable(R.drawable.g_off_01));
                Ty_s.setBackground(getDrawable(R.drawable.s_off_01));
                Ty_m.setBackground(getDrawable(R.drawable.m_off_01));
                Log.e("sts_main2", "I 버튼 클릭");

            }
        });

        Ty_g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ty_flag = 2;
                Ty_i.setBackground(getDrawable(R.drawable.i_off_01));
                Ty_g.setBackground(getDrawable(R.drawable.g_on_01));
                Ty_s.setBackground(getDrawable(R.drawable.s_off_01));
                Ty_m.setBackground(getDrawable(R.drawable.m_off_01));
                Log.e("sts_main2", "G 버튼 클릭");


            }
        });
        Ty_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ty_flag = 3;

                Ty_i.setBackground(getDrawable(R.drawable.i_off_01));
                Ty_g.setBackground(getDrawable(R.drawable.g_off_01));
                Ty_s.setBackground(getDrawable(R.drawable.s_on_01));
                Ty_m.setBackground(getDrawable(R.drawable.m_off_01));
                Log.e("sts_main2", "S 버튼 클릭");

            }
        });

        Ty_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ty_flag = 4;
                Ty_i.setBackground(getDrawable(R.drawable.i_off_01));
                Ty_g.setBackground(getDrawable(R.drawable.g_off_01));
                Ty_s.setBackground(getDrawable(R.drawable.s_off_01));
                Ty_m.setBackground(getDrawable(R.drawable.m_on_01));
                Log.e("sts_main2", "M 버튼 클릭");

            }
        });

        seekbar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                String dan = " %";
                if (flag == 1 && sw1 == 0) { //플래그 1인상태에서 켜지면, sw의 0/1 상태 잘못됨 상태 받아올때 맞출것!!!
                    bt1_value.setText(progress + dan);
                } else if (flag == 2 && sw2 == 0) {
                    bt2_value.setText(progress + dan);
                } else if (flag == 3 && sw3 == 0) {
                    bt3_value.setText(progress + dan);
                } else if (flag == 4 && sw4 == 0) {
                    bt4_value.setText(progress + dan);
                } else if (flag == 5 && sw5 == 0) {
                    bt5_value.setText(progress + dan);
                } else if (flag == 6 && sw6 == 0) {
                    bt6_value.setText(progress + dan);
                } else if (flag == 7 && sw7 == 0) {
                    bt7_value.setText(progress + dan);
                } else if (flag == 8 && sw8 == 0) {
                    bt8_value.setText(progress + dan);
                } else if (flag == 9 && sw9 == 0) {
                    bt9_value.setText(progress + dan);
                } else if (flag == 10 && sw10 == 0) {
                    bt10_value.setText(progress + dan);
                } else if (flag == 11 && sw11 == 0) {
                    bt11_value.setText(progress + dan);
                } else if (flag == 12 && sw12 == 0) {
                    bt12_value.setText(progress + dan);
                } else if (flag == 13 && sw13 == 0) {
                    bt13_value.setText(progress + dan);
                } else if (flag == 14 && sw14 == 0) {
                    bt14_value.setText(progress + dan);
                } else if (flag == 15 && sw15 == 0) {
                    bt15_value.setText(progress + dan);
                } else if (flag == 16 && sw16 == 0) {
                    bt16_value.setText(progress + dan);
                } else if (flag == 17 && sw17 == 0) {
                    bt17_value.setText(progress + dan);

                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }


    private void sw_sel(int sw1, int sw2, int sw3, int sw4, int sw5, int sw6, int sw7, int sw8, int sw9, int sw10, int sw11, int sw12, int sw13, int sw14, int sw15, int sw16, int sw17, int flag) {


//시크바 숨김
        LinearLayout bar;
        bar = findViewById(R.id.barlin);


        if (flag == 1 && sw1 == 0) {
            seekbar1.setVisibility(INVISIBLE);


        } else if (flag == 1 && sw1 == 1) {
            seekbar1.setVisibility(VISIBLE);
            //  bar.setBackground(getDrawable(R.drawable.gra1));
        }
        if (flag == 2 && sw2 == 0) {
            seekbar1.setVisibility(INVISIBLE);
        } else if (flag == 2 && sw2 == 1) {
            seekbar1.setVisibility(VISIBLE);
        }
        if (flag == 3 && sw3 == 0) {
            seekbar1.setVisibility(INVISIBLE);
        } else if (flag == 3 && sw3 == 1) {
            seekbar1.setVisibility(VISIBLE);
        }
        if (flag == 4 && sw4 == 0) {
            seekbar1.setVisibility(INVISIBLE);
        } else if (flag == 4 && sw4 == 1) {
            seekbar1.setVisibility(VISIBLE);
        }
        if (flag == 5 && sw5 == 0) {
            seekbar1.setVisibility(INVISIBLE);
        } else if (flag == 5 && sw5 == 1) {
            seekbar1.setVisibility(VISIBLE);
        }
        if (flag == 6 && sw6 == 0) {
            seekbar1.setVisibility(INVISIBLE);
        } else if (flag == 6 && sw6 == 1) {
            seekbar1.setVisibility(VISIBLE);
        }
        if (flag == 7 && sw7 == 0) {
            seekbar1.setVisibility(INVISIBLE);
        } else if (flag == 7 && sw7 == 1) {
            seekbar1.setVisibility(VISIBLE);
        }
        if (flag == 8 && sw8 == 0) {
            seekbar1.setVisibility(INVISIBLE);
        } else if (flag == 8 && sw8 == 1) {
            seekbar1.setVisibility(VISIBLE);
        }
        if (flag == 9 && sw9 == 0) {
            seekbar1.setVisibility(INVISIBLE);
        } else if (flag == 9 && sw9 == 1) {
            seekbar1.setVisibility(VISIBLE);
        }
        if (flag == 10 && sw10 == 0) {
            seekbar1.setVisibility(INVISIBLE);
        } else if (flag == 10 && sw10 == 1) {
            seekbar1.setVisibility(VISIBLE);
        }
        if (flag == 11 && sw11 == 0) {
            seekbar1.setVisibility(INVISIBLE);
        } else if (flag == 11 && sw11 == 1) {
            seekbar1.setVisibility(VISIBLE);
        }
        if (flag == 12 && sw12 == 0) {
            seekbar1.setVisibility(INVISIBLE);
        } else if (flag == 12 && sw12 == 1) {
            seekbar1.setVisibility(VISIBLE);
        }
        if (flag == 13 && sw13 == 0) {
            seekbar1.setVisibility(INVISIBLE);
        } else if (flag == 13 && sw13 == 1) {
            seekbar1.setVisibility(VISIBLE);
        }
        if (flag == 14 && sw14 == 0) {
            seekbar1.setVisibility(INVISIBLE);
        } else if (flag == 14 && sw14 == 1) {
            seekbar1.setVisibility(VISIBLE);
        }
        if (flag == 15 && sw15 == 0) {
            seekbar1.setVisibility(INVISIBLE);
        } else if (flag == 15 && sw15 == 1) {
            seekbar1.setVisibility(VISIBLE);
        }
        if (flag == 16 && sw16 == 0) {
            seekbar1.setVisibility(INVISIBLE);
        } else if (flag == 16 && sw16 == 1) {
            seekbar1.setVisibility(VISIBLE);
        }
        int text_sel_color = Color.parseColor("#EE4500");
        int text_nosel_color = Color.parseColor("#000000");


        ArrayList<String> ar = new ArrayList<>();

//반복되는 전체 초기값을 꺼짐상태로 두고

        bt1_name.setTextColor(text_nosel_color);
        bt2_name.setTextColor(text_nosel_color);
        bt3_name.setTextColor(text_nosel_color);
        bt4_name.setTextColor(text_nosel_color);
        bt5_name.setTextColor(text_nosel_color);
        bt6_name.setTextColor(text_nosel_color);
        bt7_name.setTextColor(text_nosel_color);
        bt8_name.setTextColor(text_nosel_color);
        bt9_name.setTextColor(text_nosel_color);
        bt10_name.setTextColor(text_nosel_color);
        bt11_name.setTextColor(text_nosel_color);
        bt12_name.setTextColor(text_nosel_color);
        bt13_name.setTextColor(text_nosel_color);
        bt14_name.setTextColor(text_nosel_color);
        bt15_name.setTextColor(text_nosel_color);
        bt16_name.setTextColor(text_nosel_color);
        bt17_name.setTextColor(text_nosel_color);


        switch (flag) {//1~17의 값이 있으면 해당 이름의 색상을 변경

            case 1:
                bt1_name.setTextColor(text_sel_color);
                break;
            case 2:
                bt2_name.setTextColor(text_sel_color);
                break;
            case 3:
                bt3_name.setTextColor(text_sel_color);
                break;
            case 4:
                bt4_name.setTextColor(text_sel_color);
                break;
            case 5:
                bt5_name.setTextColor(text_sel_color);
                break;
            case 6:
                bt6_name.setTextColor(text_sel_color);
                break;
            case 7:
                bt7_name.setTextColor(text_sel_color);
                break;
            case 8:
                bt8_name.setTextColor(text_sel_color);
                break;
            case 9:
                bt9_name.setTextColor(text_sel_color);
                break;
            case 10:
                bt10_name.setTextColor(text_sel_color);
                break;
            case 11:
                bt11_name.setTextColor(text_sel_color);
                break;
            case 12:
                bt12_name.setTextColor(text_sel_color);
                break;
            case 13:
                bt13_name.setTextColor(text_sel_color);
                break;
            case 14:
                bt14_name.setTextColor(text_sel_color);
                break;
            case 15:
                bt15_name.setTextColor(text_sel_color);
                break;
            case 16:
                bt16_name.setTextColor(text_sel_color);
                break;
            case 17:
                bt17_name.setTextColor(text_sel_color);
                break;


        }



    }




    //UART service connected/disconnected
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder rawBinder) {
            mService = ((UartService.LocalBinder) rawBinder).getService();
            Log.e(TAG, "onServiceConnected mService= " + mService);
            if (!mService.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth");
                finish();
            }
        }

        public void onServiceDisconnected(ComponentName classname) {
            ////     mService.disconnect(mDevice);
            mService = null;
        }
    };
    private Handler mHandler = new Handler() {
        @Override

        //Handler events that received from UART service
        public void handleMessage(Message msg) {
//                            byte[] value = etlc_manager.DeviceInformationReq((byte)1,0);
//                Log.i(TAG, "TX:" + value);
//                mService.writeRXCharacteristic(value);
//                //Update the log with time stamp
//                String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
//                listAdapter.add("[" + currentDateTimeString + "] TX: " + value);
//                messageListView.smoothScrollToPosition(listAdapter.getCount() - 1);
//                edtMessage.setText("");
        }
    };

    private void SwitchOnOff(int no, int on) {
        byte[] value = null;
        if (m_conn_state == 0) return;
        if (proto_type == PROTO_TYPE_ETLC) {
            ETLC_Manager.ETLC_MSG msg = etlc_manager.createETLC_MSG();
            msg.saddr = (byte) no;
            msg.flag = 0x65;
            msg.data = new byte[1];
            msg.data[0] = (byte) on;
            msg.data_size = 1;
            value = etlc_manager.ELC_To_Byte(msg);
        } else if (proto_type == PROTO_TYPE_RLCM) {
            RLCM_Manager.RLCM_MSG msg = rlcm_manager.createRLCM_MSG();
            msg.saddr = (byte) no;
            msg.flag = 0x23;
            msg.data = new byte[6];
            msg.data[0] = (byte) on;
            msg.data[1] = (byte) 0;
            msg.data[2] = (byte) 0;
            msg.data[3] = (byte) rlcm_manager.my_device_type;
            msg.data[4] = (byte) rlcm_manager.my_address;
            msg.data[5] = (byte) no;
            msg.data_size = 6;
            value = rlcm_manager.RLCM_To_Byte(msg);
        } else return;

        Log.i(TAG, "TX: Switch (" + Integer.toString(on) + ") " + Byte.toString(value[0]));
        mService.writeRXCharacteristic(value);
        Log.e("sts_main2", "온오프 제어 메서드 실행");
    }

    private void switch_init() //이거 오류남
    {
        /*
        for (int i=0;i<8;i++)
            switch_state[i] = 0;


        Bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switch_state[0]==0) {
                    switch_state[0] = 1;
                }
                else switch_state[0] = 0;
                SwitchOnOff(1,switch_state[0]) ;
            }
        });
       */
    }

    private void ELC_Message(ETLC_Manager.ETLC_MSG msg) {
        int index = 1;
        int value;
        int i, bit, on;
        TextView tv;

        String str;


        Log.i(TAG, "ELC_Message! " + Integer.toString((int) (msg.flag & 0xff)));
        if ((int) (msg.flag & 0xff) == 0xC2) {
            Log.i(TAG, "Sensor Value Message !");
            for (i = 0; i < 4; i++) {
                if ((msg.data[0] & (1 << i)) > 0) {
                    switch (i) {
                        case 0:
                            value = msg.data[index++];
                            value = (value & 0xff) | (msg.data[index++] << 8);
                            tv = (TextView) findViewById(R.id.sensor_temper);
                            str = Float.toString((float) value / 10) + " ℃";
                            Log.i(TAG, str);
                            tv.setText(str);
                            break;
                        case 1:
                            value = msg.data[index++];
                            value = (value & 0xff) | (msg.data[index++] << 8);
                            tv = (TextView) findViewById(R.id.sensor_humidity);
                            str = Float.toString((float) value / 10) + " % RH";
                            Log.i(TAG, str);
                            tv.setText(str);
                            break;
                        case 2:
                            value = msg.data[index++];
                            value = (value & 0xff) | (msg.data[index++] << 8);
                            tv = (TextView) findViewById(R.id.sensor_co2);
                            str = Integer.toString(value) + " ppm";
                            Log.i(TAG, str);
                            tv.setText(str);
                            break;
                        case 3:
                            value = msg.data[index++];
                            value = (value & 0xff) | (msg.data[index++] << 8);
                            tv = (TextView) findViewById(R.id.sensor_tvoc);
                            str = Integer.toString(value) + " ppb";
                            Log.i(TAG, str);
                            tv.setText(str);
                            break;
                    }
                }
            }
        } else if ((int) (msg.flag & 0xff) == 0x6D) {
            value = msg.data[0] & 0xff;
            str = "Switch State (" + Integer.toHexString(value);
            for (i = 0; i < 8; i++) {
                if ((value & (1 << i)) > 0)
                    on = 1;
                else on = 0;
                str = "Switch(" + Integer.toString(i + 1) + "), On(" + Integer.toString(on) + ")";
                Log.i(TAG, str);
                switch (i) {
                    case 0:
                        //  setColorButton(R.id.switch1,on);
                        break;
                    case 1:
                        //   setColorButton(R.id.switch2,on);
                        break;
                    case 2:
                        //   setColorButton(R.id.switch3,on);
                        break;
                    case 3:
                        // setColorButton(R.id.switch4,on);
                        break;
                    case 4:
                        //   setColorButton(R.id.switch5,on);
                        break;
                    case 5:
                        //   setColorButton(R.id.switch6,on);
                        break;
                    case 6:
                        //  setColorButton(R.id.switch7,on);
                        break;
                    case 7:
                        //   setColorButton(R.id.switch8,on);
                        break;
                }
            }
        }
    }

    private void checkDeviceInfo(RLCM_Manager.RLCM_MSG msg) {
        if (rlcm_manager.my_address == 0x7f) {
            rlcm_manager.my_device_type = msg.dev_type;
            rlcm_manager.my_address = msg.addr;
        }
    }

    private void RLCM_Message(RLCM_Manager.RLCM_MSG msg) {
        int index = 1;
        int value, sw_max;
        int i, bit, state, mode, on;
        TextView tv;

        String str;
        checkDeviceInfo(msg);


        Log.i(TAG, "RLCM_Message! " + Integer.toString((int) (msg.flag & 0xff)));
        if ((int) (msg.flag & 0xff) == 0x21) {
            mode = msg.data[0] & 0xff;
            state = msg.data[1] & 0xff;
            Log.i(TAG, "State " + Integer.toString((int) (state)));   //setColorButtonMode
            Log.i(TAG, "Mode ===========================> " + Integer.toString((int) (mode)));
            if (switch_mode != mode) {
                if (switch_mode >= 1 && switch_mode <= 4) {
                    switch (switch_mode) {
                        case 1:
                            //     setColorButtonMode(R.id.switch_individual, 0);
                            break;
                        case 2:
                            //   setColorButtonMode(R.id.switch_group, 0);
                            break;
                        case 3:
                            //  setColorButtonMode(R.id.switch_scene, 0);
                            break;
                        case 4:
                            //    setColorButtonMode(R.id.switch_mixed, 0);
                            break;
                    }
                }

                switch_mode = mode;
                switch (switch_mode) {
                    case 1:
                        //    setColorButtonMode(R.id.switch_individual, 1);
                        break;
                    case 2:
                        //  setColorButtonMode(R.id.switch_group, 1);
                        break;
                    case 3:
                        //   setColorButtonMode(R.id.switch_scene, 1);
                        break;
                    case 4:
                        //  setColorButtonMode(R.id.switch_mixed, 1);
                        break;
                }
            }

            for (i = 0; i < 8 && i < msg.data_size; i++) {
                if ((state & (1 << i)) == 0)
                    on = 0;
                else on = 1;
                switch (i) {
                    case 0:
                        //   setColorButton(R.id.switch1,on);
                        break;
                    case 1:
                        // setColorButton(R.id.switch2,on);
                        break;
                    case 2:
                        //  setColorButton(R.id.switch3,on);
                        break;
                    case 3:
                        // setColorButton(R.id.switch4,on);
                        break;
                    case 4:
                        // setColorButton(R.id.switch5,on);
                        break;
                    case 5:
                        //   setColorButton(R.id.switch6,on);
                        break;
                    case 6:
                        //   setColorButton(R.id.switch7,on);
                        break;
                    case 7:
                        //   setColorButton(R.id.switch8,on);
                        break;
                }
            }
//            for (i=1;i<=8 && i<msg.data_size;i++){
////                value = msg.data[i]&0xff;
////                if (value>0)
////                    on = 1;
////                else on = 0;
////                str = "Switch(" + Integer.toString(i+1) + "), On(" + Integer.toString(on) + ")";
////                Log.i(TAG, str);
////
////            }
        } else if ((int) (msg.flag & 0xff) == 0x24) {
            Log.i(TAG, "Sensor Value Message !");
            for (i = 0; i < 4; i++) {
                if ((msg.data[0] & (1 << i)) > 0) {
                    switch (i) {
                        case 0:
                            value = msg.data[index++];
                            value = (value & 0xff) | (msg.data[index++] << 8);
                            tv = (TextView) findViewById(R.id.sensor_temper);
                            str = Float.toString((float) value / 10) + "℃"; //온도
                            Log.i(TAG, str);
                            tv.setText(str);
                            Log.e("sts_main2", "센서값 수신(온도)" + str);
                            break;
                        case 1:
                            value = msg.data[index++];
                            value = (value & 0xff) | (msg.data[index++] << 8);
                            tv = (TextView) findViewById(R.id.sensor_humidity);
                            str = Float.toString((float) value / 10) + "% RH"; //습도
                            Log.i(TAG, str);
                            tv.setText(str);
                            Log.e("sts_main2", "센서값 수신(습도)" + str);
                            break;
                        case 2:
                            value = msg.data[index++];
                            value = (value & 0xff) | (msg.data[index++] << 8);
                            tv = (TextView) findViewById(R.id.sensor_co2);
                            str = Integer.toString(value) + "ppm"; //co2
                            Log.i(TAG, str);
                            tv.setText(str);
                            Log.e("sts_main2", "센서값 수신(co2)" + str);
                            break;
                        case 3:
                            value = msg.data[index++];
                            value = (value & 0xff) | (msg.data[index++] << 8);
                            tv = (TextView) findViewById(R.id.sensor_tvoc);
                            str = Integer.toString(value) + "ppb";  //tvoc
                            Log.i(TAG, str);
                            tv.setText(str);
                            Log.e("sts_main2", "센서값 수신(tvoc" + str);
                            break;
                    }
                }
            }
        }
//       else if ((int)(msg.flag&0xff) == 0x6E) {
//            if ((msg.data[0] & 0xf0) != 0x10) return;
//            sw_max = (msg.data[0]&0xf)+1;
//            if (sw_max == 1) sw_max = 16;
//            for (i=0;i<sw_max && i<8;i++) {
//                if ((msg.data[1]&(1<<i))>0)
//                    on = 1;
//                else on = 0;
//                str = "Switch(" + Integer.toString(i+1) + "), On(" + Integer.toString(on) + ")";
//                Log.i(TAG, str);
//                switch(i) {
//                    case 0:
//                        setColorButton(1,R.id.switch1,on);
//                        break;
//                    case 1:
//                        setColorButton(2,R.id.switch2,on);
//                        break;
//                    case 2:
//                        setColorButton(3,R.id.switch3,on);
//                        break;
//                    case 3:
//                        setColorButton(4,R.id.switch4,on);
//                        break;
//                    case 4:
//                        setColorButton(5,R.id.switch5,on);
//                        break;
//                    case 5:
//                        setColorButton(6,R.id.switch6,on);
//                        break;
//                    case 6:
//                        setColorButton(7,R.id.switch7,on);
//                        break;
//                    case 7:
//                        setColorButton(8,R.id.switch8,on);
//                        break;
//                }
//            }
//
//            if (switch_state[0]==0)
//                update_seek_level(0);
//            else {
//                value = msg.data[2] & 0xff;
//                update_seek_level(value);
//            }
//        }
    }

    private final BroadcastReceiver UARTStatusChangeReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            final Intent mIntent = intent;
            //*********************//
            if (action.equals(UartService.ACTION_GATT_CONNECTED)) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                        Log.d(TAG, "UART_CONNECT_MSG");
                        btnConnectDisconnect.setText("Disconnect");
                        edtMessage.setEnabled(true);
                        btnSend.setEnabled(true);
                        ((TextView) findViewById(R.id.deviceName)).setText(mDevice.getName() + " - ready");
                        listAdapter.add("[" + currentDateTimeString + "] Connected to: " + mDevice.getName());
                        messageListView.smoothScrollToPosition(listAdapter.getCount() - 1);
                        mState = UART_PROFILE_CONNECTED;
                        mHandler.sendEmptyMessage(0);

                    }
                });
            }

            //*********************//
            if (action.equals(UartService.ACTION_GATT_DISCONNECTED)) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                        Log.d(TAG, "UART_DISCONNECT_MSG");
                        btnConnectDisconnect.setText("Connect");
                        edtMessage.setEnabled(false);
                        btnSend.setEnabled(false);
                        ((TextView) findViewById(R.id.deviceName)).setText("Not Connected");
                        listAdapter.add("[" + currentDateTimeString + "] Disconnected to: " + mDevice.getName());
                        mState = UART_PROFILE_DISCONNECTED;
                        mService.close();
                        //setUiState();

                    }
                });
                m_conn_state = 0;
            }


            //*********************//
            if (action.equals(UartService.ACTION_GATT_SERVICES_DISCOVERED)) {
                mService.enableTXNotification();
            }
//          //*********************//
//            if (action.equals(UartService.ACTION_DATA_AVAILABLE)) {
//
//                 final byte[] txValue = intent.getByteArrayExtra(UartService.EXTRA_DATA);
//                 runOnUiThread(new Runnable() {
//                     public void run() {
//                         try {
//                         	String text = new String(txValue, "UTF-8");
//                         	if (txValue.length>5) {
//                                String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
//                                listAdapter.add("[" + currentDateTimeString + "] RX: " + text);
//                                messageListView.smoothScrollToPosition(listAdapter.getCount() - 1);
//                                ETLC_Manager.ETLC_MSG msg = etlc_manager.ETLC_Analysis(txValue);
//                                Log.i(TAG, "check msg");
//                                if (msg != null) {
//                                    m_conn_state = 1;
//                                    Log.i(TAG, "Not null msg !");
//                                    ELC_Message(msg);
//                                    etlc_manager.removeETLC_Message(0);
//                                }
//                            }
//                             if (m_conn_state == 0) {
//                                 byte[] value = etlc_manager.DeviceInformationReq((byte)1,0);
//                                 Log.i(TAG, "TX: Initial Message");
//                                 mService.writeRXCharacteristic(value);
//                             }
//                         } catch (Exception e) {
//                             Log.e(TAG, e.toString());
//                         }
//                     }
//                 });
//             }
//*********************//
            if (action.equals(UartService.ACTION_DATA_AVAILABLE)) {

                final byte[] txValue = intent.getByteArrayExtra(UartService.EXTRA_DATA);
                runOnUiThread(new Runnable() {
                    public void run() {
                        try {
                            String text = new String(txValue, "UTF-8");
                            if (txValue.length > 5) {
                                String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                                listAdapter.add("[" + currentDateTimeString + "] RX: " + text);
                                messageListView.smoothScrollToPosition(listAdapter.getCount() - 1);
                                if (proto_type == 0) {
                                    ETLC_Manager.ETLC_MSG etlc_msg = etlc_manager.ETLC_Analysis(txValue);
                                    Log.i(TAG, "ELC check msg");
                                    if (etlc_msg != null) {
                                        m_conn_state = 1;
                                        Log.i(TAG, "Not null msg !");
                                        ELC_Message(etlc_msg);
                                        etlc_manager.removeETLC_Message(0);
                                        proto_type = PROTO_TYPE_ETLC;
                                    }
                                    RLCM_Manager.RLCM_MSG rlcm_msg = rlcm_manager.RLCM_Analysis(txValue);
                                    Log.i(TAG, "RLCM check msg");
                                    if (rlcm_msg != null) {
                                        m_conn_state = 1;
                                        Log.i(TAG, "Not null msg !");
                                        RLCM_Message(rlcm_msg);
                                        rlcm_manager.removeRLCM_Message(0);
                                        proto_type = PROTO_TYPE_RLCM;
                                    }
                                } else if (proto_type == PROTO_TYPE_ETLC) {
                                    ETLC_Manager.ETLC_MSG etlc_msg = etlc_manager.ETLC_Analysis(txValue);
                                    Log.i(TAG, "check msg");
                                    if (etlc_msg != null) {
                                        m_conn_state = 1;
                                        Log.i(TAG, "Not null msg !");
                                        ELC_Message(etlc_msg);
                                        etlc_manager.removeETLC_Message(0);
                                        proto_type = PROTO_TYPE_ETLC;
                                    }
                                } else if (proto_type == PROTO_TYPE_RLCM) {
                                    RLCM_Manager.RLCM_MSG rlcm_msg = rlcm_manager.RLCM_Analysis(txValue);
                                    Log.i(TAG, "check msg");
                                    if (rlcm_msg != null) {
                                        m_conn_state = 1;
                                        Log.i(TAG, "Not null msg !");
                                        RLCM_Message(rlcm_msg);
                                        rlcm_manager.removeRLCM_Message(0);
                                        proto_type = PROTO_TYPE_RLCM;
                                    }
                                }
                            }
                            if (m_conn_state == 0) {
                                byte[] value = etlc_manager.DeviceInformationReq((byte) 3, 0);
                                Log.i(TAG, "TX: Initial Message");
                                mService.writeRXCharacteristic(value);
                            }
                        } catch (Exception e) {
                            Log.e(TAG, e.toString());
                        }
                    }
                });
            }
            //*********************//
            if (action.equals(UartService.DEVICE_DOES_NOT_SUPPORT_UART)) {
                showMessage("Device doesn't support UART. Disconnecting");
                mService.disconnect();
            }


        }
    };

    private void rlcm_init() {
        rlcm_manager = new RLCM_Manager();
    }

    private void etlc_init() {
        etlc_manager = new ETLC_Manager();
    }

    class SwitchInformationTimer extends TimerTask {
        @Override
        public void run() {
//            if (mState == UART_PROFILE_CONNECTED ) {
//                if (etlc_manager.getTxEnable()) {
//
//                    byte[] value = etlc_manager.DeviceInformationReq((byte)1,0);
//                    Log.i(TAG, "TX:" + value);
//                    mService.writeRXCharacteristic(value);
//                    //Update the log with time stamp
//                    String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
//                    listAdapter.add("[" + currentDateTimeString + "] TX: " + value);
//                    messageListView.smoothScrollToPosition(listAdapter.getCount() - 1);
//                    edtMessage.setText("");
//                }
//            }
            //else mTimer.cancel();
        }
    }

    private void service_init() {
        m_conn_state = 0;
        Intent bindIntent = new Intent(this, UartService.class);
        bindService(bindIntent, mServiceConnection, Context.BIND_AUTO_CREATE);

        LocalBroadcastManager.getInstance(this).registerReceiver(UARTStatusChangeReceiver, makeGattUpdateIntentFilter());
    }

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(UartService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(UartService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(UartService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(UartService.ACTION_DATA_AVAILABLE);
        intentFilter.addAction(UartService.DEVICE_DOES_NOT_SUPPORT_UART);
        return intentFilter;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");

        try {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(UARTStatusChangeReceiver);
        } catch (Exception ignore) {
            Log.e(TAG, ignore.toString());
        }
        unbindService(mServiceConnection);
        mService.stopSelf();
        mService = null;
        m_conn_state = 0;
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        if (!mBtAdapter.isEnabled()) {
            Log.i(TAG, "onResume - BT not enabled yet");
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case REQUEST_SELECT_DEVICE:
                //When the DeviceListActivity return, with the selected device address
                if (resultCode == Activity.RESULT_OK && data != null) {
                    String deviceAddress = data.getStringExtra(BluetoothDevice.EXTRA_DEVICE);
                    mDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(deviceAddress);

                    Log.e(TAG, "... onActivityResultdevice.address==" + mDevice + "mserviceValue" + mService);
                    ((TextView) findViewById(R.id.deviceName)).setText(mDevice.getName() + " - connecting");
                    mService.connect(deviceAddress);
                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(this, "Bluetooth has turned on ", Toast.LENGTH_SHORT).show();

                } else {
                    // User did not enable Bluetooth or an error occurred
                    Log.d(TAG, "BT not enabled");
                    Toast.makeText(this, "Problem in BT Turning ON ", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                Log.e(TAG, "wrong request code");
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

    }


    private void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBackPressed() {
        if (mState == UART_PROFILE_CONNECTED) {
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
            showMessage("nRFUART's running in background.\n             Disconnect to exit");
        } else {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(R.string.popup_title)
                    .setMessage(R.string.popup_message)
                    .setPositiveButton(R.string.popup_yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton(R.string.popup_no, null)
                    .show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sts, menu);
        return true;
    }

    //액션버튼을 클릭했을때의 동작
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();

            return true;
        }

        if (id == R.id.con_geum) {

            if (!mBtAdapter.isEnabled()) {
                Log.i(TAG, "onClick - BT not enabled yet");
                Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            } else {
                if (btnConnectDisconnect.getText().equals("Connect")) {

                    //Connect button pressed, open DeviceListActivity class, with popup windows that scan for devices

                    Intent newIntent = new Intent(sts_main2.this, DeviceListActivity.class);
                    startActivityForResult(newIntent, REQUEST_SELECT_DEVICE);
                } else {
                    //Disconnect button pressed
                    if (mDevice != null) {
                        mService.disconnect();

                    }
                }
            }


            return true;
        }
        if (id == R.id.sw_name_change) {

            final AlertDialog.Builder builder = new AlertDialog.Builder(sts_main2.this);
            final EditText edittext = new EditText(sts_main2.this);
            final String aaa;


            builder.setTitle("스위치 이름 변경").setView(edittext);


            builder.setMessage("변경할 스위치 이름을 입력하세요");//세팅해서

            builder.create();//만들고


            builder.setPositiveButton("Modify", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    String strText = edittext.getText().toString();

                    getSupportActionBar().setTitle(strText); //액션바 이름

                    Toast.makeText(getApplicationContext(), strText + "로 변경됨", Toast.LENGTH_LONG).show();

                    dialog.dismiss();
                }
            });


            builder.setNegativeButton("Cencel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                }
            });

            builder.show(); //보여준다


            return true;
        }

        if (id == R.id.bt_name_change) {

            final AlertDialog.Builder builder = new AlertDialog.Builder(sts_main2.this);
            final EditText edittext = new EditText(sts_main2.this);
            final String aaa;


            builder.setTitle("버튼 이름 변경").setView(edittext);


            builder.setMessage("변경할 버튼 이름을 입력하세요");//세팅해서

            builder.create();//만들고


            builder.setPositiveButton("Modify", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    String strText = edittext.getText().toString();  //입력 받은거 저장

                    if (flag == 0) {
                        Toast.makeText(getApplicationContext(), "버튼을 먼저 선택하세요", Toast.LENGTH_LONG).show();
                    } else if (flag == 1) {
                        bt1_name.setText(strText);
                    } else if (flag == 2) {
                        bt2_name.setText(strText);
                    } else if (flag == 3) {
                        bt3_name.setText(strText);
                    } else if (flag == 4) {
                        bt4_name.setText(strText);
                    } else if (flag == 5) {
                        bt5_name.setText(strText);
                    } else if (flag == 6) {
                        bt6_name.setText(strText);
                    } else if (flag == 7) {
                        bt7_name.setText(strText);
                    } else if (flag == 8) {
                        bt8_name.setText(strText);
                    } else if (flag == 9) {
                        bt9_name.setText(strText);
                    } else if (flag == 10) {
                        bt10_name.setText(strText);
                    } else if (flag == 11) {
                        bt11_name.setText(strText);
                    } else if (flag == 12) {
                        bt12_name.setText(strText);
                    } else if (flag == 13) {
                        bt13_name.setText(strText);
                    } else if (flag == 14) {
                        bt14_name.setText(strText);
                    } else if (flag == 15) {
                        bt15_name.setText(strText);
                    } else if (flag == 16) {
                        bt16_name.setText(strText);
                    } else if (flag == 17) {
                        bt17_name.setText(strText);
                    }


                    Toast.makeText(getApplicationContext(), strText + "로 변경됨", Toast.LENGTH_LONG).show();

                    dialog.dismiss();
                }
            });


            builder.setNegativeButton("Cencel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                }
            });

            builder.show(); //보여준다


            return true;
        }

        if (id == R.id.pic_change) {
            final List<String> list = new ArrayList<>();
            final String[] items = new String[]{"사무실", "거실", "교회", "가정집", "마켓", "회의실"};
            final int[] selectedItem = {0};
            final AlertDialog.Builder builder = new AlertDialog.Builder(sts_main2.this);

            builder.setTitle("장소를 선택하세요");
            builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    selectedItem[0] = which;
                    dianum = which;


                }
            });

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {


                @RequiresApi(api = Build.VERSION_CODES.M) //버전이 맞지 않는 명령을 사용하기위해 선언
                @Override
                public void onClick(DialogInterface dialog, int which) {


                    if (dianum == 0) {
                        img.setForeground(getDrawable(R.drawable.office));
                        Log.e("이미지0:", "" + which);
                    } else if (dianum == 1) {
                        img.setForeground(getDrawable(R.drawable.living));

                    } else if (dianum == 2) {
                        img.setForeground(getDrawable(R.drawable.cris));

                    } else if (dianum == 3) {
                        img.setForeground(getDrawable(R.drawable.home));

                    } else if (dianum == 4) {
                        img.setForeground(getDrawable(R.drawable.market));

                    } else if (dianum == 5) {
                        img.setForeground(getDrawable(R.drawable.meeting));

                    }


                }
            });

            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            builder.create();
            builder.show();
            return true;
        }


        //구분

        return super.onOptionsItemSelected(item);
    }

    //액션바 숨기기
    private void hideActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();
    }


}