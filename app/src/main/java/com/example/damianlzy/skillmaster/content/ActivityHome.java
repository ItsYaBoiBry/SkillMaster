package com.example.damianlzy.skillmaster.content;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.damianlzy.skillmaster.R;
import com.example.damianlzy.skillmaster.content.homefragments.FragmentHome;
import com.example.damianlzy.skillmaster.content.homefragments.FragmentNewsUpdate;
import com.example.damianlzy.skillmaster.content.homefragments.FragmentProfile;
import com.example.damianlzy.skillmaster.functions.Sessions;
import com.example.damianlzy.skillmaster.model.User;

public class ActivityHome extends AppCompatActivity {
    TextView menuName, menuOccupation, menuEmail, menuPhone, menuHome, menuNewsUpdate, menuProfile, menuLogout, toolabarTitle;
    DrawerLayout drawerLayout;
    FragmentTransaction ft;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        menuName=findViewById(R.id.menu_name);
        menuEmail = findViewById(R.id.menu_email);
        menuOccupation = findViewById(R.id.menu_occupation);
        menuPhone = findViewById(R.id.menu_phone);
        toolabarTitle = findViewById(R.id.toolbar_title);

        configureNavigationDrawer();
        configureToolbar();

        Sessions sessions = new Sessions(ActivityHome.this);
        User user = sessions.GetUser();
        menuName.setText(user.getUser_name());
        menuEmail.setText(user.getUser_email());
        menuOccupation.setText(user.getUser_occupation());
        menuPhone.setText(user.getUser_mobile());

        if(savedInstanceState==null){
            toolabarTitle.setText("Home");
            replacefragment(new FragmentHome());
            Log.e("Saved Instance Stance","NULL");
        }

    }
    public void replacefragment(Fragment fragment) {
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content, fragment);
        // or ft.add(R.id.your_placeholder, new FooFragment());
        // Complete the changes added above
        ft.commit();
    }
    private void configureToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        assert actionbar != null;
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionbar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void configureNavigationDrawer() {
        drawerLayout = findViewById(R.id.drawer_layout);
        menuHome = findViewById(R.id.menu_home);
        menuNewsUpdate = findViewById(R.id.menu_news_update);
        menuProfile = findViewById(R.id.menu_profile);
        menuLogout = findViewById(R.id.menu_logout);
        menuHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolabarTitle.setText("Home");
                replacefragment(new FragmentHome());
                CloseDrawer();
            }
        });
        menuNewsUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolabarTitle.setText("News Updates");
                replacefragment(new FragmentNewsUpdate());
                CloseDrawer();
            }
        });
        menuProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolabarTitle.setText("Profile");
                replacefragment(new FragmentProfile());
                CloseDrawer();
            }
        });
    }

    public void CloseDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    public void OpenDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            // Android home
            case android.R.id.home:
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    CloseDrawer();
                    return true;
                } else {
                    OpenDrawer();
                    return true;
                }
        }

        return false;
    }
}
