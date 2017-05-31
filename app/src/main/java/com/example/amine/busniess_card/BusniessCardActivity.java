package com.example.amine.busniess_card;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Amine on 30/05/2017.
 */

public class BusniessCardActivity extends Activity {

    SqlLiteConnection cn;
    private ImageView userPicture;
    EditText username , password, confirmPassord ,job, phone, adress , email;
    Button EditBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.busniess_card_user);
        userPicture = (ImageView) findViewById(R.id.imageUser);
        //userPicture.setImageBitmap(image);
        username = (EditText) findViewById(R.id.usernameEditText);
        password = (EditText) findViewById(R.id.passwordEditText);
        confirmPassord = (EditText) findViewById(R.id.confirmPassEditText);
        job = (EditText) findViewById(R.id.jobEditText);
        phone = (EditText) findViewById(R.id.phoneEditText);
        adress = (EditText) findViewById(R.id.adressEditText);
        email = (EditText) findViewById(R.id.emailEditText);
        EditBTN = (Button) findViewById(R.id.editBtn);
        updateData();

    }

    public void updateData(){
        EditBTN.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if( password.getText().toString().equals(confirmPassord.getText().toString()) )
                        {
                            boolean isUpdated = cn.updateData(username.getText().toString(), password.getText().toString(),job.getText().toString(), phone.getText().toString(), adress.getText().toString(), email.getText().toString());
                            if (isUpdated == true)
                            {
                                Intent launchContactListActivity = new Intent(BusniessCardActivity.this, MenuActivity.class);
                                launchContactListActivity.putExtra("USERNAME", username.getText().toString());
                                startActivity(launchContactListActivity);
                                Toast.makeText(BusniessCardActivity.this, "Updated", Toast.LENGTH_LONG).show();
                            }
                            else
                                Toast.makeText(BusniessCardActivity.this, "Not Updated", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(BusniessCardActivity.this, "Passwords Do not match", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );

    }

}
