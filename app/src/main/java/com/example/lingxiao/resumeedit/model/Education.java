package com.example.lingxiao.resumeedit.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.lingxiao.resumeedit.Utils.DateUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by lingxiao on 3/20/18.
 */

public class Education implements Parcelable{
//    public String id;

    public String school;

    public Date startDate;

    public Date endDate;

    public List<String> courses;

    public Education(){}

    protected Education(Parcel in) {
        //id = in.readString();
        school = in.readString();
        startDate = DateUtils.stringToDate(in.readString());
        endDate = DateUtils.stringToDate(in.readString());
        courses = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //dest.writeString(id);
        dest.writeString(school);
        dest.writeString(DateUtils.dateToString(startDate));
        dest.writeString(DateUtils.dateToString(endDate));
        dest.writeStringList(courses);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Education> CREATOR = new Creator<Education>() {
        @Override
        public Education createFromParcel(Parcel in) {
            return new Education(in);
        }

        @Override
        public Education[] newArray(int size) {
            return new Education[size];
        }
    };
}
