package com.example.MyContacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ivan on 30.07.2015.
 */
public class ContactsAdapter extends BaseAdapter {
    private ArrayList<Contact> contacts;
    private Context c;
    public ContactsAdapter(ArrayList<Contact> contacts, Context c) {
        this.contacts = contacts;
        this.c = c;
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View contactView = convertView;
        if (contactView == null) {
            contactView = LayoutInflater.from(c).inflate(R.layout.listitem_contact, null);
        }
        RelativeLayout rlContact = (RelativeLayout) contactView.findViewById(R.id.contact);
        Contact contact = contacts.get(position);
        ImageView imAvatar = (ImageView) contactView.findViewById(R.id.imAvatar);
        imAvatar.setImageBitmap(MyContacts.avatars.get(position));
        TextView nameView = (TextView) contactView.findViewById(R.id.tvName);
        nameView.setText(contact.getName());

        return contactView;
    }
}
