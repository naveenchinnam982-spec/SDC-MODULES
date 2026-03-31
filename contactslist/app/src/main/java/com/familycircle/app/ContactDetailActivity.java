package com.familycircle.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.familycircle.app.model.Contact;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class ContactDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        Contact contact = (Contact) getIntent().getSerializableExtra("contact");
        if (contact == null) {
            finish();
            return;
        }

        // Fix: Use IDs that match the activity_contact_detail.xml exactly
        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(v -> finish());
        }

        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        if (collapsingToolbar != null) {
            collapsingToolbar.setTitle(contact.getName());
        }

        ImageView ivProfile = findViewById(R.id.iv_detail_profile);
        if (ivProfile != null && contact.getImagePath() != null && !contact.getImagePath().isEmpty()) {
            ivProfile.setImageURI(Uri.parse(contact.getImagePath()));
        }

        TextView tvRelation = findViewById(R.id.tv_detail_relation);
        if (tvRelation != null) tvRelation.setText(contact.getRelation());

        TextView tvPhone = findViewById(R.id.tv_detail_phone);
        if (tvPhone != null) tvPhone.setText(contact.getPhone());

        TextView tvLocation = findViewById(R.id.tv_detail_location);
        if (tvLocation != null) tvLocation.setText(contact.getCity() + ", " + contact.getState());

        TextView tvBirthday = findViewById(R.id.tv_detail_birthday);
        if (tvBirthday != null) {
            if (contact.getBirthday() != null && !contact.getBirthday().isEmpty()) {
                tvBirthday.setText(contact.getBirthday());
            } else {
                tvBirthday.setVisibility(View.GONE);
            }
        }

        TextView tvNotes = findViewById(R.id.tv_detail_notes);
        if (tvNotes != null) {
            if (contact.getNotes() != null && !contact.getNotes().isEmpty()) {
                tvNotes.setText(contact.getNotes());
            } else {
                tvNotes.setVisibility(View.GONE);
            }
        }

        View btnCall = findViewById(R.id.btn_detail_call);
        if (btnCall != null) {
            btnCall.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + contact.getPhone()));
                startActivity(intent);
            });
        }
    }
}