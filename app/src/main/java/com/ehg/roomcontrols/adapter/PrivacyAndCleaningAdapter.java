/*
 *  Created by Emaar Hospitality Group on 23/10/18 4:20 PM
 *  Copyright (C) 2018  All rights reserved.
 *  Last modified 23/10/18 4:20 PM
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

package com.ehg.roomcontrols.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.ehg.R;
import com.ehg.roomcontrols.pojo.PrivacyAndCleaningPojo;
import com.makeramen.roundedimageview.RoundedImageView;
import java.util.ArrayList;

public class PrivacyAndCleaningAdapter extends
    RecyclerView.Adapter<PrivacyAndCleaningAdapter.ViewHolder> {

  private Context context;

  private LayoutInflater inflater;

  private int itemHeight;

  private ArrayList<PrivacyAndCleaningPojo> privacyAndCleaninglist;

  private OnPrivacyAndCleaningItemClickListener onPrivacyAndCleaningItemClickListener;

  /**
   * Parameterized constructor for offer list adapter.
   */
  public PrivacyAndCleaningAdapter(Context context, int itemHeight,
      ArrayList<PrivacyAndCleaningPojo> privacyAndCleaninglist,
      OnPrivacyAndCleaningItemClickListener onPrivacyAndCleaningItemClickListener) {
    this.context = context;
    this.inflater = LayoutInflater.from(context);
    this.itemHeight = itemHeight / 4 - 100;
    this.privacyAndCleaninglist = privacyAndCleaninglist;
    this.onPrivacyAndCleaningItemClickListener = onPrivacyAndCleaningItemClickListener;
    if (this.itemHeight == 0 || this.itemHeight < 100) {
      this.itemHeight = 100;
    }
  }

  /**
   * Called to inflate layout item and returns ViewHolder object.
   *
   * @param viewGroup viewGroup object
   * @param position integer position
   * @return returns ViewHolder object
   */
  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
    return new ViewHolder(inflater.inflate(R.layout.item_light,
        viewGroup, false));
  }

  /**
   * Called to bind data values with viewHolder items.
   *
   * @param viewHolder viewHolder object
   * @param position integer position
   */
  @Override
  public void onBindViewHolder(@NonNull final ViewHolder viewHolder,
      @SuppressLint("RecyclerView") final int position) {

    PrivacyAndCleaningPojo privacyAndCleaningPojo = privacyAndCleaninglist.get(position);

    /*if (lightPojo.isLightOn()) {
      viewHolder.textViewTitle.setTextColor(context.getResources().getColor(R.color.colorGray));
      viewHolder.appCompatImageViewLightIcon.setImageResource(R.drawable.placeholder_light);
    } else {
      viewHolder.textViewTitle.setTextColor(context.getResources().getColor(R.color.colorWhite));
      viewHolder.appCompatImageViewLightIcon.setImageResource(R.drawable.placeholder);
    }*/
    viewHolder.textViewTitle.setText(privacyAndCleaninglist.get(position).getTitle());
  }

  /**
   * Returns total number of items in adapter.
   *
   * @return itemCount
   */
  @Override
  public int getItemCount() {
    return privacyAndCleaninglist != null
        && privacyAndCleaninglist.size() > 0 ? privacyAndCleaninglist.size() : 0;
  }

  /**
   * This is ViewHolder class.
   */
  public class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

    private final TextView textViewTitle;

    private final FrameLayout frameLayout;

    private final RoundedImageView appCompatImageViewLightIcon;

    /**
     * ViewHolder constructor.
     *
     * @param view required for inflating all components from item layout
     */
    public ViewHolder(View view) {
      super(view);

      textViewTitle = view.findViewById(R.id.textview_light_lighttitle);
      frameLayout = view.findViewById(R.id.framlayoutlayout_lightitem);
      appCompatImageViewLightIcon = view.findViewById(R.id.appcompatimageview_light_lighticon);

      //Set dynamic height
      frameLayout.getLayoutParams().height = itemHeight;
      frameLayout.getLayoutParams().width = itemHeight;

      //Set OnClickListener
      frameLayout.setOnClickListener(this);
    }

    /**
     * Called when view item clicked.
     *
     * @param view clicked view item
     */
    @Override
    public void onClick(View view) {
      if (onPrivacyAndCleaningItemClickListener != null) {
        onPrivacyAndCleaningItemClickListener.onPrivacyAndCleaningItemClicked(getAdapterPosition());
      }
    }
  }

  /**
   * PrivacyAndCleaningItemClick listener.
   */
  public interface OnPrivacyAndCleaningItemClickListener {

    void onPrivacyAndCleaningItemClicked(int position);
  }
}
