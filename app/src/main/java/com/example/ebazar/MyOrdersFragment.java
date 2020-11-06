package com.example.ebazar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class MyOrdersFragment extends Fragment {


    public MyOrdersFragment() {
        // Required empty public constructor
    }
    private RecyclerView myOrderedRecyclerView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_my_oders, container, false);
        myOrderedRecyclerView=view.findViewById(R.id.my_orders_recycler_view);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        myOrderedRecyclerView.setLayoutManager(linearLayoutManager);

        List<MyOrderItemModel> myOrderItemModelList=new ArrayList<>();

        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.mobile_icon, 2," Mobile3 (RED)","Delivered on Thu, 10th Jan 2020"));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.mobile_icon, 1," Vol8 (Green)","Delivered on Thu, 10th Jan 2020"));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.mobile_icon, 0," MoF3 (Black)","Delivered on Thu, 10th Jan 2020"));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.mobile_icon, 0," T55 (Brown)","cancelled"));

        MyOrderItemAdapter adapter=new MyOrderItemAdapter(myOrderItemModelList);
        myOrderedRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();




        return  view;
    }
}