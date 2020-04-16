package com.nordicsemi.nrfUARTv2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


public class no_use  extends Activity{
    Switch aSwitch1, aSwitch2, aSwitch3, aSwitch4, aSwitch5, aSwitch6, aSwitch7, aSwitch8;
    double te, hu, co, tv;
    TextView text_temp, text_humi, text_co2, text_tvoc;
    EditText switch1, switch2, switch3, switch4, switch5, switch6, switch7, switch8;











    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sts_main);
//엑션바 title
        setTitle("IGS-TS-8 / CEO ROOM");
       // getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFffffff)); //0XFF+색상코드로 액션바 색상 변경


//온습도
        text_temp = findViewById(R.id.sensor_temper);
        text_humi = findViewById(R.id.sensor_humidity);
        text_co2 = findViewById(R.id.sensor_co2);
        text_tvoc = findViewById(R.id.sensor_tvoc);

te = 34.2;hu = 30.1;co = 0.1;tv = 0.01;
        text_temp.setText(te + "\n ℃");
        text_humi.setText(hu+"\n % RH");
        text_co2.setText(co+"\n ppm");
        text_tvoc.setText(tv+"\n ppb");
//온습도 끝

        //방이름
        switch1 = findViewById(R.id.edittext16);
        switch2= findViewById(R.id.edittext1);
        switch3 = findViewById(R.id.edittext3);
        switch4 = findViewById(R.id.edittext4);
        switch5 = findViewById(R.id.edittext5);
        switch6 = findViewById(R.id.edittext6);
        switch7 = findViewById(R.id.edittext7);
        switch8 = findViewById(R.id.edittext8);

        String bt_text = "Button";


        switch1.setText(bt_text+"1 ("+"CEO Room ALL" +")");
        switch2.setText(bt_text+"2 ("+"Desk LTG" +")");
        switch3.setText(bt_text+"3 ("+"Meeting" +")");
        switch4.setText(bt_text+"4 ("+"Saving" +")");
        switch5.setText(bt_text+"5 ("+"Power OFF" +")");
        switch6.setText(bt_text+"6 ("+"Circult #1" +")");
        switch7.setText(bt_text+"7 ("+"Circult #2" +")");
        switch8.setText(bt_text+"8 ("+"Circult #3" +")");

//방이름 끝

//시크바동작하면 text뷰에 수치 띄우기

SeekBar seekbar1 = findViewById(R.id.seekBar1);
        SeekBar seekbar2 = findViewById(R.id.seekBar2);
        SeekBar seekbar3 = findViewById(R.id.seekBar3);
        SeekBar seekbar4 = findViewById(R.id.seekBar01);
        SeekBar seekbar5 = findViewById(R.id.seekBar5);
        SeekBar seekbar6 = findViewById(R.id.seekBar6);
        SeekBar seekbar7 = findViewById(R.id.seekBar7);
        SeekBar seekbar8 = findViewById(R.id.seekBar8);
final TextView textView1 = findViewById(R.id.seek_level1);
        final TextView textView2 = findViewById(R.id.seek_level2);
        final TextView textView3 = findViewById(R.id.seek_level3);
        final TextView textView4 = findViewById(R.id.seek_level4);
        final TextView textView5 = findViewById(R.id.seek_level5);
        final TextView textView6 = findViewById(R.id.seek_level6);
        final TextView textView7 = findViewById(R.id.seek_level7);
        final TextView textView8 = findViewById(R.id.seek_level8);





seekbar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
textView1.setText(""+progress); //앞에 문자열을 더하면 toString을 안써도 String으로 변환됨
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
});

        seekbar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView2.setText(""+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekbar3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView3.setText(""+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekbar4.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView4.setText(""+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekbar5.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView5.setText(""+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekbar6.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView6.setText(""+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekbar7.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView7.setText(""+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekbar8.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView8.setText(""+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


//시크바 끝

//스위치 동작
        aSwitch1 = (Switch) findViewById(R.id.switch1);
        aSwitch2 = (Switch) findViewById(R.id.switch2);
        aSwitch3 = (Switch) findViewById(R.id.switch3);
        aSwitch4 = (Switch) findViewById(R.id.switch4);
        aSwitch5 = (Switch) findViewById(R.id.switch5);
        aSwitch6 = (Switch) findViewById(R.id.switch6);
        aSwitch7 = (Switch) findViewById(R.id.switch7);
        aSwitch8 = (Switch) findViewById(R.id.switch8);

        aSwitch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    Toast.makeText(no_use.this, switch1.getText()+" ON", Toast.LENGTH_SHORT).show();
                    //스위치 on시 action
                } else {
                    Toast.makeText(no_use.this,  switch1.getText()+" OFF", Toast.LENGTH_SHORT).show();
                    //스위치 off시 action
                }
            }
        });

        aSwitch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    Toast.makeText(no_use.this, switch2.getText()+" ON", Toast.LENGTH_SHORT).show();
                    //스위치 on시 action
                } else {
                    Toast.makeText(no_use.this,  switch2.getText()+" OFF", Toast.LENGTH_SHORT).show();
                    //스위치 off시 action
                }
            }
        });


        aSwitch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    Toast.makeText(no_use.this, switch3.getText()+" ON", Toast.LENGTH_SHORT).show();
                    //스위치 on시 action
                } else {
                    Toast.makeText(no_use.this,  switch3.getText()+" OFF", Toast.LENGTH_SHORT).show();
                    //스위치 off시 action
                }
            }
        });


        aSwitch4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    Toast.makeText(no_use.this, switch4.getText()+" ON", Toast.LENGTH_SHORT).show();
                    //스위치 on시 action
                } else {
                    Toast.makeText(no_use.this,  switch4.getText()+" OFF", Toast.LENGTH_SHORT).show();
                    //스위치 off시 action
                }
            }
        });


        aSwitch5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    Toast.makeText(no_use.this, switch5.getText()+" ON", Toast.LENGTH_SHORT).show();
                    //스위치 on시 action
                } else {
                    Toast.makeText(no_use.this,  switch5.getText()+" OFF", Toast.LENGTH_SHORT).show();
                    //스위치 off시 action
                }
            }
        });


        aSwitch6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    Toast.makeText(no_use.this, switch6.getText()+" ON", Toast.LENGTH_SHORT).show();
                    //스위치 on시 action
                } else {
                    Toast.makeText(no_use.this,  switch6.getText()+" OFF", Toast.LENGTH_SHORT).show();
                    //스위치 off시 action
                }
            }
        });


        aSwitch7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    Toast.makeText(no_use.this, switch7.getText()+" ON", Toast.LENGTH_SHORT).show();
                    //스위치 on시 action
                } else {
                    Toast.makeText(no_use.this,  switch7.getText()+" OFF", Toast.LENGTH_SHORT).show();
                    //스위치 off시 action
                }
            }
        });


        aSwitch8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    Toast.makeText(no_use.this, switch8.getText()+" ON", Toast.LENGTH_SHORT).show();
                    //스위치 on시 action
                } else {
                    Toast.makeText(no_use.this,  switch8.getText()+" OFF", Toast.LENGTH_SHORT).show();
                    //스위치 off시 action
                }
            }
        });

    }
//스위치 동작 끝


    //추가


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
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.device_scan) {

        }else if (id == R.id.test) {

            Intent intent = new Intent(this, no_use.class);
            startActivity(intent);
            finish();;



//


        }

        return super.onOptionsItemSelected(item);
    }

//추가 끝

    @Override
    protected void onPause(){
    super.onPause();
   saveState();

    }
@Override
protected void onResume(){
        super.onResume(); //어플이 중지되거나 다시 시작되면
       restoreState();

}
protected void saveState(){ //데이터를 저장
    SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
    SharedPreferences.Editor editor = pref.edit();
    editor.putString("name1",switch1.getText().toString() );
    editor.putString("name2",switch2.getText().toString() );
    editor.putString("name3",switch3.getText().toString() );
    editor.putString("name4",switch4.getText().toString() );
    editor.putString("name5",switch5.getText().toString() );
    editor.putString("name6",switch6.getText().toString() );
    editor.putString("name7",switch7.getText().toString() );
    editor.putString("name8",switch8.getText().toString() );





    editor.commit();
}

protected void restoreState(){ //다시 불러옴
        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        if((pref != null) && (pref.contains("name1"))){
            String name1 = pref.getString("name1","");
            switch1.setText(name1);
        }
    if((pref != null) && (pref.contains("name2"))){
        String name2 = pref.getString("name2","");
        switch2.setText(name2);
    }
    if((pref != null) && (pref.contains("name3"))){
        String name3 = pref.getString("name3","");
        switch3.setText(name3);
    }
    if((pref != null) && (pref.contains("name4"))){
        String name4 = pref.getString("name4","");
        switch4.setText(name4);
    }
    if((pref != null) && (pref.contains("name5"))){
        String name5 = pref.getString("name5","");
        switch5.setText(name5);
    }
    if((pref != null) && (pref.contains("name6"))){
        String name6 = pref.getString("name6","");
        switch6.setText(name6);
    }
    if((pref != null) && (pref.contains("name7"))){
        String name7 = pref.getString("name7","");
        switch7.setText(name7);
    }
    if((pref != null) && (pref.contains("name8"))){
        String name8 = pref.getString("name8","");
        switch8.setText(name8);
    }

}
protected void clearMyPrefs(){  //저장소 삭제 메서드 (사용 안함)
    SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
    SharedPreferences.Editor editor = pref.edit();
    editor.clear();
    editor.commit();
}


}
