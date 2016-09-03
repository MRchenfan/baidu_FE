package com.mrchen573.gridview;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener{
  private GridView grid_view;
  private ArrayList<Map<String,Object>> data_list;
  private SimpleAdapter sim_adapter;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    /*
     * 1.实例化控件
     * 2.准备数据源 一个特殊泛型的集合
     * 3.新建适配器
     * 4.加载适配器
     * 5.配置事件监听（OnItemClick）
     */
    grid_view= (GridView) findViewById(R.id.grid_view);
    data_list=new ArrayList<Map<String,Object>>();
    sim_adapter=new SimpleAdapter(this,getData(),R.layout.grid_items,
      new String[]{"pic","text"},new int[]{R.id.grid_img,R.id.grid_text});
    grid_view.setAdapter(sim_adapter);
    grid_view.setOnItemClickListener(this);
  }
  private ArrayList<Map<String,Object>> getData(){
    for(int i=0;i<18;i++){
      Map<String,Object> map=new HashMap<>();
      map.put("pic",R.mipmap.ic_launcher);
      map.put("text","icon-"+i);
      data_list.add(map);
    }
    return data_list;
  }

  @Override
  public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
    Toast.makeText(this,grid_view.getItemAtPosition(i)+"",Toast.LENGTH_LONG).show();
  }
}
