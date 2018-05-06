package com.herfa.android.herfa;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LevelsAdapter extends RecyclerView.Adapter<LevelsAdapter.ViewHolder> {
    private Context context;
    private ArrayList<LevelsDetails> carpenteryDetailsArrayList;
    private OnAdapterItemClick onAdapterItemClick;

    public interface OnAdapterItemClick{
        public void onItemClick(int position);
    }

    public LevelsAdapter() {
    }

    public LevelsAdapter(Context context, ArrayList<LevelsDetails> carpenteryDetailsArrayList) {
        this.context = context;
        this.carpenteryDetailsArrayList = carpenteryDetailsArrayList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<LevelsDetails> getCarpenteryDetailsArrayList() {
        return carpenteryDetailsArrayList;
    }

    public void setCarpenteryDetailsArrayList(ArrayList<LevelsDetails> carpenteryDetailsArrayList) {
        this.carpenteryDetailsArrayList = carpenteryDetailsArrayList;
    }

    @Override
    public LevelsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView=layoutInflater.inflate(R.layout.level_cell,parent,false);
        return new LevelsAdapter.ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(LevelsAdapter.ViewHolder holder, final int position) {
        LevelsDetails carpenteryDetails=carpenteryDetailsArrayList.get(position);
        holder.tv.setText(carpenteryDetails.getLevel());
        holder.imageView.setImageResource(carpenteryDetails.getImage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAdapterItemClick.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return carpenteryDetailsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            tv=itemView.findViewById(R.id.textView_carpentryCell);
            imageView=itemView.findViewById(R.id.lock_icon);
        }
    }
    public void setOnAdapterItemClick(OnAdapterItemClick onAdapterItemClick){
        this.onAdapterItemClick=onAdapterItemClick;
    }
}
