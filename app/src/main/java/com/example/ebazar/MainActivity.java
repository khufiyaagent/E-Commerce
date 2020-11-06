package com.example.ebazar;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;

import static com.example.ebazar.RegisterActivity.setSignUpFragmentVariable;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int HOME_FRAGMENT = 0;
    private static final int CART_FRAGMENT = 1;
    private static final int ORDERS_FRAGMENT = 2;
    private static final int MY_WISH_LIST_FRAGMENT = 3;
    private static final int REWARD_FRAGMENT = 4;
    private static final int MY_ACCOUNT_FRAGMENT = 5;
    public static Boolean showCart=false;
    private  int currentFragment=-1;
    private NavigationView navigationView;
    private FrameLayout frameLayout;

    private ImageView actionBarLogo;
    private Window window;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        actionBarLogo = findViewById(R.id.actionBar_logo);
        setSupportActionBar(toolbar);

        window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);


        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        frameLayout = findViewById(R.id.main_frame_layout);


        if (showCart) {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            gotoFragment("My Cart", new MyCartFragment(), -2);
        } else {
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            setFragment(new HomeFragment(), HOME_FRAGMENT);
        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (currentFragment==HOME_FRAGMENT){
                currentFragment=-1;
                super.onBackPressed();
            }else {
                if (showCart){
                    showCart=false;
                    finish();
                }else {
                    actionBarLogo.setVisibility(View.VISIBLE);
                    invalidateOptionsMenu();
                    setFragment(new HomeFragment(), HOME_FRAGMENT);
                    navigationView.getMenu().getItem(0).setChecked(true);
                }

            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (currentFragment == HOME_FRAGMENT) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getMenuInflater().inflate(R.menu.main, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.main_search_icon) {
            //todo: search
            return true;
        } else if (id == R.id.main_notification_icon) {
            //todo: notification
            return true;
        } else if (id == R.id.main_cart_icon) {
            final Dialog signInDialog=new Dialog(MainActivity.this);
            signInDialog.setContentView(R.layout.sign_in_dialog);
            signInDialog.setCancelable(true);
            signInDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            Button dialogSignInBtn=signInDialog.findViewById(R.id.signInBtn);
            Button dialogSignUpBtn=signInDialog.findViewById(R.id.signUpBtn);
            final Intent signInSignUpIntent=new Intent(MainActivity.this, RegisterActivity.class);

            dialogSignInBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    signInDialog.dismiss();;
                    setSignUpFragmentVariable=false;
                    startActivity(signInSignUpIntent);
                }
            });

            dialogSignUpBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    signInDialog.dismiss();
                    setSignUpFragmentVariable=true;
                    startActivity(signInSignUpIntent);
                }
            });
            signInDialog.show();
            //gotoFragment("My Cart", new MyCartFragment(), CART_FRAGMENT);
            return true;
        }else if (id==android.R.id.home){
            if (showCart){
                showCart=false;
                finish();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void gotoFragment(String title, Fragment fragment, int fragmentNo) {
        actionBarLogo.setVisibility(View.INVISIBLE);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(title);
        invalidateOptionsMenu();
        setFragment(fragment, fragmentNo);
        if (fragmentNo==CART_FRAGMENT) {
            navigationView.getMenu().getItem(3).setChecked(true);
        }


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_myMall) {
            actionBarLogo.setVisibility(View.VISIBLE);
            invalidateOptionsMenu();
            setFragment(new HomeFragment(), HOME_FRAGMENT);
        } else if (id == R.id.nav_myOrder) {
            gotoFragment("My Order", new MyOrdersFragment(),ORDERS_FRAGMENT);
        } else if (id == R.id.nav_myRewards) {
            gotoFragment("My Rewards", new MyRewardsFragment(), REWARD_FRAGMENT);
        } else if (id == R.id.nav_myCart) {
            gotoFragment("My Cart", new MyCartFragment(), CART_FRAGMENT);
        } else if (id == R.id.nav_myWishlist) {
            gotoFragment("My Wish List", new MyWishListFragment(), MY_WISH_LIST_FRAGMENT);
        } else if (id == R.id.nav_myAccount) {
            gotoFragment("My Account", new MyAccountFragment(), MY_ACCOUNT_FRAGMENT);
        } else if (id == R.id.nav_Signout) {

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void setFragment(Fragment fragment, int fragmentNo) {
        if (fragmentNo != currentFragment) {
            if (fragmentNo==REWARD_FRAGMENT){
                window.setStatusBarColor(Color.parseColor("#3006ba"));
                toolbar.setBackgroundColor(Color.parseColor("#3006ba"));
            }else {
                window.setStatusBarColor(Color.parseColor("#ff9933"));
                toolbar.setBackgroundColor(Color.parseColor("#ff9933"));
            }
            currentFragment = fragmentNo;
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            fragmentTransaction.replace(frameLayout.getId(), fragment);
            fragmentTransaction.commit();
        }
    }
}