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

public class MyWishListFragment extends Fragment {

private RecyclerView myWishListRecyclerView;


    public MyWishListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_wish_list, container, false);
        myWishListRecyclerView=view.findViewById(R.id.my_wish_list_recyclerView);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myWishListRecyclerView.setLayoutManager(linearLayoutManager);

        List<WishListModel> wishListModelList=new ArrayList<>();


        WishListAdapter adapter=new WishListAdapter(wishListModelList, true);

        myWishListRecyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        return  view;
    }
}