package com.example.MyContacts;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

public class NewContact extends Activity {
    private static String newDate;
    private EditText etName;
    private ImageView ivAvatar;
    private static TextView tvBirthday;
    private EditText etEmail;
    private EditText etHomePhone;
    private EditText etWorkPhone;
    private final int CAMERA_RESULT = 0;
    public static Contact contact;
    Bitmap avatar;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_contact);
        etName = (EditText) findViewById(R.id.nameView);
        ivAvatar = (ImageView) findViewById(R.id.avatarView);
        tvBirthday = (TextView) findViewById(R.id.birthdayView);
        etEmail = (EditText) findViewById(R.id.mailView);
        etHomePhone = (EditText) findViewById(R.id.homePhoneView);
        etWorkPhone = (EditText) findViewById(R.id.workPhoneView);
        contact = new Contact();
        avatar = BitmapFactory.decodeResource(getResources(), R.drawable.face);
    }

    public void showDatePickerDialog(View view) {
        DialogFragment dialogFragment = new DatePickerFragment();
        dialogFragment.show(getFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            newDate = i2 + "." + (i1 + 1) + "." + i;
            tvBirthday.setText(newDate);
            contact.setBirth(newDate);
        }
    }


    public void saveNewContact(View view) {
        contact.setName(String.valueOf(etName.getText()));
        contact.setEmail(String.valueOf(etEmail.getText()));
        contact.setHomePhone(String.valueOf(etHomePhone.getText()));
        contact.setWorkPhone(String.valueOf(etWorkPhone.getText()));
        MyContacts.contacts.add(contact);
        MyContacts.avatars.add(avatar);
        MyContacts.contactsAdapter.notifyDataSetChanged();
        finish();
    }


    public void callIntent(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.avatarView:
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_RESULT);
                break;
            default:
                return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_RESULT) {
            avatar = (Bitmap) data.getExtras().get("data");
            ivAvatar.setImageBitmap(avatar);
            contact.setAvatarExists(true);
        }
    }
}
