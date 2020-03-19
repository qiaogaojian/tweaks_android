package com.sdbean.splashad;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.TextureView;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by Michael
 * Date:  2020/2/24
 * Func:
 */
public class MyVideoView extends TextureView implements TextureView.SurfaceTextureListener {
    private MediaPlayer                      mMediaPlayer;
    private Uri                              mSource;
    private MediaPlayer.OnCompletionListener mCompletionListener;
    private OnStartListener                  onStartListener;
    private boolean                          isLooping    = false;
    private boolean                          isMute       = true;
    private int                              mRatioWidth  = 1080;
    private int                              mRatioHeight = 1920;
    public  DisplayMetrics                   mMetrics     = new DisplayMetrics();

    private Matrix defTransform        = null;
    private Matrix fullScreenTransform = new Matrix();

    public MyVideoView(Context context) {
        this(context, null, 0);
    }

    public MyVideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setSurfaceTextureListener(this);
    }

    // 视频剪裁
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = getScreenWidth();
        int h = getScreenHeight();

        setMeasuredDimension(w, h);
        fullScreenTransform.reset();
        fullScreenTransform.set(defTransform);
        fullScreenTransform.postScale((float) h / mRatioHeight, 1f, w * 0.5f, h * 0.5f); // 宽拉伸，高不变
        setTransform(fullScreenTransform);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int width = MeasureSpec.getSize(widthMeasureSpec);
//        int height = MeasureSpec.getSize(heightMeasureSpec);
//        if (0 == mRatioWidth || 0 == mRatioHeight) {
//            setMeasuredDimension(width, height);
//        } else {
//            WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
//            windowManager.getDefaultDisplay().getMetrics(mMetrics);
//            double ratio = (double) mRatioWidth / (double) mRatioHeight;
//            double invertedRatio = (double) mRatioHeight / (double) mRatioWidth;
//            double portraitHeight = width * invertedRatio;
//            double portraitWidth = width * (mMetrics.heightPixels / portraitHeight);
//            double landscapeWidth = height * ratio;
//            double landscapeHeight = (mMetrics.widthPixels / landscapeWidth) * height;
//            if (width > height * mRatioWidth / mRatioHeight) {
//                setMeasuredDimension((int) portraitWidth, mMetrics.heightPixels);
//            } else {
//                setMeasuredDimension(mMetrics.widthPixels, (int) landscapeHeight);
//            }
//            setTransform(defTransform);
//        }
//    }

    @Override
    public void setTransform(Matrix transform) {
        if (defTransform == null) {
            defTransform = transform;
        }
        super.setTransform(transform);
    }

    public void setVideoPath(Uri source) {
        mSource = source;
    }

    public void setOnCompletionListener(MediaPlayer.OnCompletionListener listener) {
        mCompletionListener = listener;
    }

    public void setOnStartListener(OnStartListener listener) {
        onStartListener = listener;
    }

    public void setLooping(boolean looping) {
        isLooping = looping;
    }

    public void muteSound(boolean isMute) {
        this.isMute = isMute;
    }

    @Override
    protected void onDetachedFromWindow() {
        // release resources on detach
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        super.onDetachedFromWindow();
    }

    /*
     * TextureView.SurfaceTextureListener
     */
    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
        Surface surface = new Surface(surfaceTexture);
        try {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setOnCompletionListener(mCompletionListener);
            mMediaPlayer.setLooping(isLooping);
            if (isMute) {
                mMediaPlayer.setVolume(0, 0);
            } else {
                mMediaPlayer.setVolume(1, 1);
            }
            if (onStartListener != null) {
                onStartListener.onStart();
            }
            mMediaPlayer.setDataSource(getContext(), mSource);
            mMediaPlayer.setSurface(surface);
            mMediaPlayer.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
            mMediaPlayer.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        surface.setDefaultBufferSize(width, height);
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        surface.release();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
    }

    public interface OnStartListener {
        void onStart();
    }

    public int getScreenHeight() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) return -1;
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.y;
    }

    public int getScreenWidth() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) return -1;
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.x;
    }
}