package com.etatech.test.adapter;

import androidx.databinding.DataBindingUtil;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.etatech.test.R;
import com.etatech.test.bean.NodeBean;
import com.etatech.test.databinding.ItemPathNodeBinding;
import com.etatech.test.utils.ui.ClickUtil;

import java.util.List;

import com.etatech.test.utils.rxbus.Action1;

/**
 * Created by Michael
 * Date:  2020/8/25
 * Func:
 */
public class PathNodeAdapter extends RecyclerView.Adapter<PathNodeAdapter.VH> {
    private List<NodeBean> nodeList;

    public PathNodeAdapter(List<NodeBean> nodeList) {
        this.nodeList = nodeList;
        notifyDataSetChanged();
    }

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
        ItemPathNodeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_path_node, viewGroup, false);
        return new VH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, final int position) {
        NodeBean node = nodeList.get(position);
        if (node.getF() != 0) {
            holder.binding.tvF.setText(node.getF() + "");
            holder.binding.tvG.setText(node.getG() + "");
            holder.binding.tvH.setText(node.getH() + "");
        } else {
            holder.binding.tvF.setText("");
            holder.binding.tvG.setText("");
            holder.binding.tvH.setText("");
        }

        switch (node.getReachSate()) {
            case -1:
                holder.binding.ivBg.setBackgroundColor(Color.parseColor("#F92671"));
                break;
            case 0:
                holder.binding.ivBg.setBackgroundColor(Color.parseColor("#65D9EF"));
                break;
            case 1:
                holder.binding.ivBg.setBackgroundColor(Color.parseColor("#2DE2A6"));
                holder.binding.tvF.setText(node.getF() + "");
                holder.binding.tvG.setText(node.getG() + "");
                holder.binding.tvH.setText(node.getH() + "");
                break;
            case 2:
                holder.binding.ivBg.setBackgroundColor(Color.parseColor("#AE81FF"));
                break;
            case 3:
                holder.binding.ivBg.setBackgroundColor(Color.parseColor("#A0DA2D"));
                break;
        }

        if (node.isPath() && !node.isEnd() && !node.isStart()) {
            holder.binding.ivPath.setVisibility(View.VISIBLE);
        } else {
            holder.binding.ivPath.setVisibility(View.INVISIBLE);
        }

        if (node.isEnd()) {
            holder.binding.ivEnd.setVisibility(View.VISIBLE);
        } else {
            holder.binding.ivEnd.setVisibility(View.INVISIBLE);
        }

        if (node.isStart()) {
            holder.binding.ivStart.setVisibility(View.VISIBLE);
        } else {
            holder.binding.ivStart.setVisibility(View.INVISIBLE);
        }

        ClickUtil.setOnClick(holder.binding.getRoot(), new Action1() {
            @Override
            public void accept(Object o) {
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
        return 100;
    }

    class VH extends RecyclerView.ViewHolder {
        ItemPathNodeBinding binding;

        public VH(ItemPathNodeBinding view) {
            super(view.getRoot());
            binding = view;
        }
    }
}
