package com.kpfu.itis.culturalchallenge.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kpfu.itis.culturalchallenge.MainActivity;
import com.kpfu.itis.culturalchallenge.R;
import com.kpfu.itis.culturalchallenge.entities.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anatoly on 12.07.2017.
 */

public class FriendsRecyclerAdapter extends RecyclerView.Adapter<FriendsRecyclerAdapter.FriendsViewHolder> {
    private List<User> mFriends;
    private Context context;
    private MainActivity mainActivity;

    public FriendsRecyclerAdapter(Context context, Activity activity) {
        this.context = context;
        this.mainActivity = (MainActivity) activity;
    }

    @Override
    public FriendsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_home,
                parent,
                false
        );
        return new FriendsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FriendsViewHolder holder, int position) {
        final User task = mFriends.get(position);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        mFriends = new ArrayList<>();

        return mFriends.size();
    }

    public class FriendsViewHolder extends RecyclerView.ViewHolder {


        public FriendsViewHolder(View itemView) {
            super(itemView);

        }
    }
}
