package com.hemanth.makeaclick;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView placesList;
    private TextView placesTextView;
    private Switch isPlaceSelected;
    private String[] placesNames;
    private String[] featureNames;
    private TextView silentMode, wifi, brightness, airplaneMode;
    private TextView silentModeState, wifiState, brightnessState, airplaneModeState;
    private LayoutInflater inflater;
    private View view;
    private String On, Off, zeroPercent, fiftyPercent;
    private AudioManager audioManager;
    private WifiManager wifiManager;
    private RecyclerGridAdapter recyclerGridAdapter;
    private RecyclerView recyclerView;
    private NotificationManager notificationManager;
    private Values values;
    private Button pressedProfile;
    private Button previousPressedProfile;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        //profiles.add(new Profile(this,"college",false,true,"50"));
        values = new Values(this);

        SharedPreferences sharedPreferences = getSharedPreferences("prefs", 0);
        boolean firstRun = sharedPreferences.getBoolean("firstRun", true);
        if (firstRun) {
            addProfiles();
            //Log.d("vj", "size:" + values.profiles.size());
            //Log.d("vj", "name0" + values.profiles.get(0).profileName);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("firstRun", false);
            Gson gson = new Gson();
            //Profile hom = new Profile(this,"home",false,false,"20");
            String json = gson.toJson(values.getProfiles());
            editor.putString("profiles", json);
            editor.apply();
            //Log.d("vj","json:" + json );
        } else {
            Gson gson = new Gson();
            String json = sharedPreferences.getString("profiles", "yes");
            Type type = new TypeToken<List<Profile>>() {
            }.getType();
            Values.setProfiles((List<Profile>) gson.fromJson(json, type));
            //SharedPreferences.Editor editor = sharedPreferences.edit();
            //Log.d("vj","json: "+ json + " gson: " + gson.fromJson(json,type));
        }


        init();
        checkPermissions();

        //Log.d("vj",""+values.profileTextView.size());

        recyclerView.setLayoutManager(new GridLayoutManager(this, Values.NO_OF_COLUMNS));
        recyclerView.setAdapter(recyclerGridAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.d("vj", "onClick " + position);

                pressedProfile = view.findViewById(R.id.profiles);

                changeColorofProfile(pressedProfile);
                changeColorofProfile(previousPressedProfile);
                previousPressedProfile = pressedProfile;


                if (pressedProfile.isSelected()) {
                    //switchCase(position);
                    values.getProfiles().get(position).setProfile();

                }
            }

            @Override
            public void onLongClick(View view, int position) {
                Log.d("vj", "onLongClick " + position);
                Intent intent = new Intent(getApplicationContext(), EditProfileActivity.class);
                //Bundle bundle = new Bundle();
                //bundle.putSerializable("POSITION",position);
                intent.putExtra(Values.POSITION, position);
                Bundle args = new Bundle();
                //args.putSerializable("PROFILES", (Serializable) profiles);
                //intent.putExtra("BUNDLE",args);
                startActivity(intent);
            }
        }));


    }

    private void addProfiles() {
        values.getProfiles().add(new Profile(this, "HOME", true, false, String.valueOf(0)));
        values.getProfiles().add(new Profile(this, "COLLEGE", false, true, String.valueOf(50)));
        values.getProfiles().add(new Profile(this, "WORK", false, true, String.valueOf(50)));
        values.getProfiles().add(new Profile(this, "TRAVEL", false, false, String.valueOf(50)));

    }

    public void changeColorofProfile(Button button) {
        if (button.isSelected()) {
            button.setSelected(false);
        } else {
            button.setSelected(true);
        }


    }


    private void changeStateOfSwitchButton(View view) {
        isPlaceSelected = view.findViewById(R.id.wifi_switch);
        if (!isPlaceSelected.isChecked()) {
            isPlaceSelected.setChecked(true);
        } else {
            isPlaceSelected.setChecked(false);
        }
    }

    public void init() {
        //inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        //view = inflater.inflate(R.layout.features_and_state_of_single_place, null);
        //placesList = findViewById(R.id.titles_list_view);
        placesNames = getResources().getStringArray(R.array.places);
        featureNames = getResources().getStringArray(R.array.features);
        //silentModeState = view.findViewById(R.id.silent_mode_feature);
        On = getResources().getString(R.string.On);
        Off = getResources().getString(R.string.Off);
        zeroPercent = getResources().getString(R.string.zero_percent);
        fiftyPercent = getResources().getString(R.string.fifty_percent);
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        recyclerGridAdapter = new RecyclerGridAdapter(this);
        recyclerView = findViewById(R.id.recycler_grid_view);

        previousPressedProfile = new Button(this);
    }

    public void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(this)) {
                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + this.getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Intent intent1;
                if (!notificationManager.isNotificationPolicyAccessGranted()) {
                    intent1 = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                    startActivity(intent1);
                }
                startActivity(intent);
            }

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }


    public static interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            Log.d("vj", "constructor");
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {

                    Log.d("vj", "onSingleTapUp" + e);
                    return true;
                }


                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildLayoutPosition(child));
                    }
                    Log.d("vj", "onLongPress" + e);
                }
            });


        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            //Log.d("vj","onInterceptTouchEvent " + gestureDetector.onTouchEvent(e) + " " + e);

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildLayoutPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            //Log.d("vj","onTouchEvent" + e);
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }


}

