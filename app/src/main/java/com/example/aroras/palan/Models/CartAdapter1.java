package com.example.aroras.palan.Models;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.aroras.palan.Interface.ItemClickListener;
import com.example.aroras.palan.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class CartViewHolder1 extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txt_cart_name,txt_price;
    public ImageView img_cart_count;

    private  ItemClickListener itemClickListener;

    public void setTxt_cart_name(TextView txt_cart_name){
        this.txt_cart_name=txt_cart_name;
    }

    public CartViewHolder1(View itemView) {
        super(itemView);
        txt_cart_name=itemView.findViewById(R.id.cart_item_name);
        txt_price=itemView.findViewById(R.id.cart_item_price);
        img_cart_count=itemView.findViewById(R.id.cart_item_count);

    }

    public void onClick(View view) {

    }
}


public class CartAdapter1 extends RecyclerView.Adapter<CartViewHolder1>{

    private List<Order> listdata =new ArrayList<>();
    private Context context;

    public CartAdapter1(List<Order> listdata , Context context){
        this.listdata=listdata;
        this.context=context;
    }

    public CartViewHolder1 onCreateViewHolder( ViewGroup parent, int i) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView =inflater.inflate(R.layout.cart_layout,parent,false);
        return new CartViewHolder1(itemView);
    }

    public void onBindViewHolder( CartViewHolder1 holder, int position) {
        TextDrawable drawable=TextDrawable.builder()
                .buildRound(""+listdata.get(position).getQuantity(), Color.RED);
        holder.img_cart_count.setImageDrawable(drawable);

        Locale locale=new Locale("en","US");
        NumberFormat fmt=NumberFormat.getCurrencyInstance(locale);
        try{
            //Toast.makeText(context,listdata.get(position).getProductId().toString(), Toast.LENGTH_SHORT).show();
        int price=(Integer.parseInt(listdata.get(position).getPrice()))*(Integer.parseInt(listdata.get(position).getQuantity()));
        holder.txt_price.setText(fmt.format(price));
        holder.txt_cart_name.setText(listdata.get(position).getProductId());}
        catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }
}