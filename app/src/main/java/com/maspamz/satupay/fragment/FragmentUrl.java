package com.maspamz.satupay.fragment;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.maspamz.satupay.helper.PrintWebView;
import com.maspamz.satupay.helper.WebViewClientHelper;
import com.maspamz.satupay.R;

/**
 * Created by Maspamz on 29/09/2018.
 *
 */

public class FragmentUrl extends Fragment {
    WebView mWebView;
    @SuppressLint("StaticFieldLeak")
    public static SwipeRefreshLayout swipeRefreshLayout;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_activity, container, false);
        // Set title bar
        ((FragmentActivity) getActivity()).setTitle("");
        mWebView = (WebView) rootView.findViewById(R.id.webView);
        Bundle getDatKey = getArguments();
        final String linkUrl= getDatKey.getString("URL_ITEM", null);

        mWebView.setWebViewClient(new WebViewClientHelper(getActivity()));

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.setVerticalScrollBarEnabled(true);
        mWebView.getSettings().setBuiltInZoomControls(false);
        mWebView.getSettings().setSupportZoom(false);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setSupportMultipleWindows(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);


        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe);
        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mWebView.loadUrl(linkUrl);
            }
        });
        mWebView.loadUrl(linkUrl);

        mWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == android.view.KeyEvent.ACTION_DOWN) {
                    if ((keyCode == android.view.KeyEvent.KEYCODE_BACK)) {
                        if(mWebView!=null) {
                            if(mWebView.canGoBack()) {
                                mWebView.goBack();
                            } else{
                                ExitDialog();
                            }
                        }
                    }
                }
                return true;
            }
        });

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_print, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_print) {
            PrintWebView.printOrCreatePdfFromWebview(mWebView,"Documnent");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void ExitDialog() {
        AlertDialog.Builder confirmExit = new AlertDialog.Builder(getActivity());
        confirmExit.setTitle("Exit ?");
        confirmExit.setMessage("Are you sure ?");
        confirmExit.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.exit(0);
            }
        });
        confirmExit.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //null
            }
        });
        confirmExit.show();
    }

}
