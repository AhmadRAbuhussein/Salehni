package com.salehni.salehni.view.activities;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.salehni.salehni.data.model.DrawerItemModel;
import com.salehni.salehni.data.model.SignInTokenModel;
import com.salehni.salehni.util.Constants;
import com.salehni.salehni.util.Global;
import com.salehni.salehni.util.TinyDB;
import com.salehni.salehni.view.adapters.ListViewDrawerAdapter;
import com.salehni.salehni.view.fragments.CustomRequestFragment;
import com.salehni.salehni.view.fragments.Drawer.ContactUsFragment;
import com.salehni.salehni.view.fragments.Drawer.MessagesFragment;
import com.salehni.salehni.view.fragments.Drawer.MyAccountFragment;
import com.salehni.salehni.view.fragments.Drawer.PrivacyPolicyFragment;
import com.salehni.salehni.R;
import com.salehni.salehni.view.fragments.Drawer.TermsConditionFragment;
import com.salehni.salehni.view.fragments.MechanicNotificationFragment;

import java.util.ArrayList;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class MainPageCustomerActivity extends AppCompatActivity {

    ListView leftDrawer;
    LinearLayout menu_Btn;
    ListViewDrawerAdapter listViewDrawerAdapter;
    DrawerLayout drawerLayout;
    FrameLayout mainFrameLayout;
    public static TextView title_Tv;

    PopupWindow popupWindow;

    SignInTokenModel signInTokenModel;
    TinyDB tinyDB;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_main_page);

        tinyDB = new TinyDB(this);
        signInTokenModel = tinyDB.getObject(Constants.login_token, SignInTokenModel.class);
        initialFirstFragment();

        leftDrawer = findViewById(R.id.left_drawer);
        drawerLayout = findViewById(R.id.drawer_layout);
        menu_Btn = findViewById(R.id.menu_Btn);
        mainFrameLayout = findViewById(R.id.mainFrameLayout);
        title_Tv = findViewById(R.id.title_Tv);


        menu_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);

                }
            }
        });
        setDrawer();
    }

    private void setDrawer() {

        String[] titles = {getResources().getString(R.string.my_prof),
                getResources().getString(R.string.messages),
                getResources().getString(R.string.terms_conditions),
                getResources().getString(R.string.privacy_policy),
                getResources().getString(R.string.contact_us),
                getResources().getString(R.string.language),
                getResources().getString(R.string.logout)};

        int[] icons = {R.drawable.my_account_icon,
                R.drawable.messages,
                R.drawable.terms_conditions_icon,
                R.drawable.privacy_policy_icon,
                R.drawable.contacts_icon,
                R.drawable.white_language_icon,
                R.drawable.logout_icon};

        ArrayList<DrawerItemModel> drawerListArray = new ArrayList<>();

        for (int i = 0; i < titles.length; i++) {

            DrawerItemModel drawerItemsModel = new DrawerItemModel();
            drawerItemsModel.setDrawerText(titles[i]);
            drawerItemsModel.setDrawerIcon(icons[i]);

            drawerListArray.add(drawerItemsModel);
        }

        listViewDrawerAdapter = new ListViewDrawerAdapter(this, drawerListArray);

        if (leftDrawer.getHeaderViewsCount() > 0) {

        } else {
            LayoutInflater inflater = getLayoutInflater();
            ViewGroup header = (ViewGroup) inflater.inflate(R.layout.menu_toolbar, leftDrawer, false);

            leftDrawer.addHeaderView(header);

        }

        leftDrawer.setAdapter(listViewDrawerAdapter);

        leftDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                if (position == 1) {
                    MyAccountFragment myAccountFragment = new MyAccountFragment();
                    replaceFragment(myAccountFragment, "myAccountFragment");
                } else if (position == 2) {
                    MessagesFragment messagesFragment = new MessagesFragment();
                    replaceFragment(messagesFragment, "messagesFragment");
                } else if (position == 3) {
                    TermsConditionFragment termsConditionFragment = new TermsConditionFragment();
                    replaceFragment(termsConditionFragment, "termsConditionFragment");
                } else if (position == 4) {
                    PrivacyPolicyFragment privacyPolicyFragment = new PrivacyPolicyFragment();
                    replaceFragment(privacyPolicyFragment, "privacyPolicyFragment");
                } else if (position == 5) {
                    ContactUsFragment contactUsFragment = new ContactUsFragment();
                    replaceFragment(contactUsFragment, "contactUsFragment");
                } else if (position == 6) {
                    langPopup();
                } else if (position == 7) {
                    logoutPopup();
                }

            }
        });

    }

//    private void addFragment(Fragment fragment, String tag) {
//
//        FragmentManager manager = getSupportFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();
//        transaction.add(R.id.mainFrameLayout, fragment, tag);
//        transaction.addToBackStack(tag);
//        transaction.commit();
//    }

    private void replaceFragment(Fragment fragment, String tag) {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.mainFrameLayout, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void initialFirstFragment() {

//        if (signInTokenModel.getUser_type() == 1) {
        CustomRequestFragment customRequestFragment = new CustomRequestFragment();
        replaceFragment(customRequestFragment, "customRequestFragment");
//        } else {
//            MechanicNotificationFragment mechanicNotificationFragment = new MechanicNotificationFragment();
//            replaceFragment(mechanicNotificationFragment, "mechanicNotificationFragment");
//        }
    }

    private void logoutPopup() {

        Display display = this.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.popup_logout, null);

        popupWindow = new PopupWindow(layout);
        popupWindow.setWidth(width - 30);
        popupWindow.setHeight(height - 20);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.showAtLocation(this.getWindow().getDecorView(), Gravity.CENTER, 0, 0);

        Button yes_Btn = (Button) layout.findViewById(R.id.yes_Btn);
        Button no_Btn = (Button) layout.findViewById(R.id.no_Btn);


//        yes_Btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SharedPreferences.Editor editor = sharedpreferences.edit();
//
//                editor.putBoolean(Constants.isLogin, false);
//                editor.commit();
//
//                Intent i = new Intent(MainScreenActivity.this, LoginActivity.class);
//                startActivity(i);
//                finish();
//                popupWindow.dismiss();
//            }
//        });
        no_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

        Global.dimBehind(popupWindow);
    }


    private void langPopup() {

        Display display = this.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.popup_language, null);

        popupWindow = new PopupWindow(layout);
        popupWindow.setWidth(width - 30);
        popupWindow.setHeight(height - 20);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.showAtLocation(this.getWindow().getDecorView(), Gravity.CENTER, 0, 0);

        Button eng_Btn = (Button) layout.findViewById(R.id.eng_Btn);
        Button ar_Btn = (Button) layout.findViewById(R.id.ar_Btn);


//        eng_Btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Global.setLang(MainScreenActivity.this, Constants.english_Language);
//                SharedPreferences.Editor editor = sharedpreferences.edit();
//
//                editor.putBoolean(Constants.isLang, true);
//                editor.putString(Constants.lang, Constants.english_Language);
//                editor.commit();
//
//                Intent i = new Intent(MainScreenActivity.this, SplashScreenActivity.class);
//                startActivity(i);
//                finish();
//
//                popupWindow.dismiss();
//            }
//        });
//        ar_Btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Global.setLang(MainScreenActivity.this, Constants.arabic_Language);
//                SharedPreferences.Editor editor = sharedpreferences.edit();
//
//                editor.putBoolean(Constants.isLang, true);
//                editor.putString(Constants.lang, Constants.arabic_Language);
//                editor.commit();
//
//                Intent i = new Intent(MainScreenActivity.this, SplashScreenActivity.class);
//                startActivity(i);
//                finish();
//                popupWindow.dismiss();
//            }
//        });

        Global.dimBehind(popupWindow);
    }

    public void clearStack() {
        //Here we are clearing back stack fragment entries
        int backStackEntry = getSupportFragmentManager().getBackStackEntryCount();
        if (backStackEntry > 0) {
            for (int i = 0; i < backStackEntry; i++) {
                getSupportFragmentManager().popBackStackImmediate();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {

//            if(!BackFragmentHelper.fireOnBackPressedEvent(this)) {
//                // lets do the default back action if fragments don't consume it
//                if (doubleBackToExitPressedOnce) {
//                    super.onBackPressed();
//                    return;
//                }
//
//                this.doubleBackToExitPressedOnce = true;
//                Global.toast(MainPageCustomerActivity.this, getResources().getString(R.string.clickAgainToExit));
//
//                new Handler().postDelayed(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        doubleBackToExitPressedOnce=false;
//                    }
//                }, 2000);
//            }

            FragmentManager fm = getSupportFragmentManager();
            if (fm.getBackStackEntryCount() > 1) {
                fm.popBackStack();
            } else {
                super.onBackPressed();
                finish();
            }
        }
    }
}