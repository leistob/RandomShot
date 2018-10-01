package tob.leis.randomshot;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class Roadmap extends View {

    public static final String TAG = "Roadmap";

    private int PRIMARY_COLOR_DARK, PRIMARY_COLOR, ACCENT_COLOR;

    private int mWidth;
    private int mHeight;
    private Paint mPaint;

    private int mRadius = 0, mLength;

    public Roadmap(Context context) {
        super(context);
        mPaint = new Paint();
        mPaint.setColor(0xFFFF0000);
        PRIMARY_COLOR_DARK = ContextCompat.getColor(this.getContext(), R.color.colorPrimaryDark);
        PRIMARY_COLOR = ContextCompat.getColor(this.getContext(), R.color.colorPrimary);
        ACCENT_COLOR = ContextCompat.getColor(this.getContext(), R.color.colorAccent);
    }

    public Roadmap(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setColor(0xFFFF0000);
        PRIMARY_COLOR_DARK = ContextCompat.getColor(this.getContext(), R.color.colorPrimaryDark);
        PRIMARY_COLOR = ContextCompat.getColor(this.getContext(), R.color.colorPrimary);
        ACCENT_COLOR = ContextCompat.getColor(this.getContext(), R.color.colorAccent);
    }

    public Roadmap(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setColor(0xFFFF0000);
        PRIMARY_COLOR_DARK = ContextCompat.getColor(this.getContext(), R.color.colorPrimaryDark);
        PRIMARY_COLOR = ContextCompat.getColor(this.getContext(), R.color.colorPrimary);
        ACCENT_COLOR = ContextCompat.getColor(this.getContext(), R.color.colorAccent);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Roadmap(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mPaint = new Paint();
        mPaint.setColor(0xFFFF0000);
        PRIMARY_COLOR_DARK = ContextCompat.getColor(this.getContext(), R.color.colorPrimaryDark);
        PRIMARY_COLOR = ContextCompat.getColor(this.getContext(), R.color.colorPrimary);
        ACCENT_COLOR = ContextCompat.getColor(this.getContext(), R.color.colorAccent);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setAntiAlias(true);


        final RectF oval = new RectF();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(6);
        mPaint.setColor(PRIMARY_COLOR_DARK);

        //Draw frame
        canvas.drawRoundRect(3, 3, mWidth-3, mHeight-3, 20, 20, mPaint);

        mPaint.setColor(PRIMARY_COLOR);
        //Draw left semicircle
        oval.set((mWidth/2)-(mRadius+(mLength/2)), (mHeight/2)-(mRadius), (mWidth/2)-(mLength/2)+mRadius, (mHeight/2)+(mRadius));
        canvas.drawArc(oval, 90, 180, true, mPaint);

        //Draw right semicircle
        oval.set((mWidth/2)+(mLength/2)-mRadius, (mHeight/2)-(mRadius), (mWidth/2)+(mLength/2)+mRadius, (mHeight/2)+(mRadius));
        canvas.drawArc(oval, 270, 180, true, mPaint);

        //Draw middle rect
        canvas.drawRect((mWidth/2)-mLength/2, (mHeight/2)-mRadius, (mWidth/2)+mLength/2, (mHeight/2)+mRadius, mPaint);

        //Draw start point
        //canvas.drawLine(mWidth/2, (mHeight/2)+mRadius-16, mWidth/2, (mHeight/2)+mRadius+16, mPaint);
        mPaint.setColor(ACCENT_COLOR);
        canvas.drawCircle(mWidth/2, (mHeight/2)+mRadius, 10, mPaint);
    }

    public void setmRadius(int radiusPercent, int lengthPercent) {
        calculateRad(radiusPercent);
        calculateLength(lengthPercent);
    }

    public void setmLength(int lengthPercent) {
        calculateLength(lengthPercent);
    }

    private void calculateRad(int radiusPercent) {
        this.mRadius = (int) ((radiusPercent/100.0)*((mHeight/2.0)-20));
        Log.i(TAG, "Rad: " + radiusPercent/100.0);
    }

    private void calculateLength(int lengthPercent) {
        int maxLength = mWidth - 2*mRadius - 20;
        this.mLength = (int) ((lengthPercent/100.0)*(maxLength-20));
    }
}
