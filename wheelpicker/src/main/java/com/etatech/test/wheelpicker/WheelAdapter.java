package com.etatech.test.wheelpicker;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.LinearLayout;

//基础的适配器，必须集成此类实现适配器
public abstract class WheelAdapter<T> extends RecyclerView.Adapter<WheelViewHolder> {
    protected static final int VIEW_TYPE_HEADER = -234234;
    protected static final int VIEW_TYPE_FOOTER = -23674632;
    private int itemHeight = 120;
    protected int itemHeadOrFootSize = 2;
    private RecyclerWheelPickerCoreView<T> picker;

    protected int getItemHeight() {
        return itemHeight;
    }

    protected abstract int getPositionByValue(T t);

    // itemMaxShowSize 必须为奇数
    protected void setPicker(RecyclerWheelPickerCoreView<T> picker, int itemHeight, int itemMaxShowSize) {
        this.itemHeight = itemHeight;
        this.itemHeadOrFootSize = (itemMaxShowSize - 1) / 2;
        this.picker = picker;
    }

    // 获取数据的真实position
    protected final int getDataPosition(int position) {
        if (position < itemHeadOrFootSize) {
            return 0;
        } else if (position >= getWheelItemCount() + itemHeadOrFootSize) {
            return getWheelItemCount() - 1;
        } else {
            return position - itemHeadOrFootSize;
        }
    }

    public RecyclerWheelPickerCoreView<T> getPicker() {
        return picker;
    }

    public final WheelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_HEADER) {
            return new WheelViewHolder(new LinearLayout(parent.getContext())).setItemLayoutParameter(itemHeight);
        } else if (viewType == VIEW_TYPE_FOOTER) {
            return new WheelViewHolder(new LinearLayout(parent.getContext())).setItemLayoutParameter(itemHeight);
        }
        return onWheelCreateViewHolder(parent, viewType).setItemLayoutParameter(itemHeight);
    }

    // 创建viewHolder
    protected abstract WheelViewHolder onWheelCreateViewHolder(@NonNull ViewGroup parent, int viewType);

    // 会被多次调用，每当选择器停止滚动，选中区域就会回调此函数
    protected abstract void onWheelSelected(RecyclerView.ViewHolder holder, int position, T t);

    // item形变，缩放透明之类的  progress 0~1，代表形变的程度越靠近中心区域，值越大
    protected abstract void onWheelScrollTranslate(RecyclerView.ViewHolder holder, float progress);

    @Deprecated
    @Override
    public final int getItemViewType(int position) {
        if (position < itemHeadOrFootSize) {
            return VIEW_TYPE_HEADER;
        }
        if (position > getItemCount() - 1 - itemHeadOrFootSize) {
            return VIEW_TYPE_FOOTER;
        }
        return getWheelItemViewType(position - 1);
    }

    // 支持设置viewType
    public int getWheelItemViewType(int position) {
        return 0;
    }

    @Deprecated
    @Override
    public final void onBindViewHolder(@NonNull WheelViewHolder viewHolder, int position) {
        //            viewHolder.itemView.getLayoutParams().height=itemHeight;
        if (getItemViewType(position) != VIEW_TYPE_FOOTER && getItemViewType(position) != VIEW_TYPE_HEADER) {
            int vP = getDataPosition(position);
            onWheelBindViewHolder(viewHolder, vP, getWheelItemData(vP));
            picker.refreshItemTranslate(viewHolder.itemView);
        }
    }

    // 接管onBindViewHolder实现viewHolder的设置
    protected abstract void onWheelBindViewHolder(@NonNull WheelViewHolder holder, int position, T t);

    // 获取元素的实际个数，隐藏函数
    @Deprecated
    @Override
    public final int getItemCount() {
        return getWheelItemCount() + itemHeadOrFootSize * 2;
    }

    // 需要实现此函数，根据索引获取相应的值
    protected abstract T getWheelItemData(int position);

    protected abstract int getWheelItemCount();
}