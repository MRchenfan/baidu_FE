package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,AbsListView.OnScrollListener {

  private ListView listView;
  private ArrayAdapter<String> arr_adapter;
  private SimpleAdapter sim_adapter;
  private ArrayList<Map<String,Object>> data_list;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    /*
     * 1.实例化控件
     * 2.新建一个适配器
     *  2.1 ArrayAdapter(上下文，布局文件，数据源)
     *  2.2 SimpleAdapter（context,data,resource,from,to）
     *      context
     *      data:数据源，一个map组成的，每行数据的key必须包含在from里面
     *      resource：布局文件
     *      from：key
     *      to：绑定数据视图的id
     * 3.适配器加载数据
     * 4.应用适配器到视图控件
     * 5.添加事件监听器
     *    AdapterView.OnItemClickListener
     *    AbsListView.OnScrollListener
     */
    listView=(ListView)findViewById(R.id.list_item);
    String[] arr_data={"数组列表项1","数组列表项2","数组列表项3","数组列表项4"};
    arr_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arr_data);
    listView.setAdapter(arr_adapter);

    data_list=new ArrayList<Map<String,Object>>();
    sim_adapter=new SimpleAdapter(this,getData(),R.layout.items,new String[]{"pic","text"},
      new int[]{R.id.item_pic,R.id.item_text});
    listView.setAdapter(sim_adapter);

    listView.setOnItemClickListener(this);
    listView.setOnScrollListener(this);
  }
  private  ArrayList<Map<String,Object>> getData(){
    for(int i=0;i<20;i++){
      Map<String,Object> map=new HashMap<String,Object>();
      map.put("pic",R.mipmap.ic_launcher);
      map.put("text","小白"+i+"号");
      data_list.add(map);
    }
    return data_list;
  }

  @Override
  public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
    /*
     * int i: position
     */
    String pos=listView.getItemAtPosition(i)+"";
    Toast.makeText(this,"position="+i+"->"+pos+" id="+l,3000).show();
  }

  @Override
  public void onScrollStateChanged(AbsListView absListView, int i) {
    /*
     * int i:滚动状态
     */
    switch (i){
      case SCROLL_STATE_FLING://由于惯性还在滑动
        break;
      case SCROLL_STATE_IDLE://视图已经停止滑动
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("pic",R.mipmap.ic_launcher);
        map.put("text","陈帆100号");
        data_list.add(map);
        sim_adapter.notifyDataSetChanged();
        break;
      case SCROLL_STATE_TOUCH_SCROLL://手指未离开屏幕，正滑动中
        break;
    }
  }

  @Override
  public void onScroll(AbsListView absListView, int i, int i1, int i2) {

  }
}
