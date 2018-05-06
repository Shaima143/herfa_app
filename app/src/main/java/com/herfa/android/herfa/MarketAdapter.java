package com.herfa.android.herfa;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Shaima on 02/05/2018.
 */

public class MarketAdapter extends RecyclerView.Adapter<MarketAdapter.ViewHolder> {
    private Context context;
    private ArrayList<MarketDetails> marketDetailsArrayList;

    public MarketAdapter() {
    }

    public MarketAdapter(Context context, ArrayList<MarketDetails> marketDetailsArrayList) {
        this.context = context;
        this.marketDetailsArrayList = marketDetailsArrayList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<MarketDetails> getMarketDetailsArrayList() {
        return marketDetailsArrayList;
    }

    public void setMarketDetailsArrayList(ArrayList<MarketDetails> marketDetailsArrayList) {
        this.marketDetailsArrayList = marketDetailsArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView=layoutInflater.inflate(R.layout.market_cell,parent,false);

        return new MarketAdapter.ViewHolder(convertView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MarketDetails marketDetails=marketDetailsArrayList.get(position);
        holder.textView.setText(marketDetails.getProduct_name());
        Picasso.with(context).load(marketDetails.getProduct_image()).
                resize(50, 50).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return marketDetailsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.product_img);
            textView=itemView.findViewById(R.id.product_txt);
        }
    }




}
