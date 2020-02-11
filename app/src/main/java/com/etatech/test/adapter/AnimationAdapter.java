package com.etatech.test.adapter;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.etatech.test.R;
import com.etatech.test.databinding.ItemTestAnimationBinding;
import com.etatech.test.utils.ui.ClickUtil;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by Michael
 * Date:  2020/2/11
 * Func:
 */
public class AnimationAdapter extends RecyclerView.Adapter<AnimationAdapter.VH> {

    private List<Integer> cardStates;

    public void setCardStates(List<Integer> cardStates) {
        this.cardStates = cardStates;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemTestAnimationBinding view = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_test_animation, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(final VH holder, final int position) {
        holder.binding.getRoot().setTag(position);
        float offsetY = AdaptScreenUtils.pt2Px(42);
        float offset = 0;
        if (position % 6 == 0) {
            offset = offsetY * 5;
        } else if (position % 6 == 1) {
            offset = offsetY * 4;
        } else if (position % 6 == 2) {
            offset = offsetY * 3;
        } else if (position % 6 == 3) {
            offset = offsetY * 2;
        } else if (position % 6 == 4) {
            offset = offsetY * 1;
        } else if (position % 6 == 5) {
            offset = offsetY * 0;
        }

        ObjectAnimator transY = ObjectAnimator.ofFloat(holder.binding.layoutCard, View.TRANSLATION_Y, 0f, offset);
        transY.setDuration(0);
        transY.start();

        ClickUtil.setOnClick(holder.binding.layoutCard, new Action1() {
            @Override
            public void call(Object o) {
                if (cardStates.get(position) == 1) {
                    return;
                }
                cardStates.set(position, 1);

                AnimatorSet animatorSetBack = new AnimatorSet();
                ObjectAnimator ani1 = ObjectAnimator.ofFloat(holder.binding.ivBack, View.ROTATION, 0, 25);
                ObjectAnimator ani2 = ObjectAnimator.ofFloat(holder.binding.ivBack, View.ROTATION_Y, 0, 90);
                ObjectAnimator ani5 = ObjectAnimator.ofFloat(holder.binding.ivBack, "scaleY", 1f, 1.6f);
                ObjectAnimator ani6 = ObjectAnimator.ofFloat(holder.binding.ivBack, "scaleX", 1f, 1.6f);
                animatorSetBack.setDuration(500);
                animatorSetBack.playTogether(ani1, ani2, ani5, ani6);

                final AnimatorSet animatorSetFront = new AnimatorSet();
                ObjectAnimator ani3 = ObjectAnimator.ofFloat(holder.binding.ivFront, View.ROTATION, 25, 0);
                ObjectAnimator ani4 = ObjectAnimator.ofFloat(holder.binding.ivFront, View.ROTATION_Y, -90, 0);
                ObjectAnimator ani7 = ObjectAnimator.ofFloat(holder.binding.ivFront, "scaleY", 1.6f, 1f);
                ObjectAnimator ani8 = ObjectAnimator.ofFloat(holder.binding.ivFront, "scaleX", 1.6f, 1f);
                animatorSetFront.setDuration(500);
                animatorSetFront.playTogether(ani3, ani4, ani7, ani8);
                holder.binding.ivFront.setVisibility(View.INVISIBLE);

                animatorSetBack.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        holder.binding.ivBack.setVisibility(View.INVISIBLE);
                        holder.binding.ivFront.setVisibility(View.VISIBLE);
                        animatorSetFront.start();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                animatorSetBack.start();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cardStates == null ? 0 : cardStates.size();
    }

    class VH extends RecyclerView.ViewHolder {
        ItemTestAnimationBinding binding;

        public VH(ItemTestAnimationBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
