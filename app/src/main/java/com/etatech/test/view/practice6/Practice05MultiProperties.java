package com.etatech.test.view.practice6;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.etatech.test.R;

public class Practice05MultiProperties extends ConstraintLayout {
    Button animateBt;
    ImageView imageView;

    public Practice05MultiProperties(Context context) {
        super(context);
    }

    public Practice05MultiProperties(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice05MultiProperties(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    int state = 0; // 0 隐藏 1 出现

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        animateBt = (Button) findViewById(R.id.animateBt);
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setScaleX(0);
        imageView.setScaleY(0);
        imageView.setAlpha(0f);
        animateBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state % 2 == 0) {
                    // 在这里处理点击事件，同时对多个属性做动画
                    imageView.animate().alpha(1)
                            .scaleX(1)
                            .scaleY(1)
                            .rotation(360)
                            .translationX(800);
                } else {
                    imageView.animate().alpha(0)
                            .scaleX(0)
                            .scaleY(0)
                            .rotation(-360)
                            .translationX(-800);
                }
                state++;
            }
        });
    }
}
