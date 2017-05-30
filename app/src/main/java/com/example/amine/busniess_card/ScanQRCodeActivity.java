package com.example.amine.busniess_card;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by Amine on 29/05/2017.
 */

public class ScanQRCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;

    private ImageView qr_code;
    private BusniessCard card;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);

        mScannerView = new ZXingScannerView(this);

        if (!getIntent().getExtras().getBoolean("generate")) {
            setContentView(mScannerView);

        } else if (getIntent().getSerializableExtra("Card") != null) {
            setContentView(R.layout.scan_qr_code);
            try {
                card = (BusniessCard) getIntent().getSerializableExtra("Card");
                Bitmap bitMatrix = QRCodeHandler.generateMatrix(card.toString(getApplicationContext()));
                qr_code = (ImageView) findViewById(R.id.qrCodeImg);
                qr_code.setImageBitmap(bitMatrix);
                card = (BusniessCard) getIntent().getSerializableExtra("Card");
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

    }
}

