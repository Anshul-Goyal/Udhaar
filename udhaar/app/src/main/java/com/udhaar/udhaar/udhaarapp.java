package com.udhaar.udhaar;
import com.onesignal.OneSignal;
import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

import org.json.JSONObject;

/**
 * Created by Architkansal on 14-05-2016.
 */

public class udhaarapp extends Application  implements OneSignal.NotificationOpenedHandler {

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        OneSignal.startInit(this)
        .setNotificationOpenedHandler(new notificationhandler())
                .init();
    }

    @Override
    public void notificationOpened(String s,JSONObject ob,boolean b)
    {

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
