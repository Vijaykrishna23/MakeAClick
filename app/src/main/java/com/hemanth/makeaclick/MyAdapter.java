package com.hemanth.makeaclick;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Hemanth on 8/30/2018.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


    private LayoutInflater inflater;
    private TextView placesTextView;
    private StringArrays stringArrays;//new StringArrays(context);
    private String[] placesNames;
    private String[] featureNames;
    private TextView[] featuresAndItsStates;
    private TextView rows;
    private LinearLayout columnLayout;
    private LinearLayout rowLayout;
    private Context context;
    private LinearLayout.LayoutParams params;


    MyAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        stringArrays = new StringArrays(context);
        placesNames = stringArrays.placesNames;
        featureNames = stringArrays.featureNames;
    }

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.features_and_state_of_single_place, parent, false);
        columnLayout = view.findViewById(R.id.features_column);
        rowLayout = view.findViewById(R.id.features_row);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyAdapter.MyViewHolder holder, int position) {

        setFeaturesAndItsStates(position);
        placesTextView.setText(placesNames[position]);

    }

    @Override
    public int getItemCount() {
        return placesNames.length;
    }

    public void setFeaturesAndItsStates(int position) {
        int id = 1000;
        featuresAndItsStates = new TextView[featureNames.length];
        for (int i = 0; i < featureNames.length; i++) {
            featuresAndItsStates[i] = new TextView(context);
            featuresAndItsStates[i].setLayoutParams(params);
            featuresAndItsStates[i].setPadding(30, 10, 10, 10);
            featuresAndItsStates[i].setId(id);
            switch (position) {
                case 0:
                    setTextForHome(i);
                    break;
                case 1:
                    setTextForCollege(i);
                    break;
                case 2:
                    setTextForWork(i);
                    break;
                case 3:
                    setTextForTravel(i);
                    break;
                /*case 4:
                    setTextForNightMode(i);
                    break;*/
            }
            featuresAndItsStates[i].setPadding(30, 10, 10, 10);
            featuresAndItsStates[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            featuresAndItsStates[i] = featuresAndItsStates[i];


            columnLayout.addView(featuresAndItsStates[i]);


            id++;

        }
    }


    public void setTextForHome(int index) {
        switch (index) {
            case 0:
                setWifi(true, index);
                break;
            case 1:
                setSilentMode(false, index);
                break;
            case 2:
                setBrightneess(0, index);
                break;
          /*  case 3:
                setBrightneess(0, index);
                break;*/
        }

    }

    private void setTextForCollege(int index) {

        switch (index) {
            case 0:
                setWifi(false, index);
                break;
            case 1:
                setSilentMode(true, index);
                break;
            case 2:
                setBrightneess(50, index);
                break;
          /*  case 3:
                setBrightneess(50, index);
                break;*/
        }

    }

    public void setTextForWork(int index) {
        switch (index) {
            case 0:
                setWifi(false, index);
                break;
            case 1:
                setSilentMode(true, index);
                break;
            case 2:
                setBrightneess(50, index);
                break;
           /* case 3:
                setBrightneess(50, index);
                break;*/
        }
    }

    public void setTextForTravel(int index) {
        switch (index) {
            case 0:
                setWifi(false, index);
                break;
            case 1:
                setSilentMode(false, index);
                break;
            case 2:
                setBrightneess(50, index);
                break;
            /*case 3:
                setBrightneess(50, index);
                break;*/
        }
    }

    public void setTextForNightMode(int index) {
        switch (index) {
            case 0:
                setWifi(false, index);
                break;
            case 1:
                setSilentMode(true, index);
                break;
            case 2:
                setBrightneess(0, index);
                break;
          /*  case 3:
                setBrightneess(0, index);
                break;*/
        }
    }

    public void setWifi(boolean ON_OR_OFF, int index) {
        if (ON_OR_OFF) {
            featuresAndItsStates[index].setText(featureNames[index] + "-" + stringArrays.On);
        } else {
            featuresAndItsStates[index].setText(featureNames[index] + "-" + stringArrays.Off);
        }
    }

    public void setSilentMode(boolean ON_OR_OFF, int index) {
        if (ON_OR_OFF) {
            featuresAndItsStates[index].setText(featureNames[index] + "-" + stringArrays.On);
        } else {
            featuresAndItsStates[index].setText(featureNames[index] + "-" + stringArrays.Off);
        }
    }

    public void setAirPlaneMode(boolean ON_OR_OFF, int index) {
        if (ON_OR_OFF) {
            featuresAndItsStates[index].setText(featureNames[index] + "-" + stringArrays.On);
        } else {
            featuresAndItsStates[index].setText(featureNames[index] + "-" + stringArrays.Off);
        }
    }

    public void setBrightneess(int percent, int index) {
        if (percent == 0) {
            featuresAndItsStates[index].setText(featureNames[index] + "-" + stringArrays.zeroPercent);
        } else {
            featuresAndItsStates[index].setText(featureNames[index] + "-" + stringArrays.fiftyPercent);
        }
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View itemView) {
            super(itemView);
            placesTextView = itemView.findViewById(R.id.places_text_view);

        }
    }

}


