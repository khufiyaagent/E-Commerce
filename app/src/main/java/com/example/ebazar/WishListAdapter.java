package com.example.ebazar;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.ViewHolder> {
   private List<WishListModel> wishListModelList;
   private Boolean wish_list;

    public WishListAdapter(List<WishListModel> wishListModelList, Boolean wish_list) {
        this.wishListModelList = wishListModelList;
        this.wish_list=wish_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_items_layout, parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String resources=wishListModelList.get(position).getProductImage();
        String productTitle=wishListModelList.get(position).getProductTitle();
        long freeCoupons=wishListModelList.get(position).getFreeCoupons();
        String rating=wishListModelList.get(position).getRating();
        long totalRating=wishListModelList.get(position).getTotalRating();
        String productPrice=wishListModelList.get(position).getProductPrice();
        String cutPrice=wishListModelList.get(position).getCutPrice();
        boolean cod=wishListModelList.get(position).isCOD();
        ((ViewHolder)holder).setData(resources,productTitle,freeCoupons,rating,totalRating,productPrice,cutPrice,cod);

    }

    @Override
    public int getItemCount() {
        return wishListModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private TextView productTitle;
        private TextView freeCoupons;
        private ImageView couponIcon;
        private TextView rating;
        private TextView totalRatings;
        private View priceCutDiv;
        private TextView productPrice;
        private TextView cutPrice;
        private TextView paymentMethod;
        private ImageButton deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage=itemView.findViewById(R.id.product_image);
            productTitle=itemView.findViewById(R.id.product_title);
            freeCoupons=itemView.findViewById(R.id.free_coupons);
            couponIcon=itemView.findViewById(R.id.coupon_icon);
            rating=itemView.findViewById(R.id.tv_product_rating_miniView);
            totalRatings=itemView.findViewById(R.id.total_ratings);
            priceCutDiv=itemView.findViewById(R.id.price_cut_divider);
            productPrice=itemView.findViewById(R.id.product_price);
            cutPrice=itemView.findViewById(R.id.cut_price);
            paymentMethod=itemView.findViewById(R.id.payment_method);
            deleteBtn=itemView.findViewById(R.id.delete_button);
        }
        private void setData(String resource, String titleTxt, long freeCouponNo, String avgRate, long totalRating, String priceTxt, String cutPriceTxt, boolean cod){

            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.placeholder)).into(productImage);

            productTitle.setText(titleTxt);

            if (freeCouponNo!=0){
                couponIcon.setVisibility(View.VISIBLE);
                if (freeCouponNo==1){
                    freeCoupons.setText("Free "+freeCouponNo+" Coupon");
                }else {
                    freeCoupons.setText("Free "+freeCouponNo+" Coupons");
                }
            }else {
                couponIcon.setVisibility(View.INVISIBLE);
                freeCoupons.setVisibility(View.INVISIBLE);
            }

            rating.setText(avgRate);
            totalRatings.setText("("+totalRating+") rating");

            productPrice.setText("₹"+priceTxt+"/-");
            cutPrice.setText("₹"+cutPriceTxt+"/+");
            if (cod){
                paymentMethod.setVisibility(View.VISIBLE);
            }else {
                paymentMethod.setVisibility(View.INVISIBLE);
            }

            if (wish_list){
                deleteBtn.setVisibility(View.VISIBLE);
            }else {
                deleteBtn.setVisibility(View.GONE);
            }
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(itemView.getContext(), "Product Deleted", Toast.LENGTH_SHORT).show();
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent productDetailsIntent=new Intent(itemView.getContext(), ProductsDetailsActivity.class);
                    itemView.getContext().startActivity(productDetailsIntent);
                }
            });



        }
    }
}
