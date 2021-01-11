package com.etatech.test.view.practice4;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.etatech.test.R;

public class Practice10MatrixSkewView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;
    Point point1 = new Point(200, 200);
    Point point2 = new Point(600, 200);

    public Practice10MatrixSkewView(Context context) {
        super(context);
    }

    public Practice10MatrixSkewView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice10MatrixSkewView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.maps);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Matrix matrix = new Matrix();

        matrix.reset();
        matrix.postSkew(0, 0.5f, point1.x + bitmap.getWidth() / 2, point1.y + bitmap.getHeight() / 2);

        canvas.save();
        canvas.concat(matrix);
        canvas.drawBitmap(bitmap, point1.x , point1.y, paint);
        canvas.restore();

        matrix.reset();
        matrix.postSkew(-0.5f, 0, point2.x + bitmap.getWidth() / 2, point2.y + bitmap.getHeight() / 2);

        canvas.save();
        canvas.concat(matrix);
        canvas.drawBitmap(bitmap, point2.x, point2.y, paint);
        canvas.restore();

        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(point1.x, point1.y, point1.x + bitmap.getWidth(), point1.y + bitmap.getHeight(), paint);
        canvas.drawRect(point2.x, point2.y, point2.x + bitmap.getWidth(), point2.y + bitmap.getHeight(), paint);
    }
}
