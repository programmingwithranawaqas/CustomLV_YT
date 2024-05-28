package com.example.customlv_yt;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fabAddNewContact;
    ListView lvContacts;
    ArrayList<Contact> contacts;
    ContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();

        fabAddNewContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder addDialog = new AlertDialog.Builder(MainActivity.this);
                addDialog.setTitle("Add New Contact");
                View view = LayoutInflater.from(MainActivity.this)
                                .inflate(R.layout.add_contact_form, null, false);
                addDialog.setView(view);
                EditText etName = view.findViewById(R.id.etName);
                EditText etPhone = view.findViewById(R.id.etPhone);

                addDialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = etName.getText().toString().trim();
                        String phone = etPhone.getText().toString();
                        if(name.isEmpty() || phone.isEmpty())
                        {
                            Toast.makeText(MainActivity.this, "Something is missing in the form", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            contacts.add(new Contact(name, phone));
                            adapter.notifyDataSetChanged();
                            Toast.makeText(MainActivity.this, name+" has been added", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                addDialog.show();

            }
        });
    }

    private void init()
    {
        fabAddNewContact = findViewById(R.id.fabAddNewContact);
        lvContacts = findViewById(R.id.lvContacts);
        contacts = new ArrayList<>();

        adapter = new ContactAdapter(this, contacts);
        lvContacts.setAdapter(adapter);
    }
}