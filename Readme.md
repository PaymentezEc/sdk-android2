# Nuvei SDK Android

SDK oficial de **Nuvei para Android** que permite:

- Inicializar entorno (**sandbox / producción**)
- Listar tarjetas
- Eliminar tarjetas
- Pagos débito
- Reembolsos
- Agregar tarjetas mediante **UI embebida**

---

## 📥 Instalación en el proyecto Android

### 1️⃣ Agregar JitPack al proyecto

**settings.gradle**
```gradle
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url "https://jitpack.io" }
    }
}
```
### 2️⃣ Agregar dependencia
```code
dependencies {
    implementation "com.github.TU_USUARIO:nuvei-sdk-android:v1.0.0"
}
```


## ⚙️ Inicialización del SDK

### ⚠️ Obligatorio inicializar antes de usar cualquier endpoint.


```code
NuveiSDK.getInstance().initEnvironment(
    Constants.APPCODE,
    Constants.APPKEY,
    Constants.SERVERCODE,
    Constants.SERVERKEY,
    Constants.CLIENTID,
    Constants.CLIENTCODE,
    true // testMode
);

```

### 🧩 Clase principal: NuveiSDK
```code
public class NuveiSDK {

    private static NuveiSDK instance;
    private final NuveiSDKRepository nuveiSDKRepository;

    public NuveiSDK() {
        this.nuveiSDKRepository = new NuveiSDKRepository();
    }

    public static synchronized NuveiSDK getInstance() {
        if (instance == null) {
            instance = new NuveiSDK();
        }
        return instance;
    }

    public void initEnvironment(
        String appCode,
        String appKey,
        String serverCode,
        String serverKey,
        String clientId,
        String clientCode,
        boolean testMode
    ) {
        nuveiSDKRepository.initEnvironment(
            appCode,
            appKey,
            serverCode,
            serverKey,
            clientId,
            clientCode,
            testMode
        );
    }

    public Environment getEnvironment() {
        return nuveiSDKRepository.getEnvironment();
    }

    public void getAllCard(
        String userId,
        NuveiCallBack<ListCardResponse> callBack
    ) {
        nuveiSDKRepository.listCards(userId, callBack);
    }

    public void deleteCard(
        String userId,
        String cardToken,
        NuveiCallBack<DeleteCardResponse> callBack
    ) {
        nuveiSDKRepository.deleteCard(userId, cardToken, callBack);
    }

    public void paymentDebit(
        DebitRequest debitRequest,
        NuveiCallBack<DebitResponse> callBack
    ) {
        nuveiSDKRepository.paymentDebit(debitRequest, callBack);
    }

    public void refundDebit(
        TransactionRefund transactionRefund,
        OrderRefund orderRefund,
        boolean moreInfo,
        NuveiCallBack<RefundResponse> callBack
    ) {
        nuveiSDKRepository.refundDebit(
            transactionRefund,
            orderRefund,
            moreInfo,
            callBack
        );
    }
}

```

### 💳 Pago Débito (Ejemplo completo)
```code
public void processmentPay(String token) {

    showLoading(true);

    UserDebit user = new UserDebit(
        "4",
        "rruiz@viamatica.com"
    );

    DebitOrder debit = new DebitOrder(
        10.0,
        "payment",
        "",
        0.0,
        0,
        0
    );

    CardToken card = new CardToken(token);

    DebitRequest request = new DebitRequest(
        user,
        debit,
        card
    );

    NuveiSDK.getInstance().paymentDebit(
        request,
        new NuveiCallBack<DebitResponse>() {

            @Override
            public void onSucces(DebitResponse data) {
                showLoading(false);

                if (data != null) {
                    Gson gson = new Gson();
                    String debitResponseJson = gson.toJson(data);

                    Intent intent = new Intent(
                        HomeActivity.this,
                        RefundActivity.class
                    );

                    intent.putExtra(
                        "DEBIT_RESPONSE",
                        debitResponseJson
                    );

                    startActivity(intent);
                } else {
                    Toast.makeText(
                        HomeActivity.this,
                        "Payment failed. Response is null.",
                        Toast.LENGTH_SHORT
                    ).show();
                }
            }

            @Override
            public void onError(ErrorResponseModel error) {
                showLoading(false);
                Toast.makeText(
                    HomeActivity.this,
                    error.getError().getDescription(),
                    Toast.LENGTH_SHORT
                ).show();
            }
        }
    );
}
```

### 🧩 UI: Agregar tarjeta (NuveiAddCardForm)

El SDK incluye un widget UI listo para usar para agregar tarjetas.
```xml
📐 XML
<com.nuvei.nuvei_sdk.widget.NuveiAddCardForm
    android:id="@+id/card_form_widget"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" />

```

#### 📱 Activity de ejemplo
```code
public class AddCardActivity extends AppCompatActivity {

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        NuveiAddCardForm addCardForm = findViewById(R.id.card_form_widget);
        progressBar = findViewById(R.id.progress_bar);

        addCardForm.setUserData(
            "4",
            "erick.guillen@nuvei.com"
        );

        addCardForm.setFormListener(new AddCardListener() {

            @Override
            public void onSucces(boolean success, String message) {
                showAlert(
                    success ? "Success" : "Failure",
                    message
                );
            }

            @Override
            public void onError(ErrorResponseModel errorResponseModel) {
                showAlert(
                    "Error",
                    errorResponseModel.getError().getDescription()
                );
            }

            @Override
            public void onLoading(boolean isLoading) {
                showLoading(isLoading);
            }
        });
    }

    private void showLoading(boolean isLoading) {
        progressBar.setVisibility(
            isLoading ? View.VISIBLE : View.GONE
        );
    }

    private void showAlert(String title, String message) {
        runOnUiThread(() -> {
            new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(
                    "Close",
                    (dialog, which) -> dialog.dismiss()
                )
                .show();
        });
    }
}
```
### 🔄 Flujo del formulario de tarjeta

1. El usuario ingresa los datos

2. onLoading(true)

3. Registro en Nuvei

4. Error → onError

5. Éxito → onSucces

6. onLoading(false)

### ⚠️ Manejo de errores

Todos los callbacks retornan:
```code
ErrorResponseModel
errorResponseModel.getError().getDescription();

```
