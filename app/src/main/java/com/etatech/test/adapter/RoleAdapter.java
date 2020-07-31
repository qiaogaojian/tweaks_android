package com.etatech.test.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.databinding.DataBindingUtil;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.etatech.test.R;
import com.etatech.test.databinding.ItemRecyclerviewAnimationBinding;
import com.etatech.test.utils.ui.ClickUtil;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

/**
 * Created by Michael
 * Date:  2020/7/30
 * Func:
 */
public class RoleAdapter extends RecyclerView.Adapter<RoleAdapter.ViewHolder> {
    private List<Integer> roleList = new ArrayList<>();
    private List<Boolean> selectTags = new ArrayList<>();
    // 角色TAG
    public final int FARMER_TAG = 0;             // 0.平民
    public final int WEREWOLF_TAG = 1;           // 1.狼人
    public final int PROPHET_TAG = 2;            // 2.预言家
    public final int WITCH_TAG = 3;              // 3.女巫
    public final int HUNTER_TAG = 4;             // 4.猎人
    public final int GUARD_TAG = 5;              // 6.守卫
    public final int GHOST_RIDER_TAG = 6;       // 16.恶灵骑士
    public final int GRAVE_KEEPER_TAG = 7;      // 18.守墓人
    public final int HIDDEN_WOLF_TAG = 8;      // 23.狂人

    private int originHeight;
    private int state = -1;  // 1 显示 2 隐藏

    public void setData(List<Integer> roleList) {
        this.roleList.addAll(roleList);
        this.selectTags = new ArrayList<>();
        for (int i = 0; i < roleList.size(); i++) {
            selectTags.add(false);
        }
        selectTags.set(0, true);
    }

    public void refresh(List<Integer> roleList) {
        this.roleList.clear();
        this.roleList.addAll(roleList);
        this.selectTags.clear();
        for (int i = 0; i < roleList.size(); i++) {
            selectTags.add(false);
        }
        selectTags.set(0, true);
        notifyDataSetChanged();
    }

    public void add(Integer roleId) {
        state = 1;
        roleList.add(0, roleId);
        selectTags.add(0, false);
        notifyItemInserted(0);
    }

    public void remove(int position, RecyclerView.ViewHolder vh) {
        hide(position, (ViewHolder) vh);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemRecyclerviewAnimationBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_recyclerview_animation, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        int role = roleList.get(position);

        if (selectTags.get(position)) {
            holder.binding.ivRole.setImageResource(getChooseImg(role));
        } else {
            holder.binding.ivRole.setImageResource(getNormallImg(role));
        }

        ClickUtil.setOnClick(holder.binding.ivRole, new Action1() {
            @Override
            public void call(Object o) {
                selectRole(roleList, position);
                notifyDataSetChanged();
            }
        });

        if (position == 0 && state == 1) {
            show(holder);
            state = -1;
        }
    }

    private void show(final ViewHolder holder) {
        if (originHeight == 0) {
            originHeight = holder.binding.ivRole.getHeight();
        }
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setIntValues(0, originHeight);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                holder.binding.layoutItemRole.getLayoutParams().height = (int) animation.getAnimatedValue();
                holder.binding.layoutItemRole.requestLayout();
            }
        });

        valueAnimator.start();
        holder.binding.layoutItemRole.setVisibility(View.VISIBLE);
    }

    private void hide(final int pos, final ViewHolder holder) {
        if (originHeight == 0) {
            originHeight = holder.binding.ivRole.getHeight();
        }
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setIntValues(originHeight, 0);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                holder.binding.layoutItemRole.getLayoutParams().height = (int) animation.getAnimatedValue();
                holder.binding.layoutItemRole.requestLayout();
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                holder.binding.layoutItemRole.setVisibility(View.GONE);
                roleList.remove(pos);
                selectTags.remove(pos);
                notifyItemRemoved(pos);
            }
        });

        valueAnimator.start();
    }

    private void selectRole(List<Integer> roleList, int position) {
        selectTags.clear();
        for (int i = 0; i < roleList.size(); i++) {
            selectTags.add(false);
        }
        selectTags.set(position, true);
    }

    @Override
    public int getItemCount() {
        return roleList == null ? 0 : roleList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemRecyclerviewAnimationBinding binding;

        public ViewHolder(ItemRecyclerviewAnimationBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }

    public int getNormallImg(int roleNo) {
        switch (roleNo) {
            case FARMER_TAG:        //村民 0
            default:
                return R.drawable.yxlc_jf_cm1;
            case WEREWOLF_TAG:      //鬼狼 1
                return R.drawable.yxlc_jf_gl1;
            case PROPHET_TAG:       //神子 2
                return R.drawable.yxlc_jf_sz1;
            case WITCH_TAG:         //魔女 3
                return R.drawable.yxlc_jf_mv1;
            case HUNTER_TAG:        //御弓者 4
                return R.drawable.yxlc_jf_ygz1;
            case GUARD_TAG:         //犬神 6
                return R.drawable.yxlc_jf_qs1;
            case GHOST_RIDER_TAG:   //烟罗 16
                return R.drawable.yxlc_jf_yl1;
            case GRAVE_KEEPER_TAG:  //神父 18
                return R.drawable.yxlc_jf_sf1;
            case HIDDEN_WOLF_TAG:   //川赤子 23
                return R.drawable.yxlc_jf_ccz1;

        }
    }

    public int getChooseImg(int roleNo) {
        switch (roleNo) {
            case FARMER_TAG:
            default:
                return R.drawable.yxlc_jf_cm2;
            case WEREWOLF_TAG:
                return R.drawable.yxlc_jf_gl2;
            case PROPHET_TAG:
                return R.drawable.yxlc_jf_sz2;
            case WITCH_TAG:
                return R.drawable.yxlc_jf_mv2;
            case HUNTER_TAG:
                return R.drawable.yxlc_jf_ygz2;
            case GUARD_TAG:
                return R.drawable.yxlc_jf_qs2;
            case GHOST_RIDER_TAG:
                return R.drawable.yxlc_jf_yl2;
            case GRAVE_KEEPER_TAG:
                return R.drawable.yxlc_jf_sf2;
            case HIDDEN_WOLF_TAG:
                return R.drawable.yxlc_jf_ccz2;

        }
    }
}

