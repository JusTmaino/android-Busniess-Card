package com.example.amine.busniess_card;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SqlLiteConnection cn;
    EditText username , password;
    Button loginBTN;
    TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cn = new SqlLiteConnection(this);
        username = (EditText) findViewById(R.id.usernameLogin);
        password = (EditText) findViewById(R.id.passwordLogin);
        loginBTN = (Button) findViewById(R.id.loginBtn);
        register = (TextView)findViewById(R.id.registerTxt);
        login();
        register();

    }

    public void login(){
        loginBTN.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean exist = cn.checkLogin(username.getText().toString() , password.getText().toString());
                        if(exist == true)
                        {
                            Intent launchMenuActivity = new Intent(MainActivity.this, MenuActivity.class);
                            launchMenuActivity.putExtra("USERNAME", username.getText().toString());
                            startActivity(launchMenuActivity);
                            Toast.makeText(MainActivity.this, "Logged In", Toast.LENGTH_LONG).show();
                        }
                        else
                            Toast.makeText(MainActivity.this,"Logging Error",Toast.LENGTH_LONG).show();
                    }
                }
        );

    }

    public void register(){
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchRegisterActivity = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(launchRegisterActivity);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
