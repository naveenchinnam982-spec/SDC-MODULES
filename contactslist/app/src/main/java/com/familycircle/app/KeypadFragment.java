package com.familycircle.app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class KeypadFragment extends Fragment {

    private static final int CALL_PERMISSION_REQUEST_CODE = 125;
    private EditText etPhoneNumber;
    private ImageView btnDeleteDigit, btnVideoCall;
    private String pendingNumber = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_keypad, container, false);

        etPhoneNumber = view.findViewById(R.id.etPhoneNumber);
        btnDeleteDigit = view.findViewById(R.id.btnDeleteDigit);
        btnVideoCall = view.findViewById(R.id.btnVideoCall);

        // Set click listeners for all numeric buttons
        setButtonClick(view, R.id.btn1, "1");
        setButtonClick(view, R.id.btn2, "2");
        setButtonClick(view, R.id.btn3, "3");
        setButtonClick(view, R.id.btn4, "4");
        setButtonClick(view, R.id.btn5, "5");
        setButtonClick(view, R.id.btn6, "6");
        setButtonClick(view, R.id.btn7, "7");
        setButtonClick(view, R.id.btn8, "8");
        setButtonClick(view, R.id.btn9, "9");
        setButtonClick(view, R.id.btn0, "0");
        setButtonClick(view, R.id.btnStar, "*");
        setButtonClick(view, R.id.btnHash, "#");

        // Handle Backspace
        btnDeleteDigit.setOnClickListener(v -> {
            String current = etPhoneNumber.getText().toString();
            if (current.length() > 0) {
                etPhoneNumber.setText(current.substring(0, current.length() - 1));
            }
        });

        btnDeleteDigit.setOnLongClickListener(v -> {
            etPhoneNumber.setText("");
            return true;
        });

        // Video Call Feature
        btnVideoCall.setOnClickListener(v -> {
            String number = etPhoneNumber.getText().toString();
            if (!number.isEmpty()) {
                // Video call via WhatsApp as a reliable system-independent method
                try {
                    String url = "https://api.whatsapp.com/send?phone=" + number;
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Video call service unavailable", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Show/Hide buttons based on text
        etPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                int visibility = s.length() > 0 ? View.VISIBLE : View.INVISIBLE;
                btnDeleteDigit.setVisibility(visibility);
                btnVideoCall.setVisibility(visibility);
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        view.findViewById(R.id.btnCallManual).setOnClickListener(v -> {
            String number = etPhoneNumber.getText().toString();
            if (!number.isEmpty()) {
                initiateImmediateCall(number);
            }
        });

        view.findViewById(R.id.btnMenu).setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), SettingsActivity.class));
        });

        return view;
    }

    private void setButtonClick(View parent, int id, String val) {
        parent.findViewById(id).setOnClickListener(v -> {
            String current = etPhoneNumber.getText().toString();
            etPhoneNumber.setText(current + val);
        });
    }

    private void initiateImmediateCall(String number) {
        pendingNumber = number;
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, CALL_PERMISSION_REQUEST_CODE);
        } else {
            performCall(number);
        }
    }

    private void performCall(String number) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + number));
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CALL_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (!pendingNumber.isEmpty()) {
                    performCall(pendingNumber);
                }
            } else {
                Toast.makeText(getContext(), "Call Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}