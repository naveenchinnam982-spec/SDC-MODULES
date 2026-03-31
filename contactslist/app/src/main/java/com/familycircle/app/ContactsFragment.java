package com.familycircle.app;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.familycircle.app.adapter.ContactAdapter;
import com.familycircle.app.model.Contact;
import com.familycircle.app.viewmodel.ContactViewModel;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public class ContactsFragment extends Fragment implements ContactAdapter.OnContactActionListener {

    private static final int CALL_PERMISSION_REQUEST_CODE = 123;
    private static final int VOICE_SEARCH_REQUEST_CODE = 1001;
    private ContactViewModel contactViewModel;
    private ContactAdapter adapter;
    private List<Contact> allContactsList = new ArrayList<>();
    private String currentFilter = "All";
    private TextView tvTotalCount;
    private FlexboxLayout flexboxStats;
    private Contact pendingCallContact;
    private SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);

        tvTotalCount = view.findViewById(R.id.tvTotalCount);
        flexboxStats = view.findViewById(R.id.flexboxStats);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ContactAdapter(getContext(), this);
        recyclerView.setAdapter(adapter);

        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        contactViewModel.getAllContacts().observe(getViewLifecycleOwner(), contacts -> {
            if (contacts != null) {
                allContactsList = contacts;
                applyFilters();
                updateDashboard(contacts);
            }
        });

        setupSearch(view);
        setupChips(view);

        view.findViewById(R.id.fab).setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), AddEditActivity.class));
        });

        ImageButton btnVoiceSearch = view.findViewById(R.id.btnVoiceSearch);
        if (btnVoiceSearch != null) {
            btnVoiceSearch.setOnClickListener(v -> startVoiceSearch());
        }

        return view;
    }

    private void startVoiceSearch() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say a relative's name to call...");
        try {
            startActivityForResult(intent, VOICE_SEARCH_REQUEST_CODE);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Voice search not supported", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == VOICE_SEARCH_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (result != null && !result.isEmpty()) {
                String spokenName = result.get(0).toLowerCase();
                searchView.setQuery(spokenName, true);
                
                // Find and call the contact immediately
                findAndCallContact(spokenName);
            }
        }
    }

    private void findAndCallContact(String name) {
        for (Contact contact : allContactsList) {
            if (contact.getName().toLowerCase().contains(name)) {
                onCallClick(contact);
                return;
            }
        }
        Toast.makeText(getContext(), "Contact not found: " + name, Toast.LENGTH_SHORT).show();
    }

    private void setupSearch(View view) {
        searchView = view.findViewById(R.id.searchView);
        if (searchView != null) {
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override public boolean onQueryTextSubmit(String query) {
                    contactViewModel.setSearchQuery(query);
                    return true;
                }
                @Override public boolean onQueryTextChange(String newText) {
                    contactViewModel.setSearchQuery(newText);
                    return true;
                }
            });
        }
    }

    private void setupChips(View view) {
        ChipGroup chipGroup = view.findViewById(R.id.chipGroup);
        if (chipGroup != null) {
            chipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
                if (checkedIds.isEmpty()) return;
                View chip = view.findViewById(checkedIds.get(0));
                if (chip instanceof Chip) {
                    currentFilter = ((Chip) chip).getText().toString();
                    applyFilters();
                }
            });
        }
    }

    private void applyFilters() {
        if (adapter == null) return;

        List<Contact> filteredList;
        if (currentFilter.equals("All")) {
            filteredList = allContactsList;
        } else {
            filteredList = allContactsList.stream().filter(contact -> {
                String rel = contact.getRelation();
                if (rel == null) return false;
                switch (currentFilter) {
                    case "Parents": return rel.equalsIgnoreCase("Father") || rel.equalsIgnoreCase("Mother");
                    case "Siblings": return rel.equalsIgnoreCase("Brother") || rel.equalsIgnoreCase("Sister");
                    case "Grandparents": return rel.equalsIgnoreCase("Grandfather") || rel.equalsIgnoreCase("Grandmother");
                    case "Uncles/Aunts": return rel.equalsIgnoreCase("Uncle") || rel.equalsIgnoreCase("Aunt");
                    case "Cousins": return rel.equalsIgnoreCase("Cousin");
                    default: return true;
                }
            }).collect(Collectors.toList());
        }
        adapter.setContacts(filteredList);
    }

    private void updateDashboard(List<Contact> contacts) {
        if (tvTotalCount != null) {
            String text = "Total: " + contacts.size();
            SpannableString ss = new SpannableString(text);
            int orangeColor = ContextCompat.getColor(getContext(), R.color.accent);
            ss.setSpan(new ForegroundColorSpan(orangeColor), 7, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ss.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 7, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvTotalCount.setText(ss);
        }
        
        if (flexboxStats == null) return;

        while (flexboxStats.getChildCount() > 1) {
            flexboxStats.removeViewAt(1);
        }

        Set<String> cities = new HashSet<>();
        for (Contact c : contacts) {
            if (c.getCity() != null && !c.getCity().isEmpty()) {
                cities.add(c.getCity().trim());
            }
        }

        for (String city : cities) {
            addLocationBadge(city);
        }
    }

    private void addLocationBadge(String city) {
        TextView badge = new TextView(getContext());
        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
                FlexboxLayout.LayoutParams.WRAP_CONTENT, FlexboxLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(8, 8, 8, 8);
        badge.setLayoutParams(params);
        badge.setBackgroundResource(R.drawable.bg_badge);
        badge.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.badge_bg));
        badge.setPadding(24, 12, 24, 12);
        badge.setText("📍 " + city);
        badge.setTextColor(ContextCompat.getColor(getContext(), R.id.tvName != -1 ? R.color.text_secondary : android.R.color.black));
        badge.setTextSize(11);
        flexboxStats.addView(badge);
    }

    @Override
    public void onDeleteClick(Contact contact) {
        new AlertDialog.Builder(getContext())
                .setTitle("Delete Contact")
                .setMessage(R.string.confirm_delete)
                .setPositiveButton(R.string.yes, (dialog, which) -> contactViewModel.delete(contact))
                .setNegativeButton(R.string.no, null)
                .show();
    }

    @Override
    public void onCallClick(Contact contact) {
        pendingCallContact = contact;
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, CALL_PERMISSION_REQUEST_CODE);
        } else {
            makeImmediateCall(contact);
        }
    }

    private void makeImmediateCall(Contact contact) {
        contact.setLastCalled(System.currentTimeMillis());
        contactViewModel.update(contact);
        
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + contact.getPhone()));
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CALL_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (pendingCallContact != null) {
                    makeImmediateCall(pendingCallContact);
                }
            } else {
                Toast.makeText(getContext(), "Call Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}