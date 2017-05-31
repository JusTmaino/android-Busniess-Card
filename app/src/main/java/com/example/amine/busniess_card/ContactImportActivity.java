package com.example.amine.busniess_card;
import android.*;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import java.io.File;
import java.io.FileOutputStream;
import com.example.amine.busniess_card.BusniessCard ;
import static android.content.ContentValues.TAG;


public class ContactImportActivity extends AppCompatActivity {
    private static final int RESULT_PICK_CONTACT = 500;
    Button importBouton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_import);
        importBouton = (Button)findViewById(R.id.bouton_import);
        importBouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickContact(v);
            }
        });

    }


    public void pickContact(View v)
    {
        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        Log.d("starting activity", "pickContact: ");
        startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check whether the result is ok
        if (resultCode == RESULT_OK) {
            // Check for the request code, we might be usign multiple startActivityForReslut
            switch (requestCode) {
                case RESULT_PICK_CONTACT:
                    contactPicked(data);
                    break;
            }
        } else {
            Log.e("MainActivity", "Failed to pick contact");
        }
    }

    public BusniessCard getContactInfo(Context context, String contactID)
    {
        String id = "";
        String adresse = "";
        String displayName = "";
        String mobilePhone = "";
        String email = "";
        String title = "";

        Cursor cursor = context.getContentResolver().query(ContactsContract.Data.CONTENT_URI,
                null
                , ContactsContract.Data.CONTACT_ID+ " = ? ", new String[]{contactID}, null);

        //getNameUsingContactId(selection);
        String contactInfoForQRCode= new String();

        if (cursor.moveToNext()) {

             id = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.CONTACT_ID));
             displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            Log.i(TAG,"ID IS "+id);
            Log.i(TAG,displayName);
            // to get data you need to query commonkinds
            Cursor emailCursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ? ", new String[]{id}, null);

            if(emailCursor.moveToNext())
            {
                //emailCursor.moveToNext();
                email = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                Log.i(TAG,email);
            }
            else
                email = "not found";
            Cursor phoneCursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null
                    , ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? ", new String[]{id}, null);

            if(phoneCursor.moveToNext())
            {
                //phoneCursor.moveToNext();
                mobilePhone = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                phoneCursor.moveToNext();
                //String organization = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.COMPANY));
                // String website = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Website.URL));
                Log.i(TAG,mobilePhone);
                phoneCursor.close();
            }
            else
                mobilePhone = "not found";

            String orgWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
            String[] orgWhereParams = new String[]{id,
                    ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE};
            Cursor orgCursor = context.getContentResolver().query(ContactsContract.Data.CONTENT_URI,
                    null, orgWhere, orgWhereParams, null);


            if(orgCursor.moveToNext())
            {

                title = orgCursor.getString(orgCursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE));
                Log.i(TAG,title);
                orgCursor.close();
            }
            else
            {
                title = "not found";

            }


            Cursor addressCursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.StructuredPostal.CONTACT_ID + " =?", new String[]{id}, null);
            if(addressCursor.moveToNext())
            {
                adresse=addressCursor.getString(addressCursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS));
                Log.i(TAG,adresse);
                addressCursor.close();
            }
            else
            {
                Log.i(TAG,"no address");
                adresse = "not provided";
            }

    }
        BusniessCard contact = new BusniessCard();
        contact.setmAddress(adresse);
        contact.setmEmail(email);
        contact.setmJobTitle(title);
        contact.setmName(displayName);
        contact.setmPhoneNumber(mobilePhone);
        return contact ;
    }

    private void contactPicked(Intent data) {
        String id = "";
        String photoPath = ""; //+ R.drawable.blank;
        byte[] photoByte = null;
        BusniessCard contact = new BusniessCard();
        Uri uri = data.getData();
        String[] projection = {ContactsContract.Contacts._ID};
        Cursor cursor = this.getContentResolver().query(uri, projection, null, null, null);
        if (cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Log.i("import", id);
            contact = getContactInfo(getApplicationContext(), id);
            Log.i("import", "after database call");

        }
        if (cursor.moveToFirst()) {
            if (cursor
                    .getString(
                            cursor
                                    .getColumnIndex("mimetype"))
                    .equals(ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE)) {
                photoByte = cursor.getBlob(cursor
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
                                    + id + ".png");

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
                contact.setmPicture(photoPath);

            }
        }
    }
}
