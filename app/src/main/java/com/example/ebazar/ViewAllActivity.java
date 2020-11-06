package com.example.ebazar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ViewAllActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView viewAllRecyclerView;
    private GridView viewAllGridView;
    public static List<HorizontalProductScrollModel> horizontalProductScrollModelList ;
    public static List<WishListModel> wishListModelList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        toolbar = findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewAllRecyclerView = findViewById(R.id.view_all_recyclerView);
        viewAllGridView = findViewById(R.id.view_all_grid_view);


        int viewAll = getIntent().getIntExtra("VIEW_ALL", -1);
        if (viewAll == 0) {
            viewAllRecyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            viewAllRecyclerView.setLayoutManager(linearLayoutManager);

            WishListAdapter wishListAdapter = new WishListAdapter(wishListModelList, false);
            viewAllRecyclerView.setAdapter(wishListAdapter);
            wishListAdapter.notifyDataSetChanged();
        } else if (viewAll == 1) {

            viewAllGridView.setVisibility(View.VISIBLE);
            GridProductLayoutAdapter gridProductLayoutAdapter = new GridProductLayoutAdapter(horizontalProductScrollModelList);

            viewAllGridView.setAdapter(gridProductLayoutAdapter);

        }


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}