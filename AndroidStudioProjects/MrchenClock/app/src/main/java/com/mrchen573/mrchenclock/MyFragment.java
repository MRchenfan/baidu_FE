package com.mrchen573.mrchenclock;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by CHEN on 2016/8/12.
 */
public class MyFragment extends Fragment {
  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    /*
     * 将fragment布局转化为一个view对象返回
     * inflater.inflate(resource,root,attachToRoot);
     * resource:需要加载的fragment
     * root:加载layout的父ViewGroup
     * attachToRoot:boolean false
     */
    View view=inflater.inflate(R.layout.fragment,container,false);
    TextView textView= (TextView) view.findViewById(R.id.frag_text);
    textView.setText("静态加载fragment");
    return view;
  }
}
