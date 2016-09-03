package com.example.myapplication;

import android.app.Activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements View.OnClickListener{
  //实例化控件
  public EditText text_screen;//显示结果的textView
  public Button btn_0;
  public Button btn_1;
  public Button btn_2;
  public Button btn_3;
  public Button btn_4;
  public Button btn_5;
  public Button btn_6;
  public Button btn_7;
  public Button btn_8;
  public Button btn_9;

  public Button btn_add;
  public Button btn_minus;
  public Button btn_multiple;
  public Button btn_div;

  public Button btn_equal;
  public Button btn_point;

  public Button btn_volumn;
  public Button btn_style;
  public Button btn_cancel;
  public Button btn_del;

  public boolean flag;//
  public boolean expFlag;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    /*
     * 1.给按钮赋值
     * 2.给按钮绑定事件
     * 3.完成控制逻辑
     */
    init();
    bind();
  }

  public void init(){
    flag=false;
    expFlag=false;
    //给按钮赋值
    text_screen=(EditText)findViewById(R.id.text_screen);

    btn_0=(Button)findViewById(R.id.btn_0);
    btn_1=(Button)findViewById(R.id.btn_1);
    btn_2=(Button)findViewById(R.id.btn_2);
    btn_3=(Button)findViewById(R.id.btn_3);
    btn_4=(Button)findViewById(R.id.btn_4);
    btn_5=(Button)findViewById(R.id.btn_5);
    btn_6=(Button)findViewById(R.id.btn_6);
    btn_7=(Button)findViewById(R.id.btn_7);
    btn_8=(Button)findViewById(R.id.btn_8);
    btn_9=(Button)findViewById(R.id.btn_9);

    btn_add=(Button)findViewById(R.id.btn_add);
    btn_minus=(Button)findViewById(R.id.btn_minus);
    btn_multiple=(Button)findViewById(R.id.btn_multiple);
    btn_div=(Button)findViewById(R.id.btn_div);

    btn_equal=(Button)findViewById(R.id.btn_equal);
    btn_point=(Button)findViewById(R.id.btn_point);

    btn_volumn=(Button)findViewById(R.id.btn_volumn);
    btn_style=(Button)findViewById(R.id.btn_style);
    btn_cancel=(Button)findViewById(R.id.btn_cancel);
    btn_del=(Button)findViewById(R.id.btn_del);
  }
  public void bind(){
    btn_0.setOnClickListener(this);
    btn_1.setOnClickListener(this);
    btn_2.setOnClickListener(this);
    btn_3.setOnClickListener(this);
    btn_4.setOnClickListener(this);
    btn_5.setOnClickListener(this);
    btn_6.setOnClickListener(this);
    btn_7.setOnClickListener(this);
    btn_8.setOnClickListener(this);
    btn_9.setOnClickListener(this);

    btn_add.setOnClickListener(this);
    btn_minus.setOnClickListener(this);
    btn_multiple.setOnClickListener(this);
    btn_div.setOnClickListener(this);

    btn_point.setOnClickListener(this);
    btn_equal.setOnClickListener(this);

    btn_volumn.setOnClickListener(this);
    btn_style.setOnClickListener(this);
    btn_cancel.setOnClickListener(this);
    btn_del.setOnClickListener(this);
  }
  @Override
  public void onClick(View view) {
    String str= String.valueOf(text_screen.getText());
    switch (view.getId()){
      case R.id.btn_0:
      case R.id.btn_1:
      case R.id.btn_2:
      case R.id.btn_3:
      case R.id.btn_4:
      case R.id.btn_5:
      case R.id.btn_6:
      case R.id.btn_7:
      case R.id.btn_8:
      case R.id.btn_9:
      case R.id.btn_point:
        if(!flag){
          str="";
          flag=true;
        }
        text_screen.setText(str+((Button)view).getText());//实现动态显示
        break;

      case R.id.btn_add:
      case R.id.btn_minus:
      case R.id.btn_multiple:
      case R.id.btn_div:
        if(expFlag){
          return;//如果有符号了，就不执行
        }else{
          expFlag=true;
        }
        text_screen.setText(str + " " + ((Button)view).getText() + " ");//实现动态显示
        if(!flag){
          flag=true;//如果
        }
        break;

      case R.id.btn_cancel:
        text_screen.setText("");//实现动态显示
        flag=false;
        expFlag=false;
        break;
      case R.id.btn_del:
        String s="";

        if(str!=""&&str.length()>0){
          s=str.substring(0,str.length()-1);
          //如果最后一个是空字符就删除三个字符长度
          if(str.substring(str.length()-1).equals(" ")){
            s=str.substring(0,str.length()-3);
          }

          text_screen.setText(s);//删除一个字符
        }

        if(!s.contains("+")&&!s.contains("-")&&!s.contains("x")&&!s.contains("/")){
          //如果把符号删除了
          expFlag=false;
        }
        break;

      case R.id.btn_equal:
        getResult();
        break;
    }
  }
  public void getResult(){
    String exp= String.valueOf(text_screen.getText());
    if(exp==""&&exp.length()<0){return;}
    if(!exp.contains(" ")){return;}

    //获取表达死
    int pos=exp.indexOf(" ");
    String s1 = exp.substring(0,pos);
    String op = exp.substring(pos+1,pos+2);
    String s2 = exp.substring(pos+3);

    //求解
    double result=0;
    if(!s1.equals(" ")&&!s2.equals(" ")){
      double d1 = Double.parseDouble(s1);
      double d2 = Double.parseDouble(s2);
      switch (op){
        case "+":
          result=d1+d2;
          break;
        case "-":
          result=d1-d2;
          break;
        case "x":
          result=d1*d2;
          break;
        case "/":
          if(d2==0){
            result=0;
          }else{
            result=d1/d2;
          }
          break;
      }
    }

    text_screen.setText(result+"");
    flag=false;
    expFlag=false;
  }
}
