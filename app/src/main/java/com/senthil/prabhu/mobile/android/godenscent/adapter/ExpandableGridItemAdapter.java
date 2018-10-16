/*
 *    Copyright (C) 2016 Fabrice Thilaw
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.senthil.prabhu.mobile.android.godenscent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.h6ah4i.android.widget.advrecyclerview.expandable.ExpandableItemConstants;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemViewHolder;
import com.senthil.prabhu.mobile.android.godenscent.R;
import com.senthil.prabhu.mobile.android.godenscent.constants.AppConstants;
import com.senthil.prabhu.mobile.android.godenscent.model.AbstractExpandableDataProvider;
import com.senthil.prabhu.mobile.android.godenscent.model.ExampleExpandableDataProvider;
import com.senthil.prabhu.mobile.android.godenscent.widget.ExpandableItemIndicator;

import java.util.List;

public class ExpandableGridItemAdapter
        extends AbstractExpandableItemAdapter<ExpandableGridItemAdapter.MyGroupViewHolder, ExpandableGridItemAdapter.GridRowHolder> {
    private static final String TAG = "ExpandableGridItemAdapter";
    private final OnCellClickListener mCellCallback;

    private View lastSelectedCell = null;

    public String getLastSelectedData() {
        return lastSelectedData;
    }

    public void setLastSelectedData(String lastSelectedData) {
        this.lastSelectedData = lastSelectedData;
    }

    private String lastSelectedData = "";

    // NOTE: Make accessible with short name
    private interface Expandable extends ExpandableItemConstants {
    }

    private Context context;
    private AbstractExpandableDataProvider mProvider;

    public View getLastSelectedCell() {
        return lastSelectedCell;
    }

    public void setLastSelectedCell(View lastSelectedCell) {
        this.lastSelectedCell = lastSelectedCell;
    }

    public static abstract class MyBaseViewHolder extends AbstractExpandableItemViewHolder {
        public FrameLayout mContainer;
        public TextView mTextView;


        public MyBaseViewHolder(View v) {
            super(v);
            mContainer = v.findViewById(R.id.container);
            mTextView = v.findViewById(android.R.id.text1);
        }

    }

    public class MyGroupViewHolder extends MyBaseViewHolder {
        public ExpandableItemIndicator mIndicator;

        public MyGroupViewHolder(View v) {
            super(v);
            mIndicator = v.findViewById(R.id.indicator);
        }
    }

    public class GridRowHolder extends MyBaseViewHolder implements View.OnClickListener {
        TextView[] dataDisplay = new TextView[AppConstants.MAX_CELLS_PER_GRID_ROW];
        ImageView[] imageViews = new ImageView[AppConstants.MAX_CELLS_PER_GRID_ROW];
        RelativeLayout[] cells = new RelativeLayout[AppConstants.CELLS_IDS.length];
        OnCellClickListener cellClickListener = null;
        private List<String> gridData;
        private long parentId = -1;

        public GridRowHolder(View v, OnCellClickListener cellClickListener) {
            super(v);
            // if (cellClickListener == null) {
            this.cellClickListener = cellClickListener;
            // }
            if (cellClickListener == null) {
                throw new ExceptionInInitializerError();
            }
            dataDisplay = new TextView[]{
                    v.findViewById(R.id.text1),
                    v.findViewById(R.id.text2),
                    v.findViewById(R.id.text3)
            };

            imageViews = new ImageView[]{
                    v.findViewById(R.id.thumbNail1),
                    v.findViewById(R.id.thumbNail2),
                    v.findViewById(R.id.thumbNail3)
            };
            cells = new RelativeLayout[]{
                    v.findViewById(AppConstants.CELLS_IDS[0]),
                    v.findViewById(AppConstants.CELLS_IDS[1]),
                    v.findViewById(AppConstants.CELLS_IDS[2])
            };

        }

        public void populateGrid(long parentId, List<String> data, List<Integer> thumbNails, String lastSelectedData) {

            this.gridData = data;
            this.parentId = parentId;
            // make sure grid data array does not exceed the number of cells
            if (data.size() > cells.length) {
                throw new ArrayIndexOutOfBoundsException();
            }
            int lastDataIndex = -1;
            for (int i = 0; i < gridData.size(); i++) {
                this.dataDisplay[i].setText(gridData.get(i));
                this.imageViews[i].setImageResource(thumbNails.get(i));

                cells[i].setTag(gridData.get(i));
                cells[i].setOnClickListener(this);
                cells[i].setVisibility(View.VISIBLE);
                lastDataIndex = i;
            }
            // remove unused cells from row
            cleanUpGrid(lastDataIndex);

        }

        // RecyclerView is called so because it reuses the layouts of its list items
        // So we do make sure that only necessary cells of a recycled row can stay visible :)
        private void cleanUpGrid(int lastUsedIndex) {
            lastUsedIndex++;
            for (int j = lastUsedIndex; j < cells.length; j++) {
                // hide unused cell
                cells[j].setVisibility(View.GONE);
            }
        }

        /**
         * Called when a view has been clicked
         *
         * @param v The view view that was clicked.
         */
        @Override
        public void onClick(View v) {
        }
    }

    public ExpandableGridItemAdapter(AbstractExpandableDataProvider dataProvider, OnCellClickListener cellClickCallback) {

        mProvider = dataProvider;
        mCellCallback = cellClickCallback;
        if (mCellCallback == null) {
            throw new ExceptionInInitializerError();
        }
        // ExpandableItemAdapter requires stable ID, and also
        // have to implement the getGroupItemId()/getChildItemId() methods appropriately.
        setHasStableIds(true);
    }

    @Override
    public int getGroupCount() {
        return mProvider.getGroupCount();
    }

    @Override
    public int getChildCount(int groupPosition) {
        // Value returned here is the paginated version of children list
        // as we want to put four (4) children in each child row
        // to provide an illusive gridview for children
        return ((ExampleExpandableDataProvider.ConcreteGroupData) mProvider.getGroupItem(groupPosition)).getConcreteGridDataList().size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition; // or mProvider.getGroupItem(groupPosition).getGroupId();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return ((ExampleExpandableDataProvider.ConcreteGroupData) mProvider.getGroupItem(groupPosition)).
                getConcreteGridDataList().get(childPosition).getGridId();
    }

    @Override
    public int getGroupItemViewType(int groupPosition) {
        return 0;
    }

    @Override
    public int getChildItemViewType(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public MyGroupViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.list_group_item, parent, false);
        return new MyGroupViewHolder(v);
    }

    @Override
    public GridRowHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.list_grid_item, parent, false);
        return new GridRowHolder(v, mCellCallback);
    }

    @Override
    public void onBindGroupViewHolder(MyGroupViewHolder holder, int groupPosition, int viewType) {
        // child item
        final AbstractExpandableDataProvider.BaseData item = mProvider.getGroupItem(groupPosition);

        // set text
        holder.mTextView.setText(item.getText());

        // mark as clickable
        holder.itemView.setClickable(true);

        // set background resource (target view ID: container)
        final int expandState = holder.getExpandStateFlags();

        if ((expandState & ExpandableItemConstants.STATE_FLAG_IS_UPDATED) != 0) {
            int bgResId;
            boolean isExpanded;
            boolean animateIndicator = ((expandState & Expandable.STATE_FLAG_HAS_EXPANDED_STATE_CHANGED) != 0);

            if ((expandState & Expandable.STATE_FLAG_IS_EXPANDED) != 0) {
                bgResId = R.drawable.bg_group_item_expanded_state;
                isExpanded = true;
            } else {
                bgResId = R.drawable.bg_group_item_normal_state;
                isExpanded = false;
            }

            holder.mContainer.setBackgroundResource(bgResId);
            holder.mIndicator.setExpandedState(isExpanded, animateIndicator);
        }
    }

    @Override
    public void onBindChildViewHolder(GridRowHolder holder, int groupPosition, int childPosition, int viewType) {
        final ExampleExpandableDataProvider.ConcreteGridData item =
                (ExampleExpandableDataProvider.ConcreteGridData) mProvider.getGridItem(groupPosition, childPosition);

        List<String> gridData = item.getGridDataArray();
        List<Integer> gridImages = item.getGridImageArray();
        holder.populateGrid(groupPosition, gridData, gridImages, getLastSelectedData());
        int bgResId;
        bgResId = R.drawable.bg_item_normal_state;

    }

    @Override
    public boolean onCheckCanExpandOrCollapseGroup(MyGroupViewHolder holder, int groupPosition, int x, int y, boolean expand) {
        // check the item is *not* pinned
        if (mProvider.getGroupItem(groupPosition).isPinned()) {
            // return false to raise View.OnClickListener#onClick() event
            return false;
        }

        // check is enabled
        return holder.itemView.isEnabled() && holder.itemView.isClickable();

    }
}