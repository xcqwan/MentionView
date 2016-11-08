package com.xcqwan.mentionview;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Thanks on 16/10/11.
 */

public class Mention implements Parcelable {
    public String relId;
    public String relName;

    public Mention() {

    }

    public Mention(String relId, String relName) {
        this.relId = relId;
        this.relName = relName;
    }

    public Mention(Parcel in) {
        relId = in.readString();
        relName = in.readString();
    }

    public static final Creator<Mention> CREATOR = new Creator<Mention>() {
        @Override
        public Mention createFromParcel(Parcel in) {
            return new Mention(in);
        }

        @Override
        public Mention[] newArray(int size) {
            return new Mention[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(relId);
        dest.writeString(relName);
    }

    @Override
    public String toString() {
        return relId + relName;
    }
}
