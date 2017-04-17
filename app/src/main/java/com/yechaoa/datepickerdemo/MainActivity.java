package com.yechaoa.datepickerdemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TextView tvDate;

    private View mView;
    private NumberPicker np1, np2, np3;

    private int maxDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDate = (TextView) findViewById(R.id.tv_date);

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

    }

    public void showDateDialog() {
        mView = View.inflate(this, R.layout.date_dialog, null);
        np1 = (NumberPicker) mView.findViewById(R.id.np1);
        np2 = (NumberPicker) mView.findViewById(R.id.np2);
        np3 = (NumberPicker) mView.findViewById(R.id.np3);

        //获取当前日期
        Calendar c = Calendar.getInstance();
        final int year =  c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH)+1;//月份是从0开始算的
        final int day = c.get(Calendar.DAY_OF_MONTH);

        //设置年份
        np1.setMaxValue(2999);
        np1.setValue(year); //中间参数 设置默认值
        np1.setMinValue(1900);

        //设置月份
        np2.setMaxValue(12);
        np2.setValue(month);
        np2.setMinValue(1);

        //设置天数
        np3.setMaxValue(31);
        np3.setValue(day);
        np3.setMinValue(1);

        //年份滑动监听
        np1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Log.i("NumberPicker","oldVal-----"+oldVal+"-----newVal-----"+newVal);
                //平年闰年判断
                if(newVal%4==0){
                    maxDay=29;
                }else {
                    maxDay = 28;
                }
                //设置天数的最大值
                np3.setMaxValue(maxDay);
            }
        });

        //月份滑动监听
        np2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Log.i("NumberPicker","oldVal-----"+oldVal+"-----newVal-----"+newVal);
                //月份判断
                switch (newVal){
                    case 2:
                        if(np1.getValue()%4==0){
                            maxDay=29;
                        }else{
                            maxDay=28;
                        }
                        break;
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                    case 8:
                    case 10:
                    case 12:
                        maxDay=31;
                        break;
                    default:
                        maxDay=30;
                        break;
                }
                //设置天数的最大值
                np3.setMaxValue(maxDay);
            }
        });

        new AlertDialog.Builder(MainActivity.this).setTitle("请选择时间")
                .setView(mView)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int years = np1.getValue();
                        int months = np2.getValue();
                        int days = np3.getValue();
                        tvDate.setText(years + "年" + months + "月" + days + "日");
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
    
}
