package com.example.customlv_yt;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends ArrayAdapter<Contact> {

    Context context;
    public ContactAdapter(@NonNull Context context, @NonNull ArrayList<Contact> objects) {
        super(context, 0, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if(v == null)
        {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.single_contact_item_design, parent, false);
        }
        ImageView ivDelete = v.findViewById(R.id.ivDelete);
        TextView tvName = v.findViewById(R.id.tvName);
        TextView tvPhone = v.findViewById(R.id.tvContact);
        Contact c = getItem(position);

        assert c != null;
        tvName.setText(c.getName());
        tvPhone.setText(c.getPhone());

        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder delDialog = new AlertDialog.Builder(context);
                delDialog.setTitle("Confirmation");
                delDialog.setMessage("Do you really want to delete this contact?...");
                delDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        remove(c);
                        notifyDataSetChanged();
                    }
                });

                delDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                delDialog.show();

            }
        });

        v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder updateDialog = new AlertDialog.Builder(context);
                updateDialog.setTitle("Update Record");
                View view = LayoutInflater.from(context)
                                .inflate(R.layout.add_contact_form, null, false);

                updateDialog.setView(view);

                EditText etName = view.findViewById(R.id.etName);
                EditText etPhone = view.findViewById(R.id.etPhone);
                etName.setText(c.getName());
                etPhone.setText(c.getPhone());


                updateDialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = etName.getText().toString().trim();
                        String phone = etPhone.getText().toString();
                        if(name.isEmpty() || phone.isEmpty())
                        {
                            Toast.makeText(context, "Something is missing", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            String oldName = c.getName();
                            c.setName(name);
                            c.setPhone(phone);
                            notifyDataSetChanged();
                            Toast.makeText(context, oldName + " has been updated.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                updateDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                updateDialog.show();

                return false;
            }
        });

        return v;
    }
}
