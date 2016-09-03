package com.example.testapp;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by CHEN on 2016/8/10.
 */
public class MarqueeText extends TextView{

  public MarqueeText(Context context) {
    super(context);
  }

  public MarqueeText(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public MarqueeText(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public MarqueeText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
  }

  @Override
  public boolean isFocused(){
    return true;
  }
}
