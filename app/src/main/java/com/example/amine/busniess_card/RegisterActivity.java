package com.example.amine.busniess_card;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    SqlLiteConnection cn;
    EditText username , password, confirmPassord ,job, phone, adress , email;
    Button registerBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        cn = new SqlLiteConnection(this);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.passwordLogin);
        confirmPassord = (EditText) findViewById(R.id.confirmPass);
        job = (EditText) findViewById(R.id.job);
        phone = (EditText) findViewById(R.id.phone);
        adress = (EditText) findViewById(R.id.adress);
        email = (EditText) findViewById(R.id.email);
        registerBTN = (Button) findViewById(R.id.registerBtn);
        addData();
    }

    public void addData(){
        registerBTN.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*boolean exist = cn.checkRegisterUser(username.getText().toString());
                        if(exist == true) {*/
                            if ((!password.getText().toString().isEmpty() && !confirmPassord.getText().toString().isEmpty())) {
                                if (password.getText().toString().equals(confirmPassord.getText().toString())) {
                                    boolean isInserted = cn.insertData(username.getText().toString(), password.getText().toString(), job.getText().toString(), phone.getText().toString(), adress.getText().toString(), email.getText().toString());
                                    if (isInserted == true) {
                                        Intent launchContactListActivity = new Intent(RegisterActivity.this, MenuActivity.class);
                                        launchContactListActivity.putExtra("USERNAME", username.getText().toString());
                                        startActivity(launchContactListActivity);
                                        Toast.makeText(RegisterActivity.this, "Registred", Toast.LENGTH_LONG).show();
                                        finish();
                                    } else
                                        Toast.makeText(RegisterActivity.this, "Not Registred", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(RegisterActivity.this, "Passwords Do not match", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(RegisterActivity.this, "Password and Confirm Needed", Toast.LENGTH_LONG).show();
                            }
                        /*}
                        else
                        {
                            Toast.makeText(RegisterActivity.this, "Username Already Exist", Toast.LENGTH_LONG).show();
                        }*/
                    }
                }
        );

    }

}
