package com.example.admin.pausas_activas.LogueoGoogle;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.example.admin.pausas_activas.Google;
import com.example.admin.pausas_activas.Registrar_Google;
import com.google.android.gms.auth.GoogleAuthUtil;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ADMIN on 26/07/2016.
 */
public abstract class AbstractGetNameTask extends AsyncTask<Void, Void, Void> {
    private static final String TAG = "TokenInfoTask";
    protected Google mActivity;
    public static String GOOGLE_USER_DATA="No_data";
    protected String mScope;
    protected String mEmail;
    protected int mRequestCode;

    AbstractGetNameTask(Google google, String email, String scope) {
        this.mActivity = google;
        this.mScope = scope;
        this.mEmail = email;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            fetchNameFromProfileServer();

        } catch (IOException ex) {
            onError("Following Error occured, please try again. "
                    + ex.getMessage(), ex);
        } catch (JSONException e) {
            onError("Bad response: " + e.getMessage(), e);
        }
        return null;
    }

    protected void onError(String msg, Exception e) {
        if (e != null) {
            Log.e(TAG, "Exception: ", e);
        }
    }

    /**
     * Get a authentication token if one is not available. If the error is not
     * recoverable then it displays the error message on parent activity.
     */
    protected abstract String fetchToken() throws IOException;

    /**
     * Contacts the user info server to get the profile of the user and extracts
     * the first name of the user from the profile. In order to authenticate
     * with the user info server the method first fetches an access token from
     * Google Play services.
     * @return
     * @return
     *
     * @throws IOException
     *             if communication with user info server failed.
     * @throws JSONException
     *             if the response from the server could not be parsed.
     */
    private void fetchNameFromProfileServer() throws IOException, JSONException {
        String token = fetchToken();
        URL url = new URL("https://www.googleapis.com/oauth2/v1/userinfo?access_token="+ token);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        int sc = con.getResponseCode();
        if (sc == 200) {
            InputStream is = con.getInputStream();
            GOOGLE_USER_DATA = readResponse(is);
            is.close();
            Intent intent=new Intent(mActivity,Registrar_Google.class);
            intent.putExtra("email_id", mEmail);
            mActivity.startActivity(intent);
            mActivity.finish();
            return;

        } else if (sc == 401) {
            GoogleAuthUtil.invalidateToken(mActivity, token);
            onError("Server auth error, please try again.", null);
            //Toast.makeText(mActivity, "Please try again", Toast.LENGTH_SHORT).show();
            //mActivity.finish();
            return;
        } else {
            onError("Server returned the following error code: " + sc, null);
            return;
        }
    }

    /**
     * Reads the response from the input stream and returns it as a string.
     */
    private static String readResponse(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] data = new byte[2048];
        int len = 0;
        while ((len = is.read(data, 0, data.length)) >= 0) {
            bos.write(data, 0, len);
        }
        return new String(bos.toByteArray(), "UTF-8");
    }


}
