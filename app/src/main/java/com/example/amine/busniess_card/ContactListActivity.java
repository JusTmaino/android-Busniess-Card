package com.example.amine.busniess_card;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.support.v4.widget.SimpleCursorAdapter;
import android.database.MatrixCursor;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class ContactListActivity extends AppCompatActivity {

    ArrayList<String> bcDetails ;
    ArrayList<BusniessCard> bcs ;
    ArrayAdapter<String> adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_list);
        bcDetails = new ArrayList<String>();
        this.getContactsData();

        // Getting reference to listview
        ListView lstContacts = (ListView) findViewById(R.id.listContacts);
        adapter = new ArrayAdapter<>(this.getApplicationContext(),android.R.layout.simple_list_item_1,bcDetails);
        lstContacts.setAdapter(adapter);
        lstContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {

                    Intent intent = new Intent(ContactListActivity.this,ShowMyContactActivity.class);
                    intent.putExtra("name", bcs.get(position).getmName());
                intent.putExtra("job", bcs.get(position).getmJobTitle());
                intent.putExtra("adresse", bcs.get(position).getmAddress());
                intent.putExtra("email", bcs.get(position).getmEmail());
                intent.putExtra("phone", bcs.get(position).getmPhoneNumber());
                intent.putExtra("details", bcs.get(position).getDetails());
                intent.putExtra("picture", bcs.get(position).getmPicture());
                    startActivity(intent);

            }
        });

    }

    public void getContactsData(){
        SqlLiteConnection sq = new SqlLiteConnection(this.getApplicationContext());
        bcs = sq.getContacts();
        Log.i(TAG, "getContactsData: avant bcs size");

            for(int i =0; i < bcs.size();i++){
                BusniessCard bc = bcs.get(i);
                String s = "";
                s += bc.getmName() + "   "+ bc.getmPhoneNumber();
                bcDetails.add(s);
            }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.contact_list, menu);
        return true;
    }
}
