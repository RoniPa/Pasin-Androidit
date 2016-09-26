package fi.jamk.menunotificationtoastdemo;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    final private int LONELY_NOTIFICATION = 0x01;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public void onPause()
    {
        if (isApplicationSentToBackground(this)) {
            launchNotification(findViewById(R.id.main_view));
        }
        super.onPause();
    }

    @Override
    public void onStop()
    {
        if (isApplicationSentToBackground(this)) {
            launchNotification(findViewById(R.id.main_view));
        }
        super.onStop();
    }

    /**
     * Launch good bye notification
     *
     * @param view
     */
    public void launchNotification(View view) {
        // Action intent
        Intent actionIntent = new Intent(Intent.ACTION_VIEW);
        actionIntent.setData(Uri.parse("https://www.youtube.com/watch?v=vHBkxTnMhXY"));

        PendingIntent i = PendingIntent.getActivity(this, 0, actionIntent, 0);

        Notification notification = new Notification.Builder(this)
                .setCategory(Notification.CATEGORY_SOCIAL)
                .setContentTitle("Goodbye my friend!")
                .setContentText("Hope to see you soon again :)")
                .setSmallIcon(R.drawable.bie)
                .setAutoCancel(true)
                .setContentIntent(i)
                .setVisibility(Notification.VISIBILITY_PUBLIC)
                .build();

        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(this.LONELY_NOTIFICATION, notification);
    }

    // this method is called from layout (activity_main)
    public void showToast(View view) {
        int xOffset;
        int yOffset;

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        xOffset = (int)((-(Math.random() * size.x)/2)+((Math.random() * size.x) / 2));
        yOffset = (int)((-(Math.random() * size.y)/2)+((Math.random() * size.y) / 2));

        Toast toast = Toast.makeText(getApplicationContext(), R.string.custom_string, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, xOffset, yOffset);
        toast.show();
    }

    public void exitDialog(MenuItem item) {
        ExitDialogFragment eDialog = new ExitDialogFragment();
        eDialog.show(getFragmentManager(), "exit");
    }

    /**
     * Checks if the application is being sent in the background (i.e behind
     * another application's Activity).
     *
     * @param context the context
     * @return <code>true</code> if another application will be above this one.
     */
    public static boolean isApplicationSentToBackground(final Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }

        return false;
    }
}
