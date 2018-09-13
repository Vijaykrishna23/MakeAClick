package com.hemanth.makeaclick;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static Context context;
    private ListView placesList;
    private TextView placesTextView;
    private RecyclerGridAdapter recyclerGridAdapter;
    private RecyclerView recyclerView;
    private NotificationManager notificationManager;
    private Button pressedProfile;
    private Button previousPressedProfile;

    public static Context getContext() {
        return context;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        init();
        SharedPreferences sharedPreferences = getSharedPreferences(Values.SHARED_PREFERENCES_NAME, 0);
        boolean firstRun = sharedPreferences.getBoolean(Values.FIRST_RUN, true);
        if (firstRun) {
            addProfiles();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(Values.FIRST_RUN, false);
            Gson gson = new Gson();
            String json = gson.toJson(Values.getProfiles());
            editor.putString(Values.PROFILES, json);
            editor.apply();
        } else {
            Gson gson = new Gson();
            String json = sharedPreferences.getString(Values.PROFILES, Values.PROFILES_DEFAULT_VALUE);
            Type type = new TypeToken<List<Profile>>() {
            }.getType();
            Values.setProfiles((List<Profile>) gson.fromJson(json, type));

        }


        checkPermissions();

        //Log.d("vj",""+values.profileTextView.size());

        recyclerView.setLayoutManager(new GridLayoutManager(this, Values.NO_OF_COLUMNS));
        recyclerView.setAdapter(recyclerGridAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.d("vj", "onClick " + position);

                pressedProfile = view.findViewById(R.id.profiles);

                changeColorOfProfile(pressedProfile);
                changeColorOfProfile(previousPressedProfile);
                previousPressedProfile = pressedProfile;


                if (pressedProfile.isSelected()) {
                    Values.getProfiles().get(position).setProfile();
                }
            }

            @Override
            public void onLongClick(View view, int position) {
                Log.d("vj", "onLongClick " + position);
                Intent intent = new Intent(getApplicationContext(), EditProfileActivity.class);
                intent.putExtra(Values.POSITION, position);
                startActivity(intent);
            }
        }));


    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView.setAdapter(recyclerGridAdapter);
    }

    private void addProfiles() {
        Values.getProfiles().add(new Profile("HOME", true, false, String.valueOf(0)));
        Values.getProfiles().add(new Profile("COLLEGE", false, true, String.valueOf(50)));
        Values.getProfiles().add(new Profile("WORK", false, true, String.valueOf(50)));
        Values.getProfiles().add(new Profile("TRAVEL", false, false, String.valueOf(50)));

    }

    public void changeColorOfProfile(Button button) {
        if (button.isSelected()) {
            button.setSelected(false);
        } else {
            button.setSelected(true);
        }


    }

    public void init() {
        context = MainActivity.this;
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        recyclerGridAdapter = new RecyclerGridAdapter();
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

