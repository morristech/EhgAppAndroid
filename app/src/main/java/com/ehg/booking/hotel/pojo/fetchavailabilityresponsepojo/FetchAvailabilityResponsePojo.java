/*
 *  Created by Emaar Hospitality Group on 25/10/18 2:50 PM
 *  Copyright (C) 2018  All rights reserved.
 *  Last modified 25/10/18 2:49 PM
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

package com.ehg.booking.hotel.pojo.fetchavailabilityresponsepojo;

import com.google.gson.annotations.SerializedName;

/**
 * FetchAvailabilityResponsePojo class.
 */
public class FetchAvailabilityResponsePojo {

  @SerializedName("Data")
  private Data data;
  @SerializedName("Message")
  private String message;
  @SerializedName("ResponseTag")
  private int responseTag;
  @SerializedName("Status")
  private Boolean status;

  /**
   * Getter method.
   *
   * @return Gets the value of data and returns data.
   */
  public Data getData() {
    return data;
  }

  /**
   * Sets the data. You can use getData() to get the value of data.
   */
  public void setData(Data data) {
    this.data = data;
  }

  /**
   * Getter method.
   *
   * @return Gets the value of message and returns message.
   */
  public String getMessage() {
    return message;
  }

  /**
   * Sets the message. You can use getMessage() to get the value of message.
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * Getter method.
   *
   * @return Gets the value of responseTag and returns responseTag.
   */
  public int getResponseTag() {
    return responseTag;
  }

  /**
   * Sets the responseTag. You can use getResponseTag() to get the value of responseTag.
   */
  public void setResponseTag(int responseTag) {
    this.responseTag = responseTag;
  }

  /**
   * Getter method.
   *
   * @return Gets the value of status and returns status.
   */
  public Boolean getStatus() {
    return status;
  }

  /**
   * Sets the status. You can use getStatus() to get the value of status.
   */
  public void setStatus(Boolean status) {
    this.status = status;
  }
}
