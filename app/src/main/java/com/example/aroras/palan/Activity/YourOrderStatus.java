package com.example.aroras.palan.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aroras.palan.Models.OrderViewHolder;
import com.example.aroras.palan.Models.Requests;
import com.example.aroras.palan.R;
import com.example.aroras.palan.Models.common;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class YourOrderStatus extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerOptions<Requests> options;
    FirebaseRecyclerAdapter<Requests,OrderViewHolder> adapter;

    //FirebaseDatabase database;
    DatabaseReference requests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_order_status);

        //adapter=findViewById(R.id.);
        recyclerView=(RecyclerView)findViewById(R.id.listOrders);

        requests = FirebaseDatabase.getInstance().getReference().child("Requests");
        //requests = database.getReference("Requests");

        options=new FirebaseRecyclerOptions.Builder<Requests>()
                        .setQuery(requests,Requests.class).build();

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadOrders(common.currentUser.getPhone());

    }

    private void loadOrders(String phone) {

        adapter=new FirebaseRecyclerAdapter<Requests, OrderViewHolder>(options) {

            protected void onBindViewHolder(OrderViewHolder viewHolder, int position, Requests model) {
                viewHolder.txtOrderId.setText(adapter.getRef(position).getKey());
               viewHolder.txtOrderStatus.setText(convertCodeToStatus(model.getStatus()));
               viewHolder.txtOrderAddress.setText(model.getAddress());
                viewHolder.txtOrderPhone.setText(model.getPhone());
            }

            public OrderViewHolder onCreateViewHolder( ViewGroup parent, int i) {

                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.orderlayout,parent,false);
                return  new OrderViewHolder(view);
            }
        };
        recyclerView.setAdapter(adapter);
    }

    private String convertCodeToStatus(String status) {
        if (status.equals("0"))
            return "Placed";
        else  if (status.equals("1"))
            return "On my Way";
        else
            return "Shipped";

    }
}
