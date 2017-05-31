package com.example.amine.busniess_card;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Amine on 30/05/2017.
 */

public class ShowMyContactActivity extends Activity {
    TextView name ,job, phone, adress , email;
    String nom ,title,tel,adr,mail ;
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
    }
}
