package be.lejournaldejemeppe.jjswebview;

import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by vande on 28/05/2017.
 */

public class ViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url){
        if (Uri.parse(url).getHost().endsWith("lejournaldejemeppe.be")) {
            return false;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
        view.getContext().startActivity(intent);

        return true;
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        // TODO Auto-generated method stub
        view.loadUrl("file:///android_asset/index.html");
    }
}
