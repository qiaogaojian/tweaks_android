package com.etatech.test.view.practice6;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.etatech.test.R;

public class Practice04Alpha extends RelativeLayout {
    Button animateBt;
    ImageView imageView;

    public Practice04Alpha(Context context) {
        super(context);
    }

    public Practice04Alpha(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice04Alpha(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    int state =0;
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        animateBt = (Button) findViewById(R.id.animateBt);
        imageView = (ImageView) findViewById(R.id.imageView);

        animateBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                // 在这里处理点击事件，通过 View.animate().alpha() 来改变 View 的透明度

                if (state % 2 == 0) {
                    imageView.animate().alpha(0);
                } else {
                    imageView.animate().alpha(1);
                }
                state++;
            }
        });
    }
}