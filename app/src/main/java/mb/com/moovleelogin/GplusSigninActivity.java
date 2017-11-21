package mb.com.moovleelogin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

public class GplusSigninActivity extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener {
    public static final int RC_SIGN_IN = 007;
    private static final String TAG = GplusSigninActivity.class.getSimpleName();
    public GoogleApiClient googleapiclient;
    public GoogleSignInResult result;
    public Button gmailsigninbuttton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gplus_signin);
        //Finding button from xml through id
        gmailsigninbuttton = (Button) findViewById(R.id.gmailsigninbutton);

        //Listening from the button
        gmailsigninbuttton.setOnClickListener(this);

        //Changing color of text on button
        gmailsigninbuttton.setTextColor(Color.parseColor("#c5f5f0"));

        //Configure signin to request the user for basic profile info,
        //basic info is stored in  DEFAULT_SIGN_IN
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        //Build the GoogleSignInClient with the options provided by googleSignInOptions
        googleapiclient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();
    }

    boolean connected = false;


    //Function for checking internet connection
    public boolean check_InternnetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
            return connected;
        }
        return connected;
    }

    //Function for signin with google account
    public void signIn() {
        check_InternnetConnection();
        if (connected) {
            //Starting the intent prompts the user to select a Google account to sign in with.
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleapiclient);
            startActivityForResult(signInIntent, RC_SIGN_IN);
        } else {
            Toast.makeText(this, "Check your internet connection please", Toast.LENGTH_SHORT).show();
        }

    }



    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        Log.e(TAG, "onConnectionFailed:" + connectionResult);
    }


    public void handleSignInResult(GoogleSignInResult result) {

        Log.e(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            Intent gotoSigninresult=new Intent(GplusSigninActivity.this,TraceLocationActivity.class);
            startActivity(gotoSigninresult);
        }
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RC_SIGN_IN == requestCode)
        {
            result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);

        }
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch(id)
        {
            case R.id.gmailsigninbutton:
                signIn();
                break;
        }
    }
}
