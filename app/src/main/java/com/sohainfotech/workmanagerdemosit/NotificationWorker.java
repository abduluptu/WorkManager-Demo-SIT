package com.sohainfotech.workmanagerdemosit;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

//step2: Create a base class of Worker
public class NotificationWorker extends Worker {
    //It's a result key
    private static final String WORK_RESULT = "work_result";

    //Notification count
    private static int count = 0;

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Data taskData = getInputData();
        //String taskDataString = taskData.getString(MainActivity.MESSAGE_STATUS);

        sendNotification("WorkManager", "Message has been sent");

       //Data outputData = new Data.Builder().putString(WORK_RESULT, "Jobs Finished").build();

      //return Result.success(outputData);
        return Result.success();
    }

    //user defined method to show notification
    private void sendNotification(String title, String messageBody) {
        Intent intent = new Intent(getApplicationContext(), NotificationDetailsActivity.class);
        /**
         * you can use your launcher Activity instead of NotificationDetailsActivity,
         * But if the Activity you used here is not launcher Activity than its not work when App is in background.
         */
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        //Add Any key-value to pass extras to intent
        intent.putExtra("title", title);
        intent.putExtra("messageBody", messageBody);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);

        //initialize NotificationManager
        NotificationManager mNotifyManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        //These are used for Oreo.
        String channelId = "task_channel";
        String channelName = "task_name";

        //For Android Version Oreo and greater than oreo.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel mChannel = new NotificationChannel(channelId, channelName, importance);
            mChannel.setDescription(messageBody);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            mNotifyManager.createNotificationChannel(mChannel);
        }

        //For Android Version lower than oreo.
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), channelId);
        mBuilder.setContentTitle(title)
                .setContentText(messageBody)
                .setSmallIcon(R.mipmap.ic_launcher)
                //.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                //.setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.ic_launcher))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setColor(Color.parseColor("#FFD600"))
                .setContentIntent(pendingIntent)
                .setChannelId(channelId)
                .setPriority(NotificationCompat.PRIORITY_LOW);

        mNotifyManager.notify(count, mBuilder.build());
        count++;
    }

}
