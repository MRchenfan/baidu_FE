package com.mrchen573.mrchenclock;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TimePicker;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
  private TimePicker time_picker;
  private DatePicker date_picker;
  private Calendar cal;
  private int year;
  private int month;
  private int day;
  private int hour;
  private int minute;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    /*
     * 1.实例化控件
     * 2.使用DatePicker和TimePicker
     * 3.使用new DatePicker.OnDateChangedListener()
     *       new TimePicker.OnTimeChangedListener()
     *       实现改变事件监听
     * 4.使用dialog形式的
     *     new DatePickerDialog
     *     new TimePickerDialog
     *
     */
    init();
    date_picker.init(year, month-1, day, new DatePicker.OnDateChangedListener() {
      @Override
      public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
        //i i1 i2 分别是年月日
        year=i;
        month=i1+1;
        day=i2;
        setTitle(year+"-"+month+"-"+day+"   "+hour+"-"+minute);
      }
    });
    time_picker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
      @Override
      public void onTimeChanged(TimePicker timePicker, int i, int i1) {
        hour=i;
        minute=i1;
        setTitle(year+"-"+month+"-"+day+"   "+hour+"-"+minute);
      }
    });
    final Context context=this;
    new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
      @Override
      public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        year=i;
        month=i1;
        day=i2;
        setTitle(year+"-"+month+"-"+day+"   "+hour+"-"+minute);
      }
    },year,month-1,day).show();

    new TimePickerDialog(this,new TimePickerDialog.OnTimeSetListener(){
      @Override
      public void onTimeSet(TimePicker timePicker, int i, int i1) {
        hour=i;
        minute=i1;
        setTitle(year+"-"+month+"-"+day+"   "+hour+"-"+minute);
      }
    },hour,minute,false).show();
  }

  private void init(){
    time_picker=(TimePicker)findViewById(R.id.time_picker);
    date_picker=(DatePicker)findViewById(R.id.date_picker);
    //将当前时间打印到标题栏
    cal=Calendar.getInstance();
    year=cal.get(Calendar.YEAR);
    month=cal.get(Calendar.MONTH)+1;
    day=cal.get(Calendar.DAY_OF_MONTH);
    hour=cal.get(Calendar.HOUR_OF_DAY);
    minute=cal.get(Calendar.MINUTE);
    setTitle(year+"-"+month+"-"+day+"   "+hour+"-"+minute);
  }
}

