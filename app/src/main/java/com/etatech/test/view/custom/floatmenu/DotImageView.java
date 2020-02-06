package com.etatech.test.view.custom.floatmenu;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.etatech.test.R;

/**
 * Created by Michael
 * Date:  2020/2/3
 * Func:
 */
public class DotImageView extends View {
    private static final String TAG        = DotImageView.class.getSimpleName();
    public static final  int    NORMAL     = 0;//不隐藏
    public static final  int    HIDE_LEFT  = 1;//左边隐藏
    public static final  int    HIDE_RIGHT = 2;//右边隐藏
    private              Paint  mPaint;//用于画anything

    private Paint   mPaintBg;//用于画anything
    private String  dotNum       = null;//红点数字
    private float   mAlphValue;//透明度动画值
    private float   mRolateValue = 1f;//旋转动画值
    private boolean inited       = false;//标记透明动画是否执行过，防止因onreseme 切换导致重复执行


    private       Bitmap mBitmap;//logo
    private final int    mLogoBackgroundRadius  = dip2px(30);//logo的灰色背景圆的半径
    private final int    mRedPointRadiusWithNum = dip2px(6);//红点圆半径
    private final int    mRedPointRadius        = dip2px(3);//红点圆半径
    private final int    mRedPointOffset        = dip2px(10);//红点对logo的偏移量，比如左红点就是logo中心的 x - mRedPointOffset

    private int mItemWidth  = dip2px(60);//菜单Item的宽度
    private int mItemHeight = dip2px(60);//菜单Item的高度

    private boolean            isDraging           = false;//是否 绘制旋转放大动画，只有 非停靠边缘才绘制
    private ValueAnimator      mDragingValueAnimator;//放大、旋转 属性动画
    private LinearInterpolator mLinearInterpolator = new LinearInterpolator();//通用用加速器
    public  boolean            mDrawDarkBg         = true;//是否绘制黑色背景，当菜单关闭时，才绘制灰色背景
    private Camera             mCamera;//camera用于执行3D动画

    private int     mStatus     = NORMAL;//0 正常，1 左，2右,3 中间方法旋转
    private int     mLastStatus = mStatus;
    private Matrix  mMatrix;
    private boolean mIsResetPosition;

    private int mBgColor = 0x99000000;

    private Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.floating_icon1);
    private Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.floating_close);

    public void setBgColor(int bgColor) {
        mBgColor = bgColor;
    }

    public void setDrawDarkBg(boolean drawDarkBg) {
        mDrawDarkBg = drawDarkBg;
        if (drawDarkBg)
        {
            setBitmap(bitmap1);
        } else
        {
            setBitmap(bitmap2);
        }
        invalidate();
    }

    public int getStatus() {
        return mStatus;
    }


    public void setStatus(int status) {
        this.mStatus = status;
        isDraging = false;
        if (this.mStatus != NORMAL)
        {
            this.mDrawDarkBg = true;
        }
        invalidate();

    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
    }

    public DotImageView(Context context, Bitmap bitmap) {
        super(context);
        this.mBitmap = bitmap;
        init();
    }


    public DotImageView(Context context) {
        super(context);
        init();
    }

    public DotImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DotImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(sp2px(10));
        mPaint.setStyle(Paint.Style.FILL);

        mPaintBg = new Paint();
        mPaintBg.setAntiAlias(true);
        mPaintBg.setStyle(Paint.Style.FILL);
        mPaintBg.setColor(mBgColor);//60% 黑色背景 （透明度 40%）

        mCamera = new Camera();
        mMatrix = new Matrix();
    }

    /**
     * 这个方法是否有优化空间
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wh = mLogoBackgroundRadius * 2;
        setMeasuredDimension(wh, wh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float centryX = getWidth() / 2;
        float centryY = getHeight() / 2;
        mCamera.save();
        canvas.save();//保存一份快照，方便后面恢复
//        if (mStatus == NORMAL) {
//            if (mLastStatus != NORMAL) {
//                canvas.restore();//恢复画布的原始快照
//                mCamera.restore();
//            }
//
//            if (isDraging) {
//                //如果当前是拖动状态则放大并旋转
//                canvas.scale((scaleOffset + 1f), (scaleOffset + 1f), getWidth() / 2, getHeight() / 2);
//                if (mIsResetPosition) {
//                    //手指拖动后离开屏幕复位时使用 x轴旋转 3d动画
//                    mCamera.save();
//                    mCamera.rotateX(720 * scaleOffset);//0-720度 最多转两圈
//                    mCamera.getMatrix(mMatrix);
//
//                    mMatrix.preTranslate(-getWidth() / 2, -getHeight() / 2);
//                    mMatrix.postTranslate(getWidth() / 2, getHeight() / 2);
//                    canvas.concat(mMatrix);
//                    mCamera.restore();
//                } else {
//                    //手指拖动且手指未离开屏幕则使用 绕图心2d旋转动画
//                    canvas.rotate(60 * mRolateValue, getWidth() / 2, getHeight() / 2);
//                }
//            }
//
//
//        } else if (mStatus == HIDE_LEFT) {
//            canvas.translate(-getWidth() * hideOffset, 0);
//            canvas.rotate(-45, getWidth() / 2, getHeight() / 2);
//
//        } else if (mStatus == HIDE_RIGHT) {
//            canvas.translate(getWidth() * hideOffset, 0);
//            canvas.rotate(45, getWidth() / 2, getHeight() / 2);
//        }
//        canvas.save();
//        if (!isDraging) {
//            if (mDrawDarkBg) {
//                mPaintBg.setColor(mBgColor);
//                canvas.drawCircle(centryX, centryY, mLogoBackgroundRadius, mPaintBg);
//                // 60% 白色 （透明度 40%）
//                mPaint.setColor(0x99ffffff);
//            } else {
//                //100% 白色背景 （透明度 0%）
//                mPaint.setColor(0xFFFFFFFF);
//            }
//            if (mAlphValue != 0) {
//                mPaint.setAlpha((int) (mAlphValue * 255));
//            }
//            canvas.drawCircle(centryX, centryY, mLogoWhiteRadius, mPaint);
//        }
//        canvas.restore();

        if (mStatus == NORMAL)
        {
            if (mLastStatus != NORMAL)
            {
                canvas.restore();//恢复画布的原始快照
                mCamera.restore();
            }
        }

        if (mDrawDarkBg)
        {
            mPaintBg.setColor(mBgColor);
            canvas.drawCircle(centryX, centryY, mLogoBackgroundRadius, mPaintBg);
        }

        float left  = centryX - mItemWidth * 0.66f / 2;//计算icon的左坐标值 中心点往左移宽度的四分之一
        float right = centryX + mItemWidth * 0.66f / 2;

        float iconH = mItemHeight * 0.66f;//计算出icon的宽度 = icon的高度

        float paddH = (mItemHeight - iconH) / 2;//总高度减去文字的高度，减去icon高度，再除以2就是上下的间距剩余

        float top    = centryY - mItemHeight / 2 + paddH;//计算icon的上坐标值
        float bottom = top + iconH;//剩下的高度空间用于画文字

        //画icon
        mPaint.setColor(0xFFFFFFFF);
        canvas.drawBitmap(mBitmap, null, new RectF(left, top, right, bottom), mPaint);

        mLastStatus = mStatus;
    }

    private int dip2px(float dipValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    private int sp2px(float spValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
