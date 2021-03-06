package com.hemanth.makeaclick;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RecyclerGridAdapter extends RecyclerView.Adapter<RecyclerGridAdapter.MyViewHolder> {

    Values values;
    private TextView profileTextView;
    private LayoutInflater inflater;

    RecyclerGridAdapter() {
        inflater = LayoutInflater.from(MainActivity.getContext());
        //Log.d("vj", "adapter profiles size:" + values.getProfiles().size());

    }

    @Override
    public RecyclerGridAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.individual_profile, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        profileTextView.setText(Values.getProfiles().get(position).getProfileName());
    }


    @Override
    public int getItemCount() {
        return Values.getProfiles().size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        MyViewHolder(View itemView) {
            super(itemView);
            profileTextView = itemView.findViewById(R.id.profiles);

        }
    }
}
