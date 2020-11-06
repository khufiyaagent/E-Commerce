package com.example.ebazar;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class MyAccountFragment extends Fragment {


    public MyAccountFragment() {
        // Required empty public constructor
    }
    public static final int MANAGE_ADDRESS=1;
    private Button view_all_address_button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_my_account, container, false);
       view_all_address_button=view.findViewById(R.id.view_all_address_button);


       view_all_address_button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent myAddressIntent=new Intent(getContext(), MyAddressesActivity.class);
               myAddressIntent.putExtra("MODE", MANAGE_ADDRESS);
               startActivity(myAddressIntent);
           }
       });





       return view;
    }
}