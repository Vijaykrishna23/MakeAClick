package com.hemanth.makeaclick;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

public class EditProfileActivity extends AppCompatActivity {
    Values values;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        values = new Values(getApplicationContext());
        Toolbar toolbar = findViewById(R.id.toolbar_edit_profile);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        int position = getIntent().getIntExtra(Values.POSITION, 5);
        Log.d("vj", "pos" + position);
        EditText editText = findViewById(R.id.profile_name);
        Switch wifi = findViewById(R.id.wifi_switch);
        Switch silentMode = findViewById(R.id.silent_mode_switch);
        EditText brightness = findViewById(R.id.brightness_switch);


        editText.setText(values.profiles.get(position).profileName);
        wifi.setChecked(values.profiles.get(position).wifiState);
        silentMode.setChecked(values.profiles.get(position).silentMode);
        brightness.setText(values.profiles.get(position).brightnessPercent);

        //RecyclerView featureForProfiles = findViewById(R.id.feature_recycler_view);
        //ProfileAdapter profileAdapter = new ProfileAdapter(this);
        //featureForProfiles.setLayoutManager(new LinearLayoutManager(this));
        //featureForProfiles.setAdapter(profileAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_profile, menu);
        return true;
    }
}
