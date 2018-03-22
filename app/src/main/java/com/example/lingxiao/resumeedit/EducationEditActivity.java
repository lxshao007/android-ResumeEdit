package com.example.lingxiao.resumeedit;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.lingxiao.resumeedit.Utils.DateUtils;
import com.example.lingxiao.resumeedit.model.Education;

import org.w3c.dom.Text;

import java.util.Arrays;

public class EducationEditActivity extends AppCompatActivity {
    public static final String KEY_EDUCATION = "education";
    private Education education;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education_edit);
        //back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        education = getIntent().getParcelableExtra(KEY_EDUCATION);

        if (education != null) setupUI();
        setTitle(education == null ? "New Education" : "Edit Education");



    }

    private void setupUI() {
        ((EditText) findViewById(R.id.education_edit_school)).setText(education.school);
        ((EditText) findViewById(R.id.education_edit_starttime)).
                setText(DateUtils.dateToString(education.startDate));
        ((EditText) findViewById(R.id.education_edit_endtime)).
                setText(DateUtils.dateToString(education.endDate));
        ((EditText) findViewById(R.id.education_edit_courses)).
                setText(TextUtils.join("\n", education.courses));
    }

    //setup view
    private void setupEditEducationView() {

    }

    //add save button view on menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    //menu selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.ic_save:
                saveAndExit();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //collect data
    private void saveAndExit() {
        //edit education condition education != null
        if (education == null) {
            education = new Education();
        }

        education.school = ((EditText) findViewById(R.id.education_edit_school)).getText().toString();
        education.startDate = DateUtils.stringToDate
                (((EditText) findViewById(R.id.education_edit_starttime)).getText().toString());
        education.endDate = DateUtils.stringToDate
                (((EditText) findViewById(R.id.education_edit_endtime)).getText().toString());
        education.courses = Arrays.asList(TextUtils.split(((EditText)
                findViewById(R.id.education_edit_courses)).getText().toString(), "\n"));

        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_EDUCATION, education);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();

    }




}
