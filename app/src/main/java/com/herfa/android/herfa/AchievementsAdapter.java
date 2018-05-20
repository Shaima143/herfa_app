package com.herfa.android.herfa;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Shaima on 12/05/2018.
 */

public class AchievementsAdapter extends RecyclerView.Adapter<AchievementsAdapter.ViewHolder>{
    private Context mContext;
    private int [] achievementsDetailsArrayList;
    private AchievementsAdapter.OnAdapterItemClick onAdapterItemClick;


    public interface OnAdapterItemClick{
        public void onItemClick(int position);
    }

    public AchievementsAdapter() {
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public int[] getAchievementsDetailsArrayList() {
        return achievementsDetailsArrayList;
    }

    public void setAchievementsDetailsArrayList(int[] achievementsDetailsArrayList) {
        this.achievementsDetailsArrayList = achievementsDetailsArrayList;
    }

    public OnAdapterItemClick getOnAdapterItemClick() {
        return onAdapterItemClick;
    }

    public AchievementsAdapter(Context mContext, int [] achievementsDetailsArrayList) {
        this.mContext = mContext;
        this.achievementsDetailsArrayList = achievementsDetailsArrayList;
    }



    @Override
    public AchievementsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = inflater.inflate(R.layout.achievements_cell,parent,false);
        return new AchievementsAdapter.ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(AchievementsAdapter.ViewHolder holder, final int position) {
        //AchievementsDetails achievementsDetails = achievementsDetailsArrayList.get(position);

        holder.badgeImage.setImageResource(achievementsDetailsArrayList[position]);
        //Picasso.with(mContext).load(achievementsDetails.getBadge()).into(holder.badgeImage);
        holder.badgeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAdapterItemClick.onItemClick(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return achievementsDetailsArrayList.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView badgeImage;

        public ViewHolder(View itemView) {
            super(itemView);
            badgeImage = itemView.findViewById(R.id.achievement_id);
        }
    }
    public void setOnAdapterItemClick(AchievementsAdapter.OnAdapterItemClick onAdapterItemClick){
        this.onAdapterItemClick=onAdapterItemClick;
    }
}
