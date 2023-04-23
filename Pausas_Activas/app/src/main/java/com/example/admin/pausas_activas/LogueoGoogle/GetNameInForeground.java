package com.example.admin.pausas_activas.LogueoGoogle;


import com.example.admin.pausas_activas.Google;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.auth.UserRecoverableAuthException;

import java.io.IOException;

/**
 * Created by ADMIN on 26/07/2016.
 */
public class GetNameInForeground extends AbstractGetNameTask {

    public GetNameInForeground(Google google, String email, String scope) {
        super(google, email, scope);
    }


    /**
     * Get a authentication token if one is not available. If the error is not recoverable then
     * it displays the error message on parent activity right away.
     */
    @Override
    protected String fetchToken() throws IOException {
        try {
            return GoogleAuthUtil.getToken(mActivity, mEmail, mScope);
        } catch (GooglePlayServicesAvailabilityException playEx) {
            // GooglePlayServices.apk is either old, disabled, or not present.
        } catch (UserRecoverableAuthException userRecoverableException) {
            // Unable to authenticate, but the user can fix this.
            // Forward the user to the appropriate activity.
            mActivity.startActivityForResult(userRecoverableException.getIntent(), mRequestCode);
        } catch (GoogleAuthException fatalException) {
            onError("Unrecoverable error " + fatalException.getMessage(), fatalException);
        }
        return null;
    }
}
