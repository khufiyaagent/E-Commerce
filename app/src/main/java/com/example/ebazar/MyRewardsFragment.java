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

public class MyRewardsFragment extends Fragment {


    public MyRewardsFragment() {
        // Required empty public constructor
    }

  private RecyclerView myRewardRecyclerView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_rewards, container, false);

        myRewardRecyclerView=view.findViewById(R.id.my_reward_recycler_view);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myRewardRecyclerView.setLayoutManager(linearLayoutManager);

        List<RewardModel> rewardModelList =new ArrayList<>();

        rewardModelList.add(new RewardModel("Get 20% OFF", "Till sept 2020", "Get 20% off on any product till sept 2020, hurry up!!!"));
        rewardModelList.add(new RewardModel("CaseBack", "Till sept 2020", "Get 20% off on any product till sept 2020, hurry up!!!"));
        rewardModelList.add(new RewardModel("Get 20% OFF", "Till sept 2020", "Get 20% off on any product till sept 2020, hurry up!!!"));
        rewardModelList.add(new RewardModel("Discount", "Till sept 2020", "Get 20% off on any product till sept 2020, hurry up!!!"));
        rewardModelList.add(new RewardModel("Get 20% OFF", "Till sept 2020", "Get 20% off on any product till sept 2020, hurry up!!!"));
        rewardModelList.add(new RewardModel("Get 20% OFF", "Till sept 2020", "Get 20% off on any product till sept 2020, hurry up!!!"));
        rewardModelList.add(new RewardModel("CaseBack", "Till sept 2020", "Get 20% off on any product till sept 2020, hurry up!!!"));
        rewardModelList.add(new RewardModel("Get 20% OFF", "Till sept 2020", "Get 20% off on any product till sept 2020, hurry up!!!"));
        rewardModelList.add(new RewardModel("Get 20% OFF", "Till sept 2020", "Get 20% off on any product till sept 2020, hurry up!!!"));
        rewardModelList.add(new RewardModel("Discount", "Till sept 2020", "Get 20% off on any product till sept 2020, hurry up!!!"));
        rewardModelList.add(new RewardModel("Get 20% OFF", "Till sept 2020", "Get 20% off on any product till sept 2020, hurry up!!!"));
        rewardModelList.add(new RewardModel("Discount", "Till sept 2020", "Get 20% off on any product till sept 2020, hurry up!!!"));

        MyRewardAdapter myRewardAdapter=new MyRewardAdapter(rewardModelList, false);

        myRewardRecyclerView.setAdapter(myRewardAdapter);
        myRewardAdapter.notifyDataSetChanged();

        return  view;
    }
}