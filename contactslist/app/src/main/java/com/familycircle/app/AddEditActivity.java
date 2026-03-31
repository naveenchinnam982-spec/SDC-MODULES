package com.familycircle.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.familycircle.app.model.Contact;
import com.familycircle.app.viewmodel.ContactViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class AddEditActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 101;

    private TextInputEditText etName, etPhone, etCity, etState, etExtra, etBirthday, etNotes, etLat, etLong;
    private Spinner spinnerRelation;
    private ImageView ivProfilePreview;
    private TextView tvHeaderTitle;
    private String currentImagePath = "";
    private ContactViewModel contactViewModel;
    private Contact existingContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        // Initialize notification channel
        NotificationHelper.createNotificationChannel(this);

        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etCity = findViewById(R.id.etCity);
        etState = findViewById(R.id.etState);
        etExtra = findViewById(R.id.etExtra); 
        etBirthday = findViewById(R.id.etBirthday);
        etNotes = findViewById(R.id.etNotes);
        etLat = findViewById(R.id.etLat);
        etLong = findViewById(R.id.etLong);
        spinnerRelation = findViewById(R.id.spinnerRelation);
        ivProfilePreview = findViewById(R.id.ivProfilePreview);
        tvHeaderTitle = findViewById(R.id.tvHeaderTitle);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        String[] relations = {"Father", "Mother", "Brother", "Sister", "Grandfather", "Grandmother",
                "Uncle", "Aunt", "Cousin", "Son", "Daughter", "Husband", "Wife", "Nephew", "Niece", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, relations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRelation.setAdapter(adapter);

        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);

        findViewById(R.id.btnSelectImage).setOnClickListener(v -> selectImage());

        existingContact = (Contact) getIntent().getSerializableExtra("contact");
        if (existingContact != null) {
            tvHeaderTitle.setText("Edit Relative");
            etName.setText(existingContact.getName());
            etPhone.setText(existingContact.getPhone());
            etCity.setText(existingContact.getCity());
            etState.setText(existingContact.getState());
            etExtra.setText(existingContact.getEmail()); 
            etBirthday.setText(existingContact.getBirthday());
            etNotes.setText(existingContact.getNotes());
            etLat.setText(String.valueOf(existingContact.getLatitude()));
            etLong.setText(String.valueOf(existingContact.getLongitude()));
            currentImagePath = existingContact.getImagePath();
            if (currentImagePath != null && !currentImagePath.isEmpty()) {
                ivProfilePreview.setImageURI(Uri.parse(currentImagePath));
            }
            for (int i = 0; i < relations.length; i++) {
                if (relations[i].equals(existingContact.getRelation())) {
                    spinnerRelation.setSelection(i);
                    break;
                }
            }
        } else {
            tvHeaderTitle.setText("Add Relative");
        }

        findViewById(R.id.btnSave).setOnClickListener(v -> saveContact());
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            if (requestCode == PICK_IMAGE) {
                Uri uri = data.getData();
                if (uri != null) {
                    getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    currentImagePath = uri.toString();
                    ivProfilePreview.setImageURI(uri);
                }
            }
        }
    }

    private void saveContact() {
        String name = etName.getText().toString().trim();
        String relation = spinnerRelation.getSelectedItem().toString();
        String phone = etPhone.getText().toString().trim();
        String city = etCity.getText().toString().trim();
        String state = etState.getText().toString().trim();
        String email = etExtra.getText().toString().trim();
        String birthday = etBirthday.getText().toString().trim();
        String notes = etNotes.getText().toString().trim();
        
        double latitude = 0;
        double longitude = 0;
        try {
            latitude = Double.parseDouble(etLat.getText().toString().trim());
            longitude = Double.parseDouble(etLong.getText().toString().trim());
        } catch (NumberFormatException ignored) {}

        if (name.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Name and Phone are required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (existingContact != null) {
            existingContact.setName(name);
            existingContact.setRelation(relation);
            existingContact.setPhone(phone);
            existingContact.setCity(city);
            existingContact.setState(state);
            existingContact.setEmail(email);
            existingContact.setBirthday(birthday);
            existingContact.setNotes(notes);
            existingContact.setImagePath(currentImagePath);
            existingContact.setLatitude(latitude);
            existingContact.setLongitude(longitude);
            contactViewModel.update(existingContact);
            Toast.makeText(this, "Contact Updated", Toast.LENGTH_SHORT).show();
        } else {
            Contact contact = new Contact(name, relation, phone, email, city, state, birthday, notes, currentImagePath, latitude, longitude);
            contactViewModel.insert(contact);
            
            // Show Notification for new contact
            NotificationHelper.showContactAddedNotification(this, name);
            Toast.makeText(this, "Contact Added", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
