package com.senthil.prabhu.mobile.android.godenscent.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.senthil.prabhu.mobile.android.godenscent.R;
import com.senthil.prabhu.mobile.android.godenscent.constants.AppConstants;

/**
 * Created by PrAbHu on 10/15/18 GodenScent.
 */
public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyViewHolder> {

    private String[] categories;
    private int rowIndex = 0;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView categoryName, boder;
        public ImageView categoryIcon;
        private CardView cardView;

        public MyViewHolder(View view) {
            super(view);

            categoryName = view.findViewById(R.id.categoryName);
            categoryIcon = view.findViewById(R.id.categoryIcon);
            cardView = view.findViewById(R.id.notification_card_view);
            boder = view.findViewById(R.id.borderImage);

        }
    }


    public CategoriesAdapter(Context context, String[] categories) {
        this.categories = categories;

    }

    @Override
    public CategoriesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_for_categories, parent, false);

        return new CategoriesAdapter.MyViewHolder(itemView);
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void onBindViewHolder(CategoriesAdapter.MyViewHolder holder, final int position) {

        holder.categoryName.setText(categories[position]);
        holder.categoryIcon.setImageResource(AppConstants.categoriesImages[position]);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rowIndex = position;
                notifyDataSetChanged();
            }
        });


        if (rowIndex == position) {
            holder.boder.setBackgroundColor(Color.BLACK);
        } else {
            holder.boder.setBackgroundColor(Color.WHITE);
        }

    }

    @Override
    public int getItemCount() {
        return categories.length;
    }
}
