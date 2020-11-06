package com.example.ebazar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyRewardAdapter extends RecyclerView.Adapter<MyRewardAdapter.ViewHolder> {

    private List<RewardModel> rewardModelList;
    private  Boolean useMiniLayout=false;

    public MyRewardAdapter(List<RewardModel> rewardModelList, Boolean useMiniLayout) {
        this.rewardModelList = rewardModelList;
        this.useMiniLayout=useMiniLayout;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (useMiniLayout) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mini_rewards_item_layout,parent, false);
        } else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rewards_item_layout, parent, false);
    }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String title=rewardModelList.get(position).getCoupon_title();
        String exdate=rewardModelList.get(position).getCoupon_validity();
        String body=rewardModelList.get(position).getCoupon_body();
        holder.setData(title,exdate,body);


    }

    @Override
    public int getItemCount() {
        return rewardModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView coupon_title;
        private TextView coupon_validity;
        private TextView coupon_bady;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            coupon_bady=itemView.findViewById(R.id.coupon_body);
            coupon_validity=itemView.findViewById(R.id.coupon_validity);
            coupon_title=itemView.findViewById(R.id.coupon_title);
        }
        private void setData(final String title, final String exdate, final String body){
            coupon_title.setText(title);
            coupon_validity.setText(exdate);
            coupon_bady.setText(body);

            if (useMiniLayout){

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ProductsDetailsActivity.couponTitle.setText(title);
                        ProductsDetailsActivity.couponExpiryDate.setText(exdate);
                        ProductsDetailsActivity.couponBody.setText(body);
                        ProductsDetailsActivity.showCouponDialogRecyclerView();
                    }
                });
            }
        }
    }
}
