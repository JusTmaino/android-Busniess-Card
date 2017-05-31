package com.example.amine.busniess_card;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Amine on 30/05/2017.
 */

public class MenuActivity extends AppCompatActivity {

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
        generate(getApplicationContext());
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

    public void generate(final Context c){
        generateQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean generate = true;
                Intent launchGenerateQRCodeActivity = new Intent(MenuActivity.this,ScanQRCodeActivity.class);
                launchGenerateQRCodeActivity.putExtra("generate", generate);
                SqlLiteConnection sq = new SqlLiteConnection(c);
                String username = getIntent().getExtras().getString("USERNAME");
                launchGenerateQRCodeActivity.putExtra("cardDetails", sq.getCard(username).getDetails());
                startActivity(launchGenerateQRCodeActivity);
            }
        });
    }

    public void edit(){
        editMyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchEditMyContactActivity = new Intent(MenuActivity.this,BusniessCardActivity.class);
                String username = getIntent().getExtras().getString("USERNAME");
                launchEditMyContactActivity.putExtra("username", username);
                startActivity(launchEditMyContactActivity);
            }
        });
    }
}
