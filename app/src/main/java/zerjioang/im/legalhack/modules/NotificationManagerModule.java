package zerjioang.im.legalhack.modules;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import zerjioang.im.legalhack.R;
import zerjioang.im.legalhack.dao.DataManager;
import zerjioang.im.legalhack.ui.ConversationActivity;

/**
 * Created by sergio on 23/2/15.
 */
public class NotificationManagerModule {

    // A integer, that identifies each notification uniquely
    public static final int NOTIFICATION_ID = 1;
    private static final int NO_ICON = -1;
    private static NotificationManagerModule nfm;
    private Context context;

    private NotificationManagerModule(Context context) {

        this.context = context;

        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(ns);
    }

    public static NotificationManagerModule getInstance(Context context) {
        if (nfm == null) {
            nfm = new NotificationManagerModule(context);
        }
        return nfm;
    }

    //metodos de la clase

    public void mostrarNotificacionSimple(Context c, String tituloChat, String mensaje, String username) {
        int bigIcon = R.drawable.legal_icon;
        int smallIcon = R.drawable.small_icon;
        String subtext = null;
        createNotification(smallIcon, bigIcon, tituloChat, mensaje, username);
    }

    public void createNotification(int notificationSmallIcon, int notificationLargeIcon, String title, String text, String username) {

        // Use NotificationCompat.Builder to set up our notification.
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        //icon appears in device notification bar and right hand corner of notification
        if (notificationSmallIcon != NO_ICON) {
            builder.setSmallIcon(notificationSmallIcon);
        }

        // This intent is fired when notification is clicked
        Intent intent = new Intent(context, ConversationActivity.class);
        DataManager.getInstance().setNotifName(title);
        DataManager.getInstance().setDestinationUser(username);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        // Set the intent that will fire when the user taps the notification.
        builder.setContentIntent(pendingIntent);

        // Large icon appears on the left of the notification
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), notificationLargeIcon));

        // Content title, which appears in large type at the top of the notification
        if (title != null) {
            builder.setContentTitle(title);
        }

        // Content text, which appears in smaller text below the title
        if (text != null) {
            builder.setContentText(text);
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Will display the notification in the notification bar
        notificationManager.notify(NOTIFICATION_ID, builder.build());

    }
}