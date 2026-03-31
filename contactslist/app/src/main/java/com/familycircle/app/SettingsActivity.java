package com.familycircle.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        prefs = getSharedPreferences("Settings", Context.MODE_PRIVATE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        // Theme Setting
        findViewById(R.id.btnTheme).setOnClickListener(v -> {
            String[] themes = {"Light", "Dark", "System Default"};
            new AlertDialog.Builder(this)
                    .setTitle("Choose Theme")
                    .setItems(themes, (dialog, which) -> {
                        if (which == 0) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        else if (which == 1) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                        
                        TextView tv = findViewById(R.id.tvCurrentTheme);
                        tv.setText(themes[which]);
                        prefs.edit().putInt("theme", which).apply();
                    })
                    .show();
        });

        // Language Setting
        findViewById(R.id.btnLanguage).setOnClickListener(v -> {
            String[] langs = {"English", "Telugu"};
            String[] langCodes = {"en", "te"};
            new AlertDialog.Builder(this)
                    .setTitle("Select Language")
                    .setItems(langs, (dialog, which) -> {
                        setLocale(langCodes[which]);
                        prefs.edit().putString("lang", langCodes[which]).apply();
                        
                        // Restart to apply language
                        Intent intent = new Intent(this, SplashActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    })
                    .show();
        });

        // Share Option
        findViewById(R.id.btnShare).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "Check out My Family Circle app! Keep your loved ones close.");
            startActivity(Intent.createChooser(intent, "Share via"));
        });

        // About Option
        findViewById(R.id.btnAbout).setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("About My Family Circle")
                    .setMessage("Version 1.0.0\n\nA simple and beautiful way to stay connected with your family.")
                    .setPositiveButton("OK", null)
                    .show();
        });
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }
}