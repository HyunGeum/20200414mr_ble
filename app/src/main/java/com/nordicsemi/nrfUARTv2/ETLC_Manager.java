package com.nordicsemi.nrfUARTv2;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ETLC_Manager {
    public static final String TAG = "nRFUART";
    public class ETLC_MSG {
        short dev_type;
        byte  scu_num;
        short addr;       // 0~1023
        byte  flag;
        byte  saddr;      // 0~63
        byte[]  data;
        byte  data_size;
    }

    private List<ETLC_MSG> array_etlc_msg = new ArrayList<ETLC_MSG>();
    //private ETLC_MSG etlc_msg;
    private short my_address= (short)0xffff;
    private byte my_device_type= (byte)0xff;
    public  boolean switch_tx_enable=false;
    private int seq_index,seq_step;
    private byte[] array_etlc_data = new byte[50];
    public ETLC_Manager()
    {
        seq_index = 0;
        switch_tx_enable = false;
    }

    private ETLC_MSG getETLC_Message()
    {
        ETLC_MSG msg = null;
        if (array_etlc_msg.size()>0) {
            msg = array_etlc_msg.get(0);
        }
        return msg;
    }

    public ETLC_MSG createETLC_MSG()
    {
        ETLC_MSG msg = new ETLC_MSG();
        msg.addr = my_address;
        msg.dev_type = my_device_type;
        return msg;
    }


    public void removeETLC_Message(int idx)
    {
        if (array_etlc_msg.size()>0) {
            if (idx == -1)
                array_etlc_msg.clear();
            else {
                if (array_etlc_msg.size()>idx) {
                    array_etlc_msg.remove(idx);
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

    public ETLC_MSG ETLC_Analysis(byte[] sdata)
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
                         chksum += array_etlc_data[n];
                     }
                     chksum += (byte)0x80;
                     chksum = (byte) ((~chksum) + 1);
                     str1 = Byte.toString(chksum);
                     str2 = Byte.toString(array_etlc_data[seq_index - 1]);
                     Log.i(TAG, "compare => " + str1 + "," + str2);
                     if (chksum == array_etlc_data[seq_index - 1]) {
                         switch_tx_enable = true;
                         if ((array_etlc_data[0]&0xff) == 0x10) {
                            //
                         }
                         else if ((array_etlc_data[0]&0xff) == 0xEC) {
                             ETLC_MSG msg = new ETLC_MSG();
                             msg.data = new byte[40];

                             msg.dev_type = array_etlc_data[1];
                             msg.scu_num = (byte) (array_etlc_data[2] >> 2);
                             msg.addr = (byte) (array_etlc_data[2] & 3);
                             msg.addr <<= 8;
                             msg.addr += array_etlc_data[3] & 0xff;
                             msg.saddr = array_etlc_data[4];
                             msg.flag = array_etlc_data[5];

                             for (n = 0; n < (seq_index - 6); n++)
                                 msg.data[n] = array_etlc_data[n + 6];
                             msg.data_size = (byte) (seq_index - 7);
                             array_etlc_msg.add(msg);
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
                         array_etlc_data[seq_index++] = seq_byte;
                         Log.i(TAG, "data["+Integer.toString(seq_index-1) + ": " + Integer.toString(array_etlc_data[seq_index-1]&0xff));
                     }
                 }
                 seq_even++;
             }
         }

         return getETLC_Message();
    }

    public byte[] ELC_To_Byte(ETLC_MSG msg)
    {
        byte length = 0,chk,ch,i;
        //length = 0xec + head + data + checksum + 0xd
        byte[] ble_array = new byte[(6 + msg.data_size + 1)*2 + 1];
        chk = (byte)0xEC;
        ble_array[length++] = NibbleToAscii((byte)0xE);
        ble_array[length++] = NibbleToAscii((byte)0xC);
        ch = (byte)msg.dev_type;
        chk += ch;
        ble_array[length++] = NibbleToAscii((byte)(ch>>4));
        ble_array[length++] = NibbleToAscii((byte)(ch&0xf));
        ch = (byte)((msg.addr>>8)&0x3);
        chk += ch;
        ble_array[length++] = NibbleToAscii((byte)(ch>>4));
        ble_array[length++] = NibbleToAscii((byte)(ch&0xf));
        ch = (byte)(msg.addr&0xff);
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

    public byte[] DeviceInformationReq(byte info, int password)
    {
        byte length = 0,chk,ch;
        byte[] ble_array = new byte[13];
        chk = (byte)0x10;
        ble_array[length++] = NibbleToAscii((byte)0x1);
        ble_array[length++] = NibbleToAscii((byte)0x0);
        chk += info;
        ble_array[length++] = NibbleToAscii((byte)(info>>4));
        ble_array[length++] = NibbleToAscii((byte)(info&0xf));
        ch = (byte)(password&0xff);
        chk += ch;
        ble_array[length++] = NibbleToAscii((byte)(ch>>4));
        ble_array[length++] = NibbleToAscii((byte)(ch&0xf));
        ch = (byte)((password>>8)&0xff);
        chk += ch;
        ble_array[length++] = NibbleToAscii((byte)(ch>>4));
        ble_array[length++] = NibbleToAscii((byte)(ch&0xf));
        ch = (byte)((password>>16)&0xff);
        chk += ch;
        ble_array[length++] = NibbleToAscii((byte)(ch>>4));
        ble_array[length++] = NibbleToAscii((byte)(ch&0xf));
        chk += (byte)0x80;
        chk = (byte) ((~chk) + 1);
        ble_array[length++] = NibbleToAscii((byte)(chk>>4));
        ble_array[length++] = NibbleToAscii((byte)(chk&0xf));
        ble_array[length++] = 0xd;
        return ble_array;
    }
}
