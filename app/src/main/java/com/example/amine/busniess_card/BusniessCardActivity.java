package com.example.amine.busniess_card;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * Created by Amine on 30/05/2017.
 */

public class BusniessCardActivity extends Activity {

    private ImageView qr_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_qr_code);
        qr_code = (ImageView) findViewById(R.id.qrCodeImg);
        //qr_code.setImageBitmap(Notre QRCODE à décodé qu'on reçu depuis handleResult);

    }
}
