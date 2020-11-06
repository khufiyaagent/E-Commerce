package com.example.ebazar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomePageAdapter extends RecyclerView.Adapter {
    private List<HomePageModel> homePageModelsList;
    private RecyclerView.RecycledViewPool recycledViewPool;
    private int lastPosition=-1;


    public HomePageAdapter(List<HomePageModel> homePageModelsList) {
        this.homePageModelsList = homePageModelsList;
        recycledViewPool=new RecyclerView.RecycledViewPool();
    }

    @Override
    public int getItemViewType(int position) {
        switch (homePageModelsList.get(position).getType()) {
            case 0:
                return HomePageModel.BANNER_SLIDER;
            case 1:
                return HomePageModel.STRIP_AD_BANNER;
            case 2:
                return HomePageModel.HORIZONTAL_PRODUCTS_VIEW;
            case 3:
                return HomePageModel.GRID_PRODUCT_VIEW;
            default:
                return -1;
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case HomePageModel.BANNER_SLIDER:
                View bannerSliderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sliding_ad_layout, parent, false);
                return new BannerSliderViewHolder(bannerSliderView);
            case HomePageModel.STRIP_AD_BANNER:
                View stripAdBannerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.strip_ad_layout, parent, false);
                return new StripAdBannerViewHolder(stripAdBannerView);
            case HomePageModel.HORIZONTAL_PRODUCTS_VIEW:
                View horizontalProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_scroll_layout, parent, false);
                return new HorizontalScrollProductViewHolder(horizontalProductView);
            case HomePageModel.GRID_PRODUCT_VIEW:
                View gridProductView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_product_layout, parent, false);
                return new GridProductViewHolder(gridProductView);
            default:
                return null;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (homePageModelsList.get(position).getType()) {
            case HomePageModel.BANNER_SLIDER:
                List<SliderModel> sliderModelList = homePageModelsList.get(position).getSliderModelList();
                ((BannerSliderViewHolder) holder).setBannerSliderViewPager(sliderModelList);
                break;
            case HomePageModel.STRIP_AD_BANNER:
                String resource = homePageModelsList.get(position).getResource();
                String color = homePageModelsList.get(position).getBackgroundColor();
                ((StripAdBannerViewHolder) holder).setStripAd(resource, color);
                break;
            case HomePageModel.HORIZONTAL_PRODUCTS_VIEW:
                String layoutColor=homePageModelsList.get(position).getBackgroundColor();
                String horizontalLayoutTitle = homePageModelsList.get(position).getTitle();
                List<WishListModel> viewAllProductList=homePageModelsList.get(position).getViewAllProductList();
                List<HorizontalProductScrollModel> horizontalProductScrollModelList = homePageModelsList.get(position).getHorizontalProductScrollModelList();
                ((HorizontalScrollProductViewHolder) holder).setHorizontalProductLayout(horizontalProductScrollModelList, horizontalLayoutTitle, layoutColor,viewAllProductList);
                break;
            case HomePageModel.GRID_PRODUCT_VIEW:
                String gridLayoutTitle = homePageModelsList.get(position).getTitle();
                String gridLayoutColor=homePageModelsList.get(position).getBackgroundColor();
                List<HorizontalProductScrollModel> gridProductScrollModelList = homePageModelsList.get(position).getHorizontalProductScrollModelList();
                ((GridProductViewHolder) holder).setGridProductLayout(gridProductScrollModelList, gridLayoutTitle, gridLayoutColor);
                break;
            default:
        }

        if (lastPosition<position){
            Animation animation= AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.fade_in);
            holder.itemView.setAnimation(animation);
            lastPosition=position;
        }

    }

    @Override
    public int getItemCount() {
        return homePageModelsList.size();
    }

    public static class BannerSliderViewHolder extends RecyclerView.ViewHolder {

        final private long DELAY_TIME = 3000;
        final private long PERIOD_TIME = 3000;
        public Timer timer;
        private ViewPager bannerSlider;
        private int currentPage = 2;
        private List<SliderModel> arrangedList;

        public BannerSliderViewHolder(@NonNull View itemView) {
            super(itemView);
            bannerSlider = itemView.findViewById(R.id.bannerSliderViewPager);

        }

        private void pageLooper(List<SliderModel> sliderModelList) {
            if (currentPage == sliderModelList.size() - 2) {
                currentPage = 2;
                bannerSlider.setCurrentItem(currentPage, false);
            }
            if (currentPage == 1) {
                currentPage = sliderModelList.size() - 3;
                bannerSlider.setCurrentItem(currentPage, false);
            }

        }

        private void startBannerSliderShow(final List<SliderModel> sliderModelList) {
            final Handler handler = new Handler();
            final Runnable update = new Runnable() {
                @Override
                public void run() {
                    if (currentPage >= sliderModelList.size()) {
                        currentPage = 1;
                    }
                    bannerSlider.setCurrentItem(currentPage++, true);
                }
            };
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(update);
                }
            }, DELAY_TIME, PERIOD_TIME);
        }

        private void stopBannerSlideShow() {
            timer.cancel();
        }

        @SuppressLint("ClickableViewAccessibility")
        private void setBannerSliderViewPager(final List<SliderModel> sliderModelList) {
            currentPage=2;
            if(timer!=null){
                timer.cancel();
            }
            arrangedList=new ArrayList<>();

            for (int x=0; x<sliderModelList.size(); x++){
                arrangedList.add(x,sliderModelList.get(x));
            }
            arrangedList.add(0,sliderModelList.get(sliderModelList.size()-2));
            arrangedList.add(1,sliderModelList.get(sliderModelList.size()-1));
            arrangedList.add(sliderModelList.get(0));
            arrangedList.add(sliderModelList.get(1));


            SliderAdapter sliderAdapter = new SliderAdapter(arrangedList);
            bannerSlider.setAdapter(sliderAdapter);
            bannerSlider.setClipToPadding(false);
            bannerSlider.setPageMargin(20);
            bannerSlider.setCurrentItem(currentPage);

            ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    currentPage = position;

                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    if (state == ViewPager.SCROLL_STATE_IDLE) {
                        pageLooper(arrangedList);

                    }
                }
            };
            bannerSlider.addOnPageChangeListener(onPageChangeListener);

            startBannerSliderShow(arrangedList);
            bannerSlider.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    pageLooper(arrangedList);
                    stopBannerSlideShow();
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        startBannerSliderShow(arrangedList);
                    }
                    return false;
                }
            });

        }
    }

    public static class StripAdBannerViewHolder extends RecyclerView.ViewHolder {
        private ImageView stripAdImage;
        private ConstraintLayout stripAdContainer;

        public StripAdBannerViewHolder(@NonNull View itemView) {
            super(itemView);
            stripAdImage = itemView.findViewById(R.id.strip_ad_image);
            stripAdContainer = itemView.findViewById(R.id.strip_container);
        }

        private void setStripAd(String resource, String color) {
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.placeholder)).into(stripAdImage);
            stripAdContainer.setBackgroundColor(Color.parseColor(color));
        }
    }

    public class HorizontalScrollProductViewHolder extends RecyclerView.ViewHolder {
        private TextView horizontalLayoutTitle;
        private Button btnHorizontalAllItem;
        private RecyclerView horizontalScrollRecyclerView;
        private ConstraintLayout horizontal_product_container_layout;

        public HorizontalScrollProductViewHolder(@NonNull View itemView) {
            super(itemView);
            horizontal_product_container_layout = itemView.findViewById(R.id.horizontal_product_container_layout);
            horizontalLayoutTitle = itemView.findViewById(R.id.horizontal_scroll_layout_title);
            btnHorizontalAllItem = itemView.findViewById(R.id.horizontal_scroll_layout_view_all_button);
            horizontalScrollRecyclerView = itemView.findViewById(R.id.horizontal_scroll_layout_recyclerView);
            horizontalScrollRecyclerView.setRecycledViewPool(recycledViewPool);
        }

        private void setHorizontalProductLayout(List<HorizontalProductScrollModel> horizontalProductScrollModelList, final String title, String color, final List<WishListModel> viewAllProductList) {
            horizontal_product_container_layout.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));
            horizontalLayoutTitle.setText(title);
            if (horizontalProductScrollModelList.size() > 8) {
                btnHorizontalAllItem.setVisibility(View.VISIBLE);
               btnHorizontalAllItem.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       ViewAllActivity.wishListModelList=viewAllProductList;
                       Intent viewAllIntent=new Intent(itemView.getContext(), ViewAllActivity.class);
                       viewAllIntent.putExtra("VIEW_ALL", 0);
                       viewAllIntent.putExtra("title", title);
                       itemView.getContext().startActivity(viewAllIntent);
                   }
               });
            } else {
                btnHorizontalAllItem.setVisibility(View.INVISIBLE);
            }
            HorizontalProductScrollAdapter horizontalProductScrollAdapter = new HorizontalProductScrollAdapter(horizontalProductScrollModelList);
            LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(itemView.getContext());
            linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);

            horizontalScrollRecyclerView.setLayoutManager(linearLayoutManager1);
            horizontalScrollRecyclerView.setAdapter(horizontalProductScrollAdapter);
            horizontalProductScrollAdapter.notifyDataSetChanged();
        }
    }

    public static class GridProductViewHolder extends RecyclerView.ViewHolder{
        private TextView gridLayoutTitle;
        private Button btnGridAllItem;
        private GridLayout gridViewLayout;
        private ConstraintLayout gridProductProductContainerLayout;

        public GridProductViewHolder(@NonNull View itemView) {
            super(itemView);
            gridProductProductContainerLayout=itemView.findViewById(R.id.gridProductProductContainerLayout);
            gridLayoutTitle=itemView.findViewById(R.id.grid_product_layout_title);
            btnGridAllItem=itemView.findViewById(R.id.grid_product_layout_view_all_button);
            gridViewLayout=itemView.findViewById(R.id.grid_product_layout_grid_view);
        }
      private void   setGridProductLayout(final List<HorizontalProductScrollModel> horizontalProductScrollModelsList, final String title, String color) {

          gridLayoutTitle.setText(title);
          gridProductProductContainerLayout.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)));

          for (int x = 0; x < 4; x++) {
              ImageView productImage = gridViewLayout.getChildAt(x).findViewById(R.id.h_s_product_image);
              TextView productName = gridViewLayout.getChildAt(x).findViewById(R.id.h_s_product_title);
              TextView productDescription = gridViewLayout.getChildAt(x).findViewById(R.id.h_s_product_description);
              TextView productPrice = gridViewLayout.getChildAt(x).findViewById(R.id.h_s_product_price);

              // productImage.setImageResource(horizontalProductScrollModelsList.get(x).getProductImage());
              Glide.with(itemView.getContext()).load(horizontalProductScrollModelsList.get(x).getProductImage()).apply(new RequestOptions().placeholder(R.drawable.placeholder)).into(productImage);

              productName.setText(horizontalProductScrollModelsList.get(x).getProductTitle());
              productDescription.setText(horizontalProductScrollModelsList.get(x).getProductDescription());
              productPrice.setText("₹" + horizontalProductScrollModelsList.get(x).getProductPrice() + "/-");
              gridViewLayout.getChildAt(x).setBackgroundColor(Color.parseColor("#ffffff"));

              if (!title.equals("")) {
                  gridViewLayout.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View view) {
                          Intent productDetailsIntent = new Intent(itemView.getContext(), ProductsDetailsActivity.class);
                          itemView.getContext().startActivity(productDetailsIntent);
                      }
                  });
              }

          }


          if (!title.equals("")) {
              btnGridAllItem.setVisibility(View.VISIBLE);
              btnGridAllItem.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      ViewAllActivity.horizontalProductScrollModelList = horizontalProductScrollModelsList;
                      Intent viewAllIntent = new Intent(itemView.getContext(), ViewAllActivity.class);
                      viewAllIntent.putExtra("VIEW_ALL", 1);
                      viewAllIntent.putExtra("title", title);
                      itemView.getContext().startActivity(viewAllIntent);
                  }
              });
          }
      }
    }

}
