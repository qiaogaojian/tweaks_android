package com.etatech.test.adapter;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.databinding.DataBindingUtil;
import android.os.Handler;
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

    private android.os.Handler handler = new Handler();
    private RecyclerView rv;
    private List<Integer> cardStates;

    public void setRv(RecyclerView rv) {
        this.rv = rv;
    }

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
                notifyItemChanged(position);
                final int indexOfFrontChild = position;
                rv.setChildDrawingOrderCallback(new RecyclerView.ChildDrawingOrderCallback() {
                    private int nextChildIndexToRender;
                    @Override
                    public int onGetChildDrawingOrder(int childCount, int iteration) {
                        int childPos = iteration;
                        // index must be in at least smaller than all children's
                        if (indexOfFrontChild < childCount) {
                            // in the last iteration we return view we want to have on top
                            if (iteration == childCount - 1) {
                                childPos = indexOfFrontChild;
                            }else if (iteration >= indexOfFrontChild) {
                                childPos = iteration + 1;
                            }
                        }
                        return childPos;
                    }
                });

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AnimatorSet animatorSetBack = new AnimatorSet();
                        ObjectAnimator back1 = ObjectAnimator.ofFloat(holder.binding.layoutBack, View.ROTATION, 0, 25);
                        ObjectAnimator back2 = ObjectAnimator.ofFloat(holder.binding.layoutBack, View.ROTATION_Y, 0, 90);
                        ObjectAnimator back3 = ObjectAnimator.ofFloat(holder.binding.layoutBack, View.SCALE_X, 1f, 1.6f);
                        ObjectAnimator back4 = ObjectAnimator.ofFloat(holder.binding.layoutBack, View.SCALE_Y, 1f, 1.6f);
                        animatorSetBack.setDuration(500);
                        animatorSetBack.playTogether(back1, back2, back3, back4);

                        final AnimatorSet animatorSetFront = new AnimatorSet();
                        ObjectAnimator front1 = ObjectAnimator.ofFloat(holder.binding.layoutFront, View.ROTATION, 25, 0);
                        ObjectAnimator front2 = ObjectAnimator.ofFloat(holder.binding.layoutFront, View.ROTATION_Y, -90, 0);
                        ObjectAnimator front3 = ObjectAnimator.ofFloat(holder.binding.layoutFront, View.SCALE_X, 1.6f, 1f);
                        ObjectAnimator front4 = ObjectAnimator.ofFloat(holder.binding.layoutFront, View.SCALE_Y, 1.6f, 1f);
                        animatorSetFront.setDuration(500);
                        animatorSetFront.playTogether(front1, front2, front3, front4);
                        holder.binding.layoutFront.setVisibility(View.INVISIBLE);

                        animatorSetBack.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                holder.binding.layoutBack.setVisibility(View.INVISIBLE);
                                holder.binding.layoutFront.setVisibility(View.VISIBLE);
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
                }, 300);
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
