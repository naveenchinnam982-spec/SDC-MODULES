package com.familycircle.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.familycircle.app.AddEditActivity;
import com.familycircle.app.ContactDetailActivity;
import com.familycircle.app.R;
import com.familycircle.app.model.Contact;
import com.google.android.material.button.MaterialButton;
import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private List<Contact> contacts = new ArrayList<>();
    private final Context context;
    private final OnContactActionListener listener;

    public interface OnContactActionListener {
        void onDeleteClick(Contact contact);
        void onCallClick(Contact contact);
    }

    public ContactAdapter(Context context, OnContactActionListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        holder.tvName.setText(contact.getName());
        holder.tvRelation.setText(contact.getRelation() != null ? contact.getRelation().toUpperCase() : "");
        holder.tvPhone.setText(contact.getPhone());
        
        String location = "";
        if (contact.getCity() != null && !contact.getCity().isEmpty()) {
            location = contact.getCity();
            if (contact.getState() != null && !contact.getState().isEmpty()) {
                location += ", " + contact.getState();
            }
        } else if (contact.getState() != null && !contact.getState().isEmpty()) {
            location = contact.getState();
        }
        holder.tvLocation.setText(location);

        if (contact.getImagePath() != null && !contact.getImagePath().isEmpty()) {
            holder.ivProfile.setImageURI(Uri.parse(contact.getImagePath()));
            holder.tvAvatarInitials.setVisibility(View.GONE);
        } else {
            holder.ivProfile.setImageDrawable(null);
            holder.tvAvatarInitials.setVisibility(View.VISIBLE);
            if (contact.getName() != null && !contact.getName().isEmpty()) {
                String[] parts = contact.getName().trim().split("\\s+");
                StringBuilder initials = new StringBuilder();
                for (int i = 0; i < Math.min(parts.length, 2); i++) {
                    if (!parts[i].isEmpty()) initials.append(parts[i].charAt(0));
                }
                holder.tvAvatarInitials.setText(initials.toString().toUpperCase());
            }
        }

        holder.btnCall.setOnClickListener(v -> listener.onCallClick(contact));

        holder.btnMessage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("smsto:" + contact.getPhone()));
            context.startActivity(intent);
        });

        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, AddEditActivity.class);
            intent.putExtra("contact", contact);
            context.startActivity(intent);
        });

        holder.btnDelete.setOnClickListener(v -> listener.onDeleteClick(contact));

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ContactDetailActivity.class);
            intent.putExtra("contact", contact);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
        notifyDataSetChanged();
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProfile;
        TextView tvAvatarInitials, tvName, tvRelation, tvPhone, tvLocation;
        ImageButton btnDelete;
        MaterialButton btnCall, btnMessage, btnEdit;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfile = itemView.findViewById(R.id.ivProfile);
            tvAvatarInitials = itemView.findViewById(R.id.tvAvatarInitials);
            tvName = itemView.findViewById(R.id.tvName);
            tvRelation = itemView.findViewById(R.id.tvRelation);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnCall = itemView.findViewById(R.id.btnCall);
            btnMessage = itemView.findViewById(R.id.btnMessage);
            btnEdit = itemView.findViewById(R.id.btnEdit);
        }
    }
}