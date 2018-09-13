package com.hemanth.makeaclick;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RecyclerGridAdapter extends RecyclerView.Adapter<RecyclerGridAdapter.MyViewHolder> {

    Values values;
    TextView profileTextView;
    LayoutInflater inflater;

    RecyclerGridAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        values = new Values(context);
        Log.d("vj", "adapter profiles size:" + values.getProfiles().size());

    }

    @Override
    public RecyclerGridAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.individual_profile, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        profileTextView.setText(values.getProfiles().get(position).getProfileName());
    }


    @Override
    public int getItemCount() {
        return values.getProfiles().size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View itemView) {
            super(itemView);
            profileTextView = itemView.findViewById(R.id.profiles);

        }
    }
}
