package com.example.bajiesleep;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by zhxh on 2018/4/28.
 */

public class TimeRangePickerDialog extends Dialog {
    private Context context;

    private static final int TIME_PICKER_INTERVAL=15;
    private boolean mIgnoreEvent=false;
    NumberPicker minuteNumberPickerStart;
    NumberPicker minuteNumberPickerEnd;

    private String startTime;
    private String endTime;
    private String startHour;
    private String startMinute;
    private String endHour;
    private String endMinute;
    private int screenWidth;

    private int h1;
    private int h2;
    private int m1,m11;
    private int m2,m22;
    int rangeTime;
    private TimePicker timePickerStart;
    private TimePicker timePickerEnd;


    private View cancelBtn, submitBtn;

    private ConfirmAction confirmAction;

    public TimeRangePickerDialog(Context context) {

        super(context);

        this.context = context;
    }

    public TimeRangePickerDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);

        this.context = context;
    }

    public TimeRangePickerDialog(Context context, int theme) {

        super(context, theme);

        this.context = context;
    }

    public TimeRangePickerDialog(Context context, String startAndEndTime, ConfirmAction confirmAction) {
        super(context, R.style.dialog);

        this.context = context;

        List<String> strings = CommonUtils.getRegEx(startAndEndTime, "\\d+:\\d+");
        if (!CommonUtils.isNull(strings) && strings.size() >= 2) {
            this.startTime = CommonUtils.getRegEx(startAndEndTime, "\\d+:\\d+").get(0);
            this.endTime = CommonUtils.getRegEx(startAndEndTime, "\\d+:\\d+").get(1);
        }

        this.confirmAction = confirmAction;

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        screenWidth = metrics.widthPixels - getDensityValue(80, context);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_time_range_picker, null);
        setContentView(view);
        getWindow().setLayout(screenWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
        String[] number = new String[]{"00","15","30","45"};
        timePickerStart = (TimePicker) findViewById(R.id.timePickerStart);
        timePickerEnd = (TimePicker) findViewById(R.id.timePickerEnd);

        /**
         * 通过反射获取TimePicker源码里hour和minute的id
         */
        Resources systemResources = Resources.getSystem();
        int minuteNumberPickerStartId = systemResources.getIdentifier("minute", "id", "android");
        minuteNumberPickerStart = (NumberPicker) timePickerStart.findViewById(minuteNumberPickerStartId);

        /**
         * 通过获取到的minuteNumberPicker我们可以先进行TimePicker的时间限制
         * 首先在前面第一行设置setDisplayedValues(null) 在设置最大值和最新数组数据前，先将数据设为null可以避免数组越界
         */
        minuteNumberPickerStart.setDisplayedValues(null);
        minuteNumberPickerStart.setMinValue(0);
        minuteNumberPickerStart.setMaxValue(number.length-1);
        minuteNumberPickerStart.setValue(0);
        minuteNumberPickerStart.setDisplayedValues(number);

        int minuteNumberPickerEndId = systemResources.getIdentifier("minute", "id", "android");
        minuteNumberPickerEnd = (NumberPicker) timePickerEnd.findViewById(minuteNumberPickerEndId);

        minuteNumberPickerEnd.setDisplayedValues(null);
        minuteNumberPickerEnd.setMinValue(0);
        minuteNumberPickerEnd.setMaxValue(number.length-1);
        minuteNumberPickerEnd.setValue(0);
        minuteNumberPickerEnd.setDisplayedValues(number);

        initView();
        initData();
        setEvent();

    }

    private void initView() {


        cancelBtn = findViewById(R.id.cancelBtn);
        submitBtn = findViewById(R.id.submitBtn);



    }


    private void initData() {

        timePickerStart.setIs24HourView(true);
        timePickerEnd.setIs24HourView(true);

        timePickerStart.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);
        timePickerEnd.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);

        setTimePickerDividerColor(timePickerStart);
        setTimePickerDividerColor(timePickerEnd);

        timePickerStart.setCurrentHour(21);
        timePickerStart.setCurrentMinute(0);
        timePickerEnd.setCurrentHour(6);
        timePickerEnd.setCurrentMinute(0);



        if (!CommonUtils.isNull(startTime) && !CommonUtils.isNull(endTime)) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                timePickerStart.setHour(Integer.parseInt(startTime.substring(0, startTime.indexOf(":"))));
//                timePickerStart.setHour(21);
                timePickerStart.setMinute(Integer.parseInt(startTime.substring(startTime.indexOf(":") + 1)));

                timePickerEnd.setHour(Integer.parseInt(endTime.substring(0, endTime.indexOf(":"))));
                timePickerEnd.setMinute(Integer.parseInt(endTime.substring(endTime.indexOf(":") + 1)));
            } else {
                timePickerStart.setCurrentHour(Integer.parseInt(startTime.substring(0, startTime.indexOf(":"))));
                timePickerStart.setCurrentMinute(Integer.parseInt(startTime.substring(startTime.indexOf(":") + 1)));
//
//                timePickerStart.setCurrentHour(15);
//                timePickerStart.setCurrentMinute(0);

                timePickerEnd.setCurrentHour(Integer.parseInt(endTime.substring(0, endTime.indexOf(":"))));
                timePickerEnd.setCurrentMinute(Integer.parseInt(endTime.substring(endTime.indexOf(":") + 1)));
            }

        }

        timePickerStart.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hourOfDay, int minute) {
//                String h = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
//                String m = minute < 10 ? "0" + minute : "" + minute;

                String h = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
                String m = minute < 10 ? "0" + minute : "" + minute;

                startTime = h + ":" + m;
            }
        });
        timePickerEnd.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hourOfDay, int minute) {
                String h = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
                String m = minute < 10 ? "0" + minute : "" + minute;

                endTime = h + ":" + m;

            }
        });
    }



    private void setEvent() {

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmAction.onLeftClick();
                dismiss();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                h1 = timePickerStart.getCurrentHour();
                m1 = timePickerStart.getCurrentMinute();

                if (m1 == 0){
                    m11 = 0;
                }else if (m1 == 1){
                    m11 = 15;
                }else if (m1 == 2){
                    m11 = 30;
                }else if (m1 == 3){
                    m11 = 45;
                }

                Log.d("asdhasd", String.valueOf(m11));

                startHour = h1 < 10 ? "0" + h1 : "" + h1;
                startMinute = m11 < 10 ? "0" + m11 : "" + m11;

                h2 = timePickerEnd.getCurrentHour();
                m2 = timePickerEnd.getCurrentMinute();

                if (m2 == 0){
                    m22 = 0;
                }else if (m2 == 1){
                    m22 = 15;
                }else if (m2 == 2){
                    m22 = 30;
                }else if (m2 == 3){
                    m22 = 45;
                }

                endHour = h2 < 10 ? "0" + h2 : "" + h2;
                endMinute = m22 < 10 ? "0" + m22 : "" + m22;

                if(timePickerStart.getCurrentHour() == 0){
                    h1 =24;
                    startHour = "00";


                }

                if(timePickerEnd.getCurrentHour() == 0){
                    h2 =24;
                    endHour = "00";

                }

                Log.d("starTime", h1+":"+m11);
                Log.d("endTime", h2+":"+m22);

//                if (h1 <  h2){
//                    rangeTime = (h2-h1)*60+(60-m1)+m2;
//                }else if (h1>h2){
//                    rangeTime = ((24-h1)+h2)*60 + (60-m1)+m2;
//                }else if (h1 == h2){
//                    if (m1<m2){
//                        rangeTime = m2-m1;
//                    }else if (m1 > m2){
//                        rangeTime = ((24-h1)+h2)*60 + (60-m1)+m2;
//                    }else if (m1 == m2){
//                        rangeTime = m2 -m1;
//                    }
//                }

                if (h1>h2){
                    if (m11 < m22){
                        rangeTime = (h2+24-h1)*60 + (m22-m11);
                    }else {
                        rangeTime = (h2+24-h1)*60 - (m11-m22);
                    }
                }else {
                    if (m11 < m22){
                        rangeTime = (h2-h1)*60+(m22-m11);
                    }else {
                        rangeTime = (h2-h1)*60-(m11-m22);
                    }
                }



                if (rangeTime < 721){

                    if (rangeTime < 241){
                        ToastUtils.showTextToast(getContext(),"监测时间不可低于4小时");
                    }else {
                        confirmAction.onRightClick(startHour,startMinute,endHour,endMinute);
                        dismiss();
                    }

                }else {
                    ToastUtils.showTextToast(getContext(),"监测时间不可超过12小时");
                }

                Log.d("rangeTime", String.valueOf(rangeTime));

            }
        });

        this.setCanceledOnTouchOutside(true);

    }

    private void setTimePickerDividerColor(TimePicker timePicker) {
        LinearLayout llFirst = (LinearLayout) timePicker.getChildAt(0);
        LinearLayout mSpinners = (LinearLayout) llFirst.getChildAt(1);
        for (int i = 0; i < mSpinners.getChildCount(); i++) {
            if (mSpinners.getChildAt(i) instanceof NumberPicker) {
                Field[] pickerFields = NumberPicker.class.getDeclaredFields();
                setPickerMargin((NumberPicker) mSpinners.getChildAt(i));
                for (Field pf : pickerFields) {
                    if (pf.getName().equals("mSelectionDivider")) {
                        pf.setAccessible(true);
                        try {
                            pf.set(mSpinners.getChildAt(i), new ColorDrawable());
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        } catch (Resources.NotFoundException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }
        }
    }

    /**
     * 设置picker之间的间距
     */
    private void setPickerMargin(NumberPicker picker) {
        LinearLayout.LayoutParams p = (LinearLayout.LayoutParams) picker.getLayoutParams();
        p.setMargins(-getDensityValue(16, context), 0, -getDensityValue(16, context), 0);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1) {
            p.setMarginStart(-getDensityValue(16, context));
            p.setMarginEnd(-getDensityValue(16, context));
        }
    }


    public interface ConfirmAction {

        void onLeftClick();

        void onRightClick(String startHour,String starMinute,String endHour,String endMinute);
    }

    public static int getDensityValue(float value, Context activity) {

        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();

        return (int) Math.ceil(value * displayMetrics.density);
    }
}
