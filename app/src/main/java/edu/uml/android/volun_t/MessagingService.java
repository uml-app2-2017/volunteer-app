package edu.uml.android.volun_t;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by adam on 3/22/17.
 */

public class MessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d("MESSAGE", "From: " + remoteMessage.getFrom());
        Log.d("MESSAGE", "Notification Message Body: " + remoteMessage.getNotification().getBody());
    }

}
