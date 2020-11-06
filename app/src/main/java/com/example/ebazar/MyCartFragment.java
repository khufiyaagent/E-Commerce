package com.example.ebazar;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;


public class MyCartFragment extends Fragment {


    private RecyclerView cartItemRecycler;
    private Button continuebtn;
    public MyCartFragment() {
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
      View view= inflater.inflate(R.layout.fragment_my_cart, container, false);


      cartItemRecycler=view.findViewById(R.id.cart_item_recycler_view);
      continuebtn=view.findViewById(R.id.cart_continue_btn);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        cartItemRecycler.setLayoutManager(linearLayoutManager);

        List<CartItemModel> cartItemModelList=new ArrayList<>();

        cartItemModelList.add(new CartItemModel(0,R.drawable.mobile_icon,"MobileX",2, "Rs 5999/-","Rs. 6999/-", 3, 0, 0 ));
        cartItemModelList.add(new CartItemModel(0,R.drawable.mymall_icon,"MX554",2, "Rs 5999/-","Rs. 6999/-", 3, 2, 0 ));
        cartItemModelList.add(new CartItemModel(0,R.drawable.mobile_icon,"LopD",2, "Rs 5999/-","Rs. 6999/-", 3, 1, 0 ));

        cartItemModelList.add(new CartItemModel(1, "5", "Rs. 8999/-", "Free", "Rs.8999/-","Rs. 599/-" ));

        CartAdapter cartAdapter=new CartAdapter(cartItemModelList);
        cartItemRecycler.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        continuebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent deliveryIntent=new Intent(getContext(), AddAddressActivity.class);
                getContext().startActivity(deliveryIntent);
            }
        });
      return  view;
    }
}