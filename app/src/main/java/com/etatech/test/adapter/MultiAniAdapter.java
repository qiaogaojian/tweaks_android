package com.etatech.test.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.etatech.test.R;
import com.etatech.test.databinding.ItemMultiAnimtionBinding;
import com.mega.imageloader.ImageLoader;

import java.util.List;

/**
 * Created by Michael
 * Date:  2020/6/28
 * Func:
 */
public class MultiAniAdapter extends RecyclerView.Adapter<MultiAniAdapter.ViewHolder> {
    private List<String> imgList;

    public MultiAniAdapter(List<String> list) {
        imgList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMultiAnimtionBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_multi_animtion, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String imgUrl = imgList.get(position);
        if (position == 6) {
            ImageLoader.displayAsBitmapImage(holder.itemMultiAnimtionBinding.ivAni, imgUrl);
        } else {
            ImageLoader.displayImage(holder.itemMultiAnimtionBinding.ivAni, imgUrl);
        }
    }

    @Override
    public int getItemCount() {
        return imgList == null ? 0 : imgList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ItemMultiAnimtionBinding itemMultiAnimtionBinding;

        public ViewHolder(ItemMultiAnimtionBinding itemView) {
            super(itemView.getRoot());
            itemMultiAnimtionBinding = itemView;
        }
    }
}
