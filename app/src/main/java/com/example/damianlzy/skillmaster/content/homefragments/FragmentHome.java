package com.example.damianlzy.skillmaster.content.homefragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.damianlzy.skillmaster.R;
import com.example.damianlzy.skillmaster.content.ActivityCourseDetails;
import com.example.damianlzy.skillmaster.model.Courses;
import com.example.damianlzy.skillmaster.model.Links;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHome extends Fragment {
    TextView tabCourses, tabMyCourses;
    LinearLayout llAllCourses, llMyCourses;
    TextView tvResult;
    GridLayout glCourses;

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
        glCourses = view.findViewById(R.id.gl_courses);
        tvResult = view.findViewById(R.id.result);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            if (bundle.getString("courses").equalsIgnoreCase("")) {
                tvResult.setText("No Courses");
            } else {
                try {
                    JSONObject result = new JSONObject(bundle.getString("courses"));
                    if (result.has("success")) {
                        JSONArray courses = result.getJSONObject("success").getJSONArray("courses");
                        String rootfile = result.getJSONObject("success").getString("file_root_path");
                        for (int i = 0; i < courses.length(); i++) {
                            Courses c = new Courses();
                            JSONObject course = courses.getJSONObject(i);
                            glCourses.addView(CourseItem(new Courses(course.getInt(c.course_id_key)
                                    , course.getInt(c.course_category_id_key)
                                    , course.getString(c.course_name_key)
                                    , course.getString(c.course_instructor_key)
                                    , course.getString(c.course_course_fee_key)
                                    , course.getString(c.course_description_key)
                                    , course.getString(c.course_address_key)
                                    , course.getString(c.course_prod_picture_key)
                                    , course.getString(c.course_status_key)
                                    , course.getString(c.course_created_at_key)
                                    , course.getString(c.course_updated_at_key)),rootfile));
                        }
                    } else if (result.has("error")) {
                        tvResult.setText("No Courses");
                    } else {
                        tvResult.setText("No Courses");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            tvResult.setText("No Courses");
        }
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

    public void SetTab(int i) {
        if (i == 1) {
            tabCourses.setTextColor(getResources().getColor(R.color.buttonBlue));
            tabMyCourses.setTextColor(getResources().getColor(R.color.grey));
            llAllCourses.setVisibility(View.VISIBLE);
            llMyCourses.setVisibility(View.GONE);
        } else if (i == 2) {
            tabCourses.setTextColor(getResources().getColor(R.color.grey));
            tabMyCourses.setTextColor(getResources().getColor(R.color.buttonBlue));
            llAllCourses.setVisibility(View.GONE);
            llMyCourses.setVisibility(View.VISIBLE);
        }
    }

    public View CourseItem(final Courses courses, String filepath) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.courses_main, null);
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = (size.x/2)-90;
        LinearLayout llItem = view.findViewById(R.id.ll_item);
        ImageView ivCourse = view.findViewById(R.id.iv_course);
        ivCourse.setMaxWidth(width);
        ivCourse.setMinimumWidth(width);
        ivCourse.setMaxHeight(550);
        ivCourse.setMinimumHeight(550);
        TextView tvCourseTitle = view.findViewById(R.id.tv_course_title);
        TextView tvCourseFees = view.findViewById(R.id.tv_course_fees);
        tvCourseTitle.setMaxWidth(width);
        tvCourseTitle.setMinimumWidth(width);
        tvCourseFees.setMaxWidth(width);
        tvCourseFees.setMinimumWidth(width);
        Glide.with(getContext()).load(filepath+courses.getCourse_prod_picture()).into(ivCourse);
        tvCourseTitle.setText(courses.getCourse_name());
        tvCourseFees.setText("Course Fee: $"+courses.getCourse_course_fee());
        llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(new Intent(getContext(), ActivityCourseDetails.class).putExtra(courses.course_id_key,courses.getCourse_id()));
            }
        });
        return view;
    }
}
