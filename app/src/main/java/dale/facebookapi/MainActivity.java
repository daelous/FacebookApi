package dale.facebookapi;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.view.KeyEvent;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.Sharer;
import com.facebook.share.internal.ShareConstants;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private LoginButton mLoginButton;
    private CallbackManager mCallbackManager;

    private GraphRequest mGraphRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_main);

        mLoginButton = (LoginButton) findViewById(R.id.login_button);
        mCallbackManager = CallbackManager.Factory.create();

        ShareLinkContent shareLinkContent = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("http://naver.com"))
                .setContentTitle("네이버")
                .setImageUrl(null)
                .setContentDescription("ContentDescription")
                .build();

        ShareDialog shareDialog=new ShareDialog(this);
        shareDialog.registerCallback(mCallbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        shareDialog.show(shareLinkContent, ShareDialog.Mode.NATIVE);
//        mLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                AccessToken accessToken = loginResult.getAccessToken();
//                accessToken.getCurrentAccessToken();
//                accessToken.getUserId();
//                accessToken.getExpires();
//                accessToken.getApplicationId();
//
//                requestMe(accessToken);
//
//                /*
//                 로그아웃
//                 if (accessToken.getToken() != null)
//                    LoginManager.getInstance().logOut();
//
//                 */
//
//
//            }
//
//            @Override
//            public void onCancel() {
//
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//
//            }
//        });

    }

    private void requestMe(AccessToken token) {
        mGraphRequest = GraphRequest.newMeRequest(token, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {


            }
        });


        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link");

        /*
        응답 예제
        {
          "id": "12345678",
          "birthday": "1/1/1950",
          "first_name": "Chris",
          "gender": "male",
          "last_name": "Colm",
          "link": "http://www.facebook.com/12345678",
          "location": {
                "id": "110843418940484",
                "name": "Seattle, Washington"}
                ,
          "locale": "en_US",
          "name": "Chris Colm",
          "timezone": -8,
          "updated_time": "2010-01-01T16:40:43+0000",
          "verified": true
        }
        * */
        mGraphRequest.setParameters(parameters);
        mGraphRequest.executeAsync();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
