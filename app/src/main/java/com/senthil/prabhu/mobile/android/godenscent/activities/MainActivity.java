package com.senthil.prabhu.mobile.android.godenscent.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.senthil.prabhu.mobile.android.godenscent.R;
import com.senthil.prabhu.mobile.android.godenscent.adapter.CategoriesAdapter;
import com.senthil.prabhu.mobile.android.godenscent.constants.AppConstants;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

    }

    private void initViews() {
        CategoriesAdapter categoriesAdapter = new CategoriesAdapter(this, AppConstants.categories);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(categoriesAdapter);
    }
}
