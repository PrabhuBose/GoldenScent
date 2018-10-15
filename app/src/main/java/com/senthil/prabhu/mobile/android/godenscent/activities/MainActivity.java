package com.senthil.prabhu.mobile.android.godenscent.activities;

import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.h6ah4i.android.widget.advrecyclerview.animator.GeneralItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.animator.RefactoredDefaultItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.decoration.SimpleListDividerDecorator;
import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemAdapter;
import com.senthil.prabhu.mobile.android.godenscent.R;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initViews();

        mDataProvider = new ExampleExpandableDataProvider();

        setupRecyclerView(mDataProvider, savedInstanceState);
        // make the first item expanded by default when activity is first opened
        mRecyclerViewExpandableItemManager.expandGroup(0);


    }

    private void initViews() {
        CategoriesAdapter categoriesAdapter = new CategoriesAdapter(this, AppConstants.categories);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(categoriesAdapter);

    }


    private void setupRecyclerView(ExampleExpandableDataProvider mDataProvider, Bundle savedInstanceState) {
        //noinspection ConstantConditions
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.expandable_recycler_view);
        final Parcelable eimSavedState = (savedInstanceState != null) ? savedInstanceState.getParcelable(SAVED_STATE_EXPANDABLE_ITEM_MANAGER) : null;
        mRecyclerViewExpandableItemManager = new RecyclerViewExpandableItemManager(eimSavedState);
        mRecyclerViewExpandableItemManager.setOnGroupExpandListener(this);
        mRecyclerViewExpandableItemManager.setOnGroupCollapseListener(this);

        //adapter
        myItemAdapter = new ExpandableGridItemAdapter(mDataProvider, MainActivity.this);
        mWrappedAdapter = mRecyclerViewExpandableItemManager.createWrappedAdapter(myItemAdapter);
        // wrap for expanding
        final GeneralItemAnimator animator = new RefactoredDefaultItemAnimator();
        // Change animations are enabled by default since support-v7-recyclerview v22.
        // Need to disable them when using animation indicator.
        animator.setSupportsChangeAnimations(false);

        assert mRecyclerView != null;
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mWrappedAdapter);  // requires *wrapped* adapter
        mRecyclerView.setItemAnimator(animator);
        mRecyclerView.setHasFixedSize(true);

        if (supportsViewElevation()) {
            // Lollipop or later has native drop shadow feature. ItemShadowDecorator is not required.
        } else {
//            mRecyclerView.addItemDecoration(new ItemShadowDecorator((NinePatchDrawable) ContextCompat.getDrawable(this, R.drawable.material_shadow_z1)));
        }
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

    private boolean supportsViewElevation() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
    }

    private void adjustScrollPositionOnGroupExpanded(int groupPosition) {
        int childItemHeight = this.getResources().getDimensionPixelSize(R.dimen.list_grid_item_height);
        int topMargin = (int) (this.getResources().getDisplayMetrics().density * 8);

        mRecyclerViewExpandableItemManager.scrollToGroup(groupPosition, childItemHeight, topMargin, topMargin);
    }

}
