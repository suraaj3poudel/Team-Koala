package com.example.alphademo.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.alphademo.R;
import com.example.alphademo.database.DatabaseSQLite;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public FirebaseAuth mAuth;
    Button login;
    EditText username, password;
    DatabaseSQLite myDB = new DatabaseSQLite(this);
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Username = "username";
    public static final String Password = "password";
    public static final boolean LoggedIn = false;
    //private String[] user = new String[]{"TeamKoala","2BNot2B","404Found","BrewingJava","CodeOfDuty","GitSetCode","KotlinKillers","RuntimeTerrorRD"};
    ArrayList<String> user = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.login);

        username = findViewById(R.id.Driverid);
        password = findViewById(R.id.password);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        user.add("teamkoala");
        user.add("2bnot2b");
        user.add("brewingjava");
        user.add("codeofduty");
        user.add("gitsetcode");
        user.add("kotlinkillers");
        user.add("runtimeterrorrd");
        user.add("404found");

        if(sharedpreferences.getBoolean("loggedIn", false))
            openActivity2();

        onClickLogin();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
    }

    private void onClickLogin() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String m_username  = username.getText().toString();
                String m_password  = password.getText().toString();
                SharedPreferences.Editor editor = sharedpreferences.edit();
                if(m_username.equals("")){
                    m_username= "TeamKoala";
                }
                if(user.contains(m_username.toLowerCase())){
                    editor.putString(Username, m_username).apply();
                    editor.putString(Password, m_password).apply();
                    editor.putBoolean("loggedIn", true).apply();
                    editor.commit();
                    openActivity2();
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
