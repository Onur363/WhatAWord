package com.example.onur.whataword;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Onur on 13.11.2017.
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService{
    private static final String REG_TOKEN="REG_TOKEN";

    @Override
    public void onTokenRefresh() {
        String recent_token= FirebaseInstanceId.getInstance().getToken();
        Log.d(REG_TOKEN,recent_token);
    }
}
