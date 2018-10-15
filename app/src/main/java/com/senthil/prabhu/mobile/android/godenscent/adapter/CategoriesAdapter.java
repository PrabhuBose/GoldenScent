package com.senthil.prabhu.mobile.android.godenscent.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.senthil.prabhu.mobile.android.godenscent.R;

/**
 * Created by PrAbHu on 10/15/18 GodenScent.
 */
public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyViewHolder> {

    private String[] categories;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView categoryName;

        public MyViewHolder(View view) {
            super(view);

            categoryName = view.findViewById(R.id.categoryName);

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

    }

    @Override
    public int getItemCount() {
        return categories.length;
    }
}
