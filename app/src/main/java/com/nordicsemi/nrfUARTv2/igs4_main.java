package com.nordicsemi.nrfUARTv2;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Stack;

import static android.content.ContentValues.TAG;

public class igs4_main extends AppCompatActivity {
    GridView gridView;
    EditText editText;
    LinearLayout lin;
    EditText editText2;
    EditText sw_name;
    Button button;
    String v;
    ImageView img;
   int aaa ;
    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_igs4_main);

 sw_name = findViewById(R.id.sw_name);

        sw_name = findViewById(R.id.sw_name);

       lin = findViewById(R.id.lin);
       img = findViewById(R.id.imageView);

       img.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

           }
       });














        // sw_name.setInputType(EditorInfo.TYPE_NULL);



     //   sw_name.setInputType(EditorInfo.TYPE_NULL);







        final View contentView = LayoutInflater.from(this).inflate(R.layout.activity_igs4_main, null, false);
        setContentView(contentView);

        contentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                contentView.getWindowVisibleDisplayFrame(r);
                int screenHeight = contentView.getRootView().getHeight();
                int keypadHeight = screenHeight - r.bottom;
                // 0.15 ratio is perhaps enough to determine keypad height.
                if (keypadHeight > screenHeight * 0.15) { //키보드가 열리면
                    Log.e("IGS4_MAIN:키패드 메세지", "키보드 열려똬");



                }
                else { //키보드가 닫히면
                    Log.e("IGS4_MAIN:키패드 메세지", "키보드 닫혔똬");

                   sw_name.setSelection(0);

                    lin.setFocusable(true);
                    lin.setSelected(true);
                    lin.setFocusableInTouchMode(true);
                  // lin.setSelection(1);
















                }
            }
        });







/*
        sw_name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                sw_name.setFocusable(false);
                sw_name.setFocusableInTouchMode(false);
                String inText = textView.getText().toString();
                // Do Something...
                textView.setInputType(EditorInfo.TYPE_NULL);// setCursorVisible(true); 도 가능하다.
                return true;
            }
        });
*/












        //initializing objects
        final ArrayList<product> productList = new ArrayList<>();
        gridView = (GridView) findViewById(R.id.gridView11);
        //adding some values to our list
        productList.add(new product(R.drawable.lig_off, "1번", "Red"));
        productList.add(new product(R.drawable.lig_off, "2번", "Yellow"));
        productList.add(new product(R.drawable.lig_off, "3번", "Yellow"));
        productList.add(new product(R.drawable.lig_off, "4번", "Green"));
        productList.add(new product(R.drawable.lig_off, "5번", "Brown"));
        productList.add(new product(R.drawable.lig_off, "6번", "Red"));
        productList.add(new product(R.drawable.lig_off, "7번", "Red"));
        productList.add(new product(R.drawable.lig_off, "8번", "Red"));
        productList.add(new product(R.drawable.lig_off, "9번", "Red"));
        productList.add(new product(R.drawable.lig_off, "10번", "Red"));

        //creating the adapter
        MyCustomListAdapter adapter = new MyCustomListAdapter(this, R.layout.my_custom_list, productList);
        //attaching adapter to the listview
        gridView.setAdapter(adapter);












    }
/*
    @Override
    public void onBackPressed() {
        sw_name.setFocusable(false);
        sw_name.setFocusableInTouchMode(false);

        Toast.makeText(igs4_main.this, "뒤로가기 버튼 클릭", Toast.LENGTH_SHORT).show();


    }
    */

    @Override
    protected void onResume() {
        super.onResume();




        Log.e("TAG", "resume 실행");



    }
}



