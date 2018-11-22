package com.obaniu.inappfloatview;

import android.os.Parcel;
import android.os.Parcelable;

/*public*/ class SyncData implements Parcelable {
    final LayoutParams layoutParams;
    final String key;
    public SyncData( String key,LayoutParams layoutParams) {
        this.layoutParams = layoutParams;
        this.key = key;
    }


    protected SyncData(Parcel in) {
        layoutParams = in.readParcelable(LayoutParams.class.getClassLoader());
        key = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(layoutParams, flags);
        dest.writeString(key);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SyncData> CREATOR = new Creator<SyncData>() {
        @Override
        public SyncData createFromParcel(Parcel in) {
            return new SyncData(in);
        }

        @Override
        public SyncData[] newArray(int size) {
            return new SyncData[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SyncData syncData = (SyncData) o;

        if (layoutParams != null ? !layoutParams.equals(syncData.layoutParams) : syncData.layoutParams != null)
            return false;
        return key.equals(syncData.key);
    }

    @Override
    public int hashCode() {
        int result = layoutParams != null ? layoutParams.hashCode() : 0;
        result = 31 * result + key.hashCode();
        return result;
    }
    @Override
    public String toString() {
        return "{" +
                "layoutParams=" + layoutParams +
                ", key='" + key + '\'' +
                '}';
    }
}
