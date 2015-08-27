package com.example.MyContacts;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.*;
import java.util.ArrayList;

public class MyContacts extends Activity {
    static ArrayList<Contact> contacts;
    static ArrayList<Bitmap> avatars;
    public static ContactsAdapter contactsAdapter;
    public static AdapterView.AdapterContextMenuInfo info;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        if (contacts != null) writeContacts(contacts);
        if (avatars != null) writeAvatars(avatars);
        createContacts();
        fillList();
    }

    private void fillList() {
        ListView lvContacts = (ListView) findViewById(R.id.lvContacts);
        registerForContextMenu(lvContacts);
        contactsAdapter = new ContactsAdapter(contacts, this);
        lvContacts.setAdapter(contactsAdapter);
    }

    private void createContacts() {
        if (readContacts() != null) contacts = readContacts();
        else contacts = new ArrayList<Contact>();
        if (readAvatars() != null) avatars = readAvatars();
        else avatars = new ArrayList<Bitmap>();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.contextmenu, menu);
        info = (AdapterView.AdapterContextMenuInfo) menuInfo;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.open_button:
                Intent intent = new Intent(this, MyContact.class);
                startActivity(intent);
                break;
            case R.id.delete_button:
                contacts.remove(info.position);
                avatars.remove(info.position);
                deleteFile(avatars.size() + "avatar.jpg");
                if (contacts != null) writeContacts(contacts);
                if (avatars != null) writeAvatars(avatars);
                contactsAdapter.notifyDataSetChanged();
                break;
        }
        return true;
    }

    public void createNewContact(View view) {
        Intent intent = new Intent(this, NewContact.class);
        startActivity(intent);
    }

    private ArrayList<Contact> readContacts() {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = openFileInput("content.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            ArrayList<Contact> contacts = (ArrayList<Contact>) objectInputStream.readObject();
            objectInputStream.close();
            return contacts;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void writeContacts(ArrayList<Contact> contacts) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = openFileOutput("content.txt", MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(contacts);
            objectOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Bitmap> readAvatars() {
        FileInputStream fileInputStream = null;
        ArrayList<Bitmap> avatars = new ArrayList<Bitmap>();
        for (int i = 0; i < contacts.size(); i++) {
            try {
                fileInputStream = openFileInput(i + "avatar.jpg");
                avatars.add(BitmapFactory.decodeStream(fileInputStream));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return avatars.size() == 0 ? null : avatars;
    }

    private void writeAvatars(ArrayList<Bitmap> avatars) {
//    new SavingAvatars(this).execute(avatars);
        FileOutputStream fileOutputStream = null;
        for (int i = 0; i < avatars.size(); i++) {
            try {
                fileOutputStream = openFileOutput(i + "avatar.jpg", MODE_PRIVATE);
                avatars.get(i).compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

