package com.example.ebazar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DeliveryActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView deliveryRecyclerView;
    private Button changeOrAddAddress;
    public static final int SELECT_ADDRESS=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Delivery");

        changeOrAddAddress = findViewById(R.id.change_or_add_address);

        deliveryRecyclerView = findViewById(R.id.delivery_recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        deliveryRecyclerView.setLayoutManager(linearLayoutManager);

        List<CartItemModel> cartItemModelList = new ArrayList<>();

        cartItemModelList.add(new CartItemModel(0, R.drawable.mobile_icon, "MobileX", 2, "Rs 5999/-", "Rs. 6999/-", 3, 0, 0));
        cartItemModelList.add(new CartItemModel(0, R.drawable.mymall_icon, "MX554", 2, "Rs 5999/-", "Rs. 6999/-", 3, 2, 0));
        cartItemModelList.add(new CartItemModel(0, R.drawable.mobile_icon, "LopD", 2, "Rs 5999/-", "Rs. 6999/-", 3, 1, 0));

        cartItemModelList.add(new CartItemModel(1, "5", "Rs. 8999/-", "Free", "Rs.8999/-", "Rs. 599/-"));

        CartAdapter cartAdapter = new CartAdapter(cartItemModelList);
        deliveryRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        changeOrAddAddress.setVisibility(View.VISIBLE);
        changeOrAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myAddressesIntent=new Intent(DeliveryActivity.this, MyAddressesActivity.class);
                myAddressesIntent.putExtra("MODE", SELECT_ADDRESS);
                startActivity(myAddressesIntent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}