package com.example.testapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{

    private String[] adps={"bjik3","bjjj3","bj3","sh2","sh"};
    private String[] emails={"abcd@163.com","","abcd@qq.com","abce@gmail.com"};
    private AutoCompleteTextView auto;
    private MultiAutoCompleteTextView multi_auto;

    private ToggleButton toggle_btn;
    private ImageView light;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //将布局xml文件引入到activity当中
        setContentView(R.layout.activity_main);
        /*
         * 1.初始化控件
         * 2.初始化一个适配器
         * 3.初始化一个数据源 adps 一个数组，传入适配器
         * 4.将adapter ->AutoCompleteTextView
         */
        auto=(AutoCompleteTextView)findViewById(R.id.auto_complete);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
          android.R.layout.simple_list_item_1, adps);
        auto.setAdapter(adapter);
        /*
         * 1.初始化控件
         * 2.初始化一个适配器
         * 3.初始化一个数据源 adps 一个数组，传入适配器
         * 4.将adapter ->AutoCompleteTextView
         * 5.设置分隔符
         */
        multi_auto=(MultiAutoCompleteTextView)findViewById(R.id.multi_auto_complete);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
          android.R.layout.simple_list_item_1, emails);
        multi_auto.setAdapter(adapter2);
        multi_auto.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        /*
         * 1.初始化控件
         * 2.事件监听
         * 3.
        */
        toggle_btn=(ToggleButton)findViewById(R.id.toggle_btn);
        light=(ImageView)findViewById(R.id.light);

        toggle_btn.setOnCheckedChangeListener(this);


    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        /*
        * 1.compoundButton 点击的控件
        * 2.b 状态就是控件状态
        */
        Log.i("tag",compoundButton.getText().toString());
        if(b){
            auto.setText("on");
        }else{
            auto.setText("off");
        }
    }
}


