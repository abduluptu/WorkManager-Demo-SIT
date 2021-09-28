package com.sohainfotech.workmanagerdemosit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class NotificationDetailsActivity extends AppCompatActivity {
    private TextView tvNoti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_details);

        tvNoti = findViewById(R.id.tvNoti);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String mTitle = extras.getString("title");
            String mMessageBody = extras.getString("messageBody");
            tvNoti.setText(mTitle + " \n" + mMessageBody);
        }
    }
}