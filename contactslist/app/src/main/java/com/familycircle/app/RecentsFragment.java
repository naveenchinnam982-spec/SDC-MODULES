package com.familycircle.app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.familycircle.app.adapter.ContactAdapter;
import com.familycircle.app.model.Contact;
import com.familycircle.app.viewmodel.ContactViewModel;
import java.util.stream.Collectors;

public class RecentsFragment extends Fragment implements ContactAdapter.OnContactActionListener {

    private static final int CALL_PERMISSION_REQUEST_CODE = 124;
    private ContactViewModel viewModel;
    private ContactAdapter adapter;
    private Contact pendingCallContact;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recents, container, false);

        RecyclerView rvRecents = view.findViewById(R.id.rvRecents);
        rvRecents.setLayoutManager(new LinearLayoutManager(getContext()));
        
        adapter = new ContactAdapter(getContext(), this);
        rvRecents.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        viewModel.getAllContacts().observe(getViewLifecycleOwner(), contacts -> {
            if (contacts != null) {
                adapter.setContacts(contacts.stream()
                        .filter(c -> c.getLastCalled() > 0)
                        .sorted((c1, c2) -> Long.compare(c2.getLastCalled(), c1.getLastCalled()))
                        .collect(Collectors.toList()));
            }
        });

        return view;
    }

    @Override
    public void onDeleteClick(Contact contact) {
        new AlertDialog.Builder(getContext())
                .setTitle("Delete Contact")
                .setMessage(R.string.confirm_delete)
                .setPositiveButton(R.string.yes, (dialog, which) -> viewModel.delete(contact))
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
        viewModel.update(contact);
        
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