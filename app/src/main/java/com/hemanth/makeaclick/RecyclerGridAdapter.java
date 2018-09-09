package com.hemanth.makeaclick;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RecyclerGridAdapter extends RecyclerView.Adapter<RecyclerGridAdapter.MyViewHolder> {

    Values values;
    TextView profiles;
    LayoutInflater inflater;

    RecyclerGridAdapter(Activity activity) {
        inflater = LayoutInflater.from(activity);
        values = new Values(activity);

    }

    @Override
    public RecyclerGridAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.individual_profile, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        profiles.setText(values.profiles.get(position).profileName);
    }


    @Override
    public int getItemCount() {
        return values.profiles.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View itemView) {
            super(itemView);
            profiles = itemView.findViewById(R.id.profiles);

        }
    }
}
