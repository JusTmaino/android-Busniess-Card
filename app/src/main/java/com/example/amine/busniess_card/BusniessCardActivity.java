package com.example.amine.busniess_card;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class BusniessCardActivity extends AppCompatActivity {

    SqlLiteConnection cn;
    private ImageView userPicture;
    EditText username , password, confirmPassord ,job, phone, adress , email;
    Button EditBTN;
    BusniessCard bc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.busniess_card_user);
        //userPicture = (ImageView) findViewById(R.id.imageUser);
        //userPicture.setImageBitmap(image);

        username = (EditText) findViewById(R.id.usernameEditText);
        password = (EditText) findViewById(R.id.passwordEditText);
        confirmPassord = (EditText) findViewById(R.id.confirmPassEditText);
        job = (EditText) findViewById(R.id.jobEditText);
        phone = (EditText) findViewById(R.id.phoneEditText);
        adress = (EditText) findViewById(R.id.adressEditText);
        email = (EditText) findViewById(R.id.emailEditText);
        EditBTN = (Button) findViewById(R.id.editBtn);

        String userID = getIntent().getExtras().getString("userID");
        //Toast.makeText(BusniessCardActivity.this,userID , Toast.LENGTH_LONG).show();
        String user_name = getIntent().getExtras().getString("username");
        cn = new SqlLiteConnection(this);
        bc = cn.getCard(user_name);
        //Toast.makeText(BusniessCardActivity.this, cn.getCard(user_name).getDetails(), Toast.LENGTH_LONG).show();

        username.setText(bc.getmName(), TextView.BufferType.EDITABLE);
        job.setText(bc.getmJobTitle(), TextView.BufferType.EDITABLE);
        phone.setText(bc.getmPhoneNumber(), TextView.BufferType.EDITABLE);
        adress.setText(bc.getmAddress(), TextView.BufferType.EDITABLE);
        email.setText(bc.getmEmail(), TextView.BufferType.EDITABLE);

        updateData(userID);

    }

    public void updateData(final String ID){
        EditBTN.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if((!password.getText().toString().isEmpty() && !confirmPassord.getText().toString().isEmpty())) {
                            if (password.getText().toString().equals(confirmPassord.getText().toString())) {
                                boolean isUpdated = cn.updateData(ID, username.getText().toString(), password.getText().toString(), job.getText().toString(), phone.getText().toString(), adress.getText().toString(), email.getText().toString());
                                if (isUpdated == true) {
                                    Toast.makeText(BusniessCardActivity.this, "Updated", Toast.LENGTH_LONG).show();
                                    finish();
                                } else
                                    Toast.makeText(BusniessCardActivity.this, "Not Updated", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(BusniessCardActivity.this, "Passwords Do not match", Toast.LENGTH_LONG).show();
                            }
                        }
                        else {
                            Toast.makeText(BusniessCardActivity.this, "Password and Confirm Needed", Toast.LENGTH_LONG).show();
                        }

                    }
                }
        );

    }

}
