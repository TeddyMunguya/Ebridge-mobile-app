package com.tracking.ebridge;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.ListView;

public class ViewNotifications extends Activity {

    ListView nlistView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_view_notifications);

        nlistView= (ListView) findViewById(R.id.notificationList);
    }

}
