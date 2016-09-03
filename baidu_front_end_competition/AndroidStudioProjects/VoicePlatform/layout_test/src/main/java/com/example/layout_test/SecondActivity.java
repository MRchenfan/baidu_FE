package com.example.layout_test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by CHEN on 2016/8/11.
 */
public class SecondActivity extends AppCompatActivity{
  private Button btn;
  @Override
  public void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_second);
    btn=(Button)findViewById(R.id.btn3);
    /*
     * 第二个页面回传数据，实际上返回的是一个intent
     */
    btn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent data=new Intent();
        data.putExtra("data","第二个页面回传的数据");
        setResult(2,data);
        finish();
      }
    });
  }
}
