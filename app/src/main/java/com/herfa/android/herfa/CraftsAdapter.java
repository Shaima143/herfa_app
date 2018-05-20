package com.herfa.android.herfa;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CraftsAdapter extends RecyclerView.Adapter<CraftsAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CraftsDetails> craftsDetailsArrayList;
    private OnCraftImageOneClick onCraftImageOneClick;
    public interface OnCraftImageOneClick{
        public void onCraftImageOneClick(int position);
    }
    private OnCraftImageTwoClick onCraftImageTwoClick;
    public interface OnCraftImageTwoClick{
        public void onCraftImageTwoClick(int position);
    }
    private OnAdapterItemClick onAdapterItemClick;
    public interface OnAdapterItemClick{
        public void onItemClick(int position);
    }
    public CraftsAdapter() {
    }

    public CraftsAdapter(Context context, ArrayList<CraftsDetails> craftsDetailsArrayList) {
        this.context = context;
        this.craftsDetailsArrayList = craftsDetailsArrayList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<CraftsDetails> getCraftsDetailsArrayList() {
        return craftsDetailsArrayList;
    }

    public void setCraftsDetailsArrayList(ArrayList<CraftsDetails> craftsDetailsArrayList) {
        this.craftsDetailsArrayList = craftsDetailsArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView=layoutInflater.inflate(R.layout.crafts_cell,parent,false);
        return new CraftsAdapter.ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        CraftsDetails craftsDetails=craftsDetailsArrayList.get(position);
        holder.craftsImageOne.setImageResource(craftsDetails.getCraftsImageOne());
        //holder.craftsImageTwo.setImageResource(craftsDetails.getCraftsImageTwo());
//        Picasso.with(context).load(craftsDetails.getCraftsImageOne()).
//                resize(50, 50).into(holder.craftsImageOne);
//        Picasso.with(context).load(craftsDetails.getCraftsImageTwo()).
              //  resize(50, 50).into(holder.craftsImageTwo);

        holder.craftNameOne.setText(craftsDetails.getCraftNameOne());
        holder.craftsImageOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCraftImageOneClick.onCraftImageOneClick(position);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAdapterItemClick.onItemClick(position);
            }
        });
        holder.craftNameOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAdapterItemClick.onItemClick(position);
            }
        });

        holder.craftsImageOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAdapterItemClick.onItemClick(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return craftsDetailsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       ImageView craftsImageOne,craftsImageTwo;
       TextView craftNameOne,craftNameTwo;
        public ViewHolder(View itemView) {
            super(itemView);
            craftsImageOne=itemView.findViewById(R.id.craft_img_one);
            craftNameOne=itemView.findViewById(R.id.craft_txt_one);
        }
    }
    public void setOnAdapterItemClick(CraftsAdapter.OnAdapterItemClick onAdapterItemClick){
        this.onAdapterItemClick=onAdapterItemClick;
    }


//    public void setOnCraftImageOneClick(OnCraftImageOneClick onCraftImageOneClick){
//        this.onCraftImageOneClick=onCraftImageOneClick;
//    }
//    public void setOnCraftImageTwoClick(OnCraftImageTwoClick onCraftImageTwoClick){
//        this.onCraftImageTwoClick=onCraftImageTwoClick;
//    }
}
