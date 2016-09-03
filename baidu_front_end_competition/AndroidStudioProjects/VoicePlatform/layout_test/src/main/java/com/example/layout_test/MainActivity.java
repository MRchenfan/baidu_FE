package com.example.layout_test;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity {
  private Button btn1;
  private Button btn2;
  private Context myContext;
  private TextView tv;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    Log.i("tag","onCreate");
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    /*
     * 1.初始化button
     * 2.给button添加点击事件
     * 3.第一种实现跳转方法：
     *  3.1 new Intent( ，) 第一个传入的是上下文对象
     *  3.2 第二个参数是目标文件
     *  3.3 startActivity() 传入一个intent对象
     * 4.第二种实现跳转方法；
     *  4.1 startActivityForresult 方法实现
     *  4.2 第一个参数是一个intent对象
     *  4.3 第二个参数是请求的一个标识？？
     *  4.4 onActivityResult方法
     */
    myContext=this;
    tv=(TextView)findViewById(R.id.text);
    btn1=(Button)findViewById(R.id.btn1);
    btn2=(Button)findViewById(R.id.btn2);

    btn1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent=new Intent(myContext,SecondActivity.class);
        startActivity(intent);
      }
    });

    btn2.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent=new Intent(myContext,SecondActivity.class);
        startActivityForResult(intent,1);
      }
    });
  }
  /*
   * 1.requestCode 请求的标识
   * 2.resultCode 返回的标识
   * 3.data 是返回的数据
   */
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if(requestCode==1&&resultCode==2){
      String content=data.getStringExtra("data");
      tv.setText(content);
    }
  }

  @Override
  protected void onStart() {
    Log.i("tag","onStart");
    super.onStart();
  }

  @Override
  protected void onResume() {
    Log.i("tag","onResume");
    super.onResume();
  }

  @Override
  protected void onPause() {
    super.onPause();
    Log.i("tag","onPause");
  }

  @Override
  protected void onStop() {
    super.onStop();
    Log.i("tag","onStop");
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    Log.i("tag","onDestroy");
  }

  @Override
  protected void onRestart() {
    super.onRestart();
    Log.i("tag","onRestart");
  }
}
