/*
 *  Created by Emaar Hospitality Group on 17/10/18 6:00 PM
 *  Copyright (C) 2018  All rights reserved.
 *  Last modified 17/10/18 6:00 PM
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

package com.ehg.booking.hotel.pojo.fetchservicesresponsepojo;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * ServiceOption.
 */
public class ServiceOption {

  @SerializedName("DailyRates")
  private List<DailyRate> dailyRates;
  @SerializedName("ServiceOptionDesc")
  private ServiceOptionDesc serviceOptionDesc;
  @SerializedName("ServiceOptionId")
  private String serviceOptionId;
  @SerializedName("ServiceOptionName")
  private String serviceOptionName;
  @SerializedName("SortOrder")
  private int sortOrder;

  /**
   * Getter method.
   *
   * @return Gets the value of dailyRates and returns dailyRates.
   */
  public List<DailyRate> getDailyRates() {
    return dailyRates;
  }

  /**
   * Sets the dailyRates. You can use getDailyRates() to get the value of dailyRates.
   */
  public void setDailyRates(
      List<DailyRate> dailyRates) {
    this.dailyRates = dailyRates;
  }

  /**
   * Getter method.
   *
   * @return Gets the value of serviceOptionDesc and returns serviceOptionDesc.
   */
  public ServiceOptionDesc getServiceOptionDesc() {
    return serviceOptionDesc;
  }

  /**
   * Sets the serviceOptionDesc. You can use getServiceOptionDesc() to get the value of
   * serviceOptionDesc.
   */
  public void setServiceOptionDesc(
      ServiceOptionDesc serviceOptionDesc) {
    this.serviceOptionDesc = serviceOptionDesc;
  }

  /**
   * Getter method.
   *
   * @return Gets the value of serviceOptionId and returns serviceOptionId.
   */
  public String getServiceOptionId() {
    return serviceOptionId;
  }

  /**
   * Sets the serviceOptionId. You can use getServiceOptionId() to get the value of serviceOptionId.
   */
  public void setServiceOptionId(String serviceOptionId) {
    this.serviceOptionId = serviceOptionId;
  }

  /**
   * Getter method.
   *
   * @return Gets the value of serviceOptionName and returns serviceOptionName.
   */
  public String getServiceOptionName() {
    return serviceOptionName;
  }

  /**
   * Sets the serviceOptionName. You can use getServiceOptionName() to get the value of
   * serviceOptionName.
   */
  public void setServiceOptionName(String serviceOptionName) {
    this.serviceOptionName = serviceOptionName;
  }

  /**
   * Getter method.
   *
   * @return Gets the value of sortOrder and returns sortOrder.
   */
  public int getSortOrder() {
    return sortOrder;
  }

  /**
   * Sets the sortOrder. You can use getSortOrder() to get the value of sortOrder.
   */
  public void setSortOrder(int sortOrder) {
    this.sortOrder = sortOrder;
  }
}
