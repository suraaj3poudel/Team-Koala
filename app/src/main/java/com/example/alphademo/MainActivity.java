package com.example.alphademo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button login,register;
    EditText username, password;
    com.example.alphademo.DatabaseSQLite myDB = new com.example.alphademo.DatabaseSQLite(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        username = findViewById(R.id.id);
        password = findViewById(R.id.password);

        onClickRegister();
        onClickLogin();

    }

    private void onClickRegister() {
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted =  myDB.addData(username.getText().toString(), password.getText().toString());
                if(isInserted){
                    Toast.makeText(MainActivity.this, "Succesfully Registered", Toast.LENGTH_LONG).show();
                }

                else{
                    Toast.makeText(MainActivity.this, "Already Registered", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void onClickLogin(){
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor result = myDB.checkUser(username.getText().toString(), password.getText().toString() ) ;

                StringBuffer buffer  = new StringBuffer();
                while(result.moveToNext()){

                    buffer.append("UserName: "  + result.getString(1)+  "\n");
                    buffer.append("    Password: " +result.getString(2) +  "\n");

                }

                showMessage("Entries", buffer.toString());
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
