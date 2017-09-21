package be.lejournaldejemeppe.jjswebview;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;



public class JssMain extends Activity {
    private WebView mWebView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jssmain);
        //Get Swipe Addon
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        //Get WebView
        mWebView = (WebView)findViewById(R.id.activity_main_webview);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        if ( !isNetworkAvailable() ) { // loading offline
            mWebView.loadUrl("file:///android_asset/index.html");
        } else {
            mWebView.loadUrl("http://lejournaldejemeppe.be");
        }
        mWebView.setWebViewClient(new ViewClient(){
            //Desactive la jolie boucle après chargement
            public void onPageFinished(WebView view, String url) {
                setSwipeFalse();
            }
        });

        //set le reload de la webview
        mSwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        mWebView.reload();
                    }
                });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService( CONNECTIVITY_SERVICE );
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void goHome(View view){
        mWebView.loadUrl("http://lejournaldejemeppe.be");
        mWebView.setWebViewClient(new ViewClient());
    }

    public void goContact(View view){
        mWebView.loadUrl("http://lejournaldejemeppe.be/nous-contacter/");
        mWebView.setWebViewClient(new ViewClient());
    }

    @Override
    public void onBackPressed(){
        if(mWebView.canGoBack()){
            mWebView.goBack();
        }
    }

    public void loadShare(View view){
        String url = mWebView.getUrl();
        String title = mWebView.getTitle();
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT, title);
        i.putExtra(Intent.EXTRA_TEXT, url);
        startActivity(Intent.createChooser(i, "Partager"));
    }

    public void setSwipeFalse(){
        mSwipeRefreshLayout.setRefreshing(false);
    }
}

