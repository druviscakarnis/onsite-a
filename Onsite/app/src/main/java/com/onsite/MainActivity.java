package com.onsite;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
    SwipeRefreshLayout refreshLayout;

    private int count = 0;
    private long startMillis = 0;

    static void refreshPage(WebView webView,String url){
        webView.loadUrl(url);
    }
    void newActivity(){
        Intent i = new Intent(this, TextInput_activity.class);
        startActivity(i);
    }
    @Override
    public void onDestroy()
    {
        // null out before the super call
        if (webView != null)
        {
            webView.loadUrl("about:blank");
            webView = null;
        }
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String url = "https://staging.onsite.lv";
        setContentView(R.layout.activity_main);

        Button btn = (Button) findViewById(R.id.secret_btn);
        webView = (WebView) findViewById(R.id.webView);

        btn.setVisibility(View.VISIBLE);
        btn.setBackgroundColor(Color.TRANSPARENT);

        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(url);

        refreshLayout = findViewById(R.id.refreshLayout);
        webView = findViewById(R.id.webView);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                System.out.println("Pressed!");
                long time = System.currentTimeMillis();

                if(startMillis==0 || (time-startMillis>5000)){
                    startMillis=time;
                    count=1;
                }else {
                    count++;
                }
                if(count==1) {
                    newActivity();
                    //webView.loadUrl("https://staging.onsite.lv/lv/admin/reports/time/r1-time-report");
                }

            }
        });


        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            }
            else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String user, String contentDisposition, String mimeType, long contentLength) {
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

                request.setMimeType(mimeType);
                request.addRequestHeader("User", user);
                request.setDescription("Downloading file...");
                request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimeType));
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, contentDisposition, mimeType));
                DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(request);
                Toast.makeText(getApplicationContext(), "File download started", Toast.LENGTH_LONG).show();
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                String currentUrl = webView.getUrl();
                refreshPage(webView,currentUrl);
                refreshLayout.setRefreshing(false);
            }
        });
    }
}