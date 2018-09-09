package com.hemanth.makeaclick;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

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

        init();
        checkPermissions();
        values.profiles.add(new Profile(this, "HOME", true, false, String.valueOf(0)));
        values.profiles.add(new Profile(this, "COLLEGE", false, true, String.valueOf(50)));
        values.profiles.add(new Profile(this, "WORK", false, true, String.valueOf(50)));
        values.profiles.add(new Profile(this, "TRAVEL", false, false, String.valueOf(50)));

        //Log.d("vj",""+values.profiles.size());

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
                    Profile.setProfile(values.profiles.get(position));
                }
            }

            @Override
            public void onLongClick(View view, int position) {
                Log.d("vj", "onLongClick " + position);
                Intent intent = new Intent(getApplicationContext(), EditProfileActivity.class);
                //Bundle bundle = new Bundle();
                //bundle.putSerializable("POSITION",position);
                intent.putExtra(Values.POSITION, position);
                startActivity(intent);
            }
        }));

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
        //silentMode = view.findViewById(R.id.silent_mode_feature);
        On = getResources().getString(R.string.On);
        Off = getResources().getString(R.string.Off);
        zeroPercent = getResources().getString(R.string.zero_percent);
        fiftyPercent = getResources().getString(R.string.fifty_percent);
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        recyclerGridAdapter = new RecyclerGridAdapter(this);
        recyclerView = findViewById(R.id.recycler_grid_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        values = new Values(this);
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

    public void switchCase(int position) {
        switch (position) {
            case 0:
                setWifi(true);
                //setAirplaneMode(false);
                setBrightness(0);
                setSilentMode(false);
                break;
            case 1:
                setWifi(false);
                //setAirplaneMode(false);
                setBrightness(50);
                setSilentMode(true);
                break;
            case 2:
                setWifi(false);
                setSilentMode(true);
                //setAirplaneMode(false);
                setBrightness(50);
                break;
            case 3:
                setWifi(false);
                setSilentMode(false);
                //setAirplaneMode(false);
                setBrightness(50);
                break;
            case 4:
                setWifi(false);
                setSilentMode(true);
                //setAirplaneMode(true);
                setBrightness(0);
                break;
        }

    }

    public void setWifi(boolean ON_OR_OFF) {
        if (ON_OR_OFF) {
            wifiManager.setWifiEnabled(true);
        } else {
            wifiManager.setWifiEnabled(false);
        }
    }

    public void setSilentMode(boolean ON_OR_OFF) {
        if (ON_OR_OFF) {
            audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
        } else {
            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        }
    }

   /* public void setAirplaneMode(boolean ON_OR_OFF) {

        boolean isEnabled = Settings.System.getInt(getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0) == 1;
        //Log.d("vj", "airplane mode " + isEnabled);
        // if (ON_OR_OFF) {
        Settings.System.putInt(getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, isEnabled ? 0 : 1);
        Log.d("vj", "airplane mode " + isEnabled);
        Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        //  intent.putExtra("state",!isEnabled);
        // this.sendBroadcast(intent);
        //} else {
        //    Settings.System.putInt(getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, isEnabled ?0 : 1);
        //}
    }*/

    public void setBrightness(int percent) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        //Settings.System.putInt(getContentResolver(),Settings.System.SCREEN_BRIGHTNESS_MODE,Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
        if (percent == 0) {
            Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 1);
            lp.screenBrightness = 1 / 100.0f;
            getWindow().setAttributes(lp);
        } else {
            Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 127);
            lp.screenBrightness = 127 / 100.0f;
            getWindow().setAttributes(lp);
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

