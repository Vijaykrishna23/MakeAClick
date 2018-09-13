package com.hemanth.makeaclick;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.google.gson.Gson;

public class EditProfileActivity extends AppCompatActivity {
    Values values;
    int position;
    Profile currentProfile;

    EditText name;
    Switch wifi;
    Switch silentMode;
    EditText brightness;

    String tempName;
    boolean tempWifi;
    boolean tempSilentMode;
    String tempBrightness;
    //public List<Profile> Values.getProfiles();// = new ArrayList<>(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        values = new Values(getApplicationContext());
        Toolbar toolbar = findViewById(R.id.toolbar_edit_profile);
        setSupportActionBar(toolbar);
        name = findViewById(R.id.profile_name);
        wifi = findViewById(R.id.wifi_switch);
        silentMode = findViewById(R.id.silent_mode_switch);
        brightness = findViewById(R.id.brightness_switch);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        position = getIntent().getIntExtra(Values.POSITION, 5);
        //Values.getProfiles() = (List<Profile>) getIntent().getBundleExtra("BUNDLE").getSerializable("Values.getProfiles()");
        //Log.d("vj", "size " + Values.getProfiles().size());
        //values.profileTextView.get(position) = values.profileTextView.get(position);

        //Values.getProfiles().get(position).printProfile(Values.getProfiles().get(position));


        name.setText(values.getProfiles().get(position).getProfileName());
        wifi.setChecked(values.getProfiles().get(position).isWifiState());
        silentMode.setChecked(values.getProfiles().get(position).isSilentModeState());
        brightness.setText(values.getProfiles().get(position).getBrightnessPercent());

        tempName = name.getText().toString();
        tempWifi = wifi.isChecked();
        tempSilentMode = silentMode.isChecked();
        tempBrightness = brightness.getText().toString();
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                tempName = s.toString();
            }
        });


        wifi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                tempWifi = buttonView.isChecked();
                Log.d("vj", "tempwifi:" + tempWifi + "wifi:" + wifi.isChecked());
            }
        });

        silentMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //values.profileTextView.get(position).setSilentModeState(buttonView.isChecked());
                tempSilentMode = buttonView.isChecked();
            }
        });

        brightness.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tempBrightness = s.toString();
            }
        });


        //RecyclerView featureForValues.getProfiles() = findViewById(R.id.feature_recycler_view);
        //ProfileAdapter profileAdapter = new ProfileAdapter(this);
        //featureForValues.getProfiles().setLayoutManager(new LinearLayoutManager(this));
        //featureForValues.getProfiles().setAdapter(profileAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                values.getProfiles().get(position).setWifiState(tempWifi);
                values.getProfiles().get(position).setProfileName(tempName);
                values.getProfiles().get(position).setSilentModeState(tempSilentMode);
                values.getProfiles().get(position).setBrightnessPercent(tempBrightness);


                //values.profileTextView.get(position).profileName = tempName;
                //values.profileTextView.get(position).wifiState = tempWifi;
                //values.profileTextView.get(position).silentModeState = tempSilentMode;
                //values.profileTextView.get(position).brightnessPercent = tempBrightness;
                values.getProfiles().get(position).printProfile(values.getProfiles().get(position));
                SharedPreferences sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(Values.getProfiles());
                editor.putString("profiles", json);
                editor.apply();
                //values.home.printProfile(values.home);
                break;
        }

        finish();

        return true;
    }

}
