package com.example.ebazar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;

import static com.example.ebazar.DBQueries.categoryModelsList;
import static com.example.ebazar.DBQueries.lists;
import static com.example.ebazar.DBQueries.loadCategories;
import static com.example.ebazar.DBQueries.loadFragmentData;
import static com.example.ebazar.DBQueries.loadedCategoriesName;


public class HomeFragment extends Fragment {

    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;
    private HomePageAdapter homePageAdapter;
    private RecyclerView category_recyclerView;
    private List<CategoryModel> categoryModelFakeList=new ArrayList<>();
    private List<HomePageModel> homePageModelFakeList=new ArrayList<>();
    private CategoryAdapter categoryAdapter;
    private ImageView no_internet_connection;
    public static SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView homePageRecyclerView;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        no_internet_connection = view.findViewById(R.id.no_internet_connection);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeColors(getContext().getResources().getColor(R.color.colorPrimary),
                getContext().getResources().getColor(R.color.colorPrimary),
                getContext().getResources().getColor(R.color.colorPrimary));
        category_recyclerView = view.findViewById(R.id.category_recyclerView);
        homePageRecyclerView = view.findViewById(R.id.home_page_RecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        category_recyclerView.setLayoutManager(linearLayoutManager);


        LinearLayoutManager testingLinearLayoutManager = new LinearLayoutManager(getContext());
        testingLinearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        homePageRecyclerView.setLayoutManager(testingLinearLayoutManager);


        ///////category fake list
        categoryModelFakeList.add(new CategoryModel("null",""));
        categoryModelFakeList.add(new CategoryModel("",""));
        categoryModelFakeList.add(new CategoryModel("",""));
        categoryModelFakeList.add(new CategoryModel("",""));
        categoryModelFakeList.add(new CategoryModel("",""));
        categoryModelFakeList.add(new CategoryModel("",""));
        categoryModelFakeList.add(new CategoryModel("",""));
        categoryModelFakeList.add(new CategoryModel("",""));
        categoryModelFakeList.add(new CategoryModel("",""));
         ///////category fake list


        ///////home page fake list
        List<SliderModel> sliderModelFakeList=new ArrayList<>();
        sliderModelFakeList.add(new SliderModel("null", "#ffffff"));
        sliderModelFakeList.add(new SliderModel("null", "#ffffff"));
        sliderModelFakeList.add(new SliderModel("null", "#ffffff"));
        sliderModelFakeList.add(new SliderModel("null", "#ffffff"));
        sliderModelFakeList.add(new SliderModel("null", "#ffffff"));
        sliderModelFakeList.add(new SliderModel("null", "#ffffff"));

        List<HorizontalProductScrollModel> horizontalProductScrollModelFakeList=new ArrayList<>();
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("","","","",""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("","","","",""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("","","","",""));
        horizontalProductScrollModelFakeList.add(new HorizontalProductScrollModel("","","","",""));
       homePageModelFakeList.add(new HomePageModel(0,sliderModelFakeList));
       homePageModelFakeList.add(new HomePageModel(1, "","#ffffff"));
       homePageModelFakeList.add(new HomePageModel(2,"", "#ffffff", horizontalProductScrollModelFakeList, new ArrayList<WishListModel>()));
       homePageModelFakeList.add(new HomePageModel(3, "", "#ffffff", horizontalProductScrollModelFakeList));
        ///////home page fake list
        categoryAdapter = new CategoryAdapter(categoryModelFakeList);

        homePageAdapter = new HomePageAdapter(homePageModelFakeList);


        connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            no_internet_connection.setVisibility(View.GONE);
            if (categoryModelsList.size() == 0) {
                loadCategories(category_recyclerView, getContext());
            } else {
                categoryAdapter=new CategoryAdapter(categoryModelsList);
                categoryAdapter.notifyDataSetChanged();
            }
            category_recyclerView.setAdapter(categoryAdapter);

            if (lists.size() == 0) {
                lists.add(new ArrayList<HomePageModel>());
                loadedCategoriesName.add("HOME");
                loadFragmentData(homePageRecyclerView,  getContext(), 0, "Home");
            } else {
                homePageAdapter = new HomePageAdapter(lists.get(0));
                homePageAdapter.notifyDataSetChanged();
            }
            homePageRecyclerView.setAdapter(homePageAdapter);

        } else {
            Glide.with(this).load(R.drawable.ic_baseline_wifi_off_24).into(no_internet_connection);
            no_internet_connection.setVisibility(View.VISIBLE);
        }
        ////////////////swipe refresh
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                categoryModelsList.clear();
                lists.clear();
                loadedCategoriesName.clear();
                if (networkInfo != null && networkInfo.isConnected()) {
                    no_internet_connection.setVisibility(View.GONE);
                    categoryAdapter=new CategoryAdapter(categoryModelFakeList);
                    category_recyclerView.setAdapter(categoryAdapter);
                    homePageAdapter=new HomePageAdapter(homePageModelFakeList);
                    homePageRecyclerView.setAdapter(homePageAdapter);
                    loadCategories(category_recyclerView, getContext());
                    lists.add(new ArrayList<HomePageModel>());
                    loadedCategoriesName.add("HOME");
                    loadFragmentData(homePageRecyclerView, getContext(), 0, "Home");
                } else {
                    Glide.with(getContext()).load(R.drawable.ic_baseline_wifi_off_24).into(no_internet_connection);
                    no_internet_connection.setVisibility(View.VISIBLE);
                }
            }
        });
        ////////////////swipe refresh
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}