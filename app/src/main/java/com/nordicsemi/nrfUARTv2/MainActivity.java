package com.nordicsemi.nrfUARTv2;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity  extends Activity {
    private String TAG = "MainActivity";
    Button ts4, ts8, sts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button Ts4, Ts8, Sts;


       Ts4 = findViewById(R.id.ts4);
        Ts8 = findViewById(R.id.ts8);
        Sts = findViewById(R.id.sts);

        Ts4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, igs4_main.class);
                startActivity(intent);
            }
        });

        Ts8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, igs8_main.class);
                startActivity(intent);
            }
        });

        Sts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, sts_main2.class);
                startActivity(intent);
            }
        });







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
}
