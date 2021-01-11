package com.etatech.test.view.practice4;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Region;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.etatech.test.R;

public class Practice02ClipPathView extends View {
    Paint paint = new Paint();
    Bitmap bitmap;
    Point point1 = new Point(200, 200);
    Point point2 = new Point(600, 200);

    public Practice02ClipPathView(Context context) {
        super(context);
    }

    public Practice02ClipPathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice02ClipPathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.maps);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path path = new Path();
        path.addCircle(point1.x + bitmap.getWidth() / 2, point1.y + bitmap.getHeight() / 2, bitmap.getWidth() / 2 - 50, Path.Direction.CW);
        canvas.save();
        canvas.clipPath(path, Region.Op.INTERSECT);
        canvas.drawBitmap(bitmap, point1.x, point1.y, paint);
        canvas.restore();

        path = new Path();
        path.addCircle(point2.x + bitmap.getWidth() / 2, point2.y + bitmap.getHeight() / 2, bitmap.getWidth() / 2 - 50, Path.Direction.CW);
        canvas.save();
        canvas.clipPath(path, Region.Op.DIFFERENCE);
        canvas.drawBitmap(bitmap, point2.x, point2.y, paint);
        canvas.restore();
    }
}
