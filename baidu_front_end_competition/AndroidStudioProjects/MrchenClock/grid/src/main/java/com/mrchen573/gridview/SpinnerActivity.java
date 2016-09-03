package com.mrchen573.gridview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by CHEN on 2016/8/12.
 */
public class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener{
  private Spinner spinner_view;
  private ArrayAdapter arr_adapter;
  private String[] data_list={"合肥","北京","上海","广州","杭州"};

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.spinner);
    /*
     * 1.实例化控件
     * 2.设置adapter
     * 3.数据源设置
     * 4.使用adapter
     * 5.事件监听
     */
    spinner_view= (Spinner)findViewById(R.id.spinner_view);
    arr_adapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,data_list);
    arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spinner_view.setAdapter(arr_adapter);
    spinner_view.setOnItemSelectedListener(this);
  }

  @Override
  public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    String str=spinner_view.getItemAtPosition(i)+"";
    Toast.makeText(this,str,Toast.LENGTH_LONG).show();
  }

  @Override
  public void onNothingSelected(AdapterView<?> adapterView) {

  }
}
