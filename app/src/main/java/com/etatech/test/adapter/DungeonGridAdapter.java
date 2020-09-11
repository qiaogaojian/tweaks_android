package com.etatech.test.adapter;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.etatech.test.R;
import com.etatech.test.bean.NodeBean;
import com.etatech.test.databinding.ItemDungeonGridBinding;
import com.etatech.test.utils.ui.ClickUtil;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by Michael
 * Date:  2020/9/1
 * Func:
 */
public class DungeonGridAdapter extends RecyclerView.Adapter<DungeonGridAdapter.VH> {
    private List<NodeBean> nodeList;

    public void refreshPath(List<NodeBean> list) {
        if (list == null || list.size() == 0) {
            ToastUtils.showShort("empty node list");
        }
        nodeList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemDungeonGridBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_dungeon_grid, viewGroup, false);
        return new VH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, final int position) {
        NodeBean node = nodeList.get(position);

        switch (node.getReachSate()) {
            case -1:
                holder.binding.ivBg.setBackgroundColor(Color.parseColor("#F92671"));
                break;
            case 0:
                if (node.getTileType().equals(NodeBean.TileType.OpenDoor)) {
                    holder.binding.ivBg.setBackgroundColor(Color.parseColor("#FA961E"));
                } else {
                    holder.binding.ivBg.setBackgroundColor(Color.parseColor("#65D9EF"));
                }
                break;
            case 1:
                holder.binding.ivBg.setBackgroundColor(Color.parseColor("#2DE2A6"));
                break;
            case 2:
                holder.binding.ivBg.setBackgroundColor(Color.parseColor("#AE81FF"));
                break;
            case 3:
                holder.binding.ivBg.setBackgroundColor(Color.parseColor("#A0DA2D"));
                break;
        }

        ClickUtil.setOnClick(holder.binding.getRoot(), new Action1() {
            @Override
            public void call(Object o) {
                if (nodeList.get(position).getReachSate() == -1) {
                    nodeList.get(position).setReachSate(0);
                    notifyItemChanged(position);
                } else if (nodeList.get(position).getReachSate() == 0) {
                    nodeList.get(position).setReachSate(-1);
                    notifyItemChanged(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return nodeList == null ? 0 : nodeList.size();
    }

    class VH extends RecyclerView.ViewHolder {
        ItemDungeonGridBinding binding;

        public VH(ItemDungeonGridBinding view) {
            super(view.getRoot());
            binding = view;
        }
    }
}
