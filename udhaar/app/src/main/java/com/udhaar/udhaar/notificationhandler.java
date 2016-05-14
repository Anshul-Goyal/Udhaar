package com.udhaar.udhaar;

import android.util.Log;
import com.onesignal.OneSignal;
import org.json.JSONObject;

/**
 * Created by Architkansal on 14-05-2016.
 */
// This fires when a notification is opened by tapping on it or one is received while the app is running.
class notificationhandler implements OneSignal.NotificationOpenedHandler {
    @Override
    public void notificationOpened(String message, JSONObject additionalData, boolean isActive) {
        try {
            if (additionalData != null) {
                if (additionalData.has("actionSelected"))
                    Log.d("OneSignalExample", "OneSignal notification button with id " + additionalData.getString("actionSelected") + " pressed");

                Log.d("OneSignalExample", "Full additionalData:\n" + additionalData.toString());
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }

        // The following can be used to open an Activity of your choose.
      /*
      Intent intent = new Intent(getApplication(), YourActivity.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
      startActivity(intent);
      */
        // Follow the insturctions in the link below to prevent the launcher Activity from starting.
        // https://documentation.onesignal.com/docs/android-notification-customizations#changing-the-open-action-of-a-notification
    }
}