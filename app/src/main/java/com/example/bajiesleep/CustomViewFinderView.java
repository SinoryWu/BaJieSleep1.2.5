package com.example.bajiesleep;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.CameraPreview;
import com.journeyapps.barcodescanner.ViewfinderView;

import java.util.ArrayList;
import java.util.List;

public class CustomViewFinderView extends ViewfinderView {
    /**
     * 自定义zxing二维码扫描界面
     * Created by IBM on 2016/10/20.
     */


    public int laserLinePosition = 0;
    public float[] position = new float[]{0f, 1f};
    //    public int[] colors=new int[]{0x00ffffff,0xffffffff,0x00ffffff};
    public int[] colors = new int[]{Color.parseColor("#00000000"), Color.parseColor("#FFC300")};
    public LinearGradient linearGradient;

    public CustomViewFinderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public int getWidth1() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        float density = dm.density;         // 屏幕密度
        int buttonHeight = (int) (20 * density);
        return buttonHeight;
    }


    /**
     * 重写draw方法绘制自己的扫描框
     *
     * @param canvas
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onDraw(Canvas canvas) {
        refreshSizes();
        if (framingRect == null || previewFramingRect == null) {
            return;
        }

        Rect frame = framingRect;
        Rect previewFrame = previewFramingRect;

        int width = canvas.getWidth();
        int height = canvas.getHeight();
        // Draw the exterior (i.e. outside the framing rect) darkened
        paint.setColor(resultBitmap != null ? resultColor : maskColor);
        canvas.drawRect(0, 0, width, frame.top, paint);
        canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
        canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1, paint);
        canvas.drawRect(0, frame.bottom + 1, width, height, paint);
        //
        if (resultBitmap != null) {
            // Draw the opaque result bitmap over the scanning rectangle
            paint.setAlpha(CURRENT_POINT_OPACITY);
            canvas.drawBitmap(resultBitmap, null, frame, paint);
        } else {
            //  paint.setAlpha(SCANNER_ALPHA[scannerAlpha]);
            //  scannerAlpha = (scannerAlpha + 1) % SCANNER_ALPHA.length;
            int middle = frame.height() / 2 + frame.top;
            laserLinePosition = laserLinePosition + 5;
            if (laserLinePosition > frame.height()) {
                laserLinePosition = 0;
            }
            linearGradient = new LinearGradient(frame.left, frame.top, frame.left, frame.top + 10 + laserLinePosition, colors, position, Shader.TileMode.CLAMP);
            paint.setShader(linearGradient);
            //绘制扫描线
//            canvas.drawRect(frame.left + 1, frame.top, frame.right - 1, frame.top + 10 + laserLinePosition, paint);
            paint.setShader(null);
            float scaleX = frame.width() / (float) previewFrame.width();
            float scaleY = frame.height() / (float) previewFrame.height();

            int frameLeft = frame.left;
            int frameTop = frame.top;

            //绘制4个角
//            paint.setColor(Color.parseColor("#FFC300"));
//            paint.setStyle(Paint.Style.FILL);
            //        paint.setColor(0xFFFFFFFF);//定义画笔的颜色
            int gap = 5;
            int borderWidth = 5;
            int borderHeight = 65;
            Path ltPath1=new Path();
            Path ltPath2=new Path();
            ltPath1.addRect(frame.left,frame.top,frame.left+getWidth1(),frame.top+getWidth1(), Path.Direction.CCW);
            ltPath2.addCircle(frame.left+getWidth1(),frame.top+getWidth1(),getWidth1(), Path.Direction.CCW);
            ltPath1.op(ltPath2, Path.Op.DIFFERENCE);
            canvas.drawPath(ltPath1,paint);
            //右上角
            Path rtPath1=new Path();
            Path rtPath2=new Path();
            rtPath1.addRect(frame.right-getWidth1(),frame.top,frame.right+1,frame.top+getWidth1(), Path.Direction.CCW);
            rtPath2.addCircle(frame.right-getWidth1(),frame.top+getWidth1(),getWidth1(), Path.Direction.CCW);
            rtPath1.op(rtPath2, Path.Op.DIFFERENCE);
            canvas.drawPath(rtPath1,paint);
            //左下角
            Path lbPath1=new Path();
            Path lbPath2=new Path();
            lbPath1.addRect(frame.left,frame.bottom-getWidth1(),frame.left+getWidth1(),frame.bottom+1, Path.Direction.CCW);
            lbPath2.addCircle(frame.left+getWidth1(),frame.bottom-getWidth1(),getWidth1(), Path.Direction.CCW);
            lbPath1.op(lbPath2, Path.Op.DIFFERENCE);
            canvas.drawPath(lbPath1,paint);
            //右下角
            Path rbPath1=new Path();
            Path rbPath2=new Path();
            rbPath1.addRect(frame.right-getWidth1(),frame.bottom-getWidth1(),frame.right+1,frame.bottom+1, Path.Direction.CCW);
            rbPath2.addCircle(frame.right-getWidth1(),frame.bottom-getWidth1(),getWidth1(), Path.Direction.CCW);
            rbPath1.op(rbPath2, Path.Op.DIFFERENCE);
            canvas.drawPath(rbPath1,paint);

            postInvalidateDelayed(16,
                    frame.left,
                    frame.top,
                    frame.right,
                    frame.bottom);
            // postInvalidate();

        }




    }

}


