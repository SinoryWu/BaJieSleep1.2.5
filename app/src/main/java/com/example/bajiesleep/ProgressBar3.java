package com.example.bajiesleep;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;

public class ProgressBar3 extends View {



    public int getRoundWidth(){
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        float density = dm.density;
        int roundWidth = (int) (10*density);
        return roundWidth;
    }



    public int getHeight1(){
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        float density = dm.density;
        int height1 = (int) (16*density);
        return height1;
    }

    private int mInnerBackground = Color.RED;
    private int mOuterBackground = Color.RED;
    private int mRoundwidth = getRoundWidth();//10px


    private Paint mInnerPaint,mOuterPaint, mTextPaint1,mTextPaint2,mTextPaint3;
    public int mMax  ;
    public  int mProgress;



    public ProgressBar3(Context context) {
        this(context,null);

    }





    public ProgressBar3(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }



    public ProgressBar3(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        //获取自定义属性
        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.ProgressBar);
        mInnerBackground = array.getColor(R.styleable.ProgressBar_innerBackground,mInnerBackground);
        mOuterBackground = array.getColor(R.styleable.ProgressBar_outerBackground,mOuterBackground);
        mRoundwidth = (int) array.getDimension(R.styleable.ProgressBar_roundwidth,dip2px(10));

        array.recycle();

        mInnerPaint = new Paint();
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setColor(mInnerBackground);
        mInnerPaint.setStrokeWidth(mRoundwidth);
        mInnerPaint.setStyle(Paint.Style.STROKE);


        mOuterPaint = new Paint();
        mOuterPaint.setAntiAlias(true);
        mOuterPaint.setColor(mOuterBackground);
        mOuterPaint.setStrokeWidth(mRoundwidth);
        mOuterPaint.setStyle(Paint.Style.STROKE);
        mOuterPaint.setStrokeCap(Paint.Cap.ROUND);


    }

    private int sp2px(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,getResources().getDisplayMetrics());
    }

    private float dip2px(int dip) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dip,getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //只保证是正方形
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(Math.min(width,height),Math.min(width,height));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        float percent = (float) mProgress/mMax;
        //先画内圆
        int center = getWidth()/2;
        canvas.drawCircle(center,center,center-mRoundwidth/2,mInnerPaint);
        Log.d("mMax", String.valueOf(mMax));
        Log.d("mProgress", String.valueOf(mProgress));

        //画外圆
        RectF rect = new RectF(0+mRoundwidth/2,0+mRoundwidth/2,
                getWidth()-mRoundwidth/2,getHeight()-mRoundwidth/2);

        if (mProgress == 0){
            return;
        }


        canvas.drawArc(rect,270,percent*360,false,mOuterPaint);
        //画进度文字



    }

    public synchronized void setMax(int max){
        if (max < 0 ){

        }
        this.mMax = max;
    }

    public synchronized void setProgress(int progress){
        if (progress < 0 ){

        }
        this.mProgress = progress;
        //刷新 invalidate
        invalidate();
    }








}
