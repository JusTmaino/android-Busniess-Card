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


    private void contactPicked(Intent data) {
        String id="";
        String displayName = "";
        String nickName = "";
        String homePhone = "";
        String mobilePhone = "";
        String email = "";
        String workPhone = "";
        String photoPath = ""; //+ R.drawable.blank;
        byte[] photoByte = null;
        String homeEmail = "";
        String workEmail = "";
        String companyName = "";
        String title = "";

        Uri uri = data.getData();
        Cursor dataCursor = this.getContentResolver().query(uri,null, null, null, null);
        if (dataCursor.moveToFirst()) {
            // Getting Display Name
            displayName = dataCursor
                    .getString(dataCursor
                            .getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
            id = dataCursor
                    .getString(dataCursor
                            .getColumnIndex(ContactsContract.Data._ID));

            do {
                Log.d("1","2");

                // Getting NickName
                if (dataCursor
                        .getString(
                                dataCursor
                                        .getColumnIndex("mimetype"))
                        .equals(ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE))
                    nickName = dataCursor.getString(dataCursor
                            .getColumnIndex("data1"));
                if (dataCursor
                        .getString(
                                dataCursor
                                        .getColumnIndex("mimetype"))
                        .equals(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)) {
                    switch (dataCursor.getInt(dataCursor
                            .getColumnIndex("data2"))) {
                        case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                            homePhone = dataCursor.getString(dataCursor
                                    .getColumnIndex("data1"));
                            break;
                        case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                            mobilePhone = dataCursor
                                    .getString(dataCursor
                                            .getColumnIndex("data1"));
                            break;
                        case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                            workPhone = dataCursor.getString(dataCursor
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
                if (dataCursor
                        .getString(
                                dataCursor
                                        .getColumnIndex("mimetype"))
                        .equals(ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)) {
                    companyName = dataCursor.getString(dataCursor
                            .getColumnIndex("data1"));
                    title = dataCursor.getString(dataCursor
                            .getColumnIndex("data4"));
                }
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

                }


            } while (dataCursor.moveToNext());
        }


        if (mobilePhone == null || mobilePhone.equals("")) {
            if (workPhone != null && !workPhone.equals(""))  {
                mobilePhone = workPhone ;
            } else if(homePhone != null && !homePhone.equals("")) {
                mobilePhone = homePhone;
            }else{
                mobilePhone = "not found";
            }
        }
        if (nickName != null && !nickName.equals("")) {
            displayName += " " + nickName;
        }
        if(displayName == null || displayName.equals("")){
            displayName = "not found";
        }
        if (workEmail != null && !workEmail.equals("")) {
            email = workEmail;
        }else if (homeEmail != null && !homeEmail.equals("")) {
            email = homeEmail ;
        }else{
            email = "not found";
        }
        if (title == null || title.equals("")) {
            title = "not found";
        }
        Log.d("nick name :",nickName);
        Log.d("displayName :",displayName);

        Log.d("phone 1 ",mobilePhone);
        Log.d("phone 2 ",workPhone);

        Log.d("email 1",homeEmail);
        Log.d("email 2",workEmail);

        Log.d("job",title);

        SqlLiteConnection sq = new SqlLiteConnection(getApplicationContext());
        sq.insertContact(id,displayName,title,mobilePhone,"not found",email,photoPath);
    }
}
