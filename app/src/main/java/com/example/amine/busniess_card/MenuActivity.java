package com.example.amine.busniess_card;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Amine on 30/05/2017.
 */

public class MenuActivity extends Activity {

    Button importContacts;
    Button scanQRCode;
    Button editMyCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        importContacts = (Button) findViewById(R.id.importContacts);
        scanQRCode = (Button) findViewById(R.id.scanQRcode);
        editMyCard = (Button) findViewById(R.id.editMycard);
        importContact();
        scan();
        edit();

    }

    public void importContact(){
        importContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchContactListActivity = new Intent(MenuActivity.this,ContactListActivity.class);
                startActivity(launchContactListActivity);
            }
        });
    }

    public void scan(){
        scanQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchScanQRCodeActivity = new Intent(MenuActivity.this,ScanQRCodeActivity.class);
                startActivity(launchScanQRCodeActivity);
            }
        });
    }

    public void edit(){
        editMyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchEditMyContactActivity = new Intent(MenuActivity.this,EditMyContactActivity.class);
                startActivity(launchEditMyContactActivity);
            }
        });
    }
}
