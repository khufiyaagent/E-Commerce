package com.example.ebazar;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DBQueries {
    public static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public static List<CategoryModel> categoryModelsList = new ArrayList<CategoryModel>();
    public static List<List<HomePageModel>> lists=new ArrayList<>();
    public static List<String> loadedCategoriesName=new ArrayList<>();

    public static void loadCategories(final RecyclerView categoryRecyclerView, final Context context) {

        firebaseFirestore.collection("CATEGORIES").orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                                categoryModelsList.add(new CategoryModel(queryDocumentSnapshot.get("icon").toString(),
                                        queryDocumentSnapshot.get("categoryName").toString()));
                            }
                            CategoryAdapter categoryAdapter=new CategoryAdapter(categoryModelsList);
                            categoryRecyclerView.setAdapter(categoryAdapter);
                            categoryAdapter.notifyDataSetChanged();
                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public static void loadFragmentData(final RecyclerView homePageRecyclerView, final Context context, final int index, String categoryName){
        firebaseFirestore.collection("CATEGORIES")
                .document(categoryName.toUpperCase())
                .collection("TOP_DEALS")
                .orderBy("index").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()) {
                                if ((long) queryDocumentSnapshot.get("view_type") == 0) {
                                    List<SliderModel> sliderModelList = new ArrayList<>();
                                    long number_of_banners = (long) queryDocumentSnapshot.get("no_of_banners");
                                    for (long x = 1; x <= number_of_banners; x++) {
                                        sliderModelList.add(new SliderModel(queryDocumentSnapshot.get("banner_" + x).toString(),
                                                queryDocumentSnapshot.get("banner_" + x + "_background").toString()));
                                    }
                                    lists.get(index).add(new HomePageModel(0, sliderModelList));
                                } else if ((long) queryDocumentSnapshot.get("view_type") == 1) {
                                    lists.get(index).add(new HomePageModel(1, queryDocumentSnapshot.get("strip_ad_banner").toString(),
                                            queryDocumentSnapshot.get("strip_ad_banner_background").toString()));

                                } else if ((long) queryDocumentSnapshot.get("view_type") == 2) {
                                    List<WishListModel> viewAllProductList=new ArrayList<>();
                                    List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();
                                    long number_of_products = (long) queryDocumentSnapshot.get("no_of_products");
                                    for (long x = 1; x <= number_of_products; x++) {
                                        horizontalProductScrollModelList.add(new HorizontalProductScrollModel(
                                                queryDocumentSnapshot.get("product_ID_" + x).toString(),
                                                queryDocumentSnapshot.get("product_image_" + x).toString(),
                                                queryDocumentSnapshot.get("product_title_" + x).toString(),
                                                queryDocumentSnapshot.get("product_subtitle_" + x).toString(),
                                                queryDocumentSnapshot.get("product_price_" + x).toString()));
                                        viewAllProductList.add(new WishListModel(
                                                queryDocumentSnapshot.get("product_image_" + x).toString(),
                                                queryDocumentSnapshot.get("product_full_name_" + x).toString(),
                                                queryDocumentSnapshot.get("product_price_" + x).toString(),
                                                queryDocumentSnapshot.get("cut_price_" + x).toString(),
                                                (long)queryDocumentSnapshot.get("free_coupon_" + x),
                                                queryDocumentSnapshot.get("average_rating_" + x).toString(),
                                                (long)queryDocumentSnapshot.get("total_rating_" + x),
                                                (boolean)queryDocumentSnapshot.get("cod_availability_" + x)
                                        ));
                                    }
                                    lists.get(index).add(new HomePageModel(2, queryDocumentSnapshot.get("layout_title").toString(), queryDocumentSnapshot.get("layout_background").toString(), horizontalProductScrollModelList,viewAllProductList));
                                } else if ((long) queryDocumentSnapshot.get("view_type") == 3) {
                                    List<HorizontalProductScrollModel> gridProductScrollModelList = new ArrayList<>();
                                    long number_of_products = (long) queryDocumentSnapshot.get("no_of_products");
                                    for (long x = 1; x <= number_of_products; x++) {
                                        gridProductScrollModelList.add(new HorizontalProductScrollModel(
                                                queryDocumentSnapshot.get("product_ID_" + x).toString(),
                                                queryDocumentSnapshot.get("product_image_" + x).toString(),
                                                queryDocumentSnapshot.get("product_title_" + x).toString(),
                                                queryDocumentSnapshot.get("product_subtitle_" + x).toString(),
                                                queryDocumentSnapshot.get("product_price_" + x).toString()));
                                    }
                                    lists.get(index).add(new HomePageModel(3, queryDocumentSnapshot.get("layout_title").toString(), queryDocumentSnapshot.get("layout_background").toString(), gridProductScrollModelList));
                                }
                                HomePageAdapter homePageAdapter=new HomePageAdapter(lists.get(index));
                                homePageRecyclerView.setAdapter(homePageAdapter);
                                homePageAdapter.notifyDataSetChanged();
                                HomeFragment.swipeRefreshLayout.setRefreshing(false);
                            }

                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
