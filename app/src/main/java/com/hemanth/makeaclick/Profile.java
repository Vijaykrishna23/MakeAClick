package com.hemanth.makeaclick;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.util.Log;
import android.view.WindowManager;

import static android.content.Context.AUDIO_SERVICE;
import static android.content.Context.WIFI_SERVICE;

public class Profile {

    private String profileName;
    private boolean wifiState;
    private boolean silentModeState;
    private String brightnessPercent;
    private transient Context context;


    Profile(Context context, String profileName, boolean wifiState, boolean silentMode, String brightnessPercent) {
        this.profileName = profileName;
        this.wifiState = wifiState;
        this.silentModeState = silentMode;
        this.brightnessPercent = brightnessPercent;
        this.context = context;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String name) {
        this.profileName = name;
    }

    public String getBrightnessPercent() {
        return brightnessPercent;
    }

    public void setBrightnessPercent(String brightnessPercent) {
        this.brightnessPercent = brightnessPercent;
    }

    public boolean isWifiState() {
        return wifiState;
    }

    public void setWifiState(boolean wifiState) {
        this.wifiState = wifiState;
    }

    public boolean isSilentModeState() {
        return silentModeState;
    }

    public void setSilentModeState(boolean silentModeState) {
        this.silentModeState = silentModeState;
    }

    public void setProfile() {
        setWifi(wifiState);
        setSilentMode(silentModeState);
        setBrightness(brightnessPercent);
    }

    private void setWifi(boolean ON_OR_OFF) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(WIFI_SERVICE);
        wifiManager.setWifiEnabled(ON_OR_OFF);

    }

    private void setSilentMode(boolean ON_OR_OFF) {
        AudioManager audioManager = (AudioManager) context.getSystemService(AUDIO_SERVICE);
        if (ON_OR_OFF)
            audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
        else
            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
    }

    public void setBrightness(String percent) {
        WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
        //Settings.System.putInt(getContentResolver(),Settings.System.SCREEN_BRIGHTNESS_MODE,Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
        if ((Integer.parseInt(percent) == 0)) {
            Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 1);
            lp.screenBrightness = 1 / 100.0f;
            ((Activity) context).getWindow().setAttributes(lp);
        } else {
            Settings.System.putInt((context).getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, Integer.parseInt(percent));
            lp.screenBrightness = Float.parseFloat(percent) / 100.0f;
            ((Activity) context).getWindow().setAttributes(lp);
        }
    }

    public void printProfile(Profile profile) {
        Log.d("vj", "\nProfileName: " + profileName);
        Log.d("vj", "\nWifi: " + wifiState);
        Log.d("vj", "\nSilentMode: " + silentModeState);
        Log.d("vj", "\nBrightness:" + brightnessPercent);
    }

}
