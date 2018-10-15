/*
 *    Copyright (C) 2015 Haruki Hasegawa
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

package com.senthil.prabhu.mobile.android.godenscent.model;

public abstract class AbstractExpandableDataProvider {
    public static abstract class BaseData {

        public abstract String getText();

        public abstract void setPinned(boolean pinned);

        public abstract boolean isPinned();
    }

    public static abstract class GroupData extends BaseData {
        public abstract boolean isSectionHeader();

        public abstract long getGroupId();
    }

    public static abstract class GridData extends BaseData {
        public abstract long getGridId();
    }

    public abstract int getGroupCount();

    public abstract int getGridCount(int groupPosition);

    public abstract GroupData getGroupItem(int groupPosition);

    public abstract GridData getGridItem(int groupPosition, int gridPosition);


}
