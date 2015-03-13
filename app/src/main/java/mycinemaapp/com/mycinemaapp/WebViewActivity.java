package mycinemaapp.com.mycinemaapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

/**
 * Created by kristian on 15-3-13.
 */
public class WebViewActivity extends Activity implements View.OnClickListener {

    private WebView webView;
    private ImageView backPage, frontPage, refreshPage, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_layout);

        initialize();

        String url = getIntent().getExtras().getString("url");

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }

    private void initialize() {
        webView = (WebView) findViewById(R.id.web_view);
        backPage = (ImageView) findViewById(R.id.back_page);
        frontPage = (ImageView) findViewById(R.id.back_page);
        refreshPage = (ImageView) findViewById(R.id.refresh_page);
        back = (ImageView) findViewById(R.id.back);

        backPage.setOnClickListener(this);
        frontPage.setOnClickListener(this);
        refreshPage.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.back_page:
                break;
            case R.id.front_page:
                break;
            case R.id.refresh_page:
                webView.reload();
                break;
        }
    }
}
