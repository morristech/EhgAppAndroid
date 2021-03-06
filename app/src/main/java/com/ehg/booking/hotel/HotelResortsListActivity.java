/*
 *  Created by Emaar Hospitality Group on 6/10/18 2:54 PM
 *  Copyright (C) 2018  All rights reserved.
 *  Last modified 6/10/18 2:54 PM
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

package com.ehg.booking.hotel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import com.ehg.R;
import com.ehg.booking.hotel.adapter.HotelResortsAdapter;
import com.ehg.booking.hotel.adapter.HotelResortsAdapter.OnHotelItemClickListener;
import com.ehg.booking.hotel.pojo.roomareasearchresponsepojo.HotelList;
import com.ehg.home.BaseActivity;
import com.ehg.utilities.AppUtil;
import java.util.ArrayList;

/**
 * This class allows to show Hotel & Resorts List.
 */
public class HotelResortsListActivity extends BaseActivity
    implements View.OnClickListener, OnHotelItemClickListener {

  private Context context;
  private RecyclerView recyclerViewHotelList;
  private TextView textViewHotelCount;
  private AppCompatImageView imageViewHotelFilter;
  private AppCompatImageView imageViewHotelSort;
  private TextView textViewHeaderTitle;
  private String headerTitle = "";
  private AppCompatImageView headerBackButton;

  /**
   * Called when activity created.
   *
   * @param savedInstanceState - bundle object
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_hotelresorts);
    context = this;

    initView();
  }

  /**
   * Init's view components of this activity.
   */
  private void initView() {

    Bundle bundle = getIntent().getExtras();
    if (bundle != null) {
      headerTitle = bundle.getString("title");
    }
    textViewHeaderTitle = findViewById(R.id.textview_header_title);
    recyclerViewHotelList = findViewById(R.id.recyclerview_hotelresorts_list);
    textViewHotelCount = findViewById(R.id.textview_hotelresorts_count);
    imageViewHotelFilter = findViewById(R.id.appcompactimageview_hotelresorts_filter);
    imageViewHotelSort = findViewById(R.id.appcompactimageview_hotelresorts_sort);
    headerBackButton = findViewById(R.id.imageview_header_back);

    //Set OnClickListener
    headerBackButton.setOnClickListener(this);
    findViewById(R.id.appcompactimageview_hotelresorts_filter).setOnClickListener(this);

    //Set Adapter
    recyclerViewHotelList.setLayoutManager(new LinearLayoutManager(context));
    recyclerViewHotelList.setHasFixedSize(true);
    HotelResortsAdapter hotelResortsAdapter = new HotelResortsAdapter(context, this, new ArrayList<HotelList>());
    recyclerViewHotelList.setAdapter(hotelResortsAdapter);
    AppUtil.animateRecyclerView(context, recyclerViewHotelList,
        R.anim.layout_animation_from_bottom);

    if (!TextUtils.isEmpty(headerTitle)) {
      textViewHeaderTitle.setText(headerTitle);
    }
  }

  /**
   * Called when activity resumed.
   */
  @Override
  protected void onResume() {
    super.onResume();
    setBackArrowRtl((AppCompatImageView) findViewById(R.id.imageview_header_back));
  }

  /**
   * Called to set RTL back arrow.
   *
   * @param appCompatImageView imageview object
   */
  @Override
  public void setBackArrowRtl(AppCompatImageView appCompatImageView) {
    super.setBackArrowRtl(appCompatImageView);
  }

  /**
   * OnKeyDown callback will be called when phone back key pressed.
   *
   * @param keyCode keycode
   * @param event event
   * @return return boolean value
   */
  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK) {
      AppUtil.finishActivityWithAnimation(this);
    }
    return super.onKeyDown(keyCode, event);
  }

  /**
   * This method Called when a view has been clicked.
   *
   * @param view - clicked view object.
   */
  @Override
  public void onClick(View view) {

    switch (view.getId()) {

      case R.id.imageview_header_back:
        AppUtil.finishActivityWithAnimation(this);
        break;

      case R.id.appcompactimageview_hotelresorts_filter:
        Intent intent = new Intent(this, HotelFilterActivity.class);
        AppUtil.startActivityWithAnimation(this, intent, false);
        break;
      default:
        break;
    }
  }

  /**
   * Called when hotel list item clicked.
   *
   * @param position clicked item position
   * @param view clicked view item
   * @param title title
   */
  @Override
  public void onHotelItemClicked(int position, View view, String title) {

  }
}
