package com.example.amine.busniess_card;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.widget.SimpleCursorAdapter;
import android.database.MatrixCursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.File;
import java.io.FileOutputStream;


/**
 * Created by Amine on 28/05/2017.
 */

public class ContactListActivity extends Activity {

    SimpleCursorAdapter mAdapter;
    MatrixCursor mMatrixCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_list);

        // The contacts from the contacts content provider is stored in this
        // cursor
        mMatrixCursor = new MatrixCursor(new String[] { "_id", "name", "photo",
                "details" });

        // Adapter to set data in the listview
        mAdapter = new SimpleCursorAdapter(getBaseContext(),
                R.layout.lv_layout, null, new String[] { "name", "photo",
                "details" }, new int[] { R.id.tv_name, R.id.tv_photo,
                R.id.tv_details }, 0);

        // Getting reference to listview
        final ListView lstContacts = (ListView) findViewById(R.id.listContacts);

        // Setting the adapter to listview
        lstContacts.setAdapter(mAdapter);

        // Creating an AsyncTask object to retrieve and load listview with
        // contacts
        ListViewContactsLoader listViewContactsLoader = new ListViewContactsLoader();

        // Starting the AsyncTask process to retrieve and load listview with
        // contacts
        listViewContactsLoader.execute();
        lstContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {


                    /*
                    Intent intent = new Intent(getActivity(),EditMyContactActivity.class);
                    intent.putExtra(DatabaseContract.BusinessCardDataTable.KEY_BCID,businessCardIDS.get(position));
                    getActivity().startActivity(intent);
                    //Toast.makeText(getActivity(), name[position]+" clicked", Toast.LENGTH_SHORT).show();
*/

            }
        });

    }

    /** An AsyncTask class to retrieve and load listview with contacts */
    private class ListViewContactsLoader extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... params) {
            Uri contactsUri = ContactsContract.Contacts.CONTENT_URI;

            // Querying the table ContactsContract.Contacts to retrieve all the
            // contacts
            Cursor contactsCursor = getContentResolver().query(contactsUri,
                    null, null, null,
                    ContactsContract.Contacts.DISPLAY_NAME + " ASC ");

            if (contactsCursor.moveToFirst()) {
                do {
                    long contactId = contactsCursor.getLong(contactsCursor
                            .getColumnIndex("_ID"));

                    Uri dataUri = ContactsContract.Data.CONTENT_URI;

                    // Querying the table ContactsContract.Data to retrieve
                    // individual items like
                    // home phone, mobile phone, work email etc corresponding to
                    // each contact
                    Cursor dataCursor = getContentResolver().query(dataUri,
                            null,
                            ContactsContract.Data.CONTACT_ID + "=" + contactId,
                            null, null);

                    String displayName = "";
                    String nickName = "";
                    String mobilePhone = "";
                    String photoPath = ""; //+ R.drawable.blank;
                    byte[] photoByte = null;
                    String homeEmail = "";
                    String email = "";
                    String workEmail = "";
                    String title = "";

                    if (dataCursor.moveToFirst()) {
                        // Getting Display Name
                        displayName = dataCursor
                                .getString(dataCursor
                                        .getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
                        do {

                            // Getting NickName
                            if (dataCursor
                                    .getString(
                                            dataCursor
                                                    .getColumnIndex("mimetype"))
                                    .equals(ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE))
                                nickName = dataCursor.getString(dataCursor
                                        .getColumnIndex("data1"));

                            // Getting Phone numbers
                            if (dataCursor
                                    .getString(
                                            dataCursor
                                                    .getColumnIndex("mimetype"))
                                    .equals(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)) {
                                switch (dataCursor.getInt(dataCursor
                                        .getColumnIndex("data2"))) {

                                    case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                                        mobilePhone = dataCursor
                                                .getString(dataCursor
                                                        .getColumnIndex("data1"));
                                        break;
                                }
                            }

                            // Getting EMails
                            if (dataCursor
                                    .getString(
                                            dataCursor
                                                    .getColumnIndex("mimetype"))
                                    .equals(ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)) {
                                switch (dataCursor.getInt(dataCursor
                                        .getColumnIndex("data2"))) {
                                    case ContactsContract.CommonDataKinds.Email.TYPE_HOME:
                                        homeEmail = dataCursor.getString(dataCursor
                                                .getColumnIndex("data1"));
                                        break;
                                    case ContactsContract.CommonDataKinds.Email.TYPE_WORK:
                                        workEmail = dataCursor.getString(dataCursor
                                                .getColumnIndex("data1"));
                                        break;
                                }
                            }

                            // Getting Organization details
                            if (dataCursor
                                    .getString(
                                            dataCursor
                                                    .getColumnIndex("mimetype"))
                                    .equals(ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)) {

                                title = dataCursor.getString(dataCursor
                                        .getColumnIndex("data4"));
                            }

                            // Getting Photo
                            if (dataCursor
                                    .getString(
                                            dataCursor
                                                    .getColumnIndex("mimetype"))
                                    .equals(ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE)) {
                                photoByte = dataCursor.getBlob(dataCursor
                                        .getColumnIndex("data15"));

                                if (photoByte != null) {
                                    Bitmap bitmap = BitmapFactory
                                            .decodeByteArray(photoByte, 0,
                                                    photoByte.length);

                                    // Getting Caching directory
                                    File cacheDirectory = getBaseContext()
                                            .getCacheDir();

                                    // Temporary file to store the contact image
                                    File tmpFile = new File(
                                            cacheDirectory.getPath() + "/wpta_"
                                                    + contactId + ".png");

                                    // The FileOutputStream to the temporary
                                    // file
                                    try {
                                        FileOutputStream fOutStream = new FileOutputStream(
                                                tmpFile);

                                        // Writing the bitmap to the temporary
                                        // file as png file
                                        bitmap.compress(
                                                Bitmap.CompressFormat.PNG, 100,
                                                fOutStream);

                                        // Flush the FileOutputStream
                                        fOutStream.flush();

                                        // Close the FileOutputStream
                                        fOutStream.close();

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    photoPath = tmpFile.getPath();
                                }

                            }

                        } while (dataCursor.moveToNext());



                        // Concatenating various information to single string
                        if (nickName != null && !nickName.equals(""))
                            displayName += "  " + nickName ;
                        if (workEmail != null && !workEmail.equals("")) {
                            email += "" + workEmail;
                        }else if (homeEmail != null && !homeEmail.equals("")) {
                            email += ""+ homeEmail ;
                        }

                        // Adding id, display name, path to photo and other
                        // details to cursor
                        mMatrixCursor.addRow(new Object[] {
                                Long.toString(contactId), displayName,
                                photoPath,email });
                    }

                } while (contactsCursor.moveToNext());
            }
            return mMatrixCursor;
        }

        @Override
        protected void onPostExecute(Cursor result) {
            // Setting the cursor containing contacts to listview
            mAdapter.swapCursor(result);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.contact_list, menu);
        return true;
    }
}
