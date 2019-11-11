package com.solanki.sahil.kisan.data.repository;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import androidx.lifecycle.MutableLiveData;

import com.solanki.sahil.kisan.data.model.Contact;
import com.solanki.sahil.kisan.data.provider.App;

import java.util.ArrayList;
import java.util.List;


public class UserRepository {
    private UserRepository instance;
    private ArrayList<Contact> mList = new ArrayList<>();


    public UserRepository getInstance() {
        if (instance == null)
            instance = new UserRepository();


        return instance;
    }

    public MutableLiveData<List<Contact>> getData() {
        setData();

        MutableLiveData<List<Contact>> result = new MutableLiveData<>();
        result.setValue(mList);

        return result;
    }

    private void setData() {
        mList = new ArrayList<>();

        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
        String selection = null;
        String[] selectionArgs = null;
        String sortOrder = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;

        ContentResolver resolver = App.getContext().getContentResolver();
        Cursor cursor = resolver.query(uri, projection, selection, selectionArgs, sortOrder+" "+"ASC");


        while (cursor.moveToNext()) {

            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            Contact contact = new Contact();
            contact.setName(name);
            contact.setNumber(number);
            mList.add(contact);

        }

    }


}
