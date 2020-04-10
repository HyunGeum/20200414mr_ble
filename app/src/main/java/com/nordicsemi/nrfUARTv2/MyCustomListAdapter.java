package com.nordicsemi.nrfUARTv2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;



public class MyCustomListAdapter extends ArrayAdapter<product> {

    public static final String TAG = "hahaha";
    List<product> productList;
    Context mCtx;
    int resource;

    public MyCustomListAdapter(Context mCtx, int resource, ArrayList<product> productList) {
        super(mCtx, resource, productList);
        this.mCtx = mCtx;
        this.resource = resource;
        this.productList = productList;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //we need to get the view of the xml for our list item
        //And for this we need a layoutinflater
        LayoutInflater layoutInflater = LayoutInflater.from(mCtx);

        //getting the view
        View view = layoutInflater.inflate(resource, null, false);

        //getting the view elements of the list from the view
        final LinearLayout imageView = view.findViewById(R.id.imageView);
        TextView textViewName = view.findViewById(R.id.textViewName);
        TextView textViewColor = view.findViewById(R.id.textViewColor);
       // Button buttonDelete = view.findViewById(R.id.buttonDelete);

        //getting the hero of the specified position
        final product product = productList.get(position);

        //adding values to the list item
       // imageView.setImageDrawable(mCtx.getResources().getDrawable(fruit.getImage()));
        imageView.setBackground(mCtx.getResources().getDrawable(product.getImage()));
        textViewName.setText(product.getName());
        textViewColor.setText(product.getColor());

        //adding a click listener to the button to remove item from the list
      /*  buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //we will call this method to remove the selected value from the list
                //we are passing the position which is to be removed in the method
                removeFruit(position);
            }
        });
*/

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //we will call this method to remove the selected value from the list
                //we are passing the position which is to be removed in the method
              //  removeFruit(position);

              //  fruitList.get(position);

                if (position == 0){


                  //  imageView.setBackground(mCtx.getResources().getDrawable(R.drawable.lig_on));
                    Log.e(TAG, "MCTX:" + mCtx);
                    Log.e(TAG,"position:"+position);

                //    imageView.setBackground(mCtx.getResources().getDrawable(R.drawable.lig_on));



                }
                if (position == 1){

                    //  imageView.setBackground(mCtx.getResources().getDrawable(R.drawable.lig_on));
                    Log.e(TAG,"MCTX:"+mCtx);
                    Log.e(TAG,"position:"+position);

                //    productList.set(0, mCtx);
                    Log.e(TAG,"product:"+product);
                 //   imageView.setBackground(mCtx.(R.drawable.lig_on));


int a = R.drawable.lig_on;


  //  product(int image, String name, String color)

                            // 아이템 수정
                            productList.remove(position);
                    Log.e(TAG,"a:"+a);

                    //productList.set(position,product(R.drawable.lig_on,"ff","ff"));
                 //   productList.add(new product(R.drawable.lig_off, "Strawberry", "Red"));
                  //  productList.set(0R.drawable.lig_on);



                        }

                //삭제
               // fruitList.remove(position);
               // fruitList.add(position);
                //reloading the list
                notifyDataSetChanged();


            }
        });



        //finally returning the view
        return view;
    }

    private void removeFruit(final int position) {
        //Creating an alert dialog to confirm the deletion
        AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
        builder.setTitle("Are you sure you want to delete this?");

        //if the response is positive in the alert
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //removing the item
                productList.remove(position);

                //reloading the list
                notifyDataSetChanged();
            }
        });

        //if response is negative nothing is being done
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        //creating and displaying the alert dialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}

