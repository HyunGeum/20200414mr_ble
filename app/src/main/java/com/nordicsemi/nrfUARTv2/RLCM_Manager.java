package com.nordicsemi.nrfUARTv2;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class RLCM_Manager {
    public static final String TAG = "nRFUART";
    public class RLCM_MSG {
        byte  gateway;
        byte  dev_type;
        byte  addr;
        byte  saddr;
        byte  flag;
        byte[]  data;
        byte  data_size;
    }

    private List<RLCM_MSG> array_RLCM_msg = new ArrayList<RLCM_MSG>();
    public byte my_address= 0x7f;
    public byte my_device_type= 0x3f;
    public  boolean switch_tx_enable=false;
    private int seq_index,seq_step;
    private byte[] array_RLCM_data = new byte[50];

    public RLCM_Manager()
    {
        seq_index = 0;
        switch_tx_enable = false;
    }

    private RLCM_MSG getRLCM_Message()
    {
        RLCM_MSG msg = null;
        if (array_RLCM_msg.size()>0) {
            msg = array_RLCM_msg.get(0);
        }
        return msg;
    }

    public RLCM_MSG createRLCM_MSG()
    {
        RLCM_MSG msg = new RLCM_MSG();
        msg.gateway = 0;
        msg.addr = my_address;
        msg.dev_type = my_device_type;
        return msg;
    }


    public void removeRLCM_Message(int idx)
    {
        if (array_RLCM_msg.size()>0) {
            if (idx == -1)
                array_RLCM_msg.clear();
            else {
                if (array_RLCM_msg.size()>idx) {
                    array_RLCM_msg.remove(idx);
                    Log.i(TAG, "remove MSG at " + Integer.toString(idx) + " !");
                }
            }
        }
    }

    int seq_char;
    byte seq_byte,seq_even,chksum; //,seq_enable
    private byte getNibble(byte ch)
    {
        byte nibble=0;
        if (ch>='0' && ch<='9')
            nibble = (byte)(ch-'0');
        else if (ch>='A' && ch<='F')
            nibble = (byte)(ch-'A'+10);
        return nibble;
    }

    String ascii_string = "0123456789ABCDEF";
    byte[] ascii=ascii_string.getBytes();
    private byte NibbleToAscii(byte ch)
    {
        return ascii[ch&0xf];
    }

    public boolean getTxEnable()
    {
        return switch_tx_enable;
    }

    public RLCM_MSG RLCM_Analysis(byte[] sdata)
    {
        int n;
        char ch;
        String str1,str2;
         for (int i=0;i<sdata.length;i++) {
             seq_char = sdata[i]&0xff;
             if (seq_char == 0xd) {
                 if (seq_index>=5) {
                     chksum = 0;
                     for (n = 0; n < (seq_index - 1); n++) {
                         chksum += array_RLCM_data[n];
                     }
                     chksum += (byte)0x80;
                     chksum = (byte) ((~chksum) + 1);
                     str1 = Byte.toString(chksum);
                     str2 = Byte.toString(array_RLCM_data[seq_index - 1]);
                     Log.i(TAG, "compare => " + str1 + "," + str2);
                     if (chksum == array_RLCM_data[seq_index - 1]) {
                         switch_tx_enable = true;
                         if ((array_RLCM_data[0]&0xff) == 0x10) {
                            //
                         }
                         else if ((array_RLCM_data[0]&0xff) == 0xE2) {
                             RLCM_MSG msg = new RLCM_MSG();
                             msg.data = new byte[40];
                             msg.gateway = array_RLCM_data[1];
                             msg.dev_type = (byte)(array_RLCM_data[2] & 0x3F);
                             msg.addr = (byte)(array_RLCM_data[3] & 0x7F);
                             msg.saddr = array_RLCM_data[4];
                             msg.flag = array_RLCM_data[5];

                             for (n = 0; n < (seq_index - 6); n++)
                                 msg.data[n] = array_RLCM_data[n + 6];
                             msg.data_size = (byte) (seq_index - 7);
                             array_RLCM_msg.add(msg);
                             if (my_address != msg.addr) {
                                 my_address = msg.addr;
                             }
                             if (my_device_type != msg.dev_type) {
                                 my_device_type = (byte)msg.dev_type;
                             }
                         }
                     }
                 }
                 seq_index =0;
                 seq_even = 0;
                 seq_byte = 0;
                 seq_char = 0;
             }
             else { //if (seq_enable!=0) {

                 if ((seq_even&1) == 0)
                     seq_byte = getNibble((byte)(seq_char&0xff));
                 else {
                     seq_byte = (byte)(seq_byte<<4);
                     seq_byte |= getNibble((byte)(seq_char&0xff));
                     if (seq_index<50) {
                         array_RLCM_data[seq_index++] = seq_byte;
                         Log.i(TAG, "data["+Integer.toString(seq_index-1) + ": " + Integer.toString(array_RLCM_data[seq_index-1]&0xff));
                     }
                 }
                 seq_even++;
             }
         }

         return getRLCM_Message();
    }

    public byte[] RLCM_To_Byte(RLCM_MSG msg)
    {
        byte length = 0,chk,ch,i;
        //length = 0xec + head + data + checksum + 0xd
        byte[] ble_array = new byte[(6 + msg.data_size + 1)*2 + 1];
        chk = (byte)0xE2;
        ble_array[length++] = NibbleToAscii((byte)0xE);
        ble_array[length++] = NibbleToAscii((byte)0x2);
        ch = (byte)msg.gateway;
        chk += ch;
        ble_array[length++] = NibbleToAscii((byte)(ch>>4));
        ble_array[length++] = NibbleToAscii((byte)(ch&0xf));
        ch = (byte)msg.dev_type;
        chk += ch;
        ble_array[length++] = NibbleToAscii((byte)(ch>>4));
        ble_array[length++] = NibbleToAscii((byte)(ch&0xf));
        ch = (byte)(msg.addr);
        chk += ch;
        ble_array[length++] = NibbleToAscii((byte)(ch>>4));
        ble_array[length++] = NibbleToAscii((byte)(ch&0xf));
        ch = (byte)(msg.saddr);
        chk += ch;
        ble_array[length++] = NibbleToAscii((byte)(ch>>4));
        ble_array[length++] = NibbleToAscii((byte)(ch&0xf));
        ch = (byte)(msg.flag);
        chk += ch;
        ble_array[length++] = NibbleToAscii((byte)(ch>>4));
        ble_array[length++] = NibbleToAscii((byte)(ch&0xf));

        for (i=0;i<msg.data_size;i++) {
            ch = (byte)(msg.data[i]);
            chk += ch;
            ble_array[length++] = NibbleToAscii((byte)(ch>>4));
            ble_array[length++] = NibbleToAscii((byte)(ch&0xf));
        }
        chk += (byte)0x80;
        chk = (byte) ((~chk) + 1);
        ble_array[length++] = NibbleToAscii((byte)(chk>>4));
        ble_array[length++] = NibbleToAscii((byte)(chk&0xf));
        ble_array[length++] = 0xd;
        return ble_array;
    }

}
