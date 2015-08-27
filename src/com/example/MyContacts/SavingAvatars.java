package com.example.MyContacts;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ivan on 08.08.2015.
 */
public class SavingAvatars extends AsyncTask<ArrayList<Bitmap>, Void, Void> {
    MyContacts myContacts;
    public SavingAvatars(MyContacts myContacts) {
        this.myContacts = myContacts;
    }

    @Override
    protected Void doInBackground(ArrayList<Bitmap>...  params) {
        ArrayList<Bitmap> avatars = params[0];
        FileOutputStream fileOutputStream = null;
        for (int i = 0; i < avatars.size(); i++) {
            try {
                fileOutputStream = new FileOutputStream(new File(i + "avatar.jpg"));
                avatars.get(i).compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(myContacts, "saving...", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Toast.makeText(myContacts, "saving done OK!", Toast.LENGTH_SHORT).show();
    }
}
