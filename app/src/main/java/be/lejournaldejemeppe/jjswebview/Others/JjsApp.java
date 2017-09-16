package be.lejournaldejemeppe.jjswebview.Others;

import android.app.Application;
import android.util.Log;

import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONObject;

/**
 * Created by LotuxPunk on 16/09/2017.
 */

public class JjsApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("OneSignalTag", "Before OneSignal init");

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
        OneSignal.startInit(this).init();

        Log.d("OneSignalTag", "After OneSignal init");

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .setNotificationOpenedHandler(new OpenNotif(this))
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        // Call syncHashedEmail anywhere in your app if you have the user's email.
        // This improves the effectiveness of OneSignal's "best-time" notification scheduling feature.
        // OneSignal.syncHashedEmail(userEmail);
    }

}
