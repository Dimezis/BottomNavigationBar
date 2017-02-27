package com.eightbitlab.bottomnavigationbar;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

class SavedState extends View.BaseSavedState {
    int selectedPosition;

    SavedState(Parcelable superState) {
        super(superState);
    }

    SavedState(Parcel source) {
        super(source);
        selectedPosition = source.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(selectedPosition);
    }

    public static final Parcelable.Creator<SavedState> CREATOR =
            new Parcelable.Creator<SavedState>() {
                @Override
                public SavedState createFromParcel(Parcel in) {
                    return new SavedState(in);
                }

                @Override
                public SavedState[] newArray(int size) {
                    return new SavedState[size];
                }
            };
}