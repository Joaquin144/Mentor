package com.joaquin.hackathon.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.joaquin.hackathon.R;

public class VideoPlayerActivity extends AppCompatActivity {

    private WebView webView;
    String title,url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        webView=findViewById(R.id.videoPlayer);
        //adding settings to videoPlayer
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebChromeClient(new ChromeClient());


        //Preventing screenshot and Screen Recording to ensure content safety

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
//                WindowManager.LayoutParams.FLAG_SECURE);


        //end


        if(getIntent()!=null){
            url=getIntent().getStringExtra("Link");
            title=getIntent().getStringExtra("Title");
            playVideo();

        }else{
            Toast.makeText(this, "Something Went Wrong!", Toast.LENGTH_LONG).show();
        }
    }
    private void playVideo(){
        if(url.length()==9){

            String videoiframe="<iframe width=\"100%\" height=\"100%\" src=\"https://player.vimeo.com/video/"+url+"\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; picture-in-picture\" allowfullscreen></iframe>";
            webView.loadData(videoiframe,"text/html","utf-8");


        }else {

            Toast.makeText(this, "Something Went Wrong!", Toast.LENGTH_LONG).show();

        }
    }

    //Writing chrome client code to apply video player settings for webView
    private class ChromeClient extends WebChromeClient {
        private View mCustomView;
        private WebChromeClient.CustomViewCallback mCustomViewCallback;
        protected FrameLayout mFullscreenContainer;
        private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;

        ChromeClient() {
        }

        public Bitmap getDefaultVideoPoster() {
            if (mCustomView == null) {
                return null;
            }
            return BitmapFactory.decodeResource(getResources(), 2130837573);
        }

        public void onHideCustomView() {
            ((FrameLayout) getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = null;
            getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            setRequestedOrientation(this.mOriginalOrientation);
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;
        }

        public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback) {
            if (this.mCustomView != null) {
                onHideCustomView();
                return;
            }
            this.mCustomView = paramView;
            this.mOriginalSystemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
            this.mOriginalOrientation = getRequestedOrientation();
            this.mCustomViewCallback = paramCustomViewCallback;
            ((FrameLayout) getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
            getWindow().getDecorView().setSystemUiVisibility(3846 | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }




}