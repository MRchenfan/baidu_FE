package com.mrchen573.gridview;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import java.io.File;

/**
 * Created by CHEN on 2016/8/12.
 * 这个类可以被js调用！！！
 */
public class WebHost {
  public Context myContext;
  //这里定义的方法可以被js调用？？？
  @JavascriptInterface
  public void makeToast(String text){
    Toast.makeText(myContext,text,Toast.LENGTH_LONG).show();
  }

  @JavascriptInterface
  public int clearCacheFolder(File dir, long numDays) {
    int deletedFiles = 0;
    if (dir!= null && dir.isDirectory()) {
      try {
        for (File child:dir.listFiles()) {
          if (child.isDirectory()) {
            deletedFiles += clearCacheFolder(child, numDays);
          }
          if (child.lastModified() < numDays) {
            if (child.delete()) {
              deletedFiles++;
            }
          }
        }
      } catch(Exception e) {
        e.printStackTrace();
      }
    }
    return deletedFiles;
  }

  public WebHost(Context context){
    this.myContext=context;
  }
}
