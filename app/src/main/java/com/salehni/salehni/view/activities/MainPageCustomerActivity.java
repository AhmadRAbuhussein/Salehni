package com.salehni.salehni.view.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.salehni.salehni.view.fragments.CustomRequestFragment;
import com.salehni.salehni.view.fragments.QuickRequestFragment;
import com.salehni.salehni.R;

public class MainPageCustomerActivity extends AppCompatActivity {

    ListView leftDrawer;
    Button menu_Btn;
    //ListViewDrawerAdapter listViewDrawerAdapter;
    DrawerLayout drawerLayout;
    FrameLayout mainFrameLayout;
    public static TextView title_Tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main_page);

        leftDrawer = findViewById(R.id.left_drawer);
        drawerLayout = findViewById(R.id.drawer_layout);
        menu_Btn = findViewById(R.id.menu_Btn);
        mainFrameLayout = findViewById(R.id.mainFrameLayout);
        title_Tv = findViewById(R.id.title_Tv);

        CustomRequestFragment customRequestFragment = new CustomRequestFragment();
        setFragment(customRequestFragment);

//        menu_Btn.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////
////
////                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
////                    drawerLayout.closeDrawer(GravityCompat.START);
////                } else {
////                    drawerLayout.openDrawer(GravityCompat.START);
////
////                }
////            }
////        });
    }

    public void setFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.mainFrameLayout, fragment, null);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}