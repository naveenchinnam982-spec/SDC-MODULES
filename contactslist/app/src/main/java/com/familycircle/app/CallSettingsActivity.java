package com.familycircle.app;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CallSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_settings);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        // Simple click listeners for each option as requested
        findViewById(R.id.btnRecordCalls).setOnClickListener(v -> showToast("Record calls"));
        findViewById(R.id.btnBlockNumbers).setOnClickListener(v -> showToast("Block numbers"));
        findViewById(R.id.btnCallBackground).setOnClickListener(v -> showToast("Call background"));
        findViewById(R.id.btnCallerInformation).setOnClickListener(v -> showToast("Caller information"));
        findViewById(R.id.btnCallAlerts).setOnClickListener(v -> showToast("Call alerts and ringtone"));
        findViewById(R.id.btnAnsweringCalls).setOnClickListener(v -> showToast("Answering and ending calls"));
        findViewById(R.id.btnQuickDecline).setOnClickListener(v -> showToast("Quick decline messages"));
        findViewById(R.id.btnCallDisplay).setOnClickListener(v -> showToast("Call display while using apps"));
    }

    private void showToast(String message) {
        Toast.makeText(this, message + " settings clicked", Toast.LENGTH_SHORT).show();
    }
}