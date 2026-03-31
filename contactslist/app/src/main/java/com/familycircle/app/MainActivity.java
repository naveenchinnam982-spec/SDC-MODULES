package com.familycircle.app;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottom_navigation);
        
        if (savedInstanceState == null) {
            loadFragment(new ContactsFragment(), R.id.nav_contacts);
        }

        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();
            
            if (itemId == R.id.nav_recents) {
                selectedFragment = new RecentsFragment();
            } else if (itemId == R.id.nav_contacts) {
                selectedFragment = new ContactsFragment();
            } else if (itemId == R.id.nav_keypad) {
                selectedFragment = new KeypadFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
            }
            return true;
        });
    }

    private void loadFragment(Fragment fragment, int itemId) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
        bottomNav.setSelectedItemId(itemId);
    }

    @Override
    public void onBackPressed() {
        // If not on Contacts (Home) tab, go back to Contacts tab first
        if (bottomNav.getSelectedItemId() != R.id.nav_contacts) {
            loadFragment(new ContactsFragment(), R.id.nav_contacts);
        } else {
            // If already on Home tab, perform default back (exit app)
            super.onBackPressed();
        }
    }
}