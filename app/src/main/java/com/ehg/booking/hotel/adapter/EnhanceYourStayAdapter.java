/*
 *  Created by Emaar Hospitality Group on 26/10/18 6:28 PM
 *  Copyright (C) 2018  All rights reserved.
 *  Last modified 26/10/18 6:28 PM
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

package com.ehg.booking.hotel.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.ehg.R;

public class EnhanceYourStayAdapter extends RecyclerView.Adapter<EnhanceYourStayAdapter.ViewHolder> {


  private final Context context;
  private final OnEnhanceYourStayItemClicklistner onItemListner;

  public EnhanceYourStayAdapter(Context context, OnEnhanceYourStayItemClicklistner stayItemClicklistner) {

    this.context = context;
    onItemListner = stayItemClicklistner;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View view = LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.item_enhanceyourstay, viewGroup, false);

    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

  }

  @Override
  public int getItemCount() {
    return 5;
  }

  /**
   * OnItemClick interface for Select item Recycler view.
   */
  public interface OnEnhanceYourStayItemClicklistner {

    void onItemClick(int position, View view);
  }

  public class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
    }

    /**
     * Called when view clicked.
     *
     * @param view view
     */
    @Override
    public void onClick(View view) {

      if (onItemListner != null) {
        onItemListner.onItemClick(getAdapterPosition(), view);
        notifyDataSetChanged();
      }
    }
  }
}
