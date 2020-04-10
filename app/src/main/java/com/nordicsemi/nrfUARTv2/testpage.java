
/*
 * Copyright (c) 2015, Nordic Semiconductor
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
 * USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.nordicsemi.nrfUARTv2;




import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


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
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;


public class testpage extends Activity implements RadioGroup.OnCheckedChangeListener {
    private static final int REQUEST_SELECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    private static final int UART_PROFILE_READY = 10;
    public static final String TAG = "nRFUART";
    private static final int UART_PROFILE_CONNECTED = 20;
    private static final int UART_PROFILE_DISCONNECTED = 21;
    private static final int STATE_OFF = 10;

    TextView mRemoteRssiVal;
    RadioGroup mRg;
    private int mState = UART_PROFILE_DISCONNECTED;
    private UartService mService = null;
    private BluetoothDevice mDevice = null;
    private BluetoothAdapter mBtAdapter = null;
    private ListView messageListView;
    private ArrayAdapter<String> listAdapter;
    private Button btnConnectDisconnect,btnSend;
    private Button sw1,sw2,sw3,sw4,sw5,sw6,sw7,sw8;
    private EditText edtMessage;
    private Timer mTimer;
    private ETLC_Manager etlc_manager;
    private int m_conn_state=0;
    private int text_on_color = Color.parseColor("#EE4500");
    private int text_off_color = Color.parseColor("#000000");
    private byte[] switch_state = new byte[8];

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);






        etlc_init();
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBtAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        messageListView = (ListView) findViewById(R.id.listMessage);
        listAdapter = new ArrayAdapter<String>(this, R.layout.message_detail);
        messageListView.setAdapter(listAdapter);
        messageListView.setDivider(null);
        btnConnectDisconnect=(Button) findViewById(R.id.btn_select);
        btnSend=(Button) findViewById(R.id.sendButton);
        edtMessage = (EditText) findViewById(R.id.sendText);
        service_init();
        switch_init();
        // Handle Disconnect & Connect button
        btnConnectDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mBtAdapter.isEnabled()) {
                    Log.i(TAG, "onClick - BT not enabled yet");
                    Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
                }
                else {
                	if (btnConnectDisconnect.getText().equals("Connect")){
                		
                		//Connect button pressed, open DeviceListActivity class, with popup windows that scan for devices
                		
            			Intent newIntent = new Intent(testpage.this, DeviceListActivity.class);
            			startActivityForResult(newIntent, REQUEST_SELECT_DEVICE);
        			} else {
        				//Disconnect button pressed
        				if (mDevice!=null)
        				{
        					mService.disconnect();
        					
        				}
        			}
                }
            }
        });
        // Handle Send button
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	EditText editText = (EditText) findViewById(R.id.sendText);
            	String message = editText.getText().toString();
            	byte[] value=null;
				try {
					//send data to service
					value = message.getBytes("UTF-8");
                    //???????value = etlc_manager.DeviceInformationReq((byte)1,0);
                    Log.i(TAG, "TX:" + value);
					mService.writeRXCharacteristic(value);
					//Update the log with time stamp
					String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
					listAdapter.add("["+currentDateTimeString+"] TX: "+ message);
               	 	messageListView.smoothScrollToPosition(listAdapter.getCount() - 1);
               	 	edtMessage.setText("");

				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
            }
        });

        SeekBar sb  = (SeekBar) findViewById(R.id.seekBar);

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar seekBar) {
                update_seek_level(true);
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                update_seek_level(false);
            }

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) { update_seek_level(false); }
        });

        // Set initial UI state
//        mTimer = new Timer();
//        mTimer.schedule(new SwitchInformationTimer(), 0, 1000);
    }




    private void update_seek_level(int level)
    {
        int value;
        TextView tv = (TextView) findViewById(R.id.seek_level);
        SeekBar sb  = (SeekBar) findViewById(R.id.seekBar);
        value = level * 100 / 254;
        tv.setText(Integer.toString(value));
        sb.setProgress(value);
    }

    private void update_seek_level(boolean bSend)
    {
        TextView tv = (TextView) findViewById(R.id.seek_level);
        SeekBar sb  = (SeekBar) findViewById(R.id.seekBar);
        int value = sb.getProgress();
        tv.setText(Integer.toString(value));
        if (bSend) {
            value = value * 254 /100;
            SwitchOnOffLevel(1,value);
        }

    }

    //UART service connected/disconnected
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder rawBinder) {
        		mService = ((UartService.LocalBinder) rawBinder).getService();
        		Log.d(TAG, "onServiceConnected mService= " + mService);
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

    private void setColorButton(int no, int id,int on)
    {
        int textColor = text_off_color;
        Button btn= (Button)findViewById(id);
        if (on!=0) {
            textColor = text_on_color;
            switch_state[no-1] = 1;
        }
        else {
            switch_state[no-1] = 0;
        }
        btn.setTextColor(textColor);
    }

    private void SwitchOnOffLevel(int no, int level)
    {
        if (m_conn_state == 0) return;
        ETLC_Manager.ETLC_MSG msg = etlc_manager.createETLC_MSG();

        msg.saddr = (byte)no;
        msg.flag = 0x65;
        msg.data = new byte[2];
        msg.data[0] = 4;
        if (level>=255) level = 254;
        msg.data[1] = (byte)(level&0xff);
        msg.data_size = 2;
        byte[] value = etlc_manager.ELC_To_Byte(msg);

//        Log.i(TAG, "TX: Switch ("+ Integer.toString(on) + ") " + Byte.toString(value[0]));
        mService.writeRXCharacteristic(value);
    }

    private void SwitchOnOff(int no, int on)
    {
        if (m_conn_state == 0) return;
        ETLC_Manager.ETLC_MSG msg = etlc_manager.createETLC_MSG();

        msg.saddr = (byte)no;
        msg.flag = 0x65;
        msg.data = new byte[1];
        msg.data[0] = (byte)on;
        msg.data_size = 1;
        byte[] value = etlc_manager.ELC_To_Byte(msg);

        Log.i(TAG, "TX: Switch ("+ Integer.toString(on) + ") " + Byte.toString(value[0]));
        mService.writeRXCharacteristic(value);
    }

    private void switch_init()
    {
        for (int i=0;i<8;i++)
            switch_state[i] = 0;
        sw1 = (Button)findViewById(R.id.switch1);
        sw2 = (Button)findViewById(R.id.switch2);
        sw3 = (Button)findViewById(R.id.switch3);
        sw4 = (Button)findViewById(R.id.switch4);
        sw5 = (Button)findViewById(R.id.switch5);
        sw6 = (Button)findViewById(R.id.switch6);
        sw7 = (Button)findViewById(R.id.switch7);
        sw8 = (Button)findViewById(R.id.switch8);

        sw1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switch_state[0]==0) {
                    switch_state[0] = 1;
                }
                else switch_state[0] = 0;
                SwitchOnOff(1,switch_state[0]) ;
            }
        });
        sw2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switch_state[1]==0) {
                    switch_state[1] = 1;
                }
                else switch_state[1] = 0;
                SwitchOnOff(2,switch_state[1]) ;
            }
        });
        sw3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switch_state[2]==0) {
                    switch_state[2] = 1;
                }
                else switch_state[2] = 0;
                SwitchOnOff(3,switch_state[2]) ;
            }
        });
        sw4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switch_state[3]==0) {
                    switch_state[3] = 1;
                }
                else switch_state[3] = 0;
                SwitchOnOff(4,switch_state[3]) ;
            }
        });
        sw5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switch_state[4]==0) {
                    switch_state[4] = 1;
                }
                else switch_state[4] = 0;
                SwitchOnOff(5,switch_state[4]) ;
            }
        });
        sw6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switch_state[5]==0) {
                    switch_state[5] = 1;
                }
                else switch_state[5] = 0;
                SwitchOnOff(6,switch_state[5]) ;
            }
        });
        sw7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switch_state[6]==0) {
                    switch_state[6] = 1;
                }
                else switch_state[6] = 0;
                SwitchOnOff(7,switch_state[6]) ;
            }
        });
        sw8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (switch_state[7]==0) {
                    switch_state[7] = 1;
                }
                else switch_state[7] = 0;
                SwitchOnOff(8,switch_state[7]) ;
            }
        });
    }

    private void ELC_Message(ETLC_Manager.ETLC_MSG msg)
    {
        int index = 1;
        int value,sw_max;
        int i,bit,on;
        TextView tv;
        String str;

        Log.i(TAG, "ELC_Message! " + Integer.toString((int)(msg.flag&0xff)));
        if ((int)(msg.flag&0xff) == 0xC2) {
            Log.i(TAG, "Sensor Value Message !");
            for (i=0;i<4;i++) {
                if ((msg.data[0]&(1<<i))>0) {
                    switch(i)
                    {
                        case 0:
                            value = msg.data[index++];
                            value = (value &0xff) | (msg.data[index++]<<8);
                            tv = (TextView) findViewById(R.id.sensor_temper);
                            str = "온도: " + Float.toString((float)value/10) + "°";
                            Log.i(TAG, str);
                            tv.setText(str);
                            break;
                        case 1:
                            value = msg.data[index++];
                            value = (value &0xff) | (msg.data[index++]<<8);
                            tv = (TextView) findViewById(R.id.sensor_humidity);
                            str = "습도: " + Float.toString((float)value/10) + "RH";
                            Log.i(TAG, str);
                            tv.setText(str);
                            break;
                        case 2:
                            value = msg.data[index++];
                            value = (value &0xff) | (msg.data[index++]<<8);
                            tv = (TextView) findViewById(R.id.sensor_co2);
                            str = "CO2: " + Integer.toString(value) + "ppm";
                            Log.i(TAG, str);
                            tv.setText(str);
                            break;
                        case 3:
                            value = msg.data[index++];
                            value = (value &0xff) | (msg.data[index++]<<8);
                            tv = (TextView) findViewById(R.id.sensor_tvoc);
                            str = "TVOC: " + Integer.toString(value) + "ppb";
                            Log.i(TAG, str);
                            tv.setText(str);
                            break;
                    }
                }
            }
        }
        else if ((int)(msg.flag&0xff) == 0x6D) {
            value = msg.data[0]&0xff;
            str = "Switch State (" + Integer.toHexString(value);
            for (i=0;i<8;i++){
                if ((value&(1<<i))>0)
                    on = 1;
                else on = 0;
                str = "Switch(" + Integer.toString(i+1) + "), On(" + Integer.toString(on) + ")";
                Log.i(TAG, str);
                switch(i) {
                    case 0:
                        setColorButton(1,R.id.switch1,on);
                        break;
                    case 1:
                        setColorButton(2,R.id.switch2,on);
                        break;
                    case 2:
                        setColorButton(3,R.id.switch3,on);
                        break;
                    case 3:
                        setColorButton(4,R.id.switch4,on);
                        break;
                    case 4:
                        setColorButton(5,R.id.switch5,on);
                        break;
                    case 5:
                        setColorButton(6,R.id.switch6,on);
                        break;
                    case 6:
                        setColorButton(7,R.id.switch7,on);
                        break;
                    case 7:
                        setColorButton(8,R.id.switch8,on);
                        break;
                }
            }
        }
        else if ((int)(msg.flag&0xff) == 0x6E) {
            if ((msg.data[0] & 0xf0) != 0x10) return;
            sw_max = (msg.data[0]&0xf)+1;
            if (sw_max == 1) sw_max = 16;
            for (i=0;i<sw_max && i<8;i++) {
                if ((msg.data[1]&(1<<i))>0)
                    on = 1;
                else on = 0;
                str = "Switch(" + Integer.toString(i+1) + "), On(" + Integer.toString(on) + ")";
                Log.i(TAG, str);
                switch(i) {
                    case 0:
                        setColorButton(1,R.id.switch1,on);
                        break;
                    case 1:
                        setColorButton(2,R.id.switch2,on);
                        break;
                    case 2:
                        setColorButton(3,R.id.switch3,on);
                        break;
                    case 3:
                        setColorButton(4,R.id.switch4,on);
                        break;
                    case 4:
                        setColorButton(5,R.id.switch5,on);
                        break;
                    case 5:
                        setColorButton(6,R.id.switch6,on);
                        break;
                    case 6:
                        setColorButton(7,R.id.switch7,on);
                        break;
                    case 7:
                        setColorButton(8,R.id.switch8,on);
                        break;
                }
            }

            if (switch_state[0]==0)
                update_seek_level(0);
            else {
                value = msg.data[2] & 0xff;
                update_seek_level(value);
            }
        }
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
                             ((TextView) findViewById(R.id.deviceName)).setText(mDevice.getName()+ " - ready");
                             listAdapter.add("["+currentDateTimeString+"] Connected to: "+ mDevice.getName());
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
                             listAdapter.add("["+currentDateTimeString+"] Disconnected to: "+ mDevice.getName());
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
          //*********************//
            if (action.equals(UartService.ACTION_DATA_AVAILABLE)) {
              
                 final byte[] txValue = intent.getByteArrayExtra(UartService.EXTRA_DATA);
                 runOnUiThread(new Runnable() {
                     public void run() {
                         try {
                         	String text = new String(txValue, "UTF-8");
                         	if (txValue.length>5) {
                                String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                                listAdapter.add("[" + currentDateTimeString + "] RX: " + text);
                                messageListView.smoothScrollToPosition(listAdapter.getCount() - 1);
                                ETLC_Manager.ETLC_MSG msg = etlc_manager.ETLC_Analysis(txValue);
                                Log.i(TAG, "check msg");
                                if (msg != null) {
                                    m_conn_state = 1;
                                    Log.i(TAG, "Not null msg !");
                                    ELC_Message(msg);
                                    etlc_manager.removeETLC_Message(0);
                                }
                            }
                            if (m_conn_state == 0) {
                                 byte[] value = etlc_manager.DeviceInformationReq((byte)3,0);
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
            if (action.equals(UartService.DEVICE_DOES_NOT_SUPPORT_UART)){
            	showMessage("Device doesn't support UART. Disconnecting");
            	mService.disconnect();
            }
        }
    };

    private void etlc_init() {
        etlc_manager =  new ETLC_Manager();
    }

    class SwitchInformationTimer extends TimerTask {
        @Override
        public void run() {
            if (mState == UART_PROFILE_CONNECTED ) {
//                if (etlc_manager.getTxEnable()) {
//
                    byte[] value = etlc_manager.DeviceInformationReq((byte)1,0);
                    Log.i(TAG, "TX:" + value);
                    mService.writeRXCharacteristic(value);
                    //Update the log with time stamp
                    String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                    listAdapter.add("[" + currentDateTimeString + "] TX: " + value);
                    messageListView.smoothScrollToPosition(listAdapter.getCount() - 1);
                    edtMessage.setText("");
//                }
            }
            //else mTimer.cancel();
        }
    }

    private void service_init() {
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
        mService= null;
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
               
                Log.d(TAG, "... onActivityResultdevice.address==" + mDevice + "mserviceValue" + mService);
                ((TextView) findViewById(R.id.deviceName)).setText(mDevice.getName()+ " - connecting");
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
        }
        else {
            new AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle(R.string.popup_title)
            .setMessage(R.string.popup_message)
            .setPositiveButton(R.string.popup_yes, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
   	                finish();
                }
            })
            .setNegativeButton(R.string.popup_no, null)
            .show();
        }
    }
    public void On_test(View view){
        Intent intent = new Intent(this, no_use.class);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long


        return super.onOptionsItemSelected(item);
    }

}
