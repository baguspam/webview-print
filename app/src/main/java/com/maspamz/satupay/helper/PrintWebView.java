package com.maspamz.satupay.helper;

import android.content.Context;
import android.os.Build;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.support.annotation.RequiresApi;
import android.webkit.WebView;

/**
 * Created by Maspamz on 30/09/2018.
 *
 */

public class PrintWebView {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressWarnings("deprecation")
    public static PrintJob printOrCreatePdfFromWebview(WebView webview, String jobName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Context c = webview.getContext();
            PrintDocumentAdapter printAdapter;
            PrintManager printManager = (PrintManager) c.getSystemService(Context.PRINT_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                printAdapter = webview.createPrintDocumentAdapter(jobName);
            } else {
                printAdapter = webview.createPrintDocumentAdapter();
            }
            if (printManager != null) {
                return printManager.print(jobName, printAdapter, new PrintAttributes.Builder().build());
            }
        } else {
            //Log.e(getClass().getName(), "ERROR: Method called on too low Android API version");
        }
        return null;
    }

}
