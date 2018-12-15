package com.example.aroras.palan.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aroras.palan.Database.Database;
import com.example.aroras.palan.Models.CartAdapter1;
import com.example.aroras.palan.Models.Order;
import com.example.aroras.palan.Models.Requests;
import com.example.aroras.palan.R;
import com.example.aroras.palan.Models.common;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Cart extends AppCompatActivity{

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;

    TextView txtTotalPrice;
    Button btnPlace;

    List<Order> cart =new ArrayList<>();
    CartAdapter1 adapter;

    private String totalprice;
    //public static String finaltotal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cart);

        //Firebase
        database=FirebaseDatabase.getInstance();
        requests=database.getReference("Requests");

        recyclerView =(RecyclerView)findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice=(TextView)findViewById(R.id.total);
        btnPlace=(Button)findViewById(R.id.btnPlaceOrder);

        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create new request
                showAlertDialog();
            }
        });

        loadListItem();
    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog =new AlertDialog.Builder(Cart.this);
        alertDialog.setTitle("One more step !");
        alertDialog.setMessage("Enter your Address");

        final EditText edtAddress= new EditText(Cart.this);
        LinearLayout.LayoutParams lp =new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        edtAddress.setLayoutParams(lp);
        alertDialog.setView(edtAddress);
        alertDialog.setIcon(R.drawable.icon_shopping_cart);

        alertDialog.setPositiveButton("YES",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Requests request =new Requests(
                        common.currentUser.getPhone(),
                        common.currentUser.getName(),
                        edtAddress.getText().toString(),
                        txtTotalPrice.getText().toString(),
                        cart
                );

                //Submit to Firebase
                requests.child(String.valueOf(System.currentTimeMillis())).setValue(request);
                new Database(getBaseContext()).cleanCart();

                //var pos=(position+1).toString();
                totalprice=txtTotalPrice.getText().toString();
                Intent instamojo =new Intent(getBaseContext(),InstamojoActivity.class);
                instamojo.putExtra("TotalPrice",totalprice);
                startActivity(instamojo);
                finish();
            }
        });

       alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               dialog.dismiss();
           }
       });

       alertDialog.show();

    }

    private void loadListItem() {

        cart =new Database(this).getCarts();
        adapter=new CartAdapter1(cart,this);
        recyclerView.setAdapter(adapter);

        //calculate total price
        int total=0;
        for(Order order:cart)
        {
            try{
            total+=(Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));
            //finaltotal=Integer.toString(total);
        }catch (NumberFormatException e) {
            e.printStackTrace();
        }
        }
        Locale locale=new Locale("en","US");
        NumberFormat fmt =NumberFormat.getCurrencyInstance(locale);

        txtTotalPrice.setText(fmt.format(total));
    }
}