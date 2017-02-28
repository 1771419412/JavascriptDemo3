package com.example.javascriptdemo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private WebView mWebView;
    private Button btn1;
    private Button btn2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mWebView = (WebView) findViewById(R.id.webview);
        //启用javascript
        mWebView.getSettings().setJavaScriptEnabled(true);

        //从assets目录下面加载html;
        mWebView.loadUrl("file:///android_asset/web.html");
        mWebView.addJavascriptInterface(MainActivity.this, "android");
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);


        btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.loadUrl("javascript:javacalljs()");
            }
        });
        btn2 = (Button) findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.loadUrl("javascript:javacalljswith(" + "'http://www.huya.com/'" + ")");
            }
        });
    }

    @JavascriptInterface
    public void startFunction() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "show", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @JavascriptInterface
    public void startFunction(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(MainActivity.this).setMessage(text).show();
            }
        });
    }
    @JavascriptInterface
public void callTel(final  String mobile){
        Log.v("law","号码是  "+mobile);
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mobile));
        try {
            //需要权限的操作
            startActivity(intent);
        } catch (SecurityException e) {
            e.printStackTrace();
        }

    }
@JavascriptInterface
    public void goSendMessage(final String mobile,final String msg)
    {
        Log.v("law","号码是  "+mobile);
        Uri uri = Uri.parse("smsto:"+mobile);
        Intent intent = new Intent(Intent.ACTION_SENDTO,uri);
        intent.putExtra("sms_body",msg);
        startActivity(intent);

    }



}
