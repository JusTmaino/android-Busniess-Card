package com.example.amine.busniess_card;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.google.zxing.Result;

import java.io.Serializable;

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

        BusniessCard card_result = getCardObject(result.getText().toString());
        Intent intent = new Intent(ScanQRCodeActivity.this, BusniessCardActivity.class);
        intent.putExtra("card", (Serializable) card_result);
        startActivity(intent);
    }


    private BusniessCard getCardObject(String s) {
        BusniessCard bc = new BusniessCard();
        //Nour je veux savoir comment je retroune la busniessCard (plutôt remplir la BusniessCard bc)
        // qui contient les données approprié à partir du String s (passé en paramètre)
        return bc;
    }
}
