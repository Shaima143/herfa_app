package com.herfa.android.herfa;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Shaima on 02/05/2018.
 */

public class MarketAdapter extends RecyclerView.Adapter<MarketAdapter.ViewHolder> {
    private Context context;
    private int[] marketDetailsArrayList;
    private MarketAdapter.OnAdapterItemClick onAdapterItemClick;


    public interface OnAdapterItemClick{
        public void onItemClick(int position);
    }
    public MarketAdapter() {
    }

    public MarketAdapter(Context context, int[] marketDetailsArrayList) {
        this.context = context;
        this.marketDetailsArrayList = marketDetailsArrayList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int[]getMarketDetailsArrayList() {
        return marketDetailsArrayList;
    }

    public void setMarketDetailsArrayList(int[] marketDetailsArrayList) {
        this.marketDetailsArrayList = marketDetailsArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView=layoutInflater.inflate(R.layout.market_cell,parent,false);

        return new MarketAdapter.ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //MarketDetails marketDetails=marketDetailsArrayList.get(position);
//        holder.textView.setText(marketDetails.getProduct_name());

        holder.imageView.setImageResource(marketDetailsArrayList[position]);

//        Picasso.with(context).load(marketDetails.getProduct_image()).
//                resize(50, 50).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAdapterItemClick.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return marketDetailsArrayList.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.product_img);
            //   textView=itemView.findViewById(R.id.product_txt);


//            imageView.setImageBitmap(
//                decodeSampledBitmapFromResource(getResources(), R.id.product_img, 100, 100));

        }


    }

    public void setOnAdapterItemClick(MarketAdapter.OnAdapterItemClick onAdapterItemClick){
        this.onAdapterItemClick=onAdapterItemClick;
    }


//    public static int calculateInSampleSize(
//            BitmapFactory.Options options, int reqWidth, int reqHeight) {
//        // Raw height and width of image
//        final int height = options.outHeight;
//        final int width = options.outWidth;
//        int inSampleSize = 1;
//
//        if (height > reqHeight || width > reqWidth) {
//
//            final int halfHeight = height / 2;
//            final int halfWidth = width / 2;
//
//            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
//            // height and width larger than the requested height and width.
//            while ((halfHeight / inSampleSize) >= reqHeight
//                    && (halfWidth / inSampleSize) >= reqWidth) {
//                inSampleSize *= 2;
//            }
//        }
//
//        return inSampleSize;
//    }
//
//
//    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
//                                                         int reqWidth, int reqHeight) {
//
//        // First decode with inJustDecodeBounds=true to check dimensions
//        final BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeResource(res, resId, options);
//
//        // Calculate inSampleSize
//        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
//
//        // Decode bitmap with inSampleSize set
//        options.inJustDecodeBounds = false;
//        return BitmapFactory.decodeResource(res, resId, options);
//    }


}