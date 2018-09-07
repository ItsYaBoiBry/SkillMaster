package com.example.damianlzy.skillmaster.content.homefragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.damianlzy.skillmaster.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHome extends Fragment {
    TextView tabCourses,tabMyCourses;
    LinearLayout llAllCourses,llMyCourses;


    public FragmentHome() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabCourses = view.findViewById(R.id.tab_courses);
        tabMyCourses = view.findViewById(R.id.tab_my_courses);
        llAllCourses = view.findViewById(R.id.ll_all_courses);
        llMyCourses = view.findViewById(R.id.ll_my_courses);
        SetTab(1);
        tabCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetTab(1);
            }
        });
        tabMyCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetTab(2);
            }
        });

    }
    public void SetTab(int i){
        if(i == 1){
            tabCourses.setTextColor(getResources().getColor(R.color.buttonBlue));
            tabMyCourses.setTextColor(getResources().getColor(R.color.grey));
            llAllCourses.setVisibility(View.VISIBLE);
            llMyCourses.setVisibility(View.GONE);
        }else if(i ==2){
            tabCourses.setTextColor(getResources().getColor(R.color.grey));
            tabMyCourses.setTextColor(getResources().getColor(R.color.buttonBlue));
            llAllCourses.setVisibility(View.GONE);
            llMyCourses.setVisibility(View.VISIBLE);
        }
    }
}
