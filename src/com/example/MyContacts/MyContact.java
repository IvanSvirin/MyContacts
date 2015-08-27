package com.example.MyContacts;

import android.app.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

public class MyContact extends Activity {
    private static String newDate;
    private TextView tvName;
    private ImageView ivAvatar;
    private static TextView tvBirthday;
    private TextView tvEmail;
    private TextView tvHomePhone;
    private TextView tvWorkPhone;
    private final int CAMERA_RESULT = 0;
    public Contact contact;
    Bitmap avatar;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_contact);
        tvName = (TextView) findViewById(R.id.nameView);
        ivAvatar = (ImageView) findViewById(R.id.avatarView);
        tvBirthday = (TextView) findViewById(R.id.birthdayView);
        tvEmail = (TextView) findViewById(R.id.mailView);
        tvHomePhone = (TextView) findViewById(R.id.homePhoneView);
        tvWorkPhone = (TextView) findViewById(R.id.workPhoneView);
        contact = MyContacts.contacts.get(MyContacts.info.position);
        avatar = MyContacts.avatars.get(MyContacts.info.position);
        tvName.setText(contact.getName());
        ivAvatar.setImageBitmap(avatar);
        tvBirthday.setText(contact.getBirth());
        tvEmail.setText(contact.getEmail());
        tvHomePhone.setText(contact.getHomePhone());
        tvWorkPhone.setText(contact.getWorkPhone());
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
            MyContacts.contacts.get(MyContacts.info.position).setBirth(newDate);
        }
    }

    public void editTextView(final View view) {
        TextView editView = (TextView) view;
        View dialog = getLayoutInflater().inflate(R.layout.edit_text, null);
        final EditText etNewText = (EditText) dialog.findViewById(R.id.newText);
        etNewText.setText(editView.getText());
        new AlertDialog.Builder(this)
                .setTitle("Редактировать")
                .setView(dialog)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((TextView) view).setText(etNewText.getText());
                        switch (view.getId()) {
                            case R.id.mailView:
                                MyContacts.contacts.get(MyContacts.info.position).setEmail(String.valueOf(etNewText.getText()));
                                break;
                            case R.id.homePhoneView:
                                MyContacts.contacts.get(MyContacts.info.position).setHomePhone(String.valueOf(etNewText.getText()));
                                break;
                            case R.id.workPhoneView:
                                MyContacts.contacts.get(MyContacts.info.position).setWorkPhone(String.valueOf(etNewText.getText()));
                                break;
                        }
                    }
                })
                .create()
                .show();
    }

    public void callIntent(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.avatarView:
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_RESULT);
                break;
            case R.id.mailButton:
                TextView mailAddress = (TextView) findViewById(R.id.mailView);
                intent = new Intent(Intent.ACTION_SEND,
                        Uri.parse((String) mailAddress.getText()));
                startActivity(Intent.createChooser(intent, "Email:"));
                break;
            case R.id.homeCallButton:
                TextView homeNumber = (TextView) findViewById(R.id.homePhoneView);
                intent = new Intent(Intent.ACTION_CALL,
                        Uri.parse("tel:" + (String) homeNumber.getText()));
                startActivity(intent);
                break;
            case R.id.workCallButton:
                TextView workNumber = (TextView) findViewById(R.id.workPhoneView);
                intent = new Intent(Intent.ACTION_CALL,
                        Uri.parse("tel:" + (String) workNumber.getText()));
                startActivity(intent);
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
            MyContacts.avatars.set(MyContacts.info.position, avatar);
            MyContacts.contacts.get(MyContacts.info.position).setAvatarExists(true);
            MyContacts.contactsAdapter.notifyDataSetChanged();
        }
    }
}
