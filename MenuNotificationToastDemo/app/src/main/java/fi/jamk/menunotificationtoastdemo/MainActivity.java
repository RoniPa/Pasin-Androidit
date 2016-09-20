package fi.jamk.menunotificationtoastdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    final private int LONELY_NOTIFICATION = 0x01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.main_menu, menu);
        return true;
    }

    public void launchNotification(View view) {
        // Action intent
        Intent actionIntent = new Intent(Intent.ACTION_VIEW);
        actionIntent.setData(Uri.parse("https://github.com/ronipa"));

        PendingIntent i = PendingIntent.getActivity(this,0,actionIntent,0);

        Notification notification = new Notification.Builder(this)
                .setCategory(Notification.CATEGORY_SOCIAL)
                .setContentTitle("Hello there!")
                .setContentText("You are lonely so we decided to notify you :)")
                .setSmallIcon(R.drawable.ic_bug)
                .setAutoCancel(true)
                .setContentIntent(i)
                .setVisibility(Notification.VISIBILITY_PUBLIC)
                .build();

        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        nm.notify(this.LONELY_NOTIFICATION, notification);
    }
}
