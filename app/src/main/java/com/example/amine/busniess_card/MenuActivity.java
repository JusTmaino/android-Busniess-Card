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
    Button generateQRCode;
    Button editMyCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        importContacts = (Button) findViewById(R.id.importContacts);
        scanQRCode = (Button) findViewById(R.id.scanQRcode);
        generateQRCode = (Button) findViewById(R.id.generateQRCod);
        editMyCard = (Button) findViewById(R.id.editMycard);
        importContact();
        scan();
        generate();
        edit();

    }

    public void importContact(){
        importContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchContactListActivity = new Intent(MenuActivity.this,ContactImportActivity.class);
                startActivity(launchContactListActivity);
            }
        });
    }

    public void scan(){
        scanQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean generate = false;
                Intent launchScanQRCodeActivity = new Intent(MenuActivity.this,ScanQRCodeActivity.class);
                launchScanQRCodeActivity.putExtra("generate", generate);
                startActivity(launchScanQRCodeActivity);
            }
        });
    }

    public void generate(){
        generateQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean generate = true;
                Intent launchGenerateQRCodeActivity = new Intent(MenuActivity.this,ScanQRCodeActivity.class);
                launchGenerateQRCodeActivity.putExtra("generate", generate);
                startActivity(launchGenerateQRCodeActivity);
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
