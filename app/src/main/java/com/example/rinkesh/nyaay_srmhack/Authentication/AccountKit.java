package com.example.rinkesh.nyaay_srmhack.Authentication;

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;

/**
 * Created by Rinkesh on 07/10/17.
 */

public class AccountKit extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            enableStrictMode();
        }
    }

    public void enableStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .penaltyDeath()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .penaltyDeath()
                .build());
    }
}
