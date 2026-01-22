package com.nuvei.nuvei_sdk.widget;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.nuvei.nuvei_sdk.NuveiWebViewActivity;
import com.nuvei.nuvei_sdk.R;
import com.nuvei.nuvei_sdk.helper.CardHelper;
import com.nuvei.nuvei_sdk.helper.GlobalHelper;
import com.nuvei.nuvei_sdk.helper.NuveiCallBack;
import com.nuvei.nuvei_sdk.helper.ThreeDsHandler;
import com.nuvei.nuvei_sdk.internal.NuveiSDK;
import com.nuvei.nuvei_sdk.listener.AddCardListener;
import com.nuvei.nuvei_sdk.models.Environment;
import com.nuvei.nuvei_sdk.models.addCard.AddCardRequest;
import com.nuvei.nuvei_sdk.models.addCard.AddCardResponse;
import com.nuvei.nuvei_sdk.models.addCard.BrowserInfo;
import com.nuvei.nuvei_sdk.models.addCard.BrowserResponse;
import com.nuvei.nuvei_sdk.models.addCard.CardInfoModel;
import com.nuvei.nuvei_sdk.models.addCard.CardModel;
import com.nuvei.nuvei_sdk.models.addCard.ExtraParams;
import com.nuvei.nuvei_sdk.models.addCard.ThreeDS2Data;
import com.nuvei.nuvei_sdk.models.cres.CresConfirmRequest;
import com.nuvei.nuvei_sdk.models.cres.CresConsultingResponse;
import com.nuvei.nuvei_sdk.models.cres.CresDataResponse;
import com.nuvei.nuvei_sdk.models.cres.CresLoginRequest;
import com.nuvei.nuvei_sdk.models.cres.CresLoginResponse;
import com.nuvei.nuvei_sdk.models.debit.UserDebit;
import com.nuvei.nuvei_sdk.models.deleteCard.UserIdDelete;
import com.nuvei.nuvei_sdk.models.error.ErrorData;
import com.nuvei.nuvei_sdk.models.error.ErrorResponseModel;
import com.nuvei.nuvei_sdk.models.listCard.ListCardResponse;
import com.nuvei.nuvei_sdk.models.verify.OtpRequest;
import com.nuvei.nuvei_sdk.models.verify.OtpResponse;
import com.nuvei.nuvei_sdk.models.verify.TransactionRequest;
import com.nuvei.nuvei_sdk.network.CresClient;
import com.nuvei.nuvei_sdk.network.CresService;
import com.nuvei.nuvei_sdk.network.NuveiClient;
import com.nuvei.nuvei_sdk.network.NuveiService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NuveiAddCardForm extends LinearLayout {
    private String userId, email;

    //FORM ADD CARD
    private TextInputEditText numberCardTextInput, holderNameTextInput, expiryDateTextInput, cvcCodeTextInput, otpCodeTextInput;
    private TextInputLayout numberCardInputLayout, holderNameInputLayout, expiryDateInputLayout, cvcCodeInputLayout, otpCodeInputLayout;

    //CARD SIMULATION
    //Flip simulation
    private CardView cardFrontView, cardBackView;
    private ConstraintLayout cardFrontLayout, cardBackLayout;
    private boolean isFront = true;

    //Data simulation
    private ImageView cardImageLogo;
    private TextView numberCardTV, nameHolderTV, expiryDateTV, cvcValueTV;


    private AddCardListener listener;
    private Context context;


    private MaterialButton addCardButton;


    private boolean isOtpActive = false;

    private LinearLayout otpLayout;
    private boolean isOtpValid = true;


    private String transactionId = "";
    private String tokenCres = "";
    private String referenceCresId = "";

    public NuveiAddCardForm(Context context, String userId, String email) {
        super(context);
        this.context = context;
        this.userId = userId;
        this.email = email;
        init();
    }

    public NuveiAddCardForm(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public NuveiAddCardForm(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    public NuveiAddCardForm(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;

        init();
    }

    public void setUserData(String userId, String email){
        this.userId = userId;
        this.email = email;
    }

    public void init() {
        LayoutInflater.from(context).inflate(R.layout.form_add_card_layout, this, true);


        // CARD FRONT & BACK
        cardFrontView = findViewById(R.id.include_card_front);
        cardBackView = findViewById(R.id.include_card_back);
        cardFrontLayout = cardFrontView.findViewById(R.id.card_front_layout);
        cardBackLayout = cardBackView.findViewById(R.id.card_back_layout);
        float scale = context.getResources().getDisplayMetrics().density;
        cardFrontView.setCameraDistance(8000 * scale);
        cardBackView.setCameraDistance(8000 * scale);
        cardBackView.setRotationY(180f);

        //CARD DESGIN ELEMENTS
        cardImageLogo = cardFrontView.findViewById(R.id.card_image);
        numberCardTV = cardFrontView.findViewById(R.id.tv_card_number);
        nameHolderTV = cardFrontView.findViewById(R.id.tv_name_value);
        expiryDateTV = cardFrontView.findViewById(R.id.tv_date_value);
        cvcValueTV = cardBackView.findViewById(R.id.tv_cvc_value);


        //TEXT INPUT INITIALIZE
        numberCardTextInput = findViewById(R.id.card_number_input);
        holderNameTextInput = findViewById(R.id.holder_name_input);
        expiryDateTextInput = findViewById(R.id.expiry_date_input);
        cvcCodeTextInput = findViewById(R.id.cvv_input);
        otpCodeTextInput = findViewById(R.id.otp_input);

        //TEXT LAYOUT INITIALIZE
        numberCardInputLayout = findViewById(R.id.card_number_input_layout);
        holderNameInputLayout = findViewById(R.id.holder_name_input_layout);
        expiryDateInputLayout = findViewById(R.id.expiry_date_input_layout);
        cvcCodeInputLayout = findViewById(R.id.cvv_input_layout);
        otpCodeInputLayout = findViewById(R.id.otp_input_layout);
        otpLayout = findViewById(R.id.otp_form);
        addCardButton = findViewById(R.id.button_add_card);

        addCardButton.setOnClickListener(view ->
                {

                        if(isOtpActive){
                            verify("BY_OTP", otpCodeTextInput.getText().toString(), transactionId);
                        }else {
                            addCardProcess();
                       }
                }
        );
        //FORM LISTENERS
        cvcCodeTextInput.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                flipCard();
            }
        });


        //------------------------ TEXT FORM ACTIONS --------------------------------

        numberCardTextInput.addTextChangedListener(new TextWatcher() {
            private String current = "";

            @Override
            public void afterTextChanged(Editable editable) {

            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().equals(current)) {
                    numberCardTextInput.removeTextChangedListener(this);
                    String raw = charSequence.toString().replaceAll("\\D", "");
                    String formatted = CardHelper.formatCardNumber(raw);
                    CardInfoModel cardInfoModel = CardHelper.getCardInfo(raw);
                    cardImageLogo.setImageDrawable(ContextCompat.getDrawable(context, cardInfoModel.getIconRes()));

                    cardFrontLayout.setBackground(new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, cardInfoModel.getGradientColor()));

                    cardBackLayout.setBackground(new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, cardInfoModel.getGradientColor()));

                    InputFilter[] fArray = new InputFilter[1];
                    fArray[0] = new InputFilter.LengthFilter(cardInfoModel.getCvcNumber());
                    cvcCodeTextInput.setFilters(fArray);
                    current = formatted;
                   // Log.v("format", formatted);
                    numberCardTextInput.setText(formatted);
                    numberCardTextInput.setSelection(formatted.length());
                    numberCardTextInput.addTextChangedListener(this);
                    if (formatted.isEmpty()) {
                        numberCardTV.setText("**** **** **** ****");
                    } else {
                        numberCardTV.setText(formatted);
                    }

                }


            }
        });


        holderNameTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {

            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                holderNameTextInput.removeTextChangedListener(this);
                if(charSequence.toString().isEmpty()){
                    nameHolderTV.setText("JHON DOE");
                }else{
                    nameHolderTV.setText(charSequence.toString());
                }
                holderNameTextInput.addTextChangedListener(this);
            }
        });


        expiryDateTextInput.addTextChangedListener(new TextWatcher() {
            private String previous = "";
            @Override
            public void afterTextChanged(Editable editable) {

            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String formatted = CardHelper.formatExpiryInput(charSequence.toString());


                expiryDateTextInput.removeTextChangedListener(this);
                expiryDateTextInput.setText(formatted);
                expiryDateTextInput.setSelection(formatted.length());
                expiryDateTextInput.addTextChangedListener(this);
                expiryDateTV.setText(formatted.isEmpty() ? "MM/YY" : formatted);
            }
        });


        cvcCodeTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String cvc = s.toString().trim();
                cvcValueTV.setText(cvc.isEmpty() ? "***" : cvc);
            }
        });
    }


    //------------------------ CARD SIMULATION FLIP --------------------------------
    private void flipCard() {
        float scale = getResources().getDisplayMetrics().density;
        cardFrontView.setCameraDistance(scale * 8000);
        cardBackView.setCameraDistance(scale * 8000);

        AnimatorSet setOut = (AnimatorSet) AnimatorInflater.loadAnimator(this.context, R.animator.card_flip_out);
        AnimatorSet setIn = (AnimatorSet) AnimatorInflater.loadAnimator(this.context, R.animator.card_flip_in);

        setOut.setTarget(isFront ? cardFrontView : cardBackView);
        setIn.setTarget(isFront ? cardBackView : cardFrontView);

        setOut.start();
        setIn.start();

        isFront = !isFront;

    }


    public void setFormListener(AddCardListener listener) {
        this.listener = listener;
    }


    private void addCardProcess() {
        numberCardInputLayout.setError(null);
        numberCardInputLayout.setErrorEnabled(false);
        holderNameInputLayout.setError(null);
        holderNameInputLayout.setErrorEnabled(false);
        expiryDateInputLayout.setError(null);
        cvcCodeInputLayout.setErrorEnabled(false);
        cvcCodeInputLayout.setError(null);

        String cardNumber = numberCardTextInput.getText().toString().replaceAll("\\D", "");
        String expiry = expiryDateTextInput.getText().toString();
        String holderName = holderNameTextInput.getText().toString().trim();
        String cvc = cvcCodeTextInput.getText().toString().trim();


        boolean hasError = false;
        if (cardNumber.isEmpty() || !CardHelper.validLuhnNumber(cardNumber)) {
            numberCardInputLayout.setErrorEnabled(true);
            numberCardInputLayout.setError("Número de tarjeta inválido");
            hasError = true;
        }

        if (holderName.isEmpty()) {
            holderNameInputLayout.setErrorEnabled(true);
            holderNameInputLayout.setError("Nombre del titular es requerido");
            hasError = true;
        }
        String expiryError = CardHelper.validateExpiryDate(expiry);
        if (expiryError != null) {
            expiryDateInputLayout.setErrorEnabled(true);
            expiryDateInputLayout.setError(expiryError);
            hasError = true;
        }

        if (cvc.isEmpty() || cvc.length() < 3) {
            cvcCodeInputLayout.setErrorEnabled(true);
            cvcCodeInputLayout.setError("CVC inválido");
            hasError = true;
        }

        if (hasError) return;
        if (listener != null) listener.onLoading(true);
        if (NuveiSDK.getInstance().getEnvironment().getClientId().isEmpty()) {
            addCardRequest("");
        } else {


        CresClient clientCress = new CresClient();
        CresService serviceCres = clientCress.getClient("").create(CresService.class);

        Call<CresLoginResponse> callCres = serviceCres.loginCres(new CresLoginRequest(
                        NuveiSDK.getInstance().getEnvironment().getClientId(),
                        NuveiSDK.getInstance().getEnvironment().getClientCode()
                )
        );


        callCres.enqueue(new Callback<CresLoginResponse>() {
            @Override
            public void onResponse(Call<CresLoginResponse> call, Response<CresLoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    tokenCres = response.body().getAccessToken();
                    createReference(tokenCres);
                } else {
                    if (listener != null) listener.onLoading(false);
                    ErrorResponseModel error = parseError(response);
                    if (listener != null) listener.onError(error);
                }
            }

            @Override
            public void onFailure(Call<CresLoginResponse> call, Throwable t) {
                if (listener != null) listener.onLoading(false);
                ErrorResponseModel error = new ErrorResponseModel(
                        new ErrorData("NetworkError", "", t.getMessage())
                );
                if (listener != null) listener.onError(error);
            }
        });


    }

    }


    private void createReference(String token){
        CresClient clientCress = new CresClient();
        CresService serviceCres = clientCress.getClient(token).create(CresService.class);

        Call<CresConsultingResponse> callCres =  serviceCres.createReferenceCres();

        callCres.enqueue(new Callback<CresConsultingResponse>() {
            @Override
            public void onResponse(Call<CresConsultingResponse> call, Response<CresConsultingResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    referenceCresId = response.body().getId();
                    addCardRequest(referenceCresId);
                }else{
                    if(listener != null) listener.onLoading(false);
                    ErrorResponseModel error = parseError(response);
                    if(listener != null) listener.onError(error);
                }
            }

            @Override
            public void onFailure(Call<CresConsultingResponse> call, Throwable t) {
                if (listener != null) listener.onLoading(false);
                ErrorResponseModel error = new ErrorResponseModel(
                        new ErrorData("NetworkError", "", t.getMessage())
                );
                if(listener != null)listener.onError(error);
            }
        });

    }

    private void addCardRequest(String referenceId){
        Environment env = NuveiSDK.getInstance().getEnvironment();
        String baseUrl = "https://cres.nuvei.com.ec";
        String[] expiryDate = expiryDateTextInput.getText().toString().split("/");
        int expiryMonth = Integer.parseInt(expiryDate[0]);
        int expiryYear = CardHelper.completeYear(Integer.parseInt(expiryDate[1]));
        String cleanNumber = numberCardTextInput.getText().toString().replaceAll("\\D", "");
        UserDebit user = new UserDebit(userId, email);
        CardModel card = new CardModel(cleanNumber, holderNameTextInput.getText().toString(), expiryMonth, expiryYear, cvcCodeTextInput.getText().toString(), CardHelper.getCardInfo(cleanNumber).getTypeCode());
        ThreeDS2Data threeDS2Data = new ThreeDS2Data(baseUrl+ "api/cres/save/"+referenceId, "browser");
        BrowserInfo browserInfo = GlobalHelper.getBrowserInfo(context);
        ExtraParams extraParams = new ExtraParams(threeDS2Data, browserInfo);
        AddCardRequest addCardRequest = new AddCardRequest(user, card, extraParams);
       // Log.v("code", NuveiSDK.getInstance().getEnvironment().getAppCode());
        NuveiClient client = new NuveiClient(NuveiSDK.getInstance().getEnvironment().getAppCode(),NuveiSDK.getInstance().getEnvironment().getAppKey());
        NuveiService service = client.getService();
        Call<AddCardResponse> call = service.addCard(addCardRequest);

        call.enqueue(new Callback<AddCardResponse>() {
            @Override
            public void onResponse(Call<AddCardResponse> call, Response<AddCardResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    handleAddCardResponse(response.body());
                }else{
                    if(listener != null) listener.onLoading(false);
                    ErrorResponseModel error = parseError(response);
                    if(listener != null) listener.onError(error);
                }

            }

            @Override
            public void onFailure(Call<AddCardResponse> call, Throwable t) {
                if (listener != null) listener.onLoading(false);
                ErrorResponseModel error = new ErrorResponseModel(
                        new ErrorData("NetworkError", "", t.getMessage())
                );
                if(listener != null)listener.onError(error);
            }
        });
    }


    private void handleAddCardResponse(AddCardResponse response){

        if (response.getCard() == null) {
            if (listener != null) listener.onError(new ErrorResponseModel(new ErrorData("", "", "")));
            if(listener != null) listener.onLoading(false);
        }else{
            transactionId = response.getCard().getTransactionReference();
            Integer status = response.getTransaction().getStatusDetail();

            switch (status){
                case 7:
                    if (listener != null) listener.onLoading(false);
                    if(response.getCard().getStatus().equals("valid")){
                        if (listener != null)  listener.onSucces(true, "Card Added Succesfully");
                    }else{
                        if(listener != null)listener.onSucces(false, "Card rejected");
                    }

                    clearForm();
                    break;
                case 9:
                    if (listener != null) {
                        listener.onLoading(false);
                        listener.onSucces(false, "Card rejected");
                    }
                    clearForm();
                    break;
                case 31:
                   if(listener != null) listener.onLoading(false);
                    isOtpActive = true;
                    otpLayout.setVisibility(View.VISIBLE);
                    String text = getContext().getString(R.string.verify_otp);
                    addCardButton.setText(text);
                    break;
                case 35:
                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                        verify("AUTHENTICATION_CONTINUE", "", transactionId);
                    }, 5000);
                    break;
                case 36:
                    verify3dsChallenge(response.getThe3Ds().getBrowserResponse());
                    break;
                default:
                    if(listener != null) listener.onLoading(false);
                    if (listener != null) listener.onError(new ErrorResponseModel(new ErrorData("", "", "Error to add card")));
                    break;

            }
        }
    }

    private  void verify(String type, String value, String transactionId){
        if(!type.equals("AUTHENTICATION_CONTINUE")){
            if(listener != null) listener.onLoading(true);
        }

        if(type.equals("BY_OTP")){
            otpCodeInputLayout.setErrorEnabled(false);
            otpCodeInputLayout.setError(null);
            if(otpCodeTextInput.getText().length() < 6){
                otpCodeInputLayout.setErrorEnabled(true);
                otpCodeInputLayout.setError("Otp code not valid");
                return;
            }
            if(otpCodeTextInput.getText().toString().isEmpty()){
                otpCodeInputLayout.setErrorEnabled(true);
                otpCodeInputLayout.setError("Otp code not valid");
                return;
            }
        }

        OtpRequest otpRequest = new OtpRequest(
                new UserIdDelete(userId), new TransactionRequest(transactionId),
                type,
                value,true
        );

        verify(otpRequest, new NuveiCallBack<OtpResponse>() {
            @Override
            public void onSucces(OtpResponse data) {
                if (listener != null) listener.onLoading(false);
                handleVerify(data, type);
            }

            @Override
            public void onError(ErrorResponseModel error) {
                if (listener != null) listener.onLoading(false);
                if(listener != null)listener.onError(error);

            }
        });

    }


    private void handleVerify(OtpResponse response, String type){
        if(listener != null) listener.onLoading(false);

        switch (type){
            case "BY_OTP":
                switch (response.getTransactionOtpresponse().getStatus_detail()){
                    case 31:
                        otpCodeTextInput.setText("");
                        isOtpValid = false;
                        break;
                    case 32:
                        isOtpActive = false;
                        otpLayout.setVisibility(GONE);
                        clearForm();
                        if (listener != null)   listener.onSucces(true, "Card Added Succesfully");
                        break;
                    case 33:
                        clearForm();

                        if(listener != null) listener.onError(new ErrorResponseModel(
                                new ErrorData(
                                        "Error", "", response.getTransactionOtpresponse().getMessage()
                                )
                        ));
                        break;
                    default:
                        if(listener != null) listener.onError(new ErrorResponseModel(
                                new ErrorData(
                                        "Error", "", String.valueOf(response.getTransactionOtpresponse().getStatus_detail())
                                )
                        ));
                        break;
                }
                break;

            case "AUTHENTICATION_CONTINUE":
                switch (response.getTransactionOtpresponse().getStatus()){
                    case "success":
                        clearForm();
                        if (listener != null)   listener.onSucces(true, "Card Added Succesfully");
                        break;
                    case "pending":
                        verify3dsChallenge(response.getThe3dsResponse().getBrowserResponse());
                        break;
                    case "failure":
                        if(listener != null) listener.onSucces(false, "Card rejected");
                        clearForm();
                        break;
                    default:
                        if(listener != null) listener.onError(new ErrorResponseModel(
                                new ErrorData(
                                        "Error", "", String.valueOf(response.getTransactionOtpresponse().getStatus_detail())
                                )
                        ));
                        break;
                }
                break;

            case "BY_CRES":
                switch (response.getTransactionOtpresponse().getStatus()){
                    case "success":
                        clearForm();
                        if (listener != null)   listener.onSucces(true, "Card Added Succesfully");
                        break;
                    case "failure":
                        if(listener != null) listener.onSucces(false, "Card rejected");
                        clearForm();
                        break;
                    default:
                        if(listener != null) listener.onError(new ErrorResponseModel(
                                new ErrorData(
                                        "Error", "", String.valueOf(response.getTransactionOtpresponse().getStatus_detail())
                                )
                        ));
                        break;
                }


        }

    }


    private void  verify3dsChallenge(BrowserResponse browserResponse){

        if( !browserResponse.getChallengeRequest().isEmpty()){
            String html = browserResponse.getChallengeRequest();


            ThreeDsHandler handler = new ThreeDsHandler();

            if (context != null) {
                Intent intent = new Intent(context, NuveiWebViewActivity.class);
                intent.putExtra(NuveiWebViewActivity.EXTRA_HTML, html);

                if (!(context instanceof Activity)) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }

                context.startActivity(intent);

                CresClient client = new CresClient();
                CresService serviceCres = client.getClient(tokenCres).create(CresService.class);

                handler.startPolling(serviceCres, tokenCres, referenceCresId, cres -> {
                    handler.stopPolling();

                    NuveiWebViewActivity.closeIfOpen();

                    confirmCresAndVerifyOtp(cres);
                });
            }
        }
    }

    private void confirmCresAndVerifyOtp(String cres){
        CresClient client = new CresClient();
        CresService serviceCres = client.getClient(tokenCres).create(CresService.class);
        Call<CresDataResponse> callCres =  serviceCres.confirmCres(new CresConfirmRequest(referenceCresId));
        callCres.enqueue(new Callback<CresDataResponse>() {
            @Override
            public void onResponse(Call<CresDataResponse> call, Response<CresDataResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                   verify("BY_CRES",cres, transactionId);
                }else{
                    if(listener != null) listener.onLoading(false);
                    ErrorResponseModel error = parseError(response);
                    if(listener != null) listener.onError(error);
                }
            }

            @Override
            public void onFailure(Call<CresDataResponse> call, Throwable t) {
                if (listener != null) listener.onLoading(false);
                ErrorResponseModel error = new ErrorResponseModel(
                        new ErrorData("NetworkError", "", t.getMessage())
                );
                if(listener != null)listener.onError(error);
            }
        });

    }



    private   void verify(OtpRequest otpRequest, NuveiCallBack callBack){
        NuveiClient client = new NuveiClient(NuveiSDK.getInstance(). getEnvironment().getServerCode(),NuveiSDK.getInstance(). getEnvironment().getServerKey());
        NuveiService service = client.getService();

        Call<OtpResponse> call = service.verify(otpRequest);

        call.enqueue(new Callback<OtpResponse>() {
            @Override
            public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    callBack.onSucces(response.body());
                }else{
                    ErrorResponseModel error = parseError(response);
                    callBack.onError(error);
                }
            }

            @Override
            public void onFailure(Call<OtpResponse> call, Throwable t) {
                ErrorResponseModel error = new ErrorResponseModel(
                        new ErrorData("NetworkError", "", t.getMessage())
                );
               // Log.v("Error on delete Card", error.getError().getDescription() );
                callBack.onError(error);
            }
        });
    }

    private void clearForm(){
        numberCardTextInput.setText("");
        holderNameTextInput.setText("");
        expiryDateTextInput.setText("");
        cvcCodeTextInput.setText("");
    }

    private ErrorResponseModel parseError(Response<?> response) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(response.errorBody().string(), ErrorResponseModel.class);
        } catch (Exception e) {
            return new ErrorResponseModel(
                    new ErrorData("Unknown Error", "", "Error parsing response: " + e.getMessage())
            );
        }
    }



}
