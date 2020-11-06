package com.example.ebazar;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import static com.example.ebazar.MainActivity.showCart;

public class ProductsDetailsActivity extends AppCompatActivity {


    private static Boolean ALREADY_ADDED_TO_WISH_LIST = false;
    private ViewPager productImagesViewPager;
    private TabLayout viewPagerIndicator;
    private FloatingActionButton addToWishListButton;


    /////product details
    private ConstraintLayout productDetailsOnlyContainer;
    private ConstraintLayout productDetailsTabLayoutContainer;
private TextView productOnlyDescriptionBody;
    private ViewPager productDetailsViewPager;
    private TabLayout productDetailsTabLayout;
    private List<ProductSpecificationModel> productSpecificationModelList=new ArrayList<>();
    private String productDescription;
    private String productOtherDetails;


    /////product details
    private Button couponRedeemBtn;
    private TextView averageRatingMiniView, productTitle;
    private TextView totalRatingMiniView;
    private TextView productPrice;
    private TextView productCutPrice;
    private ImageView codIndicatorImageView;
    private TextView codIndicatorTextView;
    private TextView rewardTitle, rewardBody;
    private FirebaseFirestore firebaseFirestore;



    ////////Rating Layout
    private LinearLayout rateNowContainer;
    private TextView totalRating;
    private LinearLayout ratingNosContainer;
    private TextView totalRatingFigure;
    private LinearLayout ratingProgressBarContainer;
    private TextView averageRating;
    ////////Rating Layout

    private Button buyNowBtn;

    //////////////CouponDialog
    public static TextView couponTitle;
    public static TextView couponBody;
    public static TextView couponExpiryDate;
    public static RecyclerView couponRecyclerView;
    public static LinearLayout selectedCoupon;
    //////////////CouponDialog

    public static void showCouponDialogRecyclerView() {

        if (couponRecyclerView.getVisibility() == View.GONE) {
            couponRecyclerView.setVisibility(View.VISIBLE);
            selectedCoupon.setVisibility(View.GONE);
        } else {
            couponRecyclerView.setVisibility(View.GONE);
            selectedCoupon.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_porducts_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        productImagesViewPager = findViewById(R.id.product_images_viewpager);
        viewPagerIndicator = findViewById(R.id.viewpager_indicator);
        addToWishListButton = findViewById(R.id.add_to_wish_list_button);
        buyNowBtn = findViewById(R.id.buy_now_btn);
        couponRedeemBtn = findViewById(R.id.coupon_redemption_button);
        productTitle=findViewById(R.id.product_title);
        averageRatingMiniView=findViewById(R.id.tv_product_rating_miniView);
        totalRatingMiniView=findViewById(R.id.total_rating_miniView);
        productPrice=findViewById(R.id.product_price);
        productCutPrice=findViewById(R.id.cut_price);
        codIndicatorImageView=findViewById(R.id.cod_indicator_imageView);
        codIndicatorTextView=findViewById(R.id.tv_cod_indicator);
        rewardBody=findViewById(R.id.reward_body);
        rewardTitle=findViewById(R.id.reward_title);
        productDetailsOnlyContainer=findViewById(R.id.product_only_details_container);
        productDetailsTabLayoutContainer=findViewById(R.id.product_details_tab_container);
        productOnlyDescriptionBody=findViewById(R.id.tv_product_details_body);
        totalRating=findViewById(R.id.total_ratings);
        ratingNosContainer=findViewById(R.id.ratings_number_container);
        totalRatingFigure=findViewById(R.id.total_ratings_figure);
        ratingProgressBarContainer=findViewById(R.id.rating_progressBar_container);
        averageRating=findViewById(R.id.average_rating);



        productDetailsViewPager = findViewById(R.id.product_details_viewPager);
        productDetailsTabLayout = findViewById(R.id.product_details_tab_layout);
        firebaseFirestore = FirebaseFirestore.getInstance();
        final List<String> productImagesList=new ArrayList<>();

        firebaseFirestore.collection("PRODUCTS").document("BJgYCjASiR0QtGUjtsdX")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot=task.getResult();
                    for (long x=1; x<(long)documentSnapshot.get("no_of_product_images") +1; x++){
                        productImagesList.add(documentSnapshot.get("product_image_"+x).toString());
                    }
                    ProductImagesAdapter productImagesAdapter = new ProductImagesAdapter(productImagesList);
                    productImagesViewPager.setAdapter(productImagesAdapter);

                    productTitle.setText(documentSnapshot.get("product_title").toString());
                    averageRatingMiniView.setText(documentSnapshot.get("average_rating").toString());
                    productPrice.setText("₹"+documentSnapshot.get("product_price").toString()+"/-");
                    productCutPrice.setText("₹"+documentSnapshot.get("cut_price").toString()+"/-");
                    totalRatingMiniView.setText("("+(long)documentSnapshot.get("total_ratings")+") rating");

                    if ((boolean)documentSnapshot.get("cod_availability")){
                        codIndicatorImageView.setVisibility(View.VISIBLE);
                        codIndicatorTextView.setVisibility(View.VISIBLE);
                    }else {
                        codIndicatorImageView.setVisibility(View.INVISIBLE);
                        codIndicatorTextView.setVisibility(View.INVISIBLE);
                    }
                    rewardTitle.setText((long)documentSnapshot.get("free_coupons")+" "+documentSnapshot.get("free_coupon_title").toString());
                    rewardBody.setText(documentSnapshot.get("free_coupon_body").toString());
                    if ((boolean)documentSnapshot.get("use_tab_layout")){
                        productDetailsTabLayoutContainer.setVisibility(View.VISIBLE);
                        productDetailsOnlyContainer.setVisibility(View.GONE);

                        productDescription=documentSnapshot.get("product_description").toString();

                        productOtherDetails=documentSnapshot.get("product_other_details").toString();

                        for (long x=1; x< (long)documentSnapshot.get("total_spec_titles")+1; x++){
                            productSpecificationModelList.add(new ProductSpecificationModel(0, documentSnapshot.get("spec_title_"+x).toString()));
                            for (long y=1; y< (long)documentSnapshot.get("spec_title_"+x+"_total_fields")+1; y++){
                                productSpecificationModelList.add(new ProductSpecificationModel(1, documentSnapshot.get("spec_title_"+x+"_field_"+y+"_name").toString(),
                                        documentSnapshot.get("spec_title_"+x+"_field_"+y+"_value").toString()));
                            }
                        }
                    }else {
                        productDetailsTabLayoutContainer.setVisibility(View.GONE);
                        productDetailsOnlyContainer.setVisibility(View.VISIBLE);
                        productOnlyDescriptionBody.setText(documentSnapshot.get("product_description").toString());
                    }
                    totalRating.setText((long)documentSnapshot.get("total_ratings")+" "+"rating");
                    averageRating.setText(documentSnapshot.get("average_rating").toString());

                    for (int x=0; x<5; x++){
                        TextView rating= (TextView) ratingNosContainer.getChildAt(x);
                        rating.setText(String.valueOf((long)documentSnapshot.get((5-x)+"_star")));
                        ProgressBar progressBar= (ProgressBar) ratingProgressBarContainer.getChildAt(x);

                        progressBar.setMax(Integer.parseInt(String.valueOf((long)documentSnapshot.get("total_ratings"))));
                        progressBar.setProgress(Integer.parseInt(String.valueOf((long)documentSnapshot.get((5-x)+"_star"))));
                    }
                    totalRatingFigure.setText(String.valueOf((long)documentSnapshot.get("total_ratings")));
                    productDetailsViewPager.setAdapter(new ProductDetailsAdapter(getSupportFragmentManager(), productDetailsTabLayout.getTabCount(), productDescription, productOtherDetails, productSpecificationModelList));

                }else {
                    String error=task.getException().getMessage();
                    Toast.makeText(ProductsDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            }
        });


        viewPagerIndicator.setupWithViewPager(productImagesViewPager, true);

        addToWishListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ALREADY_ADDED_TO_WISH_LIST) {
                    ALREADY_ADDED_TO_WISH_LIST = false;
                    addToWishListButton.setSupportImageTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                } else {
                    ALREADY_ADDED_TO_WISH_LIST = true;
                    addToWishListButton.setSupportImageTintList(getResources().getColorStateList(R.color.colorPrimary));

                }
            }
        });




        productDetailsViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(productDetailsTabLayout));

        productDetailsTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                productDetailsViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        ////////Rating Layout

        rateNowContainer = findViewById(R.id.rate_now_container);
        for (int x = 0; x < rateNowContainer.getChildCount(); x++) {
            final int startPosition = x;
            rateNowContainer.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setRating(startPosition);
                }
            });

        }

        ////////Rating Layout

        buyNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent deliveryIntent = new Intent(ProductsDetailsActivity.this, DeliveryActivity.class);
                startActivity(deliveryIntent);
            }
        });
        couponRedeemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog couponApplyDialog = new Dialog(ProductsDetailsActivity.this);
                couponApplyDialog.setContentView(R.layout.coupon_redeem_dialog);
                couponApplyDialog.setCancelable(true);
                couponApplyDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                ImageView toggle_recyclerView = couponApplyDialog.findViewById(R.id.toggle_recyclerView);
                couponRecyclerView = couponApplyDialog.findViewById(R.id.coupon_recycler_view);
                selectedCoupon = couponApplyDialog.findViewById(R.id.selected_coupon);
                TextView originalPrice = couponApplyDialog.findViewById(R.id.original_price);
                TextView couponPrice = couponApplyDialog.findViewById(R.id.discounted_price);

                couponTitle = couponApplyDialog.findViewById(R.id.coupon_title);
                couponExpiryDate = couponApplyDialog.findViewById(R.id.coupon_validity);
                couponBody = couponApplyDialog.findViewById(R.id.coupon_body);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ProductsDetailsActivity.this);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                couponRecyclerView.setLayoutManager(linearLayoutManager);

                List<RewardModel> rewardModelList = new ArrayList<>();

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

                MyRewardAdapter myRewardAdapter = new MyRewardAdapter(rewardModelList, true);

                couponRecyclerView.setAdapter(myRewardAdapter);
                myRewardAdapter.notifyDataSetChanged();

                toggle_recyclerView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showCouponDialogRecyclerView();
                    }
                });

                couponApplyDialog.show();


            }
        });

    }

    private void setRating(int startPosition) {
        for (int x = 0; x < rateNowContainer.getChildCount(); x++) {
            ImageView startBtn = (ImageView) rateNowContainer.getChildAt(x);
            startBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#bebebe")));
            if (x <= startPosition) {
                startBtn.setImageTintList(ColorStateList.valueOf(Color.parseColor("#ffbb00")));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_and_cart_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        } else if (id == R.id.main_search_icon) {
            //todo: search
            return true;
        } else if (id == R.id.main_cart_icon) {
            Intent cartIntent = new Intent(ProductsDetailsActivity.this, MainActivity.class);
            showCart = true;
            startActivity(cartIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}