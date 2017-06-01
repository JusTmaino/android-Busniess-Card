package com.example.amine.busniess_card;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;

import java.io.File;

/**
 * Created by Amine on 30/05/2017.
 */

public class ShowMyContactActivity extends Activity {
    TextView name ,job, phone, adress , email;
    ImageView qr ,pictureView;
    String nom ,title,tel,adr,mail,details,picture ;
    BusniessCard bc ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_business_card);
        nom = getIntent().getStringExtra("name");
        title = getIntent().getStringExtra("job");
        tel = getIntent().getStringExtra("phone");
        adr = getIntent().getStringExtra("adresse");
        mail = getIntent().getStringExtra("email");
        details = getIntent().getStringExtra("details");
        picture = getIntent().getStringExtra("picture");
        Bitmap bitMatrix = null;
        try {
            bitMatrix = QRCodeHandler.generateMatrix(details);

        } catch (WriterException e) {
            e.printStackTrace();
        }
        qr = (ImageView) findViewById(R.id.contactQR);
        qr.setImageBitmap(bitMatrix);
        pictureView =(ImageView) findViewById(R.id.contactImage);
        name = (TextView) findViewById(R.id.contactName);
        job = (TextView) findViewById(R.id.contactJob);
        phone = (TextView) findViewById(R.id.contactPhone);
        adress = (TextView) findViewById(R.id.contactAdress);
        email = (TextView) findViewById(R.id.contactEmail);
        name.setText(nom);
        job.setText(title);
        phone.setText(tel);
        adress.setText(adr);
        email.setText(mail);
        Log.d("picture ",picture);
        File imgFile = new  File(picture);

        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            pictureView.setImageBitmap(myBitmap);

        }

    }
}
