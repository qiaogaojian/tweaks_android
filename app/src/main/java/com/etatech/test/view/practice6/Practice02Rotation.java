package com.etatech.test.view.practice6;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.etatech.test.R;

public class Practice02Rotation extends RelativeLayout {
    Button animateBt;
    ImageView imageView;

    public Practice02Rotation(Context context) {
        super(context);
    }

    public Practice02Rotation(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice02Rotation(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
int state = 0;
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        animateBt = (Button) findViewById(R.id.animateBt);
        imageView = (ImageView) findViewById(R.id.imageView);

        animateBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                // 在这里处理点击事件，通过 View.animate().rotation/X/Y() 来让 View 旋转
                if (state % 2 == 0) {
                    imageView.animate().rotationX(180).setDuration(1000);
                } else {
                    imageView.animate().rotationX(0).setDuration(1000);
                }
                state++;
            }
        });
        animateBt.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (state % 2 == 0) {
                    imageView.animate().rotationY(180).setDuration(1000);
                } else {
                    imageView.animate().rotationY(0).setDuration(1000);
                }
                state++;
                return false;
            }
        });
    }
}