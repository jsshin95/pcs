package org.techtown.pos;

import android.os.Parcel;
import android.os.Parcelable;

public class SimpleData implements Parcelable{

    String message;

    public SimpleData(String msg){

        message=msg;
    }

    public SimpleData(Parcel src){

        message=src.readString();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public SimpleData createFromParcel(Parcel in) {
            return new SimpleData(in);
        }

        public SimpleData[] newArray(int size) {
            return new SimpleData[size];
        }
    };

    public int describeContents(){
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(message);
    }
}
