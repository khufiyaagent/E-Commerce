package com.example.ebazar;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductSpecificationAdapter extends RecyclerView.Adapter<ProductSpecificationAdapter.MyViewPager> {

    private List<ProductSpecificationModel> productSpecificationModelList;

    public ProductSpecificationAdapter(List<ProductSpecificationModel> productSpecificationModelList) {
        this.productSpecificationModelList = productSpecificationModelList;
    }

    @Override
    public int getItemViewType(int position) {
        switch (productSpecificationModelList.get(position).getType()) {
            case 0:
                return ProductSpecificationModel.SPECIFICATION_TITLE;
            case 1:
                return ProductSpecificationModel.SPECIFICATION_BODY;

            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public MyViewPager onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case ProductSpecificationModel.SPECIFICATION_TITLE:

                TextView title = new TextView(parent.getContext());
                title.setTypeface(null, Typeface.BOLD);
                title.setTextColor(Color.parseColor("#000000"));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(
                        setDp(16, parent.getContext()),
                        setDp(16, parent.getContext()),
                        setDp(16, parent.getContext()),
                        setDp(0, parent.getContext()));
                title.setLayoutParams(layoutParams);
                return new MyViewPager(title);

            case ProductSpecificationModel.SPECIFICATION_BODY:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_specification_item_layout, parent, false);
                return new MyViewPager(view);
            default:
                return null;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewPager holder, int position) {
        switch (productSpecificationModelList.get(position).getType()) {
            case ProductSpecificationModel.SPECIFICATION_TITLE:
                holder.setTitle(productSpecificationModelList.get(position).getTitle());
                break;
            case ProductSpecificationModel.SPECIFICATION_BODY:
                String featureTitle = productSpecificationModelList.get(position).getFeatureName();
                String featureValue = productSpecificationModelList.get(position).getFeatureValue();
                holder.setFeatures(featureTitle, featureValue);
                break;
            default:
                return;
        }
    }

    @Override
    public int getItemCount() {
        return productSpecificationModelList.size();
    }

    private int setDp(int dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public class MyViewPager extends RecyclerView.ViewHolder {
        private TextView featureName;
        private TextView featureValue;
        private TextView title;

        public MyViewPager(@NonNull View itemView) {
            super(itemView);
        }

        private void setTitle(String titleText) {
            title = (TextView) itemView;
            title.setText(titleText);

        }

        private void setFeatures(String title, String value) {
            featureName = itemView.findViewById(R.id.tv_feature_name);
            featureValue = itemView.findViewById(R.id.tv_feature_value);
            featureName.setText(title);
            featureValue.setText(value);
        }
    }
}
