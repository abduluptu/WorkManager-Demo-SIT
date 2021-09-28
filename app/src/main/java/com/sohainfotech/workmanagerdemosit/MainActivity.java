package com.sohainfotech.workmanagerdemosit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.app.NotificationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Ref: https://androidwave.com/android-workmanager-tutorial/
 */

/**
 * Ref: https://medium.com/@mountainappstudio/open-specific-activity-when-notification-clicked-in-fcm-android-app-development-f1c1d9a75fb5
 */

public class MainActivity extends AppCompatActivity {
    //It will be use inside NotificationWorker class
    public static final String MESSAGE_STATUS = "message_status";

    private TextView tvStatus;
    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStatus = findViewById(R.id.tvStatus);
        btnSend = findViewById(R.id.btnSend);

        //step3: Create WorkRequest
        //create WorkManager, This work manager will enqueue and manage our work request.
        final  WorkManager mWorkManager = WorkManager.getInstance();

        //Now we will create OneTimeWorkRequest, because I want to create a task that will be executed just once.
        final  OneTimeWorkRequest mRequest = new OneTimeWorkRequest.Builder(NotificationWorker.class).build();


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //step4: Enqueue the request with WorkManager
                mWorkManager.enqueue(mRequest);
            }
        });

        //step5: Fetch the particular task status
        /**
         * Let us fetch some information about this particular task and display it on tvStatus TextView.
         * We will do that using WorkInfo class. The work manager provides LiveData for each of the work request objects,
         * We can observe this and get the current status of the task.
         */
        mWorkManager.getWorkInfoByIdLiveData(mRequest.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                if(workInfo != null){
                    WorkInfo.State state = workInfo.getState();
                    tvStatus.append(state.toString() + " \n");
                }
            }
        });

    }
}