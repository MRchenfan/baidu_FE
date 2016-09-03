package com.mrchen573.mrchenclock;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

/**
 * Created by CHEN on 2016/8/12.
 */
public class MainActivity_2 extends Activity implements View.OnClickListener{
  private Button btn1;
  private Button btn2;
  private Button btn3;
  private Button btn4;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_layout);
    /*
     * 1.实例化并且绑定事件
     */
    init();
  }

  private void init() {
    btn1= (Button) findViewById(R.id.btn_1);
    btn2= (Button) findViewById(R.id.btn_2);
    btn3= (Button) findViewById(R.id.btn_3);
    btn4= (Button) findViewById(R.id.btn_4);
    btn1.setOnClickListener(this);
    btn2.setOnClickListener(this);
    btn3.setOnClickListener(this);
    btn4.setOnClickListener(this);
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()){
      case R.id.btn_1:{
        //跳转到activity3中去
        Intent intent=new Intent(this,MainActivity_3.class);
        startActivity(intent);
        break;
      }
      case R.id.btn_2:{
        /*
         * 1.实例化一个fragment
         * 2.获取fragmentManager
         * 3.获取beginTransaction 事物
         */
        MyFragment_2 myFragment_2=new MyFragment_2();
        FragmentManager fragmentManager=getFragmentManager();

        FragmentTransaction beginTransaction= fragmentManager.beginTransaction();
        beginTransaction.add(R.id.frag_container,myFragment_2);
        beginTransaction.addToBackStack(null);
        beginTransaction.commit();

        break;
      }
      case R.id.btn_3:{
        Toast.makeText(this,"clicked",Toast.LENGTH_LONG).show();
        break;
      }
      case R.id.btn_4:{
        Toast.makeText(this,"clicked",Toast.LENGTH_LONG).show();
        break;
      }
    }
  }
}
