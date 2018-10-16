package com.senthil.prabhu.mobile.android.godenscent.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.senthil.prabhu.mobile.android.godenscent.R;
import com.senthil.prabhu.mobile.android.godenscent.constants.AppConstants;

/**
 * Created by PrAbHu on 10/16/18 GodenScent.
 */
public class BestSellerAdapter extends RecyclerView.Adapter<BestSellerAdapter.MyViewHolder> {

    private String[] categories;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView productName, productDescription, productOldPrice, productNewPrice;
        private ImageView productImage;


        public MyViewHolder(View view) {
            super(view);
            productImage = view.findViewById(R.id.productImage);
            productName = view.findViewById(R.id.producName);
            productDescription = view.findViewById(R.id.productFlavour);
            productOldPrice = view.findViewById(R.id.oldPrice);
            productNewPrice = view.findViewById(R.id.newPrice);

        }
    }


    public BestSellerAdapter(Context context, String[] categories) {
        this.categories = categories;

    }

    @Override
    public BestSellerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_for_best_seller, parent, false);

        return new BestSellerAdapter.MyViewHolder(itemView);
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void onBindViewHolder(BestSellerAdapter.MyViewHolder holder, final int position) {

        holder.productName.setText(AppConstants.bestSellersProductName[position]);
        holder.productDescription.setText(AppConstants.bestSellersProductDesc[position]);
        holder.productImage.setImageResource(AppConstants.bestSellersProductImage[position]);
        holder.productOldPrice.setText(AppConstants.bestSellersProductOldPrice[position]);
        if (AppConstants.bestSellersProductNewPrice[position].length() > 0) {
            holder.productNewPrice.setText(AppConstants.bestSellersProductNewPrice[position]);
            holder.productOldPrice.setTextColor(Color.GRAY);
            holder.productOldPrice.setPaintFlags(holder.productOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }


    }

    @Override
    public int getItemCount() {
        return AppConstants.bestSellersProductName.length;
    }
}
