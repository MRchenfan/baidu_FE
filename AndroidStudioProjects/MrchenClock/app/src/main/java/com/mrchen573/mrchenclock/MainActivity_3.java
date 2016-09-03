package com.mrchen573.mrchenclock;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by CHEN on 2016/8/12.
 */
public class MainActivity_3 extends Activity {
  private TextView frag_text;
  private Button frag_btn;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_layout_2);
    /*
     * fragment创建过程：
     * 1.布局文件中加fragment控件
     * 2.创建一个fragment布局
     * 3.匹配好fragment的activity
     * 4.在布局文件中填好相关信息。
     * fragment 中的控件可以导入进来
     */
    frag_btn= (Button) findViewById(R.id.frag_btn);
    frag_text= (TextView) findViewById(R.id.frag_text);

    frag_btn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        frag_text.setText("TextView改变了");
      }
    });
  }
}
