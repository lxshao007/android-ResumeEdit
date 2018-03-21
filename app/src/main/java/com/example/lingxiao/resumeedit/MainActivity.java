package com.example.lingxiao.resumeedit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lingxiao.resumeedit.Utils.DateUtils;
import com.example.lingxiao.resumeedit.model.BasicInfo;
import com.example.lingxiao.resumeedit.model.Education;
import com.example.lingxiao.resumeedit.model.Experience;
import com.example.lingxiao.resumeedit.model.Project;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BasicInfo basicInfo;
    private List<Education> educations;
    private List<Experience> experiences;
    private List<Project> projects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        fakeDate();
        setupUI();
    }

    private void fakeDate() {
        //basic
        basicInfo = new BasicInfo();
        basicInfo.name = "Lingxiao Shao";
        basicInfo.email = "lingxiaoahao@gmail.com";

        //education
        educations = new ArrayList<>();
        Education education1 = new Education();
        education1.school = "University of Delaware";
        education1.startDate = DateUtils.stringToDate("08/2014");
        education1.endDate = DateUtils.stringToDate("12/2016");
        education1.courses = new ArrayList<>();
        education1.courses.add("statistic");
        education1.courses.add("food microbiology");

        Education education2 = new Education();
        education2.school = "Zhejiang University";
        education2.startDate = DateUtils.stringToDate("08/2010");
        education2.endDate = DateUtils.stringToDate("07/2014");
        education2.courses = new ArrayList<>();
        education2.courses.add("c programming");
        education2.courses.add("calculus");

        educations.add(education1);
        educations.add(education2);
    }

    private void setupUI() {
        setContentView(R.layout.activity_main);

        setupEducationUI();

    }

    private void setupEducationUI() {
        LinearLayout educationLinearLayout = (LinearLayout) findViewById(R.id.education_item);

        for(Education education : educations) {
            educationLinearLayout.addView(getEducationView(education));
        }
    }

    private View getEducationView(Education education) {
        View view = getLayoutInflater().inflate(R.layout.education_item, null);
        String dateString = DateUtils.dateToString(education.startDate) +
                            "~" + DateUtils.dateToString(education.endDate);
        ((TextView) view.findViewById(R.id.education_school)).setText(education.school + "(" + dateString + ")");
        ((TextView) view.findViewById(R.id.education_course)).setText(formatItem(education.courses));
        return view;
    }

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
