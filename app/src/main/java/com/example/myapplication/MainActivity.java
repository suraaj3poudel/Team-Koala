package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    Button login;
    EditText username, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.login);
        username = findViewById(R.id.id);
        password = findViewById(R.id.password);

        onClickLogin();

    }


    private void onClickLogin(){
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.equals("admin") && password.equals("admin")){
                   showMessage("Message", "Successfully logged in!");
                }
                else{
                    showMessage("Message", "Wrong Username/Password!");
                }
            }
        });
    }

    private void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);

        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();

    }
}
