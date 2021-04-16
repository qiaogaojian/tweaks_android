package com.mega.imageloader.transform;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.util.Arrays;

public class RoundedCornersTransformation implements Transformation<Bitmap> {
    private BitmapPool mBitmapPool;
    private float radius;
    private boolean exceptLeftTop;
    private boolean exceptRightTop;
    private boolean exceptLeftBottom;
    private boolean exceptRightBotoom;

    private final String ID = "com. bumptech.glide.transformations.FillSpace";
    private final byte[] ID_ByTES= ID.getBytes(CHARSET);

    public void setExceptCorner(boolean leftTop, boolean rightTop, boolean leftBottom, boolean rightBottom) {
        this.exceptLeftTop = leftTop;
        this.exceptRightTop = rightTop;
        this.exceptLeftBottom = leftBottom;
        this.exceptRightBotoom = rightBottom;
    }

    public RoundedCornersTransformation(Context context, float radius) {
        this.mBitmapPool = Glide.get(context).getBitmapPool();
        this.radius = radius;
    }

    @NonNull
    public Resource<Bitmap> transform(@NonNull Context context, @NonNull Resource<Bitmap> resource, int outWidth, int outHeight) {
        Bitmap source = (Bitmap)resource.get();
        int finalWidth;
        int finalHeight;
        float ratio;
        if (outWidth > outHeight) {
            ratio = (float)outHeight / (float)outWidth;
            finalWidth = source.getWidth();
            finalHeight = (int)((float)source.getWidth() * ratio);
            if (finalHeight > source.getHeight()) {
                ratio = (float)outWidth / (float)outHeight;
                finalHeight = source.getHeight();
                finalWidth = (int)((float)source.getHeight() * ratio);
            }
        } else if (outWidth < outHeight) {
            ratio = (float)outWidth / (float)outHeight;
            finalHeight = source.getHeight();
            finalWidth = (int)((float)source.getHeight() * ratio);
            if (finalWidth > source.getWidth()) {
                ratio = (float)outHeight / (float)outWidth;
                finalWidth = source.getWidth();
                finalHeight = (int)((float)source.getWidth() * ratio);
            }
        } else {
            finalHeight = source.getHeight();
            finalWidth = finalHeight;
        }

        this.radius *= (float)finalHeight / (float)outHeight;
        Bitmap outBitmap = this.mBitmapPool.get(finalWidth, finalHeight, Bitmap.Config.ARGB_8888);
        if (outBitmap == null) {
            outBitmap = Bitmap.createBitmap(finalWidth, finalHeight, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(outBitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        int width = (source.getWidth() - finalWidth) / 2;
        int height = (source.getHeight() - finalHeight) / 2;
        if (width != 0 || height != 0) {
            Matrix matrix = new Matrix();
            matrix.setTranslate((float)(-width), (float)(-height));
            shader.setLocalMatrix(matrix);
        }

        paint.setShader(shader);
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0.0F, 0.0F, (float)canvas.getWidth(), (float)canvas.getHeight());
        canvas.drawRoundRect(rectF, this.radius, this.radius, paint);
        if (this.exceptLeftTop) {
            canvas.drawRect(0.0F, 0.0F, this.radius, this.radius, paint);
        }

        if (this.exceptRightTop) {
            canvas.drawRect((float)canvas.getWidth() - this.radius, 0.0F, this.radius, this.radius, paint);
        }

        if (this.exceptLeftBottom) {
            canvas.drawRect(0.0F, (float)canvas.getHeight() - this.radius, this.radius, (float)canvas.getHeight(), paint);
        }

        if (this.exceptRightBotoom) {
            canvas.drawRect((float)canvas.getWidth() - this.radius, (float)canvas.getHeight() - this.radius, (float)canvas.getWidth(), (float)canvas.getHeight(), paint);
        }

        return BitmapResource.obtain(outBitmap, this.mBitmapPool);
    }

    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(ID_ByTES);
        byte[] radiusData = ByteBuffer.allocate(4).putInt((int) radius).array();messageDigest.update(radiusData);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoundedCornersTransformation that = (RoundedCornersTransformation) o;

        if (ID != null ? !ID.equals(that.ID) : that.ID != null) return false;
        return Arrays.equals(ID_ByTES, that.ID_ByTES);
    }

    @Override
    public int hashCode() {
        int result = ID != null ? ID.hashCode() : 0;
        result = 31 * result + Arrays.hashCode(ID_ByTES);
        return result;
    }

    public String getId() {
        return this.getClass().getName();
    }
}
