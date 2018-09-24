package com.example.damianlzy.skillmaster.content;

import android.content.Intent;
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
import android.widget.TextView;

import com.example.damianlzy.skillmaster.MainActivity;
import com.example.damianlzy.skillmaster.R;
import com.example.damianlzy.skillmaster.content.homefragments.FragmentHome;
import com.example.damianlzy.skillmaster.content.homefragments.FragmentNewsUpdate;
import com.example.damianlzy.skillmaster.content.homefragments.FragmentProfile;
import com.example.damianlzy.skillmaster.functions.SendRequest;
import com.example.damianlzy.skillmaster.functions.Sessions;
import com.example.damianlzy.skillmaster.model.Links;
import com.example.damianlzy.skillmaster.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class ActivityHome extends AppCompatActivity {
    TextView menuName, menuOccupation, menuEmail, menuPhone, menuHome, menuProfile, menuLogout, toolabarTitle;
    DrawerLayout drawerLayout;
    FragmentTransaction ft;
    Toolbar toolbar;
    String getcourses;

    Links links = new Links();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        menuName=findViewById(R.id.menu_name);
        menuEmail = findViewById(R.id.menu_email);
        menuOccupation = findViewById(R.id.menu_occupation);
        menuPhone = findViewById(R.id.menu_phone);
        toolabarTitle = findViewById(R.id.toolbar_title);
        SendRequest request = new SendRequest();
        Sessions sessions = new Sessions(ActivityHome.this);
        try {
            getcourses = request.execute(links.CoursesLink(),"",sessions.GetToken()).get();
            JSONObject result = new JSONObject(getcourses);
            if(result.has(getString(R.string.success))){

            }else if(result.has(getString(R.string.error))){

            }else{
                //unexpected error
            }
            Log.e("COURSES",getcourses);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        configureNavigationDrawer();
        configureToolbar();

        User user = sessions.GetUser();
        menuName.setText(user.getUser_name());
        menuEmail.setText(user.getUser_email());
        menuOccupation.setText(user.getUser_occupation());
        menuPhone.setText(user.getUser_mobile());

        if(savedInstanceState==null){
            toolabarTitle.setText("Courses");
            Bundle bundle = new Bundle();
            bundle.putString("courses",getcourses);
            replacefragment(new FragmentHome(),bundle);
            Log.e("Saved Instance Stance","NULL");
        }
    }
    public void replacefragment(Fragment fragment, Bundle bundle) {
        fragment.setArguments(bundle);
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
//        menuNewsUpdate = findViewById(R.id.menu_news_update);
        menuProfile = findViewById(R.id.menu_profile);
        menuLogout = findViewById(R.id.menu_logout);
        menuHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolabarTitle.setText("Home");
                Bundle bundle = new Bundle();
                bundle.putString("courses",getcourses);
                replacefragment(new FragmentHome(),bundle);
                CloseDrawer();
            }
        });
//        menuNewsUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                toolabarTitle.setText("News Updates");
//                replacefragment(new FragmentNewsUpdate(),null);
//                CloseDrawer();
//            }
//        });
        menuProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolabarTitle.setText("Profile");
                replacefragment(new FragmentProfile(),null);
                CloseDrawer();
            }
        });
        menuLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sessions sessions = new Sessions(ActivityHome.this);
                sessions.Logout();
                startActivity(new Intent(ActivityHome.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
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
