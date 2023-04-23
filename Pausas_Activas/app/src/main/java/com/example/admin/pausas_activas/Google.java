package com.example.admin.pausas_activas;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.admin.pausas_activas.LogueoGoogle.AbstractGetNameTask;
import com.example.admin.pausas_activas.LogueoGoogle.GetNameInForeground;
import com.google.android.gms.auth.GoogleAuthUtil;

public class Google extends AppCompatActivity {
    Context mContext = Google.this;
    AccountManager mAccountManager;
    String token;
    int serverCode;
    private static final String SCOPE = "oauth2:https://www.googleapis.com/auth/userinfo.profile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_google);
        /**
         * get user email using intent
         */
        syncGoogleAccount();
    }

    private String[] getAccountNames() {

        mAccountManager = AccountManager.get(this);
        Account[] accounts = mAccountManager.getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
        String[] names = new String[accounts.length];
        for (int i = 0; i < names.length; i++) {
            names[i] = accounts[i].name;
        }
        return names;
    }

    private AbstractGetNameTask getTask(Google activity, String email, String scope) {
        return new GetNameInForeground(activity, email, scope);
    }

    public void syncGoogleAccount() {
        if (isNetworkAvailable() == true) {
            String[] accountarrs = getAccountNames();
            if (accountarrs.length > 0) {
                //you can set here account for login
                getTask(Google.this, accountarrs[0], SCOPE).execute();
            } else {
                Toast.makeText(Google.this, "Su cuenta no fue sincronizada porfavor vuelva a intentar!", Toast.LENGTH_SHORT).show();
                Intent inte = new Intent(Google.this, Index.class);
                startActivity(inte);
                finish();
            }
        } else {
            Toast.makeText(Google.this, "Error En La Conexion Intentalo De Nuevo", Toast.LENGTH_SHORT).show();
            Intent inten = new Intent(Google.this, Index.class);
            startActivity(inten);
            Google.this.finish();
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Log.e("Network Testing", "***Available***");
            return true;
        }
        Log.e("Network Testing", "***Not Available***");
        return false;
    }

}

