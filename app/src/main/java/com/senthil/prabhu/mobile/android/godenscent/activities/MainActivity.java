package com.senthil.prabhu.mobile.android.godenscent.activities;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ScrollView;

import com.h6ah4i.android.widget.advrecyclerview.animator.GeneralItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.animator.RefactoredDefaultItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.decoration.SimpleListDividerDecorator;
import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemAdapter;
import com.senthil.prabhu.mobile.android.godenscent.R;
import com.senthil.prabhu.mobile.android.godenscent.adapter.BestSellerAdapter;
import com.senthil.prabhu.mobile.android.godenscent.adapter.CategoriesAdapter;
import com.senthil.prabhu.mobile.android.godenscent.adapter.ExpandableGridItemAdapter;
import com.senthil.prabhu.mobile.android.godenscent.constants.AppConstants;
import com.senthil.prabhu.mobile.android.godenscent.model.ExampleExpandableDataProvider;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, RecyclerViewExpandableItemManager.OnGroupCollapseListener,
        RecyclerViewExpandableItemManager.OnGroupExpandListener, AbstractExpandableItemAdapter.OnCellClickListener {

    private static final String SAVED_STATE_EXPANDABLE_ITEM_MANAGER = "RecyclerViewExpandableItemManager";

    private RecyclerViewExpandableItemManager mRecyclerViewExpandableItemManager;
    private RecyclerView.Adapter mWrappedAdapter;
    private ExampleExpandableDataProvider mDataProvider;
    private ExpandableGridItemAdapter myItemAdapter = null;

    private int lastExpandedPosition = -10;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpRecyvlerViews();
        mDataProvider = new ExampleExpandableDataProvider();
        setUpExpandableRecyclerView(mDataProvider, savedInstanceState);
        mRecyclerViewExpandableItemManager.expandGroup(0);


    }

    private void setUpRecyvlerViews() {

        CategoriesAdapter categoriesAdapter = new CategoriesAdapter(this, AppConstants.categories);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(categoriesAdapter);

        BestSellerAdapter bestSellerAdapter = new BestSellerAdapter(this, AppConstants.categories);
        RecyclerView bestSellerRecyclerView = findViewById(R.id.bestSellerRecyclerView);
        bestSellerRecyclerView.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.HORIZONTAL, false));
        bestSellerRecyclerView.setItemAnimator(new DefaultItemAnimator());
        bestSellerRecyclerView.setAdapter(bestSellerAdapter);


    }


    private void setUpExpandableRecyclerView(ExampleExpandableDataProvider mDataProvider, Bundle savedInstanceState) {
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
        RecyclerView mRecyclerView = findViewById(R.id.expandable_recycler_view);
        final Parcelable eimSavedState = (savedInstanceState != null) ? savedInstanceState.getParcelable(SAVED_STATE_EXPANDABLE_ITEM_MANAGER) : null;
        mRecyclerViewExpandableItemManager = new RecyclerViewExpandableItemManager(eimSavedState);
        mRecyclerViewExpandableItemManager.setOnGroupExpandListener(this);
        mRecyclerViewExpandableItemManager.setOnGroupCollapseListener(this);

        myItemAdapter = new ExpandableGridItemAdapter(mDataProvider, MainActivity.this);
        mWrappedAdapter = mRecyclerViewExpandableItemManager.createWrappedAdapter(myItemAdapter);
        final GeneralItemAnimator animator = new RefactoredDefaultItemAnimator();

        animator.setSupportsChangeAnimations(false);

        assert mRecyclerView != null;
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mWrappedAdapter);
        mRecyclerView.setItemAnimator(animator);
        mRecyclerView.setHasFixedSize(true);


        mRecyclerView.addItemDecoration(new SimpleListDividerDecorator(ContextCompat.getDrawable(this,
                R.drawable.list_divider_h), true));
        mRecyclerViewExpandableItemManager.attachRecyclerView(mRecyclerView);
    }

    @Override
    public void onGroupCollapse(int groupPosition, boolean fromUser) {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onGroupExpand(int groupPosition, boolean fromUser) {

        if (lastExpandedPosition != -10 && lastExpandedPosition != groupPosition) {
            mRecyclerViewExpandableItemManager.collapseGroup(lastExpandedPosition);
        }
        lastExpandedPosition = groupPosition;
        myItemAdapter.notifyItemChanged(groupPosition);
        if (fromUser) {
            adjustScrollPositionOnGroupExpanded(groupPosition);

        }
    }

    @Override
    public void onCellClick(View v, Object data) {

    }


    private void adjustScrollPositionOnGroupExpanded(int groupPosition) {
        int childItemHeight = this.getResources().getDimensionPixelSize(R.dimen.list_grid_item_height);
        int topMargin = (int) (this.getResources().getDisplayMetrics().density * 8);

        mRecyclerViewExpandableItemManager.scrollToGroup(groupPosition, childItemHeight, topMargin, topMargin);
    }


}

