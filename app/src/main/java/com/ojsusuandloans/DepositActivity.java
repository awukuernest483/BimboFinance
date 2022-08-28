package com.ojsusuandloans;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;

import com.airbnb.lottie.LottieAnimationView;

public class DepositActivity extends AppCompatActivity {

    private Button button;
    String url ="https://paystack.com/pay/y-25gkgtmf";
    LottieAnimationView lottieAnimationView;
    FrameLayout backbutton;

    @SuppressLint("SetJavaScriptEnabled")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);

        lottieAnimationView= findViewById(R.id.spin);

        WebView webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {

                lottieAnimationView.setVisibility(View.GONE);

            }
        });



        if (webView.getProgress() == 100) {
            lottieAnimationView.setVisibility(View.GONE);
        }

        backbutton = findViewById(R.id.backbutton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }


}