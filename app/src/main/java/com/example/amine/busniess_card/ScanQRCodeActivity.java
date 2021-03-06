package com.example.amine.busniess_card;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.Result;

import java.io.Serializable;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static com.example.amine.busniess_card.BusniessCard.getCardObject;

public class ScanQRCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;

    private ImageView qr_code;
    private String card;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);

        mScannerView = new ZXingScannerView(this);

        if (!getIntent().getExtras().getBoolean("generate")) {
            setContentView(mScannerView);

        } else if (getIntent().getExtras().getString("cardDetails") != null) {
            setContentView(R.layout.scan_qr_code);
            try {
                card = getIntent().getExtras().getString("Card");
                Bitmap bitMatrix = QRCodeHandler.generateMatrix(getIntent().getExtras().getString("cardDetails"));
                qr_code = (ImageView) findViewById(R.id.qrCodeImg);
                qr_code.setImageBitmap(bitMatrix);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {

        String [] array = result.getText().toString().split(";");
        String msg = "";
        msg+= "Name :"+array[0]+"\n";
        if(!array[1].isEmpty()) {
            msg+= "Job :"+array[1]+"\n";
        }
        if(!array[2].isEmpty()) {
            msg+= "Phone :"+array[2]+"\n";
        }
        if(!array[3].isEmpty()) {
            msg+= "Adress :"+array[3]+"\n";
        }
        if(!array[4].isEmpty()) {
            msg+= "Email :"+array[4]+"\n";
        }
        /*msg+= "Phone :"+array[2]+"\n";
        msg+= "Adress :"+array[3]+"\n";
        msg+= "Email :"+array[4];*/


        AlertDialog alertDialog = new AlertDialog.Builder(ScanQRCodeActivity.this).create();
        alertDialog.setTitle("QRCode Info");
        alertDialog.setMessage(msg);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
        alertDialog.show();

        //Toast.makeText(ScanQRCodeActivity.this, msg, Toast.LENGTH_LONG).show();
        //BusniessCard card_result = getCardObject(result.getText().toString());
        //Intent intent = new Intent(ScanQRCodeActivity.this, BusniessCardActivity.class);
        //intent.putExtra("BusniessCard", (Serializable) card_result);
        //startActivity(intent);
    }

}
