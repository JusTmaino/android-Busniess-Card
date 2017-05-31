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
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;


/**
 * Created by Amine on 28/05/2017.
 */

public class ContactListActivity extends Activity {

    ArrayList<String> bcDetails ;
    ArrayList<BusniessCard> bcs ;
    ArrayAdapter<String> adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_list);
        bcDetails = new ArrayList<String>();
        this.getContactsData();
        /*mAdapter = new SimpleCursorAdapter(getBaseContext(),
                R.layout.lv_layout, null, new String[] { "name", "photo",
                "details" }, new int[] { R.id.tv_name, R.id.tv_photo,
                R.id.tv_details }, 0);*/

        // Getting reference to listview
        ListView lstContacts = (ListView) findViewById(R.id.listContacts);
        adapter = new ArrayAdapter<>(this.getApplicationContext(),android.R.layout.simple_list_item_1,bcDetails);
        lstContacts.setAdapter(adapter);
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

    public void getContactsData(){
        SqlLiteConnection sq = new SqlLiteConnection(this.getApplicationContext());
        bcs = sq.getContacts();
        Log.i(TAG, "getContactsData: avant bcs size");

            for(int i =0; i < bcs.size();i++){
                BusniessCard bc = bcs.get(i);
                String s = "";
                s += bc.getDetails();
                Log.i(s, "getContactsData: ");
                bcDetails.add(s);
            }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.contact_list, menu);
        return true;
    }
}
