/*
 *  Created by Emaar Hospitality Group on 18/9/18 8:35 PM
 *  Copyright (C) 2018  All rights reserved.
 *  Last modified 18/9/18 8:35 PM
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.ehg.settings;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.ehg.R;
import com.ehg.home.BaseActivity;


/**
 * This Class Contains privacy policy information.
 */
public class PrivacyPolicyActivity extends BaseActivity {

  private Context context;
  private WebView webViewPrivacyPolicy;
  private ProgressDialog progressBar;
  private String urlPrivacyPolicy = "";
  private boolean loadingFinished = true;

  /**
   * Called when activity created first.
   *
   * @param savedInstanceState bundle object
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_privacypolicy);

    context = this;
    initView();
  }

  /**
   * Init's view components of this screen.
   */
  private void initView() {

    webViewPrivacyPolicy = findViewById(R.id.webview_settingprivacypolicy);
    progressBar = new ProgressDialog(this);
    progressBar.setCanceledOnTouchOutside(false);
    progressBar.setMessage(getString(R.string.progress_pleasewait));
  }

  /**
   * Open and load url in web view.
   */
  public void loadWebview() {

    webViewPrivacyPolicy.setWebViewClient(new PrivacyPolicyBrowser());
    webViewPrivacyPolicy.getSettings().setLoadWithOverviewMode(true);
    webViewPrivacyPolicy.getSettings().setUseWideViewPort(true);
    webViewPrivacyPolicy.getSettings().setLoadsImagesAutomatically(true);
    webViewPrivacyPolicy.getSettings().setBuiltInZoomControls(true);
    webViewPrivacyPolicy.getSettings().setPluginState(PluginState.ON);
    webViewPrivacyPolicy.getSettings().setJavaScriptEnabled(true);
    webViewPrivacyPolicy.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
    webViewPrivacyPolicy.setScrollbarFadingEnabled(false);
    //browser.setInitialScale(30);
    webViewPrivacyPolicy.getSettings().setUseWideViewPort(true);
    webViewPrivacyPolicy.getSettings().setLoadWithOverviewMode(true);
    webViewPrivacyPolicy.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
    if (urlPrivacyPolicy != null && !urlPrivacyPolicy.equalsIgnoreCase("")) {
      webViewPrivacyPolicy.loadUrl(urlPrivacyPolicy);
    }
  }


  /**
   * The Class PrivacyPolicyBrowser Sets the WebViewClient
   * that will receive various notifications and requests.;
   */
  private class PrivacyPolicyBrowser
      extends WebViewClient {

    /**
     * Give the host application a chance to take control
     * when a URL is about to be loaded in the current WebView.
     *
     * @param view - The WebView that is initiating the callback.
     * @param url - The URL to be loaded.
     * @return - true to cancel the current load, otherwise return false.
     */
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {

      view.loadUrl(url);

      return true;
    }

    /**
     * Notify the host application that the WebView will
     * load the resource specified by the given url.
     *
     * @param view - The WebView that is initiating the callback.
     * @param url - The url of the resource the WebView will load.
     */
    public void onLoadResource(WebView view, String url) {
            /*if (mProgressBar != null) {
                loadingFinished = false;
                // in standard case YourActivity.this
                mProgressBar.show();
            }*/
    }

    /**
     * Report an error to the application.
     *
     * @param view - The WebView that is initiating the callback.
     * @param errorCode - The error code corresponding to an ERROR_* value.
     * @param description - A String describing the error.
     * @param failingUrl - The url that failed to load.
     */
    @Override
    public void onReceivedError(WebView view, int errorCode,
        String description, String failingUrl) {
      if (progressBar != null && progressBar.isShowing()) {
        progressBar.dismiss();
      }
    }

    /**
     * Notify the application that a page has started loading.
     *
     * @param view - The WebView that is initiating the callback.
     * @param url - The url to be loaded.
     * @param favicon - The favicon for this page if it already exists in the database.
     */
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {

      if (progressBar != null) {
        loadingFinished = false;
        // in standard case YourActivity.this
        progressBar.show();
      }

      super.onPageStarted(view, url, favicon);
    }

    /**
     * Notify the host application that a page has finished loading.
     *
     * @param view - The WebView that is initiating the callback.
     * @param url - The url of the page.
     */
    public void onPageFinished(WebView view, String url) {
      if (progressBar != null && progressBar.isShowing()) {
        progressBar.dismiss();
      }
    }
  }
}
