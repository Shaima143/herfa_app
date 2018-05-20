package com.herfa.android.herfa;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Equipements> levelDetailsList;

    private OnAdapterItemClick onAdapterItemClick;
    public interface OnAdapterItemClick{
        public void onItemClick(int position);
    }

    public LevelAdapter() {
    }

    public LevelAdapter(Context context, ArrayList<Equipements> levelDetailsList) {
        this.context = context;
        this.levelDetailsList = levelDetailsList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<Equipements> getLevelDetailsList() {
        return levelDetailsList;
    }

    public void setLevelDetailsList(ArrayList<Equipements> levelDetailsList) {
        this.levelDetailsList = levelDetailsList;
    }

    @Override
    public LevelAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView=layoutInflater.inflate(R.layout.equipment_cell,parent,false);
        return new LevelAdapter.ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(LevelAdapter.ViewHolder holder, final int position) {
        Equipements levelDetails=levelDetailsList.get(position);

//        holder.textView1.setText(levelDetails.getEqui1());
//        holder.textView2.setText(levelDetails.getEqui2());
//        holder.textView3.setText(levelDetails.getEqui3());

//        Picasso.with(context).load(LevelDetails.()).
//                resize(50, 50).into(holder.craftsImageTwo);

//        holder.imageView1.setImageURI(Uri.parse(levelDetails.getEqui1()));
//        holder.imageView2.setImageURI(Uri.parse(levelDetails.getEqui2()));
//        holder.imageView3.setImageURI(Uri.parse(levelDetails.getEqui3()));

    Picasso.with(context).load(levelDetails.getEqui1()).resize(200,100).into(holder.imageView1);
    Picasso.with(context).load(levelDetails.getEqui2()).resize(200,100).into(holder.imageView2);
    Picasso.with(context).load(levelDetails.getEqui3()).resize(200,100).into(holder.imageView3);

       // holder.imageView.setImageResource(levelDetails.getEquipment_img());
        //holder.videoView.setVideoURI(Uri.parse(levelDetails.getVedio()));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAdapterItemClick.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return levelDetailsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //TextView textView1, textView2, textView3;
        VideoView videoView;
        ImageView imageView1, imageView2, imageView3;

        public ViewHolder(View itemView) {
            super(itemView);
           // imageView=itemView.findViewById(R.id.equipment_imageCell);
           // videoView=itemView.findViewById(R.id.videoView);

//            textView1=itemView.findViewById(R.id.equip1);
//            textView2=itemView.findViewById(R.id.equip2);
//            textView3=itemView.findViewById(R.id.equip3);

            imageView1=itemView.findViewById(R.id.equip1);
            imageView2=itemView.findViewById(R.id.equip2);
            imageView3=itemView.findViewById(R.id.equip3);

        }
    }
    public void setOnAdapterItemClick(OnAdapterItemClick onAdapterItemClick){
       this.onAdapterItemClick=onAdapterItemClick;
    }
}
