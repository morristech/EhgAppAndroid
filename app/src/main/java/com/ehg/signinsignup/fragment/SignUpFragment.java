/*
 *  Created by Emaar Hospitality Group on 11/9/18 11:44 AM
 *  Copyright (C) 2018  All rights reserved.
 *  Last modified 11/9/18 11:44 AM
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

package com.ehg.signinsignup.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.text.Html;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.ehg.R;
import com.ehg.apppreferences.SharedPreferenceUtils;
import com.ehg.home.HomeActivity;
import com.ehg.networkrequest.HttpClientRequest;
import com.ehg.networkrequest.HttpClientRequest.ApiResponseListener;
import com.ehg.networkrequest.WebServiceUtil;
import com.ehg.signinsignup.SignInSignupActivity;
import com.ehg.signinsignup.SignInSignupActivity.OnCountryCodeChangeListener;
import com.ehg.signinsignup.pojo.UserProfilePojo;
import com.ehg.utilities.AppUtil;
import com.ehg.utilities.JsonParserUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rilixtech.Country;
import com.rilixtech.CountryCodePicker;
import com.rilixtech.CountryCodePicker.OnCountryChangeListener;
import cz.msebera.android.httpclient.entity.StringEntity;
import java.io.UnsupportedEncodingException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class allows new user to sign up in app as Emaar member.
 */

public class SignUpFragment extends Fragment implements OnClickListener, ApiResponseListener,
    OnEditorActionListener, OnCountryCodeChangeListener {

  private static final String USER_SIGNUP_METHOD = "userSignup";

  private static final String OPERATION = "quickEnrolment";

  private EditText edittextFirstName;
  private EditText edittextLastName;
  private EditText edittextEmail;
  private EditText edittextMobile;
  private EditText edittextPassword;

  private Context context;
  private CountryCodePicker countryCodePicker;
  private TextView textViewWhatIsUbyEmaar;

  private String signinId;

  /**
   * Called when fragment created.
   *
   * @param savedInstanceState bundle
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  /**
   * Attach context to fragment.
   *
   * @param context activity context
   */
  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    this.context = context;
  }

  /**
   * Called to inflate fragment layout.
   *
   * @param inflater layout inflater
   * @param container view group
   * @param savedInstanceState bundle
   * @return returns view object
   */
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_sign_up,
        container, false);

    return view;
  }

  /**
   * Called to instantiate fragment view components.
   *
   * @param view view
   * @param savedInstanceState bundle
   */
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    context = getActivity();
    SignInSignupActivity signInSignupActivity = (SignInSignupActivity) context;
    signInSignupActivity.setOnCountryCodeChangeListener(this);
    initView(view);
  }

  /**
   * Method init's view components of this screen.
   *
   * @param view view object
   */
  private void initView(View view) {

    countryCodePicker = view.findViewById(R.id.countrycodepicker_signup_countrycode);
    countryCodePicker.setOnCountryChangeListener(new OnCountryChangeListener() {
      @Override
      public void onCountrySelected(Country country) {
        SharedPreferenceUtils.getInstance(context)
            .setValue(SharedPreferenceUtils.SELECTED_COUNTRY_CODE,
                Integer.parseInt(country.getPhoneCode().replace("+", "")));
      }
    });

    AppCompatImageView appCompatImageViewLogo = view.findViewById(R.id.imageview_signup_logo);
    appCompatImageViewLogo.getLayoutParams().height = AppUtil.getDeviceHeight(
        (AppCompatActivity) context) / 4;

    edittextFirstName = view.findViewById(R.id.edittext_signup_firstname);
    edittextLastName = view.findViewById(R.id.edittext_signup_lastname);
    edittextEmail = view.findViewById(R.id.edittext_signup_email);
    edittextMobile = view.findViewById(R.id.edittext_signup_mobile);
    edittextPassword = view.findViewById(R.id.edittext_signup_password);

    TextView textViewUbyEmaarAccount = view.findViewById(R.id.textview_signup_ubyemaaraccount);
    textViewUbyEmaarAccount.setText(Html.fromHtml(
        getResources().getString(R.string.signupfragment_sign_up_and_create)
            + "<b> "
            + getResources().getString(R.string.all_u_by_emaar) + "</b> "
            + getResources().getString(R.string.all_account)), TextView.BufferType.SPANNABLE);

    TextView textViewContinueAsGuest = view.findViewById(R.id.textview_signup_continueasguest);
    textViewWhatIsUbyEmaar = view.findViewById(R.id.textview_signup_whatisubyemaar);
    Button buttonSignUp = view.findViewById(R.id.button_sign_up);

    //Set OnClickListener
    buttonSignUp.setOnClickListener(this);
    textViewContinueAsGuest.setOnClickListener(this);
    textViewWhatIsUbyEmaar.setOnClickListener(this);
    view.findViewById(R.id.textview_signup_termsandconditions).setOnClickListener(this);

    //Set EditorCLickListener
    edittextFirstName.setOnEditorActionListener(this);
    edittextLastName.setOnEditorActionListener(this);
    edittextEmail.setOnEditorActionListener(this);
    edittextMobile.setOnEditorActionListener(this);
    edittextPassword.setOnEditorActionListener(this);
  }

  /**
   * Called when mobile phone keyboard keys clicked: enter/done/next keys.
   *
   * @param textView view currently focused
   * @param index index
   * @param keyEvent key event
   * @return returns boolean value
   */
  @Override
  public boolean onEditorAction(TextView textView, int index, KeyEvent keyEvent) {

    boolean cancel = false;

    if (index == EditorInfo.IME_ACTION_DONE) {

      validateSignUpFormFields();

    } else {

      switch (textView.getId()) {
        case R.id.edittext_signup_firstname:
          String firstName = edittextFirstName.getText().toString().trim();
          if (TextUtils.isEmpty(firstName)) {
            edittextFirstName.setError(getResources().getString(R.string.all_fieldrequired));
            cancel = true;

          } else {
            cancel = false;
          }
          break;

        case R.id.edittext_signup_lastname:
          String lastName = edittextLastName.getText().toString().trim();
          if (TextUtils.isEmpty(lastName)) {
            edittextLastName.setError(getResources().getString(R.string.all_fieldrequired));
            cancel = true;
          } else {
            cancel = false;
          }
          break;

        case R.id.edittext_signup_email:
          String email = edittextEmail.getText().toString().trim();
          if (TextUtils.isEmpty(email)) {
            edittextEmail.setError(getResources().getString(R.string.all_fieldrequired));
            cancel = true;
          } else if (!AppUtil.isValidEmail(email)) {
            edittextEmail.setError(getResources().getString(R.string.all_invalidemail));
            cancel = true;
          } else {
            cancel = false;
          }
          break;

        case R.id.edittext_signup_mobile:
          String mobile = edittextMobile.getText().toString().trim();
          if (TextUtils.isEmpty(mobile)) {
            edittextMobile.setError(getResources().getString(R.string.all_fieldrequired));
            cancel = true;
          } else if (!AppUtil.isValidMobile(mobile)) {
            edittextMobile.setError(getResources().getString(R.string.all_invalidmobile));
            cancel = true;
          } else {
            cancel = false;
          }
          break;
        case R.id.edittext_signup_password:
          String password = edittextPassword.getText().toString().trim();
          if (TextUtils.isEmpty(password)) {
            edittextPassword.setError(getResources().getString(R.string.all_fieldrequired));
            cancel = true;
          } else if (!AppUtil.isPasswordValid(password)) {
            edittextPassword.setError(getResources().getString(R.string.all_passwordlength));
            cancel = true;
          } else {
            cancel = false;
          }
          break;

        default:
          break;
      }
    }
    return cancel;
  }

  /**
   * Called on view clicked.
   *
   * @param view view
   */
  @Override
  public void onClick(View view) {
    Intent intent;
    switch (view.getId()) {

      case R.id.button_sign_up:
        validateSignUpFormFields();
        break;

      case R.id.textview_signup_continueasguest:
        intent = new Intent(context, HomeActivity.class);
        AppUtil.startActivityWithAnimation((AppCompatActivity) context, intent, true);
        break;

      case R.id.textview_signup_whatisubyemaar:
        AppUtil.loadWebView((AppCompatActivity) context,
            textViewWhatIsUbyEmaar.getText().toString(), "", false);
        break;

      case R.id.textview_signup_termsandconditions:
        AppUtil.loadWebView((AppCompatActivity) context,
            getResources().getString(R.string.settings_termsandconditions),
            AppUtil.TERMS_AND_CONDITIONS_URL, false);
        break;

      default:
        break;
    }
  }

  /**
   * Called when parent activity resumed.
   */
  @Override
  public void onResume() {
    super.onResume();
    resetErrors();
  }

  /**
   * Called to reset errors on form field.
   */
  private void resetErrors() {
    try {
      //Reset errors
      edittextFirstName.setError(null);
      edittextLastName.setError(null);
      edittextEmail.setError(null);
      edittextMobile.setError(null);
      edittextPassword.setError(null);
    } catch (NullPointerException n) {
      n.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Method validates sign up form fields.
   */
  private void validateSignUpFormFields() {

    resetErrors();

    boolean cancel = false;
    View focusView = null;

    String firstName = edittextFirstName.getText().toString();
    String lastName = edittextLastName.getText().toString();
    String email = edittextEmail.getText().toString();
    String mobile = edittextMobile.getText().toString();
    String password = edittextPassword.getText().toString().trim();

    if (firstName.contains(" ")) {
      edittextFirstName.setText(firstName.trim());
      edittextFirstName.setError(getResources().getString(R.string.all_spacesnotallowed));
      focusView = edittextFirstName;
      cancel = true;

    } else if (lastName.contains(" ")) {
      edittextLastName.setText(firstName.trim());
      edittextLastName.setError(getResources().getString(R.string.all_spacesnotallowed));
      focusView = edittextLastName;
      cancel = true;

    } else if (email.contains(" ")) {
      edittextEmail.setText(email.trim());
      edittextEmail.setError(getResources().getString(R.string.all_spacesnotallowed));
      focusView = edittextEmail;
      cancel = true;

    } else if (mobile.contains(" ")) {
      edittextMobile.setText(mobile.trim());
      edittextMobile.setError(getResources().getString(R.string.all_spacesnotallowed));
      focusView = edittextMobile;
      cancel = true;

    } else if (password.contains(" ")) {
      edittextPassword.setText(password.trim());
      edittextPassword.setError(getResources().getString(R.string.all_spacesnotallowed));
      focusView = edittextPassword;
      cancel = true;

    } else if (TextUtils.isEmpty(firstName)) {

      edittextFirstName.setError(getResources().getString(R.string.all_fieldrequired));
      focusView = edittextFirstName;
      cancel = true;

    } else if (TextUtils.isEmpty(lastName)) {

      edittextLastName.setError(getResources().getString(R.string.all_fieldrequired));
      focusView = edittextLastName;
      cancel = true;

    } else if (TextUtils.isEmpty(email)) {

      edittextEmail.setError(getResources().getString(R.string.all_fieldrequired));
      focusView = edittextEmail;
      cancel = true;

    } else if (!AppUtil.isValidEmail(email)) {

      edittextEmail.setError(getResources().getString(R.string.all_invalidemail));
      focusView = edittextEmail;
      cancel = true;

    } else if (TextUtils.isEmpty(mobile)) {

      edittextMobile.setError(getResources().getString(R.string.all_fieldrequired));
      focusView = edittextMobile;
      cancel = true;

    } else if (!AppUtil.isValidMobile(mobile)) {

      edittextMobile.setError(getResources().getString(R.string.all_invalidmobile));
      focusView = edittextMobile;
      cancel = true;

    } else if (TextUtils.isEmpty(password)) {

      edittextPassword.setError(getResources().getString(R.string.all_fieldrequired));
      focusView = edittextPassword;
      cancel = true;

    } else if (password.length() < 8) {

      edittextPassword.setError(getResources().getString(R.string.all_passwordlength));
      focusView = edittextPassword;
      cancel = true;

    } else if (!AppUtil.isPasswordValid(password)) {

      edittextPassword.setError(getResources().getString(R.string.all_passwordsuggestion));
      focusView = edittextPassword;
      cancel = true;

    }

    if (cancel) {

      focusView.requestFocus();

    } else {

      userSignup(email, mobile, firstName, lastName, password);
    }
  }

  //****************************** API CALLING STUFF ******************************************

  /**
   * Method registers user at Emaar cloud.
   */
  private void userSignup(String emailId, String mobileNumber, String firstName,
      String lastName, String password) {

    if (AppUtil.isNetworkAvailable(context)) {

      new HttpClientRequest().setApiResponseListner(this);

      JSONObject jsonObject = new JSONObject();
      JSONArray detailsArray = new JSONArray();
      JSONObject detailObject = new JSONObject();

      try {
        detailObject.put("emailId", emailId.trim());
        signinId = "00" + countryCodePicker.getSelectedCountryCode() + mobileNumber.trim();
        detailObject.put("mobileNumber", signinId);
        detailObject.put("lastName", lastName.trim());
        detailObject.put("firstName", firstName.trim());
        detailObject.put("password", password.trim());

        JSONObject deviceDetailObject = new JSONObject();
        deviceDetailObject.put("deviceType", WebServiceUtil.DEVICE_TYPE);
        deviceDetailObject.put("deviceId", AppUtil.getDeviceId(context));
        deviceDetailObject.put("fcmToken",
            SharedPreferenceUtils.getInstance(context)
                .getStringValue(SharedPreferenceUtils.FCM_TOKEN, ""));

        detailObject.put("deviceDetails", deviceDetailObject);

        detailsArray.put(detailObject);

        jsonObject.put("details", detailsArray);
        jsonObject.put("operation", OPERATION);
        jsonObject.put("feature", WebServiceUtil.FEATURE_SIGN_UP);

      } catch (JSONException e) {
        e.printStackTrace();
      }

      StringEntity entity = null;
      try {
        entity = new StringEntity(jsonObject.toString());
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }

      new HttpClientRequest(context, WebServiceUtil.getUrl(WebServiceUtil.METHOD_SIGN_UP),
          entity, WebServiceUtil.CONTENT_TYPE,
          USER_SIGNUP_METHOD, true).httpPostRequest();
    } else {
      AppUtil.showAlertDialog((AppCompatActivity) context,
          context.getResources().getString(R.string.all_please_check_network_settings),
          false, "", true, null);
    }
  }

  /**
   * Called when response received from api call.
   *
   * @param responseVal response
   * @param requestMethod request method name
   */
  @Override
  public void onSuccessResponse(String responseVal, String requestMethod) {
    try {
      if (requestMethod.equalsIgnoreCase(USER_SIGNUP_METHOD)
          && responseVal != null && !responseVal.equalsIgnoreCase("")
          && !responseVal.startsWith("<") && new JSONObject(responseVal).getBoolean("status")) {

        UserProfilePojo userProfilePojo = new Gson().fromJson(responseVal,
            new TypeToken<UserProfilePojo>() {
            }.getType());

        if (userProfilePojo != null && userProfilePojo.getStatus()) {

          SharedPreferenceUtils.getInstance(context)
              .setValue(SharedPreferenceUtils.SELECTED_COUNTRY_CODE,
                  Integer.parseInt(countryCodePicker.getSelectedCountryCode()));

          JsonParserUtil.getInstance(context).setUserProfilePojo(userProfilePojo);
          //Save loyaltyMEmberId
          SharedPreferenceUtils.getInstance(context)
              .setValue(SharedPreferenceUtils.LOYALTY_MEMBER_ID,
                  userProfilePojo.getData().getDetail().get(0).getLoyaltyMemberId() + "");
          //Save mobile number as accoundId
          SharedPreferenceUtils.getInstance(context)
              .setValue(SharedPreferenceUtils.ACCOUNT_ID, signinId + "");

          Intent intent = new Intent(context, HomeActivity.class);
          AppUtil.showAlertDialog((AppCompatActivity) context,
              userProfilePojo.getMessage(), true,
              getResources().getString(R.string.dialog_alerttitle), false, intent);
        }
      } else if (responseVal != null && !responseVal.equalsIgnoreCase("")
          && !responseVal.startsWith("<") && !new JSONObject(responseVal).getBoolean("status")) {

        JSONObject dataObject = new JSONObject(responseVal).getJSONObject("data");

        if (dataObject != null) {
          JSONArray detailArray = dataObject.optJSONArray("detail");
          if (detailArray != null && detailArray.length() > 0) {
            JSONObject validationError = detailArray.optJSONObject(0)
                .optJSONArray("validationErrors").optJSONObject(0);

            AppUtil.showAlertDialog((AppCompatActivity) context,
                validationError.getString("ErrorMessage"), false,
                getResources().getString(R.string.dialog_errortitle), true, null);
          }
        }
      } else {
        AppUtil.showAlertDialog((AppCompatActivity) context,
            new JSONObject(responseVal).getString("message"), false,
            getResources().getString(R.string.dialog_errortitle), true, null);
      }
    } catch (JSONException e) {
      e.printStackTrace();
    } catch (IndexOutOfBoundsException e) {
      e.printStackTrace();
    } catch (NullPointerException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Called on failure api response.
   *
   * @param errorMessage error string
   */
  @Override
  public void onFailureResponse(String errorMessage) {
    AppUtil.showAlertDialog((AppCompatActivity) context, errorMessage, false,
        getResources().getString(R.string.dialog_errortitle), true, null);
  }

  /**
   * Called when country code changed.
   */
  @Override
  public void onCountryCodeChanged() {
    if (countryCodePicker != null) {
      countryCodePicker.setCountryForPhoneCode(SharedPreferenceUtils.getInstance(context)
          .getIntValue(SharedPreferenceUtils.SELECTED_COUNTRY_CODE, 971));
    }
  }

  /**
   * Called to clear fields.
   */
  public void clearFields() {
    if (edittextFirstName != null && edittextLastName != null && edittextEmail != null
        && edittextMobile != null && edittextPassword != null) {
      edittextFirstName.setText("");
      edittextLastName.setText("");
      edittextEmail.setText("");
      edittextMobile.setText("");
      edittextPassword.setText("");
      resetErrors();
    }
  }
}
