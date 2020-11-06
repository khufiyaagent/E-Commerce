package com.example.ebazar;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyVewHolder> {

    private List<CategoryModel> categoryModelList;
    private Context context;
    private int lastPosition=-1;

    public CategoryAdapter(List<CategoryModel> categoryModelList) {
        this.categoryModelList = categoryModelList;

    }

    @NonNull
    @Override
    public MyVewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new MyVewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyVewHolder holder, int position) {

        String icon = categoryModelList.get(position).getCategory_icon_link();
        String name = categoryModelList.get(position).getCategory_name();
        holder.setCategory(name, position);
        holder.setCategory_icon(icon);

        if (lastPosition<position){
            Animation animation= AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.fade_in);
            holder.itemView.setAnimation(animation);
            lastPosition=position;
        }

    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    public static class MyVewHolder extends RecyclerView.ViewHolder {

        private ImageView category_icon;
        private TextView category_name;


        public MyVewHolder(@NonNull View itemView) {
            super(itemView);
            category_icon = itemView.findViewById(R.id.category_icon);
            category_name = itemView.findViewById(R.id.category_name);
        }

        private void setCategory_icon(String iconUrl) {
            if (!iconUrl.equals("null")) {
                Glide.with(itemView.getContext()).load(iconUrl).apply(new RequestOptions().placeholder(R.drawable.placeholder))
                        .into(category_icon);
            }else {
                category_icon.setImageResource(R.drawable.home_icon);
            }
        }

        private void setCategory(final String name, final int position) {
            category_name.setText(name);
            if (!name.equals("")) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (position != 0) {
                            Intent categoryIntent = new Intent(itemView.getContext(), CategoryActivity.class);
                            categoryIntent.putExtra("categoryName", name);
                            itemView.getContext().startActivity(categoryIntent);
                        }
                    }
                });
            }
        }
    }

}
