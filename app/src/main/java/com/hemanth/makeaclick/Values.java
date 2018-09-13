package com.hemanth.makeaclick;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hemanth on 8/31/2018.
 */

public class Values {
    final static String POSITION = "POSITION";
    final static int NO_OF_COLUMNS = 2;
    final static String SHARED_PREFERENCES_NAME = "MY_PREFERENCES";
    final static String FIRST_RUN = "FIRST_RUN";
    final static String PROFILES = "PROFILES";
    final static String PROFILES_DEFAULT_VALUE = "NO PROFILES";
    static List<Profile> profiles = new ArrayList<>();


    public static List<Profile> getProfiles() {
        return profiles;
    }


    public static void setProfiles(List<Profile> profiles) {
        Values.profiles = profiles;
    }
}
