package com.example.alphademo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button login;
    EditText username, password;
    com.example.alphademo.DatabaseSQLite myDB = new com.example.alphademo.DatabaseSQLite(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.login);

        username = findViewById(R.id.id);
        password = findViewById(R.id.password);


        onClickLogin();

    }

    private void onClickLogin() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
<<<<<<< HEAD:app/src/main/java/com/example/alphademo/MainActivity.java

                if(username.getText().toString().equals("") && password.getText().toString().equals("")){

                    openActivity2();
=======
                if(username.getText().toString().equals("admin") && password.getText().toString().equals("admin")){
                   showMessage("Message", "Successfully logged in!");
>>>>>>> 5742b86 (Rewritten login Page):app/src/main/java/com/example/myapplication/MainActivity.java
                }
                else{
                    showMessage("Message", "Error Logging In!");
                }


            }
        });
    }
        public void openActivity2(){


        Intent intent= new Intent(this, MainActivity2.class);
            startActivity(intent);
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
