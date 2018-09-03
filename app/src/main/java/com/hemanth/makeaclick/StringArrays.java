package com.hemanth.makeaclick;

import android.content.Context;

/**
 * Created by Hemanth on 8/31/2018.
 */

public class StringArrays {
    String On;
    String Off;
    String zeroPercent;
    String fiftyPercent;
    String[] placesNames;
    String[] featureNames;
    String[] featureState;

    StringArrays(Context context) {
        placesNames = context.getResources().getStringArray(R.array.places);
        featureNames = context.getResources().getStringArray(R.array.features);
        On = context.getResources().getString(R.string.On);
        Off = context.getResources().getString(R.string.Off);
        zeroPercent = context.getResources().getString(R.string.zero_percent);
        fiftyPercent = context.getResources().getString(R.string.fifty_percent);
    }
}
