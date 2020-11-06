package com.example.ebazar;

import android.app.Dialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static com.example.ebazar.RegisterActivity.setSignUpFragmentVariable;

public class CartAdapter extends RecyclerView.Adapter {

    private List<CartItemModel> cartItemModelList;

    public CartAdapter(List<CartItemModel> cartItemModelList) {
        this.cartItemModelList = cartItemModelList;
    }

    @Override
    public int getItemViewType(int position) {
        switch (cartItemModelList.get(position).getType()) {
            case 0:
                return CartItemModel.CART_ITEM;
            case 1:
                return CartItemModel.TOTAL_AMOUNT;

            default:
                return -1;
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case CartItemModel.CART_ITEM:
                View cartItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout, parent, false);
                return new CartItemViewHolder(cartItemView);
            case CartItemModel.TOTAL_AMOUNT:
                View cartTotalAmountView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_total_amount_layout, parent, false);
                return new CartTotalAmountViewHolder(cartTotalAmountView);

            default:
                return null;

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (cartItemModelList.get(position).getType()){
            case CartItemModel.CART_ITEM:
               int resource= cartItemModelList.get(position).getProductImage();
               String title= cartItemModelList.get(position).getProductTitle();
               int freeCoupons= cartItemModelList.get(position).getFreeCoupons();
               String productPrice= cartItemModelList.get(position).getProductPrice();
               String cutPrice= cartItemModelList.get(position).getCutPrice();
               int productQuantities= cartItemModelList.get(position).getProductQuantity();
               int offerApplied= cartItemModelList.get(position).getOffersApplied();
                ((CartItemViewHolder)holder).setCartItemDetails(resource,title,freeCoupons,productPrice,cutPrice,productQuantities,offerApplied);
               break;
            case CartItemModel.TOTAL_AMOUNT:
                String numberOfItem=cartItemModelList.get(position).getNumberOfItem();
             String totalItemPrice=cartItemModelList.get(position).getTotalItemsPrice();
             String deliveryPrice=cartItemModelList.get(position).getDeliveryPrice();
                String totalAmount=cartItemModelList.get(position).getTotalAmount();
                String savedAmount=cartItemModelList.get(position).getSavedPrice();

                ((CartTotalAmountViewHolder)holder).setTotalAmount(numberOfItem,totalItemPrice, deliveryPrice, totalAmount, savedAmount);
                break;
        }

    }


    @Override
    public int getItemCount() {
        return cartItemModelList.size();
    }


}


class CartItemViewHolder extends RecyclerView.ViewHolder {
    private ImageView productImage;
    private TextView productTitle;
    private TextView freeCoupons;
    private ImageView freeCouponImage;
    private TextView productPrice;
    private TextView cutPrice;
    private TextView offersApplied;
    private TextView couponsApplied;
    private TextView productQuantities;


    public CartItemViewHolder(@NonNull View itemView) {
        super(itemView);
        productImage = itemView.findViewById(R.id.product_image);
        productTitle = itemView.findViewById(R.id.product_title);
        freeCoupons = itemView.findViewById(R.id.tv_free_coupon);
        productPrice = itemView.findViewById(R.id.product_price);
        offersApplied = itemView.findViewById(R.id.offers_applied);
        cutPrice = itemView.findViewById(R.id.cut_price);
        couponsApplied = itemView.findViewById(R.id.coupon_applied);
        productQuantities = itemView.findViewById(R.id.product_quantities);
        freeCouponImage = itemView.findViewById(R.id.free_coupon_image);
    }

    public void setCartItemDetails(int resource, String title,int freeCouponsNo, String pPrice,String ctPrice, int pQuantities, int offerAppliedNo) {
        productImage.setImageResource(resource);
        productTitle.setText(title);
       if (freeCouponsNo>0){
           freeCoupons.setVisibility(View.VISIBLE);
           freeCouponImage.setVisibility(View.VISIBLE);
           if (freeCouponsNo==1){
               freeCoupons.setText("Free "+ freeCouponsNo+" coupon");
           }else{
               freeCoupons.setText("Free "+freeCouponsNo+" coupons");
           }
       }else {
           freeCoupons.setVisibility(View.INVISIBLE);
           freeCouponImage.setVisibility(View.INVISIBLE);
       }

       productPrice.setText(pPrice);
       cutPrice.setText(ctPrice);
      // productQuantities.setText(pQuantities);

       if (offerAppliedNo>0){
           offersApplied.setVisibility(View.VISIBLE);
           offersApplied.setText(offerAppliedNo+" Offers Applied");
       }

       productQuantities.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               final Dialog quantityDialog=new Dialog(itemView.getContext());
               quantityDialog.setContentView(R.layout.quantity_dialog);
               quantityDialog.setCancelable(false);

               quantityDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

               Button okBtn=quantityDialog.findViewById(R.id.okBtn);
               Button cancelBtn=quantityDialog.findViewById(R.id.cancelBtu);
               final EditText enterQuantity=quantityDialog.findViewById(R.id.quantity_count);

               okBtn.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {

                     productQuantities.setText("Qty: "+enterQuantity.getText());
                       quantityDialog.dismiss();
                   }
               });

               cancelBtn.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       quantityDialog.dismiss();
                       //quantityDialog.setCancelable(true);
                   }
               });
               quantityDialog.show();
           }
       });

    }


}

class CartTotalAmountViewHolder extends RecyclerView.ViewHolder {

    private TextView totalItems;
    private TextView totalItemPrice;
    private TextView deliveryPrice;
    private TextView totalAmount;
    private TextView totalSavedAmount;

    public CartTotalAmountViewHolder(@NonNull View itemView) {
        super(itemView);
        totalItems=itemView.findViewById(R.id.total_items);
        totalItemPrice=itemView.findViewById(R.id.total_item_price);
        deliveryPrice=itemView.findViewById(R.id.delivery_price);
        totalAmount=itemView.findViewById(R.id.total_amount);
        totalSavedAmount=itemView.findViewById(R.id.saved_amount);
    }
    public void setTotalAmount(String totalItemText, String totalItemPriceText, String deliveryPriceText, String totalAmountText, String totalSavedAmountText ){
        totalItems.setText("Price ("+totalItemText+" items)");
        totalItemPrice.setText(totalItemPriceText);
       deliveryPrice.setText(deliveryPriceText);
       totalAmount.setText(totalAmountText);
       totalSavedAmount.setText("You saved "+totalSavedAmountText+" on this order");

    }
}