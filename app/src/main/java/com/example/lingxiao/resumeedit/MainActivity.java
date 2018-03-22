package com.example.lingxiao.resumeedit;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lingxiao.resumeedit.Utils.DateUtils;
import com.example.lingxiao.resumeedit.Utils.ModelUtils;
import com.example.lingxiao.resumeedit.model.BasicInfo;
import com.example.lingxiao.resumeedit.model.Education;
import com.example.lingxiao.resumeedit.model.Experience;
import com.example.lingxiao.resumeedit.model.Project;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BasicInfo basicInfo;
    private List<Education> educations;
    private List<Experience> experiences;
    private List<Project> projects;

    private static final int REQ_CODE_EDUCATION_EDIT = 100;
    private static final String MODEL_EDUCATIONS = "education";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData();
        setupUI();


    }

    private void loadData() {
        List<Education> savedEducation = ModelUtils.read(this, MODEL_EDUCATIONS, new TypeToken<List<Education>>(){});
        educations = savedEducation == null ? new ArrayList<Education>() : savedEducation;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_EDUCATION_EDIT && resultCode == Activity.RESULT_OK) {
            String educationID = data.getStringExtra(EducationEditActivity.KEY_EDUCATION_ID);
            if (educationID != null) {
                deleteEducation(educationID);
            } else {
                Education education = data.getParcelableExtra(EducationEditActivity.KEY_EDUCATION);
                updateEducation(education);
            }
        }
    }

    private void setupUI() {
        setContentView(R.layout.activity_main);

        ImageButton addEducationButton = (ImageButton) findViewById(R.id.add_education);
        addEducationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EducationEditActivity.class);
                startActivityForResult(intent, REQ_CODE_EDUCATION_EDIT);
            }
        });

        setupEducationUI();
    }

    // add all education view to education linearlayout view
    private void setupEducationUI() {
        LinearLayout educationLinearLayout = (LinearLayout) findViewById(R.id.education_item);
        educationLinearLayout.removeAllViews();
        for(Education education : educations) {
            educationLinearLayout.addView(getEducationView(education));
        }
    }
    //get one education item view
    private View getEducationView(final Education education) {
        View view = getLayoutInflater().inflate(R.layout.education_item, null);
        String dateString = DateUtils.dateToString(education.startDate) +
                            "~" + DateUtils.dateToString(education.endDate);
        ((TextView) view.findViewById(R.id.education_school)).setText(education.school + "(" + dateString + ")");
        ((TextView) view.findViewById(R.id.education_course)).setText(formatItem(education.courses));


        view.findViewById(R.id.edit_education).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EducationEditActivity.class);
                intent.putExtra(EducationEditActivity.KEY_EDUCATION, education);
                startActivityForResult(intent, REQ_CODE_EDUCATION_EDIT);
            }
        });
        return view;
    }

    //edit
    private void updateEducation(Education education) {
        boolean found = false;
        for(int i = 0; i < educations.size(); ++i) {
            if (TextUtils.equals(educations.get(i).id, education.id)) { //must use TextUtils.equals to compare
                educations.set(i, education);
                found = true;
            }
        }
        if (!found) {
            educations.add(education);
        }
        ModelUtils.save(this, MODEL_EDUCATIONS, educations);
        setupEducationUI();
    }


    //delete
    private void deleteEducation(String educationID) {
        for(int i = 0; i < educations.size(); ++i) {
            if (TextUtils.equals(educationID, educations.get(i).id)) {
                educations.remove(i);
                break;
            }
        }
        ModelUtils.save(this, MODEL_EDUCATIONS, educations);
        setupEducationUI();
    }

    //transform courses List to string format
    private String formatItem(List<String> item) {
        StringBuilder sb = new StringBuilder();
        for (String s: item) {
            sb.append("-");
            sb.append(s);
            sb.append("\n");
        }
        return sb.toString();
    }
}
