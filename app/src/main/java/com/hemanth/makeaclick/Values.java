package com.hemanth.makeaclick;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Hemanth on 8/31/2018.
 */

public class Values {
    final static String POSITION = "POSITION";
    final static int NO_OF_COLUMNS = 2;
    static List<Profile> profiles = new ArrayList<>();
    Context context;
    String On;
    String Off;
    String zeroPercent;
    String fiftyPercent;
    List<String> placesNames;
    String[] featureNames;
    String[] featureState;
    int colorPrimary;
    int colorPrimaryLight;
    Drawable textViewBackgroundPressed;
    Profile college;


    Drawable textViewBackgroundNotPressed;

    Values(Context context) {
        this.context = context;
        placesNames = Arrays.asList(context.getResources().getStringArray(R.array.places));
        featureNames = context.getResources().getStringArray(R.array.features);
        colorPrimary = context.getResources().getColor(R.color.colorPrimary);
        colorPrimaryLight = context.getResources().getColor(R.color.colorPrimaryLight);
        textViewBackgroundNotPressed = context.getResources().getDrawable(R.drawable.textview_border_and_background_not_pressed, context.getTheme());
        textViewBackgroundPressed = context.getResources().getDrawable(R.drawable.textview_border_and_background_pressed, context.getTheme());
        On = context.getResources().getString(R.string.On);
        Off = context.getResources().getString(R.string.Off);
        zeroPercent = context.getResources().getString(R.string.zero_percent);
        fiftyPercent = context.getResources().getString(R.string.fifty_percent);
        //college = new Profile(context, "COLLEGE", false, true, String.valueOf(50));
        //profiles.add(college);
        //profileTextView.add(new Profile(context, "HOME", true, false, String.valueOf(0)));
        //profileTextView.add(new Profile(context, "COLLEGE", false, true, String.valueOf(50)));
        //profileTextView.add(new Profile(context, "WORK", false, true, String.valueOf(50)));
        //profileTextView.add(new Profile(context, "TRAVEL", false, false, String.valueOf(50)));
    }

    public static List<Profile> getProfiles() {
        return profiles;
    }

    public static void setProfiles(List<Profile> profiles) {
        Values.profiles = profiles;
    }
}
