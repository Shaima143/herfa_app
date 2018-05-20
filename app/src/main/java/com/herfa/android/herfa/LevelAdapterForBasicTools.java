package com.herfa.android.herfa;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LevelAdapterForBasicTools extends RecyclerView.Adapter<LevelAdapterForBasicTools.ViewHolder> {
    private Context context;
    private ArrayList<EquipmentsBasicTools> levelDetailsList;

    private OnAdapterItemClick onAdapterItemClick;
    public interface OnAdapterItemClick{
        public void onItemClick(int position);
    }

    public LevelAdapterForBasicTools(Context context, ArrayList<EquipmentsBasicTools> levelDetailsList) {
        this.context = context;
        this.levelDetailsList = levelDetailsList;
    }

    @Override
    public LevelAdapterForBasicTools.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView=layoutInflater.inflate(R.layout.equipment_cell_tools,parent,false);
        return new LevelAdapterForBasicTools.ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(LevelAdapterForBasicTools.ViewHolder holder, final int position) {
        EquipmentsBasicTools levelDetails=levelDetailsList.get(position);
//        URL url = null;
//        try {
//            url = new URL(levelDetails.getEqui1());
//            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//            holder.imageView1.setImageBitmap(bmp);
//        }
//        catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        catch (IOException e){
//
//        }

        Log.d("images",levelDetails.getEqui1());
        Log.d("images",levelDetails.getEqui2());
        Log.d("images",levelDetails.getEqui3());
        Log.d("images",levelDetails.getEqui4());
        Log.d("images",levelDetails.getEqui5());


//        Picasso.with(context).load(LevelDetails.()).
//                resize(50, 50).into(holder.craftsImageTwo);

//        holder.imageView1.setImageURI(Uri.parse(levelDetails.getEqui1()));
//        holder.imageView2.setImageURI(Uri.parse(levelDetails.getEqui2()));
//        holder.imageView3.setImageURI(Uri.parse(levelDetails.getEqui3()));

       Picasso.with(context).load(levelDetails.getEqui1()).resize(200,100).into(holder.imageView1);
       Picasso.with(context).load(levelDetails.getEqui2()).resize(200,100).into(holder.imageView2);
        Picasso.with(context).load(levelDetails.getEqui3()).resize(200,100).into(holder.imageView3);
        Picasso.with(context).load(levelDetails.getEqui4()).resize(200,100).into(holder.imageView4);
        Picasso.with(context).load(levelDetails.getEqui5()).resize(200,100).into(holder.imageView5);
        Picasso.with(context).load(levelDetails.getEqui6()).resize(200,100).into(holder.imageView6);
        Picasso.with(context).load(levelDetails.getEqui7()).resize(200,100).into(holder.imageView7);
        Picasso.with(context).load(levelDetails.getEqui8()).resize(200,100).into(holder.imageView8);
        Picasso.with(context).load(levelDetails.getEqui9()).resize(200,100).into(holder.imageView9);
       // holder.imageView.setImageResource(levelDetails.getEquipment_img());
        //holder.videoView.setVideoURI(Uri.parse(levelDetails.getVedio()));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //need to be implemented by the fragment to which this adapter is going to attach
                //onAdapterItemClick.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return levelDetailsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView1, textView2, textView3;
        VideoView videoView;
        ImageView imageView1, imageView2, imageView3,imageView4,imageView5,imageView6,imageView7,imageView8,imageView9;

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
            imageView4=itemView.findViewById(R.id.equip4);
            imageView5=itemView.findViewById(R.id.equip5);
            imageView6=itemView.findViewById(R.id.equip6);
            imageView7=itemView.findViewById(R.id.equip7);
            imageView8=itemView.findViewById(R.id.equip8);
            imageView9=itemView.findViewById(R.id.equip9);

        }
    }
    public void setOnAdapterItemClick(OnAdapterItemClick onAdapterItemClick){
       this.onAdapterItemClick=onAdapterItemClick;
    }
}
