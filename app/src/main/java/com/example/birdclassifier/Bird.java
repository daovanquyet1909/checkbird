package com.example.birdclassifier;

import android.os.Parcel;
import android.os.Parcelable;

public class Bird implements Parcelable {
    public String common_name;
    public String scientific_name;
    public String family;
    public String genous;
    public String img1;
    public String img2;
    public String img3;
    public String order;

    public Bird() {
    }

    public Bird(String common_name, String scientific_name, String family, String genous, String img1, String img2, String img3, String order) {
        this.common_name = common_name;
        this.scientific_name = scientific_name;
        this.family = family;
        this.genous = genous;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.order = order;
    }

    protected Bird(Parcel in) {
        common_name = in.readString();
        scientific_name = in.readString();
        family = in.readString();
        genous = in.readString();
        img1 = in.readString();
        img2 = in.readString();
        img3 = in.readString();
        order = in.readString();
    }

    public static final Creator<Bird> CREATOR = new Creator<Bird>() {
        @Override
        public Bird createFromParcel(Parcel in) {
            return new Bird(in);
        }

        @Override
        public Bird[] newArray(int size) {
            return new Bird[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(common_name);
        dest.writeString(scientific_name);
        dest.writeString(family);
        dest.writeString(genous);
        dest.writeString(img1);
        dest.writeString(img2);
        dest.writeString(img3);
        dest.writeString(order);
    }
}
