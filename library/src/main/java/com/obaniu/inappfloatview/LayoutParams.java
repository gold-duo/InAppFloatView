package com.obaniu.inappfloatview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
/**
 * Created by obaniu on 2018/12/19.
 */
public class LayoutParams extends FrameLayout.LayoutParams implements Parcelable {
    //internal fields
    int layoutId;
    private static final int MASK_EQ=0x1F;//does not contain MultiProcess flag
    private static final int MASK_VISIBILITY=0XF;
    private int maskValue = View.VISIBLE;
    public LayoutParams(Context c, AttributeSet attrs) {
        super(c, attrs);
        throw new UnsupportedOperationException("");
    }

    public LayoutParams(int width, int height) {
        super(width, height);
    }

    public LayoutParams(int width, int height, int gravity) {
        super(width, height, gravity);
    }

    public LayoutParams(ViewGroup.LayoutParams source) {
        super(source);
    }

    public LayoutParams(ViewGroup.MarginLayoutParams source) {
        super(source);
    }

    @TargetApi(19)
    public LayoutParams(FrameLayout.LayoutParams source) {
        super(source);
    }

    public LayoutParams() {
        this(WRAP_CONTENT, WRAP_CONTENT);
    }

    public boolean isRemoved() {
        return  (maskValue &(1<<5))!=0;
    }
    public void setRemoved(boolean val) {
        if(val) {
            maskValue |=(1<<5);
        }else {
            maskValue &=~(1<<5);
        }
    }
    public int getVisibility() {
        return maskValue & MASK_VISIBILITY;
    }

    public void setVisibility(int val) {
        maskValue = maskValue | (MASK_VISIBILITY & val);
    }

    public boolean isMultiProcess() {
        return (maskValue & (1 << 6)) != 0;
    }
    public void setMultiProcess(boolean val) {
        if(val) {
            maskValue |=(1<<6);
        }else {
            maskValue &=~(1<<6);
        }
    }
    public void copy(LayoutParams that) {
        gravity = that.gravity;
        width = that.width;
        height = that.height;
        leftMargin = that.leftMargin;
        rightMargin = that.rightMargin;
        topMargin = that.topMargin;
        bottomMargin = that.bottomMargin;
        if (Build.VERSION.SDK_INT >= 17) {
            int val;
            if (getLayoutDirection() != (val = that.getLayoutDirection())) setLayoutDirection(val);
            if (getMarginStart() != (val = that.getMarginStart())) setMarginStart(val);
            if (getMarginEnd() != (val = that.getMarginEnd())) setMarginEnd(val);
        }
        maskValue |= (that.maskValue & 0x1F);
    }

    boolean partialEquals(LayoutParams that){
        if (this == that) return true;
        if (that == null ) return false;
        if (width != that.width) return false;
        if (height != that.height) return false;
        if (gravity != that.gravity) return false;
        if (leftMargin != that.leftMargin) return false;
        if (rightMargin != that.rightMargin) return false;
        if (topMargin != that.topMargin) return false;
        if (bottomMargin != that.bottomMargin) return false;
        if (Build.VERSION.SDK_INT >= 17) {
            if (getLayoutDirection() != that.getLayoutDirection()) return false;
            if (getMarginStart() != that.getMarginStart()) return false;
            if (getMarginEnd() != that.getMarginEnd()) return false;
        }
        return (maskValue & MASK_EQ) == (that.maskValue & MASK_EQ);
    }
    int partialHashCode(){
        int result = width;
        result = 31 * result + height;
        result = 31 * result + gravity;
        result = 31 * result + leftMargin;
        result = 31 * result + rightMargin;
        result = 31 * result + topMargin;
        result = 31 * result + bottomMargin;
        if (Build.VERSION.SDK_INT >= 17) {
            result = 31 * result + getLayoutDirection();
            result = 31 * result + getMarginStart();
            result = 31 * result + getMarginEnd();
        }
        result = 31 * result + (maskValue & MASK_EQ);
        return result;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LayoutParams that = (LayoutParams) o;
        if(!partialEquals(that)) return false;
        if (layoutId != that.layoutId) return false;
        return maskValue == that.maskValue;
    }

    @Override
    public int hashCode() {
        int result = partialHashCode();
        result = 31 * result + layoutId;
        result = 31 * result + (isMultiProcess() ? 1:0);
        return result;
    }

    @Override
    public String toString() {
        return "{" +
                "width=" + width +
                ", height=" + height +
                ", gravity=" + gravity +
                ", leftMargin=" + leftMargin +
                ", rightMargin=" + rightMargin +
                ", topMargin=" + topMargin +
                ", bottomMargin=" + bottomMargin +
                ", layoutId=" + layoutId +
                ", multiProcess=" + isMultiProcess() +
                ", visibility="+getVisibility()+
                ", removed=" + isRemoved() +
                ", maskValue=" + maskValue +
                '}';
    }

    public static final Creator<LayoutParams> CREATOR = new Creator<LayoutParams>() {
        @Override
        public LayoutParams createFromParcel(Parcel in) {
            return new LayoutParams(in);
        }

        @Override
        public LayoutParams[] newArray(int size) {
            return new LayoutParams[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @TargetApi(17)
    protected LayoutParams(Parcel in) {
        this();
        width = in.readInt();
        height = in.readInt();
        gravity = in.readInt();
        leftMargin = in.readInt();
        rightMargin = in.readInt();
        topMargin = in.readInt();
        bottomMargin = in.readInt();
        if (Build.VERSION.SDK_INT >= 17) {
            int val;
            if ((val = in.readInt()) != getMarginStart()) setMarginStart(val);
            if ((val = in.readInt()) != getMarginEnd()) setMarginEnd(val);
            if ((val = in.readInt()) != getLayoutDirection()) setLayoutDirection(val);
        }
        layoutId = in.readInt();
        maskValue =in.readInt();
    }

    @Override
    @TargetApi(17)
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeInt(gravity);
        dest.writeInt(leftMargin);
        dest.writeInt(rightMargin);
        dest.writeInt(topMargin);
        dest.writeInt(bottomMargin);
        if (Build.VERSION.SDK_INT >= 17) {
            dest.writeInt(getMarginStart());
            dest.writeInt(getMarginEnd());
            dest.writeInt(getLayoutDirection());
        }
        dest.writeInt(layoutId);
        dest.writeInt(maskValue);
    }
}
