package com.maspamz.satupay.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.maspamz.satupay.ErrorActivity;
import com.maspamz.satupay.R;
import com.maspamz.satupay.fragment.FragmentUrl;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * Created by Maspamz on 29/09/2018.
 *
 */

public class WebViewClientHelper extends WebViewClient{
    Activity activity;

    public WebViewClientHelper (Activity activity) {
        this.activity= activity;

    }
    private ProgressDialog progressDialog;
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        // TODO Auto-generated method stub
        super.onPageStarted(view, url, favicon);
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(view.getContext());
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(true);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }
    }

    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

            if (errorCode == -2) {
                activity.setContentView(R.layout.error_page);
                view.loadData("", "", null);
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setCancelable(false);
                builder.setTitle(Html.fromHtml("<b>SatuPay</b>"));
                builder.setMessage(Html.fromHtml("<font color='#120049'>Your data services are not working.Please check your data services.</font>"));
                builder.setPositiveButton(Html.fromHtml("<font color='#7F02AE'><b>OK</b></font>"), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(activity.getApplicationContext(), ErrorActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("EXIT", true);
                        activity.startActivity(intent);


                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

            }
    }

    @Override
    public boolean shouldOverrideUrlLoading(final WebView view, String url) {
        view.loadUrl(url);

        view.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

                request.setMimeType(mimeType);
                String cookies = CookieManager.getInstance().getCookie(url);
                request.addRequestHeader("cookie", cookies);
                request.addRequestHeader("User-Agent", userAgent);
                request.setDescription("Downloading file...");
                request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimeType));
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, contentDisposition, mimeType));
                DownloadManager dm = (DownloadManager)  activity.getSystemService(DOWNLOAD_SERVICE);
                dm.enqueue(request);
                Toast.makeText(activity.getApplicationContext(), "Downloading File", Toast.LENGTH_LONG).show();
            }
        });

        if (url.startsWith("tel:")) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
            activity.startActivity(intent);
            view.reload();
            return true;
        }
        if (url.startsWith("sms:")) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            activity.startActivity(intent);
            view.reload();
            return true;
        }
        if (url.startsWith("mailto:")) {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse(url));
            try {
                activity.startActivity(intent);
            }catch (android.content.ActivityNotFoundException e) {
                e.printStackTrace();
                Log.d("Email error:",e.toString());
            }
            view.reload();
            return true;
        }
        return true;
    }


    @Override
    public void onPageFinished(WebView view, String url) {
        FragmentUrl.swipeRefreshLayout.setRefreshing(false);
        super.onPageFinished(view, url);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                progressDialog.dismiss();
            }
        }, 500);
    }

}
